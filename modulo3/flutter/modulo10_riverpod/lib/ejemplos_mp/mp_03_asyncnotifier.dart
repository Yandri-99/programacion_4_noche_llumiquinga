import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

/// mp_ejercicio3 - AsyncNotifierProvider: Estadísticas de Transporte (Async)
class EstadisticaTransporte {
  final int totalViajes;
  final int viajesActivos;
  final int totalPasajeros;
  final double ingresos;
  const EstadisticaTransporte(this.totalViajes, this.viajesActivos, this.totalPasajeros, this.ingresos);
}

class EstadisticasNotifier extends AsyncNotifier<EstadisticaTransporte> {
  @override
  Future<EstadisticaTransporte> build() async {
    await Future.delayed(const Duration(seconds: 2));
    return const EstadisticaTransporte(156, 12, 4320, 15680.50);
  }

  Future<void> recargar() async {
    state = const AsyncValue.loading();
    state = await AsyncValue.guard(() async {
      await Future.delayed(const Duration(seconds: 1));
      return const EstadisticaTransporte(160, 14, 4500, 16200.00);
    });
  }
}

final estadisticasProvider = AsyncNotifierProvider<EstadisticasNotifier, EstadisticaTransporte>(
  EstadisticasNotifier.new,
);

class EstadisticasApp extends StatelessWidget {
  const EstadisticasApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ProviderScope(
      child: MaterialApp(
        title: 'Estadísticas',
        theme: ThemeData(colorSchemeSeed: Colors.purple, useMaterial3: true),
        home: const PantallaEstadisticas(),
      ),
    );
  }
}

class PantallaEstadisticas extends ConsumerWidget {
  const PantallaEstadisticas({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final statsAsync = ref.watch(estadisticasProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Estadísticas (Async)'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () => ref.read(estadisticasProvider.notifier).recargar(),
          ),
        ],
      ),
      body: statsAsync.when(
        loading: () => const Center(child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [CircularProgressIndicator(), SizedBox(height: 16), Text('Cargando estadísticas...')],
        )),
        error: (e, _) => Center(child: Text('Error: $e')),
        data: (stats) => SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              _statCard(Icons.directions_bus, 'Viajes Totales', '${stats.totalViajes}', Colors.blue),
              _statCard(Icons.play_arrow, 'Viajes Activos', '${stats.viajesActivos}', Colors.green),
              _statCard(Icons.people, 'Pasajeros', '${stats.totalPasajeros}', Colors.orange),
              _statCard(Icons.attach_money, 'Ingresos', '\$${stats.ingresos.toStringAsFixed(2)}', Colors.purple),
            ],
          ),
        ),
      ),
    );
  }

  Widget _statCard(IconData icon, String label, String value, Color color) {
    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      child: ListTile(
        leading: CircleAvatar(backgroundColor: color.withOpacity(0.15), child: Icon(icon, color: color)),
        title: Text(label),
        trailing: Text(value, style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: color)),
      ),
    );
  }
}

void main() => runApp(const EstadisticasApp());
