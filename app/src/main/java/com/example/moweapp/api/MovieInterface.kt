package com.example.moweapp.api

import com.example.moweapp.model.*
import retrofit2.Call
import retrofit2.http.*

interface MovieInterface {


    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key: String): Call<MovieResponse>


    @GET("movie/now_playing")
    fun getReleaseMovies(
        @Query("api_key") api_key: String) : Call<MovieResponse>


    @GET("movie/{movie_id}")
    fun getDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String, ): Call<DetailResponse>


    @GET("movie/{movie_id}/credits")
    fun getCast( @Path("movie_id") movie_id: Int,
                 @Query("api_key") api_key: String,) : Call<CastResponse>


    @POST("movie/{movie_id}/rating")
    fun rateMovie( @Path("movie_id") movie_id: Int,
                   @Body rating: Double,
                   @Query("api_key") api_key: String, ): Call<ResponseBody>


    @GET("genre/movie/list")
    fun getGenreList(
        @Query("api_key") api_key: String) : Call<GenreResponse>


    @GET("discover/movie")
    fun getMovieByGenre(
        @Query("api_key") api_key: String,
        @Query("with_genres") with_genres : String ) : Call<MovieResponse>




}