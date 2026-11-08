package co.omnistream.api.config;

import co.omnistream.application.indexacion.ServicioIndexacion;
import co.omnistream.domain.recurso.RecursoMultimedia;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Carga un conjunto de recursos de ejemplo al arrancar la aplicacion.
 *
 * <p>Su proposito es que la demo tenga contenido visible de inmediato (sin tener que indexar
 * nada a mano) para mostrar la funcionalidad: indexacion con pipeline de metadatos
 * (P-rS Instanciacion + Aportacion), registro unico (P-rS Instalacion) y, a partir de ahi,
 * busqueda (P-rS Seleccion) y reproduccion (P-rS Maquinacion).</p>
 *
 * <p>Los datos viven en memoria: se recargan en cada arranque.</p>
 */
@Component
public class DatosDeEjemplo implements CommandLineRunner {

    private final ServicioIndexacion servicioIndexacion;

    public DatosDeEjemplo(ServicioIndexacion servicioIndexacion) {
        this.servicioIndexacion = servicioIndexacion;
    }

    @Override
    public void run(String... args) {
        List<RecursoMultimedia> ejemplos = List.of(
                new RecursoMultimedia("Vacaciones en la playa", "/media/videos/playa.mp4", "mp4", 734_003_200L),
                new RecursoMultimedia("Tutorial Spring Boot", "/media/videos/spring.mkv", "mkv", 512_000_000L),
                new RecursoMultimedia("Atardecer en la montana", "/media/imagenes/atardecer.jpg", "jpg", 4_194_304L),
                new RecursoMultimedia("Logo OmniStream", "/media/imagenes/logo.png", "png", 262_144L),
                new RecursoMultimedia("Podcast - Patrones de diseno", "/media/audio/patrones.mp3", "mp3", 48_234_496L),
                new RecursoMultimedia("Cancion favorita", "/media/audio/cancion.flac", "flac", 31_457_280L),
                new RecursoMultimedia("Manual de usuario", "/media/textos/manual.pdf", "pdf", 1_048_576L),
                new RecursoMultimedia("Notas de la entrega", "/media/textos/notas.md", "md", 8_192L)
        );

        ejemplos.forEach(servicioIndexacion::indexar);

        System.out.printf("[OmniStream] %d recursos de ejemplo indexados y listos.%n", ejemplos.size());
    }
}
