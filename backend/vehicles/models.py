from django.db import models
from routes.models import Route

VEHICLE_TYPES = [
    ('Bus', 'Bus'),
    ('Minibús', 'Minibús'),
    ('Van', 'Van'),
    ('Micro', 'Micro'),
    ('Taxi', 'Taxi'),
]

VEHICLE_STATUS = [
    ('Activo', 'Activo'),
    ('Inactivo', 'Inactivo'),
    ('Mantenimiento', 'Mantenimiento'),
]

class Vehicle(models.Model):
    name = models.CharField('Nombre', max_length=100)
    description = models.TextField('Descripción', blank=True, default='')
    placa = models.CharField('Placa', max_length=20, unique=True)
    tipo = models.CharField('Tipo', max_length=20, choices=VEHICLE_TYPES, default='Bus')
    capacidad = models.PositiveIntegerField('Capacidad', default=40)
    precio_pasaje = models.DecimalField('Precio pasaje', max_digits=10, decimal_places=2, default=0.00)
    estado = models.CharField('Estado', max_length=20, choices=VEHICLE_STATUS, default='Activo')
    image = models.ImageField('Imagen', upload_to='vehicles/', null=True, blank=True)
    route = models.ForeignKey(Route, on_delete=models.SET_NULL, null=True, blank=True, verbose_name='Ruta')
    is_active = models.BooleanField('Activo', default=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = 'Vehículo'
        verbose_name_plural = 'Vehículos'
        ordering = ['name']

    def __str__(self):
        return f"{self.name} ({self.placa})"
