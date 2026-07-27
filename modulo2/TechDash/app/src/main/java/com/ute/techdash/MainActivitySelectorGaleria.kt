package com.ute.techdash

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ute.techdash.ui.multimedia.SelectorGaleria
import com.ute.techdash.ui.multimedia.VisorImagen
import com.ute.techdash.ui.theme.TechDashTheme

class MainActivitySelectorGaleria : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechDashTheme {
                var uriActual by remember { mutableStateOf<Uri?>(null) }
                Column {
                    SelectorGaleria(onImagenSeleccionada = { uriActual = it })
                    uriActual?.let { VisorImagen(uri = it) }
                }
            }
        }
    }
}