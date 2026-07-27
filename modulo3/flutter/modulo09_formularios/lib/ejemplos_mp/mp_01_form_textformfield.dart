import 'package:flutter/material.dart';

/// mp_ejercicio1 - Form + TextFormField: Crear Nueva Ruta de Transporte
class FormularioCrearRuta extends StatefulWidget {
  const FormularioCrearRuta({super.key});

  @override
  State<FormularioCrearRuta> createState() => _FormularioCrearRutaState();
}

class _FormularioCrearRutaState extends State<FormularioCrearRuta> {
  final _formKey = GlobalKey<FormState>();
  final _nombreCtrl = TextEditingController();
  final _origenCtrl = TextEditingController();
  final _destinoCtrl = TextEditingController();
  final _tarifaCtrl = TextEditingController();
  String _tipoBus = 'Bus';

  @override
  void dispose() {
    _nombreCtrl.dispose();
    _origenCtrl.dispose();
    _destinoCtrl.dispose();
    _tarifaCtrl.dispose();
    super.dispose();
  }

  void _guardar() {
    if (_formKey.currentState!.validate()) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Ruta "${_nombreCtrl.text}" creada ($_tipoBus, \$${_tarifaCtrl.text})')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Crear Nueva Ruta')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('Información de la Ruta', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
              const SizedBox(height: 16),
              TextFormField(
                controller: _nombreCtrl,
                decoration: const InputDecoration(labelText: 'Nombre de la Ruta', hintText: 'Ej: Quito-Guayaquil', border: OutlineInputBorder(), prefixIcon: Icon(Icons.route)),
                validator: (v) => (v == null || v.isEmpty) ? 'Ingrese un nombre' : null,
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _origenCtrl,
                decoration: const InputDecoration(labelText: 'Origen', hintText: 'Terminal de origen', border: OutlineInputBorder(), prefixIcon: Icon(Icons.location_on)),
                validator: (v) => (v == null || v.isEmpty) ? 'Ingrese el origen' : null,
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _destinoCtrl,
                decoration: const InputDecoration(labelText: 'Destino', hintText: 'Terminal de destino', border: OutlineInputBorder(), prefixIcon: Icon(Icons.location_city)),
                validator: (v) => (v == null || v.isEmpty) ? 'Ingrese el destino' : null,
              ),
              const SizedBox(height: 16),
              DropdownButtonFormField<String>(
                value: _tipoBus,
                decoration: const InputDecoration(labelText: 'Tipo de Vehículo', border: OutlineInputBorder(), prefixIcon: Icon(Icons.directions_bus)),
                items: ['Bus', 'Minibus', 'Van', 'Micro'].map((t) => DropdownMenuItem(value: t, child: Text(t))).toList(),
                onChanged: (v) => setState(() => _tipoBus = v!),
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _tarifaCtrl,
                keyboardType: const TextInputType.numberWithOptions(decimal: true),
                decoration: const InputDecoration(labelText: 'Tarifa (\$)', hintText: '0.00', border: OutlineInputBorder(), prefixIcon: Icon(Icons.attach_money)),
                validator: (v) {
                  if (v == null || v.isEmpty) return 'Ingrese la tarifa';
                  if (double.tryParse(v) == null) return 'Ingrese un número válido';
                  return null;
                },
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: double.infinity,
                child: FilledButton.icon(
                  onPressed: _guardar,
                  icon: const Icon(Icons.save),
                  label: const Text('Crear Ruta'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: FormularioCrearRuta()));
