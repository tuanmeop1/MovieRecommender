package com.example.movierecommender.repository

import com.example.movierecommender.data.model.HybridRequest
import com.example.movierecommender.data.model.MovieDetails
import com.example.movierecommender.data.model.RatingRequest
import com.example.movierecommender.data.model.RatingResponse
import com.example.movierecommender.data.model.RecommendationResponse
import com.example.movierecommender.data.model.SearchRequest
import com.example.movierecommender.data.model.SearchResponse
import com.example.movierecommender.data.network.ApiClient
import com.example.movierecommender.data.network.ApiService

class MovieRepository {
    private val serverApiService = ApiClient.serverApiService

    suspend fun searchMovies(query: String): SearchResponse? {
        return try {
            val response = serverApiService.searchMovies(SearchRequest(query))
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetails? {
        return try {
            val response = serverApiService.getMovieDetails(movieId)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun rateMovie(request: RatingRequest): RatingResponse? {
        return try {
            val response = serverApiService.rateMovie(request)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRecommendations(request: HybridRequest): RecommendationResponse? {
        return try {
            val response = serverApiService.getRecommendations(request)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}