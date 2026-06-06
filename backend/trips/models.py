from django.db import models
from django.conf import settings

TRIP_STATUS = [
    ('pendiente', 'Pendiente'),
    ('en_curso', 'En curso'),
    ('completado', 'Completado'),
    ('cancelado', 'Cancelado'),
]

class Trip(models.Model):
    driver = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.SET_NULL, null=True, blank=True, verbose_name='Conductor', related_name='trips')
    vehicle = models.ForeignKey('vehicles.Vehicle', on_delete=models.SET_NULL, null=True, blank=True, verbose_name='Vehículo')
    route = models.ForeignKey('routes.Route', on_delete=models.SET_NULL, null=True, blank=True, verbose_name='Ruta')
    origin = models.CharField('Origen', max_length=100, blank=True, default='')
    destination = models.CharField('Destino', max_length=100, blank=True, default='')
    pasajeros = models.PositiveIntegerField('Pasajeros', default=0)
    total = models.DecimalField('Total', max_digits=10, decimal_places=2, default=0.00)
    estado = models.CharField('Estado', max_length=20, choices=TRIP_STATUS, default='pendiente')
    fecha = models.DateField('Fecha', auto_now_add=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = 'Viaje'
        verbose_name_plural = 'Viajes'
        ordering = ['-created_at']

    def __str__(self):
        return f"Viaje #{self.id} - {self.origin or '?'} → {self.destination or '?'}"
