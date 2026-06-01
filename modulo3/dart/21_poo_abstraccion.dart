// abstract class define el contrato — QUÉ puede hacer cualquier Forma
abstract class Forma {
  String get nombre;
  double calcularArea();     // cada forma lo implementa a su manera
  double calcularPerimetro();

  // Método concreto construido sobre la abstracción
  void describir() {
    print('$nombre — área: ${calcularArea().toStringAsFixed(2)}, '
          'perímetro: ${calcularPerimetro().toStringAsFixed(2)}');
  }
}

// Implementaciones concretas — el CÓMO es específico de cada clase
class Circulo extends Forma {
  final double radio;
  Circulo(this.radio);

  @override String get nombre => 'Círculo (r=$radio)';
  @override double calcularArea()      => 3.1416 * radio * radio;
  @override double calcularPerimetro() => 2 * 3.1416 * radio;
}

class Rectangulo extends Forma {
  final double ancho, alto;
  Rectangulo(this.ancho, this.alto);

  @override String get nombre => 'Rectángulo (${ancho}x$alto)';
  @override double calcularArea()      => ancho * alto;
  @override double calcularPerimetro() => 2 * (ancho + alto);
}

void main() {
  final formas = <Forma>[Circulo(5), Rectangulo(4, 7)];
  for (final f in formas) {
    f.describir();  // no importa qué tipo de Forma es
  }
}