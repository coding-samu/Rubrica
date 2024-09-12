package main;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;

public class Rubrica {
    private Vector<Persona> persone;

    public Rubrica() {
        persone = new Vector<>();
    }

    public void aggiungiPersona(Persona p) {
        persone.add(p);
    }

    public void rimuoviPersona(int index) {
        persone.remove(index);
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public void salvaSuFile(String path) {
        try (PrintStream ps = new PrintStream(new File(path))) {
            for (Persona p : persone) {
                ps.println(p.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void caricaDaFile(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String[] dati = scanner.nextLine().split(";");
                Persona p = new Persona(dati[0], dati[1], dati[2], dati[3], Integer.parseInt(dati[4]));
                persone.add(p);
            }
        } catch (Exception e) {
            // In caso di errore, ignora il caricamento
        }
    }
}
