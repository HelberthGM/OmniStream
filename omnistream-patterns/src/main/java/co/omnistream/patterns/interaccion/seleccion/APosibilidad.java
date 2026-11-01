package co.omnistream.patterns.interaccion.seleccion;

/**
 * Patron-R Seleccion (re-significado del Strategy / Specification de GoF).
 *
 * <p>Problema: cuando hay diferentes soluciones a un mismo problema, estas suelen estar
 * dispersas, dificultando su identificacion y creando cohesiones debiles.</p>
 *
 * <p>Hipotesis: para un conjunto de soluciones posibles se generan conjuntos cuya inspeccion
 * permite escoger o filtrar dichas soluciones segun un testigo (criterio).</p>
 *
 * <p>En OmniStream es la base de la <b>busqueda y el filtrado</b> adaptable por criterios de
 * cada usuario u organizacion: cada posibilidad representa un criterio aplicable a un recurso.</p>
 *
 * @param <T> tipo del testigo (criterio de seleccion)
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class APosibilidad<T> {

    /** Selector que agrupa esta posibilidad con otras. */
    protected final Selector<T> selector;

    /** Testigo/criterio asociado a esta posibilidad. */
    protected final T testigo;

    /**
     * @param testigo  criterio asociado a esta posibilidad
     * @param selector selector al que se auto-adiciona esta posibilidad
     */
    protected APosibilidad(T testigo, Selector<T> selector) {
        this.testigo = testigo;
        this.selector = selector;
        this.selector.adicionar(this);
    }

    /**
     * Indica si la posibilidad cumple la condicion para el testigo dado.
     *
     * @param testigo criterio a evaluar
     * @return {@code true} si cumple la condicion
     */
    protected abstract boolean cumpleLaCondicion(T testigo);

    /**
     * Resuelve (ejecuta) la posibilidad cuando es seleccionada.
     */
    public abstract void resuelva();
}
