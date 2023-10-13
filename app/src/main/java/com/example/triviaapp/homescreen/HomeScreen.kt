package com.example.triviaapp.homescreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.triviaapp.R
import com.example.triviaapp.model.QuestionsItem
import com.example.triviaapp.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: QuestionViewModel) {

    var currentQuestionIndex by rememberSaveable {
        mutableIntStateOf(viewModel.generateQuestionNumber())
    }
    var currentQuestionNumber by rememberSaveable {
        mutableIntStateOf(1)
    }
    var selectedAnswer by rememberSaveable {
        mutableStateOf("")
    }

    val currentQuestion = viewModel.data.value.data?.get(currentQuestionIndex) ?: QuestionsItem(
        answer = "empty answer",
        category = "empty category",
        choices = emptyList(),
        question = ""
    )
    val numberOfQuestions = viewModel.data.value.data?.size ?: 0

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
        Log.i("CrossFitQA", "Question size ${viewModel.data.value.data?.size}")
        HomeScreenContent(
            question = currentQuestion,
            currentQuestion = currentQuestionNumber,
            numberOfQuestions = numberOfQuestions,
            onClickButton = {
                currentQuestionIndex = viewModel.generateQuestionNumber()
                if (currentQuestionNumber < numberOfQuestions) currentQuestionNumber++
                selectedAnswer = ""
            },
            updateSelectedAnswer = { selectedAnswer = it },
            selectedAnswer = selectedAnswer,
        )
    }
}

@Composable
fun HomeScreenContent(
    question: QuestionsItem,
    currentQuestion: Int,
    numberOfQuestions: Int,
    onClickButton: () -> Unit,
    updateSelectedAnswer: (String) -> Unit,
    selectedAnswer: String,
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)
    val correctAnswerIndex = question.choices.indexOf(question.answer)

    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            QuestionTracker(questionNumber = currentQuestion, allQuestionNumber = numberOfQuestions)

        }
        SeperationLine(pathEffect = pathEffect)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = question.question,
                style = Typography.titleLarge,
                color = Color.White
            )

            AnswerRadioButtons(
                answers = question.choices,
                correctAnswerIndex = correctAnswerIndex,
                answer = question.answer,
                updateSelectedAnswer = updateSelectedAnswer,
                selectedAnswer = selectedAnswer,
            )

            Button(
                onClick = onClickButton,
            ) {
                Text(
                    text = stringResource(R.string.buttonNextQuestions),
                    style = Typography.titleSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        question = QuestionsItem(
            answer = "hello",
            category = "", listOf("one", "two"),
            question = "Please answer the question"
        ),
        numberOfQuestions = 0,
        currentQuestion = 0,
        onClickButton = {},
        updateSelectedAnswer = {},
        selectedAnswer = ""
    )
}
