package com.example.triviaapp.common.domain

interface ShuffleEngine {

    fun getRandomNumber(sumOfQuestions: Int): Int
}
