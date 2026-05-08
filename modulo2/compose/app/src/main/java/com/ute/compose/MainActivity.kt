// MainActivity.kt
package com.ute.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.ute.compose.material.*
import com.ute.compose.ui.theme.viewmodel.Paso01_TextFieldScreen as Paso01_ViewModelScreen
import com.ute.compose.ui.theme.viewmodel.Paso02_UiStateScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // ◀ CAMBIA AQUÍ para probar cada sección:
                // S01_SaludoScreen()
                // S02_TextScreen()
                // S03_ButtonScreen()
                // S04_LayoutScreen()
                // S05_ModifierScreen()
                //S06_EstadoScreen()
                // S07_StateHoistingScreen()
                //S08_BienvenidaScreen()

                // Componentes Material 3: TextField, Card, LazyColumn, Scaffold y diálogos
                // ◀ CAMBIA AQUÍ para probar cada paso:
                // Paso01TextFieldScreen()
                //Paso02_CardScreen()
                // Paso03_LazyColumnScreen()
                // Paso04_ScaffoldScreen()
                // Paso05_NavBarScreen()
                //Paso06_DialogosScreen()   // ← paso activo

                //MaterialTheme
                // ◀ CAMBIA AQUÍ para probar cada paso:
                //Paso01SumaScreen()
                //Paso01_ViewModelScreen()
                //Paso02_UiStateScreen()
                // Paso03_NavigationScreen()
                // Paso04_DetalleScreen()  ← solo para preview, la nav lo llama
                // Paso05_RetrofitScreen()
                // Paso06_CompletoScreen()   // ← paso activo
                Paso07Ejercicio()
            }
        }
    }
}