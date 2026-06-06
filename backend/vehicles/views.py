from rest_framework import generics, views
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from django.db.models import Count, Avg
from .models import Vehicle
from .serializers import VehicleSerializer, VehicleStatsSerializer
from config.permissions import IsAdminOrReadOnly

class VehicleListCreateView(generics.ListCreateAPIView):
    queryset = Vehicle.objects.select_related('route').all()
    serializer_class = VehicleSerializer
    permission_classes = [IsAdminOrReadOnly]
    search_fields = ['name', 'placa', 'description']
    filterset_fields = ['tipo', 'estado', 'is_active', 'route']
    ordering_fields = ['name', 'precio_pasaje', 'capacidad', 'created_at']

    def get_queryset(self):
        qs = super().get_queryset()
        tipo = self.request.query_params.get('tipo')
        capacidad_min = self.request.query_params.get('capacidad_min')
        capacidad_max = self.request.query_params.get('capacidad_max')
        if tipo:
            qs = qs.filter(tipo__iexact=tipo)
        if capacidad_min:
            qs = qs.filter(capacidad__gte=int(capacidad_min))
        if capacidad_max:
            qs = qs.filter(capacidad__lte=int(capacidad_max))
        return qs

class VehicleDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Vehicle.objects.select_related('route').all()
    serializer_class = VehicleSerializer
    permission_classes = [IsAdminOrReadOnly]

class VehicleAvailableView(generics.ListAPIView):
    serializer_class = VehicleSerializer
    permission_classes = [IsAuthenticated]

    def get_queryset(self):
        return Vehicle.objects.select_related('route').filter(
            is_active=True, estado='Activo'
        )

class VehicleStatsView(views.APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        vehicles = Vehicle.objects.all()
        total_active = vehicles.filter(is_active=True).count()
        total_inactive = vehicles.filter(is_active=False).count()
        avg = vehicles.aggregate(avg=Avg('capacidad'))['avg'] or 0
        by_tipo = dict(vehicles.values('tipo').annotate(count=Count('id')).values_list('tipo', 'count'))
        return Response({
            'total_active': total_active,
            'total_inactive': total_inactive,
            'avg_capacidad': float(avg),
            'by_tipo': by_tipo,
        })
