import 'package:flutter/material.dart';

/// [DartPad] mp_17_parametros_ruta.dart
/// Navegacion con argumentos: Detalle de Vehiculo

void main() => runApp(const MaterialApp(home: ListaVehiculos()));

class VehiculoArgs {
  final String nombre;
  final String placa;
  final int capacidad;
  final String estado;
  final String conductor;

  const VehiculoArgs(this.nombre, this.placa, this.capacidad, this.estado, this.conductor);
}

class ListaVehiculos extends StatelessWidget {
  const ListaVehiculos({super.key});

  final List<VehiculoArgs> _vehiculos = const [
    VehiculoArgs('Bus 101', 'ABC-1234', 40, 'En Ruta', 'Juan Perez'),
    VehiculoArgs('Minibus 02', 'XYZ-5678', 20, 'Activo', 'Maria Lopez'),
    VehiculoArgs('Van 04', 'GHI-3456', 12, 'Mantenimiento', 'Sin asignar'),
    VehiculoArgs('Bus 106', 'JKL-7890', 45, 'Inactivo', 'Sin asignar'),
  ];

  Color _colorEstado(String e) {
    switch (e) {
      case 'En Ruta': return Colors.blue;
      case 'Activo': return Colors.green;
      case 'Mantenimiento': return Colors.orange;
      default: return Colors.red;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Vehiculos (Nav + Args)')),
      body: ListView.builder(
        itemCount: _vehiculos.length,
        itemBuilder: (context, index) {
          final v = _vehiculos[index];
          return Card(
            margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            child: ListTile(
              leading: CircleAvatar(backgroundColor: _colorEstado(v.estado).withOpacity(0.2), child: Icon(Icons.directions_bus, color: _colorEstado(v.estado))),
              title: Text('${v.nombre} - ${v.placa}'),
              subtitle: Text(v.estado),
              trailing: const Icon(Icons.chevron_right),
              onTap: () {
                Navigator.push(context, MaterialPageRoute(
                  builder: (_) => DetalleVehiculo(vehiculo: v),
                ));
              },
            ),
          );
        },
      ),
    );
  }
}

class DetalleVehiculo extends StatelessWidget {
  final VehiculoArgs vehiculo;
  const DetalleVehiculo({super.key, required this.vehiculo});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(vehiculo.nombre)),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          const CircleAvatar(radius: 40, child: Icon(Icons.directions_bus, size: 48)),
          const SizedBox(height: 16),
          Card(child: Padding(
            padding: const EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(vehiculo.nombre, style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold)),
                const Divider(),
                _fila(Icons.badge, 'Placa', vehiculo.placa),
                _fila(Icons.people, 'Capacidad', '${vehiculo.capacidad} pasajeros'),
                _fila(Icons.info, 'Estado', vehiculo.estado),
                _fila(Icons.person, 'Conductor', vehiculo.conductor),
              ],
            ),
          )),
        ],
      ),
    );
  }

  Widget _fila(IconData icon, String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(children: [
        Icon(icon, size: 20, color: Colors.grey),
        const SizedBox(width: 8),
        Text('$label: ', style: const TextStyle(fontWeight: FontWeight.w500)),
        Text(value),
      ]),
    );
  }
}
