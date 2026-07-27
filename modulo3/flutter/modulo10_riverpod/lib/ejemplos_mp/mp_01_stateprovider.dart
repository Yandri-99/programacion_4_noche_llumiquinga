import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

/// mp_ejercicio1 - Riverpod StateProvider: Contador de Viajes Activos
final viajesActivosProvider = StateProvider<int>((ref) => 0);
final nombreRutaProvider = StateProvider<String>((ref) => 'Sin ruta');

class ContadorViajesApp extends StatelessWidget {
  const ContadorViajesApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ProviderScope(
      child: MaterialApp(
        title: 'Viajes Activos',
        theme: ThemeData(colorSchemeSeed: Colors.indigo, useMaterial3: true),
        home: const PantallaContadorViajes(),
      ),
    );
  }
}

class PantallaContadorViajes extends ConsumerWidget {
  const PantallaContadorViajes({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final viajes = ref.watch(viajesActivosProvider);
    final ruta = ref.watch(nombreRutaProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Viajes Activos (Riverpod)')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.directions_bus, size: 64, color: Colors.indigo),
            const SizedBox(height: 16),
            Text(ruta, style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 24),
            Text('$viajes', style: const TextStyle(fontSize: 64, fontWeight: FontWeight.bold)),
            const Text('viajes activos', style: TextStyle(fontSize: 16)),
            const SizedBox(height: 32),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                FilledButton.tonal(
                  onPressed: () => ref.read(viajesActivosProvider.notifier).state--,
                  child: const Icon(Icons.remove),
                ),
                const SizedBox(width: 16),
                FilledButton(
                  onPressed: () => ref.read(viajesActivosProvider.notifier).state++,
                  child: const Icon(Icons.add),
                ),
              ],
            ),
            const SizedBox(height: 24),
            ListTile(
              leading: const Icon(Icons.route),
              title: const Text('Cambiar ruta'),
              trailing: DropdownButton<String>(
                value: ruta,
                items: ['Quito-Guayaquil', 'Cuenca-Loja', 'Ambato-Riobamba'].map(
                  (r) => DropdownMenuItem(value: r, child: Text(r)),
                ).toList(),
                onChanged: (v) => ref.read(nombreRutaProvider.notifier).state = v!,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

void main() => runApp(const ContadorViajesApp());
