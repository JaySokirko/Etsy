package com.jay.foxmindtest.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jay.foxmindtest.R;

import java.util.ArrayList;

public class DetailedScreenActivity extends AppCompatActivity {

    private String TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_screen);


        Log.d(TAG, "onCreate: " + getIntent().getStringExtra("title"));

    }
}
