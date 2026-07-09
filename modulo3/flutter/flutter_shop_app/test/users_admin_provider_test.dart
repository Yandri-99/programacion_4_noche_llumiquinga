import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_shop_app/data/remote/api/userremotedatasource.dart';
import 'package:flutter_shop_app/domain/model/user.dart';
import 'package:flutter_shop_app/presentation/providers/users_admin_provider.dart';

class FakeUserRemoteDatasource implements UserRemoteDatasource {
  final List<User> _users;

  FakeUserRemoteDatasource([List<User>? users])
      : _users = users ??
            [
              const User(
                id: 1,
                username: 'admin',
                email: 'admin@test.com',
                firstName: 'Admin',
                lastName: 'User',
                isStaff: true,
                isActive: true,
                dateJoined: '2024-01-01',
                numOrders: 2,
              ),
              const User(
                id: 2,
                username: 'client',
                email: 'client@test.com',
                firstName: 'Client',
                lastName: 'User',
                isStaff: false,
                isActive: true,
                dateJoined: '2024-02-01',
                numOrders: 0,
              ),
            ];

  @override
  Future<PaginatedUsers> getUsers(
      {String? search, bool? isStaff, bool? isActive}) async {
    var users = _users;
    if (search != null && search.isNotEmpty) {
      users = users.where((u) => u.username.contains(search)).toList();
    }
    if (isStaff != null) {
      users = users.where((u) => u.isStaff == isStaff).toList();
    }
    if (isActive != null) {
      users = users.where((u) => u.isActive == isActive).toList();
    }
    return PaginatedUsers(count: users.length, next: null, results: users);
  }

  @override
  Future<User> createUser(Map<String, dynamic> payload) async {
    final user = User(
      id: 99,
      username: payload['username'] as String,
      email: payload['email'] as String,
      firstName: payload['first_name'] as String,
      lastName: payload['last_name'] as String,
      isStaff: payload['is_staff'] as bool? ?? false,
      isActive: payload['is_active'] as bool? ?? true,
      dateJoined: '2024-03-01',
      numOrders: 0,
    );
    _users.add(user);
    return user;
  }

  @override
  Future<User> updateUser(int id, Map<String, dynamic> payload) async {
    final index = _users.indexWhere((u) => u.id == id);
    final updated = _users[index].copyWith(
      isActive: payload['is_active'] as bool?,
      isStaff: payload['is_staff'] as bool?,
    );
    _users[index] = updated;
    return updated;
  }

  @override
  Future<void> deleteUser(int id) async {
    _users.removeWhere((u) => u.id == id);
  }

  @override
  Future<bool> toggleActive(int id) async {
    final index = _users.indexWhere((u) => u.id == id);
    final current = _users[index];
    _users[index] = current.copyWith(isActive: !current.isActive);
    return _users[index].isActive;
  }

  @override
  Future<Map<String, dynamic>> getStats() async {
    return {
      'total': _users.length,
      'active': _users.where((u) => u.isActive).length,
      'staff': _users.where((u) => u.isStaff).length,
    };
  }
}

void main() {
  group('UsersAdminNotifier', () {
    test('carga usuarios y filtra por búsqueda', () async {
      final notifier = UsersAdminNotifier(FakeUserRemoteDatasource());

      await notifier.load();

      expect(notifier.state.users, hasLength(2));
      expect(notifier.state.stats['total'], 2);

      notifier.setSearch('admin');
      expect(notifier.state.filtered, hasLength(1));
      expect(notifier.state.filtered.first.username, 'admin');
    });
  });
}
