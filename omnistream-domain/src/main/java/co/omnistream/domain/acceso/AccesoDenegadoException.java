package co.omnistream.domain.acceso;

/**
 * Se lanza cuando una regla del control de acceso (Patron-R Regulacion) no se cumple.
 */
public class AccesoDenegadoException extends RuntimeException {

    /**
     * @param mensaje motivo de la denegacion
     */
    public AccesoDenegadoException(String mensaje) {
        super(mensaje);
    }
}
