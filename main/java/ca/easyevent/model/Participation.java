package ca.easyevent.model;


public class Participation {



	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/
	
	private Participant participant = new Participant();
	private Depense depense = new Depense();

	private double montant;
	private double tauxParticipation;
	private double equilibre;
	

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/
	
	public Participation(){}
	
	
	public Participation(Participant participant, Depense depense, double montant,double tauxParticipation){
		this.participant = participant;
		this.depense = depense;
		this.montant = montant;
		this.tauxParticipation =tauxParticipation;
		calculEquilibre();
	}


	/*##############################################################################################
									CALCUL
	###############################################################################################*/
	
	public void calculEquilibre(){
		this.equilibre = this.montant - (this.tauxParticipation/100)*this.depense.getMontantTotal();
	}
	

	/*##############################################################################################
									ACCESSEUR
	###############################################################################################*/
	
	public Participant getParticipant() {
		return participant;
	}
	
	public Depense getDepense() {
		return depense;
	}
	
	public double getMontant() {
		return montant;
	}

	public double getTauxParticipation() {
		return tauxParticipation;
	}
	
	public double getBalance() {
		calculEquilibre();
		return equilibre;
	}



	/*##############################################################################################
									MODIFICATEUR
	###############################################################################################*/

	public void setDepense(Depense newDepense) {
		// TODO Auto-generated method stub
		
	}
}
