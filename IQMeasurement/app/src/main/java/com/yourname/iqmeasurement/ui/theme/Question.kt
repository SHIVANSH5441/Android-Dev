package com.yourname.iqmeasurement.ui.theme

data class Question(
    val text: String,                // Question text
    val options: List<String>,       // List of answer options
    val correctAnswer: String        // Correct answer
)
