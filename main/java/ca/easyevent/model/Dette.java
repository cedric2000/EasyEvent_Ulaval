package ca.easyevent.model;

public class Dette {
	
	Participant creancier, debiteur;
	double valeur;
	
	
	public Dette(Participant creancier, Participant debiteur, double valeur) {
		super();
		this.creancier = creancier;
		this.debiteur = debiteur;
		this.valeur = valeur;
	}

	
	public String toString(){
		return debiteur.getName() + " doit " + Math.round(valeur) + " a " + creancier.getName();
	}

	public Participant getCreancier() {
		return creancier;
	}

	public void setCreancier(Participant creancier) {
		this.creancier = creancier;
	}
	
	public Participant getDebiteur() {
		return debiteur;
	}


	public void setDebiteur(Participant debiteur) {
		this.debiteur = debiteur;
	}


	public double getValeur() {
		return valeur;
	}


	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	
	
}
