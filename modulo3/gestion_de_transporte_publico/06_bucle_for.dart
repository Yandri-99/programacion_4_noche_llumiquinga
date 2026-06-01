void main() {
  for (int i = 0; i < 5; i++) {
    print('Parada $i');
  }

  for (int i = 0; i <= 100; i += 25) {
    print('Progreso de ruta: $i%');
  }

  for (int i = 5; i >= 1; i--) {
    print('Próxima estación en: $i min');
  }

  final estaciones = ['La Marín', 'El Recreo', 'Quitumbe', 'El Labrador', 'Ofelia'];

  for (final estacion in estaciones) {
    print(estacion);
  }

  estaciones.forEach((e) => print(e.toLowerCase()));

  final frecuencias = {'Trolebús': 5, 'Metrobús': 8, 'Ecovía': 12};
  for (final entrada in frecuencias.entries) {
    print('${entrada.key} → cada ${entrada.value} min');
  }
}
