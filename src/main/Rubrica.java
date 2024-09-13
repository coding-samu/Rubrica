package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Properties;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Rubrica {
    private Vector<Persona> persone;
    private Connection conn;
    private String url;
    private String username;
    private String password;

    public Rubrica() {
        persone = new Vector<>();
        loadProperties("credenziali_database.properties");
        conn = getConnection();
        inizializzaDatabase();
        caricaDaDatabase();
    }

    // Metodo per caricare le proprietà dal file
    private Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }
    
    // Metodo per ottenere la connessione al database
    public Connection getConnection() {
        if (conn == null) {
            try {
                // Tentativo di connessione
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Connessione al database eseguita correttamente.");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    // Metodo per controllare se il database esiste
    public void inizializzaDatabase() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS rubrica_db");
            stmt.execute("USE rubrica_db");

            // Esecuzione del file SQL
            eseguiScriptSQL("schema_database.sql");
            
        } catch (Exception e) {
            System.out.println("Database già esistente.");
        }
    }

    // Metodo per eseguire il file schema_database.sql
    private void eseguiScriptSQL(String filePath) {
        try {
            // Carica il file SQL e leggi ogni riga
            Scanner scanner = new Scanner(new File(filePath));
            StringBuilder sqlQuery = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("--")) {
                    sqlQuery.append(line);
                    // Se la linea termina con un punto e virgola, è la fine della query
                    if (line.endsWith(";")) {
                        try (Statement stmt = conn.createStatement()) {
                            stmt.execute(sqlQuery.toString());
                        }
                        sqlQuery.setLength(0); // Resetta il buffer
                    }
                }
            }
            scanner.close();
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Tabelle già inserite nel database.");
        }
    }

    // Metodo per aggiungere una persona al database
    public void aggiungiPersona(Persona p) {
        String query = "INSERT INTO persona (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getEta());
            ps.executeUpdate();
            System.out.println(p.getNome() + " " + p.getCognome() + " aggiunto al database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per rimuovere una persona dal database
    public void rimuoviPersona(Persona p) {
        String query = "DELETE FROM persona WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            System.out.println(p.getNome() + " " + p.getCognome() + " rimosso dal database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Metodo per aggiornare una persona nel database
    public void aggiornaPersona(Persona p) {
        String query = "UPDATE persona SET nome = ?, cognome = ?, indirizzo = ?, telefono = ?, eta = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getEta());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per caricare le persone dal database
    public void caricaDaDatabase() {
        persone.clear();  // Pulisce la lista prima di caricare dal database
        String query = "SELECT * FROM persona";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta"),
                        rs.getInt("id")
                );
                persone.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public void chiudiConnessione() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

