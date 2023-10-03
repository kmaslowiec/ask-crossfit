package com.example.triviaapp.screens.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaapp.model.Questions
import com.example.triviaapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) : ViewModel() {

    private val _questions = MutableLiveData<Questions>()
    val question = _questions

    fun fetchQuestions() {
        viewModelScope.launch {
            question.value = repository.getQuestions().data
        }
    }
}