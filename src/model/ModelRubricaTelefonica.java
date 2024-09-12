package model;

import java.util.ArrayList;

public class ModelRubricaTelefonica {
	
	private ArrayList<ModelPersona> rubrica = new ArrayList<ModelPersona>();
	
	public ModelRubricaTelefonica() {
		// TODO: Qui si dovrà implementare la lettura del file informazioni.txt
	}
	
	public ArrayList<ModelPersona> getRubrica() {
		return rubrica;
	}
	
	public void removeFromRubrica(ModelPersona persona) {
		this.rubrica.remove(persona);
		aggiornaFileInformazioni();
	}
	
	public void addToRubrica(ModelPersona persona) {
		this.rubrica.add(persona);
		aggiornaFileInformazioni();
	}
	
	public void aggiornaFileInformazioni() {
		// TODO: metodo per aggiornare il file informazioni.txt
	}
}
