import 'package:flutter/material.dart';

/// mp_ejercicio3 - SearchBar + ListView.builder: Buscar y Filtrar Viajes
class ViajeBusqueda {
  final String id;
  final String ruta;
  final String bus;
  final String estado;
  final int pasajeros;
  final String fecha;

  const ViajeBusqueda(this.id, this.ruta, this.bus, this.estado, this.pasajeros, this.fecha);
}

class PantallaBuscarViajes extends StatefulWidget {
  const PantallaBuscarViajes({super.key});

  @override
  State<PantallaBuscarViajes> createState() => _PantallaBuscarViajesState();
}

class _PantallaBuscarViajesState extends State<PantallaBuscarViajes> {
  final _busquedaCtrl = TextEditingController();
  String _filtroEstado = 'Todos';

  static const List<ViajeBusqueda> _viajes = [
    ViajeBusqueda('1', 'Quito → Guayaquil', 'Bus 101', 'En curso', 35, '2026-07-20'),
    ViajeBusqueda('2', 'Cuenca → Loja', 'Minibus 02', 'Completado', 18, '2026-07-19'),
    ViajeBusqueda('3', 'Ambato → Riobamba', 'Bus 103', 'Pendiente', 0, '2026-07-20'),
    ViajeBusqueda('4', 'Machala → Pasaje', 'Van 04', 'Completado', 10, '2026-07-18'),
    ViajeBusqueda('5', 'Quito → Latacunga', 'Bus 105', 'Cancelado', 0, '2026-07-17'),
    ViajeBusqueda('6', 'Guayaquil → Manta', 'Minibus 06', 'En curso', 15, '2026-07-20'),
  ];

  List<ViajeBusqueda> get _filtrados => _viajes.where((v) {
    final matchBusqueda = v.ruta.toLowerCase().contains(_busquedaCtrl.text.toLowerCase()) ||
        v.bus.toLowerCase().contains(_busquedaCtrl.text.toLowerCase());
    final matchEstado = _filtroEstado == 'Todos' || v.estado == _filtroEstado;
    return matchBusqueda && matchEstado;
  }).toList();

  Color _colorEstado(String estado) {
    switch (estado) {
      case 'En curso': return Colors.blue;
      case 'Completado': return Colors.green;
      case 'Pendiente': return Colors.orange;
      case 'Cancelado': return Colors.red;
      default: return Colors.grey;
    }
  }

  @override
  void dispose() { _busquedaCtrl.dispose(); super.dispose(); }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Buscar Viajes')),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(12),
            child: SearchBar(
              controller: _busquedaCtrl,
              hintText: 'Buscar por ruta o bus...',
              leading: const Padding(padding: EdgeInsets.only(left: 8), child: Icon(Icons.search)),
              onChanged: (_) => setState(() {}),
            ),
          ),
          SizedBox(
            height: 40,
            child: ListView(
              scrollDirection: Axis.horizontal,
              padding: const EdgeInsets.symmetric(horizontal: 12),
              children: ['Todos', 'En curso', 'Completado', 'Pendiente', 'Cancelado'].map((e) =>
                Padding(
                  padding: const EdgeInsets.only(right: 8),
                  child: FilterChip(
                    label: Text(e),
                    selected: _filtroEstado == e,
                    onSelected: (_) => setState(() => _filtroEstado = e),
                  ),
                ),
              ).toList(),
            ),
          ),
          const SizedBox(height: 8),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12),
            child: Text('${_filtrados.length} viajes encontrados', style: TextStyle(color: Colors.grey[600])),
          ),
          Expanded(
            child: _filtrados.isEmpty
                ? const Center(child: Text('No se encontraron viajes'))
                : ListView.builder(
                    itemCount: _filtrados.length,
                    itemBuilder: (context, i) {
                      final v = _filtrados[i];
                      return ListTile(
                        leading: CircleAvatar(child: Text(v.id)),
                        title: Text(v.ruta),
                        subtitle: Text('${v.bus} • ${v.fecha}'),
                        trailing: Container(
                          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                          decoration: BoxDecoration(color: _colorEstado(v.estado).withOpacity(0.15), borderRadius: BorderRadius.circular(8)),
                          child: Text(v.estado, style: TextStyle(color: _colorEstado(v.estado), fontSize: 12)),
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

void main() => runApp(const MaterialApp(home: PantallaBuscarViajes()));
