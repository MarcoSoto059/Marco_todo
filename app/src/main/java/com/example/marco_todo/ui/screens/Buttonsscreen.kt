package com.example.marco_todo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun ButtonsScreen(onGoHomeScreen: () -> Unit) {
    var resultadoText by remember { mutableStateOf("Selecciona un tipo de botón") }
    var isChecked by remember { mutableStateOf(false) }

    BaseScreen(
        title = "Botones en Compose",
        onBack = onGoHomeScreen,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { resultadoText = "¡FAB presionado! 🚀" },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = resultadoText,
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Estándar
            Button(
                onClick = { resultadoText = "Botón estándar presionado" },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("BOTÓN ESTÁNDAR")
            }

            // Botón con Icono
            Button(
                onClick = { resultadoText = "Botón con icono presionado" },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Text("ENVIAR MENSAJE")
            }

            // Botón Outlined
            OutlinedButton(
                onClick = { resultadoText = "Botón delineado presionado" },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Filled.Star, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Text("OUTLINED BUTTON")
            }

            // Botón Tonal
            FilledTonalButton(
                onClick = { resultadoText = "Botón tonal presionado" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("FILLED TONAL BUTTON")
            }

            // Simulación de Toggle Button
            Button(
                onClick = {
                    isChecked = !isChecked
                    resultadoText = if (isChecked) "Estado: ACTIVADO" else "Estado: DESACTIVADO"
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = if (isChecked) Icons.Filled.Favorite else Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if (isChecked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
                Spacer(Modifier.width(12.dp))
                Text(if (isChecked) "ACTIVADO" else "DESACTIVADO")
            }

            // Botón de Texto
            TextButton(
                onClick = { resultadoText = "Botón de texto presionado" }
            ) {
                Text("¿NECESITAS AYUDA?", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}