from rest_framework import serializers
from .models import Maintenance

class MaintenanceSerializer(serializers.ModelSerializer):
    vehicle_name = serializers.CharField(source='vehicle.name', read_only=True)
    vehicle_placa = serializers.CharField(source='vehicle.placa', read_only=True)

    class Meta:
        model = Maintenance
        fields = ['id', 'vehicle', 'vehicle_name', 'vehicle_placa', 'tipo', 'descripcion', 'costo', 'fecha_programada', 'fecha_realizacion', 'estado', 'observaciones', 'created_at', 'updated_at']
        read_only_fields = ['created_at', 'updated_at']

class MaintenanceStatsSerializer(serializers.Serializer):
    total = serializers.IntegerField()
    by_tipo = serializers.DictField()
    by_status = serializers.DictField()
    total_cost = serializers.DecimalField(max_digits=12, decimal_places=2)
