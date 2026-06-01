package com.ute.transporte.model

data class Vehiculo(
    val id:         Int,
    val nombre:     String,
    val capacidad:  Int,
    val tipo:       String,
    val estado:     String,
    val activo:     Boolean = true
)

data class VehiculoApi(
    val id:             Int,
    val name:           String,
    val slug:           String,
    val capacity:       Int,
    val status:         String,
    val is_active:      Boolean,
    val url_image:      String,
    val vehicle_type:   String
)

data class PaginatedResponse(
    val count:    Int,
    val next:     String?,
    val previous: String?,
    val results:  List<VehiculoApi>
)

val vehiculosDeMuestra = listOf(
    Vehiculo(1, "Bus Básico",       40, "Autobús",    "Operativo"),
    Vehiculo(2, "Bus Articulado",   80, "Articulado", "Operativo"),
    Vehiculo(3, "Bus Alimentador",  30, "Alimentador","Mantenimiento", activo = false),
    Vehiculo(4, "Bus Eléctrico",    50, "Eléctrico",  "Operativo"),
    Vehiculo(5, "Minibus",          20, "Minibus",    "Operativo"),
    Vehiculo(6, "Bus Ejecutivo",    35, "Ejecutivo",  "Operativo"),
    Vehiculo(7, "Bus Doble Piso",   60, "Doble Piso", "Operativo"),
    Vehiculo(8, "Bus Urbano",       45, "Autobús",    "Operativo"),
)
