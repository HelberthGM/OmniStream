package co.omnistream.patterns.creacion.instanciacion;

/**
 * Patron-R Instanciacion (equivalente re-significado del Factory Method de GoF).
 *
 * <p>Problema: la creacion de un objeto acopla una aplicacion, limitando la extensibilidad.</p>
 *
 * <p>Hipotesis: usando una interface con un tipo plantilla y dejando a cada clase que la
 * implementa la responsabilidad de instanciar, se elimina el acoplamiento y se flexibiliza
 * la instanciacion.</p>
 *
 * <p>En OmniStream se usa para crear el procesador de metadatos adecuado segun el tipo de
 * recurso (video, imagen, audio, texto) sin acoplar el codigo cliente a clases concretas.</p>
 *
 * @param <T> tipo del objeto a instanciar
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public interface IInstanciador<T> {

    /**
     * Instancia y devuelve un objeto de tipo {@code T}.
     *
     * @return la nueva instancia
     */
    T instanciar();
}
