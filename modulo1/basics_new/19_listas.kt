fun main() {
    println("Listas")
    println("Inmutables")
    val frutas = listOf("manzana", "banana", "cereza", "pera", "piña")
    println(frutas)
    
    println("Size: ${frutas.size}")
    println("Mostrar el elemento indice 0: ${frutas[0]}") 
    println("Mostrar el primer elemento: ${frutas.first()}")
    println("Mostrar el ultimo elemento: ${frutas.last()}")
    
    println("Mostrar el elemento indice 2: ${frutas.get(2)}")
    println("Mostrar contenido segun indice: ${frutas.indexOf("banana")}")
    println("Verificar existencia de un elemento: ${frutas.contains("piña")}")
    println("Verificar existencia de un elemento: ${"naranja" in frutas}")
    
    //Sublista
    println("Sublista: ${frutas.sublist(1,4)}")
    println("Tomar primeros 2 elementos: ${frutas.take(2)}")
    println("Suprimir tres primeros elementos: ${frutas.drop(3)}")
    println("Tomar los ultimos dos elementos: ${frutas.takeLast (2)}")
    println(frutas)
    
    println("Mutables")
    val colores=mutableListOf("blanco", "azul", "amarillo","rojo")
    println(colores)
    colores.add("verde")
    println(colores)
    colores.add(0,"morado")
    println(colores)
    colores.add("verde")
    println(colores)
    colores.[1]="gris"
    println(colores)
    
    println("Array deque")
    val numeros=ArrayDeque<Int>()
    println(numeros)
    numeros.addFirst(1)
    println(numeros)
    numeros.addFirst(3)
    println(numeros)
    numeros.addLast(2)
    println(numeros)
    numeros.removeFirst()
    println(numeros)
    numeros.removeFirst()
    println(numeros)
}