from django.db import models
from trips.models import Trip

PAYMENT_METHODS = [
    ('efectivo', 'Efectivo'),
    ('transferencia', 'Transferencia'),
    ('tarjeta', 'Tarjeta'),
    ('app', 'App móvil'),
]

PAYMENT_STATUS = [
    ('pendiente', 'Pendiente'),
    ('completado', 'Completado'),
    ('reembolsado', 'Reembolsado'),
    ('fallido', 'Fallido'),
]

class Payment(models.Model):
    trip = models.OneToOneField(Trip, on_delete=models.CASCADE, verbose_name='Viaje', related_name='payment')
    monto = models.DecimalField('Monto', max_digits=10, decimal_places=2)
    metodo_pago = models.CharField('Método de pago', max_length=20, choices=PAYMENT_METHODS, default='efectivo')
    estado = models.CharField('Estado', max_length=20, choices=PAYMENT_STATUS, default='pendiente')
    fecha_pago = models.DateTimeField('Fecha de pago', auto_now_add=True)
    referencia = models.CharField('Referencia', max_length=100, blank=True, default='')
    notas = models.TextField('Notas', blank=True, default='')

    class Meta:
        verbose_name = 'Pago'
        verbose_name_plural = 'Pagos'
        ordering = ['-fecha_pago']

    def __str__(self):
        return f"Pago #{self.id} - Viaje #{self.trip.id} - ${self.monto}"
