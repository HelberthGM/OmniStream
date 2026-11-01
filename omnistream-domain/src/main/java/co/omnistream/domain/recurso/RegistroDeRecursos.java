package co.omnistream.domain.recurso;

import co.omnistream.patterns.estructuracion.instalacion.RegistroDeInstalacion;

/**
 * Registro de acceso unico de los recursos multimedia indexados (Patron-R Instalacion).
 *
 * <p>Provee un punto unico donde cada {@link RecursoMultimedia} se instala al indexarse. Se
 * expone como singularidad perezosa para que todos los modulos compartan el mismo registro
 * durante la ejecucion.</p>
 */
public final class RegistroDeRecursos extends RegistroDeInstalacion<RecursoMultimedia> {

    private static final RegistroDeRecursos INSTANCIA = new RegistroDeRecursos();

    private RegistroDeRecursos() {
    }

    /**
     * @return la instancia unica del registro de recursos
     */
    public static RegistroDeRecursos instancia() {
        return INSTANCIA;
    }

    /**
     * Busca un recurso instalado por su identificador.
     *
     * @param id identificador del recurso
     * @return el recurso, o {@code null} si no esta instalado
     */
    public RecursoMultimedia porId(String id) {
        return getInstalados().stream()
                .filter(recurso -> recurso.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
