
interface GPS { fun localizar() = println("Localizando por GPS") }
interface Radio { fun localizar() = println("Localizando por Radio") }

class SistemaLocalizacion : GPS, Radio {
    override fun localizar() {
        super<GPS>.localizar()
        super<Radio>.localizar()
        println("Y fusionando datos de localizacion")
    }
}
