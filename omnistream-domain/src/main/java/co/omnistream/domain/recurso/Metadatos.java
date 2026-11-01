package co.omnistream.domain.recurso;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contenedor mutable de metadatos de un recurso.
 *
 * <p>Es el objeto "aportable" que atraviesa el pipeline de extraccion de metadatos
 * (Patron-R Aportacion). Cada extractor del pipeline deposita aqui las claves que sabe leer.</p>
 */
public class Metadatos {

    private final Map<String, Object> valores = new LinkedHashMap<>();

    /**
     * Registra o reemplaza un metadato.
     *
     * @param clave nombre del metadato (ej. "duracionSegundos", "resolucion", "codec")
     * @param valor valor asociado
     */
    public void poner(String clave, Object valor) {
        valores.put(clave, valor);
    }

    /**
     * @param clave nombre del metadato
     * @return el valor, o {@code null} si no existe
     */
    public Object obtener(String clave) {
        return valores.get(clave);
    }

    /**
     * @param clave nombre del metadato
     * @return {@code true} si el metadato esta presente
     */
    public boolean contiene(String clave) {
        return valores.containsKey(clave);
    }

    /**
     * @return vista de solo lectura de todos los metadatos
     */
    public Map<String, Object> comoMapa() {
        return Map.copyOf(valores);
    }

    @Override
    public String toString() {
        return "Metadatos" + valores;
    }
}
