
fun main() {
    println("Map - Asignacion de Conductores")
    println("Inmutables")
    val asignacionRutas = mapOf(
        "Ruta Norte" to "Carlos",
        "Ruta Sur" to "Ana",
        "Ruta Este" to "Pedro",
        "Ruta Oeste" to "Luis"
    )
    println(asignacionRutas["Ruta Norte"])
    println(asignacionRutas["Ruta Expreso"])
    println(asignacionRutas.getOrDefault("Ruta Norte", "Sin asignar"))
    println(asignacionRutas.getOrDefault("Ruta Expreso", "Sin asignar"))
    println(asignacionRutas.keys)
    println(asignacionRutas.values)
    println(asignacionRutas.entries)
    println(asignacionRutas)
    for ((ruta, conductor) in asignacionRutas) {
        println("ruta: $ruta - conductor: $conductor")
    }
    for (asignacion in asignacionRutas) {
        println("asignacion: $asignacion")
    }

    println("mutables")
    val inventarioFlota = mutableMapOf(
        "Buses" to 15,
        "Microbuses" to 8,
        "Taxis" to 12,
        "Trolebuses" to 5
    )
    inventarioFlota["Metros"] = 3
    println(inventarioFlota)
    inventarioFlota["Buses"] = 20
    println(inventarioFlota)
    inventarioFlota.remove("Taxis")
    println(inventarioFlota)
    inventarioFlota.getOrPut("Tranvias") { 4 }
    println(inventarioFlota)
    inventarioFlota.getOrPut("Buses") { 10 }
    println(inventarioFlota)
}
