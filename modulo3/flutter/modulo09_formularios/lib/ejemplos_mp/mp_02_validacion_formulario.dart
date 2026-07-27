import 'package:flutter/material.dart';

/// mp_ejercicio2 - Form + Validación: Registrar Nuevo Vehículo
class FormularioRegistrarVehiculo extends StatefulWidget {
  const FormularioRegistrarVehiculo({super.key});

  @override
  State<FormularioRegistrarVehiculo> createState() => _FormularioRegistrarVehiculoState();
}

class _FormularioRegistrarVehiculoState extends State<FormularioRegistrarVehiculo> {
  final _formKey = GlobalKey<FormState>();
  final _placaCtrl = TextEditingController();
  final _nombreCtrl = TextEditingController();
  final _capacidadCtrl = TextEditingController();
  String _tipo = 'Bus';

  @override
  void dispose() {
    _placaCtrl.dispose();
    _nombreCtrl.dispose();
    _capacidadCtrl.dispose();
    super.dispose();
  }

  void _registrar() {
    if (_formKey.currentState!.validate()) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Vehículo "${_nombreCtrl.text}" registrado - Placa: ${_placaCtrl.text}')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Registrar Vehículo')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('Datos del Vehículo', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
              const SizedBox(height: 16),
              TextFormField(
                controller: _nombreCtrl,
                decoration: const InputDecoration(labelText: 'Nombre', hintText: 'Ej: Bus 105', border: OutlineInputBorder(), prefixIcon: Icon(Icons.badge)),
                validator: (v) => (v == null || v.isEmpty) ? 'Ingrese un nombre' : null,
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _placaCtrl,
                textCapitalization: TextCapitalization.characters,
                decoration: const InputDecoration(labelText: 'Placa', hintText: 'ABC-1234', border: OutlineInputBorder(), prefixIcon: Icon(Icons.confirmation_number)),
                validator: (v) {
                  if (v == null || v.isEmpty) return 'Ingrese la placa';
                  if (v.length < 5) return 'Placa muy corta (mín. 5 caracteres)';
                  return null;
                },
              ),
              const SizedBox(height: 16),
              DropdownButtonFormField<String>(
                value: _tipo,
                decoration: const InputDecoration(labelText: 'Tipo', border: OutlineInputBorder(), prefixIcon: Icon(Icons.category)),
                items: ['Bus', 'Minibus', 'Van', 'Micro'].map((t) => DropdownMenuItem(value: t, child: Text(t))).toList(),
                onChanged: (v) => setState(() => _tipo = v!),
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _capacidadCtrl,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(labelText: 'Capacidad (pasajeros)', hintText: '40', border: OutlineInputBorder(), prefixIcon: Icon(Icons.people)),
                validator: (v) {
                  if (v == null || v.isEmpty) return 'Ingrese la capacidad';
                  final n = int.tryParse(v);
                  if (n == null || n <= 0) return 'Ingrese un número válido';
                  return null;
                },
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: double.infinity,
                child: FilledButton.icon(
                  onPressed: _registrar,
                  icon: const Icon(Icons.add_circle),
                  label: const Text('Registrar Vehículo'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: FormularioRegistrarVehiculo()));
