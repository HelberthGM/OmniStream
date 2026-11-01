package co.omnistream.patterns.estructuracion.instalacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registro de acceso unico para el Patron-R Instalacion.
 *
 * <p>Mantiene la lista de elementos instalados y expone solo una copia de consulta, de modo
 * que la unica forma de modificar el registro sea a traves de {@link #instalacion(Object)} y
 * {@link #desinstalacion(Object)}.</p>
 *
 * @param <T> tipo de elemento instalado
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public class RegistroDeInstalacion<T> {

    private final List<T> instalados = new ArrayList<>();

    /**
     * Devuelve una lista de solo consulta de los elementos instalados.
     *
     * @return copia inmutable de los elementos instalados
     */
    public List<T> getInstalados() {
        return Collections.unmodifiableList(new ArrayList<>(instalados));
    }

    /**
     * Instala un elemento.
     *
     * @param elemento elemento a instalar
     */
    public void instalacion(T elemento) {
        instalados.add(elemento);
    }

    /**
     * Desinstala un elemento.
     *
     * @param elemento elemento a desinstalar
     */
    public void desinstalacion(T elemento) {
        instalados.remove(elemento);
    }
}
