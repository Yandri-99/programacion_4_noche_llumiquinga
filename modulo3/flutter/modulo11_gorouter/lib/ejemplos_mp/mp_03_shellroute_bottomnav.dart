import 'package:flutter/material.dart';

/// mp_ejercicio3 - ShellRoute: Navegación con BottomNavBar para App de Transporte

class AppTransporteShell extends StatelessWidget {
  const AppTransporteShell({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TransporteApp ShellRoute',
      theme: ThemeData(colorSchemeSeed: Colors.cyan, useMaterial3: true),
      home: const ShellRouteScreen(),
    );
  }
}

class ShellRouteScreen extends StatefulWidget {
  const ShellRouteScreen({super.key});

  @override
  State<ShellRouteScreen> createState() => _ShellRouteScreenState();
}

class _ShellRouteScreenState extends State<ShellRouteScreen> {
  int _currentIndex = 0;

  final _screens = const [
    InicioScreen(),
    RutasScreen(),
    MapaScreen(),
    PerfilScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_currentIndex],
      bottomNavigationBar: NavigationBar(
        selectedIndex: _currentIndex,
        onDestinationSelected: (i) => setState(() => _currentIndex = i),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.home), label: 'Inicio'),
          NavigationDestination(icon: Icon(Icons.route), label: 'Rutas'),
          NavigationDestination(icon: Icon(Icons.map), label: 'Mapa'),
          NavigationDestination(icon: Icon(Icons.person), label: 'Perfil'),
        ],
      ),
    );
  }
}

class InicioScreen extends StatelessWidget {
  const InicioScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('TransporteApp')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Card(child: Padding(
            padding: const EdgeInsets.all(16),
            child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              const Text('Bienvenido', style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold)),
              const SizedBox(height: 8),
              Text('Viajes de hoy: 12', style: TextStyle(color: Colors.grey[600])),
            ]),
          )),
          Card(child: ListTile(
            leading: const Icon(Icons.directions_bus),
            title: const Text('Bus 101'),
            subtitle: const Text('Quito → Guayaquil • En curso'),
            trailing: const Badge(label: Text('35'), child: Icon(Icons.people)),
          )),
          Card(child: ListTile(
            leading: const Icon(Icons.directions_bus),
            title: const Text('Minibus 02'),
            subtitle: const Text('Cuenca → Loja • Pendiente'),
            trailing: const Badge(label: Text('18'), child: Icon(Icons.people)),
          )),
        ],
      ),
    );
  }
}

class RutasScreen extends StatelessWidget {
  const RutasScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Rutas')),
      body: const ListTile(leading: Icon(Icons.route), title: Text('Quito → Guayaquil'), subtitle: Text('15.00 • Horario: 06:00-22:00')),
    );
  }
}

class MapaScreen extends StatelessWidget {
  const MapaScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Mapa en Vivo')),
      body: const Center(child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [Icon(Icons.map, size: 80, color: Colors.grey), SizedBox(height: 16), Text('Mapa en desarrollo')],
      )),
    );
  }
}

class PerfilScreen extends StatelessWidget {
  const PerfilScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Perfil')),
      body: const ListTile(
        leading: CircleAvatar(child: Icon(Icons.person)),
        title: Text('Conductor 1'),
        subtitle: Text('conductor@transporte.com'),
      ),
    );
  }
}

void main() => runApp(const AppTransporteShell());
