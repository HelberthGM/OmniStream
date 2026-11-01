package co.omnistream.patterns.creacion.singularizacion;

/**
 * Patron-R Singularizacion (re-significado del Singleton de GoF, pero EXTENSIBLE).
 *
 * <p>Problema: cuando se crea una sola instancia via Singleton clasico no es posible
 * extender la clase, quedando atados a la primera consideracion de diseno.</p>
 *
 * <p>Hipotesis: es posible crear una sola instancia acudiendo a un objeto temporal en el
 * momento de la creacion; si en algun momento no se desea tal efecto, basta con invocar el
 * constructor en una instanciacion normal. Esto conserva el principio abierto/cerrado.</p>
 *
 * <p>En OmniStream sirve de base para registros/contextos unicos (ver Instalacion) que deben
 * ser extensibles.</p>
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class ASingularidad {

    /** Referencia unica compartida (la "singularidad"). */
    protected static ASingularidad singular;

    /**
     * Singulariza la instancia recibida: si aun no hay singular, o si es de otra clase,
     * la reemplaza; de lo contrario conserva la existente.
     *
     * @param s instancia candidata a ser singular
     * @return la instancia singular efectiva
     */
    protected ASingularidad singularizar(ASingularidad s) {
        if (singular == null || !singular.getClass().equals(s.getClass())) {
            singular = s;
        }
        return singular;
    }

    /**
     * Singulariza esta misma instancia.
     *
     * @return la instancia singular efectiva
     */
    public ASingularidad singularizar() {
        return singularizar(this);
    }
}
