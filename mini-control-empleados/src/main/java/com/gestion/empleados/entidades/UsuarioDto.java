package com.gestion.empleados.entidades;

public class UsuarioDto {

    private String nombre;
    private String email;
    private String password;
    private String tipo;

    private Empleado empleado;

    public UsuarioDto() {
    }

    public UsuarioDto(String nombre, String email, String password, String tipo, Empleado empleado) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
        this.empleado = empleado;
    }


    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
