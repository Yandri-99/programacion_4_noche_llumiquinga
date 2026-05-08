fun main() {
    println("Ciclos while")
    println("while basico")
    var contador=1
    while (contador<=5){
        println(contador)
        contador++
    }
    
    contador = 1
    do{
        println(contador)
        contador++
    }while(contador<=5)
    
    println("brake continue")
    contador=1
    while (contador<=10){
        contador++
        if(contador ==3) continue
        if(contador==7)break
        println(contador)
    }
    var input: String
    while (true){
        println("Escribe 'salir' para terminar:")
        input=readLine()?; ""
        if(input=="salir")break
        println("Ingresaste $input")
    }
}