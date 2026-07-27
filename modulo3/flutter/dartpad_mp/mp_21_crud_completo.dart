import 'package:flutter/material.dart';

/// [DartPad] mp_21_crud_completo.dart
/// CRUD Completo con Navigator: Gestion de Rutas

class RutaCrud {
  int id;
  String nombre;
  String origen;
  String destino;
  double tarifa;

  RutaCrud(this.id, this.nombre, this.origen, this.destino, this.tarifa);
}

void main() => runApp(const MaterialApp(home: CrudRutas()));

class CrudRutas extends StatefulWidget {
  const CrudRutas({super.key});

  @override
  State<CrudRutas> createState() => _CrudRutasState();
}

class _CrudRutasState extends State<CrudRutas> {
  final List<RutaCrud> _rutas = [
    RutaCrud(1, 'Quito-Guayaquil', 'Quito', 'Guayaquil', 15.0),
    RutaCrud(2, 'Cuenca-Loja', 'Cuenca', 'Loja', 8.5),
    RutaCrud(3, 'Ambato-Riobamba', 'Ambato', 'Riobamba', 6.0),
  ];

  int _nextId = 4;

  void _agregar(RutaCrud r) => setState(() => _rutas.add(r));
  void _actualizar(RutaCrud r) => setState(() {
    final i = _rutas.indexWhere((x) => x.id == r.id);
    if (i >= 0) _rutas[i] = r;
  });
  void _eliminar(int id) => setState(() => _rutas.removeWhere((x) => x.id == id));

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('CRUD Rutas')),
      body: ListView.builder(
        itemCount: _rutas.length,
        itemBuilder: (context, index) {
          final r = _rutas[index];
          return Card(
            margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            child: ListTile(
              leading: CircleAvatar(child: Text('${r.id}')),
              title: Text(r.nombre),
              subtitle: Text('${r.origen} → ${r.destino} - \$${r.tarifa}'),
              trailing: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  IconButton(icon: const Icon(Icons.edit, color: Colors.blue), onPressed: () async {
                    final result = await Navigator.push(context, MaterialPageRoute(builder: (_) => FormRuta(ruta: r)));
                    if (result != null) _actualizar(result);
                  }),
                  IconButton(icon: const Icon(Icons.delete, color: Colors.red), onPressed: () {
                    _eliminar(r.id);
                    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Eliminado: ${r.nombre}')));
                  }),
                ],
              ),
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          final result = await Navigator.push(context, MaterialPageRoute(builder: (_) => FormRuta(nextId: _nextId)));
          if (result != null) { _agregar(result); _nextId++; }
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}

class FormRuta extends StatefulWidget {
  final RutaCrud? ruta;
  final int? nextId;
  const FormRuta({super.key, this.ruta, this.nextId});

  @override
  State<FormRuta> createState() => _FormRutaState();
}

class _FormRutaState extends State<FormRuta> {
  late TextEditingController _nombreCtrl, _origenCtrl, _destinoCtrl, _tarifaCtrl;
  bool get _esEdicion => widget.ruta != null;

  @override
  void initState() {
    super.initState();
    _nombreCtrl = TextEditingController(text: widget.ruta?.nombre ?? '');
    _origenCtrl = TextEditingController(text: widget.ruta?.origen ?? '');
    _destinoCtrl = TextEditingController(text: widget.ruta?.destino ?? '');
    _tarifaCtrl = TextEditingController(text: widget.ruta?.tarifa.toString() ?? '');
  }

  @override
  void dispose() { _nombreCtrl.dispose(); _origenCtrl.dispose(); _destinoCtrl.dispose(); _tarifaCtrl.dispose(); super.dispose(); }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(_esEdicion ? 'Editar Ruta' : 'Nueva Ruta')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(controller: _nombreCtrl, decoration: const InputDecoration(labelText: 'Nombre', border: OutlineInputBorder())),
            const SizedBox(height: 12),
            TextField(controller: _origenCtrl, decoration: const InputDecoration(labelText: 'Origen', border: OutlineInputBorder())),
            const SizedBox(height: 12),
            TextField(controller: _destinoCtrl, decoration: const InputDecoration(labelText: 'Destino', border: OutlineInputBorder())),
            const SizedBox(height: 12),
            TextField(controller: _tarifaCtrl, keyboardType: const TextInputType.numberWithOptions(decimal: true), decoration: const InputDecoration(labelText: 'Tarifa', border: OutlineInputBorder())),
            const SizedBox(height: 20),
            ElevatedButton.icon(
              onPressed: () {
                if (_nombreCtrl.text.isEmpty) return;
                final ruta = RutaCrud(
                  widget.ruta?.id ?? widget.nextId!,
                  _nombreCtrl.text,
                  _origenCtrl.text,
                  _destinoCtrl.text,
                  double.tryParse(_tarifaCtrl.text) ?? 0,
                );
                Navigator.pop(context, ruta);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text(_esEdicion ? 'Ruta actualizada' : 'Ruta creada'), backgroundColor: Colors.green),
                );
              },
              icon: Icon(_esEdicion ? Icons.save : Icons.add),
              label: Text(_esEdicion ? 'Guardar Cambios' : 'Crear Ruta'),
            ),
          ],
        ),
      ),
    );
  }
}
