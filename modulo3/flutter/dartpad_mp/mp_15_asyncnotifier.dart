import 'package:flutter/material.dart';

/// [DartPad] mp_15_asyncnotifier.dart
/// Async simulado: Lista de Viajes con Future.delayed (sin paquetes externos)

class Viaje {
  final int id;
  final String ruta;
  final String conductor;
  final String hora;
  final String estado;

  const Viaje(this.id, this.ruta, this.conductor, this.hora, this.estado);
}

void main() => runApp(const MaterialApp(home: ListaViajes()));

class ListaViajes extends StatefulWidget {
  const ListaViajes({super.key});

  @override
  State<ListaViajes> createState() => _ListaViajesState();
}

class _ListaViajesState extends State<ListaViajes> {
  late Future<List<Viaje>> _futureViajes;

  @override
  void initState() {
    super.initState();
    _futureViajes = _cargarViajes();
  }

  Future<List<Viaje>> _cargarViajes() async {
    await Future.delayed(const Duration(seconds: 2));
    return const [
      Viaje(1, 'Quito - Guayaquil', 'Juan Perez', '08:00', 'Completado'),
      Viaje(2, 'Cuenca - Loja', 'Maria Lopez', '09:30', 'En Curso'),
      Viaje(3, 'Ambato - Riobamba', 'Carlos Garcia', '10:15', 'Pendiente'),
      Viaje(4, 'Quito - Latacunga', 'Ana Martinez', '11:00', 'Completado'),
      Viaje(5, 'Guayaquil - Manta', 'Luis Rodriguez', '12:30', 'En Curso'),
    ];
  }

  void _recargar() {
    setState(() {
      _futureViajes = _cargarViajes();
    });
  }

  Color _colorEstado(String estado) {
    switch (estado) {
      case 'Completado': return Colors.green;
      case 'En Curso': return Colors.blue;
      case 'Pendiente': return Colors.orange;
      default: return Colors.grey;
    }
  }

  IconData _iconoEstado(String estado) {
    switch (estado) {
      case 'Completado': return Icons.check_circle;
      case 'En Curso': return Icons.play_circle;
      case 'Pendiente': return Icons.schedule;
      default: return Icons.help;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Viajes (Async)'),
        actions: [IconButton(icon: const Icon(Icons.refresh), onPressed: _recargar)],
      ),
      body: FutureBuilder<List<Viaje>>(
        future: _futureViajes,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  CircularProgressIndicator(),
                  SizedBox(height: 16),
                  Text('Cargando viajes...'),
                ],
              ),
            );
          }
          if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          }
          final viajes = snapshot.data!;
          return ListView.builder(
            itemCount: viajes.length,
            itemBuilder: (context, index) {
              final v = viajes[index];
              return Card(
                margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                child: ListTile(
                  leading: CircleAvatar(
                    backgroundColor: _colorEstado(v.estado).withOpacity(0.15),
                    child: Icon(_iconoEstado(v.estado), color: _colorEstado(v.estado)),
                  ),
                  title: Text(v.ruta),
                  subtitle: Text('${v.conductor} - ${v.hora}'),
                  trailing: Container(
                    padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                    decoration: BoxDecoration(color: _colorEstado(v.estado), borderRadius: BorderRadius.circular(12)),
                    child: Text(v.estado, style: const TextStyle(color: Colors.white, fontSize: 11)),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
