from django.urls import path
from . import views

urlpatterns = [
    path('vehicles/', views.VehicleListCreateView.as_view(), name='vehicle-list'),
    path('vehicles/available/', views.VehicleAvailableView.as_view(), name='vehicle-available'),
    path('vehicles/stats/', views.VehicleStatsView.as_view(), name='vehicle-stats'),
    path('vehicles/<int:pk>/', views.VehicleDetailView.as_view(), name='vehicle-detail'),
]
