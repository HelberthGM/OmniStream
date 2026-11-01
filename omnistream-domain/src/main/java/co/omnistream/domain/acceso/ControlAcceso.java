package co.omnistream.domain.acceso;

import co.omnistream.patterns.interaccion.regulacion.IRegla;
import co.omnistream.patterns.interaccion.regulacion.Regulador;

/**
 * Control de acceso por usuario (materializa el Patron-R Regulacion).
 *
 * <p>Extiende {@link Regulador} para evaluar, como una plantilla fija de tres pasos, las reglas
 * de: (1) estar autenticado, (2) tener el rol requerido y (3) estar dentro de cuota. Cada regla
 * es intercambiable sin alterar el orden de la plantilla.</p>
 *
 * <p>Si alguna regla no se cumple, lanza {@link AccesoDenegadoException}.</p>
 */
public class ControlAcceso extends Regulador {

    /**
     * Construye el control de acceso con las tres reglas como lambdas ({@link IRegla}).
     *
     * @param autenticado  {@code true} si el usuario esta autenticado
     * @param tieneRol     {@code true} si el usuario tiene el rol requerido
     * @param dentroCuota  {@code true} si el usuario esta dentro de su cuota
     */
    public ControlAcceso(boolean autenticado, boolean tieneRol, boolean dentroCuota) {
        super(
                reglaQue(autenticado, "Usuario no autenticado"),
                reglaQue(tieneRol, "Usuario sin el rol requerido"),
                reglaQue(dentroCuota, "Usuario fuera de cuota")
        );
    }

    private static IRegla reglaQue(boolean condicion, String mensajeSiFalla) {
        return () -> {
            if (!condicion) {
                throw new AccesoDenegadoException(mensajeSiFalla);
            }
        };
    }
}
