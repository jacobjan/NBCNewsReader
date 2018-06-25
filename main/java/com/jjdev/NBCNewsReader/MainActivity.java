package com.jjdev.NBCNewsReader;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.jjdev.NBCNewsReader.BoardAdapter;

public class MainActivity extends AppCompatActivity {
    private List<Post> news = new ArrayList<>();
    private ListView boardList;
    private BoardAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardList = findViewById(R.id.board_list);

        BoardRequest boardRequest = new BoardRequest(this);
        boardRequest.send(new ValueCallback<List<Post>>() {
            @Override
            public void onReceiveValue(List<Post> value) {
                news = value;
                final List<String> headlines = filerHeadlines(news);
                boardList.post(new Runnable() {
                    @Override
                    public void run() {
                        boardAdapter = new BoardAdapter(MainActivity.this, headlines);
                        boardList.setAdapter(boardAdapter);
                    }
                });
             }
        });

        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Toast.makeText(getApplicationContext(), news.get(position).getAll(), Toast.LENGTH_LONG).show();
                loadurl( news.get(position).getUrl());
            }
        });
    }

    private void loadurl(String url) {
        Bundle bdl = new Bundle();
        Intent loadUrlIntent = new Intent(Intent.ACTION_VIEW);
        loadUrlIntent.setData(Uri.parse(url));
        startActivity(loadUrlIntent);
    }

    private List<String> filerHeadlines(List<Post> posts) {
        List<String> newsHeadlineList = new ArrayList<>();
        for (Post post : posts) {
            newsHeadlineList.add(post.getType() + " - " + post.getHeadline());
        }
        return newsHeadlineList;
    }
}
