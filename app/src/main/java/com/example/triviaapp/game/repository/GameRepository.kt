package com.example.triviaapp.game.repository

import com.example.triviaapp.game.api.GameApi
import com.example.triviaapp.game.model.Questions
import javax.inject.Inject

class GameRepository @Inject constructor(private val api: GameApi) {

    suspend fun getQuestions(): Result<Questions> = runCatching {
        api.getAllQuestions()
    }
}
