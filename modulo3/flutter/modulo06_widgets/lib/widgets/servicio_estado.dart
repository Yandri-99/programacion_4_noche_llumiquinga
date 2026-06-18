import 'package:flutter/material.dart';

class ServicioEstado extends StatefulWidget {
  final String nombre;
  const ServicioEstado({super.key, required this.nombre});

  @override
  State<ServicioEstado> createState() => _ServicioEstadoState();
}

class _ServicioEstadoState extends State<ServicioEstado> {
  bool _activo = true;
  int _reinicios = 0;

  static const int _maxReinicios = 3;

  void _toggle() {
    setState(() {
      // notifica a Flutter → rebuild
      _activo = !_activo;
      if (_activo) _reinicios++;
    });
  }

  @override
  Widget build(BuildContext context) {
    final enLimite = _reinicios >= _maxReinicios;

    return Padding(
      padding: const EdgeInsets.all(24),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // ── Patrón 1: Ícono + color condicional ─────────────────
          Icon(
            _activo ? Icons.check_circle : Icons.cancel,
            size: 72,
            color: _activo ? Colors.green : Colors.red,
          ),
          const SizedBox(height: 8),

          Text(
            widget.nombre,
            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
          ),

          // ── Patrón 2: Texto condicional ──────────────────────────
          Text(
            _activo ? 'En línea' : 'Fuera de línea',
            style: TextStyle(
              fontSize: 15,
              fontWeight: FontWeight.w600,
              color: _activo ? Colors.green.shade700 : Colors.red.shade700,
            ),
          ),
          const SizedBox(height: 16),

          // ── Patrón 3: Widget que aparece / desaparece ────────────
          if (!_activo)
            Container(
              margin: const EdgeInsets.only(bottom: 16),
              padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 8),
              decoration: BoxDecoration(
                color: Colors.red.shade50,
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.red.shade300),
              ),
              child: const Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Icon(Icons.warning_amber, color: Colors.red, size: 16),
                  SizedBox(width: 6),
                  Text(
                    'Requiere atención',
                    style: TextStyle(color: Colors.red, fontSize: 13),
                  ),
                ],
              ),
            ),

          // ── Patrón 4: Botón con texto, color y estado dinámicos ──
          FilledButton.icon(
            onPressed: enLimite ? null : _toggle, // null = desactivado
            icon: Icon(_activo ? Icons.stop : Icons.play_arrow),
            label: Text(_activo ? 'Detener servicio' : 'Iniciar servicio'),
            style: FilledButton.styleFrom(
              backgroundColor: _activo
                  ? Colors.red.shade600
                  : Colors.green.shade600,
            ),
          ),
          const SizedBox(height: 12),

          // ── Patrón 5: Opacidad condicional ───────────────────────
          Opacity(
            opacity: enLimite ? 0.4 : 1.0,
            child: Text(
              'Reinicios: $_reinicios / $_maxReinicios',
              style: TextStyle(
                fontSize: 13,
                color: enLimite ? Colors.red : Colors.grey.shade600,
              ),
            ),
          ),

          // ── Patrón 6: Widget condicional por otro estado ─────────
          if (enLimite)
            Padding(
              padding: const EdgeInsets.only(top: 8),
              child: Text(
                'Límite de reinicios alcanzado',
                style: TextStyle(
                  fontSize: 12,
                  color: Colors.red.shade700,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
        ],
      ),
    );
  }
}
