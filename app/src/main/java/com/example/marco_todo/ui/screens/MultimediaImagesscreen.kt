package com.example.marco_todo.ui.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.VideoView
import android.widget.Toast
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import kotlin.OptIn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.marco_todo.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.example.marco_todo.ui.components.BaseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultimediaImagesScreen(onGoHomeScreen: () -> Unit) {
    var selectedSection by remember { mutableIntStateOf(0) }
    val sections = listOf("Visor", "Picker", "Video")
    val icons = listOf(Icons.Default.Image, Icons.Default.AddPhotoAlternate, Icons.Default.PlayCircle)

    BaseScreen(
        title = "Multimedia",
        onBack = onGoHomeScreen
    ) { paddingValues ->
        Scaffold(
            modifier = Modifier.padding(paddingValues),
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    sections.forEachIndexed { index, label ->
                        NavigationBarItem(
                            selected = selectedSection == index,
                            onClick = { selectedSection = index },
                            icon = { Icon(icons[index], contentDescription = label) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedSection) {
                    0 -> ImageDemoSection()
                    1 -> PickerDemoSection()
                    2 -> VideoDemoSection()
                }
            }
        }
    }
}

@Composable
fun ImageDemoSection() {
    var scaleType by remember { mutableStateOf(ContentScale.Fit) }
    var scaleLabel by remember { mutableStateOf("Fit") }
    var alphaValue by remember { mutableFloatStateOf(1.0f) }
    var rotationValue by remember { mutableFloatStateOf(0f) }
    var tintColor by remember { mutableStateOf<Color?>(null) }
    var isSample1Active by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Visor de Imágenes",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = if (isSample1Active) R.drawable.ic_launcher_foreground else R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = scaleType,
                    colorFilter = tintColor?.let { ColorFilter.tint(it) },
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(alphaValue)
                        .rotate(rotationValue)
                )
            }
        }

        ControlSection(title = "Escalado: $scaleLabel") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                val scales = listOf(
                    "Fit" to ContentScale.Fit,
                    "Crop" to ContentScale.Crop,
                    "Fill" to ContentScale.FillBounds,
                    "Inside" to ContentScale.Inside
                )
                scales.forEach { (label, scale) ->
                    FilterChip(
                        selected = scaleType == scale,
                        onClick = { scaleType = scale; scaleLabel = label },
                        label = { Text(label) }
                    )
                }
            }
        }

        ControlSection(title = "Transparencia: ${(alphaValue * 100).toInt()}%") {
            Slider(
                value = alphaValue, 
                onValueChange = { alphaValue = it },
                colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary)
            )
        }

        ControlSection(title = "Rotación: ${rotationValue.toInt()}°") {
            Slider(
                value = rotationValue, 
                onValueChange = { rotationValue = it }, 
                valueRange = 0f..360f
            )
        }

        ControlSection(title = "Filtro de Color") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val tints = listOf(
                    "Ninguno" to null,
                    "Rojo" to Color(0x4DFF0000),
                    "Verde" to Color(0x4D00FF00),
                    "Azul" to Color(0x4D0000FF)
                )
                tints.forEach { (label, color) ->
                    FilterChip(
                        selected = tintColor == color,
                        onClick = { tintColor = color },
                        label = { Text(label) }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { isSample1Active = !isSample1Active },
                modifier = Modifier.weight(1f)
            ) {
                Text("CAMBIAR")
            }
            OutlinedButton(
                onClick = {
                    scaleType = ContentScale.Fit
                    scaleLabel = "Fit"
                    alphaValue = 1.0f
                    rotationValue = 0f
                    tintColor = null
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("RESETEAR")
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ControlSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        content()
    }
}

@Composable
fun PickerDemoSection() {
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedUri = uri
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedUri = photoUri
            Toast.makeText(context, "Imagen guardada en la galería", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createImageUri(context)
            if (uri != null) {
                photoUri = uri
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Error al crear el archivo de imagen", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Selector de Medios",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                if (selectedUri == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.outlineVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Selecciona una imagen o toma una foto",
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    AsyncImage(
                        model = selectedUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("GALERÍA")
            }
            Button(
                onClick = { permissionLauncher.launch(android.Manifest.permission.CAMERA) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("CÁMARA")
            }
        }

        if (selectedUri != null) {
            TextButton(
                onClick = { selectedUri = null },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("LIMPIAR SELECCIÓN")
            }
        }
    }
}

fun createImageUri(context: Context): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val name = "JPEG_${timeStamp}_"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MarcoTodo")
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoDemoSection() {
    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    //val sampleWebVideoUrl = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val sampleWebVideoUrl = "https://www.w3schools.com/html/mov_bbb.mp4"

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isLoading = state == Player.STATE_BUFFERING
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                isLoading = false
                Log.e("VideoDemoSection", "Error de reproducción: ${error.errorCodeName} (${error.errorCode})", error)
                
                val errorMessage = when (error.errorCode) {
                    androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> "Error de red: Sin conexión"
                    androidx.media3.common.PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> "Archivo no encontrado"
                    androidx.media3.common.PlaybackException.ERROR_CODE_IO_UNSPECIFIED -> "Error de origen (Source Error). Verifica la URL o permisos."
                    else -> "Error: ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(videoUri) {
        videoUri?.let {
            isLoading = true
            exoPlayer.stop()
            exoPlayer.clearMediaItems()
            val mediaItem = MediaItem.Builder()
                .setUri(it)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }
    }

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            videoUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            if (videoUri == null) {
                Text("Carga un video para comenzar", color = Color.White)
            } else {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            useController = true
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                videoUri = Uri.parse(sampleWebVideoUrl)
            }) {
                Text("Web Video")
            }
            /*
            Button(onClick = {
                videoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
            }) {
                Text("Local Video")
            }
            */
        }
        
        Text(
            text = if (videoUri != null) stringResource(R.string.video_status_playing) else "Listo",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
