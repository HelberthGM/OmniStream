package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.RecursoMultimedia;
import co.omnistream.patterns.estructuracion.aportacion.AAportador;

/**
 * Eslabon del pipeline de extraccion de metadatos (materializa el Patron-R Aportacion).
 *
 * <p>Cada extractor concreto lee una porcion de los metadatos de un {@link RecursoMultimedia}
 * (por ejemplo: dimensiones de una imagen, duracion de un video) y delega al siguiente eslabon.
 * Encadenandolos con {@link #adicionarAportador} se arma el pipeline completo.</p>
 */
public abstract class ExtractorMetadatos extends AAportador<RecursoMultimedia> {

    @Override
    protected final void aplicarAportacion(RecursoMultimedia recurso) {
        extraer(recurso);
    }

    /**
     * Extrae y deposita en el recurso los metadatos que este extractor sabe leer.
     *
     * @param recurso recurso cuyos metadatos se enriquecen
     */
    protected abstract void extraer(RecursoMultimedia recurso);
}
