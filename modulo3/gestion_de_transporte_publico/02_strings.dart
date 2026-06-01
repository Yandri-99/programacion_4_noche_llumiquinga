void main() {
  final ruta = 'Trolebús';
  final paradas = 15;

  print('Ruta: $ruta');
  print('${ruta.toUpperCase()} tiene $paradas paradas');

  final tarjeta = '''
Ruta:       $ruta
Paradas:    $paradas
Frecuencia: ${paradas >= 10 ? 'Alta' : 'Baja'}
  ''';
  print(tarjeta);

  final horario = r'C:\Transporte\Horarios\invierno.txt';
  print(horario);

  final saludo = 'Bienvenido al ' + ruta + '!';

  print('trolebús'.toUpperCase());
  print('  Trolebús  '.trim());
  print('Transporte'.contains('ansp'));
  print('Transporte'.replaceAll('T', 't'));
  print('bus,metro,ecovia'.split(','));
  print('Transporte'.substring(0, 6));
  print('Transporte'.startsWith('Trans'));
  print('R-1'.padLeft(6, '0'));
}
