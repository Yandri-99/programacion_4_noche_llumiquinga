
fun main() {
    //Val Inmutable
    val nombre="Yandri"
    val edad: Int = 21
    //Var mutable
    var contador=0
    contador = contador+1
    
    println("$nombre tiene $edad años")
    
    //Tipos de datos
    val numero1: Byte = 127
    val numero2: Short = 32_767
    val numero3: Int = 12
    val numero4: Long = 2_222_222_333_222_333
    
    println(numero1)
    println(numero2)
    println(numero3)
    println(numero4)
    
    // Numero decimales
    val numero5: Float=3.14f
    val numero6: Double=3.14159265
    
    val booleano: Boolean=true
    
    //Caracteres
    val caracter: Char ='k'
    val cadena: String="Klotin"
    val inferido = "string"
    
    println("Tipo de inferido: ${inferido::class.simpleName}")
    
    //Utilidades de String
    //Expresiones
	val primerNombre="Yandri"
    val primerApellido="Llumiquinga"
    val primerNombreMayuscula=primerNombre.uppercase()
    val primerApellidoMayuscula=primerApellido.uppercase()
    
    println("Nombre Completo: ${primerNombreMayuscula} ${primerApellidoMayuscula}")
    println("Nombre Completo: ${primerNombre.uppercase()} ${primerApellido.uppercase()}")
    
    /*
     Comentario de varias
     lineas
    */
    
    // Una sola linea
}