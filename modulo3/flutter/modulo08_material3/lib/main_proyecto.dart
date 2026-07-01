// lib/main.dart
import 'package:flutter/material.dart';
import 'screens/pantalla_ajustes.dart';

void main() => runApp(const AppAjustes());

class AppAjustes extends StatefulWidget {
  const AppAjustes({super.key});
  @override
  State<AppAjustes> createState() => _AppAjustesState();
}

class _AppAjustesState extends State<AppAjustes> {
  ThemeMode _themeMode = ThemeMode.system;
  Color     _seedColor = const Color(0xFF1565C0);

  static const _paletas = [
    (nombre: 'Azul',    color: Color(0xFF1565C0)),
    (nombre: 'Verde',   color: Color(0xFF2E7D32)),
    (nombre: 'Morado',  color: Color(0xFF6A1B9A)),
    (nombre: 'Naranja', color: Color(0xFFE65100)),
    (nombre: 'Rojo',    color: Color(0xFFC62828)),
  ];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title:     'Sistema de Monitoreo',
      themeMode: _themeMode,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
            seedColor: _seedColor, brightness: Brightness.light),
        useMaterial3: true,
      ),
      darkTheme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
            seedColor: _seedColor, brightness: Brightness.dark),
        useMaterial3: true,
      ),
      home: PantallaAjustes(
        themeMode:   _themeMode,
        seedColor:   _seedColor,
        onThemeMode: (mode)  => setState(() => _themeMode = mode),
        onSeedColor: (color) => setState(() => _seedColor = color),
        paletas:     _paletas,
      ),
    );
  }
}