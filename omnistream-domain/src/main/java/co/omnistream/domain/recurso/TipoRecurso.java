package co.omnistream.domain.recurso;

/**
 * Tipos de recurso multimedia soportados por OmniStream.
 *
 * <p>Alineado con el objetivo del sistema de procesar multiples formatos de video, imagen,
 * audio y texto.</p>
 */
public enum TipoRecurso {
    VIDEO,
    IMAGEN,
    AUDIO,
    TEXTO;

    /**
     * Deduce el tipo de recurso a partir de la extension de un archivo.
     *
     * @param extension extension sin punto (ej. "mp4", "jpg"); puede venir en mayusculas
     * @return el tipo correspondiente
     * @throws IllegalArgumentException si la extension no es reconocida
     */
    public static TipoRecurso desdeExtension(String extension) {
        String ext = extension == null ? "" : extension.trim().toLowerCase();
        return switch (ext) {
            case "mp4", "mkv", "avi", "mov", "webm" -> VIDEO;
            case "jpg", "jpeg", "png", "gif", "bmp", "webp" -> IMAGEN;
            case "mp3", "wav", "flac", "aac", "ogg" -> AUDIO;
            case "txt", "md", "pdf", "epub", "doc", "docx" -> TEXTO;
            default -> throw new IllegalArgumentException("Extension no soportada: " + extension);
        };
    }
}
