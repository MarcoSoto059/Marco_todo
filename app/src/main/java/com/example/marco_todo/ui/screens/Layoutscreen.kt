package com.example.marco_todo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun LayoutScreen(onGoHomeScreen: () -> Unit) {
    BaseScreen(
        title = "Diseño y Layouts",
        onBack = onGoHomeScreen
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            LayoutExampleSection(title = "1. Row (Fila Horizontal)") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LayoutBox(Color(0xFFE57373))
                    LayoutBox(Color(0xFF81C784))
                    LayoutBox(Color(0xFF64B5F6))
                }
            }

            LayoutExampleSection(title = "2. Column (Columna Vertical)") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Componente superior", style = MaterialTheme.typography.bodyLarge)
                    Text("Componente central", color = MaterialTheme.colorScheme.secondary)
                    Text("Componente inferior", color = MaterialTheme.colorScheme.tertiary)
                }
            }

            LayoutExampleSection(title = "3. Box (Superposición)") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.shapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Fondo del Box",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            "Superpuesto",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LayoutExampleSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title, 
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
fun LayoutBox(color: Color) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color, MaterialTheme.shapes.small)
    )
}
