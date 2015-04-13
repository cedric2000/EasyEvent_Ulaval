package ca.easyevent.model;

import java.util.ArrayList;

import ca.easyevent.utils.DateModifiable;

public class Depense implements Comparable
{
	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private long id;
	private String libelle = new String("");
    private double montantTotal =0;
    private int nbParticipant= 1;

	private DateModifiable date;
	private ArrayList<Participation> listeParticipation = new ArrayList<>();

	
	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/
	
	public Depense(){}
	
	public Depense(String libelle, DateModifiable date){
		this.libelle = libelle;
		this.date = date;
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
	 * Le montant total d'une dépense est donné par la somme des participations pour chaque participants
	 */
	public void calculMontantTotal()
	{
		this.montantTotal=0;
		for(Participation participation : this.listeParticipation )
            this.montantTotal += participation.getMontant();
	}
/*
    public void calculEquilibreWithPropag()
    {
        calculMontantTotal();
        for(Participation participation : this.listeParticipation )
            participation.();
    }
	**/
	/*################################################################################################
										ACCESSEUR
	##################################################################################################*/

	public String getLibelle() {
		return libelle;
	}
	
	public double getMontantTotal() {
		return montantTotal;
	}

    public int getNbParticipants() {
        return nbParticipant;
    }
	
	public DateModifiable getDate() {
		return date;
	}
	
	public ArrayList<Participation> getListeParticipation() {
		return listeParticipation;
	}

    public long getId() {
        return id;
    }

    /*################################################################################################
									MODIFICATEUR 
	##################################################################################################*/

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDate(DateModifiable date) {
        this.date = date;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNbParticipant(int nbParticipant) {
        this.nbParticipant = nbParticipant;
    }

    /*##############################################################################################
									DESCRIPTEUR 
	##############################################################################################*/

    @Override
    public String toString() {
        return "Depense [libelle=" + libelle + ", date=" + date + ", montantTotal=" + montantTotal + "]";
    }

    /*################################################################################################
                    RELATION D'ORDRE POUR COMPARAISON
    ##################################################################################################*/

    @Override
    public int compareTo(Object another) {
        DateModifiable dateEvent1 = ((Depense) another).getDate();
        DateModifiable dateEvent2 = this.getDate();
        if (dateEvent1.before(dateEvent2))  return -1;
        else if(dateEvent1.equals(dateEvent2)) return 0;
        else return 1;
    }

}
