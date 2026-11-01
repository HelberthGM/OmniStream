package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Extractor de metadatos especificos de imagen (ancho, alto, EXIF).
 *
 * <p>Esqueleto: define las claves que este extractor es responsable de poblar.</p>
 */
public class ExtractorImagen extends ExtractorMetadatos {

    @Override
    protected void extraer(RecursoMultimedia recurso) {
        // TODO: integrar analisis real del archivo de imagen (ImageIO / metadata-extractor).
        recurso.getMetadatos().poner("ancho", null);
        recurso.getMetadatos().poner("alto", null);
        recurso.getMetadatos().poner("exif", null);
    }
}
