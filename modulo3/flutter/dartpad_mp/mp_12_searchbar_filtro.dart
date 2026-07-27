import 'package:flutter/material.dart';

/// [DartPad] mp_12_searchbar_filtro.dart
/// SearchBar + Filtro: Busqueda de Rutas de Transporte

void main() => runApp(const MaterialApp(home: BusquedaRutas()));

class RutaInfo {
  final String nombre;
  final String origen;
  final String destino;
  final double tarifa;
  final String horario;

  const RutaInfo(this.nombre, this.origen, this.destino, this.tarifa, this.horario);
}

class BusquedaRutas extends StatefulWidget {
  const BusquedaRutas({super.key});

  @override
  State<BusquedaRutas> createState() => _BusquedaRutasState();
}

class _BusquedaRutasState extends State<BusquedaRutas> {
  final _ctrl = TextEditingController();
  String _filtro = 'Todos';

  final List<RutaInfo> _todas = [
    const RutaInfo('Ruta Norte', 'Terminal Norte', 'Centro', 2.50, '06:00-22:00'),
    const RutaInfo('Ruta Sur', 'Terminal Sur', 'Centro', 3.00, '05:30-21:30'),
    const RutaInfo('Ruta Aeropuerto', 'Centro', 'Aeropuerto', 8.00, '24h'),
    const RutaInfo('Ruta Universitaria', 'Centro', 'Universidad', 1.50, '07:00-20:00'),
    const RutaInfo('Ruta Industrial', 'Zona Industrial', 'Centro', 2.00, '06:00-18:00'),
    const RutaInfo('Ruta Nocturna', 'Terminal Norte', 'Terminal Sur', 4.00, '22:00-06:00'),
  ];

  List<RutaInfo> get _filtradas {
    var lista = _todas;
    if (_ctrl.text.isNotEmpty) {
      final q = _ctrl.text.toLowerCase();
      lista = lista.where((r) =>
        r.nombre.toLowerCase().contains(q) ||
        r.origen.toLowerCase().contains(q) ||
        r.destino.toLowerCase().contains(q)
      ).toList();
    }
    if (_filtro == 'Economicas') lista = lista.where((r) => r.tarifa < 3).toList();
    if (_filtro == 'Premium') lista = lista.where((r) => r.tarifa >= 5).toList();
    return lista;
  }

  @override
  void dispose() {
    _ctrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Busqueda de Rutas')),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(12),
            child: TextField(
              controller: _ctrl,
              decoration: InputDecoration(
                hintText: 'Buscar por nombre, origen o destino...',
                prefixIcon: const Icon(Icons.search),
                border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                suffixIcon: _ctrl.text.isNotEmpty
                  ? IconButton(icon: const Icon(Icons.clear), onPressed: () { _ctrl.clear(); setState(() {}); })
                  : null,
              ),
              onChanged: (_) => setState(() {}),
            ),
          ),
          SizedBox(
            height: 40,
            child: ListView(
              scrollDirection: Axis.horizontal,
              padding: const EdgeInsets.symmetric(horizontal: 12),
              children: ['Todos', 'Economicas', 'Premium'].map((f) {
                final sel = _filtro == f;
                return Padding(
                  padding: const EdgeInsets.only(right: 8),
                  child: FilterChip(
                    label: Text(f),
                    selected: sel,
                    onSelected: (_) => setState(() => _filtro = f),
                  ),
                );
              }).toList(),
            ),
          ),
          const SizedBox(height: 8),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Text('${_filtradas.length} rutas encontradas', style: TextStyle(color: Colors.grey[600])),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: _filtradas.length,
              itemBuilder: (context, index) {
                final r = _filtradas[index];
                return Card(
                  margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                  child: ListTile(
                    leading: const CircleAvatar(child: Icon(Icons.route)),
                    title: Text(r.nombre),
                    subtitle: Text('${r.origen} → ${r.destino}'),
                    trailing: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text('\$${r.tarifa}', style: const TextStyle(fontWeight: FontWeight.bold, color: Colors.green)),
                        Text(r.horario, style: const TextStyle(fontSize: 10)),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
