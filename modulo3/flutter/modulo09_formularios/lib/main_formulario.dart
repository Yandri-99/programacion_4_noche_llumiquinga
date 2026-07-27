// lib/main.dart
import 'package:flutter/material.dart';

void main() => runApp(const AppGestorSSH());

class AppGestorSSH extends StatelessWidget {
  const AppGestorSSH({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF1B5E20), // verde oscuro
        ),
        useMaterial3: true,
      ),
      home: const _ConexionSSH(),
    );
  }
}

class _ConexionSSH extends StatefulWidget {
  const _ConexionSSH();
  @override
  State<_ConexionSSH> createState() => _ConexionSSHState();
}

class _ConexionSSHState extends State<_ConexionSSH> {
  // Controller da acceso al texto, posición y selección del campo
  final _ctrlHostname = TextEditingController();
  final _ctrlIp       = TextEditingController();
  final _ctrlPuerto   = TextEditingController(text: '22'); // valor inicial

  final _focusIp     = FocusNode();
  final _focusPuerto = FocusNode();

  @override
  void dispose() {
    // SIEMPRE liberar controllers y FocusNodes en dispose
    _ctrlHostname.dispose();
    _ctrlIp.dispose();
    _ctrlPuerto.dispose();
    _focusIp.dispose();
    _focusPuerto.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final cs = Theme.of(context).colorScheme;

    return Scaffold(
      appBar: AppBar(
        title:           const Text('Conexión SSH'),
        backgroundColor: cs.primaryContainer,
        foregroundColor: cs.onPrimaryContainer,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(
              controller:      _ctrlHostname,
              decoration:      const InputDecoration(
                labelText:  'Hostname',
                hintText:   'prod-web-01',
                prefixIcon: Icon(Icons.dns),
                border:     OutlineInputBorder(),
              ),
              textInputAction: TextInputAction.next,              // tecla "Siguiente"
              onSubmitted:     (_) => _focusIp.requestFocus(),   // salta a IP
            ),
            const SizedBox(height: 12),
            TextField(
              controller:      _ctrlIp,
              focusNode:       _focusIp,
              decoration:      const InputDecoration(
                labelText:  'Dirección IP',
                hintText:   '192.168.1.100',
                prefixIcon: Icon(Icons.router),
                border:     OutlineInputBorder(),
              ),
              keyboardType:    TextInputType.number,
              textInputAction: TextInputAction.next,
              onSubmitted:     (_) => _focusPuerto.requestFocus(),
            ),
            const SizedBox(height: 12),
            TextField(
              controller:      _ctrlPuerto,
              focusNode:       _focusPuerto,
              decoration:      const InputDecoration(
                labelText:  'Puerto SSH',
                prefixIcon: Icon(Icons.lock_outline),
                border:     OutlineInputBorder(),
              ),
              keyboardType:    TextInputType.number,
              textInputAction: TextInputAction.done,
              onSubmitted:     (_) => FocusScope.of(context).unfocus(),
            ),
            const SizedBox(height: 20),
            FilledButton.icon(
              onPressed: () {
                FocusScope.of(context).unfocus();
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Text(
                      'Conectando a ${_ctrlHostname.text} '
                      '(${_ctrlIp.text}:${_ctrlPuerto.text})',
                    ),
                    behavior: SnackBarBehavior.floating,
                  ),
                );
              },
              icon:  const Icon(Icons.terminal),
              label: const Text('Conectar'),
            ),
            const SizedBox(height: 8),
            OutlinedButton(
              onPressed: () {
                _ctrlHostname.clear();
                _ctrlIp.clear();
                _ctrlPuerto.text = '22'; // restablecer valor por defecto
              },
              child: const Text('Limpiar campos'),
            ),
          ],
        ),
      ),
    );
  }
}