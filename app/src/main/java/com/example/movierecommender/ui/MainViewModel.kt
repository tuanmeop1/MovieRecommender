package com.example.movierecommender.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierecommender.data.model.HybridRequest
import com.example.movierecommender.data.model.MovieDetails
import com.example.movierecommender.data.model.MovieResult
import com.example.movierecommender.data.model.RatingRequest
import com.example.movierecommender.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    private val repository = MovieRepository()

    val searchResults = MutableLiveData<List<MovieResult>>()
    val movieDetails = MutableLiveData<MovieDetails?>()
    val recommendations = MutableLiveData<List<String>>()
    val ratingStatus = MutableLiveData<String>()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            val response = repository.searchMovies(query)
            searchResults.postValue(response?.results ?: emptyList())
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val details = repository.getMovieDetails(movieId)
            movieDetails.postValue(details)
        }
    }

    fun rateMovie(userId: Int, movieId: Int, rating: Float) {
        viewModelScope.launch {
            val request = RatingRequest(userId, movieId, rating)
            val response = repository.rateMovie(request)
            ratingStatus.postValue(response?.message ?: "Error")
        }
    }

    fun getRecommendations(userId: Int, title: String) {
        viewModelScope.launch {
            val request = HybridRequest(userId, title)
            val response = repository.getRecommendations(request)
            recommendations.postValue(response?.recommendations ?: emptyList())
        }
    }
}