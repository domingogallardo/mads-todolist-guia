package models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    @ManyToMany
    @JoinTable(name = "Equipo_Usuario",
        joinColumns = { @JoinColumn(name = "fk_equipo") },
        inverseJoinColumns = {@JoinColumn(name = "fk_usuario")})
    private Set<Usuario> usuarios = new HashSet<>();

    // Un constructor vacío necesario para JPA
    public Equipo() {
    }

    // El constructor principal con los campos obligatorios
    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    // Métodos de actualización de la relación muchos-a-muchos con usuario

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        usuario.getEquipos().add(this);
    }

    public void removeUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        usuario.getEquipos().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Equipo other = (Equipo) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return ((long) id == (long) other.id);
        // sino comparamos por campos obligatorios
        return nombre.equals(other.nombre);
    }

    @Override
    public int hashCode() {
        // Devolvemos el hash de los campos obligatorios
        return Objects.hash(nombre);
    }
}