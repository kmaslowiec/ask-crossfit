package com.example.triviaapp.game.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaapp.game.repository.DataOrException
import com.example.triviaapp.common.domain.ShuffleEngine
import com.example.triviaapp.game.model.Questions
import com.example.triviaapp.game.repository.GameRepository
import com.example.triviaapp.common.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository,
    private val statsRepository: StatsRepository,
    private val twister: ShuffleEngine
) : ViewModel() {

    val data: MutableState<DataOrException<Questions, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    private val _currentQuestionIndex = mutableIntStateOf(generateQuestionNumber())
    var currentQuestionIndex: State<Int> = _currentQuestionIndex

    private val _currentQuestionNumber = mutableIntStateOf(1)
    var currentQuestionNumber: State<Int> = _currentQuestionNumber

    private val _selectedAnswer = mutableStateOf("")
    var selectedAnswer: State<String> = _selectedAnswer

    init {
        getAllQuestions()
    }

    fun updateSelectedAnswer(newAnswer: String) {
        _selectedAnswer.value = newAnswer
    }

    fun updateCurrentQuestionIndex() {
        currentQuestionIndex = mutableIntStateOf(generateQuestionNumber())
    }

    fun generateQuestionNumber(): Int {
        return twister.getRandomNumber(100)
    }

    fun increaseQuestionNumber() {
        _currentQuestionNumber.intValue++
    }


    fun addWrongAnswerToStats() {
        viewModelScope.launch {
            statsRepository.incrementWrongAnswersNumber()
        }
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getQuestions()
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }
}
