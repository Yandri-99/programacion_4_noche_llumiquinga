import { useWindowDimensions, useColorScheme, StyleSheet, Text, View } from 'react-native'

export function Paso4() {
  const { width, height, scale, fontScale } = useWindowDimensions()
  const esquema = useColorScheme()

  const colores = {
    fondo:   esquema === 'dark' ? '#0d1b2a' : '#f5f5f5',
    tarjeta: esquema === 'dark' ? '#1a2a3a' : '#ffffff',
    texto:   esquema === 'dark' ? '#e8eaed' : '#1a1a1a',
    detalle: esquema === 'dark' ? '#9aa0a6' : '#666666',
    borde:   esquema === 'dark' ? '#2d3e50' : '#e0e0e0',
  }

  const columnas = width > 500 ? 2 : 1
  const anchoTarjeta = (width - 48 - (columnas - 1) * 12) / columnas

  const servidores = [
    { nombre: 'web-01',   ip: '10.0.2.10', estado: 'activo' },
    { nombre: 'db-01',    ip: '10.0.2.20', estado: 'activo' },
    { nombre: 'cache-01', ip: '10.0.2.30', estado: 'timeout' },
    { nombre: 'backup-01',ip: '10.0.2.40', estado: 'activo' },
  ]

  return (
    <View style={[styles.contenedor, { backgroundColor: colores.fondo }]}>
      <Text style={[styles.titulo, { color: colores.texto }]}>
        Inventario de Servidores
      </Text>

      <View style={[styles.tarjeta, {
        backgroundColor: colores.tarjeta,
        borderColor: colores.borde,
        width: '100%',
      }]}>
        <Text style={[styles.etiqueta, { color: colores.detalle }]}>
          Pantalla: {Math.round(width)} × {Math.round(height)} dp
          {'  '}·{'  '}escala: {scale.toFixed(1)}
          {'  '}·{'  '}tema: {esquema ?? 'sin preferencia'}
        </Text>
      </View>

      <View style={{ flexDirection: 'row', flexWrap: 'wrap', gap: 12 }}>
        {servidores.map(srv => (
          <View
            key={srv.nombre}
            style={[styles.tarjeta, {
              backgroundColor: colores.tarjeta,
              borderColor: colores.borde,
              width: anchoTarjeta,
            }]}
          >
            <Text style={[styles.nombreServidor, { color: colores.texto }]}>
              {srv.nombre}
            </Text>
            <Text style={[styles.ipServidor, { color: colores.detalle }]}>
              {srv.ip}
            </Text>
            <Text style={{
              fontSize: 12,
              fontWeight: '600',
              color: srv.estado === 'activo' ? '#2e7d32' : '#c62828',
            }}>
              {srv.estado.toUpperCase()}
            </Text>
          </View>
        ))}
      </View>

      <View style={[styles.nota, { borderColor: colores.borde }]}>
        <Text style={[styles.notaTexto, { color: colores.detalle }]}>
          useSafeAreaInsets() — disponible desde react-native-safe-area-context.
          El hook expone los valores numéricos {'{'}top, bottom, left, right{'}'}
          para cuando necesitas posicionar elementos manualmente respetando el
          notch y la barra de inicio.
        </Text>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  contenedor: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 24,
    backgroundColor: '#f5f5f5',
    gap: 14,
  },
  titulo: {
    fontSize: 20,
    fontWeight: '700',
    color: '#1a1a1a',
  },
  tarjeta: {
    padding: 16,
    borderRadius: 10,
    borderWidth: 2,
    backgroundColor: '#fff',
    gap: 6,
  },
  etiqueta: {
    fontSize: 12,
    fontWeight: '600',
    letterSpacing: 0.5,
  },
  nombreServidor: {
    fontSize: 15,
    fontWeight: '600',
    color: '#1a1a1a',
  },
  ipServidor: {
    fontSize: 12,
    color: '#777',
  },
  nota: {
    borderWidth: 1,
    borderRadius: 8,
    padding: 12,
    width: '100%',
  },
  notaTexto: {
    fontSize: 12,
    lineHeight: 18,
  },
})
