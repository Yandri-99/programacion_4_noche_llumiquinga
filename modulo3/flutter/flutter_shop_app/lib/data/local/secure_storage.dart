import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorage {
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  static const _accessKey = 'access_token';
  static const _refreshKey = 'refresh_token';

  Future<String?> getAccess() => _storage.read(key: _accessKey);
  Future<String?> getRefresh() => _storage.read(key: _refreshKey);

  Future<void> saveAccessToken(String token) =>
      _storage.write(key: _accessKey, value: token);

  Future<void> saveRefreshToken(String token) =>
      _storage.write(key: _refreshKey, value: token);

  Future<void> saveTokens(String access, String refresh) async {
    await _storage.write(key: _accessKey, value: access);
    await _storage.write(key: _refreshKey, value: refresh);
  }

  Future<void> clearSession() async {
    await _storage.delete(key: _accessKey);
    await _storage.delete(key: _refreshKey);
  }
}

final secureStorageProvider = Provider<SecureStorage>((ref) {
  return SecureStorage();
});
