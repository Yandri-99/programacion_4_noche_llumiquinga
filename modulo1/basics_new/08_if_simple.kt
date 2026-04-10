fun main() {
    println("Control de flujo") 
    println("If Simple")
    println("Saturacion de oxigeno")
    val saturacion = readLine()?.toDoubleOrNull()?:35.5
    if(saturacion <= 95){
        println("Alerta: Saturacion baja - evaluar suministro de oxigeno ")
    }
    if(saturacion >= 95){
        println("saturacion Alta")
    }
    println("Saturacion registrada: $saturacion")
}