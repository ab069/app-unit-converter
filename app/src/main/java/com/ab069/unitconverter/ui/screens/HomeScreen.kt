package com.ab069.unitconverter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.ab069.unitconverter.R
import com.ab069.unitconverter.converter.Converter
import com.ab069.unitconverter.ui.components.ConverterCard

private data class Tab(val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab("Length", Icons.Default.Straighten),
    Tab("Weight", Icons.Default.FitnessCenter),
    Tab("Temp", Icons.Default.Thermostat),
    Tab("Volume", Icons.Default.WaterDrop)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { i, tab ->
                    NavigationBarItem(
                        selected = selectedTab == i,
                        onClick = { selectedTab = i },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> ConverterCard(
                    units = Converter.length.map { it.label },
                    onConvert = { v, from, to ->
                        Converter.format(Converter.convertStandard(v, Converter.length[from], Converter.length[to]))
                    }
                )
                1 -> ConverterCard(
                    units = Converter.weight.map { it.label },
                    onConvert = { v, from, to ->
                        Converter.format(Converter.convertStandard(v, Converter.weight[from], Converter.weight[to]))
                    }
                )
                2 -> ConverterCard(
                    units = listOf("Celsius", "Fahrenheit", "Kelvin"),
                    onConvert = { v, from, to ->
                        val units = listOf("Celsius", "Fahrenheit", "Kelvin")
                        Converter.format(Converter.convertTemperature(v, units[from], units[to]))
                    }
                )
                3 -> ConverterCard(
                    units = Converter.volume.map { it.label },
                    onConvert = { v, from, to ->
                        Converter.format(Converter.convertStandard(v, Converter.volume[from], Converter.volume[to]))
                    }
                )
            }
        }
    }
}
