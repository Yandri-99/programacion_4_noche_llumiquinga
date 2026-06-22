// lib/main.dart
import 'package:flutter/material.dart';
import 'widgets/catalogo_basicos.dart';
import 'widgets/etiqueta.dart';
import 'widgets/servicio_estado.dart';
import 'widgets/contador_limitado.dart';
import 'widgets/reloj.dart';
import 'screens/pantalla_contexto.dart';
import 'widgets/indicador.dart';

// ┌──────────────────────────────────────────────────────────────────┐
// │  Cambia este número y guarda (Ctrl+S) para navegar entre pasos. │
// │  1  Paso 1   StatelessWidget mínimo                             │
// │  2  Paso 1b  Widgets básicos — catálogo                        │
// │  3  Paso 2   StatelessWidget con parámetros                     │
// │  4  Paso 3   StatefulWidget / setState / cambio de estatus      │
// │  5  Paso 3b  Parámetros en StatefulWidget                       │
// │  6  Paso 4   Ciclo de vida con Timer                            │
// │  7  Paso 5   BuildContext                                        │
// │  8  Paso 6   Composición de widgets                             │
// └──────────────────────────────────────────────────────────────────┘
const int paso = 7;

void main() => runApp(
  MaterialApp(
    debugShowCheckedModeBanner: false,
    theme: ThemeData.from(
      colorScheme: ColorScheme.fromSeed(
        seedColor:
            Colors.deepPurple, // prueba también Colors.teal o Colors.deepOrange
        brightness: Brightness.dark,
      ),
    ),
    home: switch (paso) {
      1 => const Scaffold(body: Center(child: Saludo())),
      2 => const CatalogoBasicos(),
      3 => const Scaffold(
        body: Center(
          child: Wrap(
            spacing: 12,
            runSpacing: 8,
            children: [
              Etiqueta(texto: 'Activo', color: Colors.green),
              Etiqueta(texto: 'Error', color: Colors.red, relleno: true),
              Etiqueta(texto: 'En espera', color: Colors.orange),
              Etiqueta(
                texto: 'Crítico',
                color: Colors.red,
                fontSize: 16,
                relleno: true,
              ),
              Etiqueta(texto: 'Info', color: Colors.blue, fontSize: 11),
            ],
          ),
        ),
      ),
      4 => const Scaffold(
        body: Center(child: ServicioEstado(nombre: 'nginx-proxy')),
      ),
      5 => Scaffold(
        // Paso 3b
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ContadorLimitado(
                etiqueta: 'Intentos de login',
                limite: 3,
                color: Colors.red,
                onLimite: () => debugPrint('¡Cuenta bloqueada!'),
              ),
              const SizedBox(height: 40),
              ContadorLimitado(
                etiqueta: 'Conexiones activas',
                limite: 10,
                color: Colors.indigo,
              ),
            ],
          ),
        ),
      ),
      6 => Scaffold(
        // Paso 4
        appBar: AppBar(title: const Text('Cronómetro')),
        body: const Center(child: Reloj()),
      ),
      8 => Scaffold(                             // Paso 6
      body: Center(
        child: Wrap(
          spacing:    32,
          runSpacing: 24,
          alignment:  WrapAlignment.center,
          children: const [
            Indicador(label: 'Servidores activos', valor: '8',
                      color: Colors.green, icono: Icons.dns),
            Indicador(label: 'Alertas críticas',   valor: '2',
                      color: Colors.red,   icono: Icons.warning_amber,
                      subtitulo: 'Requieren atención'),
            Indicador(label: 'Tráfico',            valor: '4.2 GB',
                      color: Colors.indigo),
            Indicador(label: 'Uptime',             valor: '99.8%',
                      color: Colors.teal, subtitulo: 'Últimos 30 días'),
          ],
        ),
      ),
    ),
      7 => const PantallaContexto(), // Paso 5 — ya tiene su propio Scaffold
      _ => Scaffold(
        body: Center(child: Text('Paso $paso: crea el widget primero')),
      ),
    },
  ),
  
);

class Saludo extends StatelessWidget {
  const Saludo({super.key});
  @override
  Widget build(BuildContext context) =>
      const Text('Hola Flutter', style: TextStyle(fontSize: 32));
}
