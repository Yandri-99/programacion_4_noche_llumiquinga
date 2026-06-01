void main() {
  final terminales = ['TERMINAL-NORTE', 'TERMINAL-CENTRAL', 'TERMINAL-SUR'];
  final andenes   = ['ANDEN-A', 'ANDEN-B', 'ANDEN-C'];
  String? busEncontrado;

  busqueda:
  for (final terminal in terminales) {
    for (final anden in andenes) {
      final bus = '$terminal/$anden';
      print('Buscando bus en $bus...');

      if (terminal == 'TERMINAL-CENTRAL' && anden == 'ANDEN-B') {
        busEncontrado = bus;
        break busqueda;
      }
    }
  }
  print('Bus asignado: $busEncontrado');
}
