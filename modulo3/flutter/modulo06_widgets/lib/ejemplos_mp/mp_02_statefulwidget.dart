import 'package:flutter/material.dart';

/// mp_ejercicio2 - StatefulWidget: Contador de Pasajeros del Bus
class ContadorPasajeros extends StatefulWidget {
  final int capacidadMaxima;

  const ContadorPasajeros({super.key, this.capacidadMaxima = 40});

  @override
  State<ContadorPasajeros> createState() => _ContadorPasajerosState();
}

class _ContadorPasajerosState extends State<ContadorPasajeros> {
  int _pasajeros = 0;

  void _agregar() {
    setState(() {
      if (_pasajeros < widget.capacidadMaxima) _pasajeros++;
    });
  }

  void _quitar() {
    setState(() {
      if (_pasajeros > 0) _pasajeros--;
    });
  }

  void _resetear() {
    setState(() => _pasajeros = 0);
  }

  double get _porcentaje => _pasajeros / widget.capacidadMaxima;

  Color get _colorEstado {
    if (_porcentaje < 0.5) return Colors.green;
    if (_porcentaje < 0.8) return Colors.orange;
    return Colors.red;
  }

  String get _textoEstado {
    if (_porcentaje < 0.5) return 'Disponible';
    if (_porcentaje < 0.8) return 'Casi lleno';
    return 'Bus LLENO';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Contador de Pasajeros')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.directions_bus, size: 80, color: _colorEstado),
            const SizedBox(height: 16),
            Text('Bus #101 - Ruta Centro',
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
            const SizedBox(height: 24),
            Text('$_pasajeros / ${widget.capacidadMaxima}',
                style: TextStyle(fontSize: 48, fontWeight: FontWeight.bold, color: _colorEstado)),
            const SizedBox(height: 8),
            LinearProgressIndicator(
              value: _porcentaje,
              backgroundColor: Colors.grey[300],
              valueColor: AlwaysStoppedAnimation<Color>(_colorEstado),
              minHeight: 10,
            ),
            const SizedBox(height: 8),
            Text(_textoEstado, style: TextStyle(color: _colorEstado, fontSize: 16, fontWeight: FontWeight.w600)),
            const SizedBox(height: 32),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                ElevatedButton.icon(
                  onPressed: _pasajeros > 0 ? _quitar : null,
                  icon: const Icon(Icons.remove),
                  label: const Text('Bajar'),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
                ),
                const SizedBox(width: 16),
                ElevatedButton.icon(
                  onPressed: _resetear,
                  icon: const Icon(Icons.refresh),
                  label: const Text('Reset'),
                ),
                const SizedBox(width: 16),
                ElevatedButton.icon(
                  onPressed: _pasajeros < widget.capacidadMaxima ? _agregar : null,
                  icon: const Icon(Icons.add),
                  label: const Text('Subir'),
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

void main() => runApp(const MaterialApp(home: ContadorPasajeros(capacidadMaxima: 40)));
