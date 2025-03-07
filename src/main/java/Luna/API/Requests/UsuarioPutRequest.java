package Luna.API.Requests;

import Luna.API.Modelo.Usuario.Curso;
import Luna.API.Modelo.Usuario.Grupo;

public class UsuarioPutRequest {
    private String nombre;
    private String apellidos;
    private String telefono;
    private Curso curso;
    private Grupo grupo;
    
    public UsuarioPutRequest( String nombre, String apellidos,  String telefono, Curso curso, Grupo grupo ) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.curso = curso;
        this.grupo = grupo;
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

    
}
