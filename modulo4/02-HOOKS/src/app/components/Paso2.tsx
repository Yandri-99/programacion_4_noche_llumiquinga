// app/index.tsx — agrega la función Paso2
import { useState, useEffect } from 'react'
import { StyleSheet, Text, View, Pressable } from 'react-native'

type EstadoPing = 'pinging' | 'ok' | 'timeout' | 'inactivo'

export function Paso2() {
  const [latencia, setLatencia] = useState<number | null>(null)
  const [estado, setEstado] = useState<EstadoPing>('inactivo')
  const [activo, setActivo] = useState<boolean>(false)
  const [ciclos, setCiclos] = useState<number>(0)

  useEffect(() => {
    if (!activo) return   // no arrancar el intervalo si el ping está detenido

    const intervalo = setInterval(() => {
      setEstado('pinging')

      // Simula latencia de red: número aleatorio entre 10 ms y 200 ms
      // con un 15 % de probabilidad de timeout
      setTimeout(() => {
        const falla = Math.random() < 0.15
        if (falla) {
          setLatencia(null)
          setEstado('timeout')
        } else {
          const ms = Math.floor(Math.random() * 190) + 10
          setLatencia(ms)
          setEstado('ok')
        }
        setCiclos(c => c + 1)
      }, 400)
    }, 2000)

    // ← función de limpieza: cancela el intervalo
    return () => clearInterval(intervalo)
  }, [activo])   // se re-ejecuta cuando 'activo' cambia

  const colorEstado: Record<EstadoPing, string> = {
    pinging:  '#1565c0',
    ok:       '#2e7d32',
    timeout:  '#c62828',
    inactivo: '#757575',
  }

  return (
    <View style={styles.contenedor}>
      <Text style={styles.titulo}>Monitor de Ping — nginx-01</Text>

      <View style={styles.tarjeta}>
        <Text style={[styles.etiqueta, { color: colorEstado[estado] }]}>
          {estado.toUpperCase()}
        </Text>
        <Text style={styles.latencia}>
          {latencia !== null ? `${latencia} ms` : '— ms'}
        </Text>
        <Text style={styles.ciclos}>Ciclos completados: {ciclos}</Text>
        <Text style={styles.detalle}>nginx-01 · 10.0.2.30 · Puerto 80</Text>
      </View>

      <Pressable
        style={({ pressed }) => [
          styles.boton,
          activo ? styles.botonDetener : styles.botonIniciar,
          pressed && { opacity: 0.75 },
        ]}
        onPress={() => setActivo(a => !a)}
      >
        <Text style={styles.textoBoton}>
          {activo ? 'Detener ping' : 'Iniciar ping'}
        </Text>
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
    gap: 14,
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
  etiqueta: {
    fontSize: 12,
    fontWeight: '600',
    letterSpacing: 0.5,
  },
  latencia: {
    fontSize: 28,
    fontWeight: '700',
    color: '#1565c0',
  },
  ciclos: {
    fontSize: 13,
    color: '#555',
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
  botonDetener: {
    backgroundColor: '#c62828',
  },
  botonIniciar: {
    backgroundColor: '#1565c0',
  },
  textoBoton: {
    color: '#fff',
    fontWeight: '600',
    fontSize: 15,
  },
})