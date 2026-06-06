from rest_framework import generics, views
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from django.db.models import Count, Sum
from .models import Maintenance
from .serializers import MaintenanceSerializer, MaintenanceStatsSerializer
from config.permissions import IsAdminOrReadOnly, IsAdminUser

class MaintenanceListCreateView(generics.ListCreateAPIView):
    queryset = Maintenance.objects.select_related('vehicle').all()
    serializer_class = MaintenanceSerializer
    permission_classes = [IsAdminOrReadOnly]
    search_fields = ['vehicle__name', 'vehicle__placa', 'descripcion']
    filterset_fields = ['tipo', 'estado', 'vehicle']
    ordering_fields = ['fecha_programada', 'costo', 'created_at']

class MaintenanceDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Maintenance.objects.select_related('vehicle').all()
    serializer_class = MaintenanceSerializer
    permission_classes = [IsAdminOrReadOnly]

class MaintenanceStatsView(views.APIView):
    permission_classes = [IsAdminUser]

    def get(self, request):
        m = Maintenance.objects.all()
        total = m.count()
        by_tipo = dict(m.values('tipo').annotate(count=Count('id')).values_list('tipo', 'count'))
        by_status = dict(m.values('estado').annotate(count=Count('id')).values_list('estado', 'count'))
        total_cost = m.aggregate(total=Sum('costo'))['total'] or 0
        return Response({
            'total': total,
            'by_tipo': by_tipo,
            'by_status': by_status,
            'total_cost': total_cost,
        })

