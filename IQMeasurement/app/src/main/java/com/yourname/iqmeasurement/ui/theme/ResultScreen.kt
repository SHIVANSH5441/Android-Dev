package com.yourname.iqmeasurement.ui.theme

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ResultScreen(
    score: Int,
    totalQuestions: Int,
    onRestart: () -> Unit,
    onBackToHome: () -> Unit
) {
    val stableScore = remember { score }  // ✅ Stabilized score
    val animatedScore by animateIntAsState( 
        targetValue = stableScore,
        animationSpec = tween(durationMillis = 1500),
        label = "Score Animation"
    )

    var scoreSaved by remember { mutableStateOf(false) }

    val message = when {
        score == totalQuestions -> "🎯 Perfect Score! You're a true genius!"
        score >= 8 -> "🌟 Excellent work! You're incredibly sharp!"
        score >= 5 -> "💪 Good job! Keep pushing yourself to improve!"
        score >= 3 -> "📈 Not bad! With a little more practice, you'll excel!"
        else -> "💡 Don't give up! Every attempt makes you smarter."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your Score",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$animatedScore / $totalQuestions",
            color = Color.Red,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = message,
            color = Color.Yellow,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = onRestart,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Restart Quiz")
        }

        Button(
            onClick = {
                if (!scoreSaved) {
                    val currentDate = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())
                    ScoreRepository.addScore(score, totalQuestions, currentDate)
                    scoreSaved = true
                }
                onBackToHome()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Back to Home Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        score = 7,
        totalQuestions = 10,
        onRestart = {},
        onBackToHome = {}
    )
}