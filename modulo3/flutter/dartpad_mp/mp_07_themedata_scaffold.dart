import 'package:flutter/material.dart';

/// [DartPad] mp_07_themedata_scaffold.dart
/// ThemeData + Scaffold: Tema de la App de Transporte

void main() => runApp(MaterialApp(
  title: 'TransportApp Theme',
  theme: ThemeData(
    colorScheme: ColorScheme.fromSeed(
      seedMaterial: Colors.deepPurple,
      brightness: Brightness.light,
    ),
    useMaterial3: true,
    cardTheme: CardThemeData(elevation: 3, shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12))),
    appBarTheme: const AppBarTheme(centerTitle: true, elevation: 0),
  ),
  home: Scaffold(
    appBar: AppBar(title: const Text('TransportApp - Material 3')),
    body: ListView(
      padding: const EdgeInsets.all(16),
      children: [
        Card(
          child: ListTile(
            leading: CircleAvatar(child: Icon(Icons.directions_bus)),
            title: const Text('Ruta Principal', style: TextStyle(fontWeight: FontWeight.bold)),
            subtitle: const Text('Quito - Guayaquil'),
            trailing: const Icon(Icons.chevron_right),
          ),
        ),
        Card(
          child: ListTile(
            leading: CircleAvatar(backgroundColor: Colors.green, child: const Icon(Icons.check, color: Colors.white)),
            title: const Text('Ruta Secundaria', style: TextStyle(fontWeight: FontWeight.bold)),
            subtitle: const Text('Cuenca - Loja'),
            trailing: const Icon(Icons.chevron_right),
          ),
        ),
        Card(
          child: ListTile(
            leading: CircleAvatar(backgroundColor: Colors.orange, child: const Icon(Icons.schedule, color: Colors.white)),
            title: const Text('Ruta Nocturna', style: TextStyle(fontWeight: FontWeight.bold)),
            subtitle: const Text('Ambato - Riobamba'),
            trailing: const Icon(Icons.chevron_right),
          ),
        ),
        const SizedBox(height: 20),
        ElevatedButton.icon(
          onPressed: () {},
          icon: const Icon(Icons.add),
          label: const Text('Nueva Ruta'),
        ),
        const SizedBox(height: 8),
        OutlinedButton.icon(
          onPressed: () {},
          icon: const Icon(Icons.filter_list),
          label: const Text('Filtrar Rutas'),
        ),
      ],
    ),
  ),
));
