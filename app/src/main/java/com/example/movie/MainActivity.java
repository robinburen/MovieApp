package com.example.movie;

import android.arch.lifecycle.ViewModelProviders;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener, MovieListener {

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private EditText mSelectYear;
    private List<Movie> allMovies;
    private MainActivityViewModel viewModel;
    public static final String MOVIE_DETAILS = "Movie";
    public static final int REQUEST_MOVIE_DETAILS = 888;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        mSelectYear = findViewById(R.id.selectYear);
        Button getMoviesButton = findViewById(R.id.button);

        getMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt(mSelectYear.getText().toString());
                viewModel.getMostPopularMovies(year);
            }
        });

        recyclerView.addOnItemTouchListener(this);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() { // Add Global Layout Listener to calculate the span count.
                    @Override
                    public void onGlobalLayout() {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        gridLayoutManager.setSpanCount(calculateSpanCount());
                        gridLayoutManager.requestLayout();
                    }
                });

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG)
                        .show();
            }
        });

        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                allMovies = movies;
                updateUI();
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (child != null) {
            int adapterPosition = recyclerView.getChildAdapterPosition(child);
            if (mGestureDetector.onTouchEvent(motionEvent)) {
                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra(MOVIE_DETAILS, allMovies.get(adapterPosition));
                startActivityForResult(intent, REQUEST_MOVIE_DETAILS);
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new MoviesAdapter(allMovies, this);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(allMovies);
        }
    }

    /**
     * Calculate the number of spans for the recycler view based on the recycler view width.
     *
     * @return int number of spans.
     */

    private int calculateSpanCount() {
        int viewWidth = recyclerView.getMeasuredWidth();
        float cardViewWidth = getResources().getDimension(R.dimen.poster_width);
        float cardViewMargin = getResources().getDimension(R.dimen.margin_medium);

        int spanCount = (int) Math.floor(viewWidth / (cardViewWidth + cardViewMargin));

        return spanCount >= 1 ? spanCount : 1;
    }

    @Override
    public void onItemClick(Movie movie) {

    }
}
