package com.example.marco_todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marco_todo.ui.components.BaseScreen

data class BotonInfo(
    val titulo: String,
    val icono: ImageVector,
    val accion: () -> Unit
)

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

    val botones = listOf(
        BotonInfo("Texto", Icons.Default.TextFields, onGoTextWidgetScreen),
        BotonInfo("Botones", Icons.Default.SmartButton, onGoButtonsScreen),
        BotonInfo("Selección", Icons.Default.Checklist, onGoSelectionScreen),
        BotonInfo("Listas", Icons.AutoMirrored.Filled.List, onGoCollectionListScreen),
        BotonInfo("Multimedia", Icons.Default.Image, onGoMultimediaScreen),
        BotonInfo("Indicadores", Icons.Default.LinearScale, onGoBarrasIndicadoresScreen),
        BotonInfo("Navegación", Icons.Default.Navigation, onGoNavigationScreen),
        BotonInfo("Layout", Icons.Default.Layers, onGoLayoutScreen),
        BotonInfo("Fecha y hora", Icons.Default.DateRange, onGoFechaHoraScreen),
        BotonInfo("Contenedores", Icons.Default.DashboardCustomize, onGoContenedoresScreen),
        BotonInfo("Diálogos", Icons.AutoMirrored.Filled.Message, onGoDialogosScreen),
        BotonInfo("Material", Icons.Default.DesignServices, onGoMaterialDesignScreen),
        BotonInfo("Google", Icons.Default.Language, onGoGoogleScreen),
        BotonInfo("Compose", Icons.Default.Architecture, onGoJetpackComposeScreen),
        BotonInfo("Acerca de", Icons.Default.Info, onGoAcercaDeScreen)
    )

    BaseScreen(
        title = "Catálogo de Componentes",
        actions = {
            IconButton(onClick = {
                Toast.makeText(contexto, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
                onLogout()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Salir",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Explora los ejemplos de UI",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(botones) { boton ->
                    ItemCatalogo(boton)
                }
            }
        }
    }
}

@Composable
fun ItemCatalogo(boton: BotonInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { boton.accion() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = boton.icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = boton.titulo,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
