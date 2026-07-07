// lib/data/repository/category_repository_impl.dart

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_shop_app/data/remote/api/categoryremotedatasource.dart';
import 'package:flutter_shop_app/domain/repository/catalog_repository.dart';
import '../../domain/model/category.dart';

class CategoryRepositoryImpl implements CategoryRepository {
  final CategoryRemoteDatasource _datasource;
  CategoryRepositoryImpl(this._datasource);

  @override
  Future<List<Category>> getCategories() => _datasource.getCategories();

  @override
  Future<Category> getCategory(int id) => _datasource.getCategory(id);

  @override
  Future<Category> createCategory(Map<String, dynamic> payload) =>
      _datasource.createCategory(payload);

  @override
  Future<Category> updateCategory(int id, Map<String, dynamic> payload) =>
      _datasource.updateCategory(id, payload);

  @override
  Future<void> deleteCategory(int id) => _datasource.deleteCategory(id);

  @override
  Future<Map<String, dynamic>> getStats() => _datasource.getStats();
}

final categoryRepositoryProvider = Provider<CategoryRepository>((ref) {
  return CategoryRepositoryImpl(ref.watch(categoryDatasourceProvider));
});