// Sin open — no se puede heredar (protección por defecto)
class Animal(val nombre: String)
// class Perro : Animal("Rex")  // ERROR — Animal es final

// Con open — la jerarquía está diseñada para ello
open class Animal(val nombre: String, val sonido: String) {
    // open — la subclase PUEDE sobreescribir
    open fun hacerSonido() = println("$nombre dice: $sonido")
    open fun descripcion() = "Soy $nombre"

    // Sin open — la subclase NO puede sobreescribir
    fun respirar() = println("$nombre respira")
}

// HERENCIA: Perro reutiliza todo de Animal y especializa hacerSonido
class Perro(nombre: String) : Animal(nombre, "Guau") {
    override fun hacerSonido() {
        super.hacerSonido()          // reutiliza la implementación del padre
        println("(mueve la cola)")   // añade comportamiento propio
    }
    override fun descripcion() = "${super.descripcion()}, un perro"
}

class Gato(nombre: String, val interior: Boolean) : Animal(nombre, "Miau") {
    override fun descripcion() =
        "${super.descripcion()}, un gato ${if (interior) "de interior" else "callejero"}"
}

fun main() {
    val perro = Perro("Rex")
    perro.hacerSonido()
    // Rex dice: Guau
    // (mueve la cola)

    val gato = Gato("Misi", true)
    println(gato.descripcion())  // Soy Misi, un gato de interior

    // Herencia — Perro y Gato tienen todo lo de Animal más lo propio
    perro.respirar()  // Rex respira — heredado de Animal
}