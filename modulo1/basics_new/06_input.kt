fun main() {
    println("Inputs")
    println("Escribe Nombre")
    val nombre = readLine()?:"anonimo"
    println("Hola $nombre")
    
    println("Escriba su edad :")
    val edadStr = readLine()
    println("Su edad es: $edadStr")
    val edadNum = edadStr?.toDouble()
    print("El doble de edad es: ${edadNum!! * 2}")  
}