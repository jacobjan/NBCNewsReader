package com.jjdev.NBCNewsReader;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.ValueCallback;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardRequest {

    private static final String BOARD_API_URL = "https://s3.amazonaws.com/shrekendpoint/news.json";
            // MHE "https://jsonplaceholder.typicode.com/posts";

    private final RequestQueue requestQueue;
    public List<Post> posts = new ArrayList<>();
    Gson gson = new Gson();

    public BoardRequest(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void send(final ValueCallback<List<Post>> onComplete) {
        JsonRequest jsonRequest = new JsonArrayRequest(BOARD_API_URL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                Map jsonObject = null;
                jsonObject = (Map) ((List)gson.fromJson(response.toString(), Object.class)).get(0);
                List newsItems = (List) jsonObject.get("items");
                try {
                    posts = processResponse(newsItems);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                onComplete.onReceiveValue(posts);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                onComplete.onReceiveValue(null);
            }
        });
        requestQueue.add(jsonRequest);
    }

    private List<Post> processResponse(List response) throws ParseException {
        String pubDT = "";
        int numOfStale = 0;
        for (int i = 0; i < response.size(); i++) {
            Map<String, String> newsObject = (Map<String, String>) response.get(i);
            pubDT = newsObject.get("published");
            Post currPost = new Post(newsObject.get("id"),
                                        newsObject.get("type"),
                                        pubDT,
                                        newsObject.get("headline"),
                                        newsObject.get("url"),
                                        newsObject.get("summary"));

            if ( currPost.staleForDays(7, pubDT) == false ) {
                posts.add(currPost);
            } else {
                Log.i("jjdev", ++numOfStale + "*** Expired !! " + pubDT + " headline:"+ newsObject.get("headline"));
            }
      }
       return posts;
    }
}
