import 'package:flutter/material.dart';

/// [DartPad] mp_09_alertdialog_bottomsheet.dart
/// AlertDialog + BottomSheet + showModalBottomSheet

void main() => runApp(const MaterialApp(home: GestionVehiculos()));

class GestionVehiculos extends StatefulWidget {
  const GestionVehiculos({super.key});

  @override
  State<GestionVehiculos> createState() => _GestionVehiculosState();
}

class _GestionVehiculosState extends State<GestionVehiculos> {
  final List<Map<String, dynamic>> _vehiculos = [
    {'nombre': 'Bus 101', 'placa': 'ABC-1234', 'activo': true},
    {'nombre': 'Minibus 02', 'placa': 'XYZ-5678', 'activo': true},
    {'nombre': 'Van 04', 'placa': 'GHI-3456', 'activo': false},
  ];

  void _confirmarEliminar(int index) {
    showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text('Confirmar Eliminacion'),
        content: Text('Eliminar ${_vehiculos[index]['nombre']}?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(ctx), child: const Text('Cancelar')),
          TextButton(
            onPressed: () {
              setState(() => _vehiculos.removeAt(index));
              Navigator.pop(ctx);
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Vehiculo eliminado'), backgroundColor: Colors.red),
              );
            },
            child: const Text('Eliminar', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  void _mostrarOpciones(int index) {
    showModalBottomSheet(
      context: context,
      builder: (ctx) => SafeArea(
        child: Wrap(
          children: [
            ListTile(
              leading: const Icon(Icons.edit),
              title: const Text('Editar'),
              onTap: () {
                Navigator.pop(ctx);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Editando ${_vehiculos[index]['nombre']}')),
                );
              },
            ),
            ListTile(
              leading: const Icon(Icons.info),
              title: const Text('Detalles'),
              onTap: () {
                Navigator.pop(ctx);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Placa: ${_vehiculos[index]['placa']}')),
                );
              },
            ),
            ListTile(
              leading: const Icon(Icons.delete, color: Colors.red),
              title: const Text('Eliminar', style: TextStyle(color: Colors.red)),
              onTap: () {
                Navigator.pop(ctx);
                _confirmarEliminar(index);
              },
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Gestion de Vehiculos')),
      body: ListView.builder(
        itemCount: _vehiculos.length,
        itemBuilder: (context, index) {
          final v = _vehiculos[index];
          return Card(
            margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            child: ListTile(
              leading: CircleAvatar(
                backgroundColor: v['activo'] ? Colors.green[100] : Colors.red[100],
                child: Icon(Icons.directions_bus, color: v['activo'] ? Colors.green : Colors.red),
              ),
              title: Text(v['nombre']),
              subtitle: Text('Placa: ${v['placa']}'),
              trailing: IconButton(
                icon: const Icon(Icons.more_vert),
                onPressed: () => _mostrarOpciones(index),
              ),
            ),
          );
        },
      ),
    );
  }
}
