package com.ute.techdash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ute.techdash.ui.hardware.DashboardHardware
import com.ute.techdash.ui.hardware.PantallaMenu
import com.ute.techdash.ui.hardware.gps.PantallaGPS
import com.ute.techdash.ui.hardware.sensores.PantallaSensores
import com.ute.techdash.ui.theme.TechDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechDashTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "menu") {
                    composable("menu")      { PantallaMenu(navController) }
                    composable("gps")       { PantallaGPS() }
                    composable("sensores")  { PantallaSensores() }
                    composable("dashboard") { DashboardHardware() }
                }
            }
        }
    }
}