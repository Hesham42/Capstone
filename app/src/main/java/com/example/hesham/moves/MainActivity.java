package com.example.hesham.moves;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.adapter.MoviesAdapter;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
import com.example.hesham.moves.async.FetchMovieTask;
import com.example.hesham.moves.async.Movies;
import com.example.hesham.moves.async.MyCallback;
import com.example.hesham.moves.widget.NoteActiviy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyCallback {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    String popular = "http://api.themoviedb.org/3/movie/popular?";
    String top_rated = "http://api.themoviedb.org/3/movie/top_rated?";

    public static final String API_KEY = "28f81313599c7074d6380330fe1dca22";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {


            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        }
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CallApi(popular);


    }


    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, SignInAndSignUp.class);
        startActivity(startIntent);
        finish();

    }


    private void CallApi(String url) {
        if (InternetConnection.checkConnection(MainActivity.this)) {
            FetchMovieTask moviesTask = new FetchMovieTask((MyCallback) this, this);
            moviesTask.execute(url);
        }else {
            Toast.makeText(this,"there is no internet found",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Pouplar) {
            CallApi(popular);

        }
        if (id == R.id.TopRate) {
            CallApi(top_rated);

        }
        if (id == R.id.History) {
            Intent intent = new Intent(this, NoteActiviy.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.main_logout_btn) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void updateAdapter(final List<Movies> movies) {
        adapter = new MoviesAdapter(movies,
                MainActivity.this,
                new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movies item) {

                if (InternetConnection.checkConnection(MainActivity.this)) {
                    Intent i = new Intent(MainActivity.this, Details.class);
                    i.putExtra("sampleObject", (Serializable) item);
                    startActivity(i);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


}
