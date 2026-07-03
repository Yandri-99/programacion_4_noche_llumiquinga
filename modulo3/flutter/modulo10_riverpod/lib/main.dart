// lib/main.dart
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_riverpod/legacy.dart' show StateProvider;
import 'screens/pantalla_servidores.dart';
import 'screens/pantalla_busqueda.dart';
// Importa las pantallas a medida que las crees en cada paso:
// import 'screens/pantalla_servidores.dart';
// import 'screens/pantalla_busqueda.dart';
// import 'screens/pantalla_metricas.dart';
// import 'screens/pantalla_dashboard.dart';

// ┌──────────────────────────────────────────────────────────────────┐
// │  Cambia este número y guarda (Ctrl+S) para navegar entre pasos. │
// │  1  Paso 1  ProviderScope + StateProvider básico (contador)     │
// │  2  Paso 2  NotifierProvider + lista de servidores              │
// │  3  Paso 3  Provider derivado + búsqueda filtrada               │
// │  4  Paso 4  AsyncNotifierProvider + métricas loading/error      │
// │  5  Paso 5  NavigationBar con dos tabs usando Riverpod          │
// └──────────────────────────────────────────────────────────────────┘
const int paso = 1;

// StateProvider — estado simple del Paso 1
final contadorProvider = StateProvider<int>((ref) => 0);

void main() {
  runApp(const ProviderScope(child: AppMonitoreo()));
}

class AppMonitoreo extends StatelessWidget {
  const AppMonitoreo({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFF0D47A1)),
        useMaterial3: true,
      ),
      home: switch (paso) {
        1 => const _Paso1(),
        2 => const PantallaServidores(),
        3 => const PantallaBusqueda(),
        // 4 => const PantallaMetricas(),
        // 5 => const PantallaDashboard(),
        _ => Scaffold(
          body: Center(child: Text('Paso $paso: crea el widget primero')),
        ),
      },
    );
  }
}

// ─── Paso 1 — vive en main.dart ─────────────────────────────────────────
class _Paso1 extends ConsumerWidget {
  const _Paso1();

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final count = ref.watch(contadorProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Servidores conectados')),
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text('$count', style: Theme.of(context).textTheme.displayLarge),
            const Text('servidores activos'),
          ],
        ),
      ),
      floatingActionButton: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          FloatingActionButton(
            heroTag: 'add',
            onPressed: () => ref.read(contadorProvider.notifier).state++,
            child: const Icon(Icons.add),
          ),
          const SizedBox(height: 8),
          FloatingActionButton(
            heroTag: 'rem',
            onPressed: () {
              if (ref.read(contadorProvider) > 0) {
                ref.read(contadorProvider.notifier).state--;
              }
            },
            child: const Icon(Icons.remove),
          ),
        ],
      ),
    );
  }
}
