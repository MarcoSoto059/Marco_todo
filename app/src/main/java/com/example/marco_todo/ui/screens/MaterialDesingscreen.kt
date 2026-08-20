package com.example.marco_todo.ui.screens

import android.content.Context
import android.graphics.Color
import android.util.Patterns
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.marco_todo.ui.theme.Marco_todoTheme
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


import com.example.marco_todo.ui.components.BaseScreen

@Composable
fun MaterialDesignApp(onGoHomeScreen: () -> Unit) {
    MaterialWidgetsScreen(onGoHomeScreen)
}

@Composable
fun MaterialWidgetsScreen(onGoHomeScreen: () -> Unit) {

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var intereses by remember {
        mutableStateOf(setOf<String>())
    }
    var errorNombre by remember {
        mutableStateOf<String?>(null)
    }
    var errorCorreo by remember {
        mutableStateOf<String?>(null)
    }
    var resumen by remember {
        mutableStateOf("Aquí aparecerán los datos del perfil.")
    }

    BaseScreen(
        title = "Interoperabilidad Material",
        onBack = onGoHomeScreen
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = "Perfil con Android Views",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Integración de componentes clásicos en Compose",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            TarjetaInformacion()
            Spacer(modifier = Modifier.height(24.dp))

            CampoMaterial(
                titulo = "Nombre",
                valor = nombre,
                error = errorNombre,
                esCorreo = false
            ) {
                nombre = it
                errorNombre = null
            }
            Spacer(modifier = Modifier.height(12.dp))

            CampoMaterial(
                titulo = "Correo electrónico",
                valor = correo,
                error = errorCorreo,
                esCorreo = true
            ) {
                correo = it
                errorCorreo = null
            }
            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Selecciona tus intereses",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            GrupoIntereses(
                seleccionados = intereses
            ) {
                intereses = it
            }
            Spacer(modifier = Modifier.height(24.dp))

            TarjetaResumen(resumen)
            Spacer(modifier = Modifier.height(24.dp))

            BotonGuardar {
                errorNombre = null
                errorCorreo = null
                var datosCorrectos = true

                if (nombre.isBlank()) {
                    errorNombre = "Ingresa tu nombre"
                    datosCorrectos = false
                }
                if (correo.isBlank()) {
                    errorCorreo = "Ingresa tu correo electrónico"
                    datosCorrectos = false

                } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    errorCorreo = "Ingresa un correo válido"
                    datosCorrectos = false
                }

                if (datosCorrectos) {
                    val interesesTexto =
                        if (intereses.isEmpty()) {
                            "Ninguno"
                        } else {
                            intereses.joinToString(", ")
                        }
                    resumen = """
                        Nombre: $nombre
                        Correo: $correo
                        Intereses: $interesesTexto
                    """.trimIndent()
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}


// CardView
@Composable
fun TarjetaInformacion() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            CardView(context).apply {
                radius = context.dp(14).toFloat()
                cardElevation = context.dp(5).toFloat()
                setContentPadding(
                    context.dp(16),
                    context.dp(16),
                    context.dp(16),
                    context.dp(16)
                )

                val contenido = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    addView(
                        TextView(context).apply {
                            text = "Acerca de la aplicación"
                            textSize = 17f
                            setTextColor(Color.DKGRAY)
                        }
                    )

                    addView(
                        TextView(context).apply {
                            text =
                                "Ejemplo funcional de componentes Material Design en Android."
                            textSize = 14f
                            setTextColor(Color.GRAY)
                            setPadding(
                                0,
                                context.dp(8),
                                0,
                                0
                            )
                        }
                    )
                }
                addView(contenido)
            }
        }
    )
}

