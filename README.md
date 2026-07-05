# Full-Application

Aplicación Android multi-módulo con arquitectura MVVM + Clean Architecture, construida con Jetpack Compose, Hilt, Room y Retrofit.

## Estado Actual
⚠️ **Alpha temprana** — La aplicación tiene login funcional pero las pantallas principales están en fase de maqueta.

## Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| UI | Jetpack Compose + Material 3 |
| DI | Dagger Hilt |
| Persistencia | Room |
| Red | Retrofit + Gson |
| Navegación | Navigation Compose |
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
- ✅ New User Registration (formulario completo con validación)
- ✅ Home Screen (Scaffold + Drawer + BottomNav + HorizontalPager)
- 🚧 Main Screen (placeholder)
- 🚧 Search Screen (placeholder)
- 🚧 Favorites Screen (placeholder)
- 🚧 Settings Screen (placeholder)

## Cómo compilar

```bash
./gradlew assembleDebug
```

## Próximos pasos

- [ ] Mergear PR #22 (fix MitoTextField)
- [ ] Implementar pantallas reales (Main, Search, Favorites, Settings)
- [ ] Añadir tests unitarios y UI tests
- [ ] Configurar GitHub Actions CI/CD
