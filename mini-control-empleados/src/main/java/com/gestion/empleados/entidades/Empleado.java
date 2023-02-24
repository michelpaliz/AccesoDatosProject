package com.gestion.empleados.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestion.empleados.servicio.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleado")
public class Empleado {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_empleado", nullable = false)
    private Long id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "salario")
    private double salario;
    @Basic
    @Column(name = "sexo")
    private String sexo;
    @Basic
    @Column(name = "telefono")
    private int telefono;

    //Este campo está enlazado con un atributo y con la propiedad one-to-many
    @Basic
    @Column(name = "id_departamento", nullable = false)
    @JoinColumn(name = "id_departamento")
    private int idDepartamento;

    //
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


    //Esta lista indica que un empleado tiene muchos mensajes
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idEmpleado", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("empleado")
    private List<Mensajeria> mensajeriaList = new ArrayList<>();


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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public List<Mensajeria> getMensajeriaList() {
        return mensajeriaList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setMensajeriaList(List<Mensajeria> mensajeriaList) {
        this.mensajeriaList = mensajeriaList;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }





}
