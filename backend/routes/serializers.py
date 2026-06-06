from rest_framework import serializers
from .models import Route

class RouteSerializer(serializers.ModelSerializer):
    num_vehicles = serializers.SerializerMethodField()

    class Meta:
        model = Route
        fields = ['id', 'name', 'description', 'origin', 'destination', 'tarifa', 'image', 'is_active', 'num_vehicles', 'created_at']
        read_only_fields = ['created_at']

    def get_num_vehicles(self, obj):
        return obj.vehicle_set.count()

class RouteStatsSerializer(serializers.Serializer):
    total = serializers.IntegerField()
    active = serializers.IntegerField()
    inactive = serializers.IntegerField()
    detail = serializers.ListField(child=serializers.DictField())
