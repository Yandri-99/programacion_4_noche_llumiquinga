from rest_framework import generics, status, views
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework_simplejwt.views import TokenObtainPairView
from .serializers import EmailTokenObtainPairSerializer

class EmailTokenObtainPairView(TokenObtainPairView):
    serializer_class = EmailTokenObtainPairSerializer
from django.contrib.auth import get_user_model
from django.db.models import Count, Q
from .models import Driver
from .serializers import DriverSerializer, RegisterSerializer, LoginSerializer, DriverStatsSerializer
from config.permissions import IsAdminOrReadOnly, IsAdminUser

Driver = get_user_model()

class RegisterView(generics.CreateAPIView):
    queryset = Driver.objects.all()
    serializer_class = RegisterSerializer
    permission_classes = [AllowAny]

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        driver = serializer.save()
        refresh = RefreshToken.for_user(driver)
        return Response({
            'access': str(refresh.access_token),
            'refresh': str(refresh),
            'user_id': driver.id,
            'email': driver.email,
            'nombre': driver.get_full_name() or driver.username,
            'is_staff': driver.is_staff,
        }, status=status.HTTP_201_CREATED)

class LogoutView(views.APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        try:
            refresh_token = request.data.get('refresh')
            if refresh_token:
                token = RefreshToken(refresh_token)
                token.blacklist()
            return Response(status=status.HTTP_204_NO_CONTENT)
        except Exception:
            return Response(status=status.HTTP_204_NO_CONTENT)

class DriverListCreateView(generics.ListCreateAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverSerializer
    permission_classes = [IsAdminOrReadOnly]
    search_fields = ['username', 'email', 'first_name', 'last_name', 'licencia']
    filterset_fields = ['is_staff', 'is_active', 'disponible']
    ordering_fields = ['username', 'date_joined']

    def get_queryset(self):
        return Driver.objects.annotate(
            num_trips=Count('trips', filter=Q(trips__isnull=False))
        )

class DriverDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverSerializer
    permission_classes = [IsAdminOrReadOnly]

class DriverProfileView(generics.RetrieveAPIView):
    serializer_class = DriverSerializer
    permission_classes = [IsAuthenticated]

    def get_object(self):
        return self.request.user

class ToggleActiveView(views.APIView):
    permission_classes = [IsAdminUser]

    def post(self, request, pk):
        try:
            driver = Driver.objects.get(pk=pk)
            driver.is_active = not driver.is_active
            driver.save()
            return Response({'message': 'Estado cambiado', 'is_active': driver.is_active})
        except Driver.DoesNotExist:
            return Response({'error': 'Conductor no encontrado'}, status=404)

class DriverStatsView(generics.RetrieveAPIView):
    permission_classes = [IsAdminUser]

    def get(self, request):
        total = Driver.objects.count()
        available = Driver.objects.filter(disponible=True, is_active=True).count()
        unavailable = Driver.objects.filter(disponible=False, is_active=True).count()
        return Response({'total': total, 'available': available, 'unavailable': unavailable})
