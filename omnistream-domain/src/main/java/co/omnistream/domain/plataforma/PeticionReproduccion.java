package co.omnistream.domain.plataforma;

/**
 * Peticion de reproduccion normalizada, comun a todas las plataformas.
 *
 * <p>Cada adaptador de plataforma (Web, Desktop, Mobile) reinterpreta su peticion nativa hacia
 * esta representacion comun (Patron-R Re-Interpretacion).</p>
 *
 * @param idRecurso identificador del recurso a reproducir
 * @param calidad   calidad solicitada (ej. "AUTO", "1080p", "AUDIO_ONLY")
 * @param offline   si la reproduccion debe resolverse en modo offline
 */
public record PeticionReproduccion(String idRecurso, String calidad, boolean offline) {
}
