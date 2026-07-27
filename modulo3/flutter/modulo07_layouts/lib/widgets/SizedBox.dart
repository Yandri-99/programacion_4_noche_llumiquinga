import 'package:flutter/material.dart';

class Sizedbox extends StatelessWidget {
  final double height;
  final double width;

  const Sizedbox({super.key, required this.height, required this.width});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          // SizedBox — espaciado fijo
          const Text('SizedBox', style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          const Text('Primer elemento'),
          const SizedBox(height: 32), // ← espacio fijo de 32px
          const Text('Segundo elemento (después de 32px)'),

          const Divider(height: 32),

          // Padding — espacio alrededor de un hijo
          const Text('Padding', style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          Container(
            color: Colors.indigo.shade50,
            child: const Padding(
              padding: EdgeInsets.only(left: 24), // ← sangría izquierda
              child: Text('Texto con Padding izquierdo'),
            ),
          ),

          const Divider(height: 32),

          // Align — posicionar dentro del espacio disponible
          const Text('Align', style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          const Align(
            alignment: Alignment.centerRight, // ← borde derecho
            child: Icon(Icons.settings, color: Colors.indigo),
          ),

          const Divider(height: 32),

          // Wrap — flujo automático de elementos
          const Text('Wrap', style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          Wrap(
            spacing: 8,
            runSpacing: 8,
            children: [
              'nginx',
              'TLS 1.3',
              'HTTP/2',
              'IPv6',
              'Load Balancer',
              'CDN',
              'WAF',
            ].map((t) => Chip(label: Text(t))).toList(),
          ),
        ],
      ),
    );
  }
}
