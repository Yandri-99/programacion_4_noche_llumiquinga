import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

/// mp_ejercicio1 - FutureBuilder + HTTP: Obtener Rutas desde API
class RutaApi {
  final int id;
  final String name;
  final String origin;
  final String destination;

  const RutaApi(this.id, this.name, this.origin, this.destination);

  factory RutaApi.fromJson(Map<String, dynamic> json) {
    return RutaApi(json['id'], json['name'], json['origin'], json['destination']);
  }
}

class RutasApiScreen extends StatefulWidget {
  const RutasApiScreen({super.key});

  @override
  State<RutasApiScreen> createState() => _RutasApiScreenState();
}

class _RutasApiScreenState extends State<RutasApiScreen> {
  late Future<List<RutaApi>> _futureRutas;

  @override
  void initState() {
    super.initState();
    _futureRutas = _fetchRutas();
  }

  Future<List<RutaApi>> _fetchRutas() async {
    try {
      final response = await http.get(Uri.parse('http://10.0.2.2:8000/api/routes/'));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        final results = data['results'] ?? data;
        return (results as List).map((j) => RutaApi.fromJson(j)).toList();
      }
      throw Exception('Error ${response.statusCode}');
    } catch (e) {
      return [
        const RutaApi(1, 'Quito-Guayaquil', 'Terminal Quito', 'Terminal Guayaquil'),
        const RutaApi(2, 'Cuenca-Loja', 'Terminal Cuenca', 'Terminal Loja'),
        const RutaApi(3, 'Ambato-Riobamba', 'Terminal Ambato', 'Terminal Riobamba'),
      ];
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Rutas desde API'),
        actions: [
          IconButton(icon: const Icon(Icons.refresh), onPressed: () => setState(() => _futureRutas = _fetchRutas())),
        ],
      ),
      body: FutureBuilder<List<RutaApi>>(
        future: _futureRutas,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [CircularProgressIndicator(), SizedBox(height: 16), Text('Cargando rutas...')],
            ));
          }
          if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          }
          final rutas = snapshot.data!;
          return ListView.builder(
            itemCount: rutas.length,
            itemBuilder: (context, index) {
              final r = rutas[index];
              return ListTile(
                leading: CircleAvatar(child: Text('${r.id}')),
                title: Text(r.name),
                subtitle: Text('${r.origin} → ${r.destination}'),
                trailing: const Icon(Icons.chevron_right),
              );
            },
          );
        },
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: RutasApiScreen()));
