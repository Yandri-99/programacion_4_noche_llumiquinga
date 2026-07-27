import 'package:flutter/material.dart';

/// [DartPad] mp_10_form_textformfield.dart
/// Form + TextFormField: Registro de Conductor

void main() => runApp(const MaterialApp(home: RegistroConductor()));

class RegistroConductor extends StatefulWidget {
  const RegistroConductor({super.key});

  @override
  State<RegistroConductor> createState() => _RegistroConductorState();
}

class _RegistroConductorState extends State<RegistroConductor> {
  final _formKey = GlobalKey<FormState>();
  final _nombreCtrl = TextEditingController();
  final _emailCtrl = TextEditingController();
  final _licenciaCtrl = TextEditingController();
  String _tipoLicencia = 'A';

  @override
  void dispose() {
    _nombreCtrl.dispose();
    _emailCtrl.dispose();
    _licenciaCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Registro de Conductor')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const Icon(Icons.person_add, size: 60, color: Colors.blue),
              const SizedBox(height: 16),
              TextFormField(
                controller: _nombreCtrl,
                decoration: const InputDecoration(
                  labelText: 'Nombre completo',
                  prefixIcon: Icon(Icons.person),
                  border: OutlineInputBorder(),
                ),
                validator: (v) => (v == null || v.isEmpty) ? 'Campo obligatorio' : null,
              ),
              const SizedBox(height: 12),
              TextFormField(
                controller: _emailCtrl,
                keyboardType: TextInputType.emailAddress,
                decoration: const InputDecoration(
                  labelText: 'Email',
                  prefixIcon: Icon(Icons.email),
                  border: OutlineInputBorder(),
                ),
                validator: (v) => (v == null || !v.contains('@')) ? 'Email invalido' : null,
              ),
              const SizedBox(height: 12),
              TextFormField(
                controller: _licenciaCtrl,
                decoration: const InputDecoration(
                  labelText: 'No. Licencia',
                  prefixIcon: Icon(Icons.badge),
                  border: OutlineInputBorder(),
                ),
                validator: (v) => (v == null || v.isEmpty) ? 'Campo obligatorio' : null,
              ),
              const SizedBox(height: 12),
              DropdownButtonFormField<String>(
                value: _tipoLicencia,
                decoration: const InputDecoration(
                  labelText: 'Tipo de Licencia',
                  prefixIcon: Icon(Icons.category),
                  border: OutlineInputBorder(),
                ),
                items: ['A', 'B', 'C', 'D'].map((t) => DropdownMenuItem(value: t, child: Text(t))).toList(),
                onChanged: (v) => setState(() => _tipoLicencia = v!),
              ),
              const SizedBox(height: 20),
              ElevatedButton.icon(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text('Conductor registrado: ${_nombreCtrl.text}')),
                    );
                    _nombreCtrl.clear();
                    _emailCtrl.clear();
                    _licenciaCtrl.clear();
                  }
                },
                icon: const Icon(Icons.save),
                label: const Text('Registrar Conductor'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
