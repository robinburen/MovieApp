package com.example.movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MoviesAPIService {
    @GET("/3/discover/movie?api_key=3bed9a4b7b78d10b9110b938e34d7622&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Call<AllMovies> getMostPopularMovies(@Query("year") int year);
}
