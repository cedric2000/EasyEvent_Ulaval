package ca.easyevent.model;

import java.util.ArrayList;

import ca.easyevent.utils.DateModifiable;

public class Depense
{
	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/
	
	private String libelle = new String("");
	private DateModifiable date;
	
	private double montantTotal =0;
	private ArrayList<Participation> listeParticipation = new ArrayList<Participation>();

	
	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/
	
	public Depense(){}
	
	public Depense(String libelle, DateModifiable date){
		this.libelle = libelle;
		this.date = date;
	}
	
	
	public Depense(String libelle, DateModifiable date, ArrayList<Participation> listeParticipation) {
		super();
		this.libelle = libelle;
		this.date = date;
		this.listeParticipation = listeParticipation;
	}
	
	/*##############################################################################################
										AJOUT
	###############################################################################################*/


	public void addParticipation(Participation p){
		this.listeParticipation.add(p) ;
	}
	
	
	/*#################################################################################################
									CALCUL BUDGET TOTAL
	##################################################################################################*/
	
	/**
	 * Le montant total d'une d�pense est donn� par la somme des participations pour chaque participants
	 */
	public void calculMontantTotal()
	{
		this.montantTotal=0;
		for(Participation participation : this.listeParticipation )
			this.montantTotal += participation.getMontant();
	}
	
	/*################################################################################################
										ACCESSEUR
	##################################################################################################*/

	public String getLibelle() {
		return libelle;
	}
	
	public double getMontantTotal() {
		calculMontantTotal();
		return montantTotal;
	}
	
	public DateModifiable getDate() {
		return date;
	}
	
	public ArrayList<Participation> getListeParticipation() {
		return listeParticipation;
	}

	@Override
	public String toString() {
		return "Depense [libelle=" + libelle + ", date=" + date + ", montantTotal=" + montantTotal + "]";
	}
	
	/*################################################################################################
									MODIFICATEUR 
	##################################################################################################*/

	
	
	/*##############################################################################################
									DESCRIPTEUR 
	##############################################################################################*/
	
	
	

	
	
}
