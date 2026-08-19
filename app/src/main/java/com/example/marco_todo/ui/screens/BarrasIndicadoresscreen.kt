package com.example.marco_todo.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marco_todo.ui.theme.Green
import com.example.marco_todo.ui.theme.GreenDark
import com.example.marco_todo.ui.theme.Mint
import com.example.marco_todo.ui.theme.Navy
import com.example.marco_todo.ui.theme.TextMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarrasIndicadoresScreen(onGoHomeScreen: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Barras e Indicadores", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGoHomeScreen) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Ejemplos funcionales de medidores en Compose",
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            SeccionProgressBar()
            SeccionDivider()
            SeccionSeekBar()
            SeccionDivider()
            SeccionRatingBar()
            SeccionDivider()
            SeccionLinearProgressIndicator()
            SeccionDivider()
            SeccionCircularProgressIndicator()
        }
    }
}

@Composable
private fun SeccionDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Mint)
    )
}

@Composable
private fun SectionContainer(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Navy)
        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
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
        title = "ProgressBar (modo indeterminado)",
        subtitle = "Indica que una tarea está en curso, sin conocer cuánto falta."
    ) {
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
            Text(if (cargando) "Cargando..." else "Simular tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            cargando -> CircularProgressIndicator(color = Green)
            completado -> Text("Tarea completada", color = GreenDark, fontWeight = FontWeight.Bold)
            else -> Text("Sin actividad", color = TextMuted)
        }
    }
}

@Composable
private fun SeccionSeekBar() {
    var valor by remember { mutableFloatStateOf(50f) }

    SectionContainer(
        title = "SeekBar",
        subtitle = "Barra deslizante para elegir un valor dentro de un rango."
    ) {
        Text("Volumen: ${valor.toInt()}%", color = Navy, fontWeight = FontWeight.Bold)
        Slider(
            value = valor,
            onValueChange = { valor = it },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Green,
                activeTrackColor = Green
            )
        )
    }
}

@Composable
private fun SeccionRatingBar() {
    var rating by remember { mutableIntStateOf(3) }

    SectionContainer(
        title = "RatingBar",
        subtitle = "Calificación mediante estrellas (componente personalizado)."
    ) {
        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Estrella $i",
                    tint = if (i <= rating) Green else Mint,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { rating = i }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Calificación: $rating / 5", color = Navy, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SeccionLinearProgressIndicator() {
    var progreso by remember { mutableFloatStateOf(0.3f) }

    SectionContainer(
        title = "LinearProgressIndicator",
        subtitle = "Barra de progreso Material Design, en modo determinado."
    ) {
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = Green,
            trackColor = Mint
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("${(progreso * 100).toInt()}%", color = Navy, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            progreso = if (progreso >= 1f) 0f else (progreso + 0.1f).coerceAtMost(1f)
        }) {
            Text("Aumentar progreso")
        }
    }
}

@Composable
private fun SeccionCircularProgressIndicator() {
    val progreso = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    SectionContainer(
        title = "CircularProgressIndicator",
        subtitle = "Indicador circular de progreso, en modo determinado."
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { progreso.value },
                modifier = Modifier.size(96.dp),
                color = Green,
                trackColor = Mint,
                strokeWidth = 8.dp
            )
            Text(
                text = "${(progreso.value * 100).toInt()}%",
                color = Navy,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            scope.launch {
                progreso.snapTo(0f)
                progreso.animateTo(1f, animationSpec = tween(durationMillis = 1500))
            }
        }) {
            Text("Cargar")
        }
    }
}