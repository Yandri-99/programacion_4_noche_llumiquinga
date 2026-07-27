import 'package:flutter/material.dart';

/// [DartPad] mp_01_statelesswidget.dart
/// StatelessWidget: Tarjeta de Ruta de Transporte

class RutaTransporte {
  final String nombre;
  final String origen;
  final String destino;
  final double tarifa;
  final String horario;

  const RutaTransporte({
    required this.nombre,
    required this.origen,
    required this.destino,
    required this.tarifa,
    required this.horario,
  });
}

class TarjetaRuta extends StatelessWidget {
  final RutaTransporte ruta;

  const TarjetaRuta({super.key, required this.ruta});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(12),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Icon(Icons.directions_bus, color: Colors.blue, size: 28),
                const SizedBox(width: 8),
                Text(
                  ruta.nombre,
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            const Divider(),
            Text('Origen: ${ruta.origen}', style: const TextStyle(fontSize: 14)),
            Text('Destino: ${ruta.destino}', style: const TextStyle(fontSize: 14)),
            Text('Horario: ${ruta.horario}', style: const TextStyle(fontSize: 14)),
            const SizedBox(height: 8),
            Text(
              '\$${ruta.tarifa.toStringAsFixed(2)}',
              style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.green),
            ),
          ],
        ),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(
  home: Scaffold(
    appBar: AppBar(title: Text('Rutas de Transporte - StatelessWidget')),
    body: SingleChildScrollView(
      child: Column(
        children: [
          TarjetaRuta(ruta: RutaTransporte(
            nombre: 'Ruta Quito-Guayaquil',
            origen: 'Terminal Terrestre Quito',
            destino: 'Terminal Terrestre Guayaquil',
            tarifa: 15.00,
            horario: '06:00 - 22:00',
          )),
          TarjetaRuta(ruta: RutaTransporte(
            nombre: 'Ruta Cuenca-Loja',
            origen: 'Terminal Cuenca',
            destino: 'Terminal Loja',
            tarifa: 8.50,
            horario: '07:00 - 20:00',
          )),
        ],
      ),
    ),
  ),
));
