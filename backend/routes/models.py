from django.db import models

class Route(models.Model):
    name = models.CharField('Nombre', max_length=100)
    description = models.TextField('Descripción', blank=True, default='')
    origin = models.CharField('Origen', max_length=100)
    destination = models.CharField('Destino', max_length=100)
    tarifa = models.DecimalField('Tarifa', max_digits=10, decimal_places=2, default=0.00)
    image = models.ImageField('Imagen', upload_to='routes/', null=True, blank=True)
    is_active = models.BooleanField('Activo', default=True)
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        verbose_name = 'Ruta'
        verbose_name_plural = 'Rutas'
        ordering = ['name']

    def __str__(self):
        return f"{self.origin} → {self.destination}"
