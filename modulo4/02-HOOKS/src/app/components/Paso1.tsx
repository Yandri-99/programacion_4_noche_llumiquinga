// app/index.tsx — reemplaza la función Paso1
import { useState } from 'react'
import { Pressable, StyleSheet, Text, View } from 'react-native'

export function Paso1() {
  const [intentos, setIntentos] = useState<number>(0)
  const [conectado, setConectado] = useState<boolean>(false)

  function manejarReintento() {
    if (conectado) return
    const nuevoIntento = intentos + 1
    setIntentos(nuevoIntento)
    // Simula éxito al tercer intento
    if (nuevoIntento >= 3) {
      setConectado(true)
    }
  }

  function reiniciar() {
    setIntentos(0)
    setConectado(false)
  }

  const estadoTexto = conectado
    ? '✓ Conectado a redis-01'
    : `Intento ${intentos} de 3 fallido`

  const colorEstado = conectado ? '#2e7d32' : '#c62828'

  return (
    <View style={styles.contenedor}>
      <Text style={styles.titulo}>Conexión a Caché Redis</Text>

      <View style={[styles.tarjeta, { borderColor: colorEstado }]}>
        <Text style={[styles.estado, { color: colorEstado }]}>
          {estadoTexto}
        </Text>
        <Text style={styles.detalle}>redis-01 · 10.0.2.20:6379</Text>
      </View>

      <Pressable
        style={({ pressed }) => [
          styles.boton,
          conectado ? styles.botonDeshabilitado : styles.botonActivo,
          pressed && !conectado && { opacity: 0.75 },
        ]}
        onPress={manejarReintento}
        disabled={conectado}
      >
        <Text style={styles.textoBoton}>
          {conectado ? 'Conectado' : 'Reintentar conexión'}
        </Text>
      </Pressable>

      <Pressable style={styles.botonSecundario} onPress={reiniciar}>
        <Text style={styles.textoSecundario}>Reiniciar simulación</Text>
      </Pressable>
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
    gap: 16,
  },
  titulo: {
    fontSize: 20,
    fontWeight: '700',
    color: '#1a1a1a',
  },
  tarjeta: {
    width: '100%',
    padding: 16,
    borderRadius: 10,
    borderWidth: 2,
    backgroundColor: '#fff',
    gap: 6,
  },
  estado: {
    fontSize: 15,
    fontWeight: '600',
  },
  detalle: {
    fontSize: 13,
    color: '#666',
  },
  boton: {
    width: '100%',
    paddingVertical: 14,
    borderRadius: 8,
    alignItems: 'center',
  },
  botonActivo: {
    backgroundColor: '#1565c0',
  },
  botonDeshabilitado: {
    backgroundColor: '#a5d6a7',
  },
  textoBoton: {
    color: '#fff',
    fontWeight: '600',
    fontSize: 15,
  },
  botonSecundario: {
    paddingVertical: 10,
  },
  textoSecundario: {
    color: '#1565c0',
    fontSize: 14,
  },
})