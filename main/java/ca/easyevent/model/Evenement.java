package ca.easyevent.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.easyevent.utils.DateModifiable;


public class Evenement implements Parcelable
{
	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private String titre, lieu;
    private DateModifiable dateDebut, dateFin;

	private ArrayList<Depense> listDepense = new ArrayList<>();
	private ArrayList<Participant> listeParticipant = new ArrayList<>();

	/*##############################################################################################
								Constructeur
	###############################################################################################*/

	public Evenement(){}
	
	public Evenement(String titre, String lieu, DateModifiable dateDebut) {
		super();
		this.titre = titre;
		this.lieu = lieu;
		this.dateDebut = dateDebut;
		this.dateFin = null;
	}

    public Evenement(Parcel source){
        if(source.dataSize()>0){
            this.titre  = source.readString();
            this.lieu   = source.readString();
            this.dateDebut = source.readParcelable(DateModifiable.class.getClassLoader());
            this.dateFin    = source.readParcelable(DateModifiable.class.getClassLoader());
            source.readTypedList(this.listeParticipant, Participant.CREATOR);

                //Création des dépendances
            int numDepense =0;
            for (Participant participant : this.listeParticipant){
                numDepense =0;
                for (Participation participation :participant.getListeParticipation()){
                    participation.setParticipant(participant);
                    if(this.listDepense.size()<participant.getListeParticipation().size()) {
                        participation.getDepense().addParticipation(participation);
                        this.listDepense.add(participation.getDepense());
                    }
                    else{
                        this.listDepense.get(numDepense).addParticipation(participation);
                    }
                    numDepense ++;
                }
            }
        }
    }
	/*##############################################################################################
										AJOUT
	###############################################################################################*/

	public void ajoutParticipant(String name, String telephone, String mail){
		this.listeParticipant.add(new Participant(name, telephone, mail));
	}

    public void ajoutParticipant(Participant participant){
        this.listeParticipant.add(participant);
    }

	public void ajoutDepense(String libelle, DateModifiable date){
		this.listDepense.add(new Depense(libelle, date));
	}

    public void ajoutDepense(Depense depense){
        this.listDepense.add(depense);
    }

	public void ajouterParticipation(String nomParticipant, String libelleDepense, double montant, double tauxParticipation )
	{
        Participant participant = new Participant();
        for (Participant participantCourant :this.listeParticipant) {
            if(participantCourant.getName().equals(nomParticipant))
                participant = this.listeParticipant.get(this.listeParticipant.indexOf(participantCourant));
        }
        Depense depense= new Depense();
        for (Depense depenseCourant :this.listDepense) {
            if(depenseCourant.getLibelle().equals(libelleDepense))
                depense = this.listDepense.get(this.listDepense.indexOf(depenseCourant));
        }
		
		Participation participation = new Participation(participant, depense, montant, tauxParticipation);
		participant.addParticipation(participation);
		depense.addParticipation(participation);
	}

    public void ajouterParticipation(Participant participant, Depense depense, double montant, double tauxParticipation )
    {
        Participation participation = new Participation(participant, depense, montant, tauxParticipation);
        participant.addParticipation(participation);
        depense.addParticipation(participation);
    }
	

	/*################################################################################################
								ACCESSEUR
	##################################################################################################*/

    public String getTitre() {
        return titre;
    }

    public String getLieu() {
        return lieu;
    }

    public DateModifiable getDateDebut() {
        return dateDebut;
    }

    public ArrayList<Depense> getListDepense() {
        return listDepense;
    }

    public DateModifiable getDateFin() {
        return dateFin;
    }

    public ArrayList<Participant> getListeParticipant() {
        return listeParticipant;
    }


    /*################################################################################################
								MODIFICATEUR
	##################################################################################################*/

    public void setListeParticipant(ArrayList<Participant> listeParticipant) {
        this.listeParticipant = listeParticipant;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDateDebut(DateModifiable dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(DateModifiable dateFin) {
        this.dateFin = dateFin;
    }

    public void setListDepense(ArrayList<Depense> listDepense) {
        this.listDepense = listDepense;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "titre='" + titre + '\'' +
                ", lieu='" + lieu + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", listDepense=" + listDepense +
                ", listeParticipant=" + listeParticipant +
                '}';
    }

    /*################################################################################################
								COMPORTEMENT PARCELABLE
	##################################################################################################*/

    public final static Creator<Evenement> CREATOR =
            new Creator<Evenement>()
            {
                public Evenement createFromParcel(Parcel in) {
                    return new Evenement(in);
                }

                public Evenement[] newArray(int size) {
                    return new Evenement[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titre);
        dest.writeString(this.lieu);
        dest.writeParcelable(this.dateDebut, flags);
        dest.writeParcelable(this.dateFin, flags);
        dest.writeTypedList(this.listeParticipant);
    }

}
