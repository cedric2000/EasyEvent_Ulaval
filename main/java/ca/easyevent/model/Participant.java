package ca.easyevent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Participant implements Parcelable{


	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/
	
	private String nom = new String(""); 
	private String telephone = new String(""); 
	private String mail = new String(""); 
		
	private float BudgetPersoTotal = 0;
	private float equilibrePersoTotal = 0;
	
	private ArrayList<Participation> listeParticipation = new ArrayList<>();
	

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

    public Participant(Parcel source){
        if(source.dataSize()>0){
            this.nom = source.readString();
            this.telephone = source.readString();
            this.mail = source.readString();
            source.readList(this.listeParticipation, Participation.class.getClassLoader());
        }
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }



	/*##############################################################################################
									DESCRIPTEUR 
	##############################################################################################*/
	
	@Override
	public String toString() {
		return "Participant [nom=" + nom + ", telephone=" + telephone
				+ ", mail=" + mail + ", BudgetPersoTotal=" + BudgetPersoTotal
				+ ", equilibrePersoTotal=" + equilibrePersoTotal + "]";
	}

	/*################################################################################################
								COMPORTEMENT PARCELABLE
	##################################################################################################*/


    public final static Creator<Participant> CREATOR =
            new Creator<Participant>()
            {
                public Participant createFromParcel(Parcel in) {
                    return new Participant(in);
                }

                public Participant[] newArray(int size) {
                    return new Participant[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeString(this.mail);
        dest.writeString(this.telephone);
        dest.writeList(this.listeParticipation);
    }

}
