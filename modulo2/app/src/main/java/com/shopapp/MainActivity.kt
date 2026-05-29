package com.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.shopapp.presentation.components.LoadingScreen
import com.shopapp.presentation.navigation.Screen
import com.shopapp.presentation.ui.auth.LoginScreen
import com.shopapp.presentation.ui.auth.RegisterScreen
import com.shopapp.presentation.viewmodel.AuthViewModel
import com.shopapp.theme.ShopAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShopAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ShopApp()
                }
            }
        }
    }
}

@Composable
fun ShopApp() {
    val authViewModel: AuthViewModel = hiltViewModel()

    val isCheckingSession by authViewModel.isCheckingSession.collectAsState()
    val isAuthenticated   by authViewModel.isAuthenticated.collectAsState()
    val isStaff           by authViewModel.isStaff.collectAsState()

    val navController = rememberNavController()

    // Splash mientras valida sesión
    if (isCheckingSession) {
        LoadingScreen(message = "Iniciando ShopApp...")
        return
    }

    val startDestination = when {
        !isAuthenticated -> Screen.Login.route
        isStaff          -> Screen.AdminDashboard.route
        else             -> Screen.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        // ── LOGIN ───────────────────────────────
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { staff ->
                    val dest = if (staff) {
                        Screen.AdminDashboard.route
                    } else {
                        Screen.Home.route
                    }

                    navController.navigate(dest) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
            )
        }

        // ── REGISTER ────────────────────────────
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { staff ->
                    val dest = if (staff) {
                        Screen.AdminDashboard.route
                    } else {
                        Screen.Home.route
                    }

                    navController.navigate(dest) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
            )
        }

        // ── HOME (con logout) ───────────────────
        composable(Screen.Home.route) {
            HomeTestScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── ADMIN DASHBOARD (con logout) ────────
        composable(Screen.AdminDashboard.route) {
            AdminDashboardTestScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

// ─────────────────────────────────────────────
// Home temporal con logout
// ─────────────────────────────────────────────
@Composable
fun HomeTestScreen(
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Home — M4",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}

// ─────────────────────────────────────────────
// Admin temporal con logout
// ─────────────────────────────────────────────
@Composable
fun AdminDashboardTestScreen(
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Admin Dashboard — M8",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}
