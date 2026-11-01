package co.omnistream.domain.plataforma;

import co.omnistream.patterns.interaccion.reinterpretacion.IInterprete;

/**
 * Adaptador de plataforma (materializa el Patron-R Re-Interpretacion / Adapter).
 *
 * <p>Cada plataforma (Web, Desktop, Mobile) habla un "idioma" distinto. El adaptador reinterpreta
 * la peticion nativa de la plataforma hacia una {@link PeticionReproduccion} comun que el resto
 * del sistema entiende, haciendo transparente el origen.</p>
 */
public abstract class AdaptadorPlataforma implements IInterprete {

    private PeticionReproduccion ultimaPeticion;

    /**
     * Reinterpreta la peticion nativa de la plataforma (recibida como texto crudo) hacia la
     * representacion comun y la almacena.
     */
    @Override
    public final void interpretar() {
        this.ultimaPeticion = reinterpretar();
    }

    /**
     * Cada plataforma implementa como traducir su peticion nativa a la comun.
     *
     * @return la peticion de reproduccion normalizada
     */
    protected abstract PeticionReproduccion reinterpretar();

    /**
     * @return nombre de la plataforma (WEB, DESKTOP, MOBILE)
     */
    public abstract String plataforma();

    /**
     * @return la ultima peticion reinterpretada (o {@code null} si aun no se interpreto)
     */
    public PeticionReproduccion getUltimaPeticion() {
        return ultimaPeticion;
    }
}
