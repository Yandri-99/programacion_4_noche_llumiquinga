from rest_framework import generics, views
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from django.db.models import Count, Sum
from .models import Payment
from .serializers import PaymentSerializer, PaymentStatsSerializer
from config.permissions import IsAdminOrReadOnly, IsAdminUser

class PaymentListCreateView(generics.ListCreateAPIView):
    queryset = Payment.objects.select_related('trip__driver').all()
    serializer_class = PaymentSerializer
    permission_classes = [IsAdminOrReadOnly]
    search_fields = ['trip__id', 'referencia', 'trip__origin', 'trip__destination']
    filterset_fields = ['metodo_pago', 'estado', 'trip']
    ordering_fields = ['fecha_pago', 'monto']

class PaymentDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Payment.objects.select_related('trip__driver').all()
    serializer_class = PaymentSerializer
    permission_classes = [IsAdminOrReadOnly]

class PaymentStatsView(views.APIView):
    permission_classes = [IsAdminUser]

    def get(self, request):
        p = Payment.objects.all()
        total = p.count()
        revenue = p.aggregate(total=Sum('monto'))['total'] or 0
        by_method = dict(p.values('metodo_pago').annotate(count=Count('id')).values_list('metodo_pago', 'count'))
        by_status = dict(p.values('estado').annotate(count=Count('id')).values_list('estado', 'count'))
        return Response({
            'total_payments': total,
            'total_revenue': revenue,
            'by_method': by_method,
            'by_status': by_status,
        })
