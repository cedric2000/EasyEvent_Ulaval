package ca.easyevent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.easyevent.utils.DateModifiable;

public class Depense implements Parcelable
{
	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/
	
	private String libelle = new String("");
    private double montantTotal =0;
    private double nbParticipant= 0;

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

    public Depense(Parcel source){
        if(source.dataSize()>0){
            this.libelle = source.readString();
            this.montantTotal = source.readDouble();
            this.date = source.readParcelable(DateModifiable.class.getClassLoader());
            this.listeParticipation = new ArrayList<>();
        }
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

    public void calculEquilibreWithPropag()
    {
        calculMontantTotal();
        for(Participation participation : this.listeParticipation )
            participation.calculEquilibreWithPropag();
    }
	
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
        return this.listeParticipation.size();
    }
	
	public DateModifiable getDate() {
		return date;
	}
	
	public ArrayList<Participation> getListeParticipation() {
		return listeParticipation;
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

	/*##############################################################################################
									DESCRIPTEUR 
	##############################################################################################*/

    @Override
    public String toString() {
        return "Depense [libelle=" + libelle + ", date=" + date + ", montantTotal=" + montantTotal + "]";
    }


	/*################################################################################################
								COMPORTEMENT PARCELABLE
	##################################################################################################*/

    public final static Creator<Depense> CREATOR =
            new Creator<Depense>()
            {
                public Depense createFromParcel(Parcel in) {
                    return new Depense(in);
                }

                public Depense[] newArray(int size) {
                    return new Depense[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.libelle);
        dest.writeDouble(this.montantTotal);
        dest.writeParcelable(this.date, flags);
    }

}
