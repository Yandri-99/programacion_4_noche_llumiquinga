from django.contrib import admin
from .models import Trip

@admin.register(Trip)
class TripAdmin(admin.ModelAdmin):
    list_display = ['id', 'driver', 'vehicle', 'route', 'estado', 'pasajeros', 'total', 'fecha']
    list_filter = ['estado', 'fecha']
    search_fields = ['driver__username', 'vehicle__placa', 'origin', 'destination']
