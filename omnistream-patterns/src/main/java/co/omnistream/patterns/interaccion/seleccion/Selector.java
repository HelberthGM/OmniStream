package co.omnistream.patterns.interaccion.seleccion;

import java.util.ArrayList;
import java.util.List;

/**
 * Selector del Patron-R Seleccion: agrupa posibilidades y permite escoger o filtrar.
 *
 * @param <T> tipo del testigo (criterio de seleccion)
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public class Selector<T> {

    private final List<APosibilidad<T>> opciones = new ArrayList<>();

    /**
     * Adiciona una posibilidad al selector.
     *
     * @param opcion posibilidad a agregar
     */
    public void adicionar(APosibilidad<T> opcion) {
        opciones.add(opcion);
    }

    /**
     * Remueve una posibilidad del selector.
     *
     * @param opcion posibilidad a quitar
     */
    public void remover(APosibilidad<T> opcion) {
        opciones.remove(opcion);
    }

    /**
     * Resuelve las posibilidades que CUMPLEN la condicion para el testigo dado.
     *
     * @param testigo criterio de seleccion
     */
    public void escogerTestigo(T testigo) {
        for (APosibilidad<T> opcion : opciones) {
            if (opcion.cumpleLaCondicion(testigo)) {
                opcion.resuelva();
            }
        }
    }

    /**
     * Resuelve las posibilidades que NO cumplen la condicion para el testigo dado.
     *
     * @param testigo criterio de filtrado
     */
    public void filtrarTestigo(T testigo) {
        for (APosibilidad<T> opcion : opciones) {
            if (!opcion.cumpleLaCondicion(testigo)) {
                opcion.resuelva();
            }
        }
    }
}
