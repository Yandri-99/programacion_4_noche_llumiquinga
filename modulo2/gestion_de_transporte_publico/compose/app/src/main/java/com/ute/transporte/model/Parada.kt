package com.ute.transporte.model

data class Parada(
    val id:        Int,
    val nombre:    String,
    val direccion: String,
    val ruta:      String,
    val favorito:  Boolean = false
)

val paradasDeMuestra = listOf(
    Parada(1, "Terminal Quitumbe",    "Av. Quitumbe Ñan",         "Ruta 1", favorito = true),
    Parada(2, "Plaza San Francisco",  "Calle Bolívar y Chile",    "Ruta 2"),
    Parada(3, "Parque El Ejido",      "Av. Patria y 10 de Agosto","Ruta 1", favorito = true),
    Parada(4, "Estación Río Coca",    "Av. Eloy Alfaro",          "Ruta 3"),
    Parada(5, "Parque La Carolina",   "Av. de los Shyris",        "Ruta 2"),
    Parada(6, "Terminal Carcelén",    "Av. Carcelén",             "Ruta 3"),
    Parada(7, "Plaza Foch",           "Av. Amazonas y Reina V.",   "Ruta 1", favorito = true),
    Parada(8, "Estación Marín",       "Av. 24 de Mayo",           "Ruta 2"),
)
