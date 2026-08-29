package com.example.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMICalculatorScreen() {
    var weight by remember { mutableStateOf("") }
    var heightMeters by remember { mutableStateOf("") }
    var heightFeet by remember { mutableStateOf("") }
    var heightInches by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Male") }
    var selectedUnit by remember { mutableStateOf("Metric (kg, m)") }
    var bmiResult by remember { mutableStateOf<Double?>(null) }
    var healthTip by remember { mutableStateOf("") }

    var expandedUnit by remember { mutableStateOf(false) }
    var expandedGender by remember { mutableStateOf(false) }

    val unitOptions = listOf("Metric (kg, m)", "Imperial (lbs, ft & in)")
    val genderOptions = listOf("Male", "Female", "Other")

    // Reset height values when switching units
    LaunchedEffect(selectedUnit) {
        heightMeters = ""
        heightFeet = ""
        heightInches = ""
    }

    // Calculate BMI in real-time
    LaunchedEffect(weight, heightMeters, heightFeet, heightInches, selectedUnit) {
        val weightValue = weight.toDoubleOrNull()

        bmiResult = if (selectedUnit == "Imperial (lbs, ft & in)") {
            val feetValue = heightFeet.toDoubleOrNull() ?: 0.0
            val inchesValue = heightInches.toDoubleOrNull() ?: 0.0
            val totalInches = (feetValue * 12) + inchesValue

            if (weightValue != null && totalInches > 0) {
                703 * (weightValue / (totalInches * totalInches))
            } else null
        } else {
            val heightValue = heightMeters.toDoubleOrNull()
            if (weightValue != null && heightValue != null && heightValue > 0) {
                weightValue / (heightValue * heightValue)
            } else null
        }

        // Set Health Tips
        healthTip = when {
            bmiResult == null -> ""
            bmiResult!! < 18.5 -> "Eat a balanced diet and exercise to gain weight."
            bmiResult!! in 18.5..24.9 -> "Maintain your healthy weight with a balanced diet and regular exercise."
            bmiResult!! in 25.0..29.9 -> "Exercise regularly and watch your diet to manage weight."
            else -> "Consult a doctor for a weight management plan."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("BMI Calculator", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // **Unit Selection Dropdown**
        ExposedDropdownMenuBox(
            expanded = expandedUnit,
            onExpandedChange = { expandedUnit = it }
        ) {
            OutlinedTextField(
                value = selectedUnit,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedUnit,
                onDismissRequest = { expandedUnit = false }
            ) {
                unitOptions.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            selectedUnit = unit
                            expandedUnit = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Weight Input**
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (${if (selectedUnit == "Metric (kg, m)") "kg" else "lbs"})") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Height Input (Dynamic)**
        if (selectedUnit == "Imperial (lbs, ft & in)") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = heightFeet,
                    onValueChange = { heightFeet = it },
                    label = { Text("Feet") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = heightInches,
                    onValueChange = { heightInches = it },
                    label = { Text("Inches") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            OutlinedTextField(
                value = heightMeters,
                onValueChange = { heightMeters = it },
                label = { Text("Height (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Age Input**
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Gender Selection Dropdown**
        ExposedDropdownMenuBox(
            expanded = expandedGender,
            onExpandedChange = { expandedGender = it }
        ) {
            OutlinedTextField(
                value = selectedGender,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedGender,
                onDismissRequest = { expandedGender = false }
            ) {
                genderOptions.forEach { gender ->
                    DropdownMenuItem(
                        text = { Text(gender) },
                        onClick = {
                            selectedGender = gender
                            expandedGender = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **Display BMI Result with Animation**
        AnimatedVisibility(
            visible = bmiResult != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            bmiResult?.let { bmi ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    val category = when {
                        bmi < 18.5 -> "Underweight"
                        bmi in 18.5..24.9 -> "Normal weight"
                        bmi in 25.0..29.9 -> "Overweight"
                        else -> "Obese"
                    }
                    Text(
                        text = "BMI: %.2f ($category)".format(bmi),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))  // Space between BMI and health tip
                    Text(
                        text = healthTip,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
    }
