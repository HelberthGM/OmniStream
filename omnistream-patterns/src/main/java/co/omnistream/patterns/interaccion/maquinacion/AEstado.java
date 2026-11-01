package co.omnistream.patterns.interaccion.maquinacion;

/**
 * Patron-R Maquinacion (re-significado del State de GoF).
 *
 * <p>Problema: los estados de un modulo no son estables en el tiempo; aparecen nuevos estados
 * y los disenos se estancan al considerar solo estados iniciales.</p>
 *
 * <p>Hipotesis: crear una maquina de estados cada vez que se vean estados criticos, de modo
 * que puedan adaptarse a nuevas consideraciones.</p>
 *
 * <p>En OmniStream modela los <b>estados del reproductor</b> (Detenido, Reproduciendo,
 * Pausado) y sus transiciones ante los comandos del usuario.</p>
 *
 * @param <C> tipo del criterio que dispara las transiciones
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class AEstado<C> {

    /**
     * Dispara una transicion: si se cumple la condicion, ejecuta la accion y pasa al
     * siguiente estado.
     *
     * @param criterio criterio de la transicion
     * @return el estado resultante, o {@code null} si no hubo transicion
     */
    public AEstado<C> disparar(C criterio) {
        if (seCumpleLaCondicionDeTransicion(criterio)) {
            accion(criterio);
            return pasarAlSiguienteEstado(criterio);
        }
        return this;
    }

    /**
     * @param criterio criterio evaluado
     * @return {@code true} si la transicion procede
     */
    protected abstract boolean seCumpleLaCondicionDeTransicion(C criterio);

    /**
     * Accion asociada a la transicion.
     *
     * @param criterio criterio de la transicion
     */
    protected abstract void accion(C criterio);

    /**
     * @param criterio criterio de la transicion
     * @return el siguiente estado
     */
    protected abstract AEstado<C> pasarAlSiguienteEstado(C criterio);
}
