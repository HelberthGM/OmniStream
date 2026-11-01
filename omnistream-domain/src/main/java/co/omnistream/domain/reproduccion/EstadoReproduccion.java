package co.omnistream.domain.reproduccion;

import co.omnistream.patterns.interaccion.maquinacion.AEstado;

/**
 * Base de los estados del reproductor (materializa el Patron-R Maquinacion).
 *
 * <p>El reproductor delega en el estado actual la reaccion a cada {@link ComandoReproduccion}.
 * Agregar un nuevo estado (p. ej. "Buffering") no obliga a tocar los estados existentes.</p>
 */
public abstract class EstadoReproduccion extends AEstado<ComandoReproduccion> {

    /**
     * @return nombre legible del estado (util para la API y las trazas)
     */
    public abstract String nombre();
}
