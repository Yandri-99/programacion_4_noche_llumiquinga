import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:flutter_shop_app/main.dart';

void main() {
  testWidgets('App renders without errors', (WidgetTester tester) async {
    await tester.pumpWidget(const ProviderScope(child: FlutterShopApp()));
    expect(find.text('Módulo 2 · Conexión real con Django'), findsOneWidget);
  });
}
