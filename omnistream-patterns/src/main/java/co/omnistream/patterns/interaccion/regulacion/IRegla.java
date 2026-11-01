package co.omnistream.patterns.interaccion.regulacion;

/**
 * Regla intercambiable del Patron-R Regulacion.
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
@FunctionalInterface
public interface IRegla {

    /**
     * Aplica la regla.
     */
    void aplicar();
}
