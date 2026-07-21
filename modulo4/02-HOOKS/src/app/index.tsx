// app/index.tsx
import { View, Text } from 'react-native'
import { Paso1 } from './components/Paso1'
import { Paso2 } from './components/Paso2'
import { Paso3 } from './components/Paso3'
import { Paso4 } from './components/Paso4'
import { Paso5 } from './components/Paso5'
import { Paso6 } from './components/Paso6'

// ┌──────────────────────────────────────────────────────────────────┐
// │  Cambia este número y guarda (Ctrl+S) para navegar entre pasos. │
// │  1  Paso 1  useState — contador de reintentos                    │
// │  2  Paso 2  useEffect — ping periódico con setInterval           │
// │  3  Paso 3  useRef — foco en TextInput y contador sin re-render  │
// │  4  Paso 4  Hooks nativos de RN — dimensiones, esquema de color  │
// │  5  Paso 5  Custom hook — useConexionSimulada                    │
// │  6  Paso 6  Ejemplo combinado — pantalla de estado del servidor  │
// └──────────────────────────────────────────────────────────────────┘
const PASO: number = 1

export default function Index() {
  switch (PASO) {
    case 1:
      return <Paso1 />
    case 2:
      return <Paso2 />
    case 3:
      return <Paso3 />
    case 4:
      return <Paso4 />
    case 5:
      return <Paso5 />
    case 6:
      return <Paso6 />
    default:
      return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
          <Text>Paso {PASO}: crea la pantalla primero</Text>
        </View>
      )
  }
}
