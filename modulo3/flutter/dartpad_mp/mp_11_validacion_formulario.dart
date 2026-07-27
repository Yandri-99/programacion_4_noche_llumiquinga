import 'package:flutter/material.dart';

/// [DartPad] mp_11_validacion_formulario.dart
/// Validacion de Formulario: Registro de Ruta

void main() => runApp(const MaterialApp(home: RegistroRuta()));

class RegistroRuta extends StatefulWidget {
  const RegistroRuta({super.key});

  @override
  State<RegistroRuta> createState() => _RegistroRutaState();
}

class _RegistroRutaState extends State<RegistroRuta> {
  final _formKey = GlobalKey<FormState>();
  final _nombreCtrl = TextEditingController();
  final _origenCtrl = TextEditingController();
  final _destinoCtrl = TextEditingController();
  final _tarifaCtrl = TextEditingController();
  final _horarioCtrl = TextEditingController();

  String? _validarRequerido(String? v) => (v == null || v.trim().isEmpty) ? 'Campo obligatorio' : null;

  String? _validarTarifa(String? v) {
    if (v == null || v.trim().isEmpty) return 'Campo obligatorio';
    final n = double.tryParse(v);
    if (n == null || n <= 0) return 'Ingrese un valor valido (> 0)';
    return null;
  }

  @override
  void dispose() {
    _nombreCtrl.dispose();
    _origenCtrl.dispose();
    _destinoCtrl.dispose();
    _tarifaCtrl.dispose();
    _horarioCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Registro de Ruta')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const Icon(Icons.route, size: 60, color: Colors.blue),
              const SizedBox(height: 16),
              TextFormField(
                controller: _nombreCtrl,
                decoration: const InputDecoration(labelText: 'Nombre de la ruta', border: OutlineInputBorder()),
                validator: _validarRequerido,
              ),
              const SizedBox(height: 12),
              TextFormField(
                controller: _origenCtrl,
                decoration: const InputDecoration(labelText: 'Punto de origen', border: OutlineInputBorder()),
                validator: _validarRequerido,
              ),
              const SizedBox(height: 12),
              TextFormField(
                controller: _destinoCtrl,
                decoration: const InputDecoration(labelText: 'Punto de destino', border: OutlineInputBorder()),
                validator: _validarRequerido,
              ),
              const SizedBox(height: 12),
              TextFormField(
                controller: _tarifaCtrl,
                keyboardType: const TextInputType.numberWithOptions(decimal: true),
                decoration: const InputDecoration(labelText: 'Tarifa (\$)', border: OutlineInputBorder()),
                validator: _validarTarifa,
              ),
              const SizedBox(height: 12),
              TextFormField(
                controller: _horarioCtrl,
                decoration: const InputDecoration(labelText: 'Horario (ej: 06:00 - 22:00)', border: OutlineInputBorder()),
                validator: _validarRequerido,
              ),
              const SizedBox(height: 20),
              ElevatedButton.icon(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text('Ruta "${_nombreCtrl.text}" registrada exitosamente'), backgroundColor: Colors.green),
                    );
                  }
                },
                icon: const Icon(Icons.save),
                label: const Text('Registrar Ruta'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
