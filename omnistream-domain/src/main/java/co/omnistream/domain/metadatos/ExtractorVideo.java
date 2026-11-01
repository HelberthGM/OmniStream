package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Extractor de metadatos especificos de video (duracion, resolucion, codec, fps).
 *
 * <p>Esqueleto: define las claves que este extractor es responsable de poblar. La integracion
 * con una libreria real de analisis multimedia (p. ej. ffprobe/JAVE) se implementa aqui.</p>
 */
public class ExtractorVideo extends ExtractorMetadatos {

    @Override
    protected void extraer(RecursoMultimedia recurso) {
        // TODO: integrar analisis real del archivo de video.
        recurso.getMetadatos().poner("duracionSegundos", null);
        recurso.getMetadatos().poner("resolucion", null);
        recurso.getMetadatos().poner("codec", null);
        recurso.getMetadatos().poner("fps", null);
    }
}
