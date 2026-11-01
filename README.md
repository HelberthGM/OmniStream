# OmniStream

Plataforma **auto-hospedada** de gestión multimedia: almacenar, categorizar, consultar y
reproducir recursos (video, imagen, audio, texto) de forma autónoma, segura y principalmente
**offline**, manteniendo el control total de la información.

> Esta es la **base inicial** del proyecto: define la arquitectura, el framework de patrones y
> las interfaces/clases abstractas que materializan dichos patrones (alcance de "esqueleto con
> contratos"). La lógica concreta de cada módulo (parsing real de metadatos, persistencia,
> seguridad) se implementa sobre estos contratos en las siguientes iteraciones.

---

## Stack técnico

| Área | Elección | Motivo |
|------|----------|--------|
| Lenguaje | **Java 21 (LTS)** | Estable, ampliamente conocido por el equipo |
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

> Requiere JDK 21+ y Maven 3.9+. El build de la API necesita acceso a Maven Central para
> descargar Spring Boot la primera vez.

```bash
# Compilar todo el proyecto
mvn clean install

# Levantar la API REST
mvn -pl omnistream-api spring-boot:run
```

### Endpoints REST iniciales

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/recursos` | Indexa un recurso (corre el pipeline de metadatos y lo registra) |
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
