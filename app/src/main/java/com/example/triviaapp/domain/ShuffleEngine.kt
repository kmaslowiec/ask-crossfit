package com.example.triviaapp.domain

interface ShuffleEngine {

    fun getRandomNumber(sumOfQuestions: Int): Int
}
