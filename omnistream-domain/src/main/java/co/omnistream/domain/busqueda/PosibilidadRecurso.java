package co.omnistream.domain.busqueda;

import co.omnistream.domain.recurso.RecursoMultimedia;
import co.omnistream.patterns.interaccion.seleccion.APosibilidad;
import co.omnistream.patterns.interaccion.seleccion.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Posibilidad de seleccion sobre un recurso (materializa el Patron-R Seleccion).
 *
 * <p>Cada recurso indexado se representa como una posibilidad dentro de un {@link Selector}. Al
 * invocar {@code escogerTestigo(criterio)}, el selector resuelve las posibilidades cuyo recurso
 * satisface el criterio, acumulandolas en {@link #getResultados()}.</p>
 */
public class PosibilidadRecurso extends APosibilidad<CriterioBusqueda> {

    private final RecursoMultimedia recurso;
    private final List<RecursoMultimedia> resultados;

    /**
     * @param recurso    recurso representado por esta posibilidad
     * @param selector   selector al que se adiciona
     * @param resultados coleccion compartida donde se acumulan los recursos seleccionados
     */
    public PosibilidadRecurso(RecursoMultimedia recurso,
                              Selector<CriterioBusqueda> selector,
                              List<RecursoMultimedia> resultados) {
        super(null, selector);
        this.recurso = recurso;
        this.resultados = resultados;
    }

    @Override
    protected boolean cumpleLaCondicion(CriterioBusqueda criterio) {
        return criterio.satisface(recurso);
    }

    @Override
    public void resuelva() {
        resultados.add(recurso);
    }

    /**
     * @return los recursos seleccionados hasta el momento
     */
    public List<RecursoMultimedia> getResultados() {
        return new ArrayList<>(resultados);
    }
}
