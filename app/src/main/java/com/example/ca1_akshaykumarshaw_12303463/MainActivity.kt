package com.example.ca1_akshaykumarshaw_12303463

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ca1_akshaykumarshaw_12303463.ui.theme.CA1_AkshayKumarShaw_12303463Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA1_AkshayKumarShaw_12303463Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FitnessTrackerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessTrackerScreen(modifier: Modifier = Modifier) {
    // 1. Separate variables for each metric (Units kept in data but ignored in UI)
    val stepsData = Triple("Steps", "8,542", "steps")
    val caloriesData = Triple("Calories", "420", "kcal")
    val distanceData = Triple("Distance", "6.2", "km")

    // 2. Add variables into a list
    val fitnessData = listOf(stepsData, caloriesData, distanceData)

    // 3. State Management
    val options = fitnessData.map { it.first }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fitness Tracker",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        // 4. Exposed Dropdown Menu Box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            TextField(
                value = "Selected: $selectedOption",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                fitnessData.forEach { (label, _, _) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedOption = label
                            expanded = false
                        }
                    )
                }
            }
        }

        // 5. Data Boxes
        fitnessData.forEach { (label, value, _) -> // We ignore the 'unit' part of the Triple here
            val isSelected = selectedOption == label

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 16.dp)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                    Text(
                        // Removed $unit - showing only the numeric value
                        text = value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}