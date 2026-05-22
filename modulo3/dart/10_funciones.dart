//Funcion sin parametros y sin retorno
void saludar() {
  print('hola mundo');
}

//Funcion con parametros y sin retorno
void saludoConParametro(String nombre) {
  print('hola $nombre');
}

//Funcion con parametros y con retorno
int sumar(int a, int b){
  return a+b;
}

//Funcion sin parametros y con retorno
int obtenerNumero() {
  return 10;
}

//Funcion flecha (arrow function)
int multiplicar(int a, int b) => a * b;

//Funcion con parametros opcionales
void saludarOpcional(String nombre, [String apellido='Sin Apellido'])

//Funcion con parametros nombrados
void registroUsuario({
  required String nombre,
  required int edad,
}){
  print("Hola $nombre $apellido");
}
void main() {
  saludar();
  saludoConParametro('Pedro Perez');
  int numero = obtenerNumero();
  print(numero);
  print('el numero es: ${obtenerNumero()}');
  print('la suma es: ${sumar(5,5)}');
  print('la multiplicacion es : ${multiplicar(5,5)}');
  saludarOpcional('fran','Higuera');
  saludarOpcional('fran');
  registroUsuario(
    nombre: 'Ana',
    edad: 22,
  );
  // Dart puede inferir el tipo de retorno, pero es buena práctica declararlo
// explícitamente en funciones públicas para mejorar la legibilidad.

// Con tipo explícito — recomendado
String formatearPrecio(double precio) => '\$${precio.toStringAsFixed(2)}';

// Sin tipo — Dart infiere que retorna String
String formatearPrecioSinTipo(double precio) => '\$${precio.toStringAsFixed(2)}';

void main() {
  saludar();
  saludoConParametro('Pedro Perez');
  int numero = obtenerNumero();
  print(numero);
  print('el numero es: ${obtenerNumero()}');
  print('la suma es: ${sumar(5,5)}');
  print('la multiplicacion es : ${multiplicar(5,5)}');
  saludarOpcional('fran','Higuera');
  saludarOpcional('fran');
  registroUsuario(
    nombre: 'Ana',
    edad: 22,
  );
  print(formatearPrecio(1299.9));
  print(formatearPrecioSinTipo(1299.9));
}
