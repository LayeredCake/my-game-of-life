package com.example.mygameoflife;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = new GridView(this);
        setContentView(mView);
        mView.setBackgroundColor(Color.BLACK);
    }
}
