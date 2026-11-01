package co.omnistream.patterns.interaccion.difusionrecoleccion;

import java.util.ArrayList;
import java.util.List;

/**
 * Patron-R Difusion-Recoleccion (re-significado del Observer de GoF).
 *
 * <p>Problema: en una interaccion masiva entre modulos se produce facilmente una marana de
 * canales de comunicacion, incrementando exponencialmente el flujo de mensajes.</p>
 *
 * <p>Hipotesis: generar un difusor que envie controladamente el mensaje a todos sus abonados;
 * y un recolector que recoja mensajes aprovechando el mismo esquema.</p>
 *
 * <p>En OmniStream notifica <b>eventos de indexacion y procesamiento</b> (recurso indexado,
 * metadatos extraidos, duplicado detectado) a los modulos interesados.</p>
 *
 * @author Framework P-rS - Sandro J. Bolanos (re-significado para OmniStream)
 */
public abstract class AAbonado implements IDifusor, IRecolector {

    /** Lista de abonados compartida entre todos los que comparten el mismo "director". */
    protected List<AAbonado> abonados;

    /**
     * Crea un abonado "director" que inicia su propia lista de abonados.
     */
    protected AAbonado() {
        abonados = new ArrayList<>();
        abonados.add(this);
    }

    /**
     * Crea un abonado que se une a la lista de un director existente.
     *
     * @param director abonado cuyo canal se comparte
     */
    protected AAbonado(AAbonado director) {
        abonados = director.abonados;
        abonados.add(this);
    }

    /**
     * Incluye un abonado en el canal.
     *
     * @param abonado nuevo abonado
     */
    public void incluirAbonado(AAbonado abonado) {
        abonados.add(abonado);
    }

    /**
     * Saca un abonado del canal.
     *
     * @param abonado abonado a retirar
     */
    public void sacarAbonado(AAbonado abonado) {
        abonados.remove(abonado);
    }

    /**
     * Difunde a todos los abonados (excepto a si mismo).
     */
    @Override
    public void difundir() {
        for (AAbonado abonado : abonados) {
            if (abonado != this) {
                abonado.recibir(this);
            }
        }
    }

    /**
     * Recibe una difusion de otro abonado. Las especializaciones redefinen la reaccion.
     *
     * @param emisor abonado que difunde
     */
    protected void recibir(AAbonado emisor) {
        // Comportamiento por defecto: las subclases concretas lo redefinen segun su interes.
    }

    /**
     * Recolecta de todos los abonados (excepto de si mismo).
     */
    @Override
    public void recolectar() {
        for (AAbonado abonado : abonados) {
            if (abonado != this) {
                procesar(abonado.entregar());
            }
        }
    }

    /**
     * Entrega la contribucion de este abonado durante una recoleccion.
     *
     * @return objeto entregado (por defecto {@code null})
     */
    protected Object entregar() {
        return null;
    }

    /**
     * Procesa lo entregado por un abonado durante la recoleccion.
     *
     * @param entregado objeto recolectado
     */
    protected void procesar(Object entregado) {
        // Comportamiento por defecto: las subclases concretas lo redefinen.
    }
}
