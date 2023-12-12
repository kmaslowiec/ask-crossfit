package com.example.triviaapp.game.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaapp.common.domain.ShuffleEngine
import com.example.triviaapp.common.repository.StatsRepository
import com.example.triviaapp.game.model.QuestionsState
import com.example.triviaapp.game.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository,
    private val statsRepository: StatsRepository,
    private val twister: ShuffleEngine
) : ViewModel() {

    private val _questionsState = MutableStateFlow<QuestionsState>(QuestionsState.Loading)
    val questionsState = _questionsState.asStateFlow()

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
            repository.getQuestions().onSuccess {
                _questionsState.value = QuestionsState.Success(it)
            }.onFailure { _questionsState.value = QuestionsState.Error }
        }
    }
}
