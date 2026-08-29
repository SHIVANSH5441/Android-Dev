package com.yourname.iqmeasurement.ui.theme

object ScoreRepository {
    private val scoreList = mutableListOf<ScoreEntry>()

    // Function to add a new score
    fun addScore(score: Int, totalQuestions: Int, date: String) {
        scoreList.add(ScoreEntry(score, totalQuestions, date))
    }

    // Function to retrieve scores
    fun getScores(): List<ScoreEntry> = scoreList
}