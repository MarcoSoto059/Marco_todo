package com.example.marco_todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onGoNavigationScreen: () -> Unit,
    onGoBarrasIndicadoresScreen: () -> Unit,
    onGoButtonsScreen: () -> Unit,
    onGoMultimediaScreen: () -> Unit,
    onGoTextWidgetScreen: () -> Unit,
    onGoSelectionScreen: () -> Unit,
    onGoCollectionListScreen: () -> Unit,
    onGoMaterialDesignScreen: () -> Unit,
    onGoGoogleScreen: () -> Unit,
    onGoJetpackComposeScreen: () -> Unit,
    onGoLayoutScreen: () -> Unit,
    onGoFechaHoraScreen: () -> Unit,
    onGoContenedoresScreen: () -> Unit,
    onGoDialogosScreen: () -> Unit,
    onGoAcercaDeScreen: () -> Unit,
    onLogout: () -> Unit
) {
    val contexto = LocalContext.current

    data class BotonInfo(val titulo: String, val accion: () -> Unit)

    val botones = listOf(
        BotonInfo("Texto", onGoTextWidgetScreen),
        BotonInfo("Botones", onGoButtonsScreen),
        BotonInfo("Selección", onGoSelectionScreen),
        BotonInfo("Listas y Colecciones", onGoCollectionListScreen),
        BotonInfo("Imágenes y Multimedia", onGoMultimediaScreen),
        BotonInfo("Barras e Indicadores", onGoBarrasIndicadoresScreen),
        BotonInfo("Navegación", onGoNavigationScreen),
        BotonInfo("Layout", onGoLayoutScreen),
        BotonInfo("Fecha y hora", onGoFechaHoraScreen),
        BotonInfo("Contenedores Desplazables", onGoContenedoresScreen),
        BotonInfo("Diálogos y Mensajes", onGoDialogosScreen),
        BotonInfo("Material Design", onGoMaterialDesignScreen),
        BotonInfo("Google", onGoGoogleScreen),
        BotonInfo("Jetpack Compose", onGoJetpackComposeScreen),
        BotonInfo("Acerca de", onGoAcercaDeScreen)
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                Button(
                    onClick = {
                        Toast.makeText(contexto, "¡Hasta luego! Cerrando sesión...", Toast.LENGTH_SHORT).show()
                        onLogout()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Salir")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Salir")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Catálogo UI",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(botones) { boton ->
                    Button(
                        onClick = boton.accion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = boton.titulo,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}