fun main(){
    println("Operadores Logicos")
    val esMayor = true
    val tienePermiso = false
    val estaActivo = true
    
    println("operador And &&")
    println("$esMayor && $tienePermiso = ${esMayor && tienePermiso}")
    println("$esMayor && $estaActivo = ${esMayor && estaActivo}")
    
    println("Or Logico ||")
    println("$esMayor || $tienePermiso = ${esMayor || tienePermiso}")
    println("$esMayor || $estaActivo = ${esMayor || estaActivo}")
    println("$esMayor || $tienePermiso = ${esMayor || tienePermiso}")
    println("$esMayor || $tienePermiso || $estaActivo = ${esMayor || tienePermiso || estaActivo}")
    
    println("Not Logico !")
    println("! $esMayor = ${!esMayor}")
    println("! $estaActivo = ${!estaActivo}")  
    val texto = readLine()
    println(texto)
}