package com.example.triviaapp.game.api

import com.example.triviaapp.game.model.Questions
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface GameApi {

    @GET("crossfit_questions.json")
    suspend fun getAllQuestions(): Questions
}
