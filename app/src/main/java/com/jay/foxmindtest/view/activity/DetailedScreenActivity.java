package com.jay.foxmindtest.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.foxmindtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailedScreenActivity extends AppCompatActivity {

    private String TAG = "LOG_TAG";

    private String image;
    private String title;
    private String description;
    private String price;
    private String currencyCode;

    private boolean isAlReadySaved;

    private FrameLayout parentLayout;
    private FloatingActionButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_screen);

        parentLayout = findViewById(R.id.parent_layout);
        saveBtn = findViewById(R.id.save);

        isAlReadySaved = false;

        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");

        description = getIntent().getStringExtra("description");

        price = getIntent().getStringExtra("price");
        currencyCode = getIntent().getStringExtra("currency");

        ImageView imageView = findViewById(R.id.image);
        TextView titleTextView = findViewById(R.id.title);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView priceTextView = findViewById(R.id.price);
        TextView currencyTextView = findViewById(R.id.currency);

        Picasso.get().load(image).into(imageView);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        priceTextView.setText(getResources().getString(R.string.price).concat(price));
        currencyTextView.setText(getResources().getString(R.string.currency_code).concat(currencyCode));
    }
}
