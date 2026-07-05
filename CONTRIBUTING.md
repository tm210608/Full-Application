# Contribuyendo a Full-Application

Gracias por tu interés en contribuir a este proyecto.

## Requisitos previos

- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- SDK 34

## Pull Requests

1. Crea un fork del repositorio.
2. Crea una rama desde `develop`: `git checkout -b feature/nombre-cambio`.
3. Asegúrate de que el proyecto compile: `./gradlew assembleDebug`.
4. Si añades funcionalidad nueva, incluye tests cuando sea posible.
5. Envía el PR contra la rama `develop`.

## Convenciones de código

- El proyecto sigue la guía de estilo oficial de Kotlin.
- Las pantallas Compose implementan `Screen` o `ScreenByScaffold`.
- Los ViewModels exponen estado mediante `StateFlow` y eventos mediante `SharedFlow`.
- Las dependencias se inyectan con Dagger Hilt.
- Los nombres de recursos (strings, colores, dimensiones) van en inglés.

## Estructura de commits

Usa commits semánticos:

- `feat:` — nueva funcionalidad
- `fix:` — corrección de errores
- `refactor:` — cambio de código sin alterar comportamiento
- `docs:` — cambios en documentación
- `chore:` — tareas de mantenimiento

## Reportar issues

Usa las plantillas de GitHub para bugs o features. Incluye:
- Versión del dispositivo/emulador
- Pasos para reproducir
- Comportamiento esperado vs real
- Capturas de pantalla si aplica
