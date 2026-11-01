package co.omnistream.patterns.estructuracion.aportacion;

/**
 * Patron-R Aportacion (re-significado del Chain of Responsibility / pipeline de GoF).
 *
 * <p>Problema: generalmente un objeto afectado por las operaciones de otros objetos no se
 * abstrae como tal, aumentando la complejidad de dichas operaciones.</p>
 *
 * <p>Hipotesis: es posible centralizar en una operacion tipo "linea de ensamble" las
 * operaciones que afectaran a un objeto; se adiciona el listado de aportadores y luego se
 * pasa el objeto a transformar por toda la cadena.</p>
 *
 * <p>En OmniStream modela el <b>pipeline de extraccion de metadatos</b>: cada aportador
 * extrae/enriquece una porcion de los metadatos de un recurso (duracion, resolucion, EXIF,
 * codec, etc.) y delega al siguiente.</p>
 *
 * @param <A> tipo del objeto "aportable" que atraviesa la cadena
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class AAportador<A> {

    /** Siguiente eslabon de la cadena de aportacion. */
    private AAportador<A> siguiente;

    /**
     * Aplica la aportacion de este eslabon y delega al siguiente si existe.
     *
     * @param aportable objeto a transformar/enriquecer
     */
    public void aportar(A aportable) {
        aplicarAportacion(aportable);
        if (siguiente != null) {
            siguiente.aportar(aportable);
        }
    }

    /**
     * Adiciona un aportador al final de la cadena.
     *
     * @param aportador nuevo eslabon
     * @return este mismo aportador (para encadenar llamadas)
     */
    public AAportador<A> adicionarAportador(AAportador<A> aportador) {
        AAportador<A> sgte = this;
        while (sgte.siguiente != null) {
            sgte = sgte.siguiente;
        }
        sgte.siguiente = aportador;
        return this;
    }

    /**
     * Inserta un aportador inmediatamente despues de este eslabon.
     *
     * @param aportador eslabon a insertar
     */
    public void insertarAportador(AAportador<A> aportador) {
        aportador.siguiente = this.siguiente;
        this.siguiente = aportador;
    }

    /**
     * Operacion concreta que cada aportador aplica sobre el objeto.
     *
     * @param aportable objeto a transformar/enriquecer
     */
    protected abstract void aplicarAportacion(A aportable);
}
