from django.contrib import admin
from .models import Vehicle

@admin.register(Vehicle)
class VehicleAdmin(admin.ModelAdmin):
    list_display = ['name', 'placa', 'tipo', 'capacidad', 'estado', 'route', 'is_active']
    list_filter = ['tipo', 'estado', 'is_active']
    search_fields = ['name', 'placa']
