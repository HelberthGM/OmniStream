package co.omnistream.api.reproduccion;

import co.omnistream.api.reproduccion.dto.ReproduccionRequest;
import co.omnistream.domain.recurso.RecursoMultimedia;
import co.omnistream.domain.recurso.RegistroDeRecursos;
import co.omnistream.domain.reproduccion.ComandoReproduccion;
import co.omnistream.domain.reproduccion.Reproductor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Endpoints REST de reproduccion multiplataforma.
 *
 * <p>Mantiene un {@link Reproductor} por recurso (la maquina de estados del Patron-R
 * Maquinacion) y aplica los comandos recibidos, devolviendo el estado resultante.</p>
 *
 * <p>Nota: la sesion de reproduccion se guarda en memoria por simplicidad de la base; en una
 * iteracion posterior se asocia a la sesion del usuario.</p>
 */
@RestController
@RequestMapping("/api/reproduccion")
public class ReproduccionController {

    private final Map<String, Reproductor> reproductores = new ConcurrentHashMap<>();

    /**
     * Aplica un comando de reproduccion a un recurso indexado.
     *
     * @param request id del recurso y comando
     * @return id del recurso y estado resultante del reproductor
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> ejecutar(@RequestBody ReproduccionRequest request) {
        RecursoMultimedia recurso = RegistroDeRecursos.instancia().porId(request.idRecurso());
        if (recurso == null) {
            return ResponseEntity.notFound().build();
        }

        Reproductor reproductor = reproductores.computeIfAbsent(
                request.idRecurso(), id -> new Reproductor(recurso));

        ComandoReproduccion comando = ComandoReproduccion.valueOf(request.comando().toUpperCase());
        String estado = reproductor.ejecutar(comando);

        return ResponseEntity.ok(Map.of(
                "idRecurso", request.idRecurso(),
                "estado", estado
        ));
    }
}
