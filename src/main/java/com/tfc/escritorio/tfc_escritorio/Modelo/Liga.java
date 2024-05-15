/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfc.escritorio.tfc_escritorio.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author irnieto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Liga implements Serializable{

    public Integer getCodLiga() {
        return codLiga;
    }

    public void setCodLiga(Integer codLiga) {
        this.codLiga = codLiga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Liga(Integer codLiga, String nombre, Boolean nacional) {
        this.codLiga = codLiga;
        this.nombre = nombre;
        this.nacional = nacional;
    }

    public Liga() {
    }
    

    
    public Boolean getNacional() {
        return nacional;
    }

    public void setNacional(Boolean nacional) {
        this.nacional = nacional;
    }

    @Override
    public String toString() {
        return nombre;
    }
    

    public Liga(String nombre, Boolean nacional) {
        this.nombre = nombre;
        this.nacional = nacional;
    }
    private Integer codLiga;

    private String nombre;

    private Boolean nacional;
    
}
