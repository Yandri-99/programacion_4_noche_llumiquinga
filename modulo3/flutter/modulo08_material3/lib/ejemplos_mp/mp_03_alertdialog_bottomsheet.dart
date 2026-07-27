import 'package:flutter/material.dart';

/// mp_ejercicio3 - AlertDialog + BottomSheet: Confirmar y gestionar viajes
class AppConfirmarViajes extends StatelessWidget {
  const AppConfirmarViajes({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Gestionar Viajes',
      theme: ThemeData(colorSchemeSeed: Colors.deepPurple, useMaterial3: true),
      home: const PantallaConfirmarViajes(),
    );
  }
}

class PantallaConfirmarViajes extends StatelessWidget {
  const PantallaConfirmarViajes({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Gestionar Viajes')),
      body: ListView(
        padding: const EdgeInsets.all(12),
        children: [
          _viajeCard(
            context,
            'Bus 101', 'Quito → Guayaquil', '08:15', 35, 40,
            onCancelar: () => _mostrarDialogoCancelar(context, 'Bus 101'),
            onIniciar: () => _mostrarBottomSheet(context, 'Bus 101', 'iniciar'),
            onCompletar: () => _mostrarDialogoConfirmar(context, 'Bus 101'),
          ),
          _viajeCard(
            context,
            'Minibus 02', 'Cuenca → Loja', '09:30', 18, 20,
            onCancelar: () => _mostrarDialogoCancelar(context, 'Minibus 02'),
            onIniciar: () => _mostrarBottomSheet(context, 'Minibus 02', 'iniciar'),
            onCompletar: () => _mostrarDialogoConfirmar(context, 'Minibus 02'),
          ),
        ],
      ),
    );
  }

  Widget _viajeCard(BuildContext context, String bus, String ruta, String hora, int pax, int cap,
      {required VoidCallback onCancelar, required VoidCallback onIniciar, required VoidCallback onCompletar}) {
    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(children: [
              const Icon(Icons.directions_bus, color: Colors.deepPurple),
              const SizedBox(width: 8),
              Text(bus, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
              const Spacer(),
              Text(hora, style: TextStyle(color: Colors.grey[600])),
            ]),
            const SizedBox(height: 8),
            Text(ruta, style: const TextStyle(fontSize: 15)),
            Text('$pax/$cap pasajeros', style: const TextStyle(fontSize: 13)),
            const Divider(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                TextButton.icon(
                  onPressed: onCancelar,
                  icon: const Icon(Icons.cancel, color: Colors.red),
                  label: const Text('Cancelar', style: TextStyle(color: Colors.red)),
                ),
                TextButton.icon(
                  onPressed: onIniciar,
                  icon: const Icon(Icons.play_arrow, color: Colors.blue),
                  label: const Text('Iniciar', style: TextStyle(color: Colors.blue)),
                ),
                TextButton.icon(
                  onPressed: onCompletar,
                  icon: const Icon(Icons.check_circle, color: Colors.green),
                  label: const Text('Completar', style: TextStyle(color: Colors.green)),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  void _mostrarDialogoCancelar(BuildContext context, String bus) {
    showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text('Cancelar Viaje'),
        content: Text('¿Está seguro de cancelar el viaje de $bus?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(ctx), child: const Text('No')),
          TextButton(
            onPressed: () { Navigator.pop(ctx); ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Viaje $bus cancelado'))); },
            child: const Text('Sí, cancelar', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  void _mostrarDialogoConfirmar(BuildContext context, String bus) {
    showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        icon: const Icon(Icons.check_circle, color: Colors.green, size: 48),
        title: const Text('Completar Viaje'),
        content: Text('¿Marcar viaje de $bus como completado?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(ctx), child: const Text('Cancelar')),
          FilledButton(
            onPressed: () { Navigator.pop(ctx); ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Viaje $bus completado'))); },
            child: const Text('Completar'),
          ),
        ],
      ),
    );
  }

  void _mostrarBottomSheet(BuildContext context, String bus, String accion) {
    showModalBottomSheet(
      context: context,
      builder: (ctx) => Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Icon(Icons.directions_bus, size: 48, color: Colors.blue),
            const SizedBox(height: 12),
            Text('Iniciar Viaje - $bus', style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            const Text('Seleccionar estado inicial:'),
            const SizedBox(height: 12),
            ListTile(leading: const Icon(Icons.schedule), title: const Text('Pendiente'), onTap: () { Navigator.pop(ctx); }),
            ListTile(leading: const Icon(Icons.play_arrow, color: Colors.blue), title: const Text('En Curso'), onTap: () { Navigator.pop(ctx); }),
            const SizedBox(height: 16),
          ],
        ),
      ),
    );
  }
}

void main() => runApp(const AppConfirmarViajes());
