package com.example.triviaapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.triviaapp.common.DataStoreConstants
import com.example.triviaapp.common.NetworkConstants
import com.example.triviaapp.domain.MersenneTwister
import com.example.triviaapp.domain.ShuffleEngine
import com.example.triviaapp.service.QuestionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreConstants.DATA_STORE_PREFERENCES_NAME_FOR_STATS)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTwister(): ShuffleEngine = MersenneTwister()

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionService = Retrofit.Builder()
        .baseUrl(NetworkConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuestionService::class.java)

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> = appContext.dataStore
}
