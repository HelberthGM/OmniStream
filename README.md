# OmniStream

Plataforma **auto-hospedada** de gestión multimedia: almacenar, categorizar, consultar y
reproducir recursos (video, imagen, audio, texto) de forma autónoma, segura y principalmente
**offline**, manteniendo el control total de la información.

> Esta es la **base inicial** del proyecto: define la arquitectura, el framework de patrones y
> las interfaces/clases abstractas que materializan dichos patrones (alcance de "esqueleto con
> contratos"). La lógica concreta de cada módulo (parsing real de metadatos, persistencia,
> seguridad) se implementa sobre estos contratos en las siguientes iteraciones.

---

## 🚀 Cómo usarlo (demo funcional)

Esta entrega incluye una **demo funcional end-to-end**: al arrancar, el servidor carga 8 recursos
de ejemplo y expone un **panel web interactivo** para indexar, buscar y reproducir, además de la
API REST.

### 1. Requisitos
- **JDK 21 o superior** (el proyecto compila con `--release 21`; probado también en JDK 25).
- **Maven 3.9+**.
- Conexión a internet la **primera** vez (para descargar Spring Boot desde Maven Central).

### 2. Levantar el servidor

```bash
# Desde la raíz del proyecto
# 1) Compila e instala TODOS los módulos en el repositorio local (~/.m2)
mvn clean install -DskipTests

# 2) Levanta la API usando esos artefactos recién instalados
mvn spring-boot:run -pl omnistream-api
```

> **Importante:** el paso 1 no es opcional la primera vez ni después de cambiar código en
> `omnistream-domain`, `omnistream-application` o `omnistream-patterns`. Si ejecutas solo
> `mvn spring-boot:run -pl omnistream-api`, Maven resuelve esos módulos desde `~/.m2` y puede
> usar **jars viejos**, haciendo que la lista aparezca vacía o que la API devuelva `500`.

Cuando veas en consola `[OmniStream] 8 recursos de ejemplo indexados y listos.` el servidor está arriba.

> **¿La interfaz sale vacía o la API devuelve 500?** Casi siempre es un artefacto viejo en el
> repositorio local. Detén el servidor y ejecuta `mvn clean install -DskipTests` desde la raíz;
> luego vuelve a levantarlo.

### 3. Abrir el panel

Abre en el navegador: **http://localhost:8080**

Verás un panel donde puedes, **sin usar la terminal**:
- **Adjuntar** un archivo desde tu equipo (el servidor deduce nombre, tipo y tamaño, y extrae metadatos).
- **Buscar / filtrar** por tipo y por nombre, en vivo.
- **Reproducir / Pausar / Detener** cada recurso y ver cómo cambia su estado
  (`DETENIDO → REPRODUCIENDO → PAUSADO → …`), que es la máquina de estados del dominio.

> Los datos viven en memoria: se recargan los 8 ejemplos en cada reinicio.

### 4. Probar la API REST directamente (opcional)

```bash
# Adjuntar un archivo (recomendado): deduce nombre, tipo y tamaño
curl -X POST http://localhost:8080/api/recursos/upload \
  -F "archivo=@/ruta/a/mi/video.mp4"

# Indexar describiendo los datos a mano (alternativa JSON)
curl -X POST http://localhost:8080/api/recursos \
  -H 'Content-Type: application/json' \
  -d '{"nombre":"Vacaciones","rutaArchivo":"/media/v.mp4","extension":"mp4","tamanoBytes":1048576}'

# Listar / buscar
curl "http://localhost:8080/api/recursos"
curl "http://localhost:8080/api/recursos?tipo=VIDEO"
curl "http://localhost:8080/api/recursos?nombre=podcast"

# Controlar reproducción (usa un id devuelto al indexar/listar)
curl -X POST http://localhost:8080/api/reproduccion \
  -H 'Content-Type: application/json' \
  -d '{"idRecurso":"ID_DEL_RECURSO","comando":"REPRODUCIR"}'
```

### 5. Qué patrón P-rS se demuestra en cada acción

| Acción en la demo | Patrón(es) P-rS que se ejecuta(n) |
|-------------------|-----------------------------------|
| Indexar un recurso | **Instanciación** (crea el pipeline por tipo) → **Aportación** (extrae metadatos) → **Instalación** (lo registra) |
| Buscar / filtrar | **Selección** (criterios combinables) |
| Reproducir / pausar / detener | **Maquinación** (máquina de estados) |

