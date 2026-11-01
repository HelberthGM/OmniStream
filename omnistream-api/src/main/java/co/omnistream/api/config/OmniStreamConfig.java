package co.omnistream.api.config;

import co.omnistream.application.busqueda.ServicioBusqueda;
import co.omnistream.application.indexacion.ServicioIndexacion;
import co.omnistream.domain.metadatos.PipelineMetadatosFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registra como beans de Spring los servicios de aplicacion y sus colaboradores.
 *
 * <p>El dominio y la aplicacion se mantienen libres de dependencias de Spring; es esta capa de
 * la API la que los "conecta" al contenedor.</p>
 */
@Configuration
public class OmniStreamConfig {

    @Bean
    public PipelineMetadatosFactory pipelineMetadatosFactory() {
        return new PipelineMetadatosFactory();
    }

    @Bean
    public ServicioIndexacion servicioIndexacion(PipelineMetadatosFactory factory) {
        return new ServicioIndexacion(factory);
    }

    @Bean
    public ServicioBusqueda servicioBusqueda() {
        return new ServicioBusqueda();
    }
}
