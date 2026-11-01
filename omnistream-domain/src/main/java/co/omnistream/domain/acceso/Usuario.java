package co.omnistream.domain.acceso;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Usuario de OmniStream con sus roles, para el control de acceso por usuario.
 */
public class Usuario {

    private final String id;
    private final String nombreUsuario;
    private final Set<String> roles;

    /**
     * @param nombreUsuario identificador legible del usuario
     * @param roles         roles asignados (ej. "LECTOR", "ADMIN")
     */
    public Usuario(String nombreUsuario, Set<String> roles) {
        this.id = UUID.randomUUID().toString();
        this.nombreUsuario = Objects.requireNonNull(nombreUsuario, "nombreUsuario");
        this.roles = Set.copyOf(roles);
    }

    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public Set<String> getRoles() {
        return roles;
    }

    /**
     * @param rol rol a verificar
     * @return {@code true} si el usuario tiene el rol
     */
    public boolean tieneRol(String rol) {
        return roles.contains(rol);
    }
}
