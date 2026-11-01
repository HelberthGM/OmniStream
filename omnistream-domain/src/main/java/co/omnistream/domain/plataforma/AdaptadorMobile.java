package co.omnistream.domain.plataforma;

/**
 * Adaptador de la plataforma Mobile (aplicacion movil).
 *
 * <p>Esqueleto: traduce la peticion nativa del cliente movil hacia la peticion comun. En movil
 * es frecuente la reproduccion offline y la calidad ajustada a la red.</p>
 */
public class AdaptadorMobile extends AdaptadorPlataforma {

    private final String idRecurso;
    private final String calidad;
    private final boolean offline;

    public AdaptadorMobile(String idRecurso, String calidad, boolean offline) {
        this.idRecurso = idRecurso;
        this.calidad = calidad;
        this.offline = offline;
    }

    @Override
    protected PeticionReproduccion reinterpretar() {
        return new PeticionReproduccion(idRecurso, calidad == null ? "AUTO" : calidad, offline);
    }

    @Override
    public String plataforma() {
        return "MOBILE";
    }
}
