import 'package:flutter/material.dart';

/// mp_ejercicio3 - StatefulWidget + StatelessWidget: Catálogo de Vehículos
class Vehiculo {
  final String nombre;
  final String placa;
  final String tipo;
  final int capacidad;
  final bool activo;
  final IconData icono;

  const Vehiculo({
    required this.nombre,
    required this.placa,
    required this.tipo,
    required this.capacidad,
    required this.activo,
    required this.icono,
  });
}

class ItemVehiculo extends StatelessWidget {
  final Vehiculo vehiculo;
  final VoidCallback? onToggle;

  const ItemVehiculo({super.key, required this.vehiculo, this.onToggle});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: CircleAvatar(
        backgroundColor: vehiculo.activo ? Colors.green[100] : Colors.red[100],
        child: Icon(vehiculo.icono, color: vehiculo.activo ? Colors.green : Colors.red),
      ),
      title: Text('${vehiculo.nombre} - ${vehiculo.placa}'),
      subtitle: Text('${vehiculo.tipo} • Cap: ${vehiculo.capacidad} pax'),
      trailing: Switch(
        value: vehiculo.activo,
        onChanged: (_) => onToggle?.call(),
      ),
    );
  }
}

class CatalogoVehiculos extends StatefulWidget {
  const CatalogoVehiculos({super.key});

  @override
  State<CatalogoVehiculos> createState() => _CatalogoVehiculosState();
}

class _CatalogoVehiculosState extends State<CatalogoVehiculos> {
  final List<Vehiculo> _vehiculos = [
    Vehiculo(nombre: 'Bus 101', placa: 'ABC-1234', tipo: 'Bus', capacidad: 40, activo: true, icono: Icons.directions_bus),
    Vehiculo(nombre: 'Minibus 02', placa: 'XYZ-5678', tipo: 'Minibus', capacidad: 20, activo: true, icono: Icons.minibus),
    Vehiculo(nombre: 'Van 03', placa: 'LMN-9012', tipo: 'Van', capacidad: 12, activo: false, icono: Icons.airport_shuttle),
  ];

  void _toggle(int index) {
    setState(() {
      final old = _vehiculos[index];
      _vehiculos[index] = Vehiculo(
        nombre: old.nombre,
        placa: old.placa,
        tipo: old.tipo,
        capacidad: old.capacidad,
        activo: !old.activo,
        icono: old.icono,
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    final activos = _vehiculos.where((v) => v.activo).length;
    return Scaffold(
      appBar: AppBar(title: const Text('Catálogo de Vehículos')),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16),
            child: Text(
              '$activos de ${_vehiculos.length} vehículos activos',
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: _vehiculos.length,
              itemBuilder: (context, index) => ItemVehiculo(
                vehiculo: _vehiculos[index],
                onToggle: () => _toggle(index),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: CatalogoVehiculos()));
