package co.omnistream.domain.reproduccion;

/**
 * Estado "Pausado": REPRODUCIR reanuda ({@link Reproduciendo}) y DETENER va a {@link Detenido}.
 */
public class Pausado extends EstadoReproduccion {

    @Override
    public String nombre() {
        return "PAUSADO";
    }

    @Override
    protected boolean seCumpleLaCondicionDeTransicion(ComandoReproduccion criterio) {
        return criterio == ComandoReproduccion.REPRODUCIR || criterio == ComandoReproduccion.DETENER;
    }

    @Override
    protected void accion(ComandoReproduccion criterio) {
        // Reanudar o detener segun el comando.
    }

    @Override
    protected EstadoReproduccion pasarAlSiguienteEstado(ComandoReproduccion criterio) {
        return criterio == ComandoReproduccion.REPRODUCIR ? new Reproduciendo() : new Detenido();
    }
}
