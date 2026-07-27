import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../data/remote/api/userremotedatasource.dart';
import '../../domain/model/user.dart';

enum UserRoleFilter { all, staff, clients }

enum UserStatusFilter { all, active, inactive }

extension UserRoleFilterLabel on UserRoleFilter {
  String get label => switch (this) {
        UserRoleFilter.all => 'Todos',
        UserRoleFilter.staff => 'Staff',
        UserRoleFilter.clients => 'Clientes',
      };
}

extension UserStatusFilterLabel on UserStatusFilter {
  String get label => switch (this) {
        UserStatusFilter.all => 'Todos',
        UserStatusFilter.active => 'Activos',
        UserStatusFilter.inactive => 'Inactivos',
      };
}

class UsersAdminState {
  final List<User> users;
  final bool isLoading;
  final String? error;
  final String search;
  final UserRoleFilter roleFilter;
  final UserStatusFilter statusFilter;
  final UserFormState formState;
  final Map<String, int> stats;

  const UsersAdminState({
    this.users = const [],
    this.isLoading = false,
    this.error,
    this.search = '',
    this.roleFilter = UserRoleFilter.all,
    this.statusFilter = UserStatusFilter.all,
    this.formState = const UserFormIdle(),
    this.stats = const {'total': 0, 'active': 0, 'staff': 0},
  });

  List<User> get filtered => users.where((u) {
        final query = search.trim().toLowerCase();
        final matchesSearch = query.isEmpty ||
            u.username.toLowerCase().contains(query) ||
            u.email.toLowerCase().contains(query) ||
            '${u.firstName} ${u.lastName}'.toLowerCase().contains(query);
        final matchesRole = switch (roleFilter) {
          UserRoleFilter.all => true,
          UserRoleFilter.staff => u.isStaff,
          UserRoleFilter.clients => !u.isStaff,
        };
        final matchesStatus = switch (statusFilter) {
          UserStatusFilter.all => true,
          UserStatusFilter.active => u.isActive,
          UserStatusFilter.inactive => !u.isActive,
        };
        return matchesSearch && matchesRole && matchesStatus;
      }).toList();

  UsersAdminState copyWith({
    List<User>? users,
    bool? isLoading,
    String? error,
    String? search,
    UserRoleFilter? roleFilter,
    UserStatusFilter? statusFilter,
    UserFormState? formState,
    Map<String, int>? stats,
  }) =>
      UsersAdminState(
        users: users ?? this.users,
        isLoading: isLoading ?? this.isLoading,
        error: error,
        search: search ?? this.search,
        roleFilter: roleFilter ?? this.roleFilter,
        statusFilter: statusFilter ?? this.statusFilter,
        formState: formState ?? this.formState,
        stats: stats ?? this.stats,
      );
}

sealed class UserFormState {
  const UserFormState();
}

class UserFormIdle extends UserFormState {
  const UserFormIdle();
}

class UserFormSaving extends UserFormState {
  const UserFormSaving();
}

class UserFormSuccess extends UserFormState {
  final String message;
  const UserFormSuccess(this.message);
}

class UserFormError extends UserFormState {
  final String message;
  const UserFormError(this.message);
}

class UsersAdminNotifier extends StateNotifier<UsersAdminState> {
  final UserRemoteDatasource _datasource;

  UsersAdminNotifier(this._datasource) : super(const UsersAdminState()) {
    load();
  }

  Future<void> load() async {
    state = state.copyWith(isLoading: true, error: null);
    try {
      final usersResult = await _datasource.getUsers(
        search: state.search.isEmpty ? null : state.search,
        isStaff: switch (state.roleFilter) {
          UserRoleFilter.all => null,
          UserRoleFilter.staff => true,
          UserRoleFilter.clients => false,
        },
        isActive: switch (state.statusFilter) {
          UserStatusFilter.all => null,
          UserStatusFilter.active => true,
          UserStatusFilter.inactive => false,
        },
      );
      final stats = await _datasource.getStats();
      state = state.copyWith(
        users: usersResult.results,
        stats: {
          'total': stats['total'] as int? ?? 0,
          'active': stats['active'] as int? ?? 0,
          'staff': stats['staff'] as int? ?? 0,
        },
        isLoading: false,
        error: null,
      );
    } catch (e) {
      state = state.copyWith(
        isLoading: false,
        error: e.toString().replaceAll('Exception: ', ''),
      );
    }
  }

