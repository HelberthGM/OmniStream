package co.omnistream.domain.busqueda;

import co.omnistream.domain.recurso.TipoRecurso;

/**
 * Fabrica de criterios de busqueda reutilizables.
 *
 * <p>Permite construir criterios adaptables a cada usuario u organizacion y combinarlos con
 * {@link CriterioBusqueda#y} / {@link CriterioBusqueda#o}.</p>
 */
public final class Criterios {

    private Criterios() {
    }

    /**
     * @param tipo tipo buscado
     * @return criterio que exige un tipo de recurso exacto
     */
    public static CriterioBusqueda porTipo(TipoRecurso tipo) {
        return recurso -> recurso.getTipo() == tipo;
    }

    /**
     * @param fragmento texto a buscar en el nombre (sin distinguir mayusculas)
     * @return criterio que exige que el nombre contenga el fragmento
     */
    public static CriterioBusqueda porNombreContiene(String fragmento) {
        String f = fragmento == null ? "" : fragmento.toLowerCase();
        return recurso -> recurso.getNombre().toLowerCase().contains(f);
    }

    /**
     * @param maximoBytes tamano maximo permitido
     * @return criterio que exige que el recurso no supere el tamano dado
     */
    public static CriterioBusqueda porTamanoMaximo(long maximoBytes) {
        return recurso -> recurso.getTamanoBytes() <= maximoBytes;
    }

    /**
     * @param clave clave de metadato que debe estar presente y no nula
     * @return criterio que exige la presencia del metadato
     */
    public static CriterioBusqueda conMetadato(String clave) {
        return recurso -> recurso.getMetadatos().obtener(clave) != null;
    }
}
