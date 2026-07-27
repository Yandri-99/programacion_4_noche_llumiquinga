import 'package:flutter/material.dart';

/// [DartPad] mp_05_stack_positioned.dart
/// Stack + Positioned: Tarjeta de Vehiculo con Badge de Estado

class TarjetaVehiculoStack extends StatelessWidget {
  final String nombre;
  final String placa;
  final String estado;
  final int ocupacion;
  final int capacidad;

  const TarjetaVehiculoStack({
    super.key,
    required this.nombre,
    required this.placa,
    required this.estado,
    required this.ocupacion,
    required this.capacidad,
  });

  Color get _colorEstado {
    switch (estado) {
      case 'Activo': return Colors.green;
      case 'En Ruta': return Colors.blue;
      case 'Mantenimiento': return Colors.orange;
      case 'Inactivo': return Colors.red;
      default: return Colors.grey;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(12),
      elevation: 4,
      child: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.all(16),
            child: Row(
              children: [
                Stack(
                  children: [
                    CircleAvatar(
                      radius: 30,
                      backgroundColor: Colors.blue[50],
                      child: const Icon(Icons.directions_bus, size: 32, color: Colors.blue),
                    ),
                    Positioned(
                      right: 0,
                      bottom: 0,
                      child: CircleAvatar(
                        radius: 12,
                        backgroundColor: _colorEstado,
                        child: Text('$ocupacion',
                            style: const TextStyle(color: Colors.white, fontSize: 10, fontWeight: FontWeight.bold)),
                      ),
                    ),
                  ],
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(nombre, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                      Text('Placa: $placa', style: TextStyle(color: Colors.grey[600])),
                      const SizedBox(height: 8),
                      LinearProgressIndicator(
                        value: ocupacion / capacidad,
                        backgroundColor: Colors.grey[200],
                        valueColor: AlwaysStoppedAnimation<Color>(_colorEstado),
                      ),
                      const SizedBox(height: 4),
                      Text('$ocupacion/$capacidad pasajeros', style: const TextStyle(fontSize: 12)),
                    ],
                  ),
                ),
              ],
            ),
          ),
          Positioned(
            top: 8,
            right: 8,
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
              decoration: BoxDecoration(color: _colorEstado, borderRadius: BorderRadius.circular(12)),
              child: Text(estado, style: const TextStyle(color: Colors.white, fontSize: 11, fontWeight: FontWeight.bold)),
            ),
          ),
        ],
      ),
    );
  }
}

void main() => runApp(MaterialApp(
  home: Scaffold(
    appBar: AppBar(title: const Text('Vehiculos - Stack Layout')),
    body: ListView(
      children: const [
        TarjetaVehiculoStack(nombre: 'Bus 101', placa: 'ABC-1234', estado: 'En Ruta', ocupacion: 35, capacidad: 40),
        TarjetaVehiculoStack(nombre: 'Minibus 02', placa: 'XYZ-5678', estado: 'Activo', ocupacion: 8, capacidad: 20),
        TarjetaVehiculoStack(nombre: 'Bus 103', placa: 'DEF-9012', estado: 'Mantenimiento', ocupacion: 0, capacidad: 40),
        TarjetaVehiculoStack(nombre: 'Van 04', placa: 'GHI-3456', estado: 'Inactivo', ocupacion: 0, capacidad: 12),
      ],
    ),
  ),
));
