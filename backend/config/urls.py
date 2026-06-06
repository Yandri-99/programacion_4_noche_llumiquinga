from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static
from rest_framework_simplejwt.views import TokenRefreshView
import accounts.views

urlpatterns = [
    path('admin/', admin.site.urls),
    # Auth
    path('api/auth/login/', accounts.views.EmailTokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/auth/register/', accounts.views.RegisterView.as_view(), name='auth_register'),
    path('api/auth/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/auth/logout/', accounts.views.LogoutView.as_view(), name='auth_logout'),
    # Apps
    path('api/', include('accounts.urls')),
    path('api/', include('routes.urls')),
    path('api/', include('vehicles.urls')),
    path('api/', include('trips.urls')),
    path('api/', include('schedules.urls')),
    path('api/', include('maintenance.urls')),
    path('api/', include('payments.urls')),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
