package com.jjdev.NBCNewsReader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.jjdev.NBCNewsReader.R;

public class BoardAdapter extends BaseAdapter {

    private final List<String> listOfContent;
    private final LayoutInflater layoutInflater;

    public BoardAdapter(Context context, List<String> content) {
        this.listOfContent = content;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listOfContent.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_board, parent, false);
        }

        String displine = (String) getItem(position);
        TextView headlineView = convertView.findViewById(R.id.board_headline_text_view);
        headlineView.setText(displine);
        headlineView.setBackgroundColor(Color.WHITE);
        if ( displine.startsWith("video")) {
            headlineView.setBackgroundColor(Color.GRAY);
        } else if ( displine.startsWith("slideshow")) {
            headlineView.setBackgroundColor(Color.GREEN);
        }
        return convertView;
    }
}
