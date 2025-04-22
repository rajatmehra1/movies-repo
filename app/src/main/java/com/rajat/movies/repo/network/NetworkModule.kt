package com.rajat.movies.repo.network

import com.rajat.movies.repo.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/"

    private var retrofit: Retrofit? = null

    private val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder().addHeader(
                    "Authorization", "Bearer ${BuildConfig.TMDB_BEARER_TOKEN}"
                ).build()
                chain.proceed(newRequest)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    val apiService: MoviesApiService by lazy {
        client.create(MoviesApiService::class.java)
    }
}