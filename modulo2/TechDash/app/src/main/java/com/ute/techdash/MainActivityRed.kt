package com.ute.techdash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ute.techdash.ui.hardware.red.BannerConectividad
import com.ute.techdash.ui.hardware.red.ConectividadRepository
import com.ute.techdash.ui.hardware.sensores.PantallaSensores
import com.ute.techdash.ui.theme.TechDashTheme

class MainActivityRed : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechDashTheme {
                val context = LocalContext.current
                val repositorioRed = remember { ConectividadRepository(context) }
                Column {
                    BannerConectividad(repositorioRed)
                    PantallaSensores()    // sustituye por PantallaGPS() si prefieres
                }
            }
        }
    }
}