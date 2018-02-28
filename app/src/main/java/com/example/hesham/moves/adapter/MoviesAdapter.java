package com.example.hesham.moves.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hesham.moves.R;
import com.example.hesham.moves.async.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/28/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    List<Movies> moviesList = new ArrayList<>();
    Context context;

    public interface OnItemClickListener {
        void onItemClick(Movies item);
    }

    private final OnItemClickListener listener;

    public MoviesAdapter(List<Movies> moviesList, Context context,OnItemClickListener listner) {
        this.moviesList = moviesList;
        this.context = context;
        this.listener=listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_allmovies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movies movies = moviesList.get(position);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + movies.getPosterPath())
                .into(holder.imageView);

        holder.OnBindingListner(movies,listener);

    }

    @Override
    public int getItemCount() {
        if (moviesList==null){
            return 0;
        }else{

            return moviesList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }

        public void OnBindingListner(final Movies item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
