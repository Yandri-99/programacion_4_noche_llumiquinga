// lib/router/app_router_paso3.dart
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../screens/pantalla_inicio.dart';
import '../screens/pantallaservidoresfiltro.dart';
import '../screens/pantalla_detalle.dart';
import '../models/servidor_ssh.dart';

final appRouterPaso3 = GoRouter(
  initialLocation: '/',
  routes: [
    GoRoute(
      path:    '/',
      builder: (context, state) => const PantallaInicio(),
    ),
    GoRoute(
      path:    '/servidores',
      builder: (context, state) {
        // Query parameters — /servidores?soloSSL=true
        final soloSSL = state.uri.queryParameters['soloSSL'] == 'true';
        return PantallaServidoresFiltro(soloSSL: soloSSL);
      },
    ),
    GoRoute(
      path:    '/servidores/:id',
      builder: (context, state) {
        final id       = state.pathParameters['id']!;
        final servidor = state.extra as ServidorSSH?;
        return PantallaDetalle(id: id, servidor: servidor);
      },
    ),
  ],
);