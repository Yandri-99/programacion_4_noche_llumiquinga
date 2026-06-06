from django.contrib import admin
from .models import Payment

@admin.register(Payment)
class PaymentAdmin(admin.ModelAdmin):
    list_display = ['id', 'trip', 'monto', 'metodo_pago', 'estado', 'fecha_pago']
    list_filter = ['metodo_pago', 'estado']
    search_fields = ['trip__id', 'referencia']
