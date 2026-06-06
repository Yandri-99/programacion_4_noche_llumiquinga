from django.contrib import admin
from .models import Driver

@admin.register(Driver)
class DriverAdmin(admin.ModelAdmin):
    list_display = ['username', 'email', 'licencia', 'disponible', 'is_active', 'is_staff']
    list_filter = ['disponible', 'is_active', 'is_staff']
    search_fields = ['username', 'email', 'licencia']
