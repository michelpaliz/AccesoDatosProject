package com.gestion.empleados.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departamento")
public class Departamento {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id_departamento" , nullable = false)
    private Long idDepartamento;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;

    //Un departamento tiene muchos empleados
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idDepartamento", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("departamento")
    private List<Empleado> empleadoList = new ArrayList<>();

    public Departamento() {
    }

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "idDepartamento=" + idDepartamento +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", empleadoList=" + empleadoList +
                '}';
    }
}
