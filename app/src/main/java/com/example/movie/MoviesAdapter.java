package com.example.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> movies;
    private MovieListener movieListener;

    public MoviesAdapter(List<Movie> movies, MovieListener movieListener) {
        this.movies = movies;
        this.movieListener = movieListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_movie_list_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);

        viewHolder.popularityPosition.setText(String.valueOf(i+1) + ".");
        new DownloadImage(viewHolder.moviePoster).execute(movie.getPosterPath());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout movieCell;
        TextView popularityPosition;
        ImageView moviePoster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieCell = itemView.findViewById(R.id.movieCell);
            popularityPosition = itemView.findViewById(R.id.popularityPosition);
            moviePoster = itemView.findViewById(R.id.moviePoster);

            movieCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieListener.onItemClick(movies.get(ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }

    public void swapList(List<Movie> newList) {
        movies = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
