from django.contrib import admin
from .models import Maintenance

@admin.register(Maintenance)
class MaintenanceAdmin(admin.ModelAdmin):
    list_display = ['vehicle', 'tipo', 'costo', 'fecha_programada', 'estado']
    list_filter = ['tipo', 'estado']
    search_fields = ['vehicle__name', 'vehicle__placa', 'descripcion']
