from rest_framework import serializers
from .models import Trip

class DriverInTripSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    nombre = serializers.SerializerMethodField()
    email = serializers.EmailField()
    disponible = serializers.BooleanField()

    def get_nombre(self, obj):
        return obj.get_full_name() or obj.username

class VehicleInTripSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name = serializers.CharField()
    placa = serializers.CharField()
    tipo = serializers.CharField()
    capacidad = serializers.IntegerField()

class TripSerializer(serializers.ModelSerializer):
    driver_name = serializers.CharField(source='driver.get_full_name', read_only=True, default=None)
    vehicle_name = serializers.CharField(source='vehicle.name', read_only=True, default=None)
    vehicle_placa = serializers.CharField(source='vehicle.placa', read_only=True, default=None)
    route_name = serializers.CharField(source='route.name', read_only=True, default=None)
    driver_detail = DriverInTripSerializer(source='driver', read_only=True)
    vehicle_detail = VehicleInTripSerializer(source='vehicle', read_only=True)
    route_detail = serializers.SerializerMethodField()

    class Meta:
        model = Trip
        fields = ['id', 'driver', 'driver_name', 'driver_detail', 'vehicle', 'vehicle_name', 'vehicle_placa', 'vehicle_detail', 'route', 'route_name', 'route_detail', 'origin', 'destination', 'pasajeros', 'total', 'estado', 'fecha', 'created_at', 'updated_at']
        read_only_fields = ['created_at', 'updated_at', 'fecha']

    def get_route_detail(self, obj):
        if obj.route:
            return {'id': obj.route.id, 'name': obj.route.name, 'origin': obj.route.origin, 'destination': obj.route.destination}
        return None

class UpdateTripStatusSerializer(serializers.Serializer):
    estado = serializers.ChoiceField(choices=[s[0] for s in Trip._meta.get_field('estado').choices])

class TripStatsSerializer(serializers.Serializer):
    total_trips = serializers.IntegerField()
    total_revenue = serializers.DecimalField(max_digits=12, decimal_places=2)
    by_status = serializers.DictField()
