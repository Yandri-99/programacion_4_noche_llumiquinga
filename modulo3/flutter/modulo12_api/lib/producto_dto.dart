import 'producto.dart';

class ProductoDto {
  final int     id;
  final String  name;
  final int  price;
  final bool    isActive;
  final String? categoryName;

  const ProductoDto({
    required this.id,
    required this.name,
    required this.price,
    required this.isActive,
    this.categoryName,
  });

  factory ProductoDto.fromJson(Map<String, dynamic> json) => ProductoDto(
    id:           json['id']            as int,
    name:         json['title']          as String? ?? 'nombre',
    price:        json['price']!       as int? ?? 0,
    isActive:     json['is_active']     as bool? ?? false,
    categoryName: json['category']['name'] as String? ?? 'categoría',
  );

  Producto toDomain() => Producto(
    id:        id,
    nombre:    name,
    precio:    price.toDouble() ?? 0,
    activo:    isActive,
    categoria: categoryName,
  );
}