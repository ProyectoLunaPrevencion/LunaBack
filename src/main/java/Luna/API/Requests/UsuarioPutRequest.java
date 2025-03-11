package Luna.API.Requests;

import Luna.API.Modelo.Usuario.Curso;
import Luna.API.Modelo.Usuario.Grupo;
import Luna.API.Modelo.Usuario.Rol;

public class UsuarioPutRequest {
    private String nombre;
    private String apellidos;
    private String telefono;
    private Curso curso;
    private Grupo grupo;
    private String email;
    private Rol rol;
    
    public UsuarioPutRequest( String nombre, String apellidos,  String telefono, Curso curso, Grupo grupo, String email, Rol rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.curso = curso;
        this.grupo = grupo;
        this.email = email;
        this.rol = rol;

    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public Curso getCurso() {
        return curso;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public String getEmail() {
        return email;
    }

    public Rol getRol() {
        return rol;
    }



    
}
