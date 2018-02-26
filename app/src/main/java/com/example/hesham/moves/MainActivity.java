package com.example.hesham.moves;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOFAllMovies.MoviesAdapter;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.example.hesham.moves.widget.NoteActiviy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;


    MoviesAPI moviesAPI;
    MovesModel PoplarModel;
    MovesModel TopRateModel;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;


    List<ResultModel> PopularResult = new ArrayList<>();
    List<ResultModel> TopRateResult = new ArrayList<>();
    List<ResultModel> Favourit = new ArrayList<>();
    int flag=0;
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



        CallApi();

    }
    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, SignInAndSignUp.class);
        startActivity(startIntent);
        finish();

    }

    private  void CallApi() {
        if (InternetConnection.checkConnection(MainActivity.this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);


            Call<MovesModel> PopularRecall = moviesAPI.getAllMovesPopular();
            PopularRecall.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        PoplarModel = response.body();
//                        Log.e("Guinness", "p main" + PoplarModel.toString());

                        PopularResult = PoplarModel.getResults();
//                      Log.e("Guinness", response.toString());
                        flag=1;
                        adapter = new MoviesAdapter(PopularResult, MainActivity.this);
                        recyclerView.setAdapter(adapter);


                    } else {
//                        Log.d("Guinness", " the respons code of popular " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
//                    Log.d("Guinness", "Respons get onFailure popular");

                }
            });




            final Call<MovesModel> TopRate = moviesAPI.getAllMovestop_rated();
            TopRate.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        TopRateModel = response.body();
//                        Log.e("Guinness", "top " + TopRateModel.toString());

                        TopRateResult = TopRateModel.getResults();
//                        Log.d("Guinness", response.toString());
                    } else {
//                        Log.d("Guinness", " the respons code of TopRate " + response.code());

                    }

                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {

//                    Log.d("Guinness", "Respons get onFailure TopRate");

                }
            });

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    recyclerView, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
                    if (InternetConnection.checkConnection(MainActivity.this)) {

                        if (flag == 1) {

                            Intent i = new Intent(MainActivity.this, Details.class);
                            ResultModel model = getPopularResult().get(position);
                            i.putExtra("sampleObject", model);
                            startActivity(i);

                        } else if (flag == 2) {

                            Intent i = new Intent(MainActivity.this, Details.class);
                            ResultModel model = getTopRateResult().get(position);
                            i.putExtra("sampleObject", model);
                            startActivity(i);

                        } else if (flag == 3) {
                            Intent i = new Intent(MainActivity.this, Details.class);
                            ResultModel model = getFavourit().get(position);
                            i.putExtra("sampleObject", model);
                            startActivity(i);

                        }

                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getText(R.string.Nointernet), Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

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
            flag=1;
            adapter = new MoviesAdapter(getPopularResult(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
        if (id == R.id.TopRate) {
            flag=2;
            adapter = new MoviesAdapter(getTopRateResult(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
        if (id==R.id.History){
            Intent intent= new Intent(this, NoteActiviy.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.main_logout_btn) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }
        return super.onOptionsItemSelected(item);

    }





    public List<ResultModel> getPopularResult() {
        return PopularResult;
    }

    public List<ResultModel> getTopRateResult() {
        return TopRateResult;
    }

    public List<ResultModel> getFavourit() {
        return Favourit;
    }


}
