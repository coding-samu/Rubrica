package model;

import java.util.ArrayList;

public class ModelRubricaTelefonica {
	
	private ArrayList<ModelPersona> rubrica = new ArrayList<ModelPersona>();
	
	public ArrayList<ModelPersona> getRubrica() {
		return rubrica;
	}
	
	public boolean removeFromRubrica(ModelPersona persona) {
		return this.rubrica.remove(persona);
	}
	
	public boolean addToRubrica(ModelPersona persona) {
		return this.rubrica.add(persona);
	}
}
