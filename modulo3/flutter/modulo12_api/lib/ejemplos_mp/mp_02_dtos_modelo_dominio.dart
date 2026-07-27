import 'package:flutter/material.dart';

/// mp_ejercicio2 - DTOs y Modelos de Dominio: Transporte
/// Archivo Dart puro que demuestra patrón DTO -> Domain Model

/// DTO: Lo que llega del API
class VehiculoDto {
  final int id;
  final String name;
  final String placa;
  final String tipo;
  final int capacidad;
  final bool isActive;
  final Map<String, dynamic>? route;

  const VehiculoDto({
    required this.id,
    required this.name,
    required this.placa,
    required this.tipo,
    required this.capacidad,
    required this.isActive,
    this.route,
  });

  factory VehiculoDto.fromJson(Map<String, dynamic> json) {
    return VehiculoDto(
      id: json['id'],
      name: json['name'],
      placa: json['placa'],
      tipo: json['tipo'],
      capacidad: json['capacidad'],
      isActive: json['is_active'] ?? true,
      route: json['route'],
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id, 'name': name, 'placa': placa, 'tipo': tipo,
    'capacidad': capacidad, 'is_active': isActive, 'route': route,
  };
}

/// Domain Model: Modelo de dominio simplificado
class VehiculoDominio {
  final int id;
  final String nombre;
  final String placa;
  final String tipo;
  final int capacidad;
  final bool activo;
  final String? nombreRuta;

  const VehiculoDominio(this.id, this.nombre, this.placa, this.tipo, this.capacidad, this.activo, this.nombreRuta);
}

/// Extensión DTO -> Domain
extension VehiculoDtoExtension on VehiculoDto {
  VehiculoDominio toDomain() {
    return VehiculoDominio(id, name, placa, tipo, capacidad, isActive, route?['name']);
  }
}

/// Demostración
class PantallaTransporteDto extends StatelessWidget {
  const PantallaTransporteDto({super.key});

  static final List<VehiculoDto> _dtoData = [
    VehiculoDto(id: 1, name: 'Bus 101', placa: 'ABC-1234', tipo: 'Bus', capacidad: 40, isActive: true, route: {'id': 1, 'name': 'Quito-Guayaquil'}),
    VehiculoDto(id: 2, name: 'Minibus 02', placa: 'XYZ-5678', tipo: 'Minibus', capacidad: 20, isActive: true),
    VehiculoDto(id: 3, name: 'Van 03', placa: 'LMN-9012', tipo: 'Van', capacidad: 12, isActive: false, route: {'id': 2, 'name': 'Cuenca-Loja'}),
  ];

  @override
  Widget build(BuildContext context) {
    final dominioData = _dtoData.map((dto) => dto.toDomain()).toList();

    return Scaffold(
      appBar: AppBar(title: const Text('DTOs de Transporte')),
      body: ListView(
        padding: const EdgeInsets.all(12),
        children: dominioData.map((v) => Card(
          child: ListTile(
            leading: Icon(Icons.directions_bus, color: v.activo ? Colors.green : Colors.red),
            title: Text('${v.nombre} - ${v.placa}'),
            subtitle: Text('${v.tipo} • ${v.capacidad} pax${v.nombreRuta != null ? ' • ${v.nombreRuta}' : ''}'),
            trailing: Container(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
              decoration: BoxDecoration(
                color: v.activo ? Colors.green[50] : Colors.red[50],
                borderRadius: BorderRadius.circular(8),
              ),
              child: Text(v.activo ? 'Activo' : 'Inactivo',
                  style: TextStyle(color: v.activo ? Colors.green : Colors.red, fontSize: 11)),
            ),
          ),
        )).toList(),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: PantallaTransporteDto()));
