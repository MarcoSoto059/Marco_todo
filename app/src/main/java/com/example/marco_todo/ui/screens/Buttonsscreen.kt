package com.example.marco_todo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(onGoHomeScreen: () -> Unit) {
    var resultadoText by remember { mutableStateOf("Presiona un botón") }
    var isChecked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Botones", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGoHomeScreen) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { resultadoText = "Presionaste el FAB 🚀" }) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = resultadoText,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                onClick = { resultadoText = "Presionaste el Button estándar" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Button estándar")
            }
            IconButton(
                onClick = { resultadoText = "Presionaste el ImageButton" },
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Imagen personalizada",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Button(
                onClick = { resultadoText = "Presionaste el Material Button con Icono" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Material Button")
            }

            Button(
                onClick = {
                    isChecked = !isChecked
                    resultadoText = if (isChecked) "ToggleButton: Activado" else "ToggleButton: Desactivado"
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(if (isChecked) "Activado" else "Desactivado")
            }

            OutlinedButton(
                onClick = { resultadoText = "Presionaste un Outlined Button" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Outlined Button")
            }
        }
    }
}