import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../theme/app_colors.dart';
import '../../providers/users_admin_provider.dart';
import '../../widgets/user_form.dart';

class UsersAdminScreen extends ConsumerStatefulWidget {
  const UsersAdminScreen({super.key});

  @override
  ConsumerState<UsersAdminScreen> createState() => _UsersAdminScreenState();
}

class _UsersAdminScreenState extends ConsumerState<UsersAdminScreen> {
  @override
  Widget build(BuildContext context) {
    final state = ref.watch(usersAdminProvider);
    final filtered = state.filtered;

    return Column(
      children: [
        Container(
          color: AppColors.surface,
          padding: const EdgeInsets.fromLTRB(16, 16, 16, 0),
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text('Usuarios',
                          style: TextStyle(
                              color: AppColors.textPrimary,
                              fontSize: 22,
                              fontWeight: FontWeight.bold)),
                      Text('${state.stats['total'] ?? 0} usuarios',
                          style: const TextStyle(
                              color: AppColors.textSecondary, fontSize: 13)),
                    ],
                  ),
                  ElevatedButton.icon(
                    onPressed: () => showUserForm(context, ref),
                    icon: const Icon(Icons.person_add_alt_1_rounded, size: 18),
                    label: const Text('Nuevo'),
                    style: ElevatedButton.styleFrom(
                        minimumSize: const Size(0, 40),
                        padding: const EdgeInsets.symmetric(horizontal: 16)),
                  ),
                ],
              ),
              const SizedBox(height: 12),
              TextField(
                onChanged: ref.read(usersAdminProvider.notifier).setSearch,
                decoration: const InputDecoration(
                  hintText: 'Buscar usuario...',
                  prefixIcon: Icon(Icons.search_rounded,
                      color: AppColors.textSecondary),
                  contentPadding: EdgeInsets.symmetric(vertical: 10),
                ),
                style: const TextStyle(color: AppColors.textPrimary),
              ),
              const SizedBox(height: 10),
              SizedBox(
                height: 34,
                child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(right: 8),
                      child: ChoiceChip(
                        label: const Text('Todos'),
                        selected: state.roleFilter == UserRoleFilter.all,
                        onSelected: (_) => ref
                            .read(usersAdminProvider.notifier)
                            .setRoleFilter(UserRoleFilter.all),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(right: 8),
                      child: ChoiceChip(
                        label: const Text('Staff'),
                        selected: state.roleFilter == UserRoleFilter.staff,
                        onSelected: (_) => ref
                            .read(usersAdminProvider.notifier)
                            .setRoleFilter(UserRoleFilter.staff),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(right: 8),
                      child: ChoiceChip(
                        label: const Text('Clientes'),
                        selected: state.roleFilter == UserRoleFilter.clients,
                        onSelected: (_) => ref
                            .read(usersAdminProvider.notifier)
                            .setRoleFilter(UserRoleFilter.clients),
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 10),
              SizedBox(
                height: 34,
                child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(right: 8),
                      child: ChoiceChip(
                        label: const Text('Todos'),
                        selected: state.statusFilter == UserStatusFilter.all,
                        onSelected: (_) => ref
                            .read(usersAdminProvider.notifier)
                            .setStatusFilter(UserStatusFilter.all),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(right: 8),
                      child: ChoiceChip(
                        label: const Text('Activos'),
                        selected: state.statusFilter == UserStatusFilter.active,
                        onSelected: (_) => ref
                            .read(usersAdminProvider.notifier)
                            .setStatusFilter(UserStatusFilter.active),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(right: 8),
                      child: ChoiceChip(
                        label: const Text('Inactivos'),
                        selected:
                            state.statusFilter == UserStatusFilter.inactive,
                        onSelected: (_) => ref
                            .read(usersAdminProvider.notifier)
                            .setStatusFilter(UserStatusFilter.inactive),
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 12),
            ],
          ),
        ),
        Expanded(
          child: Builder(builder: (_) {
            if (state.isLoading) {
              return const Center(
                  child: CircularProgressIndicator(color: AppColors.accent));
            }
            if (state.error != null) {
              return Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(state.error!,
                        style: const TextStyle(color: AppColors.error)),
                    const SizedBox(height: 12),
                    ElevatedButton(
                        onPressed: () =>
                            ref.read(usersAdminProvider.notifier).load(),
                        child: const Text('Reintentar')),
                  ],
                ),
              );
            }
            if (filtered.isEmpty) {
              return const Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text('👤', style: TextStyle(fontSize: 48)),
                    SizedBox(height: 12),
                    Text('Sin usuarios',
                        style: TextStyle(
                            color: AppColors.textPrimary,
                            fontSize: 18,
                            fontWeight: FontWeight.bold)),
                  ],
                ),
              );
            }
            return ListView.separated(
              padding: const EdgeInsets.all(16),
              itemCount: filtered.length,
              separatorBuilder: (_, __) => const SizedBox(height: 10),
              itemBuilder: (_, index) {
                final user = filtered[index];
                return _UserCard(
                  user: user,
                  onToggleActive: () => ref
                      .read(usersAdminProvider.notifier)
                      .toggleActive(user.id),
                  onToggleStaff: () => ref
                      .read(usersAdminProvider.notifier)
                      .toggleStaff(user.id),
                  onEdit: () => showUserForm(context, ref, initial: user),
                  onDelete: () => _confirmDelete(context, ref, user),
                );
              },
            );
          }),
        ),
      ],
    );
  }

  Future<void> _confirmDelete(
      BuildContext context, WidgetRef ref, dynamic user) async {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        backgroundColor: AppColors.surface,
        title: const Text('¿Eliminar usuario?',
            style: TextStyle(color: AppColors.textPrimary)),
        content: Text('"${user.username}" se eliminará permanentemente.',
            style: const TextStyle(color: AppColors.textSecondary)),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancelar')),
          TextButton(
            onPressed: () {
              Navigator.pop(context);
              ref.read(usersAdminProvider.notifier).deleteUser(user.id);
            },
            child: const Text('Eliminar',
                style: TextStyle(
                    color: AppColors.error, fontWeight: FontWeight.bold)),
          ),
        ],
      ),
    );
  }
}

