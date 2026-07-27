import 'package:flutter/material.dart';

/// [DartPad] mp_16_rutas_basicas.dart
/// Navegacion basica con Navigator.push: Pantallas de Rutas

void main() => runApp(const MaterialApp(home: PantallaRutas()));

class PantallaRutas extends StatelessWidget {
  const PantallaRutas({super.key});

  final List<Map<String, String>> _rutas = const [
    {'nombre': 'Quito - Guayaquil', 'duracion': '4h 30min', 'tarifa': '\$15.00', 'vehiculos': '12 buses'},
    {'nombre': 'Cuenca - Loja', 'duracion': '3h 00min', 'tarifa': '\$8.50', 'vehiculos': '6 minibuses'},
    {'nombre': 'Ambato - Riobamba', 'duracion': '2h 00min', 'tarifa': '\$6.00', 'vehiculos': '8 buses'},
    {'nombre': 'Quito - Latacunga', 'duracion': '1h 30min', 'tarifa': '\$4.00', 'vehiculos': '10 vans'},
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Rutas de Transporte')),
      body: ListView.builder(
        itemCount: _rutas.length,
        itemBuilder: (context, index) {
          final r = _rutas[index];
          return Card(
            margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            child: ListTile(
              leading: const CircleAvatar(child: Icon(Icons.route)),
              title: Text(r['nombre']!),
              subtitle: Text('${r['duracion']} - ${r['tarifa']}'),
              trailing: const Icon(Icons.chevron_right),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (_) => DetalleRuta(
                      nombre: r['nombre']!,
                      duracion: r['duracion']!,
                      tarifa: r['tarifa']!,
                      vehiculos: r['vehiculos']!,
                    ),
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

class DetalleRuta extends StatelessWidget {
  final String nombre;
  final String duracion;
  final String tarifa;
  final String vehiculos;

  const DetalleRuta({super.key, required this.nombre, required this.duracion, required this.tarifa, required this.vehiculos});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(nombre)),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Card(
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text('Detalles de la Ruta', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                  const Divider(),
                  ListTile(leading: const Icon(Icons.timer), title: const Text('Duracion'), subtitle: Text(duracion)),
                  ListTile(leading: const Icon(Icons.attach_money), title: const Text('Tarifa'), subtitle: Text(tarifa)),
                  ListTile(leading: const Icon(Icons.directions_bus), title: const Text('Vehiculos Asignados'), subtitle: Text(vehiculos)),
                ],
              ),
            ),
          ),
          const SizedBox(height: 16),
          ElevatedButton.icon(
            onPressed: () => Navigator.pop(context),
            icon: const Icon(Icons.arrow_back),
            label: const Text('Volver'),
          ),
        ],
      ),
    );
  }
}
