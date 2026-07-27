import 'package:flutter/material.dart';

/// mp_ejercicio2 - NavigationBar + SnackBar: App de Gestión de Viajes
class AppGestionViajes extends StatelessWidget {
  const AppGestionViajes({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Gestión de Viajes',
      theme: ThemeData(colorSchemeSeed: Colors.teal, useMaterial3: true),
      home: const PantallaPrincipalViajes(),
    );
  }
}

class PantallaPrincipalViajes extends StatefulWidget {
  const PantallaPrincipalViajes({super.key});

  @override
  State<PantallaPrincipalViajes> createState() => _PantallaPrincipalViajesState();
}

class _PantallaPrincipalViajesState extends State<PantallaPrincipalViajes> {
  int _indiceActual = 0;

  final List<String> _titulos = ['Viajes Activos', 'Rutas', 'Conductores', 'Configuración'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(_titulos[_indiceActual]), centerTitle: true),
      body: _indiceActual == 0 ? _buildViajesActivos() : Center(child: Text(_titulos[_indiceActual])),
      bottomNavigationBar: NavigationBar(
        selectedIndex: _indiceActual,
        onDestinationSelected: (i) => setState(() => _indiceActual = i),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.directions_bus), label: 'Viajes'),
          NavigationDestination(icon: Icon(Icons.route), label: 'Rutas'),
          NavigationDestination(icon: Icon(Icons.people), label: 'Conductores'),
          NavigationDestination(icon: Icon(Icons.settings), label: 'Config'),
        ],
      ),
      floatingActionButton: _indiceActual == 0
          ? FloatingActionButton(
              onPressed: () => ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Función de nuevo viaje - Próximamente'), duration: Duration(seconds: 2)),
              ),
              child: const Icon(Icons.add),
            )
          : null,
    );
  }

  Widget _buildViajesActivos() {
    return ListView(
      children: [
        _viajeCard('Bus 101', 'Quito → Guayaquil', '08:15', 'En Curso', Colors.blue),
        _viajeCard('Minibus 02', 'Cuenca → Loja', '09:30', 'En Curso', Colors.blue),
        _viajeCard('Bus 103', 'Ambato → Riobamba', '10:00', 'Pendiente', Colors.orange),
        _viajeCard('Van 04', 'Machala → Pasaje', '11:00', 'Completado', Colors.green),
      ],
    );
  }

  Widget _viajeCard(String bus, String ruta, String hora, String estado, Color color) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 6),
      child: ListTile(
        leading: CircleAvatar(backgroundColor: color.withOpacity(0.15), child: Icon(Icons.directions_bus, color: color)),
        title: Text(bus),
        subtitle: Text('$ruta • $hora'),
        trailing: Container(
          padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
          decoration: BoxDecoration(color: color.withOpacity(0.15), borderRadius: BorderRadius.circular(12)),
          child: Text(estado, style: TextStyle(color: color, fontSize: 12, fontWeight: FontWeight.w600)),
        ),
        onTap: () => ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Detalles del viaje $bus - $ruta')),
        ),
      ),
    );
  }
}

void main() => runApp(const AppGestionViajes());
