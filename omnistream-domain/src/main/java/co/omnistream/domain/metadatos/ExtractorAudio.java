package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Extractor de metadatos especificos de audio (duracion, bitrate, artista, album).
 *
 * <p>Esqueleto: define las claves que este extractor es responsable de poblar.</p>
 */
public class ExtractorAudio extends ExtractorMetadatos {

    @Override
    protected void extraer(RecursoMultimedia recurso) {
        // TODO: integrar analisis real del archivo de audio (jaudiotagger / tika).
        recurso.getMetadatos().poner("duracionSegundos", null);
        recurso.getMetadatos().poner("bitrate", null);
        recurso.getMetadatos().poner("artista", null);
        recurso.getMetadatos().poner("album", null);
    }
}
