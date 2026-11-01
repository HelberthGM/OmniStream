package co.omnistream.patterns.interaccion.difusionrecoleccion;

/**
 * Rol recolector del Patron-R Difusion-Recoleccion.
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public interface IRecolector {

    /**
     * Recolecta los mensajes de todos los abonados.
     */
    void recolectar();
}
