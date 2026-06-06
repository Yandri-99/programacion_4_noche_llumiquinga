from django.urls import path
from . import views

urlpatterns = [
    path('maintenance/', views.MaintenanceListCreateView.as_view(), name='maintenance-list'),
    path('maintenance/stats/', views.MaintenanceStatsView.as_view(), name='maintenance-stats'),
    path('maintenance/<int:pk>/', views.MaintenanceDetailView.as_view(), name='maintenance-detail'),
]
