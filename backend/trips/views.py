from rest_framework import generics, views, status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from django.db.models import Count, Sum
from .models import Trip
from .serializers import TripSerializer, UpdateTripStatusSerializer, TripStatsSerializer
from config.permissions import IsAdminUser, IsOwnerOrAdmin

class TripListCreateView(generics.ListCreateAPIView):
    serializer_class = TripSerializer
    permission_classes = [IsAuthenticated]
    filterset_fields = ['estado']
    search_fields = ['origin', 'destination', 'driver__username', 'vehicle__placa']
    ordering_fields = ['created_at', 'fecha', 'total']

    def get_queryset(self):
        user = self.request.user
        qs = Trip.objects.select_related('driver', 'vehicle', 'route').all()
        if not user.is_staff:
            qs = qs.filter(driver=user)
        status_filter = self.request.query_params.get('status')
        if status_filter:
            qs = qs.filter(estado=status_filter)
        return qs

    def perform_create(self, serializer):
        serializer.save(driver=self.request.user)

class TripDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Trip.objects.select_related('driver', 'vehicle', 'route').all()
    serializer_class = TripSerializer
    permission_classes = [IsOwnerOrAdmin]

class UpdateTripStatusView(views.APIView):
    permission_classes = [IsAdminUser]

    def post(self, request, pk):
        try:
            trip = Trip.objects.get(pk=pk)
            serializer = UpdateTripStatusSerializer(data=request.data)
            if serializer.is_valid():
                trip.estado = serializer.validated_data['estado']
                trip.save()
                return Response(TripSerializer(trip).data)
            return Response(serializer.errors, status=400)
        except Trip.DoesNotExist:
            return Response({'error': 'Viaje no encontrado'}, status=404)

class TripStatsView(views.APIView):
    permission_classes = [IsAdminUser]

    def get(self, request):
        trips = Trip.objects.all()
        total = trips.count()
        revenue = trips.aggregate(total=Sum('total'))['total'] or 0
        by_status = dict(trips.values('estado').annotate(count=Count('id')).values_list('estado', 'count'))
        return Response({
            'total_trips': total,
            'total_revenue': revenue,
            'by_status': by_status,
        })
