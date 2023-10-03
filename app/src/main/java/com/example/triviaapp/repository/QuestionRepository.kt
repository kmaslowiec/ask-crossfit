package com.example.triviaapp.repository

import android.util.Log
import com.example.triviaapp.data.DataOrException
import com.example.triviaapp.model.QuestionsItem
import com.example.triviaapp.service.QuestionService
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val service: QuestionService) {

    private val dataOrException = DataOrException<ArrayList<QuestionsItem>, Boolean, Exception>()

    suspend fun getQuestions(): DataOrException<ArrayList<QuestionsItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = service.getAllQuestions()
            Log.d("Bughunt", "SUCCESS: $dataOrException.data")
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.exception = exception
            Log.d("Bughunt", "Failure: $dataOrException.data")

        }
        return dataOrException
    }
}