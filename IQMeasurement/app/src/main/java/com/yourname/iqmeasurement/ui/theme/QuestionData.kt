package com.yourname.iqmeasurement.ui.theme


object QuestionData {
    val questions = listOf(
        Question(
            text = "You enter a dark room with a matchstick. There’s a candle, a kerosene lamp, and a fireplace. What do you light first?",
            options = listOf("Candle", "Lamp", "Fireplace", "Matchstick"),
            correctAnswer = "Matchstick"
        ),
        Question(
            text = "Which shape has the most sides?",
            options = listOf("Square", "Quadrilateral", "Heptagon", "Hexagon"),
            correctAnswer = "Heptagon"
        ),
        Question(
            text = "If a plane crashes on the border of the USA and Canada, where do they bury the survivors?",
            options = listOf("USA", "Canada", "Both", "None"),
            correctAnswer = "None"
        ),
        Question(
            text = "If a bat and a ball cost \$1.10 in total, and the bat costs \$1 more than the ball, how much does the ball cost?",
            options = listOf("\$0.05", "\$0.10", "\$0.15", "\$0.20"),
            correctAnswer = "\$0.05"
        ),
        Question(
            text = "What comes next in the sequence: 1, 4, 9, 16, 25, ?",
            options = listOf("36", "40", "49", "50"),
            correctAnswer = "36"
        ),
        Question(
            text = "A man has 4 daughters, and each daughter has a brother. How many children does the man have?",
            options = listOf("4", "5", "6", "8"),
            correctAnswer = "5"
        ),
        Question(
            text = "Which word is the odd one out?",
            options = listOf("Apple", "Banana", "Carrot", "Grapes"),
            correctAnswer = "Carrot"
        ),
        Question(
            text = "What number should replace the question mark: 2, 6, 12, 20, 30, ?",
            options = listOf("38", "40", "42", "48"),
            correctAnswer = "42"
        ),
        Question(
            text = "I speak without a mouth and hear without ears. I have no body, but I come alive with wind. What am I?",
            options = listOf("Shadow", "Echo", "Fire", "Whisper"),
            correctAnswer = "Echo"
        )
    )
}