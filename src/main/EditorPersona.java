package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorPersona extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtNome, txtCognome, txtIndirizzo, txtTelefono, txtEta;
    private Rubrica rubrica;
    private Persona persona;
    private Main parent;

    public EditorPersona(Main parent, Persona persona, Rubrica rubrica) {
        this.persona = persona;
        this.rubrica = rubrica;
        this.parent = parent;

        setTitle("Editor Persona");
        setSize(300, 300);
        setModal(true);
        setLocationRelativeTo(parent);

        // Campi di input
        txtNome = new JTextField(20);
        txtCognome = new JTextField(20);
        txtIndirizzo = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtEta = new JTextField(20);

        if (persona != null) {
            txtNome.setText(persona.getNome());
            txtCognome.setText(persona.getCognome());
            txtIndirizzo.setText(persona.getIndirizzo());
            txtTelefono.setText(persona.getTelefono());
            txtEta.setText(String.valueOf(persona.getEta()));
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Cognome:"));
        panel.add(txtCognome);
        panel.add(new JLabel("Indirizzo:"));
        panel.add(txtIndirizzo);
        panel.add(new JLabel("Telefono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Età:"));
        panel.add(txtEta);

        // Pulsanti
        JButton btnSalva = new JButton("Salva");
        JButton btnAnnulla = new JButton("Annulla");

        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaPersona();
            }
        });

        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panelBottoni = new JPanel();
        panelBottoni.add(btnSalva);
        panelBottoni.add(btnAnnulla);

        add(panel, BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);
    }

    private void salvaPersona() {
        String nome = txtNome.getText();
        String cognome = txtCognome.getText();
        String indirizzo = txtIndirizzo.getText();
        String telefono = txtTelefono.getText();
        int eta = Integer.parseInt(txtEta.getText());

        if (persona == null) {
            persona = new Persona(nome, cognome, indirizzo, telefono, eta);
            rubrica.aggiungiPersona(persona);
        } else {
            persona.setNome(nome);
            persona.setCognome(cognome);
            persona.setIndirizzo(indirizzo);
            persona.setTelefono(telefono);
            persona.setEta(eta);
        }

        parent.aggiornaDati();
        dispose();
    }
}
