package com.example.hesham.moves.Utilities;

import android.util.Log;

import com.example.hesham.moves.BuildConfig;
import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelreviews.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.example.hesham.moves.MainActivity.API_KEY;

/**
 * Created by Hesham on 9/20/2017.
 */
//http://api.themoviedb.org/3/movie/popular?api_key=KEYs


public interface MoviesAPI {
    String BASE_URL = "http://api.themoviedb.org";

    @GET("/3/movie/popular?api_key="+API_KEY)
    Call<MovesModel> getAllMovesPopular();
    @GET("/3/movie/top_rated?api_key="+API_KEY)
    Call<MovesModel> getAllMovestop_rated();
    @GET("/3/movie/{id}/videos?api_key="+API_KEY)
    Call<Trial> selectedVedio(@Path("id") int id);
    @GET("/3/movie/{id}/Reviews?api_key="+API_KEY)
    Call<Reviews> selectedReviews(@Path("id") int id);

}
