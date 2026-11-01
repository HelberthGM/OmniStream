package co.omnistream.domain.busqueda;

import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Criterio de busqueda/filtrado aplicable a un recurso.
 *
 * <p>Interfaz funcional que permite expresar criterios adaptables a cada usuario u organizacion
 * (por tipo, por nombre, por rango de tamano, por metadato, etc.) y combinarlos.</p>
 */
@FunctionalInterface
public interface CriterioBusqueda {

    /**
     * @param recurso recurso a evaluar
     * @return {@code true} si el recurso satisface el criterio
     */
    boolean satisface(RecursoMultimedia recurso);

    /**
     * Combinacion logica AND con otro criterio.
     *
     * @param otro criterio adicional
     * @return criterio compuesto
     */
    default CriterioBusqueda y(CriterioBusqueda otro) {
        return recurso -> this.satisface(recurso) && otro.satisface(recurso);
    }

    /**
     * Combinacion logica OR con otro criterio.
     *
     * @param otro criterio alternativo
     * @return criterio compuesto
     */
    default CriterioBusqueda o(CriterioBusqueda otro) {
        return recurso -> this.satisface(recurso) || otro.satisface(recurso);
    }
}
