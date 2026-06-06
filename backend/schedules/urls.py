from django.urls import path
from . import views

urlpatterns = [
    path('schedules/', views.ScheduleListCreateView.as_view(), name='schedule-list'),
    path('schedules/<int:pk>/', views.ScheduleDetailView.as_view(), name='schedule-detail'),
]
