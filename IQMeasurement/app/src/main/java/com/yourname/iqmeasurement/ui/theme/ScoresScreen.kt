package com.yourname.iqmeasurement.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

// Sample data class for storing score information
data class ScoreEntry(val score: Int, val totalQuestions: Int, val date: String)

@Composable
fun ScoresScreen(scores: List<ScoreEntry>, onBackToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onBackToHome, modifier = Modifier.align(Alignment.Start)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text(
            text = "Your Scores",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(scores) { scoreEntry ->
                val backgroundColor = when {
                    scoreEntry.score >= 8 -> Color(0xFFD1E7DD) // Green for high scores
                    scoreEntry.score >= 5 -> Color(0xFFFFF3CD) // Yellow for medium scores
                    else -> Color(0xFFF8D7DA)                        // Low scores
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Score: ${scoreEntry.score} / ${scoreEntry.totalQuestions}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Date: ${scoreEntry.date}",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoresScreenPreview() {
    IQMeasurementAppTheme {
        ScoresScreen(
            scores = listOf(
                ScoreEntry(8, 10, "March 1, 2025"),
                ScoreEntry(6, 9, "March 5, 2025"),
                ScoreEntry(4, 8, "March 10, 2025")
            ),
            onBackToHome = {}
        )
    }
}
