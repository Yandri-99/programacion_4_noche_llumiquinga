void main() {
  // var — tipo inferido (como val en Kotlin)
  var nombre = 'Ana';           // String
  var edad   = 28;              // int
  var precio = 89.99;           // double
  var activo = true;            // bool

  // Tipo explícito
  String apellido = 'García';
  int    stock    = 100;
  double pi       = 3.14159;
  bool   visible  = false;

  // final — no se puede reasignar (como val en Kotlin)
  final ciudad = 'Madrid';
  // ciudad = 'Barcelona';  // ERROR — final no se puede reasignar

  // const — constante en tiempo de compilación (como const en Kotlin)
  const gravedad = 9.8;
  const pi2      = 3.14159;

  // Diferencia clave: final vs const
  final ahora  = DateTime.now();   // OK — se evalúa en runtime
  // const ahora = DateTime.now(); // ERROR — DateTime.now() no es constante de compilación

  print('$nombre $apellido tiene $edad años en $ciudad');
}

// var — mutable, tipo inferido
var contador = 0;
contador = 1;          // OK

// final — inmutable referencia, evaluado en runtime
final lista = [1, 2, 3];
lista.add(4);          // OK — la referencia es final, no el contenido
// lista = [5, 6];     // ERROR — no se puede reasignar la referencia

// const — inmutable profundo, evaluado en compilación
const colores = ['rojo', 'azul'];
// colores.add('verde'); // ERROR — lista const es completamente inmutable

void main() {
  // Tipo no-nullable — NUNCA puede ser null
  String nombre = 'Ana';
  // nombre = null;       // ERROR de compilación

  // Tipo nullable — puede ser null (añadir ?)
  String? apellido = null;   // OK
  apellido = 'García';       // OK

  // Operadores de null safety
  String? ciudad;

  // ?. — safe call (igual que en Kotlin)
  print(ciudad?.length);      // null — no lanza excepción

  // ?? — operador Elvis (igual que ?: en Kotlin)
  String resultado = ciudad ?? 'Sin ciudad';
  print(resultado);           // Sin ciudad

  // ! — non-null assertion (igual que !! en Kotlin) — úsalo con precaución
  String ciudadSegura = ciudad!;  // lanza si ciudad es null

  // Null check con if
  if (apellido != null) {
    print(apellido.length);   // smart cast — ya es String aquí
  }

  // late — inicialización diferida (como lateinit en Kotlin)
  late String token;
  token = 'abc123';           // debe asignarse antes de usar
  print(token);
}

void main() {
  // List — lista ordenada (como List en Kotlin)
  List<String> frutas   = ['manzana', 'banana', 'cereza'];
  var          numeros  = [1, 2, 3, 4, 5];       // tipo inferido: List<int>

  print(frutas[0]);         // manzana
  print(frutas.length);     // 3
  frutas.add('dátil');
  frutas.remove('banana');

  // Map — clave → valor (como Map en Kotlin)
  Map<String, int> edades = {
    'Ana':   28,
    'Luis':  34,
    'María': 25,
  };

  print(edades['Ana']);     // 28
  print(edades['Pedro']);   // null — clave no existe
  edades['Carlos'] = 40;    // añadir

  // Set — sin duplicados (como Set en Kotlin)
  Set<String> tags = {'flutter', 'dart', 'mobile'};
  tags.add('flutter');      // ignorado — ya existe
  print(tags.length);       // 3

  // Spread operator — para combinar colecciones
  var lista1 = [1, 2, 3];
  var lista2 = [4, 5, 6];
  var combinada = [...lista1, ...lista2];  // [1, 2, 3, 4, 5, 6]
  print(combinada);

  // Collection if — elementos condicionales
  bool mostrarExtra = true;
  var items = [
    'elemento1',
    'elemento2',
    if (mostrarExtra) 'elemento3',  // solo si la condición es true
  ];

  // Collection for — generar elementos
  var cuadrados = [for (var i = 1; i <= 5; i++) i * i];
  print(cuadrados);  // [1, 4, 9, 16, 25]
}