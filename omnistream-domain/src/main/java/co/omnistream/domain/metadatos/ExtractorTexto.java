package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Extractor de metadatos especificos de texto (numero de palabras, idioma, encoding).
 *
 * <p>Esqueleto: define las claves que este extractor es responsable de poblar.</p>
 */
public class ExtractorTexto extends ExtractorMetadatos {

    @Override
    protected void extraer(RecursoMultimedia recurso) {
        // TODO: integrar analisis real del archivo de texto (Apache Tika).
        recurso.getMetadatos().poner("palabras", null);
        recurso.getMetadatos().poner("idioma", null);
        recurso.getMetadatos().poner("encoding", null);
    }
}
