import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:http/http.dart' as http;

class PantallaPaso1 extends StatelessWidget {
  const PantallaPaso1({super.key});

  Future<Map<String, dynamic>> _fetchTodo() async {
    final res = await http.get(
      Uri.parse('https://jsonplaceholder.typicode.com/todos/1'),
    );
    return jsonDecode(res.body) as Map<String, dynamic>;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Paso 1 · FutureBuilder crudo'),
        leading: BackButton(onPressed: () => context.go('/')),
      ),
      body: Center(
        child: FutureBuilder<Map<String, dynamic>>(
          future: _fetchTodo(),
          builder: (context, snap) {
            // Estado 1: esperando respuesta
            if (snap.connectionState != ConnectionState.done) {
              return const CircularProgressIndicator();
            }
            // Estado 2: error de red
            if (snap.hasError) {
              return Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(Icons.wifi_off, size: 48, color: Colors.red),
                  const SizedBox(height: 8),
                  Text('Error: ${snap.error}',
                      style: const TextStyle(color: Colors.red)),
                ],
              );
            }
            // Estado 3: datos disponibles
            final todo = snap.data!;
            return Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(Icons.task_alt, size: 48, color: Colors.green),
                  const SizedBox(height: 16),
                  Text('ID: ${todo['id']}',
                      style: const TextStyle(fontSize: 14, color: Colors.grey)),
                  const SizedBox(height: 8),
                  Text(
                    todo['title'] as String,
                    style: const TextStyle(
                        fontSize: 20, fontWeight: FontWeight.bold),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 12),
                  Chip(
                    label: Text(
                      (todo['completed'] as bool) ? 'Completada ✓' : 'Pendiente',
                    ),
                    backgroundColor: (todo['completed'] as bool)
                        ? Colors.green[100]
                        : Colors.orange[100],
                  ),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}