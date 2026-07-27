import 'package:flutter/material.dart';

/// [DartPad] mp_04_column_row.dart
/// Column + Row: Panel de Estadísticas de Transporte

class EstadisticasTransporte extends StatelessWidget {
  const EstadisticasTransporte({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Panel de Transporte')),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
              color: Colors.blue[800],
              child: const Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('Sistema de Transporte Publico',
                      style: TextStyle(color: Colors.white, fontSize: 22, fontWeight: FontWeight.bold)),
                  SizedBox(height: 4),
                  Text('Resumen del dia', style: TextStyle(color: Colors.white70, fontSize: 14)),
                ],
              ),
            ),
            _filaEstadistica(Icons.directions_bus, 'Vehiculos Activos', '12', Colors.green),
            _filaEstadistica(Icons.route, 'Rutas Operando', '8', Colors.blue),
            _filaEstadistica(Icons.people, 'Pasajeros Hoy', '1,250', Colors.orange),
            _filaEstadistica(Icons.attach_money, 'Ingresos del Dia', '\$4,320', Colors.purple),
            const Padding(
              padding: EdgeInsets.all(16),
              child: Text('Ultimos Viajes', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            ),
            _filaViaje('08:15', 'Quito - Guayaquil', 'Bus 101', '35 pasajeros'),
            _filaViaje('09:30', 'Cuenca - Loja', 'Minibus 02', '18 pasajeros'),
            _filaViaje('10:45', 'Ambato - Riobamba', 'Bus 103', '40 pasajeros'),
          ],
        ),
      ),
    );
  }

  Widget _filaEstadistica(IconData icono, String label, String valor, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
      child: Row(
        children: [
          CircleAvatar(backgroundColor: color.withOpacity(0.15), child: Icon(icono, color: color)),
          const SizedBox(width: 12),
          Expanded(child: Text(label, style: const TextStyle(fontSize: 15))),
          Text(valor, style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: color)),
        ],
      ),
    );
  }

  Widget _filaViaje(String hora, String ruta, String bus, String pax) {
    return ListTile(
      leading: CircleAvatar(child: Text(hora, style: const TextStyle(fontSize: 12))),
      title: Text(ruta),
      subtitle: Text('$bus - $pax'),
      trailing: const Icon(Icons.chevron_right),
    );
  }
}

void main() => runApp(const MaterialApp(home: EstadisticasTransporte()));
