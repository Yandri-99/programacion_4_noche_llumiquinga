fun main() {
    println("Funcion Lambda")
    val suma1: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    println(suma1(1, 2))
    
    // Inferido 
    val suma2: (Int, Int) -> Int = { a, b -> a + b }
    println(suma2(1, 2))
    
    // Parametro implicito it 
    val duplicar: (Int) -> Int = { it * 2 }
    println(duplicar(2))
}