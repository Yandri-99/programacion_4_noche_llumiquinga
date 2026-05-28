// MainActivity.kt — temporalmente inyectar el repositorio para probar
package com.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.shopapp.domain.model.Category
import com.shopapp.domain.repository.CategoryRepository
import com.shopapp.theme.ShopAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var categoryRepository: CategoryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
                    var status     by remember { mutableStateOf("Conectando...") }

                    LaunchedEffect(Unit) {
                        categoryRepository.getCategories()
                            .onSuccess {
                                categories = it
                                status     = "✅ ${it.size} categorías del backend"
                            }
                            .onFailure {
                                status = "❌ ${it.message}"
                            }
                    }

                    VerificationScreen(
                        connectionStatus = status,
                        categories       = categories,
                    )
                }
            }
        }
    }
}