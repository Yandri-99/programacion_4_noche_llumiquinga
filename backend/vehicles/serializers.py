from rest_framework import serializers
from .models import Vehicle

class RouteSummarySerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name = serializers.CharField()

class VehicleSerializer(serializers.ModelSerializer):
    route = RouteSummarySerializer(read_only=True)
    ruta_name = serializers.CharField(source='route.name', read_only=True, default=None)

    class Meta:
        model = Vehicle
        fields = ['id', 'name', 'description', 'placa', 'tipo', 'capacidad', 'precio_pasaje', 'estado', 'image', 'route', 'ruta_name', 'is_active', 'created_at', 'updated_at']
        read_only_fields = ['created_at', 'updated_at']

class VehicleStatsSerializer(serializers.Serializer):
    total_active = serializers.IntegerField()
    total_inactive = serializers.IntegerField()
    avg_capacidad = serializers.FloatField()
    by_tipo = serializers.DictField()
