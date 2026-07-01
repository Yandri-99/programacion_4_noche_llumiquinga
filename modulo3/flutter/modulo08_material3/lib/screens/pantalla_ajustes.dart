// lib/screens/pantalla_ajustes.dart
import 'package:flutter/material.dart';

class PantallaAjustes extends StatelessWidget {
  final ThemeMode themeMode;
  final Color     seedColor;
  final void Function(ThemeMode) onThemeMode;
  final void Function(Color)     onSeedColor;
  final List<({String nombre, Color color})> paletas;

  const PantallaAjustes({
    super.key,
    required this.themeMode,
    required this.seedColor,
    required this.onThemeMode,
    required this.onSeedColor,
    required this.paletas,
  });

  @override
  Widget build(BuildContext context) {
    final cs   = Theme.of(context).colorScheme;
    final text = Theme.of(context).textTheme;

    return Scaffold(
      appBar: AppBar(
        title:           const Text('Apariencia'),
        backgroundColor: cs.surfaceContainerHighest,
      ),
      body: ListView(
        children: [

          // ── Sección: Tema ─────────────────────────────────────────
          _SeccionTitulo('Tema'),
          ...[
            (label: 'Sistema', mode: ThemeMode.system, icon: Icons.brightness_auto),
            (label: 'Claro',   mode: ThemeMode.light,  icon: Icons.light_mode),
            (label: 'Oscuro',  mode: ThemeMode.dark,   icon: Icons.dark_mode),
          ].map((opcion) => RadioListTile<ThemeMode>(
            title:     Text(opcion.label),
            secondary: Icon(opcion.icon),
            value:     opcion.mode,
            groupValue: themeMode,
            onChanged:  (v) => onThemeMode(v!),
          )),

          const Divider(),

          // ── Sección: Color de acento ──────────────────────────────
          _SeccionTitulo('Color de acento'),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Wrap(
              spacing: 12,
              children: paletas.map((p) {
                final seleccionado = seedColor == p.color;
                return GestureDetector(
                  onTap: () => onSeedColor(p.color),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      AnimatedContainer(
                        duration: const Duration(milliseconds: 200),
                        width:  48,
                        height: 48,
                        decoration: BoxDecoration(
                          color:  p.color,
                          shape:  BoxShape.circle,
                          border: seleccionado
                              ? Border.all(color: cs.onSurface, width: 3)
                              : null,
                          boxShadow: seleccionado
                              ? [BoxShadow(
                                  color:      p.color.withOpacity(0.5),
                                  blurRadius: 8,
                                )]
                              : null,
                        ),
                        child: seleccionado
                            ? const Icon(Icons.check, color: Colors.white)
                            : null,
                      ),
                      const SizedBox(height: 4),
                      Text(p.nombre, style: text.labelSmall),
                    ],
                  ),
                );
              }).toList(),
            ),
          ),

          const Divider(),

          // ── Sección: Vista previa ─────────────────────────────────
          _SeccionTitulo('Vista previa'),
          Padding(
            padding: const EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Tarjeta de servidor con botones
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('prod-web-01',
                            style: text.titleMedium?.copyWith(
                                fontWeight: FontWeight.bold)),
                        Text('10.0.2.10 · Ubuntu 24.04',
                            style: text.bodySmall?.copyWith(
                                color: cs.onSurfaceVariant)),
                        const SizedBox(height: 12),
                        Row(children: [
                          FilledButton.icon(
                            onPressed: () {},
                            icon:  const Icon(Icons.terminal, size: 16),
                            label: const Text('SSH'),
                          ),
                          const SizedBox(width: 8),
                          OutlinedButton(
                            onPressed: () {},
                            child: const Text('Métricas'),
                          ),
                          const SizedBox(width: 8),
                          TextButton(
                            onPressed: () {},
                            child: const Text('Logs'),
                          ),
                        ]),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 12),
                // Paleta activa
                Text('Colores activos:',
                    style: text.labelMedium?.copyWith(
                        color: cs.onSurfaceVariant)),
                const SizedBox(height: 6),
                Row(children: [
                  _CirculoColor(color: cs.primary,                label: 'P'),
                  _CirculoColor(color: cs.secondary,              label: 'S'),
                  _CirculoColor(color: cs.tertiary,               label: 'T'),
                  _CirculoColor(color: cs.error,                  label: 'E'),
                  _CirculoColor(color: cs.surfaceContainerHighest, label: 'Sf'),
                ]),
              ],
            ),
          ),

          const SizedBox(height: 32),
        ],
      ),
    );
  }
}

class _SeccionTitulo extends StatelessWidget {
  final String texto;
  const _SeccionTitulo(this.texto);

  @override
  Widget build(BuildContext context) => Padding(
    padding: const EdgeInsets.fromLTRB(16, 16, 16, 4),
    child: Text(
      texto,
      style: Theme.of(context).textTheme.labelLarge?.copyWith(
        color: Theme.of(context).colorScheme.primary,
      ),
    ),
  );
}

class _CirculoColor extends StatelessWidget {
  final Color  color;
  final String label;
  const _CirculoColor({required this.color, required this.label});

  @override
  Widget build(BuildContext context) => Padding(
    padding: const EdgeInsets.only(right: 8),
    child: CircleAvatar(
      radius:          16,
      backgroundColor: color,
      child: Text(
        label,
        style: TextStyle(
          color: color.computeLuminance() > 0.5 ? Colors.black : Colors.white,
          fontSize:   10,
          fontWeight: FontWeight.bold,
        ),
      ),
    ),
  );
}