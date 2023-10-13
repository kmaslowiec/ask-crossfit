package com.example.triviaapp.di

import com.example.triviaapp.common.MersenneTwister
import com.example.triviaapp.common.NetworkConstants
import com.example.triviaapp.service.QuestionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTwister(): MersenneTwister = MersenneTwister()

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionService = Retrofit.Builder()
        .baseUrl(NetworkConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuestionService::class.java)
}
