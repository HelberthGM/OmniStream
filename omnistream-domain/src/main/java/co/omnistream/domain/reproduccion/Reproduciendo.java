package co.omnistream.domain.reproduccion;

/**
 * Estado "Reproduciendo": PAUSAR pasa a {@link Pausado} y DETENER a {@link Detenido}.
 */
public class Reproduciendo extends EstadoReproduccion {

    @Override
    public String nombre() {
        return "REPRODUCIENDO";
    }

    @Override
    protected boolean seCumpleLaCondicionDeTransicion(ComandoReproduccion criterio) {
        return criterio == ComandoReproduccion.PAUSAR || criterio == ComandoReproduccion.DETENER;
    }

    @Override
    protected void accion(ComandoReproduccion criterio) {
        // Pausar o detener el flujo de reproduccion segun el comando.
    }

    @Override
    protected EstadoReproduccion pasarAlSiguienteEstado(ComandoReproduccion criterio) {
        return criterio == ComandoReproduccion.PAUSAR ? new Pausado() : new Detenido();
    }
}
