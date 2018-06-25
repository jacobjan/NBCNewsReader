package com.jjdev.NBCNewsReader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjdev.NBCNewsReader.R;

public class ListDetailActivity extends AppCompatActivity {

    TextView detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        detail = (TextView) findViewById(R.id.list_detail);
        Bundle extras= getIntent().getExtras();
        detail.setText((String)extras.get("currTitle"));
    }
}
