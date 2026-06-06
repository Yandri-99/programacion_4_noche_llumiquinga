from django.urls import path
from . import views

urlpatterns = [
    path('routes/', views.RouteListCreateView.as_view(), name='route-list'),
    path('routes/stats/', views.RouteStatsView.as_view(), name='route-stats'),
    path('routes/<int:pk>/', views.RouteDetailView.as_view(), name='route-detail'),
]
