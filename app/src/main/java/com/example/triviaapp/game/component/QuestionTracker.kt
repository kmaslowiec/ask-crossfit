package com.example.triviaapp.game.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.triviaapp.ui.theme.Typography

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

@Preview
@Composable
fun QuestionTrackerPreview() {
    QuestionTracker(questionNumber = 1, allQuestionNumber = 100)
}
