/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfc.escritorio.tfc_escritorio.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author irnieto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idJugador;

    private String nombre;

    private Date fechaNacimiento;

    private Integer idEquipo;

    private Integer tarjetasAmarillas;

    private Integer tarjetasRojas;

    private Integer partidosJugados;

    private Integer goles;

    private Integer asistencias;

    private String imagen;

    private Integer codPosicion;

    @Override
    public String toString() {
        return nombre;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIdJugador() {
        return idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public Integer getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public Integer getTarjetasRojas() {
        return tarjetasRojas;
    }

    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public Integer getGoles() {
        return goles;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public String getImagen() {
        return imagen;
    }

    public Integer getCodPosicion() {
        return codPosicion;
    }

}
