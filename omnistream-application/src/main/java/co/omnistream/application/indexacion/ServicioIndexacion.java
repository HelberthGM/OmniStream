package co.omnistream.application.indexacion;

import co.omnistream.domain.metadatos.ExtractorMetadatos;
import co.omnistream.domain.metadatos.PipelineMetadatosFactory;
import co.omnistream.domain.recurso.RecursoMultimedia;

/**
 * Caso de uso: indexar un recurso multimedia.
 *
 * <p>Orquesta varios P-rS:</p>
 * <ol>
 *   <li><b>Instanciacion</b>: {@link PipelineMetadatosFactory} crea el pipeline segun el tipo.</li>
 *   <li><b>Aportacion</b>: el pipeline extrae/enriquece los metadatos del recurso.</li>
 *   <li><b>Instalacion</b>: el recurso se instala en el registro unico de recursos.</li>
 * </ol>
 */
public class ServicioIndexacion {

    private final PipelineMetadatosFactory pipelineFactory;

    /**
     * @param pipelineFactory fabrica de pipelines de extraccion de metadatos
     */
    public ServicioIndexacion(PipelineMetadatosFactory pipelineFactory) {
        this.pipelineFactory = pipelineFactory;
    }

    /**
     * Indexa un recurso: extrae sus metadatos y lo instala en el registro.
     *
     * @param recurso recurso a indexar
     * @return el mismo recurso, ya indexado y con metadatos
     */
    public RecursoMultimedia indexar(RecursoMultimedia recurso) {
        ExtractorMetadatos pipeline = pipelineFactory.construir(recurso.getTipo());
        pipeline.aportar(recurso);
        recurso.instalar();
        return recurso;
    }
}
