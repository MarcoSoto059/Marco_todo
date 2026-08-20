package com.example.marco_todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun DialogosYMensajesScreen(onGoHomeScreen: () -> Unit) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val contexto = LocalContext.current

    BaseScreen(
        title = "Diálogos y Mensajes",
        onBack = onGoHomeScreen,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Notificaciones y Alertas",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Diferentes formas de interactuar con el usuario",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            MessageCard(
                title = "Alerta de Sistema",
                description = "Muestra una ventana emergente que bloquea la interacción hasta que se tome una decisión.",
                buttonText = "ABRIR DIÁLOGO",
                onClick = { mostrarDialogo = true }
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            MessageCard(
                title = "Barra de Mensaje (Snackbar)",
                description = "Mensaje breve en la parte inferior que desaparece automáticamente.",
                buttonText = "MOSTRAR SNACKBAR",
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Operación realizada con éxito",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MessageCard(
                title = "Notificación Toast",
                description = "Mensaje nativo de Android, rápido y poco intrusivo.",
                buttonText = "LANZAR TOAST",
                onClick = {
                    Toast.makeText(contexto, "Notificación rápida generada", Toast.LENGTH_SHORT).show()
                }
            )
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                icon = { Icon(Icons.Default.Info, contentDescription = null) },
                title = { Text("¿Confirmar Acción?") },
                text = { Text("Esta es una demostración de un diálogo de confirmación estándar de Material 3.") },
                confirmButton = {
                    Button(onClick = { mostrarDialogo = false }) { Text("Aceptar") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Composable
fun MessageCard(title: String, description: String, buttonText: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                text = description, 
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
            ) {
                Text(buttonText, fontSize = 12.sp)
            }
        }
    }
}
