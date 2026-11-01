package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Extractor comun a todos los tipos: deposita los metadatos basicos presentes en el recurso.
 *
 * <p>Suele ser el primer eslabon del pipeline, independientemente del tipo de recurso.</p>
 */
public class ExtractorBasico extends ExtractorMetadatos {

    @Override
    protected void extraer(RecursoMultimedia recurso) {
        recurso.getMetadatos().poner("nombre", recurso.getNombre());
        recurso.getMetadatos().poner("tipo", recurso.getTipo().name());
        recurso.getMetadatos().poner("extension", recurso.getExtension());
        recurso.getMetadatos().poner("tamanoBytes", recurso.getTamanoBytes());
    }
}