  void setSearch(String q) => state = state.copyWith(search: q);

  void setRoleFilter(UserRoleFilter filter) {
    state = state.copyWith(roleFilter: filter);
    load();
  }

  void setStatusFilter(UserStatusFilter filter) {
    state = state.copyWith(statusFilter: filter);
    load();
  }

  Future<void> createUser(Map<String, dynamic> payload) async {
    state = state.copyWith(formState: const UserFormSaving());
    try {
      final created = await _datasource.createUser(payload);
      state = state.copyWith(
        users: [created, ...state.users],
        stats: {
          ...state.stats,
          'total': state.stats['total']! + 1,
          if (created.isActive) 'active': state.stats['active']! + 1,
        },
        formState: const UserFormSuccess('Usuario creado'),
      );
    } catch (e) {
      state = state.copyWith(
        formState: UserFormError(e.toString().replaceAll('Exception: ', '')),
      );
    }
  }

  Future<void> updateUser(int id, Map<String, dynamic> payload) async {
    state = state.copyWith(formState: const UserFormSaving());
    try {
      final updated = await _datasource.updateUser(id, payload);
      state = state.copyWith(
        users: state.users.map((u) => u.id == id ? updated : u).toList(),
        formState: const UserFormSuccess('Usuario actualizado'),
      );
    } catch (e) {
      state = state.copyWith(
        formState: UserFormError(e.toString().replaceAll('Exception: ', '')),
      );
    }
  }

  Future<void> deleteUser(int id) async {
    final target = state.users.firstWhere((u) => u.id == id);
    try {
      await _datasource.deleteUser(id);
      state = state.copyWith(
        users: state.users.where((u) => u.id != id).toList(),
        stats: {
          ...state.stats,
          'total': state.stats['total']! - 1,
          'active': state.stats['active']! - (target.isActive ? 1 : 0),
        },
      );
    } catch (e) {
      state = state.copyWith(error: e.toString().replaceAll('Exception: ', ''));
    }
  }

  Future<void> toggleActive(int id) async {
    final current = state.users.firstWhere((u) => u.id == id);
    final next = !current.isActive;
    state = state.copyWith(
      users: state.users
          .map((u) => u.id == id ? u.copyWith(isActive: next) : u)
          .toList(),
    );
    try {
      final result = await _datasource.toggleActive(id);
      state = state.copyWith(
        users: state.users
            .map((u) => u.id == id ? u.copyWith(isActive: result) : u)
            .toList(),
        stats: {
          ...state.stats,
          'active': state.stats['active']! + (result ? 1 : -1),
        },
      );
    } catch (_) {
      state = state.copyWith(
        users: state.users
            .map((u) => u.id == id ? u.copyWith(isActive: !next) : u)
            .toList(),
      );
    }
  }

  Future<void> toggleStaff(int id) async {
    final current = state.users.firstWhere((u) => u.id == id);
    final next = !current.isStaff;
    state = state.copyWith(
      users: state.users
          .map((u) => u.id == id ? u.copyWith(isStaff: next) : u)
          .toList(),
    );
    try {
      await _datasource.updateUser(id, {'is_staff': next});
      state = state.copyWith(
        users: state.users
            .map((u) => u.id == id ? u.copyWith(isStaff: next) : u)
            .toList(),
        stats: {
          ...state.stats,
          'staff': state.stats['staff']! + (next ? 1 : -1),
        },
      );
    } catch (_) {
      state = state.copyWith(
        users: state.users
            .map((u) => u.id == id ? u.copyWith(isStaff: !next) : u)
            .toList(),
      );
    }
  }

  void resetFormState() =>
      state = state.copyWith(formState: const UserFormIdle());
}

final usersAdminProvider =
    StateNotifierProvider<UsersAdminNotifier, UsersAdminState>((ref) {
  return UsersAdminNotifier(ref.watch(userDatasourceProvider));
});
