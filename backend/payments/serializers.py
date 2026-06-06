from rest_framework import serializers
from .models import Payment

class PaymentSerializer(serializers.ModelSerializer):
    trip_info = serializers.SerializerMethodField()

    class Meta:
        model = Payment
        fields = ['id', 'trip', 'trip_info', 'monto', 'metodo_pago', 'estado', 'fecha_pago', 'referencia', 'notas']
        read_only_fields = ['fecha_pago']

    def get_trip_info(self, obj):
        if obj.trip:
            return {
                'id': obj.trip.id,
                'origin': obj.trip.origin,
                'destination': obj.trip.destination,
                'driver_name': obj.trip.driver.get_full_name() if obj.trip.driver else None,
                'estado': obj.trip.estado,
            }
        return None

class PaymentStatsSerializer(serializers.Serializer):
    total_payments = serializers.IntegerField()
    total_revenue = serializers.DecimalField(max_digits=12, decimal_places=2)
    by_method = serializers.DictField()
    by_status = serializers.DictField()
