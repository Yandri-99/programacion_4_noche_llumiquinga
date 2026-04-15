fun main(){
    println("CICLOS for")
    for (i in 1..5){
        println(i)
    }
    println("until")
    for (i in 1 until 5){
        println(i)
    }
    println("downTo")
    for (i in 10 downTo 1){
        println(i)
    }
    println("listas")
    val nombres=listOf("Ana", "Luis", "Juana")
    fot (nombre in nombres){
        print(nombre)
    }
    println("indice valor")
    for ((index, valor)in nombres.withIndex()){
        println("$index: $valor")
    }
}