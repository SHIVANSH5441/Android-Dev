package com.yourname.iqmeasurement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yourname.iqmeasurement.ui.theme.HomeScreen
import com.yourname.iqmeasurement.ui.theme.IQMeasurementAppTheme
import com.yourname.iqmeasurement.ui.theme.InstructionsScreen
import com.yourname.iqmeasurement.ui.theme.QuizScreen
import com.yourname.iqmeasurement.ui.theme.ResultScreen
import com.yourname.iqmeasurement.ui.theme.QuestionData
import com.yourname.iqmeasurement.ui.theme.ScoresScreen
import com.yourname.iqmeasurement.ui.theme.ScoreRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IQMeasurementAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(
                onStartQuiz = { navController.navigate("quiz") },
                onViewScores = { navController.navigate("scores") },
                onViewInstructions = { navController.navigate("instructions") }
            )
        }

        composable(route = "scores") {
            val scores = ScoreRepository.getScores()
            ScoresScreen(
                scores = scores,
                onBackToHome = { navController.popBackStack() }
            )
        }

        composable(route = "instructions") {
            InstructionsScreen(
                onStartQuiz = { navController.navigate("quiz") },
                onBackToHome = { navController.popBackStack() }
            )
        }

        composable(route = "quiz") {
            QuizScreen(navController = navController)
        }

        composable(route = "result/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            var hasNavigated by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                hasNavigated = false  // Reset flag when entering ResultScreen
            }

            ResultScreen(
                score = score,
                totalQuestions = QuestionData.questions.size,
                onRestart = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.popBackStack(route = "quiz", inclusive = true)
                        navController.navigate("home")
                    }
                },
                onBackToHome = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.navigate("home")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    IQMeasurementAppTheme {
        AppNavigation()
    }
}
