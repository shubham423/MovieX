package com.shubham.moviesdb.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shubham.moviesdb.MoviesApplication
import com.shubham.moviesdb.local.database.AppDatabase
import com.shubham.moviesdb.local.database.MovieDao
import com.shubham.moviesdb.remote.MoviesApi
import com.shubham.moviesdb.utils.Constants.TMDB_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideHttpClientBuilder(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(MoviesApplication.applicationContext()))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.newBuilder().build())
            .build()


    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_db"
        ).build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao = database.movieDao()

}