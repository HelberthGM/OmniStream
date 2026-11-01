package co.omnistream.domain.plataforma;

/**
 * Adaptador de la plataforma Web (navegador).
 *
 * <p>Esqueleto: en la implementacion real traduce parametros de la peticion HTTP/query del
 * cliente web hacia una {@link PeticionReproduccion} comun.</p>
 */
public class AdaptadorWeb extends AdaptadorPlataforma {

    private final String idRecurso;
    private final String calidad;

    public AdaptadorWeb(String idRecurso, String calidad) {
        this.idRecurso = idRecurso;
        this.calidad = calidad;
    }

    @Override
    protected PeticionReproduccion reinterpretar() {
        // La web generalmente tiene conexion; offline por defecto false.
        return new PeticionReproduccion(idRecurso, calidad == null ? "AUTO" : calidad, false);
    }

    @Override
    public String plataforma() {
        return "WEB";
    }
}
