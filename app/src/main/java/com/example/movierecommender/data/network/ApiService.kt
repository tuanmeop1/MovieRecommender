package com.example.movierecommender.data.network

import com.example.movierecommender.data.model.HybridRequest
import com.example.movierecommender.data.model.MovieDetails
import com.example.movierecommender.data.model.RatingRequest
import com.example.movierecommender.data.model.RatingResponse
import com.example.movierecommender.data.model.RecommendationResponse
import com.example.movierecommender.data.model.SearchRequest
import com.example.movierecommender.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("search/")
    suspend fun searchMovies(@retrofit2.http.Body request: SearchRequest): Response<SearchResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@retrofit2.http.Path("movie_id") movieId: Int): Response<MovieDetails>

    @POST("rate/")
    suspend fun rateMovie(@retrofit2.http.Body request: RatingRequest): Response<RatingResponse>

    @POST("recommend/")
    suspend fun getRecommendations(@retrofit2.http.Body request: HybridRequest): Response<RecommendationResponse>
}