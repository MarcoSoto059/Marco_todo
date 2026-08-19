package com.example.marco_todo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    onGoHomeScreen: () -> Unit
) {
    var musicaChecked by remember { mutableStateOf(false) }
    var deportesChecked by remember { mutableStateOf(false) }
    var tecnologiaChecked by remember { mutableStateOf(false) }

    var selectedContenido by remember { mutableStateOf("Sin selección") }
    val opcionesContenido = listOf("Películas", "Series", "Documentales")

    var notificacionesEnabled by remember { mutableStateOf(false) }

    var androidSelected by remember { mutableStateOf(false) }
    var kotlinSelected by remember { mutableStateOf(false) }
    var javaSelected by remember { mutableStateOf(false) }

    var resultado by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selección de Usuario") },
                navigationIcon = {
                    IconButton(onClick = onGoHomeScreen) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Intereses", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = musicaChecked, onCheckedChange = { musicaChecked = it })
                Text("Música")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = deportesChecked, onCheckedChange = { deportesChecked = it })
                Text("Deportes")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = tecnologiaChecked, onCheckedChange = { tecnologiaChecked = it })
                Text("Tecnología")
            }

            HorizontalDivider()

            Text("Contenido", style = MaterialTheme.typography.titleMedium)
            opcionesContenido.forEach { opcion ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (opcion == selectedContenido),
                        onClick = { selectedContenido = opcion }
                    )
                    Text(opcion)
                }
            }

            HorizontalDivider()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notificaciones", style = MaterialTheme.typography.titleMedium)
                Switch(checked = notificacionesEnabled, onCheckedChange = { notificacionesEnabled = it })
            }

            HorizontalDivider()

            Text("Categorías", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterChip(
                    selected = androidSelected,
                    onClick = { androidSelected = !androidSelected },
                    label = { Text("Android") }
                )
                FilterChip(
                    selected = kotlinSelected,
                    onClick = { kotlinSelected = !kotlinSelected },
                    label = { Text("Kotlin") }
                )
                FilterChip(
                    selected = javaSelected,
                    onClick = { javaSelected = !javaSelected },
                    label = { Text("Java") }
                )
            }

            Button(
                onClick = {
                    val intereses = mutableListOf<String>()
                    if (musicaChecked) intereses.add("Música")
                    if (deportesChecked) intereses.add("Deportes")
                    if (tecnologiaChecked) intereses.add("Tecnología")

                    val categorias = mutableListOf<String>()
                    if (androidSelected) categorias.add("Android")
                    if (kotlinSelected) categorias.add("Kotlin")
                    if (javaSelected) categorias.add("Java")

                    val interesesTexto = if (intereses.isEmpty()) "Ninguno" else intereses.joinToString(", ")
                    val categoriasTexto = if (categorias.isEmpty()) "Ninguna" else categorias.joinToString(", ")

                    resultado = """
                        SELECCIÓN DEL USUARIO
                        
                        Intereses: $interesesTexto
                        Contenido: $selectedContenido
                        Notificaciones: ${if (notificacionesEnabled) "Activadas" else "Desactivadas"}
                        Categorías: $categoriasTexto
                    """.trimIndent()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }

            if (resultado.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text = resultado,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
