
fun main() {
    println("Listas - Rutas de Transporte")
    println("Inmutables")
    val rutas = listOf("Ruta Norte", "Ruta Sur", "Ruta Este", "Ruta Oeste", "Ruta Central")
    println(rutas)

    println("Size: ${rutas.size}")
    println("Mostrar el elemento indice 0: ${rutas[0]}")
    println("Mostrar el primer elemento: ${rutas.first()}")
    println("Mostrar el ultimo elemento: ${rutas.last()}")

    println("Mostrar el elemento indice 2: ${rutas.get(2)}")
    println("Mostrar contenido segun indice: ${rutas.indexOf("Ruta Sur")}")
    println("Verificar existencia de un elemento: ${rutas.contains("Ruta Central")}")
    println("Verificar existencia de un elemento: ${"Ruta Expreso" in rutas}")

    println("Sublista: ${rutas.subList(1, 4)}")
    println("Tomar primeros 2 elementos: ${rutas.take(2)}")
    println("Suprimir tres primeros elementos: ${rutas.drop(3)}")
    println("Tomar los ultimos dos elementos: ${rutas.takeLast(2)}")
    println(rutas)

    println("Mutables")
    val conductores = mutableListOf("Carlos", "Ana", "Pedro", "Luis")
    println(conductores)
    conductores.add("Maria")
    println(conductores)
    conductores.add(0, "Juan")
    println(conductores)
    conductores.add("Maria")
    println(conductores)
    conductores[1] = "Sofia"
    println(conductores)

    println("Array deque")
    val paradas = ArrayDeque<String>()
    println(paradas)
    paradas.addFirst("Terminal Norte")
    println(paradas)
    paradas.addFirst("Plaza Central")
    println(paradas)
    paradas.addLast("Mercado")
    println(paradas)
    paradas.removeFirst()
    println(paradas)
    paradas.removeFirst()
    println(paradas)
}
