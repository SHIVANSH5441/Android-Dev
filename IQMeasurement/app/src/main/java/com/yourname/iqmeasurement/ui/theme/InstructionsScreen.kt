package com.yourname.iqmeasurement.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun InstructionsScreen(onStartQuiz: () -> Unit, onBackToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Back Arrow Button
        IconButton(onClick = onBackToHome) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        // Title
        Text(
            text = "📘 Instructions",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Instruction Steps
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text("✔️ Read each question carefully.", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("✔️ Select the most appropriate answer.", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("✔️ There’s a time limit of 20 seconds per Question, so think wisely.", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("✔️ Try your best to score high!", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onStartQuiz,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstructionsScreenPreview() {
    InstructionsScreen(onStartQuiz = {}, onBackToHome = {})
}
