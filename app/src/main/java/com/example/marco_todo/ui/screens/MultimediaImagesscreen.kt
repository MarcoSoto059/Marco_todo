package com.example.marco_todo.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import com.example.marco_todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultimediaImagesScreen(onGoHomeScreen: () -> Unit) {
    var selectedSection by remember { mutableIntStateOf(0) }
    val sections = listOf("Imágenes", "Picker", "Video")
    val icons = listOf(Icons.Default.Face, Icons.Default.List, Icons.Default.PlayArrow)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(sections[selectedSection]) },
                navigationIcon = {
                    IconButton(onClick = onGoHomeScreen) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
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
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedSection) {
                0 -> ImageDemoSection()
                1 -> PickerDemoSection()
                2 -> VideoDemoSection()
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            elevation = CardDefaults.cardElevation(4.dp)
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

        Text("scaleType: $scaleLabel", fontWeight = FontWeight.Bold)
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

        Text("Alpha: ${(alphaValue * 100).toInt()}%")
        Slider(value = alphaValue, onValueChange = { alphaValue = it })

        Text("Rotación: ${rotationValue.toInt()}°")
        Slider(value = rotationValue, onValueChange = { rotationValue = it }, valueRange = 0f..360f)

        Text("Tintado")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val tints = listOf(
                "None" to null,
                "Red" to Color(0x4DFF0000),
                "Green" to Color(0x4D00FF00),
                "Blue" to Color(0x4D0000FF)
            )
            tints.forEach { (label, color) ->
                FilterChip(
                    selected = tintColor == color,
                    onClick = { tintColor = color },
                    label = { Text(label) }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { isSample1Active = !isSample1Active }) {
                Text("Cambiar Recurso")
            }
            OutlinedButton(onClick = {
                scaleType = ContentScale.Fit
                scaleLabel = "Fit"
                alphaValue = 1.0f
                rotationValue = 0f
                tintColor = null
            }) {
                Text("Resetear")
            }
        }
    }
}

@Composable
fun PickerDemoSection() {
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedUri = uri
            selectedBitmap = null
        } else {
            Toast.makeText(context, "Selección cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            selectedBitmap = bitmap
            selectedUri = null
        } else {
            Toast.makeText(context, "Captura cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                if (selectedUri == null && selectedBitmap == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                        Text("Ningún medio seleccionado", color = Color.Gray)
                    }
                } else {
                    if (selectedUri != null) {
                        // In a real app, use Coil or Glide for Uris
                        Text("URI: $selectedUri")
                    } else if (selectedBitmap != null) {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Text("Galería")
            }
            Button(onClick = { cameraLauncher.launch() }) {
                Text("Cámara")
            }
        }

        if (selectedUri != null || selectedBitmap != null) {
            Button(
                onClick = { selectedUri = null; selectedBitmap = null },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Limpiar Selección")
            }
        }
    }
}

@Composable
fun VideoDemoSection() {
    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    val sampleWebVideoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

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
                        VideoView(ctx).apply {
                            val mediaController = MediaController(ctx)
                            mediaController.setAnchorView(this)
                            setMediaController(mediaController)
                        }
                    },
                    update = { view ->
                        view.setVideoURI(videoUri)
                        view.start()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                videoUri = Uri.parse(sampleWebVideoUrl)
            }) {
                Text("Web Video")
            }
        }
        
        Text(
            text = if (videoUri != null) stringResource(R.string.video_status_playing) else "Listo",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
