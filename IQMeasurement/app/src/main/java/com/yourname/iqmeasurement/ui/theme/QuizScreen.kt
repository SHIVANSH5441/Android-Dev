package com.yourname.iqmeasurement.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(navController: NavController) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val selectedAnswer = remember { mutableStateOf<String?>(null) }
    val score = remember { mutableIntStateOf(0) }
    var showResultScreen by remember { mutableStateOf(false) }

    // Timer-related states
    var timeLeft by remember { mutableIntStateOf(20) }

    // This will reset the timer whenever the question index changes
    LaunchedEffect(currentQuestionIndex) {
        timeLeft = 20 // Reset timer for each new question
    }

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000L) // Delay for 1 second
            timeLeft-- // Decrement the timer every second
        } else if (timeLeft == 0) {
            if (currentQuestionIndex < QuestionData.questions.size - 1) {
                currentQuestionIndex++ // Move to the next question
                selectedAnswer.value = null // Reset selected answer
            } else {
                showResultScreen = true // Show result screen after last question
            }
        }
    }

    if (showResultScreen) {
        ResultScreen(
            score = score.intValue,
            totalQuestions = QuestionData.questions.size,
            onRestart = {
                currentQuestionIndex = 0
                score.intValue = 0
                selectedAnswer.value = null
                showResultScreen = false
            },
            onBackToHome = {
                navController.navigate("home")
            }
        )
    } else {
        val question = QuestionData.questions[currentQuestionIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Question ${currentQuestionIndex + 1} / ${QuestionData.questions.size}",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 5f,
                        offset = Offset(2f, 2f)
                    )
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display Countdown Timer
            Text(
                text = "Time Left: $timeLeft seconds",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (timeLeft <= 10) Color.Red else Color.Green, // Highlight low time
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Progress Bar for countdown
            LinearProgressIndicator(
                progress = (timeLeft.toFloat() / 15), // Updated to 15 seconds
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                color = Color.Green
            )

            Text(
                text = question.text,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )

            question.options.forEach { option ->
                AnswerOption(
                    text = option,
                    isSelected = selectedAnswer.value == option,
                    onClick = { selectedAnswer.value = option }
                )
            }

            Button(
                onClick = {
                    if (selectedAnswer.value == question.correctAnswer) {
                        score.intValue += 1 // Only add 1 point for a correct answer
                    }

                    if (currentQuestionIndex < QuestionData.questions.size - 1) {
                        currentQuestionIndex++
                        selectedAnswer.value = null
                    } else {
                        showResultScreen = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun AnswerOption(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                if (isSelected) Color(0xFF4CAF50) else Color(0xFFF1F1F1),
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val mockNavController = rememberNavController()
    QuizScreen(navController = mockNavController)
}