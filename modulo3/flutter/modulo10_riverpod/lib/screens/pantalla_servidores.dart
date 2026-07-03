// lib/screens/pantalla_servidores.dart
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../models/servidor_ssh.dart';
import '../providers/servidores_provider.dart';

class PantallaServidores extends ConsumerWidget {
  const PantallaServidores({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final servidores = ref.watch(servidoresProvider);
    final cs = Theme.of(context).colorScheme;

    return Scaffold(
      appBar: AppBar(
        title: Text('Servidores (${servidores.length})'),
        backgroundColor: cs.primaryContainer,
        foregroundColor: cs.onPrimaryContainer,
      ),
      body: servidores.isEmpty
          ? const Center(child: Text('Sin servidores'))
          : ListView.separated(
              itemCount: servidores.length,
              separatorBuilder: (context, index) =>
                  const Divider(height: 1, indent: 72),
              itemBuilder: (context, i) {
                final s = servidores[i];
                return ListTile(
                  leading: CircleAvatar(
                    backgroundColor: s.ssl
                        ? Colors.green.shade50
                        : Colors.grey.shade100,
                    child: Icon(
                      Icons.dns,
                      color: s.ssl ? Colors.green : Colors.grey,
                    ),
                  ),
                  title: Text(
                    s.nombre,
                    style: const TextStyle(fontWeight: FontWeight.w600),
                  ),
                  subtitle: Text('${s.ip}:${s.puerto}'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: Icon(
                          s.favorito ? Icons.star : Icons.star_border,
                          color: s.favorito ? Colors.amber : null,
                        ),
                        onPressed: () => ref
                            .read(servidoresProvider.notifier)
                            .toggleFavorito(s.id),
                      ),
                      IconButton(
                        icon: const Icon(
                          Icons.delete_outline,
                          color: Colors.red,
                        ),
                        onPressed: () => ref
                            .read(servidoresProvider.notifier)
                            .eliminar(s.id),
                      ),
                    ],
                  ),
                );
              },
            ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          final id = DateTime.now().millisecondsSinceEpoch.toString();
          ref
              .read(servidoresProvider.notifier)
              .agregar(
                ServidorSSH(
                  id: id,
                  nombre: 'nuevo-srv-$id',
                  ip: '192.168.0.${servidores.length + 1}',
                  puerto: 22,
                  ssl: true,
                ),
              );
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}
