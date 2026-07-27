package com.ute.techdash

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ute.techdash.ui.multimedia.PantallaCamara
import com.ute.techdash.ui.theme.TechDashTheme

class MainActivityCamara : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechDashTheme {
                PantallaCamara(
                    onFotoTomada = { uri -> Log.d("TechDash", "Foto guardada: $uri") },
                    onCerrar     = {}
                )
            }
        }
    }
}
