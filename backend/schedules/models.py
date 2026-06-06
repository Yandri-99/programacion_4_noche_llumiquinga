from django.db import models
from routes.models import Route
from vehicles.models import Vehicle

DAYS_OF_WEEK = [
    ('LUN', 'Lunes'),
    ('MAR', 'Martes'),
    ('MIE', 'Miércoles'),
    ('JUE', 'Jueves'),
    ('VIE', 'Viernes'),
    ('SAB', 'Sábado'),
    ('DOM', 'Domingo'),
]

class Schedule(models.Model):
    route = models.ForeignKey(Route, on_delete=models.CASCADE, verbose_name='Ruta')
    vehicle = models.ForeignKey(Vehicle, on_delete=models.SET_NULL, null=True, blank=True, verbose_name='Vehículo')
    hora_salida = models.TimeField('Hora de salida')
    hora_llegada = models.TimeField('Hora de llegada')
    dias_operacion = models.CharField('Días de operación', max_length=50, help_text='Ej: LUN,MIE,VIE')
    activo = models.BooleanField('Activo', default=True)
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        verbose_name = 'Horario'
        verbose_name_plural = 'Horarios'
        ordering = ['hora_salida']

    def __str__(self):
        dias = self.dias_operacion.replace(',', ', ')
        return f"{self.route} - {self.hora_salida} ({dias})"
