import 'package:flutter/material.dart';

/// mp_ejercicio1 - GoRouter Básico: Rutas de App de Transporte
/// Archivo Dart puro sin dependencias externas - demuestra conceptos de routing

class RutaTransporteModel {
  final int id;
  final String nombre;
  final String origen;
  final String destino;
  const RutaTransporteModel(this.id, this.nombre, this.origen, this.destino);
}

/// Simula navegación con Navigator simple (conceptos de GoRouter)
class AppTransporteRouter extends StatelessWidget {
  const AppTransporteRouter({super.key});

  static const List<RutaTransporteModel> rutas = [
    RutaTransporteModel(1, 'Ruta Norte', 'Terminal Norte', 'Centro'),
    RutaTransporteModel(2, 'Ruta Sur', 'Terminal Sur', 'Airport'),
    RutaTransporteModel(3, 'Ruta Este', 'Centro', 'Zona Industrial'),
  ];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Transporte - Rutas',
      theme: ThemeData(colorSchemeSeed: Colors.orange, useMaterial3: true),
      home: const ListaRutasScreen(),
    );
  }
}

class ListaRutasScreen extends StatelessWidget {
  const ListaRutasScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Rutas de Transporte')),
      body: ListView.builder(
        itemCount: AppTransporteRouter.rutas.length,
        itemBuilder: (context, index) {
          final ruta = AppTransporteRouter.rutas[index];
          return ListTile(
            leading: CircleAvatar(child: Text('${ruta.id}')),
            title: Text(ruta.nombre),
            subtitle: Text('${ruta.origen} → ${ruta.destino}'),
            trailing: const Icon(Icons.chevron_right),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (_) => DetalleRutaScreen(ruta: ruta),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

class DetalleRutaScreen extends StatelessWidget {
  final RutaTransporteModel ruta;
  const DetalleRutaScreen({super.key, required this.ruta});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(ruta.nombre)),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Icon(Icons.route, size: 64, color: Theme.of(context).colorScheme.primary),
            const SizedBox(height: 16),
            Text(ruta.nombre, style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Text('Origen: ${ruta.origen}', style: const TextStyle(fontSize: 16)),
            Text('Destino: ${ruta.destino}', style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 24),
            FilledButton.icon(
              onPressed: () => Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (_) => VehiculosRutaScreen(rutaNombre: ruta.nombre),
                ),
              ),
              icon: const Icon(Icons.directions_bus),
              label: const Text('Ver Vehículos Asignados'),
            ),
          ],
        ),
      ),
    );
  }
}

class VehiculosRutaScreen extends StatelessWidget {
  final String rutaNombre;
  const VehiculosRutaScreen({super.key, required this.rutaNombre});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Vehículos - $rutaNombre')),
      body: const ListTile(
        leading: Icon(Icons.directions_bus),
        title: Text('Bus 101'),
        subtitle: Text('Placa: ABC-1234 • Cap: 40'),
        trailing: Icon(Icons.chevron_right),
      ),
    );
  }
}

void main() => runApp(const AppTransporteRouter());
