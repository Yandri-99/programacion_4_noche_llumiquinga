from rest_framework import serializers
from .models import Schedule

class ScheduleSerializer(serializers.ModelSerializer):
    route_name = serializers.CharField(source='route.name', read_only=True)
    vehicle_name = serializers.CharField(source='vehicle.name', read_only=True, default=None)
    vehicle_placa = serializers.CharField(source='vehicle.placa', read_only=True, default=None)

    class Meta:
        model = Schedule
        fields = ['id', 'route', 'route_name', 'vehicle', 'vehicle_name', 'vehicle_placa', 'hora_salida', 'hora_llegada', 'dias_operacion', 'activo', 'created_at']
        read_only_fields = ['created_at']
