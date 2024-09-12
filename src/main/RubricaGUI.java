package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RubricaGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private Rubrica rubrica;
    private JTable tabellaPersone;
    private DefaultTableModel modelloTabella;

    public RubricaGUI() {
        rubrica = new Rubrica();
        rubrica.caricaDaFile("informazioni.txt");

        setTitle("Rubrica");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Modello per la JTable
        String[] colonne = {"Nome", "Cognome", "Telefono"};
        modelloTabella = new DefaultTableModel(colonne, 0);
        tabellaPersone = new JTable(modelloTabella);
        JScrollPane scrollPane = new JScrollPane(tabellaPersone);

        // Popolamento tabella
        aggiornaTabella();

        // Pulsanti
        JButton btnNuovo = new JButton("Nuovo");
        JButton btnModifica = new JButton("Modifica");
        JButton btnElimina = new JButton("Elimina");

        // Ascoltatori per i pulsanti
        btnNuovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostraEditorPersona(null);
            }
        });

        btnModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabellaPersone.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona una persona da modificare.");
                } else {
                    Persona p = rubrica.getPersone().get(selectedRow);
                    mostraEditorPersona(p);
                }
            }
        });

        btnElimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabellaPersone.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona una persona da eliminare.");
                } else {
                    Persona p = rubrica.getPersone().get(selectedRow);
                    int conferma = JOptionPane.showConfirmDialog(null, "Eliminare la persona " + p.getNome() + " " + p.getCognome() + "?");
                    if (conferma == JOptionPane.YES_OPTION) {
                        rubrica.rimuoviPersona(selectedRow);
                        aggiornaTabella();
                        rubrica.salvaSuFile("informazioni.txt");
                    }
                }
            }
        });

        // Layout principale
        JPanel panelPulsanti = new JPanel();
        panelPulsanti.add(btnNuovo);
        panelPulsanti.add(btnModifica);
        panelPulsanti.add(btnElimina);

        add(scrollPane, BorderLayout.CENTER);
        add(panelPulsanti, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void aggiornaTabella() {
        modelloTabella.setRowCount(0);
        for (Persona p : rubrica.getPersone()) {
            modelloTabella.addRow(new Object[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }
    }

    private void mostraEditorPersona(Persona persona) {
        EditorPersona editor = new EditorPersona(this, persona, rubrica);
        editor.setVisible(true);
    }

    public void aggiornaDati() {
        aggiornaTabella();
        rubrica.salvaSuFile("informazioni.txt");
    }

    public static void main(String[] args) {
        new RubricaGUI();
    }
}
