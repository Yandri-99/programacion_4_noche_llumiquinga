interface A { fun saludar() = println("Hola desde A") }
interface B { fun saludar() = println("Hola desde B") }

class C : A, B {
    // Obligatorio cuando ambas tienen implementación del mismo método
    override fun saludar() {
        super<A>.saludar()
        super<B>.saludar()
        println("Y desde C")
    }
}