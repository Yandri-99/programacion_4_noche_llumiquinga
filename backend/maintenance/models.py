from django.db import models
from vehicles.models import Vehicle

MAINTENANCE_TYPES = [
    ('preventivo', 'Preventivo'),
    ('correctivo', 'Correctivo'),
    ('predictivo', 'Predictivo'),
]

MAINTENANCE_STATUS = [
    ('pendiente', 'Pendiente'),
    ('en_proceso', 'En proceso'),
    ('completado', 'Completado'),
    ('cancelado', 'Cancelado'),
]

class Maintenance(models.Model):
    vehicle = models.ForeignKey(Vehicle, on_delete=models.CASCADE, verbose_name='Vehículo', related_name='maintenances')
    tipo = models.CharField('Tipo', max_length=20, choices=MAINTENANCE_TYPES, default='preventivo')
    descripcion = models.TextField('Descripción')
    costo = models.DecimalField('Costo', max_digits=10, decimal_places=2, default=0.00)
    fecha_programada = models.DateField('Fecha programada', null=True, blank=True)
    fecha_realizacion = models.DateField('Fecha de realización', null=True, blank=True)
    estado = models.CharField('Estado', max_length=20, choices=MAINTENANCE_STATUS, default='pendiente')
    observaciones = models.TextField('Observaciones', blank=True, default='')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = 'Mantenimiento'
        verbose_name_plural = 'Mantenimientos'
        ordering = ['-created_at']

    def __str__(self):
        return f"{self.get_tipo_display()} - {self.vehicle.placa} ({self.get_estado_display()})"
