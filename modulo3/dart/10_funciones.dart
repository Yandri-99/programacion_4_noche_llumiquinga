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
  registroCliente(
    nombre: 'Ana',
    edad: 22,
  );
}