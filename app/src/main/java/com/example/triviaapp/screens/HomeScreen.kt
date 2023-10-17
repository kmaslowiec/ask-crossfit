package com.example.triviaapp.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.triviaapp.model.QuestionsItem
import com.example.triviaapp.screens.viewmodel.QuestionViewModel
import com.example.triviaapp.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toList()
    var questionNumber by rememberSaveable {
        mutableIntStateOf(viewModel.generateQuestionNumber())
    }

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
        HomeScreenContent(questions, questionNumber + 1)
    }
}

@Composable
fun HomeScreenContent(questions: List<QuestionsItem>?, randomNum: Int) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)

    var selectedState by rememberSaveable {
        mutableStateOf("")
    }
    val answerChoices = questions?.get(randomNum)?.choices

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
        DrawDotterLine(pathEffect = pathEffect)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = questions?.get(randomNum)?.question ?: "no question",
                style = Typography.titleLarge,
                color = Color.White
            )

            answerChoices?.forEach { text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .selectable(
                            selected = (text == selectedState),
                            onClick = { selectedState = text }
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 5.dp,
                            shape = RoundedCornerShape(12.dp),
                            brush = Brush.linearGradient(
                                colors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.error)
                            )
                        ),
                ) {
                    RadioButton(
                        selected = (text == selectedState),
                        onClick = { selectedState = text },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)

                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterVertically),
                        text = text,
                        style = Typography.bodySmall
                    )
                }
            }
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
            withStyle(style = Typography.labelMedium.toSpanStyle().copy(color = Color.Gray)) {
                append("$allQuestionNumber")
            }
        }
    })
}

@Composable
fun DrawDotterLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)

    ) {
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(questions = emptyList(), randomNum = 0)
}
