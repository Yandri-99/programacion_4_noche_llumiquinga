class Usuario(val nombre: String, val email: String) {
    val nombreNormalizado: String
    val dominioEmail: String

    init {
        // Encapsulamiento en acción: validamos antes de construir
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.contains("@")) { "Email inválido: $email" }

        nombreNormalizado = nombre.trim().lowercase()
        dominioEmail      = email.substringAfter("@")
    }
}

fun main() {
    val u = Usuario("  Ana García  ", "ana@kotlin.dev")
    println(u.nombreNormalizado)  // ana garcía
    println(u.dominioEmail)       // kotlin.dev

    // Usuario("", "invalido")   // IllegalArgumentException — require falla
}


