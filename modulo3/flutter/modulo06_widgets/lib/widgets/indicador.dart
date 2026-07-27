import 'package:flutter/material.dart';

class Indicador extends StatelessWidget {
  final String  label;
  final String  valor;          // String para mayor flexibilidad: '8', '4.2 GB', '99%'
  final Color   color;
  final String? subtitulo;      // línea adicional opcional
  final IconData? icono;        // ícono opcional antes del valor

  const Indicador({
    super.key,
    required this.label,
    required this.valor,
    required this.color,
    this.subtitulo,
    this.icono,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize:       MainAxisSize.min,
      children: [
        // Valor principal — con ícono opcional
        Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            if (icono != null) ...[
              Icon(icono, size: 22, color: color),
              const SizedBox(width: 4),
            ],
            Text(
              valor,
              style: TextStyle(
                  fontSize: 28, fontWeight: FontWeight.bold, color: color),
            ),
          ],
        ),
        Text(label,
            style: TextStyle(fontSize: 12, color: Colors.grey.shade600)),
        if (subtitulo != null)
          Text(subtitulo!,
              style: TextStyle(fontSize: 10, color: Colors.grey.shade400)),
      ],
    );
  }
}