package com.example.triviaapp.game.model

sealed class QuestionsState {
    object Loading : QuestionsState()
    data class Success(val questions: Questions) : QuestionsState()
    object Error : QuestionsState()
}
