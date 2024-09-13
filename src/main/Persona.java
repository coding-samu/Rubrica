package main;

public class Persona {
	
	private String nome;
	private String cognome;
	private String indirizzo;
	private String telefono;
	private int eta;
	private int id;
	
	public Persona(String nome, String cognome, String indirizzo, String telefono, int eta, int id) {
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eta = eta;
		this.id = id;
	}
	
	public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eta = eta;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public String getIndirizzo() {
		return this.indirizzo;
	}
	
	public String getTelefono() {
		return this.telefono;
	}
	
	public int getEta() {
		return this.eta;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setEta(int eta) {
		this.eta = eta;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.nome + ";" + this.cognome + ";" + this.indirizzo + ";" + this.telefono + ";" + this.eta;
	}
}
