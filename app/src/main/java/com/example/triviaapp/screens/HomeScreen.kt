package com.example.triviaapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.triviaapp.model.QuestionsItem
import com.example.triviaapp.screens.viewmodel.QuestionViewModel
import com.example.triviaapp.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toList()

    if (viewModel.data.value.loading == true) {
        Log.i("CrossFitQA", "... is loading")
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Log.i("CrossFitQA", "Question size ${questions?.size}")
        HomeScreenContent(questions, viewModel.generateQuestionNumber())
    }
}

@Composable
fun HomeScreenContent(questions: List<QuestionsItem>?, randomNum: Int) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            QuestionTracker(questionNumber = randomNum, allQuestionNumber = questions?.size ?: 0)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .background(MaterialTheme.colorScheme.primary),
        ) {

        }
    }
}

@Composable
fun QuestionTracker(questionNumber: Int, allQuestionNumber: Int) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(style = Typography.labelLarge.toSpanStyle().copy(color = Color.White)) {
                append("$questionNumber / ")
            }
            withStyle(style = Typography.labelLarge.toSpanStyle().copy(color = Color.Gray)) {
                append("$allQuestionNumber")
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(questions = emptyList(), randomNum = 0)
}
