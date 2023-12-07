package com.example.triviaapp.game.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.triviaapp.ui.theme.Typography

@Composable
fun AnswerRadioButtons(
    answers: List<String>,
    correctAnswerIndex: Int,
    answer: String,
    updateSelectedAnswer: (String) -> Unit,
    saveNumOfWrongAnswers: () -> Unit,
    selectedAnswer: String
) {
    answers.forEachIndexed { index, text ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .selectable(
                    selected = (text == selectedAnswer),
                    onClick = {
                        updateSelectedAnswer(text)
                        saveNumOfWrongAnswers()
                    }
                )
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 5.dp,
                    shape = RoundedCornerShape(12.dp),
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.error)
                    )
                )
                .background(
                    color = if (selectedAnswer == answer && correctAnswerIndex == index) {
                        MaterialTheme.colorScheme.background
                    } else {
                        Color.Transparent
                    }
                )
        ) {
            RadioButton(
                selected = (text == selectedAnswer),
                onClick = {
                    updateSelectedAnswer(text)
                    saveNumOfWrongAnswers()
                },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)

            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = text,
                style = Typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerRadioButtonsPreview() {
    AnswerRadioButtons(
        answers = listOf("Answer one"),
        correctAnswerIndex = 1,
        answer = "Answer one",
        updateSelectedAnswer = {},
        selectedAnswer = "",
        saveNumOfWrongAnswers = {}
    )
}
