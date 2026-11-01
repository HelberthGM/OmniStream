package co.omnistream.domain.plataforma;

/**
 * Adaptador de la plataforma Desktop (aplicacion de escritorio).
 *
 * <p>Esqueleto: traduce la peticion nativa del cliente de escritorio hacia la peticion comun.
 * El escritorio puede operar offline con recursos ya descargados.</p>
 */
public class AdaptadorDesktop extends AdaptadorPlataforma {

    private final String idRecurso;
    private final boolean offline;

    public AdaptadorDesktop(String idRecurso, boolean offline) {
        this.idRecurso = idRecurso;
        this.offline = offline;
    }

    @Override
    protected PeticionReproduccion reinterpretar() {
        return new PeticionReproduccion(idRecurso, "AUTO", offline);
    }

    @Override
    public String plataforma() {
        return "DESKTOP";
    }
}
