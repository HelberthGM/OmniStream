package co.omnistream.api.recurso.dto;

/**
 * Cuerpo de la peticion para indexar un recurso.
 *
 * @param nombre      nombre visible del recurso
 * @param rutaArchivo ruta en el almacenamiento local
 * @param extension   extension del archivo (sin punto)
 * @param tamanoBytes tamano en bytes
 */
public record IndexarRecursoRequest(String nombre, String rutaArchivo, String extension, long tamanoBytes) {
}
