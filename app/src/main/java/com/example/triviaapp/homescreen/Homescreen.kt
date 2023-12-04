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
fun HomeScreen(viewModel: HomescreenViewModel) {

    val currentQuestion = viewModel.data.value.data?.get(viewModel.currentQuestionIndex.value) ?: QuestionsItem(
        answer = "empty answer",
        category = "empty category",
        choices = emptyList(),
        question = ""
    )
    val numberOfQuestions = viewModel.data.value.data?.size ?: 0

    if (viewModel.data.value.loading == true) {
        Log.i("AskGpt", "... is loading")
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Log.i("AskGpt", "Question size ${viewModel.data.value.data?.size}")
        HomeScreenContent(
            question = currentQuestion,
            currentQuestion = viewModel.currentQuestionNumber.value,
            numberOfQuestions = numberOfQuestions,
            onClickButton = {
                viewModel.updateCurrentQuestionIndex()
                if (viewModel.currentQuestionNumber.value < numberOfQuestions) viewModel.increaseQuestionNumber()
                viewModel.updateSelectedAnswer("")
            },
            updateSelectedAnswer = { viewModel.updateSelectedAnswer(it) },
            saveNumOfWrongAnswers = {
                if (viewModel.selectedAnswer.value != currentQuestion.answer) viewModel.addWrongAnswerToStats()
            },
            selectedAnswer = viewModel.selectedAnswer.value,
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
    saveNumOfWrongAnswers: () -> Unit
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)

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
                correctAnswerIndex = question.choices.indexOf(question.answer),
                answer = question.answer,
                updateSelectedAnswer = updateSelectedAnswer,
                selectedAnswer = selectedAnswer,
                saveNumOfWrongAnswers = saveNumOfWrongAnswers
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
        selectedAnswer = "",
        saveNumOfWrongAnswers = {}
    )
}
