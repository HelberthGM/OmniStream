package co.omnistream.api.recurso.dto;

import co.omnistream.domain.recurso.RecursoMultimedia;

import java.util.Map;

/**
 * Representacion de un recurso para las respuestas de la API.
 *
 * @param id        identificador del recurso
 * @param nombre    nombre visible
 * @param tipo      tipo de recurso (VIDEO, IMAGEN, AUDIO, TEXTO)
 * @param extension extension del archivo
 * @param metadatos metadatos extraidos
 */
public record RecursoResponse(String id, String nombre, String tipo, String extension, Map<String, Object> metadatos) {

    /**
     * Construye el DTO a partir de la entidad de dominio.
     *
     * @param recurso recurso de dominio
     * @return DTO de respuesta
     */
    public static RecursoResponse desde(RecursoMultimedia recurso) {
        return new RecursoResponse(
                recurso.getId(),
                recurso.getNombre(),
                recurso.getTipo().name(),
                recurso.getExtension(),
                recurso.getMetadatos().comoMapa()
        );
    }
}
