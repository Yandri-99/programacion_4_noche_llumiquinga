package com.shopapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopapp.domain.model.Category
import com.shopapp.theme.*

@Composable
fun VerificationScreen(
    connectionStatus: String = "Sin conectar",
    categories: List<Category> = emptyList(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "ShopApp",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Accent,
            )

            Text(
                text = "Módulo 2 · Conexión con backend",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
            )

            EnvCard(
                items = listOf(
                    "Kotlin" to "2.0.21",
                    "Compose BOM" to "2024.10.01",
                    "Material 3" to "✓",
                    "Hilt" to "2.52",
                    "Retrofit" to "2.11.0",
                    "API URL" to BuildConfig.API_BASE_URL,
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = connectionStatus,
                style = MaterialTheme.typography.bodyMedium,
                color = if (connectionStatus.startsWith("✅")) Success else Error,
            )

            Spacer(modifier = Modifier.height(16.dp))

            categories.take(3).forEach { cat ->
                Text(
                    text = "• ${cat.name} (${cat.totalProducts} productos)",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }
        }
    }
}

@Composable
private fun EnvCard(items: List<Pair<String, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Text(
            text = "Estado del entorno",
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = item.first,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
                Text(
                    text = item.second,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                )
            }

            if (index < items.lastIndex) {
                HorizontalDivider(color = BorderLight, thickness = 0.5.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenPreview() {
    ShopAppTheme {
        VerificationScreen()
    }
}