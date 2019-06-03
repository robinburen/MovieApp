package com.example.movie;

import retrofit2.Call;

class MoviesRepository {
    private MoviesAPIService allMoviesApiService = MovieAPI.create();

    Call<AllMovies> getMostPopularMovies2018(int year) {
        return allMoviesApiService.getMostPopularMovies(year);
    }
}
