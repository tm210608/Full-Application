# Full-Application

> Plantilla (skeleton) Android multi-módulo lista para reutilizar, construida con
> **MVVM + Clean Architecture**, **Jetpack Compose (Material 3)**, **Hilt**, **Room** y **Retrofit**.

Proyecto pensado como base sólida y profesional para acelerar el arranque de nuevas
aplicaciones: arquitectura desacoplada por módulos, inyección de dependencias, capa de
datos y navegación ya estructuradas, y componentes de UI reutilizables.

---

## Stack Tecnológico

| Capa            | Tecnología                                      |
|-----------------|-------------------------------------------------|
| Lenguaje        | Kotlin 1.9 (JVM 17)                             |
| UI              | Jetpack Compose + Material 3                    |
| Inyección (DI)  | Dagger Hilt                                     |
| Persistencia    | Room (SQLite)                                   |
| Red             | Retrofit + Gson                                 |
| Navegación      | Navigation Compose                             |
| Concurrencia    | Kotlin Coroutines + Flow                        |
| Tests           | JUnit 4 + MockK + Turbine                       |
| Build           | Gradle KTS + Version Catalog (`libs.versions.toml`) |
| CI              | GitHub Actions (`android-ci.yml`)              |

**Requisitos mínimos**

- `minSdk` 26 · `targetSdk` / `compileSdk` 34
- JDK 17
- Android Studio Hedgehog o superior

---

## Arquitectura

Arquitectura limpia con separación estricta por módulos Gradle. Cada feature es un módulo
independiente y las capacidades transversales viven en `:libs`.

```
:app                        → Punto de entrada, Application, tema global y composición de navegación
:login                      → Autenticación (Welcome, Login, Registro) con su capa domain/data/ui
:home                       → Pantalla principal (Scaffold + Drawer + BottomNav + HorizontalPager)
:menu                       → Menú de navegación lateral

:libs:common                → Utilidades y modelos compartidos (Gender, State, recursos de navegación)
:libs:core                  → Abstracciones base (Screen, ScreenProvider, AppInfo)
:libs:navigation            → Motor de navegación (AppNavigation, NavigationController)
:libs:network               → Capa de red (Retrofit) — paquete com.mito.network.auth de ejemplo
:libs:database              → Base de datos local (Room) — UserEntity, UserDao, DB
:libs:components            → Componentes y recursos UI reutilizables (MitoTextField, botones, colores, tipografías)
```

### Capas por módulo de feature (ej. `:login`)

```
domain/    → interfaces de repositorio, casos de uso y modelos de negocio
data/      → implementaciones, mappers, fuentes de datos y módulo Hilt (DI)
ui/        → Composables, ViewModels y modelos de UI
preview/   → Fakes de previsualización aislados de la lógica de producción
```

---

## Estado actual (skeleton)

La base está **production-ready** y limpia:

- ✅ Cero código de ejemplo con prefijo `Dummy` (renombrado a `Login*` / `Auth*`)
- ✅ Fakes de previsualización aislados en `login.preview` (no crashean: stubs seguros)
- ✅ Recursos de UI compartidos centralizados en `:libs:components`
- ✅ Tema unificado (paleta naranja) y migración a `Foundation HorizontalPager`
- ✅ Compilación con KSP, Java 17 y Version Catalog en todos los módulos
- ✅ Tests unitarios con MockK + Turbine

### Screens implementadas

- Welcome Screen (animada)
- Login Screen (email / password)
- New User Registration (formulario con validación + persistencia Room)
- Home Screen (Scaffold + Drawer + BottomNav + HorizontalPager)
- Main / Search / Favorites / Settings (esqueleto de dashboard)

---

## Cómo compilar

```bash
./gradlew assembleDebug      # APK de debug
```

## Tests

```bash
./gradlew test               # tests unitarios
./gradlew lint               # revisión de estilo/correctitud
```

## CI

El workflow `.github/workflows/android-ci.yml` compila (`assembleDebug`), ejecuta tests y
sube el APK en cada push/PR a `main` y `develop`.

---

## Cómo reutilizar esta plantilla

1. Clona el repositorio y renombra el paquete `com.mito.*` al de tu proyecto.
2. Empieza por `:libs:components` para definir tu sistema de diseño (colores, tipografías).
3. Añade tu dominio en `:libs:database` (entities/daos) y `:libs:network` (services).
4. Crea un módulo de feature por flujo, siguiendo la estructura `domain / data / ui / preview`.
5. Registra las pantallas en `:libs:core` (`Screen`) y conéctalas en `:app`.

---

## Contribuir

Ver `CONTRIBUTING.md`. Ramas sugeridas: `feature/*`, `fix/*` sobre `develop`.
