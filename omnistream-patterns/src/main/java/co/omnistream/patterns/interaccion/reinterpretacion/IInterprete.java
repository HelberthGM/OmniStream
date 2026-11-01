package co.omnistream.patterns.interaccion.reinterpretacion;

/**
 * Patron-R Re-Interpretacion (re-significado del Adapter de GoF).
 *
 * <p>Problema: gran parte de los defectos en los modelos corresponde al mal uso de la
 * semantica (nominacion inadecuada) cuando un modulo pide colaboracion a otro.</p>
 *
 * <p>Hipotesis: la colaboracion que un interprete presenta debe corresponder al servicio
 * pedido, pero reinterpretado segun las condiciones del interprete, haciendo transparente el
 * uso de tales interpretaciones.</p>
 *
 * <p>En OmniStream es la base de los <b>adaptadores de plataforma</b> (Web, Desktop, Mobile):
 * cada uno reinterpreta la peticion del cliente hacia los servicios comunes de reproduccion.</p>
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public interface IInterprete {

    /**
     * Ejecuta la interpretacion propia de este interprete.
     */
    void interpretar();
}
