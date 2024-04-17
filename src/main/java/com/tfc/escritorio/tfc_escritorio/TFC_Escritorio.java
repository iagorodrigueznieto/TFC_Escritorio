/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.tfc.escritorio.tfc_escritorio;

import Controlador.Controller;

/**
 *
 * @author irnieto
 */
public class TFC_Escritorio {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
            controller.iniciarVentanaLogin();
        } catch (Exception e) {

        }

    }
}
