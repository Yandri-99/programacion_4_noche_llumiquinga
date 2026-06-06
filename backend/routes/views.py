from rest_framework import generics, views
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from .models import Route
from .serializers import RouteSerializer, RouteStatsSerializer
from config.permissions import IsAdminOrReadOnly

class RouteListCreateView(generics.ListCreateAPIView):
    queryset = Route.objects.all()
    serializer_class = RouteSerializer
    permission_classes = [IsAdminOrReadOnly]
    search_fields = ['name', 'origin', 'destination']
    filterset_fields = ['is_active']
    ordering_fields = ['name', 'tarifa', 'created_at']

class RouteDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Route.objects.all()
    serializer_class = RouteSerializer
    permission_classes = [IsAdminOrReadOnly]

class RouteStatsView(views.APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        routes = Route.objects.all()
        total = routes.count()
        active = routes.filter(is_active=True).count()
        inactive = routes.filter(is_active=False).count()
        detail = [{'id': r.id, 'name': r.name, 'origin': r.origin, 'destination': r.destination, 'num_vehicles': r.vehicle_set.count()} for r in routes]
        return Response({'total': total, 'active': active, 'inactive': inactive, 'detail': detail})
