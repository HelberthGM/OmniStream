package co.omnistream.api.reproduccion.dto;

/**
 * Peticion para ejecutar un comando de reproduccion sobre un recurso.
 *
 * @param idRecurso identificador del recurso
 * @param comando   comando a aplicar (REPRODUCIR, PAUSAR, DETENER)
 */
public record ReproduccionRequest(String idRecurso, String comando) {
}
