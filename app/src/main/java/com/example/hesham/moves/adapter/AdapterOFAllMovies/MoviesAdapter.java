package com.example.hesham.moves.adapter.AdapterOFAllMovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hesham.moves.R;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<ResultModel> resultModels;
    private Context context;

    public MoviesAdapter(List<ResultModel> resultModels, Context context) {
        this.resultModels = resultModels;

        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allmovies, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + resultModels.get(position).getPosterPath()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return resultModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);

        }
    }
}