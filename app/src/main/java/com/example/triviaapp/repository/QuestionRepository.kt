package com.example.triviaapp.repository

import com.example.triviaapp.data.DataOrException
import com.example.triviaapp.model.Questions
import com.example.triviaapp.service.QuestionService
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val service: QuestionService) {

    private val dataOrException = DataOrException<Questions, Boolean, Exception>()

    suspend fun getQuestions(): DataOrException<Questions, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = service.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.exception = exception
        }
        return dataOrException
    }
}