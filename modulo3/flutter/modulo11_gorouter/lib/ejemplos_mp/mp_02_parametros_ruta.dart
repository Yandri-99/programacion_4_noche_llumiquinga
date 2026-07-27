import 'package:flutter/material.dart';

/// mp_ejercicio2 - Rutas con Parámetros: Detalle de Viaje

class Viaje {
  final int id;
  final String ruta;
  final String bus;
  final String conductor;
  final int pasajeros;
  final int capacidad;
  final String estado;
  final String origen;
  final String destino;
  final String horaSalida;

  const Viaje(this.id, this.ruta, this.bus, this.conductor, this.pasajeros, this.capacidad, this.estado, this.origen, this.destino, this.horaSalida);
}

class AppViajesParametros extends StatelessWidget {
  const AppViajesParametros({super.key});

  static const viajes = [
    Viaje(1, 'Quito-Guayaquil', 'Bus 101', 'Carlos Pérez', 35, 40, 'En curso', 'Terminal Quito', 'Terminal Guayaquil', '08:15'),
    Viaje(2, 'Cuenca-Loja', 'Minibus 02', 'Ana López', 18, 20, 'Pendiente', 'Terminal Cuenca', 'Terminal Loja', '09:30'),
    Viaje(3, 'Ambato-Riobamba', 'Bus 103', 'Luis García', 40, 40, 'Completado', 'Terminal Ambato', 'Terminal Riobamba', '07:00'),
  ];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Detalle de Viaje',
      theme: ThemeData(colorSchemeSeed: Colors.deepOrange, useMaterial3: true),
      home: ListaViajesParamScreen(viajes: viajes),
    );
  }
}

class ListaViajesParamScreen extends StatelessWidget {
  final List<Viaje> viajes;
  const ListaViajesParamScreen({super.key, required this.viajes});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Viajes - Parámetros')),
      body: ListView.builder(
        itemCount: viajes.length,
        itemBuilder: (context, index) {
          final v = viajes[index];
          return Card(
            margin: const EdgeInsets.all(8),
            child: ListTile(
              title: Text(v.ruta),
              subtitle: Text('${v.bus} • ${v.conductor}'),
              trailing: const Icon(Icons.chevron_right),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (_) => DetalleViajeParamScreen(viaje: v),
                  ),
                );
              },
            ),
          );
        },
      ),
    );
  }
}

class DetalleViajeParamScreen extends StatelessWidget {
  final Viaje viaje;
  const DetalleViajeParamScreen({super.key, required this.viaje});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Viaje #${viaje.id}')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Center(child: Icon(Icons.directions_bus, size: 64, color: Theme.of(context).colorScheme.primary)),
            const SizedBox(height: 16),
            Center(child: Text(viaje.ruta, style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold))),
            const Divider(height: 32),
            _infoRow(Icons.location_on, 'Origen', viaje.origen),
            _infoRow(Icons.location_city, 'Destino', viaje.destino),
            _infoRow(Icons.directions_bus, 'Vehículo', viaje.bus),
            _infoRow(Icons.person, 'Conductor', viaje.conductor),
            _infoRow(Icons.schedule, 'Hora Salida', viaje.horaSalida),
            _infoRow(Icons.people, 'Pasajeros', '${viaje.pasajeros}/${viaje.capacidad}'),
            const SizedBox(height: 16),
            LinearProgressIndicator(
              value: viaje.pasajeros / viaje.capacidad,
              minHeight: 8,
            ),
            const SizedBox(height: 24),
            Center(
              child: Text(
                viaje.estado,
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: viaje.estado == 'En curso' ? Colors.blue
                      : viaje.estado == 'Completado' ? Colors.green
                      : Colors.orange,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _infoRow(IconData icon, String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 6),
      child: Row(
        children: [Icon(icon, size: 20, color: Colors.grey), const SizedBox(width: 12), Text('$label: ', style: const TextStyle(fontWeight: FontWeight.w500)), Expanded(child: Text(value))],
      ),
    );
  }
}

void main() => runApp(const AppViajesParametros());
