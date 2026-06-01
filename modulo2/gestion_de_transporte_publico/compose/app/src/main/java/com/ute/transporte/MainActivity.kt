package com.ute.transporte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.ute.transporte.material.*
import com.ute.transporte.ui.theme.viewmodel.Paso01_TextFieldScreen as Paso01_ViewModelScreen
import com.ute.transporte.ui.theme.viewmodel.Paso02_UiStateScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Paso07Ejercicio()
            }
        }
    }
}
