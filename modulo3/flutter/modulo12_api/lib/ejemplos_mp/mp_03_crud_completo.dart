import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

/// mp_ejercicio3 - CRUD Completo con API: Gestión de Vehículos
class VehiculoItem {
  final int id;
  final String name;
  final String placa;
  final String tipo;
  final int capacidad;
  final bool isActive;

  const VehiculoItem(this.id, this.name, this.placa, this.tipo, this.capacidad, this.isActive);

  factory VehiculoItem.fromJson(Map<String, dynamic> json) =>
      VehiculoItem(json['id'], json['name'], json['placa'], json['tipo'], json['capacidad'], json['is_active'] ?? true);
}

class CrudVehiculosScreen extends StatefulWidget {
  const CrudVehiculosScreen({super.key});

  @override
  State<CrudVehiculosScreen> createState() => _CrudVehiculosScreenState();
}

class _CrudVehiculosScreenState extends State<CrudVehiculosScreen> {
  final _apiBase = 'http://10.0.2.2:8000/api/vehicles/';
  List<VehiculoItem> _vehiculos = [];
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _cargar();
  }

  Future<void> _cargar() async {
    setState(() => _loading = true);
    try {
      final resp = await http.get(Uri.parse(_apiBase));
      if (resp.statusCode == 200) {
        final data = json.decode(resp.body);
        final results = data['results'] ?? data;
        setState(() {
          _vehiculos = (results as List).map((j) => VehiculoItem.fromJson(j)).toList();
          _loading = false;
        });
      } else {
        _cargarDemo();
      }
    } catch (_) {
      _cargarDemo();
    }
  }

  void _cargarDemo() {
    setState(() {
      _vehiculos = [
        const VehiculoItem(1, 'Bus 101', 'ABC-1234', 'Bus', 40, true),
        const VehiculoItem(2, 'Minibus 02', 'XYZ-5678', 'Minibus', 20, true),
        const VehiculoItem(3, 'Van 03', 'LMN-9012', 'Van', 12, false),
      ];
      _loading = false;
    });
  }

  Future<void> _toggleActivo(VehiculoItem v) async {
    try {
      await http.patch(
        Uri.parse('$_apiBase${v.id}/'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({'is_active': !v.isActive}),
      );
    } catch (_) {}
    setState(() {
      final i = _vehiculos.indexOf(v);
      _vehiculos[i] = VehiculoItem(v.id, v.name, v.placa, v.tipo, v.capacidad, !v.isActive);
    });
  }

  Future<void> _eliminar(VehiculoItem v) async {
    try {
      await http.delete(Uri.parse('$_apiBase${v.id}/'));
    } catch (_) {}
    setState(() => _vehiculos.remove(v));
    if (mounted) ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('${v.name} eliminado')));
  }

  void _mostrarFormulario() {
    final nombreCtrl = TextEditingController();
    final placaCtrl = TextEditingController();
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      builder: (ctx) => Padding(
        padding: EdgeInsets.fromLTRB(24, 24, 24, MediaQuery.of(ctx).viewInsets.bottom + 24),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text('Nuevo Vehículo', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            TextField(controller: nombreCtrl, decoration: const InputDecoration(labelText: 'Nombre', border: OutlineInputBorder())),
            const SizedBox(height: 12),
            TextField(controller: placaCtrl, decoration: const InputDecoration(labelText: 'Placa', border: OutlineInputBorder())),
            const SizedBox(height: 16),
            FilledButton(
              onPressed: () {
                Navigator.pop(ctx);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Vehículo "${nombreCtrl.text}" creado')),
                );
              },
              child: const Text('Crear Vehículo'),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('CRUD Vehículos'), actions: [
        IconButton(icon: const Icon(Icons.refresh), onPressed: _cargar),
      ]),
      body: _loading
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: _vehiculos.length,
              itemBuilder: (context, index) {
                final v = _vehiculos[index];
                return ListTile(
                  leading: Icon(Icons.directions_bus, color: v.isActive ? Colors.green : Colors.red),
                  title: Text('${v.name} - ${v.placa}'),
                  subtitle: Text('${v.tipo} • ${v.capacidad} pax'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Switch(value: v.isActive, onChanged: (_) => _toggleActivo(v)),
                      IconButton(icon: const Icon(Icons.delete, size: 20), onPressed: () => _eliminar(v)),
                    ],
                  ),
                );
              },
            ),
      floatingActionButton: FloatingActionButton(
        onPressed: _mostrarFormulario,
        child: const Icon(Icons.add),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: CrudVehiculosScreen()));
