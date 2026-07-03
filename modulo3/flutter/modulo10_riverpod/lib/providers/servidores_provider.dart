// lib/providers/servidores_provider.dart
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../models/servidor_ssh.dart';

// NotifierProvider — estado complejo con métodos propios
class ServidoresNotifier extends Notifier<List<ServidorSSH>> {
  @override
  List<ServidorSSH> build() => [
    ServidorSSH(id:'1', nombre:'prod-web-01', ip:'10.0.2.10', puerto:22,   ssl:true,  favorito:true),
    ServidorSSH(id:'2', nombre:'prod-db-01',  ip:'10.0.2.20', puerto:22,   ssl:true),
    ServidorSSH(id:'3', nombre:'staging-api', ip:'10.0.3.10', puerto:2222, ssl:false),
  ];

  void toggleFavorito(String id) {
    state = state.map((s) =>
        s.id == id
          ? ServidorSSH(id:s.id, nombre:s.nombre, ip:s.ip,
                        puerto:s.puerto, ssl:s.ssl,
                        favorito:!s.favorito)
          : s
    ).toList();
  }

  void eliminar(String id) {
    state = state.where((s) => s.id != id).toList();
  }

  void agregar(ServidorSSH servidor) {
    state = [...state, servidor];
  }
}

final servidoresProvider =
    NotifierProvider<ServidoresNotifier, List<ServidorSSH>>(
  ServidoresNotifier.new,
);