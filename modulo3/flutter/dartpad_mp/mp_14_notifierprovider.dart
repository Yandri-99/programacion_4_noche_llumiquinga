import 'package:flutter/material.dart';

/// [DartPad] mp_14_notifierprovider.dart
/// Notificador simple: Contador de Vehiculos por Tipo

void main() => runApp(const MaterialApp(home: ContadorVehiculos()));

class ContadorVehiculos extends StatefulWidget {
  const ContadorVehiculos({super.key});

  @override
  State<ContadorVehiculos> createState() => _ContadorVehiculosState();
}

class _ContadorVehiculosState extends State<ContadorVehiculos> {
  final Map<String, int> _conteos = {'Buses': 0, 'Minibuses': 0, 'Vans': 0, 'Autos': 0};
  final Map<String, IconData> _iconos = {
    'Buses': Icons.directions_bus,
    'Minibuses': Icons.minibus,
    'Vans': Icons.airport_shuttle,
    'Autos': Icons.local_taxi,
  };
  final Map<String, Color> _colores = {
    'Buses': Colors.blue,
    'Minibuses': Colors.green,
    'Vans': Colors.orange,
    'Autos': Colors.purple,
  };

  int get _total => _conteos.values.fold(0, (a, b) => a + b);

  void _incrementar(String tipo) {
    setState(() => _conteos[tipo] = _conteos[tipo]! + 1);
  }

  void _decrementar(String tipo) {
    setState(() {
      if (_conteos[tipo]! > 0) _conteos[tipo] = _conteos[tipo]! - 1;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Vehiculos por Tipo')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Card(
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                children: [
                  Text('Total Flota: $_total', style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
                  const SizedBox(height: 8),
                  LinearProgressIndicator(
                    value: _total > 0 ? _total / 100 : 0,
                    minHeight: 10,
                  ),
                  const Text('Meta: 100 vehiculos'),
                ],
              ),
            ),
          ),
          const SizedBox(height: 12),
          ..._conteos.entries.map((e) => Card(
            margin: const EdgeInsets.only(bottom: 8),
            child: Padding(
              padding: const EdgeInsets.all(12),
              child: Row(
                children: [
                  Icon(_iconos[e.key], color: _colores[e.key], size: 32),
                  const SizedBox(width: 12),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(e.key, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
                        Text('${e.value} unidades'),
                      ],
                    ),
                  ),
                  IconButton(onPressed: () => _decrementar(e.key), icon: const Icon(Icons.remove_circle_outline)),
                  Text('${e.value}', style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                  IconButton(onPressed: () => _incrementar(e.key), icon: const Icon(Icons.add_circle_outline, color: Colors.green)),
                ],
              ),
            ),
          )),
        ],
      ),
    );
  }
}
