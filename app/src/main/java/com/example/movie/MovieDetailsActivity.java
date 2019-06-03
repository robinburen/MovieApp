package com.example.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView mBackdropImage = findViewById(R.id.backdropImage);
        ImageView mPosterImage = findViewById(R.id.posterImage);
        TextView mTitle = findViewById(R.id.title);
        TextView mReleaseDate = findViewById(R.id.releaseDate);
        TextView mRating = findViewById(R.id.rating);
        TextView mOverview = findViewById(R.id.overview);

        Movie movie = getIntent().getParcelableExtra(MainActivity.MOVIE_DETAILS);

        new DownloadImage(mBackdropImage).execute(movie.getBackdropPath());
        new DownloadImage(mPosterImage).execute(movie.getPosterPath());

        mTitle.setText(movie.getTitle());
        mReleaseDate.setText("Release: " + movie.getReleaseDate());
        mRating.setText(movie.getVoteAverage().toString());
        mOverview.setText(movie.getOverview());
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
