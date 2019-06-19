package com.hasanin.hossam.moviefinder;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShowMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle data = getIntent().getExtras();

        RecyclerView linksButtons = findViewById(R.id.movieLinks);
        ImageView movieImage = findViewById(R.id.movieImage);
        TextView movieName = findViewById(R.id.movieName);
        TextView movieStory = findViewById(R.id.movieStory);

        Glide.with(this).load(data.getString("movieImg")).into(movieImage);
        movieName.setText(data.getString("movieName"));
        movieStory.setText(data.getString("movieStory"));
//        ArrayList<String> c = new ArrayList<String>();
//        c.add("foshia");
//        c.add("pink");
//        c.add("yellow");
//        c.add("baby blue");
//        c.add("brown");
//        c.add("green 2");
//        c.add("green");
//        c.add("pink 2");
//        c.add("red");
//        c.add("pink3");
        LinksRecAdabter linksRecAdabter = new LinksRecAdabter(data.getStringArrayList("movieLinks") , this);
        //LinksRecAdabter linksRecAdabter = new LinksRecAdabter(c , this);
        linksButtons.setAdapter(linksRecAdabter);
        linksButtons.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