---

## Stack técnico

| Área | Elección | Motivo |
|------|----------|--------|
| Lenguaje | **Java 25 (LTS)** | Estable, ampliamente conocido por el equipo |
| Build | **Maven multi-módulo** | Separa responsabilidades y facilita el trabajo en equipo |
| API | **Spring Boot 3** (módulo `api`) | Estándar REST, bien documentado |
| Persistencia | SQLite + JPA *(iteración siguiente)* | Offline / auto-hospedado |

---

## Arquitectura de módulos

El proyecto es un Maven multi-módulo alineado con las capas del documento ArchiMate de OmniStream:

```
omnistream/
├── omnistream-patterns      → Framework base: Patrones re-Significados (P-rS)
├── omnistream-domain        → Dominio + materialización de los P-rS
├── omnistream-application   → Casos de uso / servicios (orquestación)
└── omnistream-api           → API REST (Spring Boot) — Web / Desktop / Mobile
```

**Regla de dependencias** (de adentro hacia afuera):
`api → application → domain → patterns`. El dominio y los patrones **no** dependen de Spring:
así el núcleo queda limpio y testeable, y solo la capa `api` conoce el framework web.

---

## Patrones de diseño

La base combina **patrones GoF** con los **Patrones re-Significados (P-rS)** del profesor
Sandro J. Bolaños. Los P-rS siguen tres convenciones clave del paper:

1. **El nombre es la sustantivación de la acción** (p. ej. *Instanciación*, no *Instanciador*).
2. Son **extensibles** (respetan abierto/cerrado): a diferencia del Singleton clásico, un P-rS
   siempre se puede extender.
3. Forman un **framework base** reutilizable (módulo `omnistream-patterns`) desde el cual se
   invocan y extienden, en vez de reimplementarlos en cada uso.

### Mapeo P-rS → GoF → uso en OmniStream

| P-rS (profesor) | GoF equivalente | Dónde se usa en OmniStream |
|---|---|---|
| **Instanciación** | Factory Method | `PipelineMetadatosFactory` crea el extractor según el tipo de recurso |
| **Aportación** | Chain of Responsibility | Pipeline de extracción de metadatos (`ExtractorMetadatos`) |
| **Instalación** | Registry | `RegistroDeRecursos`: registro único de recursos indexados |
| **Selección** | Strategy / Specification | Búsqueda y filtrado por criterios (`CriterioBusqueda`, `Selector`) |
| **Maquinación** | State | Estados del reproductor (`Detenido`/`Reproduciendo`/`Pausado`) |
| **Re-Interpretación** | Adapter | Adaptadores de plataforma Web / Desktop / Mobile |
| **Regulación** | Template Method | Control de acceso por reglas (`ControlAcceso`) |
| **Configuración** | Decorator / cadena | Base para configurar el reproductor dinámicamente |
| **Difusión-Recolección** | Observer | Base para notificar eventos de indexación |
| **Singularización** | Singleton extensible | Base para registros/contextos únicos y extensibles |

Cada clase del framework (`omnistream-patterns`) incluye en su Javadoc el **Problema** y la
**Hipótesis** del P-rS correspondiente, tal como se enuncian en el paper.

---

## Flujo de ejemplo (ya funcional en el núcleo)

Indexar un recurso orquesta tres P-rS en cadena:

1. **Instanciación** → se crea el pipeline de metadatos adecuado al tipo.
2. **Aportación** → el pipeline extrae/enriquece los metadatos del recurso.
3. **Instalación** → el recurso se registra en el registro único.

Luego, **Selección** permite buscar/filtrar y **Maquinación** gobierna la reproducción.

---

## Compilar y ejecutar

> Requiere JDK 25+ y Maven 3.9+. El build de la API necesita acceso a Maven Central para
> descargar Spring Boot la primera vez.

```bash
# Compilar todo el proyecto
mvn clean install

# Levantar la API REST
mvn -pl omnistream-api spring-boot:run
```

