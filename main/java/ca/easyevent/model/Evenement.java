package ca.easyevent.model;


import java.util.ArrayList;

import ca.easyevent.utils.DateModifiable;


public class Evenement implements Comparable
{
	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private long id;
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

    public DateModifiable getDateFin() {
        return dateFin;
    }

    public long getId() {
        return id;
    }

    /*################################################################################################
								MODIFICATEUR
	##################################################################################################*/

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

    public void setId(long id) {
        this.id = id;
    }


    /*################################################################################################
								DESCRIPTEUR
	##################################################################################################*/

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
                            RELATION D'ORDRE POUR COMPARAISON
    ##################################################################################################*/
    @Override
    public int compareTo(Object another) {
        DateModifiable dateEvent1 = ((Evenement) another).getDateDebut();
        DateModifiable dateEvent2 = this.getDateDebut();
        if (dateEvent1.before(dateEvent2))  return -1;
        else if(dateEvent1.equals(dateEvent2)) return 0;
        else return 1;
    }
}
