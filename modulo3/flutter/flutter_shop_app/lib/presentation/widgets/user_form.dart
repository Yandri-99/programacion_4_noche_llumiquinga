import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../core/utils/validators.dart';
import '../../domain/model/user.dart';
import '../../theme/app_colors.dart';
import '../providers/users_admin_provider.dart';

Future<void> showUserForm(
  BuildContext context,
  WidgetRef ref, {
  User? initial,
}) {
  ref.read(usersAdminProvider.notifier).resetFormState();
  return showModalBottomSheet(
    context: context,
    isScrollControlled: true,
    backgroundColor: AppColors.surface,
    shape: const RoundedRectangleBorder(
      borderRadius: BorderRadius.vertical(top: Radius.circular(24)),
    ),
    builder: (_) => ProviderScope(
      parent: ProviderScope.containerOf(context),
      child: UserFormSheet(initial: initial),
    ),
  );
}

class UserFormSheet extends ConsumerStatefulWidget {
  final User? initial;
  const UserFormSheet({super.key, this.initial});

  @override
  ConsumerState<UserFormSheet> createState() => _UserFormSheetState();
}

class _UserFormSheetState extends ConsumerState<UserFormSheet> {
  final _formKey = GlobalKey<FormState>();
  final _usernameCtrl = TextEditingController();
  final _emailCtrl = TextEditingController();
  final _firstNameCtrl = TextEditingController();
  final _lastNameCtrl = TextEditingController();
  final _passwordCtrl = TextEditingController();
  bool _isStaff = false;
  bool _isActive = true;

  @override
  void initState() {
    super.initState();
    if (widget.initial != null) {
      final u = widget.initial!;
      _usernameCtrl.text = u.username;
      _emailCtrl.text = u.email;
      _firstNameCtrl.text = u.firstName;
      _lastNameCtrl.text = u.lastName;
      _isStaff = u.isStaff;
      _isActive = u.isActive;
    }
  }

  @override
  void dispose() {
    _usernameCtrl.dispose();
    _emailCtrl.dispose();
    _firstNameCtrl.dispose();
    _lastNameCtrl.dispose();
    _passwordCtrl.dispose();
    super.dispose();
  }

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) return;

    final payload = {
      'username': _usernameCtrl.text.trim(),
      'email': _emailCtrl.text.trim(),
      'first_name': _firstNameCtrl.text.trim(),
      'last_name': _lastNameCtrl.text.trim(),
      'is_staff': _isStaff,
      'is_active': _isActive,
      if (widget.initial == null) 'password': _passwordCtrl.text.trim(),
    };

    if (widget.initial != null) {
      await ref
          .read(usersAdminProvider.notifier)
          .updateUser(widget.initial!.id, payload);
    } else {
      await ref.read(usersAdminProvider.notifier).createUser(payload);
    }
  }

  @override
  Widget build(BuildContext context) {
    final formState = ref.watch(usersAdminProvider.select((s) => s.formState));
    final isSaving = formState is UserFormSaving;
    final isEdit = widget.initial != null;

    if (formState is UserFormSuccess) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (mounted) Navigator.pop(context);
      });
    }

    return Padding(
      padding:
          EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
      child: SingleChildScrollView(
        padding: const EdgeInsets.fromLTRB(24, 8, 24, 32),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            Center(
              child: Container(
                width: 40,
                height: 4,
                margin: const EdgeInsets.symmetric(vertical: 12),
                decoration: BoxDecoration(
                  color: AppColors.border,
                  borderRadius: BorderRadius.circular(2),
                ),
              ),
            ),
            Text(
              isEdit ? 'Editar usuario' : 'Nuevo usuario',
              style: const TextStyle(
                color: AppColors.textPrimary,
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 18),
            if (formState is UserFormError) ...[
              Container(
                width: double.infinity,
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: AppColors.error.withValues(alpha: 0.12),
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Text(
                  formState.message,
                  style: const TextStyle(color: AppColors.error, fontSize: 13),
                ),
              ),
              const SizedBox(height: 14),
            ],
            Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    controller: _usernameCtrl,
                    enabled: !isSaving,
                    decoration: const InputDecoration(labelText: 'Usuario *'),
                    style: const TextStyle(color: AppColors.textPrimary),
                    validator: (v) => validateRequired(v, 'Usuario'),
                  ),
                  const SizedBox(height: 12),
                  TextFormField(
                    controller: _emailCtrl,
                    enabled: !isSaving,
                    keyboardType: TextInputType.emailAddress,
                    decoration: const InputDecoration(labelText: 'Email *'),
                    style: const TextStyle(color: AppColors.textPrimary),
                    validator: (v) => validateEmail(v),
                  ),
                  const SizedBox(height: 12),
                  TextFormField(
                    controller: _firstNameCtrl,
                    enabled: !isSaving,
                    decoration: const InputDecoration(labelText: 'Nombre *'),
                    style: const TextStyle(color: AppColors.textPrimary),
                    validator: (v) => validateRequired(v, 'Nombre'),
                  ),
                  const SizedBox(height: 12),
                  TextFormField(
                    controller: _lastNameCtrl,
                    enabled: !isSaving,
                    decoration: const InputDecoration(labelText: 'Apellido *'),
                    style: const TextStyle(color: AppColors.textPrimary),
                    validator: (v) => validateRequired(v, 'Apellido'),
                  ),
                  const SizedBox(height: 12),
                  if (!isEdit)
                    TextFormField(
                      controller: _passwordCtrl,
                      enabled: !isSaving,
                      obscureText: true,
                      decoration:
                          const InputDecoration(labelText: 'Contraseña *'),
                      style: const TextStyle(color: AppColors.textPrimary),
                      validator: (v) => validatePassword(v),
                    ),
                  if (!isEdit) const SizedBox(height: 12),
                  Container(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 12, vertical: 10),
                    decoration: BoxDecoration(
                      color: AppColors.surface2,
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Column(
                      children: [
                        SwitchListTile.adaptive(
                          value: _isStaff,
                          onChanged: isSaving
                              ? null
                              : (v) => setState(() => _isStaff = v),
                          title: const Text('Es staff',
                              style: TextStyle(color: AppColors.textPrimary)),
                          subtitle: const Text(
                              'Puede acceder al panel de admin',
                              style: TextStyle(color: AppColors.textSecondary)),
                          activeColor: AppColors.accent,
                        ),
                        SwitchListTile.adaptive(
                          value: _isActive,
                          onChanged: isSaving
                              ? null
                              : (v) => setState(() => _isActive = v),
                          title: const Text('Cuenta activa',
                              style: TextStyle(color: AppColors.textPrimary)),
                          subtitle: const Text('Permite iniciar sesión',
                              style: TextStyle(color: AppColors.textSecondary)),
                          activeColor: AppColors.success,
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 18),
                  Row(
                    children: [
                      Expanded(
                        child: OutlinedButton(
                          onPressed:
                              isSaving ? null : () => Navigator.pop(context),
                          child: const Text('Cancelar'),
                        ),
                      ),
                      const SizedBox(width: 12),
                      Expanded(
                        child: ElevatedButton.icon(
                          onPressed: isSaving ? null : _submit,
                          icon: isSaving
                              ? const SizedBox(
                                  width: 16,
                                  height: 16,
                                  child: CircularProgressIndicator(
                                      strokeWidth: 2,
                                      color: AppColors.onAccent))
                              : const Icon(Icons.save_rounded, size: 18),
                          label: Text(isSaving
                              ? 'Guardando...'
                              : (isEdit ? 'Guardar' : 'Crear')),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
