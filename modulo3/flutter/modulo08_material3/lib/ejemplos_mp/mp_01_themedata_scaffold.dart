import 'package:flutter/material.dart';

/// mp_ejercicio1 - ThemeData + Scaffold: App de Transporte con Tema Personalizado
class AppTransporteTheme extends StatelessWidget {
  const AppTransporteTheme({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TransporteApp',
      theme: ThemeData(
        colorSchemeSeed: Colors.indigo,
        useMaterial3: true,
        brightness: Brightness.light,
      ),
      darkTheme: ThemeData(
        colorSchemeSeed: Colors.indigo,
        useMaterial3: true,
        brightness: Brightness.dark,
      ),
      themeMode: ThemeMode.system,
      home: const PantallaTransporte(),
    );
  }
}

class PantallaTransporte extends StatelessWidget {
  const PantallaTransporte({super.key});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return Scaffold(
      appBar: AppBar(
        title: const Text('TransporteApp'),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Bienvenido', style: theme.textTheme.headlineMedium?.copyWith(fontWeight: FontWeight.bold)),
            const SizedBox(height: 4),
            Text('Gestión de Transporte Público', style: theme.textTheme.bodyLarge),
            const SizedBox(height: 24),
            Card(
              child: ListTile(
                leading: const CircleAvatar(child: Icon(Icons.directions_bus)),
                title: const Text('Mi Ruta'),
                subtitle: const Text('Quito → Guayaquil • Salida 08:00'),
                trailing: const Icon(Icons.chevron_right),
                onTap: () {},
              ),
            ),
            const SizedBox(height: 8),
            Card(
              child: ListTile(
                leading: const CircleAvatar(child: Icon(Icons.map)),
                title: const Text('Mapa en Vivo'),
                subtitle: const Text('12 vehículos activos'),
                trailing: const Icon(Icons.chevron_right),
                onTap: () {},
              ),
            ),
            const SizedBox(height: 8),
            Card(
              child: ListTile(
                leading: const CircleAvatar(child: Icon(Icons.notifications)),
                title: const Text('Alertas'),
                subtitle: const Text('2 alertas pendientes'),
                trailing: const Badge(label: Text('2'), child: const Icon(Icons.chevron_right)),
                onTap: () {},
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: NavigationBar(
        destinations: const [
          NavigationDestination(icon: Icon(Icons.home), label: 'Inicio'),
          NavigationDestination(icon: Icon(Icons.route), label: 'Rutas'),
          NavigationDestination(icon: Icon(Icons.map), label: 'Mapa'),
          NavigationDestination(icon: Icon(Icons.person), label: 'Perfil'),
        ],
      ),
    );
  }
}

void main() => runApp(const AppTransporteTheme());
