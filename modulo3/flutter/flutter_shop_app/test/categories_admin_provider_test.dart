import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_shop_app/data/remote/api/categoryremotedatasource.dart';
import 'package:flutter_shop_app/domain/model/category.dart';
import 'package:flutter_shop_app/presentation/providers/categories_admin_provider.dart';

class FakeCategoryDatasource implements CategoryRemoteDatasource {
  @override
  Future<Category> createCategory(Map<String, dynamic> payload) {
    throw UnimplementedError();
  }

  @override
  Future<void> deleteCategory(int id) {
    throw UnimplementedError();
  }

  @override
  Future<Category> getCategory(int id) {
    throw UnimplementedError();
  }

  @override
  Future<PaginatedCategories> getCategories(
      {int page = 1, int pageSize = 20}) async {
    final items = List.generate(
      2,
      (index) => Category(
        id: (page - 1) * 2 + index + 1,
        name: 'Categoría ${page}_$index',
        slug: 'categoria-${page}-$index',
        description: '',
        isActive: true,
        totalProducts: 0,
        createdAt: '2024-01-01T00:00:00Z',
      ),
    );

    return PaginatedCategories(items: items, hasMore: page < 2, count: 4);
  }

  @override
  Future<Map<String, dynamic>> getStats() {
    throw UnimplementedError();
  }

  @override
  Future<Category> updateCategory(int id, Map<String, dynamic> payload) {
    throw UnimplementedError();
  }
}

void main() {
  test('loadMore agrega nuevas categorías cuando hay más páginas', () async {
    final notifier = CategoriesAdminNotifier(FakeCategoryDatasource());

    await notifier.load(reset: true);
    expect(notifier.state.categories.length, 2);
    expect(notifier.state.hasMore, isTrue);

    notifier.loadMore();
    await Future<void>.delayed(Duration.zero);
    expect(notifier.state.categories.length, 4);
    expect(notifier.state.hasMore, isFalse);
  });
}
