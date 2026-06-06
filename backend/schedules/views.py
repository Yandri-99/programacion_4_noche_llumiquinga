from rest_framework import generics
from .models import Schedule
from .serializers import ScheduleSerializer
from config.permissions import IsAdminOrReadOnly

class ScheduleListCreateView(generics.ListCreateAPIView):
    queryset = Schedule.objects.select_related('route', 'vehicle').all()
    serializer_class = ScheduleSerializer
    permission_classes = [IsAdminOrReadOnly]
    search_fields = ['route__name', 'route__origin', 'route__destination']
    filterset_fields = ['activo', 'route', 'vehicle']
    ordering_fields = ['hora_salida', 'hora_llegada']

class ScheduleDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Schedule.objects.select_related('route', 'vehicle').all()
    serializer_class = ScheduleSerializer
    permission_classes = [IsAdminOrReadOnly]
