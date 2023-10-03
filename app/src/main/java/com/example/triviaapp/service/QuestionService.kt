package com.example.triviaapp.service

import com.example.triviaapp.model.QuestionsItem
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionService {

    @GET("crossfit_questions.json")
    suspend fun getAllQuestions(): ArrayList<QuestionsItem>
}