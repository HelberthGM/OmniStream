package co.omnistream.domain.recurso;

import co.omnistream.patterns.estructuracion.instalacion.AInstalable;
import co.omnistream.patterns.estructuracion.instalacion.RegistroDeInstalacion;

import java.util.Objects;
import java.util.UUID;

/**
 * Recurso multimedia gestionado por OmniStream (un video, imagen, audio o texto).
 *
 * <p>Materializa el <b>Patron-R Instalacion</b>: al indexarse, el recurso se "instala" en el
 * {@link RegistroDeRecursos} (registro de acceso unico), y al eliminarse se desinstala. Asi se
 * evita el flujo disperso de recursos entre modulos.</p>
 */
public class RecursoMultimedia extends AInstalable<RegistroDeRecursos> {

    private final String id;
    private final String nombre;
    private final String rutaArchivo;
    private final String extension;
    private final TipoRecurso tipo;
    private final long tamanoBytes;
    private final Metadatos metadatos;

    /**
     * @param nombre      nombre visible del recurso
     * @param rutaArchivo ruta en el almacenamiento local auto-hospedado
     * @param extension   extension del archivo (sin punto)
     * @param tamanoBytes tamano en bytes
     */
    public RecursoMultimedia(String nombre, String rutaArchivo, String extension, long tamanoBytes) {
        this.id = UUID.randomUUID().toString();
        this.nombre = Objects.requireNonNull(nombre, "nombre");
        this.rutaArchivo = Objects.requireNonNull(rutaArchivo, "rutaArchivo");
        this.extension = Objects.requireNonNull(extension, "extension");
        this.tipo = TipoRecurso.desdeExtension(extension);
        this.tamanoBytes = tamanoBytes;
        this.metadatos = new Metadatos();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistroDeInstalacion<AInstalable<RegistroDeRecursos>> registro() {
        return (RegistroDeInstalacion<AInstalable<RegistroDeRecursos>>) (RegistroDeInstalacion<?>)
                RegistroDeRecursos.instancia();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public String getExtension() {
        return extension;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public long getTamanoBytes() {
        return tamanoBytes;
    }

    public Metadatos getMetadatos() {
        return metadatos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecursoMultimedia otro)) {
            return false;
        }
        return id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "RecursoMultimedia{id=%s, nombre=%s, tipo=%s}".formatted(id, nombre, tipo);
    }
}
