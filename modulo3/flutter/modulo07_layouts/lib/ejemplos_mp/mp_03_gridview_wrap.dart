import 'package:flutter/material.dart';

/// mp_ejercicio3 - GridView + Wrap: Flota de Vehículos en Grid
class VehiculoGrid {
  final String nombre;
  final String tipo;
  final String placa;
  final int capacidad;
  final bool activo;
  final IconData icono;

  const VehiculoGrid(this.nombre, this.tipo, this.placa, this.capacidad, this.activo, this.icono);
}

class FlotaGrid extends StatelessWidget {
  const FlotaGrid({super.key});

  static const List<VehiculoGrid> vehiculos = [
    VehiculoGrid('Bus 101', 'Bus', 'ABC-1234', 40, true, Icons.directions_bus),
    VehiculoGrid('Bus 102', 'Bus', 'ABC-1235', 40, true, Icons.directions_bus),
    VehiculoGrid('Minibus 02', 'Minibus', 'XYZ-5678', 20, true, Icons.minibus),
    VehiculoGrid('Minibus 03', 'Minibus', 'XYZ-5679', 20, false, Icons.minibus),
    VehiculoGrid('Van 04', 'Van', 'GHI-3456', 12, true, Icons.airport_shuttle),
    VehiculoGrid('Van 05', 'Van', 'GHI-3457', 12, true, Icons.airport_shuttle),
    VehiculoGrid('Bus 106', 'Bus', 'JKL-7890', 45, false, Icons.directions_bus),
    VehiculoGrid('Micro 07', 'Micro', 'MNO-1234', 15, true, Icons.local_taxi),
  ];

  @override
  Widget build(BuildContext context) {
    final activos = vehiculos.where((v) => v.activo).length;
    return Scaffold(
      appBar: AppBar(title: const Text('Flota - Grid Layout')),
      body: Column(
        children: [
          // Wrap de filtros
          Padding(
            padding: const EdgeInsets.all(12),
            child: Wrap(
              spacing: 8,
              children: [
                Chip(label: Text('Todos (${vehiculos.length})'), backgroundColor: Colors.blue),
                Chip(label: Text('Activos ($activos)')),
                Chip(label: Text('Inactivos (${vehiculos.length - activos})')),
                const Chip(label: Text('Bus')),
                const Chip(label: Text('Minibus')),
                const Chip(label: Text('Van')),
              ],
            ),
          ),
          // Grid de vehículos
          Expanded(
            child: GridView.builder(
              padding: const EdgeInsets.all(12),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
                childAspectRatio: 0.85,
              ),
              itemCount: vehiculos.length,
              itemBuilder: (context, index) {
                final v = vehiculos[index];
                return Card(
                  child: Padding(
                    padding: const EdgeInsets.all(12),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(v.icono, size: 40, color: v.activo ? Colors.blue : Colors.grey),
                        const SizedBox(height: 8),
                        Text(v.nombre, style: const TextStyle(fontWeight: FontWeight.bold)),
                        Text(v.placa, style: TextStyle(color: Colors.grey[600], fontSize: 12)),
                        const SizedBox(height: 4),
                        Text('Cap: ${v.capacidad} pax', style: const TextStyle(fontSize: 12)),
                        const SizedBox(height: 4),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                          decoration: BoxDecoration(
                            color: v.activo ? Colors.green[50] : Colors.red[50],
                            borderRadius: BorderRadius.circular(8),
                          ),
                          child: Text(
                            v.activo ? 'Activo' : 'Inactivo',
                            style: TextStyle(
                              fontSize: 11,
                              color: v.activo ? Colors.green[700] : Colors.red[700],
                              fontWeight: FontWeight.w600,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: FlotaGrid()));
