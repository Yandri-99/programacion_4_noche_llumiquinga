import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

/// mp_ejercicio2 - NotifierProvider: Lista de Vehículos con Estado
class Vehiculo {
  final String nombre;
  final String placa;
  final bool activo;
  const Vehiculo(this.nombre, this.placa, this.activo);
}

class VehiculosNotifier extends StateNotifier<List<Vehiculo>> {
  VehiculosNotifier()
      : super([
          const Vehiculo('Bus 101', 'ABC-1234', true),
          const Vehiculo('Minibus 02', 'XYZ-5678', true),
          const Vehiculo('Van 03', 'LMN-9012', false),
        ]);

  void agregar(Vehiculo v) => state = [...state, v];
  void eliminar(int i) => state = [...state]..removeAt(i);
  void toggle(int i) {
    final v = state[i];
    state = [...state]..[i] = Vehiculo(v.nombre, v.placa, !v.activo);
  }
}

final vehiculosProvider = StateNotifierProvider<VehiculosNotifier, List<Vehiculo>>(
  (ref) => VehiculosNotifier(),
);

final filtroProvider = StateProvider<String>((ref) => 'Todos');

class ListaVehiculosApp extends StatelessWidget {
  const ListaVehiculosApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ProviderScope(
      child: MaterialApp(
        title: 'Lista de Vehículos',
        theme: ThemeData(colorSchemeSeed: Colors.teal, useMaterial3: true),
        home: const PantallaVehiculos(),
      ),
    );
  }
}

class PantallaVehiculos extends ConsumerWidget {
  const PantallaVehiculos({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final vehiculos = ref.watch(vehiculosProvider);
    final filtro = ref.watch(filtroProvider);

    final filtrados = filtro == 'Todos'
        ? vehiculos
        : vehiculos.where((v) => v.activo == (filtro == 'Activos')).toList();

    return Scaffold(
      appBar: AppBar(title: const Text('Vehículos (Riverpod)')),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8),
            child: Row(
              children: ['Todos', 'Activos', 'Inactivos'].map((f) =>
                Padding(
                  padding: const EdgeInsets.only(right: 8),
                  child: ChoiceChip(
                    label: Text(f),
                    selected: filtro == f,
                    onSelected: (_) => ref.read(filtroProvider.notifier).state = f,
                  ),
                ),
              ).toList(),
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: filtrados.length,
              itemBuilder: (context, i) {
                final v = filtrados[i];
                final realIndex = vehiculos.indexOf(v);
                return ListTile(
                  leading: Icon(Icons.directions_bus, color: v.activo ? Colors.green : Colors.red),
                  title: Text(v.nombre),
                  subtitle: Text(v.placa),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Switch(value: v.activo, onChanged: (_) => ref.read(vehiculosProvider.notifier).toggle(realIndex)),
                      IconButton(
                        icon: const Icon(Icons.delete, size: 20),
                        onPressed: () => ref.read(vehiculosProvider.notifier).eliminar(realIndex),
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => ref.read(vehiculosProvider.notifier).agregar(
          Vehiculo('Bus ${vehiculos.length + 100}', 'NUE-${vehiculos.length + 1}000', true),
        ),
        child: const Icon(Icons.add),
      ),
    );
  }
}

void main() => runApp(const ListaVehiculosApp());
