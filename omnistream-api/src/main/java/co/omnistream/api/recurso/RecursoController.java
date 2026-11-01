package co.omnistream.api.recurso;

import co.omnistream.api.recurso.dto.IndexarRecursoRequest;
import co.omnistream.api.recurso.dto.RecursoResponse;
import co.omnistream.application.busqueda.ServicioBusqueda;
import co.omnistream.application.indexacion.ServicioIndexacion;
import co.omnistream.domain.busqueda.CriterioBusqueda;
import co.omnistream.domain.busqueda.Criterios;
import co.omnistream.domain.recurso.RecursoMultimedia;
import co.omnistream.domain.recurso.TipoRecurso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST para indexar y buscar recursos multimedia.
 *
 * <p>Delega en los servicios de aplicacion, que a su vez orquestan los P-rS del dominio.</p>
 */
@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final ServicioIndexacion servicioIndexacion;
    private final ServicioBusqueda servicioBusqueda;

    public RecursoController(ServicioIndexacion servicioIndexacion, ServicioBusqueda servicioBusqueda) {
        this.servicioIndexacion = servicioIndexacion;
        this.servicioBusqueda = servicioBusqueda;
    }

    /**
     * Indexa un recurso: ejecuta el pipeline de metadatos y lo registra.
     *
     * @param request datos del recurso a indexar
     * @return el recurso indexado con sus metadatos
     */
    @PostMapping
    public ResponseEntity<RecursoResponse> indexar(@RequestBody IndexarRecursoRequest request) {
        RecursoMultimedia recurso = new RecursoMultimedia(
                request.nombre(), request.rutaArchivo(), request.extension(), request.tamanoBytes());
        RecursoMultimedia indexado = servicioIndexacion.indexar(recurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(RecursoResponse.desde(indexado));
    }

    /**
     * Busca recursos indexados por tipo y/o fragmento del nombre.
     *
     * @param tipo    tipo de recurso (opcional)
     * @param nombre  fragmento del nombre (opcional)
     * @return recursos que satisfacen los criterios
     */
    @GetMapping
    public List<RecursoResponse> buscar(@RequestParam(required = false) TipoRecurso tipo,
                                        @RequestParam(required = false) String nombre) {
        CriterioBusqueda criterio = recurso -> true;
        if (tipo != null) {
            criterio = criterio.y(Criterios.porTipo(tipo));
        }
        if (nombre != null && !nombre.isBlank()) {
            criterio = criterio.y(Criterios.porNombreContiene(nombre));
        }
        return servicioBusqueda.buscar(criterio).stream()
                .map(RecursoResponse::desde)
                .toList();
    }
}
