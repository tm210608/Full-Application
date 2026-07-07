# Full-Application

Aplicación Android multi-módulo con arquitectura MVVM + Clean Architecture, construida con Jetpack Compose, Hilt, Room y Retrofit.

## Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| UI | Jetpack Compose + Material 3 |
| DI | Dagger Hilt |
| Persistencia | Room |
| Red | Retrofit + Gson |
| Navegación | Navigation Compose |
| Tests | JUnit 5 + MockK + Turbine |
| Build | Gradle KTS + Version Catalog |

## Módulos

```
:app                    → Punto de entrada
:login                  → Autenticación (Welcome, Login, Registro)
:home                   → Pantalla principal con Drawer + BottomNav + Pager
:menu                   → Menú de navegación lateral
:libs:common            → Utilidades compartidas
:libs:core              → Abstracciones base
:libs:navigation        → Motor de navegación
:libs:network           → Capa de red (Retrofit)
:libs:database          → Base de datos local (Room)
:libs:components        → Componentes UI reutilizables
```

## Screens implementadas

- ✅ Welcome Screen (animada)
- ✅ Login Screen (email/password)
- ✅ New User Registration (formulario completo con validación + persistencia)
- ✅ Home Screen (Scaffold + Drawer + BottomNav + HorizontalPager)
- ✅ Main Screen (dashboard con cards)
- ✅ Search Screen (barra de búsqueda + resultados)
- ✅ Favorites Screen (estado vacío)
- ✅ Settings Screen (lista de opciones)

## Cómo compilar

```bash
./gradlew assembleDebug
```

## Tests

```bash
./gradlew test
```
