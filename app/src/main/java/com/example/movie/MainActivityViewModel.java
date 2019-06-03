package com.example.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository = new MoviesRepository();
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<Movie>> getAllMovies() {
        return movies;
    }

    public void getMostPopularMovies(int year) {
        moviesRepository
                .getMostPopularMovies2018(year)
                .enqueue(new Callback<AllMovies>() {
                    @Override
                    public void onResponse(@NonNull Call<AllMovies> call, @NonNull Response<AllMovies> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            movies.setValue(response.body().getResults());
                        } else {
                            error.setValue("Api Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AllMovies> call, @NonNull Throwable t) {
                        error.setValue("Api Error: " + t.getMessage());
                    }
                });
    }
}
