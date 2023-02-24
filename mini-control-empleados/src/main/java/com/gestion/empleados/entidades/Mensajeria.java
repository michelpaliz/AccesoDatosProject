package com.gestion.empleados.entidades;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "mensajeria")
public class Mensajeria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_mensajeria", nullable = false)
    private int idMensajeria;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 45)
    private String descripcion;
    @Basic
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Basic
    @Column(name = "id_departamento", nullable = false)
    private int idDepartamento;

    //Este atributo está enlazado con el one-to-many de la entidad de empleado
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado idEmpleado;

    public Mensajeria() {
    }

    public Mensajeria(String descripcion, Date fecha, int idDepartamento, Empleado idEmpleado) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idDepartamento = idDepartamento;
        this.idEmpleado = idEmpleado;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }


    public int getIdMensajeria() {
        return idMensajeria;
    }

    public void setIdMensajeria(int idMensajeria) {
        this.idMensajeria = idMensajeria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

}
