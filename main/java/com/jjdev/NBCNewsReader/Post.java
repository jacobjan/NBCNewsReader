package com.jjdev.NBCNewsReader;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private final String id;
    private final String type;
    private final String published;
    private final String headline;
    private final String url;
    private final String summary;
    public long millisInDay = 24 * 60 * 60 * 1000;
    public long millisTD = (new Date()).getTime();
    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public Post(String id, String type, String published, String headline, String url, String summary) {
        this.id = id;
        this.type = type;
        this.published = published;
        this.headline = headline;
        this.url = url;
        this.summary = summary;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public boolean staleForDays(int ifMoreThanDaysOld, String published) throws ParseException {
        long MillisSpan = millisFromToday(published);
        long MillisExp = ifMoreThanDaysOld * millisInDay;
        if ( MillisSpan > MillisExp )
            return true;
        else
            return false;
    }

    public long millisFromToday(String published) throws ParseException {
        String strtz = published.replace("Z", "-0800");
        long millisDiff = millisTD - formatter.parse(strtz).getTime();
        return millisDiff;
    }

    public String getAll() {
        return headline + " ID:"+id+" summary:"+summary;
    }
}
