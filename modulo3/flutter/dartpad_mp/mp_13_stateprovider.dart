import 'package:flutter/material.dart';

/// [DartPad] mp_13_stateprovider.dart
/// Estado simple con ValueNotifier: Lista de Paradas de la Ruta

class Parada {
  final String nombre;
  final String horario;
  bool favorita;

  Parada(this.nombre, this.horario, {this.favorita = false});
}

class ParadasRuta extends StatefulWidget {
  const ParadasRuta({super.key});

  @override
  State<ParadasRuta> createState() => _ParadasRutaState();
}

class _ParadasRutaState extends State<ParadasRuta> {
  final List<Parada> _paradas = [
    Parada('Terminal Norte', '06:00', favorita: true),
    Parada('Av. Amazonas', '06:15'),
    Parada('Centro Historico', '06:30', favorita: true),
    Parada('La Mariscal', '06:45'),
    Parada('Terminal Sur', '07:00'),
    Parada('Zona Industrial', '07:15'),
  ];

  int get _favoritas => _paradas.where((p) => p.favorita).length;

  void _toggleFavorita(int index) {
    setState(() {
      _paradas[index].favorita = !_paradas[index].favorita;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Paradas de la Ruta'),
        actions: [
          Center(
            child: Padding(
              padding: const EdgeInsets.only(right: 16),
              child: Text('Favoritas: $_favoritas', style: const TextStyle(fontSize: 14)),
            ),
          ),
        ],
      ),
      body: ListView.builder(
        itemCount: _paradas.length,
        itemBuilder: (context, index) {
          final p = _paradas[index];
          return Card(
            margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
            child: ListTile(
              leading: CircleAvatar(
                backgroundColor: p.favorita ? Colors.amber[100] : Colors.grey[200],
                child: Text('${index + 1}', style: TextStyle(color: p.favorita ? Colors.amber[800] : Colors.grey)),
              ),
              title: Text(p.nombre),
              subtitle: Text('Llegada: ${p.horario}'),
              trailing: IconButton(
                icon: Icon(p.favorita ? Icons.star : Icons.star_border, color: p.favorita ? Colors.amber : Colors.grey),
                onPressed: () => _toggleFavorita(index),
              ),
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Ruta con $_favoritas paradas favoritas')),
          );
        },
        label: Text('Ver Ruta ($_favoritas favoritas)'),
        icon: const Icon(Icons.navigation),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: ParadasRuta()));
