import 'package:flutter/material.dart';

/// [DartPad] mp_20_dtos_modelo_dominio.dart
/// DTOs, Modelo, Capa de Dominio: Ruta de Transporte

// ─── DTO (simula respuesta JSON) ────────────────────────
class RutaDto {
  final int id;
  final String nombre;
  final String origen;
  final String destino;
  final double tarifa;
  final bool activa;

  const RutaDto({required this.id, required this.nombre, required this.origen, required this.destino, required this.tarifa, required this.activa});

  factory RutaDto.fromJson(Map<String, dynamic> json) => RutaDto(
    id: json['id'] ?? 0,
    nombre: json['nombre'] ?? '',
    origen: json['origen'] ?? '',
    destino: json['destino'] ?? '',
    tarifa: (json['tarifa'] ?? 0).toDouble(),
    activa: json['activa'] ?? true,
  );
}

// ─── Modelo de dominio ──────────────────────────────────
class Ruta {
  final int id;
  final String nombre;
  final String descripcion;
  final String infoCompleta;
  final bool activa;

  const Ruta({required this.id, required this.nombre, required this.descripcion, required this.infoCompleta, required this.activa});

  factory Ruta.fromDto(RutaDto dto) => Ruta(
    id: dto.id,
    nombre: dto.nombre,
    descripcion: '${dto.origen} → ${dto.destino}',
    infoCompleta: '${dto.nombre}: ${dto.origen} a ${dto.destino} (\$${dto.tarifa.toStringAsFixed(2)})',
    activa: dto.activa,
  );
}

// ─── Datos mock ─────────────────────────────────────────
final List<Map<String, dynamic>> _mockData = [
  {'id': 1, 'nombre': 'Quito-Guayaquil', 'origen': 'Quito', 'destino': 'Guayaquil', 'tarifa': 15.0, 'activa': true},
  {'id': 2, 'nombre': 'Cuenca-Loja', 'origen': 'Cuenca', 'destino': 'Loja', 'tarifa': 8.5, 'activa': true},
  {'id': 3, 'nombre': 'Ambato-Riobamba', 'origen': 'Ambato', 'destino': 'Riobamba', 'tarifa': 6.0, 'activa': false},
];

void main() {
  // Parseo DTO → Modelo
  final dtos = _mockData.map((j) => RutaDto.fromJson(j)).toList();
  final rutas = dtos.map((d) => Ruta.fromDto(d)).toList();

  runApp(MaterialApp(
    home: Scaffold(
      appBar: AppBar(title: const Text('DTOs → Modelo → Dominio')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          const Card(
            child: Padding(
              padding: EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('Flujo de datos', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                  Divider(),
                  Text('1. RutaDto.fromJson()  ← JSON'),
                  Text('2. Ruta.fromDto(dto)    ← DTO'),
                  Text('3. Ruta (modelo dominio) → UI'),
                ],
              ),
            ),
          ),
          const SizedBox(height: 12),
          ...rutas.map((r) => Card(
            margin: const EdgeInsets.only(bottom: 8),
            child: ListTile(
              leading: CircleAvatar(
                backgroundColor: r.activa ? Colors.green[100] : Colors.red[100],
                child: Icon(Icons.route, color: r.activa ? Colors.green : Colors.red),
              ),
              title: Text(r.nombre, style: const TextStyle(fontWeight: FontWeight.bold)),
              subtitle: Text(r.descripcion),
              trailing: Text(r.activa ? 'Activa' : 'Inactiva', style: TextStyle(color: r.activa ? Colors.green : Colors.red)),
            ),
          )),
        ],
      ),
    ),
  ));
}
