/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.LOGIN;
import Vista.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tfc.escritorio.tfc_escritorio.Modelo.Equipo;
import com.tfc.escritorio.tfc_escritorio.Modelo.Jugador;
import com.tfc.escritorio.tfc_escritorio.Modelo.Liga;
import com.tfc.escritorio.tfc_escritorio.Modelo.Usuario;
import com.tfc.escritorio.tfc_escritorio.TFC_Escritorio;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author irnieto
 */
public class Controller {
    
    static Vista_Registrarse registrarse = new Vista_Registrarse();
    static Vista_Insertar_Equipo_Liga gestionEquipoLiga = new Vista_Insertar_Equipo_Liga(null,true);
    static LOGIN login = new LOGIN();
    static Vista_Crear_Liga crear = new Vista_Crear_Liga(null, true);
    static Vista_Ligas ligas = new Vista_Ligas(null, true);
    static Vista_Traspasos traspasos = new Vista_Traspasos(null, true);
    static MenuPrincipal menu = new MenuPrincipal(null, true);
    static DefaultListModel<Jugador> model = new DefaultListModel<>();
    static DefaultListModel<Equipo> modeloListaEquipo2 = new DefaultListModel<>();
    static DefaultListModel<Equipo> modeloListaEquipo = new DefaultListModel<>();
    static DefaultComboBoxModel<Liga> modeloComboLigas = new DefaultComboBoxModel<>();
    static DefaultListModel<Liga> modeloListaEquipoenLiga = new DefaultListModel<>();
    
    public Controller() {
        
    }
    
    public void iniciarGestionEquipoLigas() {
        gestionEquipoLiga.setVisible(true);
    }
    
    public void iniciarVentanaTraspasos(){
        traspasos.setVisible(true);
    }
    
    
    public void iniciarVentanaGestionLigas() {
        ligas.setVisible(true);
    }
    
    public void gestionEquiposLigas() {
        
    }
    
    public void iniciarVentanaRegistrarse() {
        registrarse.setVisible(true);
        
    }
    
    public void iniciarVentanaLogin() {
        login.setVisible(true);
        
    }
    
    public void IniciarVentanaCrearLiga() {
        crear.setVisible(true);
    }
    
