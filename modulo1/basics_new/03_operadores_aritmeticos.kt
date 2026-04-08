//Operadores aritmeticos
fun main() {
    
    val numero1=10
    val numero2=2
    
    println("Suma")
    println("numero1+numero2=${numero1+numero2}")
    println("Resta")
    println("numero1-numero2= ${numero1-numero2}")
    println("Multiplicacion")
    println("numero1*numero2= ${numero1*numero2}")
    println("Divison")
    println("numero1/numero2= ${numero1/numero2}")
    println("Modulo")
    println("numero1%numero2= ${numero1%numero2}")
    
    println("Operadores de Asignacion Compuestas")
    var x=10
    x+=5
    println("x+=5 ${x}")
    x-=3
    println("x-=3 ${x}")
    x*=2
    println("x*3 ${x}")
    x/=4
    println("x/=3 ${x}")
    x%=4
    println("x%3 ${x}")
    
    // Incremento decremento
    var contador=0
    contador++
    println("contador++ ${contador}")
    contador --
    println("contador-- ${contador}")
}