class _UserCard extends StatelessWidget {
  final dynamic user;
  final VoidCallback onToggleActive;
  final VoidCallback onToggleStaff;
  final VoidCallback onEdit;
  final VoidCallback onDelete;

  const _UserCard(
      {required this.user,
      required this.onToggleActive,
      required this.onToggleStaff,
      required this.onEdit,
      required this.onDelete});

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: user.isActive ? 1.0 : 0.55,
      child: Container(
        padding: const EdgeInsets.all(12),
        decoration: BoxDecoration(
            color: AppColors.surface,
            borderRadius: BorderRadius.circular(14),
            border: Border.all(color: AppColors.border)),
        child: Row(
          children: [
            CircleAvatar(
              radius: 24,
              backgroundColor: user.isStaff
                  ? AppColors.accent.withValues(alpha: 0.2)
                  : AppColors.surface2,
              child: Text(user.username.substring(0, 1).toUpperCase(),
                  style: const TextStyle(
                      color: AppColors.textPrimary,
                      fontWeight: FontWeight.bold)),
            ),
            const SizedBox(width: 12),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('${user.firstName} ${user.lastName}',
                      style: const TextStyle(
                          color: AppColors.textPrimary,
                          fontWeight: FontWeight.bold)),
                  const SizedBox(height: 2),
                  Text('@${user.username}',
                      style: const TextStyle(
                          color: AppColors.textSecondary, fontSize: 13)),
                  const SizedBox(height: 2),
                  Text(user.email,
                      style: const TextStyle(
                          color: AppColors.textSecondary, fontSize: 12)),
                  const SizedBox(height: 6),
                  Wrap(
                    spacing: 6,
                    children: [
                      Chip(
                          label: Text(user.isStaff ? 'Staff' : 'Cliente'),
                          visualDensity: VisualDensity.compact,
                          padding: EdgeInsets.zero),
                      Chip(
                          label: Text(user.isActive ? 'Activo' : 'Inactivo'),
                          visualDensity: VisualDensity.compact,
                          padding: EdgeInsets.zero),
                    ],
                  ),
                ],
              ),
            ),
            PopupMenuButton<String>(
              onSelected: (value) {
                if (value == 'toggle_active') onToggleActive();
                if (value == 'toggle_staff') onToggleStaff();
                if (value == 'edit') onEdit();
                if (value == 'delete') onDelete();
              },
              itemBuilder: (_) => [
                PopupMenuItem(
                    value: 'toggle_active',
                    child: Text(user.isActive ? 'Desactivar' : 'Activar')),
                PopupMenuItem(
                    value: 'toggle_staff',
                    child: Text(user.isStaff ? 'Quitar staff' : 'Hacer staff')),
                const PopupMenuItem(value: 'edit', child: Text('Editar')),
                const PopupMenuItem(value: 'delete', child: Text('Eliminar')),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
