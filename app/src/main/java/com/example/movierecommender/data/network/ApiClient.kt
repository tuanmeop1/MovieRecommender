package com.example.movierecommender.data.network

import com.example.movierecommender.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val serverRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

//    val tmdbRetrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.TMDB_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
}

object ApiClient {
    val serverApiService: ApiService by lazy {
        RetrofitClient.serverRetrofit.create(ApiService::class.java)
    }

//    val tmdbApiService: TmdbApiService by lazy {
//        RetrofitClient.tmdbRetrofit.create(TmdbApiService::class.java)
//    }
}