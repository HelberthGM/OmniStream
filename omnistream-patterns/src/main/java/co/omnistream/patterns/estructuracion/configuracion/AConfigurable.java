package co.omnistream.patterns.estructuracion.configuracion;

/**
 * Patron-R Configuracion (re-significado del Decorator / cadena dinamica de GoF).
 *
 * <p>Problema: un modelo, por su caracter estatico, imposibilita el manejo versatil del
 * software, obligando a reacomodaciones que hacen proliferar abstraccion y codigo.</p>
 *
 * <p>Hipotesis: si se provee para ciertos modulos dinamicos una organizacion que pueda
 * reconfigurarse, se evita tener que reestructurar los disenos ya implementados.</p>
 *
 * <p>En OmniStream modela la <b>configuracion del reproductor</b>: filtros/etapas (subtitulos,
 * normalizacion de audio, transcodificacion) que se encadenan y reordenan dinamicamente.</p>
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class AConfigurable {

    /** Siguiente eslabon configurable. */
    private AConfigurable siguiente;

    /**
     * Servicio concreto que aporta esta etapa de la configuracion.
     */
    protected abstract void servicioEnvolvente();

    /**
     * Ejecuta este servicio y, en cadena, el de los configurables siguientes.
     */
    public final void ejecutarConfiguracion() {
        servicioEnvolvente();
        if (siguiente != null) {
            siguiente.ejecutarConfiguracion();
        }
    }

    /**
     * @return el siguiente configurable en la cadena
     */
    public AConfigurable getSiguiente() {
        return siguiente;
    }

    /**
     * Define el siguiente configurable en la cadena.
     *
     * @param siguiente eslabon siguiente
     */
    public void setSiguiente(AConfigurable siguiente) {
        this.siguiente = siguiente;
    }
}
