
fun main() {
    val distanciaKm = 120
    val tiempoHoras = 2

    println("Velocidad promedio:")
    println("distancia/tiempo=${distanciaKm / tiempoHoras} km/h")
    println("Combustible total:")
    println("distancia*consumo=${distanciaKm * 5} litros")
    println("Costo por pasajero:")
    println("recaudacion/pasajeros=${1500 / 40}")
    println("Diferencia de pasajeros:")
    println("capacidad - ocupados=${50 - 35}")
    println("Excedente de carga:")
    println("pesoTotal%capacidad=${4800 % 1500}")

    var recaudacion = 200
    recaudacion += 150
    println("recaudacion+=150 $recaudacion")
    recaudacion -= 50
    println("recaudacion-=50 $recaudacion")
    recaudacion *= 2
    println("recaudacion*=2 $recaudacion")
    recaudacion /= 3
    println("recaudacion/=3 $recaudacion")
    recaudacion %= 100
    println("recaudacion%=100 $recaudacion")

    var viajes = 0
    viajes++
    println("viajes++ $viajes")
    viajes--
    println("viajes-- $viajes")
}
