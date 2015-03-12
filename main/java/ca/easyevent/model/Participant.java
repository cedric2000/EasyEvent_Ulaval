package ca.easyevent.model;

import java.util.ArrayList;


public class Participant {


	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/
	
	private String nom = new String(""); 
	private String telephone = new String(""); 
	private String mail = new String(""); 
		
	private float BudgetPersoTotal = 0;
	private float equilibrePersoTotal = 0;
	
	private ArrayList<Participation> listeParticipation = new ArrayList<Participation>();
	

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/
	
	public Participant() {}
	
	public Participant(String name) {
		this.nom = name;
	}
	
	public Participant(String name, String telephone, String mail) {
		this.nom = name;
		this.telephone = telephone;
		this.mail = mail;
	}
	
	/*##############################################################################################
									AJOUT PARTICIPATION
	###############################################################################################*/

	public void addParticipation(Participation p){
		this.listeParticipation.add(p) ;
	}
	

	/*##############################################################################################
								CALCUL 
	##############################################################################################*/
	
	public void calculBudgetPersoTotal()
	{
		this.BudgetPersoTotal = 0;
		for(Participation participation : this.listeParticipation )
			this.BudgetPersoTotal += participation.getMontant();
	}

	public void calculEquiPersoTotal()
	{
		this.equilibrePersoTotal = 0;
		for(Participation participation : this.listeParticipation )
			this.equilibrePersoTotal += participation.getBalance();
	}
	

	/*##############################################################################################
									ACCESSEUR 
	##############################################################################################*/
	
	public String getName() {
		return nom;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public String getMail() {
		return mail;
	}

	public float getBudgetPersoTotal() {
		calculBudgetPersoTotal();
		return BudgetPersoTotal;
	}
	
	public ArrayList<Participation> getListeParticipation() {
		return listeParticipation;
	}
	
	public float getEquiPersoTotal()
	{
		calculEquiPersoTotal();
		return equilibrePersoTotal;
	}


	
	/*##############################################################################################
									MODIFICATEUR 
	##############################################################################################*/
	
	
	
	/*##############################################################################################
									DESCRIPTEUR 
	##############################################################################################*/
	
	@Override
	public String toString() {
		return "Participant [nom=" + nom + ", telephone=" + telephone
				+ ", mail=" + mail + ", BudgetPersoTotal=" + BudgetPersoTotal
				+ ", equilibrePersoTotal=" + equilibrePersoTotal + "]";
	}
	

}
