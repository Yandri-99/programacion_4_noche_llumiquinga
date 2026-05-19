void main() {
  final nombre = 'Ana';
  final edad   = 28;

  // Interpolación con $ (igual que en Kotlin)
  print('Hola, $nombre');                    // Hola, Ana

  // Expresión con ${ }
  print('${nombre.toUpperCase()} tiene ${edad + 1} años el próximo año');

  // String multilinea con triple comillas
  final tarjeta = '''
Nombre: $nombre
Edad:   $edad
Mayor:  ${edad >= 18 ? 'Sí' : 'No'}
  ''';
  print(tarjeta);

  // Raw string — ignora el escape y la interpolación
  final ruta = r'C:\Users\Ana\Documents';  // el \ no se interpreta
  print(ruta);

  // Concatenación (menos idiomático — preferir interpolación)
  final saludo = 'Hola, ' + nombre + '!';

  // Métodos útiles de String
  print('flutter'.toUpperCase());           // FLUTTER
  print('  Flutter  '.trim());              // Flutter
  print('Flutter'.contains('lut'));         // true
  print('Flutter'.replaceAll('t', 'T'));    // FluTTer
  print('a,b,c'.split(','));                // [a, b, c]
  print('Flutter'.substring(0, 4));         // Flut
  print('Flutter'.startsWith('Flu'));       // true
  print('abc'.padLeft(5, '0'));             // 00abc
}