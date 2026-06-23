import 'package:flutter/material.dart';

class AvatarBadge extends StatelessWidget {
  final String nombre;
  final int alertas;
  final bool activo;
  final String detalle;

  const AvatarBadge({
    super.key,
    required this.nombre,
    required this.alertas,
    required this.activo,
    required this.detalle,
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      clipBehavior: Clip.none, // permite que el badge salga del Stack
      children: [
        // Avatar — capa inferior
        Container(
          width: 56,
          height: 56,
          decoration: BoxDecoration(
            color: activo ? Colors.indigo.shade100 : Colors.grey.shade200,
            borderRadius: BorderRadius.circular(12),
          ),
          child: Center(
            child: Text(
              nombre.substring(0, 2).toUpperCase(),
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 18,
                color: activo ? Colors.indigo : Colors.grey,
              ),
            ),
          ),
        ),

        // Punto de estado — esquina inferior derecha
        Positioned(
          bottom: 0,
          right: 0,
          child: Container(
            width: 14,
            height: 14,
            decoration: BoxDecoration(
              color: activo ? Colors.green : Colors.red,
              shape: BoxShape.circle,
              border: Border.all(color: Colors.white, width: 2),
            ),
          ),
        ),

        // Badge de alertas — capa superior, solo si las hay
        if (alertas > 0)
          Positioned(
            top: -4,
            right: -4,
            child: Container(
              padding: const EdgeInsets.all(4),
              decoration: const BoxDecoration(
                color: Colors.orange,
                shape: BoxShape.circle,
              ),
              constraints: const BoxConstraints(minWidth: 18, minHeight: 18),
              child: Text(
                alertas > 9 ? '9+' : '$alertas',
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 10,
                  fontWeight: FontWeight.bold,
                ),
                textAlign: TextAlign.center,
              ),
            ),
          ),
      ],
    );
  }
}
