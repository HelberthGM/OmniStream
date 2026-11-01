package co.omnistream.application.busqueda;

import co.omnistream.domain.busqueda.CriterioBusqueda;
import co.omnistream.domain.busqueda.PosibilidadRecurso;
import co.omnistream.domain.recurso.RecursoMultimedia;
import co.omnistream.domain.recurso.RegistroDeRecursos;
import co.omnistream.patterns.interaccion.seleccion.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso: buscar/filtrar recursos indexados.
 *
 * <p>Materializa el <b>Patron-R Seleccion</b>: toma los recursos del registro unico, los
 * representa como posibilidades dentro de un {@link Selector} y aplica el criterio recibido.</p>
 */
public class ServicioBusqueda {

    /**
     * Busca los recursos indexados que satisfacen el criterio.
     *
     * @param criterio criterio de busqueda (puede ser compuesto con {@code y}/{@code o})
     * @return recursos que satisfacen el criterio
     */
    public List<RecursoMultimedia> buscar(CriterioBusqueda criterio) {
        Selector<CriterioBusqueda> selector = new Selector<>();
        List<RecursoMultimedia> resultados = new ArrayList<>();

        for (RecursoMultimedia recurso : RegistroDeRecursos.instancia().getInstalados()) {
            new PosibilidadRecurso(recurso, selector, resultados);
        }

        selector.escogerTestigo(criterio);
        return resultados;
    }
}