@Composable
fun CampoMaterial(
    titulo: String,
    valor: String,
    error: String?,
    esCorreo: Boolean,
    onValueChange: (String) -> Unit
) {
    val accionActual by rememberUpdatedState(onValueChange)

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->

            val materialContext = context.materialContext()
            val textInputLayout =
                TextInputLayout(materialContext).apply {
                    hint = titulo
                    boxBackgroundMode =
                        TextInputLayout.BOX_BACKGROUND_OUTLINE
                }

            val editText =
                TextInputEditText(materialContext).apply {
                    inputType =
                        if (esCorreo) {
                            android.text.InputType.TYPE_CLASS_TEXT or
                                    android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        } else {
                            android.text.InputType.TYPE_CLASS_TEXT or
                                    android.text.InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                        }

                    layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    doAfterTextChanged {
                        accionActual(it.toString())
                    }
                }

            textInputLayout.addView(editText)
            textInputLayout
        },

        update = { textInputLayout ->
            textInputLayout.error = error
            val editText = textInputLayout.editText
            if (editText?.text.toString() != valor) {
                editText?.setText(valor)
                editText?.setSelection(valor.length)
            }
        }
    )
}


// ChipGroup
@Composable
fun GrupoIntereses(
    seleccionados: Set<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    val accionActual by rememberUpdatedState(onSelectionChange)
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val materialContext = context.materialContext()
            ChipGroup(materialContext).apply {
                isSingleSelection = false
                isSelectionRequired = false

                val opciones = listOf(
                    "Android",
                    "Kotlin",
                    "Diseño"
                )

                opciones.forEach { opcion ->
                    val chip = Chip(materialContext).apply {
                        text = opcion
                        isCheckable = true
                        setOnCheckedChangeListener { _, marcado ->
                            val nuevosIntereses =
                                seleccionados.toMutableSet()

                            if (marcado) {
                                nuevosIntereses.add(opcion)
                            } else {
                                nuevosIntereses.remove(opcion)
                            }
                            accionActual(nuevosIntereses)
                        }
                    }
                    addView(chip)
                }
            }
        },

        update = { grupo ->
            for (i in 0 until grupo.childCount) {
                val chip = grupo.getChildAt(i) as Chip
                val seleccionado =
                    seleccionados.contains(chip.text.toString())
                if (chip.isChecked != seleccionado) {
                    chip.isChecked = seleccionado
                }
            }
        }
    )
}


// MaterialCardView
@Composable
fun TarjetaResumen(
    resumen: String
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val materialContext = context.materialContext()
            MaterialCardView(materialContext).apply {
                radius = context.dp(14).toFloat()
                cardElevation = context.dp(5).toFloat()
                setContentPadding(
                    context.dp(16),
                    context.dp(16),
                    context.dp(16),
                    context.dp(16)
                )
                val contenido =
                    LinearLayout(materialContext).apply {
                        orientation = LinearLayout.VERTICAL
                        addView(
                            TextView(materialContext).apply {
                                text = "Resumen del perfil"
                                textSize = 18f
                            }
                        )
                        addView(
                            TextView(materialContext).apply {
                                tag = "resumen"
                                text = resumen
                                textSize = 14f
                                setPadding(
                                    0,
                                    context.dp(8),
                                    0,
                                    0
                                )
                            }
                        )
                    }
                addView(contenido)
            }
        },

        update = { tarjeta ->
            val textoResumen =
                tarjeta.findViewWithTag<TextView>("resumen")
            textoResumen.text = resumen
        }
    )
}

@Composable
fun BotonGuardar(
    onClick: () -> Unit
) {
    val accionActual by rememberUpdatedState(onClick)
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            val materialContext = context.materialContext()
            ExtendedFloatingActionButton(materialContext).apply {
                text = "Guardar perfil"
                icon = ContextCompat.getDrawable(
                    context,
                    android.R.drawable.ic_menu_save
                )

                setOnClickListener {
                    accionActual()
                    Toast.makeText(
                        context,
                        "Perfil actualizado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    )
}

fun Context.materialContext(): ContextThemeWrapper {
    return ContextThemeWrapper(
        this,
        com.google.android.material.R.style
            .Theme_Material3_DayNight_NoActionBar
    )
}

fun Context.dp(valor: Int): Int {

    return (
            valor * resources.displayMetrics.density
            ).toInt()
}