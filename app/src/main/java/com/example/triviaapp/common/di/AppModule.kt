package com.example.triviaapp.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.triviaapp.common.constants.DataStoreConstants
import com.example.triviaapp.common.constants.NetworkConstants
import com.example.triviaapp.game.domain.MersenneTwister
import com.example.triviaapp.common.domain.ShuffleEngine
import com.example.triviaapp.game.api.GameApi
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
    fun provideQuestionApi(): GameApi = Retrofit.Builder()
        .baseUrl(NetworkConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GameApi::class.java)

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> = appContext.dataStore
}
