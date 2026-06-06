from django.urls import path
from . import views

urlpatterns = [
    path('drivers/', views.DriverListCreateView.as_view(), name='driver-list'),
    path('drivers/profile/', views.DriverProfileView.as_view(), name='driver-profile'),
    path('drivers/stats/', views.DriverStatsView.as_view(), name='driver-stats'),
    path('drivers/<int:pk>/', views.DriverDetailView.as_view(), name='driver-detail'),
    path('drivers/<int:pk>/toggle-active/', views.ToggleActiveView.as_view(), name='driver-toggle-active'),
]
