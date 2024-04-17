/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfc.escritorio.tfc_escritorio.Modelo;

/**
 *
 * @author irnieto
 */
public class Usuario {

    private Integer idUsuario;
    private String login;
    private String correo;
    private String contraseña;
    private Integer cod_Rol; 
    
    

    public Usuario(String login, String email, String contraseña, Integer codRol) {
        this.login = login;
        this.correo = email;
        this.contraseña = contraseña;
        this.cod_Rol=codRol;
    }

    public Integer getCodRol() {
        return cod_Rol;
    }

    public void setCodRol(Integer codRol) {
        this.cod_Rol = codRol;
    }
    
   
    
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return correo;
    }

    public void setEmail(String email) {
        this.correo = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
