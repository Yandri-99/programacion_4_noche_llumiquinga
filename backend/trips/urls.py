from django.urls import path
from . import views

urlpatterns = [
    path('trips/', views.TripListCreateView.as_view(), name='trip-list'),
    path('trips/stats/', views.TripStatsView.as_view(), name='trip-stats'),
    path('trips/<int:pk>/', views.TripDetailView.as_view(), name='trip-detail'),
    path('trips/<int:pk>/update-status/', views.UpdateTripStatusView.as_view(), name='trip-update-status'),
]
