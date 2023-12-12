package com.example.triviaapp.game.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.triviaapp.R
import com.example.triviaapp.game.component.AnswerRadioButtons
import com.example.triviaapp.game.component.QuestionTracker
import com.example.triviaapp.game.component.SeperationLine
import com.example.triviaapp.game.model.QuestionsItem
import com.example.triviaapp.game.model.QuestionsState.Error
import com.example.triviaapp.game.model.QuestionsState.Loading
import com.example.triviaapp.game.model.QuestionsState.Success
import com.example.triviaapp.game.viewmodel.GameViewModel
import com.example.triviaapp.ui.theme.Typography

@Composable
fun GameScreen(viewModel: GameViewModel) {
    when (val state = viewModel.questionsState.collectAsState().value) {
        is Loading -> {
            Log.i("AskGpt", "... is loading")
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val currentQuestion = state.questions[viewModel.currentQuestionIndex.value]
            val amountOfQuestions = state.questions.size
            Log.i("AskGpt", "Question size $amountOfQuestions")
            HomeScreenContent(
                question = currentQuestion,
                currentQuestion = viewModel.currentQuestionNumber.value,
                numberOfQuestions = amountOfQuestions,
                onClickButton = {
                    viewModel.updateCurrentQuestionIndex()
                    if (viewModel.currentQuestionNumber.value < amountOfQuestions) viewModel.increaseQuestionNumber()
                    viewModel.updateSelectedAnswer("")
                },
                updateSelectedAnswer = { viewModel.updateSelectedAnswer(it) },
                saveNumOfWrongAnswers = {
                    if (viewModel.selectedAnswer.value != currentQuestion.answer) viewModel.addWrongAnswerToStats()
                },
                selectedAnswer = viewModel.selectedAnswer.value,
            )
        }

        is Error -> {}
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
