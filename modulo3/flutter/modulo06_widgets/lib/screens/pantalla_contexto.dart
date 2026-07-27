import 'package:flutter/material.dart';

class PantallaContexto extends StatelessWidget {
  const PantallaContexto({super.key});

  @override
  Widget build(BuildContext context) {
    // ── Tema ──────────────────────────────────────────────────────
    final tema = Theme.of(context);
    final colores = tema.colorScheme;

    // ── Pantalla ──────────────────────────────────────────────────
    final tamanio = MediaQuery.sizeOf(context);
    final esMovil = tamanio.width < 600;
    final esRetrato = MediaQuery.orientationOf(context) == Orientation.portrait;
    final paddingTop = MediaQuery.paddingOf(context).top;

    return Scaffold(
      backgroundColor: colores.surface,
      appBar: AppBar(
        backgroundColor: colores.secondaryContainer,
        foregroundColor: colores.onSecondaryContainer,
        title: Text(
          'Pantalla ${esMovil ? "móvil" : "tablet"} · ${esRetrato ? "retrato" : "paisaje"}',
          style: tema.textTheme.titleMedium,
        ),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          // ── Información de pantalla ────────────────────────────
          _Seccion(
            titulo: 'Pantalla',
            items: [
              'Ancho:         ${tamanio.width.toStringAsFixed(0)} px',
              'Alto:          ${tamanio.height.toStringAsFixed(0)} px',
              'Pixel ratio:   ${MediaQuery.devicePixelRatioOf(context).toStringAsFixed(1)}',
              'Padding top:   ${paddingTop.toStringAsFixed(1)} px',
              'Orientación:   ${MediaQuery.orientationOf(context).name}',
            ],
          ),
          const SizedBox(height: 16),

          // ── Colores del tema ───────────────────────────────────
          _Seccion(titulo: 'colorScheme', items: []),
          Wrap(
            spacing: 8,
            runSpacing: 8,
            children: [
              _ChipColor(nombre: 'primary', color: colores.primary),
              _ChipColor(
                nombre: 'primaryContainer',
                color: colores.primaryContainer,
              ),
              _ChipColor(nombre: 'secondary', color: colores.secondary),
              _ChipColor(nombre: 'surface', color: colores.surface),
              _ChipColor(nombre: 'error', color: colores.error),
            ],
          ),
          const SizedBox(height: 16),

          // ── Tipografía del tema ────────────────────────────────
          _Seccion(titulo: 'textTheme', items: []),
          Text('displaySmall', style: tema.textTheme.displaySmall),
          Text('headlineMedium', style: tema.textTheme.headlineMedium),
          Text('titleLarge', style: tema.textTheme.titleLarge),
          Text('bodyLarge', style: tema.textTheme.bodyLarge),
          Text('bodyMedium', style: tema.textTheme.bodyMedium),
          Text('labelSmall', style: tema.textTheme.labelSmall),
          const SizedBox(height: 24),
          FilledButton(
            onPressed: () {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  content: Text('SnackBar desde ScaffoldMessenger.of(context)'),
                ),
              );
            },
            child: const Text('Mostrar SnackBar'),
          ),
        ],
      ),
    );
  }
}

// ── Widgets auxiliares privados — solo usados en este archivo ────────

class _Seccion extends StatelessWidget {
  final String titulo;
  final List<String> items;
  const _Seccion({required this.titulo, required this.items});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          titulo,
          style: Theme.of(context).textTheme.labelLarge?.copyWith(
            color: Theme.of(context).colorScheme.primary,
          ),
        ),
        const Divider(),
        for (final item in items)
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 2),
            child: Text(item, style: Theme.of(context).textTheme.bodyMedium),
          ),
      ],
    );
  }
}

class _ChipColor extends StatelessWidget {
  final String nombre;
  final Color color;
  const _ChipColor({required this.nombre, required this.color});

  @override
  Widget build(BuildContext context) {
    final luminancia = color.computeLuminance();
    final textoColor = luminancia > 0.4 ? Colors.black87 : Colors.white;
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
      decoration: BoxDecoration(
        color: color,
        borderRadius: BorderRadius.circular(8),
      ),
      child: Text(
        nombre,
        style: TextStyle(
          color: textoColor,
          fontSize: 11,
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }
}
