package com.example.bioformulationmedicines

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.bioformulationmedicines.ui.theme.BioFormulationTheme

// Import Jetpack DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("user_prefs")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BioFormulationTheme {
                BioFormulationApp()
            }
        }
    }
}

@Composable
fun BioFormulationApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var solubility by remember { mutableStateOf("Select") }
    var stability by remember { mutableStateOf("Select") }
    var recommendedMaterial by remember { mutableStateOf("Select options") }

    // Load saved values from DataStore
    LaunchedEffect(Unit) {
        val savedValues = getUserInput(context)
        solubility = savedValues.first.ifEmpty { "Select" }
        stability = savedValues.second.ifEmpty { "Select" }
    }

    // Choose animation based on recommendation
    val animationFile = when (recommendedMaterial) {
        "Liposomes" -> "Animation 1.json"
        "Hydrogels" -> "Animation.json"
        "Polymeric Nanoparticles" -> "Animation 2.json"
        else -> "Medical Clipboard.json"
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(animationFile))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFB3E5FC), Color.White))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bio-Formulation Material Selector",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
            )

            // Solubility Dropdown
            Text("Select Solubility", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            DropdownSelector(
                options = listOf("High", "Low"),
                selectedOption = solubility,
                onSelectionChanged = { solubility = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Stability Dropdown
            Text("Select Stability", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            DropdownSelector(
                options = listOf("High", "Low"),
                selectedOption = stability,
                onSelectionChanged = { stability = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    recommendedMaterial = predictMaterial(solubility, stability)
                    coroutineScope.launch { saveUserInput(context, "Select", "Select") }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("Predict Material", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedVisibility(visible = recommendedMaterial != "Select options") {
                Text(
                    text = "Recommended: $recommendedMaterial",
                    fontSize = 18.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                )
            }
        }
    }
}

// Dropdown Selector Component
@Composable
fun DropdownSelector(
    options: List<String>,
    selectedOption: String,
    onSelectionChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Column {
            TextButton(onClick = { expanded = true }) {
                Text(selectedOption, fontSize = 16.sp)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelectionChanged(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// Decision Logic
fun predictMaterial(solubility: String, stability: String): String {
    return when {
        solubility.equals("High", ignoreCase = true) && stability.equals("High", ignoreCase = true) -> "Liposomes"
        solubility.equals("Low", ignoreCase = true) && stability.equals("High", ignoreCase = true) -> "Hydrogels"
        else -> "Polymeric Nanoparticles"
    }
}

// Save user input using Jetpack DataStore
suspend fun saveUserInput(context: Context, solubility: String, stability: String) {
    val dataStoreKeySolubility = stringPreferencesKey("solubility")
    val dataStoreKeyStability = stringPreferencesKey("stability")

    context.dataStore.edit { preferences ->
        preferences[dataStoreKeySolubility] = solubility
        preferences[dataStoreKeyStability] = stability
    }
}

// Get saved user input from DataStore
suspend fun getUserInput(context: Context): Pair<String, String> {
    val dataStoreKeySolubility = stringPreferencesKey("solubility")
    val dataStoreKeyStability = stringPreferencesKey("stability")

    val preferences = context.dataStore.data.first()
    val solubility = preferences[dataStoreKeySolubility] ?: "Select"
    val stability = preferences[dataStoreKeyStability] ?: "Select"

    return Pair(solubility, stability)
}