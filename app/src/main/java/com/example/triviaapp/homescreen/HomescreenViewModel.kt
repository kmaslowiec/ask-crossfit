package com.example.triviaapp.homescreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaapp.data.DataOrException
import com.example.triviaapp.domain.ShuffleEngine
import com.example.triviaapp.model.Questions
import com.example.triviaapp.repository.QuestionRepository
import com.example.triviaapp.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomescreenViewModel @Inject constructor(
    private val repository: QuestionRepository,
    private val statsRepository: StatsRepository,
    private val twister: ShuffleEngine
) : ViewModel() {

    val data: MutableState<DataOrException<Questions, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
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

    fun generateQuestionNumber(): Int {
        return twister.getRandomNumber(100)
    }

    fun addWrongAnswerToStats() {
        viewModelScope.launch {
            statsRepository.incrementWrongAnswersNumber()
        }
    }
}
