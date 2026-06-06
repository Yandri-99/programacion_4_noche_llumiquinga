from django.contrib.auth.models import AbstractUser
from django.db import models

class Driver(AbstractUser):
    telefono = models.CharField('Teléfono', max_length=20, blank=True, default='')
    licencia = models.CharField('Licencia', max_length=50, blank=True, default='')
    disponible = models.BooleanField('Disponible', default=True)

    class Meta:
        verbose_name = 'Conductor'
        verbose_name_plural = 'Conductores'

    def __str__(self):
        return f"{self.get_full_name() or self.username} - {self.licencia or 'Sin licencia'}"
