package com.example.marco_todo.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun BarrasIndicadoresScreen(onGoHomeScreen: () -> Unit) {
    BaseScreen(
        title = "Barras e Indicadores",
        onBack = onGoHomeScreen
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Ejemplos funcionales de medidores",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
            SeccionProgressBar()
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), thickness = 0.5.dp)
            SeccionSeekBar()
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), thickness = 0.5.dp)
            SeccionRatingBar()
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), thickness = 0.5.dp)
            SeccionLinearProgressIndicator()
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), thickness = 0.5.dp)
            SeccionCircularProgressIndicator()
        }
    }
}

@Composable
private fun SectionContainer(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = title, 
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold, 
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )
        content()
    }
}

@Composable
private fun SeccionProgressBar() {
    var cargando by remember { mutableStateOf(false) }
    var completado by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    SectionContainer(
        title = "Circular (Indeterminado)",
        subtitle = "Indica que una tarea está en curso sin un tiempo definido."
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Button(
                onClick = {
                    completado = false
                    cargando = true
                    scope.launch {
                        delay(2000)
                        cargando = false
                        completado = true
                    }
                },
                enabled = !cargando
            ) {
                Text(if (cargando) "Cargando..." else "Simular")
            }

            if (cargando) {
                CircularProgressIndicator(strokeWidth = 3.dp)
            } else if (completado) {
                Text("¡Completado!", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SeccionSeekBar() {
    var valor by remember { mutableFloatStateOf(50f) }

    SectionContainer(
        title = "Deslizador (Slider)",
        subtitle = "Control para seleccionar valores numéricos en un rango."
    ) {
        Text(
            text = "Valor actual: ${valor.toInt()}%", 
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Slider(
            value = valor,
            onValueChange = { valor = it },
            valueRange = 0f..100f
        )
    }
}

@Composable
private fun SeccionRatingBar() {
    var rating by remember { mutableIntStateOf(3) }

    SectionContainer(
        title = "Puntuación (Rating)",
        subtitle = "Componente personalizado para calificaciones."
    ) {
        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Estrella $i",
                    tint = if (i <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { rating = i }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Calificación: $rating de 5", 
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SeccionLinearProgressIndicator() {
    var progreso by remember { mutableFloatStateOf(0.3f) }

    SectionContainer(
        title = "Lineal (Determinado)",
        subtitle = "Barra de progreso horizontal con valores específicos."
    ) {
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${(progreso * 100).toInt()}% completado", style = MaterialTheme.typography.bodySmall)
            Button(
                onClick = {
                    progreso = if (progreso >= 1f) 0f else (progreso + 0.1f).coerceAtMost(1f)
                },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Aumentar")
            }
        }
    }
}

@Composable
private fun SeccionCircularProgressIndicator() {
    val progreso = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    SectionContainer(
        title = "Circular (Determinado)",
        subtitle = "Indicador circular con animación de carga."
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progreso.value },
                    modifier = Modifier.size(80.dp),
                    strokeWidth = 6.dp,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = "${(progreso.value * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(onClick = {
                scope.launch {
                    progreso.snapTo(0f)
                    progreso.animateTo(1f, animationSpec = tween(durationMillis = 1500))
                }
            }) {
                Text("Iniciar Carga")
            }
        }
    }
}
