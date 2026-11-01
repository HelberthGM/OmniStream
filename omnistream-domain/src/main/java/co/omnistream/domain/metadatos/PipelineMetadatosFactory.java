package co.omnistream.domain.metadatos;

import co.omnistream.domain.recurso.TipoRecurso;
import co.omnistream.patterns.creacion.instanciacion.IInstanciador;

import java.util.EnumMap;
import java.util.Map;

/**
 * Construye el pipeline de extraccion de metadatos adecuado segun el {@link TipoRecurso}.
 *
 * <p>Materializa el <b>Patron-R Instanciacion</b>: en lugar de que el codigo cliente conozca
 * las clases concretas de extractores, registra un {@link IInstanciador} por tipo. Asi, agregar
 * soporte para un nuevo tipo o cambiar la cadena no acopla a los consumidores (principio
 * abierto/cerrado).</p>
 *
 * <p>Cada pipeline inicia con {@link ExtractorBasico} y encadena el extractor especializado del
 * tipo correspondiente mediante el Patron-R Aportacion.</p>
 */
public class PipelineMetadatosFactory {

    private final Map<TipoRecurso, IInstanciador<ExtractorMetadatos>> instanciadores =
            new EnumMap<>(TipoRecurso.class);

    /**
     * Crea la factory con los instanciadores por defecto para los cuatro tipos soportados.
     */
    public PipelineMetadatosFactory() {
        instanciadores.put(TipoRecurso.VIDEO, ExtractorVideo::new);
        instanciadores.put(TipoRecurso.IMAGEN, ExtractorImagen::new);
        instanciadores.put(TipoRecurso.AUDIO, ExtractorAudio::new);
        instanciadores.put(TipoRecurso.TEXTO, ExtractorTexto::new);
    }

    /**
     * Permite registrar o reemplazar el instanciador de un tipo (extensibilidad).
     *
     * @param tipo         tipo de recurso
     * @param instanciador fabrica del extractor especializado
     */
    public void registrar(TipoRecurso tipo, IInstanciador<ExtractorMetadatos> instanciador) {
        instanciadores.put(tipo, instanciador);
    }

    /**
     * Construye el pipeline para un tipo: extractor basico + extractor especializado.
     *
     * @param tipo tipo de recurso
     * @return la cabeza de la cadena de extraccion
     */
    public ExtractorMetadatos construir(TipoRecurso tipo) {
        IInstanciador<ExtractorMetadatos> instanciador = instanciadores.get(tipo);
        if (instanciador == null) {
            throw new IllegalArgumentException("Sin pipeline registrado para el tipo: " + tipo);
        }
        ExtractorMetadatos cabeza = new ExtractorBasico();
        cabeza.adicionarAportador(instanciador.instanciar());
        return cabeza;
    }
}
