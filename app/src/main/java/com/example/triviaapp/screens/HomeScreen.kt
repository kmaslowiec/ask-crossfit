package com.example.triviaapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.triviaapp.screens.viewmodel.QuestionViewModel
import com.example.triviaapp.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {
        Log.d("Bughunt", "... is loading")
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Log.d("BUGHUNT", "Question size ${questions?.size}")
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "Question 1/100", style = Typography.labelLarge)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .background(Color.DarkGray),
        ) {

        }
    }

}
