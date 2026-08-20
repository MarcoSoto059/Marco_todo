package com.example.marco_todo.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.marco_todo.data.repository.LocalRepository
import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun CollectionListScreen(onGoHomeScreen: () -> Unit) {
    var currentDemo by remember { mutableStateOf<String?>(null) }

    when (currentDemo) {
        null -> DemoSelector(onSelect = { currentDemo = it }, onGoHomeScreen = onGoHomeScreen)
        "listView" -> DemoLazyColumnBasico(onBack = { currentDemo = null })
        "recyclerView" -> DemoLazyColumnBusqueda(onBack = { currentDemo = null })
        "gridView" -> DemoLazyVerticalGrid(onBack = { currentDemo = null })
        "spinner" -> DemoListaDesplegable(onBack = { currentDemo = null })
        "expandableListView" -> DemoListaJerarquica(onBack = { currentDemo = null })
    }
}

@Composable
private fun DemoSelector(onSelect: (String) -> Unit, onGoHomeScreen: () -> Unit) {
    val demos = listOf(
        Triple("ListView", "Lista tradicional en una columna.", "listView"),
        Triple("RecyclerView", "Lista eficiente con búsqueda.", "recyclerView"),
        Triple("GridView", "Distribución en cuadrícula.", "gridView"),
        Triple("Spinner", "Selector compacto desplegable.", "spinner"),
        Triple("Expandable", "Lista con secciones expandibles.", "expandableListView")
    )

    BaseScreen(
        title = "Listas y Colecciones",
        onBack = onGoHomeScreen
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Explora diferentes tipos de listas",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(demos) { index, (title, description, key) ->
                    Card(
                        onClick = { onSelect(key) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when(key) {
                                        "listView" -> Icons.AutoMirrored.Filled.List
                                        "recyclerView" -> Icons.Default.Search
                                        "gridView" -> Icons.Default.GridView
                                        "spinner" -> Icons.Default.ArrowDropDownCircle
                                        else -> Icons.Default.Expand
                                    },
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

// funciones de las listas

@Composable
private fun DemoLazyColumnBasico(onBack: () -> Unit) {
    var texto by remember { mutableStateOf("") }
    val items = LocalRepository.items
    var editIndex by remember { mutableIntStateOf(-1) }
    var editText by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableIntStateOf(-1) }
    val snackHostState = remember { SnackbarHostState() }

    BaseScreen(
        title = "ListView (LazyColumn)",
        onBack = onBack,
        snackbarHost = { SnackbarHost(snackHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Información Local Persistente (Sesión)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = texto,
                    onValueChange = { texto = it },
                    label = { Text("Nuevo elemento") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilledTonalButton(
                    onClick = {
                        if (texto.isNotBlank()) {
                            LocalRepository.addItem(texto.trim(), "Elemento agregado localmente")
                            texto = ""
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Elementos: ${items.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF1A237E)
                )
                if (items.isNotEmpty()) {
                    TextButton(onClick = { LocalRepository.clearAll() }) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Limpiar todo")
                    }
                }
            }

            if (items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Inventory2,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("La lista esta vacia", color = Color.Gray, fontSize = 16.sp)
                        Text("Agrega un elemento arriba", color = Color.LightGray, fontSize = 13.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(items) { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(
                                            Color(0xFF1A237E).copy(alpha = 0.1f),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "${index + 1}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A237E),
                                        fontSize = 14.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = item.title,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 16.sp
                                )
                                IconButton(onClick = {
                                    editIndex = item.id
                                    editText = item.title
                                    showEditDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, "Editar", tint = Color(0xFF1565C0))
                                }
                                IconButton(onClick = { showDeleteDialog = item.id }) {
                                    Icon(Icons.Default.Delete, "Eliminar", tint = Color(0xFFD32F2F))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar elemento") },
            text = {
                OutlinedTextField(
                    value = editText,
                    onValueChange = { editText = it },
                    label = { Text("Texto") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (editText.isNotBlank()) {
                        LocalRepository.updateItem(editIndex, editText.trim(), "Actualizado")
                        showEditDialog = false
                    }
                }) { Text("Guardar") }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) { Text("Cancelar") }
            }
        )
    }

    if (showDeleteDialog >= 0) {
        val itemId = showDeleteDialog
        val itemToDelete = items.find { it.id == itemId }
        AlertDialog(
            onDismissRequest = { showDeleteDialog = -1 },
            title = { Text("Eliminar elemento") },
            text = { Text("Deseas eliminar '${itemToDelete?.title}'?") },
            confirmButton = {
                TextButton(onClick = {
                    LocalRepository.removeItem(itemId)
                    showDeleteDialog = -1
                }) { Text("Eliminar", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = -1 }) { Text("Cancelar") }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoLazyColumnBusqueda(onBack: () -> Unit) {
    val todosLosElementos = remember {
        mutableStateListOf(
            "Alice Johnson", "Bob Smith", "Charlie Brown", "Diana Ross",
            "Edward Norton", "Fiona Apple", "George Lucas", "Hannah Montana",
            "Ivan Drago", "Julia Roberts", "Kevin Hart", "Laura Palmer",
            "Michael Scott", "Nancy Drew", "Oscar Wilde", "Pam Beesly",
            "Quentin Tarantino", "Rachel Green", "Steve Rogers", "Tina Turner"
        )
    }
    var busqueda by remember { mutableStateOf("") }
    var ordenarAsc by remember { mutableStateOf(true) }

    val resultados = remember(busqueda, ordenarAsc, todosLosElementos) {
        val filtrados = if (busqueda.isBlank()) {
            todosLosElementos.toList()
        } else {
            todosLosElementos.filter { it.contains(busqueda, ignoreCase = true) }
        }
        if (ordenarAsc) filtrados.sorted() else filtrados.sortedDescending()
    }

    BaseScreen(
        title = "RecyclerView (Busqueda)",
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        IconButton(onClick = { busqueda = "" }) {
                            Icon(Icons.Default.Clear, "Limpiar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Resultados: ${resultados.size} de ${todosLosElementos.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Orden:", style = MaterialTheme.typography.bodySmall)
                    Switch(
                        checked = ordenarAsc,
                        onCheckedChange = { ordenarAsc = it },
                        modifier = Modifier.scale(0.8f).padding(horizontal = 4.dp)
                    )
                    Text(
                        if (ordenarAsc) "A-Z" else "Z-A",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (resultados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.SearchOff, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outlineVariant)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Sin resultados para '$busqueda'", color = MaterialTheme.colorScheme.outline)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(resultados) { nombre ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color(0xFF1A237E), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        nombre.first().toString(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(nombre, fontWeight = FontWeight.Medium)
                                    Text(
                                        "ID: ${nombre.hashCode().toString().takeLast(4)}",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoLazyVerticalGrid(onBack: () -> Unit) {
    val numeros = (1..30).toList()
    val colores = listOf(
        Color(0xFFE53935), Color(0xFF1E88E5), Color(0xFF43A047),
        Color(0xFFFB8C00), Color(0xFF8E24AA), Color(0xFF00ACC1),
        Color(0xFFD81B60), Color(0xFF3949AB), Color(0xFFFDD835),
        Color(0xFF00897B)
    )

    val productos = listOf(
        "Laptop", "Celular", "Tablet", "Audifonos", "Mouse",
        "Teclado", "Monitor", "Webcam", "USB", "Disco Duro",
        "Cargador", "Impresora", "Router", "Parlante", "Smartwatch"
    )

    var columnas by remember { mutableIntStateOf(2) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GridView", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Cuadricula de Numeros", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A237E))
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Columnas: $columnas", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Slider(
                        value = columnas.toFloat(),
                        onValueChange = { columnas = it.toInt() },
                        valueRange = 2f..5f,
                        steps = 2,
                        modifier = Modifier.width(150.dp)
                    )
                }
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnas),
                    modifier = Modifier.heightIn(max = 400.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(numeros) { num ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colores[(num - 1) % colores.size]
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "$num",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Grid de Productos", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A237E))
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.heightIn(max = 500.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productos) { producto ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    tint = Color(0xFF1A237E),
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    producto,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoListaDesplegable(onBack: () -> Unit) {
    val paises = listOf("Mexico", "Colombia", "Argentina", "Espana", "Chile", "Peru", "Venezuela", "Ecuador")
    val lenguajes = listOf("Kotlin", "Java", "Python", "JavaScript", "C#", "Swift", "Dart", "Rust", "Go", "TypeScript")

    var paisSeleccionado by remember { mutableStateOf("") }
    var carreraSeleccionada by remember { mutableStateOf("") }
    var lenguajeSeleccionado by remember { mutableStateOf("") }

    var expandedPais by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spinner", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    "DropdownMenu (basico)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A237E)
                )
                Text(
                    "Menu desplegable simple con opciones.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    OutlinedButton(
                        onClick = { expandedPais = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            if (paisSeleccionado.isEmpty()) "Selecciona un pais" else paisSeleccionado,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start,
                            color = if (paisSeleccionado.isEmpty()) Color.Gray else Color.Black
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = expandedPais,
                        onDismissRequest = { expandedPais = false }
                    ) {
                        paises.forEach { pais ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (pais == paisSeleccionado) {
                                            Icon(
                                                Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = Color(0xFF2E7D32),
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                        Text(pais)
                                    }
                                },
                                onClick = {
                                    paisSeleccionado = pais
                                    expandedPais = false
                                }
                            )
                        }
                    }
                }

                if (paisSeleccionado.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Text(
                            "Pais seleccionado: $paisSeleccionado",
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "ExposedDropdownMenuBox (Material 3)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A237E)
                )
                Text(
                    "Campo con menu desplegable integrado y busqueda.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                var expanded by remember { mutableStateOf(false) }
                var textoBusqueda by remember { mutableStateOf("") }
                val filtrados = if (textoBusqueda.isBlank()) lenguajes
                else lenguajes.filter { it.contains(textoBusqueda, ignoreCase = true) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = if (lenguajeSeleccionado.isEmpty()) textoBusqueda else lenguajeSeleccionado,
                        onValueChange = {
                            textoBusqueda = it
                            lenguajeSeleccionado = ""
                            expanded = true
                        },
                        label = { Text("Busca un lenguaje") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (filtrados.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("Sin resultados", color = Color.Gray) },
                                onClick = { expanded = false }
                            )
                        } else {
                            filtrados.forEach { lenguaje ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (lenguaje == lenguajeSeleccionado) {
                                                Icon(
                                                    Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = Color(0xFF2E7D32),
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                            }
                                            Text(lenguaje)
                                        }
                                    },
                                    onClick = {
                                        lenguajeSeleccionado = lenguaje
                                        textoBusqueda = ""
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                if (lenguajeSeleccionado.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
                    ) {
                        Text(
                            "Lenguaje seleccionado: $lenguajeSeleccionado",
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A237E)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "DropdownMenu con categorias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1A237E)
                )
                Text(
                    "Menu desplegable agrupado por categorias.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                val categoriasCarreras = mapOf(
                    "Ingenieria" to listOf("Ingenieria en Software", "Ingenieria en Sistemas", "Ingenieria Civil"),
                    "Ciencias" to listOf("Ciencias de la Computacion", "Matematicas", "Fisica"),
                    "Tecnologia" to listOf("Tecnologias de la Informacion", "Redes y Telecomunicaciones", "Ciberseguridad")
                )
                var expandedCarr by remember { mutableStateOf(false) }

                Box {
                    OutlinedButton(
                        onClick = { expandedCarr = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            if (carreraSeleccionada.isEmpty()) "Selecciona una carrera" else carreraSeleccionada,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start,
                            color = if (carreraSeleccionada.isEmpty()) Color.Gray else Color.Black
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = expandedCarr,
                        onDismissRequest = { expandedCarr = false }
                    ) {
                        categoriasCarreras.forEach { (categoria, items) ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        categoria,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1A237E)
                                    )
                                },
                                onClick = { },
                                enabled = false
                            )
                            items.forEach { carrera ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            modifier = Modifier.padding(start = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (carrera == carreraSeleccionada) {
                                                Icon(
                                                    Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = Color(0xFF2E7D32),
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                            }
                                            Text(carrera)
                                        }
                                    },
                                    onClick = {
                                        carreraSeleccionada = carrera
                                        expandedCarr = false
                                    }
                                )
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }

                if (carreraSeleccionada.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC))
                    ) {
                        Text(
                            "Carrera seleccionada: $carreraSeleccionada",
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFAD1457)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoListaJerarquica(onBack: () -> Unit) {
    data class CategoriaMenu(
        val nombre: String,
        val icono: ImageVector,
        val subcategorias: List<String>
    )

    val menuItems = listOf(
        CategoriaMenu("Dispositivos", Icons.Default.PhoneAndroid, listOf("Smartphones", "Tablets", "Laptops", "Smartwatches")),
        CategoriaMenu("Accesorios", Icons.Default.Headphones, listOf("Audifonos", "Cargadores", "Fundas", "Protectores de pantalla", "Cables")),
        CategoriaMenu("Software", Icons.Default.Computer, listOf("Sistemas Operativos", "Editores de codigo", "Diseno grafico", "Productividad", "Seguridad")),
        CategoriaMenu("Redes", Icons.Default.Wifi, listOf("Router WiFi", "Switches", "Cables de red", "Antenas", "Repetidores de senal")),
        CategoriaMenu("Componentes", Icons.Default.Memory, listOf("Procesadores", "Tarjetas madre", "Memoria RAM", "Almacenamiento", "Fuentes de poder", "Gabinetes")),
        CategoriaMenu("Perifericos", Icons.Default.Mouse, listOf("Teclados", "Mouse", "Monitores", "Impresoras", "Escanner", "Webcams")),
        CategoriaMenu("Servicios", Icons.Default.Cloud, listOf("Almacenamiento en la nube", "Hosting", "Dominios", "APIs", "Base de datos")),
        CategoriaMenu("Herramientas", Icons.Default.Build, listOf("Git", "Docker", "VS Code", "Android Studio", "Postman", "Figma"))
    )

    val expandedItems = remember { mutableStateMapOf<String, Boolean>() }
    var selectedSub by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ExpandableListView", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF1A237E), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Toca una categoria para expandir/colapsar. Toca una subcategoria para seleccionarla.",
                        fontSize = 12.sp,
                        color = Color(0xFF1A237E)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                menuItems.forEach { categoria ->
                    val isExpanded = expandedItems[categoria.nombre] == true

                    item(key = "parent_${categoria.nombre}") {
                        Card(
                            onClick = {
                                expandedItems[categoria.nombre] = !isExpanded
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isExpanded) Color(0xFFE8EAF6) else Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = if (isExpanded) 4.dp else 2.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            Color(0xFF1A237E).copy(alpha = 0.1f),
                                            RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        categoria.icono,
                                        contentDescription = null,
                                        tint = Color(0xFF1A237E),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        categoria.nombre,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        "${categoria.subcategorias.size} subcategorias",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                                Icon(
                                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = Color(0xFF1A237E)
                                )
                            }
                        }
                    }

                    if (isExpanded) {
                        items(
                            items = categoria.subcategorias,
                            key = { "child_${categoria.nombre}_$it" }
                        ) { subcategoria ->
                            val isSelected = selectedSub == "${categoria.nombre}_$subcategoria"

                            Card(
                                onClick = {
                                    selectedSub = if (isSelected) null else "${categoria.nombre}_$subcategoria"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 32.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) Color(0xFFC5CAE9) else Color(0xFFF5F5F5)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = Color(0xFF1A237E),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        subcategoria,
                                        modifier = Modifier.weight(1f),
                                        fontSize = 14.sp
                                    )
                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF2E7D32),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
