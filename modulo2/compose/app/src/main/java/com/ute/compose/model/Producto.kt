package com.ute.compose.model

// Producto local — usado en los pasos 1 al 4
data class Producto(
    val id:        Int,
    val nombre:    String,
    val precio:    Double,
    val categoria: String,
    val stock:     Int,
    val activo:    Boolean = true
)

// Producto de la API — mapea el JSON de los pasos 5 y 6
data class ProductoApi(
    val id:            Int,
    val name:          String,
    val slug:          String,
    val price:         String,
    val stock:         Int,
    val is_active:     Boolean,
    val url_image:     String,
    val category_name: String
)

data class PaginatedResponse(
    val count:    Int,
    val next:     String?,
    val previous: String?,
    val results:  List<ProductoApi>
)

// Datos locales de muestra para los primeros pasos
val productosDeMuestra = listOf(
    Producto(1, "Teclado mecánico",  89.99,  "Periféricos", 15),
    Producto(2, "Monitor 27\"",      349.99, "Pantallas",   8),
    Producto(3, "Mouse inalámbrico", 29.99,  "Periféricos", 42, activo = false),
    Producto(4, "Auriculares BT",    149.99, "Audio",       23),
    Producto(5, "Webcam HD",         59.99,  "Cámaras",     11),
    Producto(6, "Hub USB-C",         39.99,  "Accesorios",  30),
    Producto(7, "SSD 1TB",           89.99,  "Almacenamiento", 19),
    Producto(8, "Mousepad XL",       24.99,  "Periféricos", 55),
)