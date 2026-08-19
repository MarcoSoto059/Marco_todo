package com.example.marco_todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marco_todo.ui.screens.AcercaDeScreen
import com.example.marco_todo.ui.screens.BarrasIndicadoresScreen
import com.example.marco_todo.ui.screens.ButtonsScreen
import com.example.marco_todo.ui.screens.HomeScreen
import com.example.marco_todo.ui.screens.LoginScreen
import com.example.marco_todo.ui.screens.MultimediaImagesScreen
import com.example.marco_todo.ui.screens.NavegacionScreen
import com.example.marco_todo.ui.screens.TextWidgetScreen
import com.example.marco_todo.ui.screens.SelectionScreen
import com.example.marco_todo.ui.screens.CollectionListScreen
import com.example.marco_todo.ui.screens.MaterialDesignApp
import com.example.marco_todo.ui.screens.GoogleScreen
import com.example.marco_todo.ui.screens.JetpackComposeScreen
import com.example.marco_todo.ui.screens.DialogosYMensajesScreen
import com.example.marco_todo.ui.screens.FechaYHoraScreen
import com.example.marco_todo.ui.screens.ContenedoresDesplazablesScreen
import com.example.marco_todo.ui.screens.LayoutScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {
        composable("Login") {
            LoginScreen(onLoginScreen = {
                navController.navigate("home") {
                    popUpTo("Login") { inclusive = true }
                }
            })
        }

        composable("home") {
            HomeScreen(
                onGoBarrasIndicadoresScreen = { navController.navigate("barrasindicadores") },
                onGoNavigationScreen = { navController.navigate("navegacion") },
                onGoButtonsScreen = { navController.navigate("botones") },
                onGoMultimediaScreen = { navController.navigate("multimedia") },
                onGoTextWidgetScreen = { navController.navigate("textos") },
                onGoSelectionScreen = { navController.navigate("seleccion") },
                onGoCollectionListScreen = { navController.navigate("listas") },
                onGoMaterialDesignScreen = { navController.navigate("diseño") },
                onGoGoogleScreen = { navController.navigate("google") },
                onGoJetpackComposeScreen = { navController.navigate("jetpackcompose") },
                onGoLayoutScreen = { navController.navigate("layout") },
                onGoFechaHoraScreen = { navController.navigate("fecha") },
                onGoDialogosScreen = { navController.navigate("dialogos") },
                onGoContenedoresScreen = { navController.navigate("contenedores") },
                onGoAcercaDeScreen = { navController.navigate("acerca") },
                onLogout = { navController.navigate("Login") { popUpTo(0) } }
            )
        }

        // Pantallas con parámetro antiguo 'onGoHomeScreen'
        composable("barrasindicadores") {
            BarrasIndicadoresScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("navegacion") {
            NavegacionScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("botones") {
            ButtonsScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("multimedia") {
            MultimediaImagesScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("seleccion") {
            SelectionScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("listas") {
            CollectionListScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("diseño") {
            MaterialDesignApp(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("google") {
            GoogleScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("jetpackcompose") {
            JetpackComposeScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("textos") {
            TextWidgetScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("layout") {
            LayoutScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("fecha") {
            FechaYHoraScreen(onGoBack = { navController.popBackStack() })
        }
        composable("dialogos") {
            DialogosYMensajesScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("contenedores") {
            ContenedoresDesplazablesScreen(onGoHomeScreen = { navController.popBackStack() })
        }
        composable("acerca") {
            AcercaDeScreen(onGoHomeScreen = { navController.popBackStack() })
        }
    }
}