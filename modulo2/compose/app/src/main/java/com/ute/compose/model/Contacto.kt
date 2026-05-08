package com.ute.compose.model

data class Contacto(
    val id:       Int,
    val nombre:   String,
    val email:    String,
    val telefono: String,
    val favorito: Boolean = false
)

// Lista de muestra — la usamos en todos los pasos
val contactosDeMuestra = listOf(
    Contacto(1, "Ana García",    "ana@ejemplo.com",    "+593 99 111 2222", favorito = true),
    Contacto(2, "Luis Martínez", "luis@ejemplo.com",   "+593 99 333 4444"),
    Contacto(3, "María López",   "maria@ejemplo.com",  "+593 99 555 6666", favorito = true),
    Contacto(4, "Carlos Ruiz",   "carlos@ejemplo.com", "+593 99 777 8888"),
    Contacto(5, "Sofía Torres",  "sofia@ejemplo.com",  "+593 99 999 0000"),
    Contacto(6, "Pedro Mora",    "pedro@ejemplo.com",  "+593 98 111 2222"),
    Contacto(7, "Elena Vega",    "elena@ejemplo.com",  "+593 98 333 4444", favorito = true),
    Contacto(8, "Diego Paz",     "diego@ejemplo.com",  "+593 98 555 6666"),
)