    public void busqueda(JList lista, JTextField txtBuscar) {
        try {
            model.clear();
            lista.setModel(model);
            String endPointUrl = "http://192.168.2.211:8080/jugadores/buscar?nombre=" + URLEncoder.encode(txtBuscar.getText(), "UTF-8");
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
            
            String endpoint = "http://192.168.2.211:8080/usuarios/login?login=" + URLEncoder.encode(login, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                StringBuilder response;
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                return true;
            }
            
            if (responseCode == 350) {
                return false;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }
    
    public void registrarse(String login, String correo, String contraseña) {
        try {
            Usuario usuario = new Usuario(login, correo, contraseña, 2, "");
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(usuario);
            // URL del endpoint
            URL url = new URL("http://192.168.2.211:8080/usuarios");

            // Abrir conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Configurar la solicitud HTTP
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            System.out.println(json);
            try (OutputStream os = connection.getOutputStream()) {
                
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
        } catch (Exception e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
            
        }
        
    }
    
    public void cargarComboBox(JComboBox combo) {
        try {
            String endPointUrl = "http://192.168.2.211:8080/liga";
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
            
            List<Liga> dataList = mapper.readValue(builder.toString(), new TypeReference<List<Liga>>() {
            });
            for (Liga equipo : dataList) {
                modeloComboLigas.addElement(equipo);
            }
            combo.setModel(modeloComboLigas);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminar(JList jlist) {
        try {
            Jugador jugador = (Jugador) jlist.getSelectedValue();
            if (jugador != null) {
                String endpoint = "http://192.168.2.211:8080/jugadores?id=" + URLEncoder.encode(jugador.getId_jugador().toString(), "UTF-8");
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                System.out.println(connection.getResponseCode());
                
                connection.disconnect();
                JOptionPane.showMessageDialog(null, "Elemento eliminado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un elemento de la lista.");
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException | ProtocolException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void cargarListaEquipos(JList lista) {
        try {
            String endPointUrl = "http://192.168.2.211:8080/equipos";
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
            
            List<Equipo> dataList = mapper.readValue(builder.toString(), new TypeReference<List<Equipo>>() {
            });
            for (Equipo equipo : dataList) {
                modeloListaEquipo.addElement(equipo);
            }
            lista.setModel(modeloListaEquipo);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void añadirEquipoA1liga(JList listaEquipos, JComboBox comboLigas, JList equiposEnLaLiga) {
        try {
            
            Liga ligaSeleccionada = (Liga) comboLigas.getSelectedItem();
            Equipo equipoSeleccionado = (Equipo) listaEquipos.getSelectedValue();
            String endpointURL = "http://192.168.2.211:8080/liga/insertar?codEquipo=" + URLEncoder.encode(equipoSeleccionado.getIdEquipo().toString(), "UTF-8") + "&codLiga=" + URLEncoder.encode(ligaSeleccionada.getCodLiga().toString(), "UTF-8");
            URL url = new URL(endpointURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(null, "El equipo ha sido añadido correctamente.");
                
            } else {
                JOptionPane.showMessageDialog(null, "El equipo no se ha podido insertar porque ya existe en la liga.");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verEquipos1LigaDelComboBox(JComboBox combo, JList listaEquiposEn1Liga) {
        try {
            modeloListaEquipo2.clear();
            Liga seleccionada = (Liga) combo.getSelectedItem();
            String endPointUrl = "http://192.168.2.211:8080/equipos/liga?codLiga=" + URLEncoder.encode(seleccionada.getCodLiga().toString(), "UTF-8");
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
            
            List<Equipo> dataList = mapper.readValue(builder.toString(), new TypeReference<List<Equipo>>() {
            });
            for (Equipo equipo : dataList) {
                modeloListaEquipo2.addElement(equipo);
            }
            listaEquiposEn1Liga.setModel(modeloListaEquipo2);
            
        } catch (Exception e) {
            
        }
    }
    
    public void eliminarEquipoDe1Liga(JComboBox comboLigas, JList listaEquiposEn1Liga) {
        try {
            Liga ligaSeleccionada = (Liga) comboLigas.getSelectedItem();
            Equipo equipoSeleccionado = (Equipo) listaEquiposEn1Liga.getSelectedValue();
            if (equipoSeleccionado != null) {
                String endpoint = "http://192.168.2.211:8080/liga/eliminar?codEquipo=" + URLEncoder.encode(equipoSeleccionado.getIdEquipo().toString(), "UTF-8") + "&codLiga=" + URLEncoder.encode(ligaSeleccionada.getCodLiga().toString(), "UTF-8");
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                
                if (connection.getResponseCode() == 200) {
                    JOptionPane.showMessageDialog(null, "Equipo eliminado de la liga con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el equipo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona un elemento de la lista de equipos.");
            }
        } catch (Exception e) {
            
        }
    }
    
    public void crearLiga(JTextField texto, JCheckBox checkbox) {
        try {
            boolean nacional;
            nacional = checkbox.isSelected();
            Liga liga = new Liga(texto.getText(), nacional);
            
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(liga);
            // URL del endpoint
            URL url = new URL("http://192.168.2.211:8080/liga/crear");

            // Abrir conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Configurar la solicitud HTTP
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            try (OutputStream os = connection.getOutputStream()) {
                
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (connection.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(null, "Liga registrada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la liga");
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void cargarListaLigas(JList lista) {
        try {
            String endPointUrl = "http://192.168.2.211:8080/liga";
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
            
            List<Liga> dataList = mapper.readValue(builder.toString(), new TypeReference<List<Liga>>() {
            });
            for (Liga liga : dataList) {
                modeloListaEquipoenLiga.addElement(liga);
            }
            lista.setModel(modeloListaEquipoenLiga);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarLiga(Liga liga, JTextField texto, JCheckBox combo) {
        try {
            String enlace = "http://192.168.2.211:8080/liga";
            Liga nuevaLiga = new Liga(liga.getCodLiga(), texto.getText(), combo.isSelected());
            URL url = new URL(enlace);
            
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(nuevaLiga);
            // URL del endpoint

            // Abrir conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Configurar la solicitud HTTP
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            try (OutputStream os = connection.getOutputStream()) {
                
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (connection.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(null, "Liga modificadaa con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar la liga");
            }
            
        } catch (MalformedURLException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
            
        } catch (ProtocolException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarJugadoresD1Equipo() {
        
    }
    
    public void traspasar1Jugador(JList jlist, JComboBox combo) {
        try {
            Integer codEquipo = ((Equipo) combo.getSelectedItem()).getIdEquipo();
            Integer idJugador = ((Jugador) jlist.getSelectedValue()).getId_jugador();
            
            String enlace = "http://192.168.2.211:8080/jugadores/traspaso?codJugador=" + URLEncoder.encode(Integer.toString(idJugador), "UTF-8") + "&codEquipo=" + URLEncoder.encode(Integer.toString(codEquipo), "UTF-8");
            URL url = new URL(enlace);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            if (connection.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(null, "Jugador traspasado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido realizar el traspaso.");
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void descargarPDF() {
        String pdfUrl = "http://192.168.2.211:8080/jugadores/exportpdf";
        
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(pdfUrl));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public void eliminarLiga(JList listaLigas) {
        Liga liga = (Liga) listaLigas.getSelectedValue();
        if (liga != null) {
            try {
                String url = "http://192.168.2.211:8080/liga?codLiga=" + URLEncoder.encode(liga.getCodLiga().toString(), "UTF-8");
                URL enlace = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) enlace.openConnection();
                connection.setRequestMethod("DELETE");
                if (connection.getResponseCode() == 200) {
                    JOptionPane.showMessageDialog(null, "La liga se ha eliminado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido eliminar la liga.");
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una liga");
        }
    }
    
}
