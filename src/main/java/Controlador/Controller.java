/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.LOGIN;
import Vista.Vista_Menu;
import Vista.Vista_Registrarse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tfc.escritorio.tfc_escritorio.Modelo.Jugador;
import com.tfc.escritorio.tfc_escritorio.Modelo.Usuario;
import com.tfc.escritorio.tfc_escritorio.TFC_Escritorio;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author irnieto
 */
public class Controller {

    static Vista_Menu menu = new Vista_Menu();
    static Vista_Registrarse registrarse = new Vista_Registrarse();
    static LOGIN login = new LOGIN();
    static DefaultListModel<Jugador> model = new DefaultListModel<>();

    public Controller() {

    }

    public void iniciarVentanaPrincipal() {
        menu.setVisible(true);

    }

    public void iniciarVentanaRegistrarse() {
        registrarse.setVisible(true);

    }

    public void iniciarVentanaLogin() {
        login.setVisible(true);

    }

    public void busqueda(JList lista, JTextField txtBuscar) {
        try {
            model.clear();
            lista.setModel(model);
            String endPointUrl = "https://proyectotfciagorod.zeabur.app//jugadores/buscar?nombre=" + txtBuscar.getText();
            URL url = new URL(endPointUrl);
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();

            connection.disconnect();
            ObjectMapper mapper = new ObjectMapper();

            List<Jugador> dataList = mapper.readValue(builder.toString(), new TypeReference<List<Jugador>>() {
            });
            for (Jugador jugador : dataList) {
                model.addElement(jugador);
            }
            lista.setModel(model);
        } catch (MalformedURLException ex) {
            Logger.getLogger(TFC_Escritorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TFC_Escritorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean login(String login, String password) {

        try {
            String endpoint = "http://localhost:8080/usuarios/login?login=" + URLEncoder.encode(login, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response);

                boolean loginResult = Boolean.parseBoolean(response.toString());
                System.out.println(loginResult);
                return loginResult;
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void registrarse(String login, String correo, String contraseña) {
        Usuario usuario = new Usuario(login, correo, contraseña, 2);
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(usuario);
    }

    public void eliminar(JList jlist) {
        try {
            Jugador jugador = (Jugador) jlist.getSelectedValue();
            String endpoint = "http://localhost:8080/jugadores?id=" + URLEncoder.encode(jugador.getIdJugador().toString(), "UTF-8");
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            System.out.println(connection.getResponseCode());
            connection.disconnect();

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException | ProtocolException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
