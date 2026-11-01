package co.omnistream.patterns.estructuracion.instalacion;

/**
 * Patron-R Instalacion (re-significado del Registry de GoF).
 *
 * <p>Problema: conjuntos particulares de objetos manipulados en distintos contextos deben
 * moverse, creando flujos de informacion innecesarios y dificiles de controlar.</p>
 *
 * <p>Hipotesis: crear un registro con acceso unico donde los objetos se "instalan" elimina el
 * flujo de informacion y facilita su manipulacion.</p>
 *
 * <p>En OmniStream es la base del <b>registro de recursos multimedia indexados</b>: un recurso
 * se instala al indexarse y se desinstala al eliminarse.</p>
 *
 * @param <R> tipo de registro concreto asociado a este instalable
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class AInstalable<R extends RegistroDeInstalacion<?>> {

    /**
     * Instala esta instancia en el registro correspondiente.
     */
    public void instalar() {
        registro().instalacion(this);
    }

    /**
     * Desinstala esta instancia del registro correspondiente.
     */
    public void desinstalar() {
        registro().desinstalacion(this);
    }

    /**
     * Provee el registro donde este objeto se instala. Cada especializacion decide cual.
     *
     * @return el registro de instalacion
     */
    @SuppressWarnings("unchecked")
    protected abstract RegistroDeInstalacion<AInstalable<R>> registro();
}
