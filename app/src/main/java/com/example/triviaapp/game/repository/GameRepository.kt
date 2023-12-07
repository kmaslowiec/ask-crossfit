package com.example.triviaapp.game.repository

import com.example.triviaapp.game.model.Questions
import com.example.triviaapp.game.api.GameApi
import javax.inject.Inject

class GameRepository @Inject constructor(private val api: GameApi) {

    private val dataOrException = DataOrException<Questions, Boolean, Exception>()

    suspend fun getQuestions(): DataOrException<Questions, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.exception = exception
        }
        return dataOrException
    }
}
