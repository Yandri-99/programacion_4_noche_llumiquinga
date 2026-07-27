import 'package:flutter/material.dart';

/// [DartPad] mp_08_navigationbar_snackbar.dart
/// BottomNavigationBar + SnackBar + ScaffoldMessenger

void main() => runApp(const MaterialApp(home: PantallaPrincipal()));

class PantallaPrincipal extends StatefulWidget {
  const PantallaPrincipal({super.key});

  @override
  State<PantallaPrincipal> createState() => _PantallaPrincipalState();
}

class _PantallaPrincipalState extends State<PantallaPrincipal> {
  int _indiceActual = 0;

  final List<String> _titulos = ['Inicio', 'Rutas', 'Vehiculos', 'Reportes'];

  final List<Widget> _pantallas = [
    _pantallaInicio(),
    _pantallaRutas(),
    _pantallaVehiculos(),
    _pantallaReportes(),
  ];

  static Widget _pantallaInicio() => ListView(
    padding: const EdgeInsets.all(16),
    children: [
      Card(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            const Text('Resumen del Dia', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const Divider(),
            _filaResumen(Icons.directions_bus, '12 Vehiculos Activos', Colors.blue),
            _filaResumen(Icons.route, '8 Rutas Operando', Colors.green),
            _filaResumen(Icons.people, '1,250 Pasajeros', Colors.orange),
          ]),
        ),
      ),
    ],
  );

  static Widget _pantallaRutas() => ListView(
    padding: const EdgeInsets.all(16),
    children: const [
      Card(child: ListTile(title: Text('Quito - Guayaquil'), subtitle: Text('4h 30min - \$15.00'), leading: Icon(Icons.route))),
      Card(child: ListTile(title: Text('Cuenca - Loja'), subtitle: Text('3h - \$8.50'), leading: Icon(Icons.route))),
      Card(child: ListTile(title: Text('Ambato - Riobamba'), subtitle: Text('2h - \$6.00'), leading: Icon(Icons.route))),
    ],
  );

  static Widget _pantallaVehiculos() => ListView(
    padding: const EdgeInsets.all(16),
    children: const [
      Card(child: ListTile(title: Text('Bus 101'), subtitle: Text('ABC-1234 - Cap: 40'), leading: Icon(Icons.directions_bus))),
      Card(child: ListTile(title: Text('Minibus 02'), subtitle: Text('XYZ-5678 - Cap: 20'), leading: Icon(Icons.directions_bus))),
      Card(child: ListTile(title: Text('Van 04'), subtitle: Text('GHI-3456 - Cap: 12'), leading: Icon(Icons.airport_shuttle))),
    ],
  );

  static Widget _pantallaReportes() => ListView(
    padding: const EdgeInsets.all(16),
    children: const [
      Card(child: ListTile(title: Text('Ingresos Semanales'), subtitle: Text('\$12,450.00'), leading: Icon(Icons.attach_money))),
      Card(child: ListTile(title: Text('Pasajeros Mensuales'), subtitle: Text('38,500'), leading: Icon(Icons.people))),
    ],
  );

  static Widget _filaResumen(IconData icono, String texto, Color color) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(children: [
        Icon(icono, color: color, size: 20),
        const SizedBox(width: 8),
        Text(texto, style: const TextStyle(fontSize: 14)),
      ]),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(_titulos[_indiceActual])),
      body: _pantallas[_indiceActual],
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Accion realizada en ${_titulos[_indiceActual]}'), duration: const Duration(seconds: 2)),
          );
        },
        child: const Icon(Icons.add),
      ),
      bottomNavigationBar: NavigationBar(
        selectedIndex: _indiceActual,
        onDestinationSelected: (i) => setState(() => _indiceActual = i),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.home), label: 'Inicio'),
          NavigationDestination(icon: Icon(Icons.route), label: 'Rutas'),
          NavigationDestination(icon: Icon(Icons.directions_bus), label: 'Vehiculos'),
          NavigationDestination(icon: Icon(Icons.bar_chart), label: 'Reportes'),
        ],
      ),
    );
  }
}
