from django.contrib import admin
from .models import Schedule

@admin.register(Schedule)
class ScheduleAdmin(admin.ModelAdmin):
    list_display = ['route', 'vehicle', 'hora_salida', 'hora_llegada', 'dias_operacion', 'activo']
    list_filter = ['activo', 'dias_operacion']
    search_fields = ['route__name']
