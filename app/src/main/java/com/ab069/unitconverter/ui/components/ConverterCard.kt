package com.ab069.unitconverter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterCard(
    units: List<String>,
    onConvert: (value: Double, fromIndex: Int, toIndex: Int) -> String
) {
    var inputText by remember { mutableStateOf("") }
    var fromIndex by remember { mutableIntStateOf(0) }
    var toIndex by remember { mutableIntStateOf(1) }
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    val result = remember(inputText, fromIndex, toIndex) {
        val value = inputText.toDoubleOrNull()
        if (value != null) onConvert(value, fromIndex, toIndex) else ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = fromExpanded,
                    onExpandedChange = { fromExpanded = it }
                ) {
                    OutlinedTextField(
                        value = units[fromIndex],
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("From") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(fromExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = fromExpanded,
                        onDismissRequest = { fromExpanded = false }
                    ) {
                        units.forEachIndexed { i, unit ->
                            DropdownMenuItem(
                                text = { Text(unit) },
                                onClick = { fromIndex = i; fromExpanded = false }
                            )
                        }
                    }
                }
            }

            IconButton(onClick = { val tmp = fromIndex; fromIndex = toIndex; toIndex = tmp }) {
                Icon(Icons.Default.SwapVert, contentDescription = "Swap")
            }

            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = toExpanded,
                    onExpandedChange = { toExpanded = it }
                ) {
                    OutlinedTextField(
                        value = units[toIndex],
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("To") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(toExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = toExpanded,
                        onDismissRequest = { toExpanded = false }
                    ) {
                        units.forEachIndexed { i, unit ->
                            DropdownMenuItem(
                                text = { Text(unit) },
                                onClick = { toIndex = i; toExpanded = false }
                            )
                        }
                    }
                }
            }
        }

        if (result.isNotEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Result", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$result ${units[toIndex]}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}
