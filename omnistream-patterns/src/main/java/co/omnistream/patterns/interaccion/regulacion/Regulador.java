package co.omnistream.patterns.interaccion.regulacion;

/**
 * Patron-R Regulacion (re-significado del Template Method de GoF).
 *
 * <p>Problema: aplicar unas condiciones fijas hace que los modulos queden atados a ellas,
 * obligando a extensiones excesivas y modelos confusos.</p>
 *
 * <p>Hipotesis: las condiciones pueden manipularse como reglas establecidas en interfaces; una
 * extension puede aplicar las reformas que correspondan.</p>
 *
 * <p>En OmniStream modela el <b>control de acceso</b>: las reglas (autenticado, autorizado,
 * dentro de cuota) se evaluan como una plantilla fija cuyos pasos son reglas intercambiables.</p>
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public class Regulador {

    private final IRegla reglaUno;
    private final IRegla reglaDos;
    private final IRegla reglaTres;

    /**
     * @param reglaUno  primera regla
     * @param reglaDos  segunda regla
     * @param reglaTres tercera regla
     */
    public Regulador(IRegla reglaUno, IRegla reglaDos, IRegla reglaTres) {
        this.reglaUno = reglaUno;
        this.reglaDos = reglaDos;
        this.reglaTres = reglaTres;
    }

    /**
     * Plantilla fija: regula ejecutando las tres reglas en orden.
     */
    public final void regular() {
        servicioUno();
        servicioDos();
        servicioTres();
    }

    /** Paso 1 (redefinible). */
    protected void servicioUno() {
        if (reglaUno != null) {
            reglaUno.aplicar();
        }
    }

    /** Paso 2 (redefinible). */
    protected void servicioDos() {
        if (reglaDos != null) {
            reglaDos.aplicar();
        }
    }

    /** Paso 3 (redefinible). */
    protected void servicioTres() {
        if (reglaTres != null) {
            reglaTres.aplicar();
        }
    }
}
