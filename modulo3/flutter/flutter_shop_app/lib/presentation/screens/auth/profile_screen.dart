// lib/presentation/screens/auth/profile_screen.dart

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../theme/app_colors.dart';
import '../../providers/auth_provider.dart';

class ProfileScreen extends ConsumerWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final user = ref.watch(authProvider).user;
    final tt   = Theme.of(context).textTheme;

    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Column(
            children: [
              const SizedBox(height: 24),

              // Avatar
              Container(
                width:  80, height: 80,
                decoration: BoxDecoration(
                  gradient: const LinearGradient(
                    colors: [AppColors.accent, AppColors.accentLight],
                    begin:  Alignment.topLeft,
                    end:    Alignment.bottomRight,
                  ),
                  shape: BoxShape.circle,
                ),
                child: Center(
                  child: Text(
                    (user?.username.isNotEmpty == true)
                        ? user!.username[0].toUpperCase()
                        : '?',
                    style: const TextStyle(
                      color:      AppColors.onAccent,
                      fontSize:   34,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 16),
              Text(user?.username ?? '\u2014', style: tt.headlineMedium),
              Text(user?.email    ?? '\u2014', style: tt.bodyMedium),
              const SizedBox(height: 8),
              if (user?.isStaff == true)
                Container(
                  padding:    const EdgeInsets.symmetric(horizontal: 14, vertical: 4),
                  decoration: BoxDecoration(
                    color:        AppColors.accent.withValues(alpha: 0.15),
                    borderRadius: BorderRadius.circular(999),
                  ),
                  child: const Text(
                    'Staff',
                    style: TextStyle(
                      color:         AppColors.accent,
                      fontSize:      12,
                      fontWeight:    FontWeight.bold,
                      letterSpacing: 0.8,
                    ),
                  ),
                ),
              const SizedBox(height: 32),

              // Info
              Container(
                width:   double.infinity,
                padding: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  color:        AppColors.surface,
                  borderRadius: BorderRadius.circular(16),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'INFORMACI\u00D3N DE LA CUENTA',
                      style: TextStyle(
                        color:         AppColors.textSecondary,
                        fontSize:      11,
                        fontWeight:    FontWeight.bold,
                        letterSpacing: 0.8,
                      ),
                    ),
                    const SizedBox(height: 16),
                    ...[
                      ('ID de usuario', user?.id.toString() ?? '\u2014'),
                      ('Usuario',       user?.username      ?? '\u2014'),
                      ('Email',         user?.email         ?? '\u2014'),
                      ('Rol',           user?.isStaff == true ? 'Administrador' : 'Cliente'),
                    ].asMap().entries.map((entry) {
                      final isLast = entry.key == 3;
                      return Column(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 10),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Text(entry.value.$1,
                                    style: const TextStyle(color: AppColors.textSecondary)),
                                Text(
                                  entry.value.$2,
                                  style: const TextStyle(
                                    color: AppColors.textPrimary, fontWeight: FontWeight.w600,
                                  ),
                                ),
                              ],
                            ),
                          ),
                          if (!isLast) const Divider(height: 1),
                        ],
                      );
                    }),
                  ],
                ),
              ),
              const SizedBox(height: 24),

              // Boton logout
              _LogoutButton(
                onConfirm: () async {
                  await ref.read(authProvider.notifier).logout();
                  if (context.mounted) context.go('/login');
                },
              ),
              const SizedBox(height: 32),
            ],
          ),
        ),
      ),
    );
  }
}

class _LogoutButton extends StatelessWidget {
  final Future<void> Function() onConfirm;
  const _LogoutButton({required this.onConfirm});

  @override
  Widget build(BuildContext context) => SizedBox(
    width:  double.infinity,
    height: 52,
    child:  OutlinedButton.icon(
      onPressed: () => showDialog(
        context: context,
        builder: (_) => AlertDialog(
          backgroundColor: AppColors.surface,
          shape:           RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
          title:           const Text('\u00BFCerrar sesi\u00F3n?',
              style: TextStyle(color: AppColors.textPrimary)),
          content:         const Text(
            'Tu sesi\u00F3n se cerrar\u00E1 en este dispositivo.',
            style: TextStyle(color: AppColors.textSecondary),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child:     const Text('Cancelar'),
            ),
            TextButton(
              onPressed: () async {
                Navigator.pop(context);
                await onConfirm();
              },
              child: const Text(
                'Cerrar sesi\u00F3n',
                style: TextStyle(color: AppColors.error, fontWeight: FontWeight.bold),
              ),
            ),
          ],
        ),
      ),
      icon:  const Icon(Icons.logout, color: AppColors.error),
      label: const Text('Cerrar sesi\u00F3n'),
      style: OutlinedButton.styleFrom(
        foregroundColor: AppColors.error,
        side:            BorderSide(color: AppColors.error.withValues(alpha: 0.5)),
      ),
    ),
  );
}
