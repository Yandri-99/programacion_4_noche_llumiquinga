import 'package:flutter/material.dart';

/// [DartPad] mp_18_shellroute_bottomnav.dart
/// ShellRoute simulado: Navegacion principal con BottomNavigationBar

void main() => runApp(const MaterialApp(home: ShellRouteApp()));

class ShellRouteApp extends StatefulWidget {
  const ShellRouteApp({super.key});

  @override
  State<ShellRouteApp> createState() => _ShellRouteAppState();
}

class _ShellRouteAppState extends State<ShellRouteApp> {
  int _indice = 0;

  final _titulos = ['Inicio', 'Rutas', 'Conductores', 'Config'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(_titulos[_indice]), centerTitle: true),
      body: _indice == 0
          ? _inicio()
          : _indice == 1
              ? _rutas()
              : _indice == 2
                  ? _conductores()
                  : _config(),
      bottomNavigationBar: NavigationBar(
        selectedIndex: _indice,
        onDestinationSelected: (i) => setState(() => _indice = i),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.home), label: 'Inicio'),
          NavigationDestination(icon: Icon(Icons.route), label: 'Rutas'),
          NavigationDestination(icon: Icon(Icons.person), label: 'Conductores'),
          NavigationDestination(icon: Icon(Icons.settings), label: 'Config'),
        ],
      ),
    );
  }

  Widget _inicio() => ListView(
    padding: const EdgeInsets.all(16),
    children: [
      _statCard(Icons.directions_bus, 'Vehiculos Activos', '12', Colors.blue),
      _statCard(Icons.route, 'Rutas Operando', '8', Colors.green),
      _statCard(Icons.people, 'Pasajeros Hoy', '1,250', Colors.orange),
      _statCard(Icons.attach_money, 'Ingresos', '\$4,320', Colors.purple),
    ],
  );

  Widget _rutas() => ListView(
    padding: const EdgeInsets.all(16),
    children: const [
      Card(child: ListTile(title: Text('Quito - Guayaquil'), subtitle: Text('4h 30min - \$15.00'), leading: Icon(Icons.route))),
      Card(child: ListTile(title: Text('Cuenca - Loja'), subtitle: Text('3h - \$8.50'), leading: Icon(Icons.route))),
      Card(child: ListTile(title: Text('Ambato - Riobamba'), subtitle: Text('2h - \$6.00'), leading: Icon(Icons.route))),
    ],
  );

  Widget _conductores() => ListView(
    padding: const EdgeInsets.all(16),
    children: const [
      Card(child: ListTile(title: Text('Juan Perez'), subtitle: Text('Licencia: A - 10 anos exp'), leading: Icon(Icons.person))),
      Card(child: ListTile(title: Text('Maria Lopez'), subtitle: Text('Licencia: B - 5 anos exp'), leading: Icon(Icons.person))),
      Card(child: ListTile(title: Text('Carlos Garcia'), subtitle: Text('Licencia: A - 8 anos exp'), leading: Icon(Icons.person))),
    ],
  );

  Widget _config() => ListView(
    padding: const EdgeInsets.all(16),
    children: const [
      Card(child: ListTile(title: Text('Notificaciones'), trailing: Switch(value: true, onChanged: null), leading: Icon(Icons.notifications))),
      Card(child: ListTile(title: Text('Modo Oscuro'), trailing: Switch(value: false, onChanged: null), leading: Icon(Icons.dark_mode))),
      Card(child: ListTile(title: Text('Idioma'), subtitle: Text('Espanol'), leading: Icon(Icons.language), trailing: Icon(Icons.chevron_right))),
    ],
  );

  Widget _statCard(IconData icon, String label, String value, Color color) {
    return Card(
      margin: const EdgeInsets.only(bottom: 8),
      child: ListTile(
        leading: CircleAvatar(backgroundColor: color.withOpacity(0.15), child: Icon(icon, color: color)),
        title: Text(label),
        trailing: Text(value, style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: color)),
      ),
    );
  }
}
