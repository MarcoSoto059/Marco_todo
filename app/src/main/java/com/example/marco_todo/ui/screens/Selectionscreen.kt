package com.example.marco_todo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun SelectionScreen(onGoHomeScreen: () -> Unit) {
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

    BaseScreen(
        title = "Componentes de Selección",
        onBack = onGoHomeScreen
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Checkboxes Section
            SelectionSection(title = "Intereses (Checkboxes)") {
                Column {
                    SelectionRow(label = "Música", checked = musicaChecked) { musicaChecked = it }
                    SelectionRow(label = "Deportes", checked = deportesChecked) { deportesChecked = it }
                    SelectionRow(label = "Tecnología", checked = tecnologiaChecked) { tecnologiaChecked = it }
                }
            }

            // RadioButtons Section
            SelectionSection(title = "Contenido Preferido (RadioButtons)") {
                Column {
                    opcionesContenido.forEach { opcion ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedContenido = opcion }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (opcion == selectedContenido),
                                onClick = { selectedContenido = opcion }
                            )
                            Text(opcion, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            // Switch Section
            SelectionSection(title = "Configuración (Switch)") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Activar Notificaciones", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = notificacionesEnabled, onCheckedChange = { notificacionesEnabled = it })
                }
            }

            // Chips Section
            SelectionSection(title = "Habilidades (FilterChips)") {
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

                    resultado = """
                        Preferencias Guardadas:
                        - Intereses: ${if (intereses.isEmpty()) "Ninguno" else intereses.joinToString(", ")}
                        - Contenido: $selectedContenido
                        - Notificaciones: ${if (notificacionesEnabled) "Sí" else "No"}
                        - Habilidades: ${if (categorias.isEmpty()) "Ninguna" else categorias.joinToString(", ")}
                    """.trimIndent()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("PROCESAR SELECCIÓN")
            }

            if (resultado.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "RESUMEN DE SELECCIÓN",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = resultado,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectionSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        content()
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp).alpha(0.5f))
    }
}

@Composable
fun SelectionRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp)
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}
