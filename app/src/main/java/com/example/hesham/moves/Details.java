package com.example.hesham.moves;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.async.Movies;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    Movies model;
    ImageView img;
    TextView Title, data, Rate, Dec;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        img = (ImageView) findViewById(R.id.ImageOfResutl);
        if (InternetConnection.checkConnection(getApplicationContext())) {
            Intent i = getIntent();
            model = (Movies) i.getSerializableExtra("sampleObject");
            if (model != null) {
                Title.setText(model.getTitle());
                Dec.setText(model.getOverview());
                Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + model.getPosterPath()).into(img);
                data.setText(model.getReleaseDate());
                Rate.setText(model.getVoteAverage() + "/10");

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);


    }
}