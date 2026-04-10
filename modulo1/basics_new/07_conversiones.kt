fun main() {
  	println("conversiones")
  	val entero: Int = 12
  	
  	val decimal: Double = entero.toDouble()
  	val longVariable: Long = entero.toLong()
  	val variableString: String = entero.toString()
  	
  	println("to Double $decimal")
  	println("to Long $longVariable")
  	println("to String $variableString")
   
   println("String a Numerico")
   val numero1 = "1234".toInt()
   val numero2 = "3.1415".toDouble()
   
   val invalido = "abcd".toIntOrNull()
   println(invalido)
   
   }
