import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../domain/model/auth_models.dart';
import '../../domain/model/auth_state.dart';

class AuthNotifier extends StateNotifier<AuthState> {
  AuthNotifier() : super(const AuthState.checking());

  Future<void> login(String username, String password) async {
    state = const AuthState.checking();
    await Future.delayed(const Duration(milliseconds: 300));

    if (username.trim().isEmpty || password.trim().isEmpty) {
      state =
          const AuthState.unauthenticated('Usuario y contraseña obligatorios');
      return;
    }

    state = AuthState.authenticated(
      const LoggedUser(
        id: 1,
        username: 'demo',
        email: 'demo@example.com',
        isStaff: false,
      ),
    );
  }

  Future<void> register(
    String username,
    String email,
    String password,
    String confirmPassword,
  ) async {
    state = const AuthState.checking();
    await Future.delayed(const Duration(milliseconds: 300));

    if (password != confirmPassword) {
      state = const AuthState.unauthenticated('Las contraseñas no coinciden');
      return;
    }

    state = AuthState.authenticated(
      LoggedUser(
        id: 2,
        username: username.trim(),
        email: email.trim(),
        isStaff: false,
      ),
    );
  }

  Future<void> logout() async {
    state = const AuthState.unauthenticated();
  }

  void clearError() {
    if (state.status == AuthStatus.unauthenticated) {
      state = const AuthState.unauthenticated();
    }
  }
}

final authProvider = StateNotifierProvider<AuthNotifier, AuthState>((ref) {
  return AuthNotifier();
});
