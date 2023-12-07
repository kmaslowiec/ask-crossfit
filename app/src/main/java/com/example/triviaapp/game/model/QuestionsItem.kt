package com.example.triviaapp.game.model

data class QuestionsItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)