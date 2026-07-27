import 'package:flutter/material.dart';

/// [DartPad] mp_19_futurebuilder_http.dart
/// FutureBuilder con datos mock: Lista de Vehiculos (sin paquete http)

class VehiculoHttp {
  final int id;
  final String nombre;
  final String placa;
  final String estado;

  const VehiculoHttp(this.id, this.nombre, this.placa, this.estado);
}

Future<List<VehiculoHttp>> fetchVehiculos() async {
  await Future.delayed(const Duration(seconds: 2));
  return const [
    VehiculoHttp(1, 'Bus 101', 'ABC-1234', 'Activo'),
    VehiculoHttp(2, 'Bus 102', 'ABC-1235', 'Inactivo'),
    VehiculoHttp(3, 'Minibus 02', 'XYZ-5678', 'Activo'),
    VehiculoHttp(4, 'Van 04', 'GHI-3456', 'Activo'),
    VehiculoHttp(5, 'Bus 106', 'JKL-7890', 'Mantenimiento'),
    VehiculoHttp(6, 'Micro 07', 'MNO-1234', 'Activo'),
  ];
}

void main() => runApp(const MaterialApp(home: VehiculosFuture()));

class VehiculosFuture extends StatefulWidget {
  const VehiculosFuture({super.key});

  @override
  State<VehiculosFuture> createState() => _VehiculosFutureState();
}

class _VehiculosFutureState extends State<VehiculosFuture> {
  late Future<List<VehiculoHttp>> _future;

  @override
  void initState() {
    super.initState();
    _future = fetchVehiculos();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Vehiculos (FutureBuilder)'),
        actions: [IconButton(icon: const Icon(Icons.refresh), onPressed: () => setState(() => _future = fetchVehiculos()))],
      ),
      body: FutureBuilder<List<VehiculoHttp>>(
        future: _future,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) return Center(child: Text('Error: ${snapshot.error}'));
          final items = snapshot.data!;
          final activos = items.where((v) => v.estado == 'Activo').length;
          return Column(
            children: [
              Padding(
                padding: const EdgeInsets.all(16),
                child: Text('$activos de ${items.length} vehiculos activos', style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
              ),
              Expanded(
                child: ListView.builder(
                  itemCount: items.length,
                  itemBuilder: (context, index) {
                    final v = items[index];
                    final color = v.estado == 'Activo' ? Colors.green : v.estado == 'Mantenimiento' ? Colors.orange : Colors.red;
                    return Card(
                      margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
                      child: ListTile(
                        leading: Icon(Icons.directions_bus, color: color),
                        title: Text('${v.nombre} - ${v.placa}'),
                        trailing: Chip(label: Text(v.estado), backgroundColor: color.withOpacity(0.15)),
                      ),
                    );
                  },
                ),
              ),
            ],
          );
        },
      ),
    );
  }
}
