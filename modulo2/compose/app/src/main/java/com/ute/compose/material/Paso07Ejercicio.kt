package com.ute.compose.material

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Paso07Ejercicio() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Paso 1 · TextField y OutlinedTextField",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()
        HorizontalDivider()
        SumaNumeros()
    }
}

// ──  Suma de dos numeros ───────────────────────────────
@Composable
private fun SumaNumeros() {
    var producto    by remember { mutableStateOf("") }
    var cantidad    by remember { mutableStateOf("0") }
    var precio  by remember { mutableStateOf("0") }
    var subtotal    by remember { mutableStateOf("0") }
    var descuento    by remember { mutableStateOf("0") }
    var total  by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Formulario nuevo contacto",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)

        // PRODUCTO
        OutlinedTextField(
            value           = producto,
            onValueChange   = { producto = it },
            label           = { Text("Nombre del producto") },
            leadingIcon     = { Icon(Icons.Default.Person, contentDescription = null) },
            // keyboardOptions configura el teclado del sistema operativo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        // CANTIDAD
        OutlinedTextField(
            value           = cantidad,
            onValueChange   = { cantidad = it },
            label           = { Text("Ingrese la cantidad ") },
            leadingIcon     = { Icon(Icons.Default.Person, contentDescription = null) },
            // keyboardOptions configura el teclado del sistema operativo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        // PRECIO
        OutlinedTextField(
            value           = precio,
            onValueChange   = { precio = it },
            label           = { Text("Ingrese el precio unitario ") },
            leadingIcon     = { Icon(Icons.Default.Person, contentDescription = null) },
            // keyboardOptions configura el teclado del sistema operativo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        Button(
            onClick  = { /* Paso 6: mostrará un diálogo de confirmación */
                val cantidadDouble = cantidad.toDoubleOrNull()?:0.0
                val precioDouble = precio.toDoubleOrNull()?:0.0
                val subtotalCalculado = cantidadDouble * precioDouble
                var descuentoCalculado = 0.0
                if (subtotalCalculado > 50) {
                    descuentoCalculado = subtotalCalculado * 0.10
                } else if (subtotalCalculado >= 20) {
                    descuentoCalculado = subtotalCalculado * 0.05
                }
                val totalCalculado = subtotalCalculado - descuentoCalculado

                subtotal = subtotalCalculado.toString()
                descuento = descuentoCalculado.toString()
                total = totalCalculado.toString()

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text="Sumar")
        }
        Text(text = "Producto: $producto")
        Text(text = "Subtotal: $subtotal")
        Text(text = "Descuento: $descuento")
        Text(text = "Total a pagar: $total")

    }
}
@Preview(showBackground = true)
@Composable
fun Paso07EjercicioPreview() {
    MaterialTheme {
        Paso07Ejercicio()
    }
}