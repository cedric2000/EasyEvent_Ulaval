package ca.easyevent.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Participation implements Parcelable{

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

    public Participation(Parcel source){
        if(source.dataSize()>0){
            this.participant = source.readParcelable(Participant.class.getClassLoader());
            this.depense = source.readParcelable(Depense.class.getClassLoader());
            this.montant = source.readDouble();
            this.tauxParticipation = source.readDouble();
        }
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



	/*################################################################################################
								COMPORTEMENT PARCELABLE
	##################################################################################################*/


    public final static Creator<Participation> CREATOR =
            new Creator<Participation>()
            {
                public Participation createFromParcel(Parcel in) {
                    return new Participation(in);
                }

                public Participation[] newArray(int size) {
                    return new Participation[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.participant, flags);
        dest.writeParcelable(this.depense, flags);
        dest.writeDouble(this.montant);
        dest.writeDouble(this.tauxParticipation);
    }
}
