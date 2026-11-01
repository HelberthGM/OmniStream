package co.omnistream.domain.reproduccion;

/**
 * Estado "Detenido": solo el comando REPRODUCIR produce transicion (a {@link Reproduciendo}).
 */
public class Detenido extends EstadoReproduccion {

    @Override
    public String nombre() {
        return "DETENIDO";
    }

    @Override
    protected boolean seCumpleLaCondicionDeTransicion(ComandoReproduccion criterio) {
        return criterio == ComandoReproduccion.REPRODUCIR;
    }

    @Override
    protected void accion(ComandoReproduccion criterio) {
        // Iniciar la reproduccion desde el principio.
    }

    @Override
    protected EstadoReproduccion pasarAlSiguienteEstado(ComandoReproduccion criterio) {
        return new Reproduciendo();
    }
}
