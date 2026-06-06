from django.contrib import admin
from .models import Route

@admin.register(Route)
class RouteAdmin(admin.ModelAdmin):
    list_display = ['name', 'origin', 'destination', 'tarifa', 'is_active']
    list_filter = ['is_active']
    search_fields = ['name', 'origin', 'destination']
