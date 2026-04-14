fun main() {
    println("Condicional when")
    println("Codigo de especialidad (1-7)")
    val codigo = readLine()?.toIntOrNull()?:0
    val especialidad = when(codigo){
        1->"Medicina General"
        2->"Pediatria"
        3->"Cardiologia"
        4->"Genecologia"
        5->"Traumatologia"
        6->"Neurologia"
        7->"Darmatologia"
        else ->"Especialidad no registrada"
   }
   println("Especialidad: $especialidad")
}