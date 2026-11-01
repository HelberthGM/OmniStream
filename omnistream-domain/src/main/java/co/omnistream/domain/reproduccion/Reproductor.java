package co.omnistream.domain.reproduccion;

import co.omnistream.domain.recurso.RecursoMultimedia;

import java.util.Objects;

/**
 * Reproductor de un recurso multimedia. Es el "contexto" de la maquina de estados
 * (Patron-R Maquinacion): mantiene el estado actual y le delega cada comando.
 */
public class Reproductor {

    private final RecursoMultimedia recurso;
    private EstadoReproduccion estado;

    /**
     * @param recurso recurso a reproducir; el reproductor inicia DETENIDO
     */
    public Reproductor(RecursoMultimedia recurso) {
        this.recurso = Objects.requireNonNull(recurso, "recurso");
        this.estado = new Detenido();
    }

    /**
     * Aplica un comando, delegando la transicion al estado actual.
     *
     * @param comando comando de reproduccion
     * @return nombre del estado resultante
     */
    public String ejecutar(ComandoReproduccion comando) {
        this.estado = (EstadoReproduccion) estado.disparar(comando);
        return estado.nombre();
    }

    /**
     * @return nombre del estado actual
     */
    public String estadoActual() {
        return estado.nombre();
    }

    /**
     * @return recurso asociado a este reproductor
     */
    public RecursoMultimedia getRecurso() {
        return recurso;
    }
}