Abre [http://localhost:8080](http://localhost:8080) para ver una guía rápida con ejemplos
de uso. La página `/` también incluye un enlace para consultar los recursos indexados.

### Endpoints REST iniciales

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET`  | `/` | Panel web interactivo (demo) |
| `POST` | `/api/recursos/upload` | **Adjunta un archivo** (multipart) y lo indexa |
| `POST` | `/api/recursos` | Indexa un recurso describiendo sus datos (JSON) |
| `GET`  | `/api/recursos?tipo=VIDEO&nombre=vac` | Busca/filtra recursos indexados |
| `POST` | `/api/reproduccion` | Aplica un comando (`REPRODUCIR`/`PAUSAR`/`DETENER`) a un recurso |

Ejemplo:

```bash
curl -X POST localhost:8080/api/recursos \
  -H 'Content-Type: application/json' \
  -d '{"nombre":"Vacaciones","rutaArchivo":"/media/v.mp4","extension":"mp4","tamanoBytes":1048576}'

curl "localhost:8080/api/recursos?tipo=VIDEO"
```

---

## Estructura de paquetes de los patrones

Los P-rS se organizan por familia, igual que en el paper:

```
co.omnistream.patterns
├── creacion
│   ├── instanciacion      (IInstanciador)
│   └── singularizacion    (ASingularidad)
├── estructuracion
│   ├── aportacion         (AAportador)
│   ├── instalacion        (AInstalable, RegistroDeInstalacion)
│   └── configuracion      (AConfigurable)
└── interaccion
    ├── seleccion          (APosibilidad, Selector)
    ├── difusionrecoleccion(AAbonado, IDifusor, IRecolector)
    ├── maquinacion        (AEstado)
    ├── reinterpretacion   (IInterprete)
    └── regulacion         (Regulador, IRegla)
```

---

## CI/CD (GitHub Actions)

El repositorio incluye automatización que se ejecuta en cada `push` y `pull_request` a `main`:

| Workflow | Archivo | Qué hace | Ejes que cubre |
|----------|---------|----------|----------------|
| **CI** | `.github/workflows/ci.yml` | Build + tests (`mvn verify`) con JDK 25; cachea Maven; sube reportes | Calidad, Confiabilidad, Velocidad, Trazabilidad |
| **CodeQL** | `.github/workflows/codeql.yml` | Análisis estático de seguridad del código Java (push, PR y semanal) | Seguridad, Calidad |
| **Supply Chain** | `.github/workflows/supply-chain.yml` | `dependency-review` en PRs + OWASP Dependency-Check programado | Seguridad (cadena de suministro) |
| **Dependabot** | `.github/dependabot.yml` | PRs automáticos de actualización de dependencias Maven y Actions | Seguridad, Mantenibilidad |

**Principios de diseño aplicados:**

- **Calidad** → nada se integra sin pasar build + tests + análisis estático.
- **Seguridad** → `permissions` mínimos por job, runner endurecido (`harden-runner`), SHA de las actions fijados (evita *tag hijacking*), escaneo de dependencias y CodeQL. No se usan secretos.
- **Confiabilidad** → matriz de JDK con `fail-fast: false`, `timeout-minutes` en todos los jobs, re-escaneos programados.
- **Velocidad** → caché de `~/.m2`, `concurrency` con `cancel-in-progress` para abortar corridas redundantes.
- **Trazabilidad / Mantenibilidad** → nombres descriptivos, reportes subidos como artefactos, resumen en el *job summary*, Dependabot manteniendo todo al día.

> **Recomendación:** activar en *Settings → Branches* una regla de protección para `main` que exija
> que el workflow **CI** pase antes de permitir el *merge* de un PR.

## Próximos pasos sugeridos

- [ ] Implementar extractores reales de metadatos (ffprobe/Tika/metadata-extractor).
- [ ] Persistencia offline con SQLite + Spring Data JPA.
- [ ] Control de acceso real (autenticación/roles) integrado con la capa `api`.
- [ ] Difusión de eventos de indexación a suscriptores (Difusión-Recolección).
- [ ] Pruebas unitarias por patrón y por caso de uso.

---

## Créditos

- Equipo OmniStream: Helberth Alejandro García Melo, Joshoa Alarcón Sánchez, Ferney Eduardo Ballesta Ávila.
- Patrones re-Significados (P-rS): basados en el documento del profesor **Sandro J. Bolaños C.**,
  Universidad Distrital Francisco José de Caldas.
