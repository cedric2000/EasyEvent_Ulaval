package ca.easyevent.model;

import com.cevex.easyevent.util.DateModifiable;

import java.util.ArrayList;



public class Evenement 
{
	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/
	
	private ArrayList<Depense> listDepense = new ArrayList<>();
	private ArrayList<Participant> listeParticipant = new ArrayList<>();
	
	private String titre, lieu;
	private DateModifiable dateDebut, dateFin;

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
	
	public Evenement(String titre, String lieu, DateModifiable dateDebut,DateModifiable dateFin) {
		super();
		this.titre = titre;
		this.lieu = lieu;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}



	/*##############################################################################################
										AJOUT
	###############################################################################################*/

	public void ajoutParticipant(String name, String telephone, String mail){
		this.listeParticipant.add(new Participant(name, telephone, mail));
	}

	public void ajoutDepense(String libelle, DateModifiable date){
		this.listDepense.add(new Depense(libelle, date));
	}
	
	public void ajouterParticipation(String nomParticipant, String libelleDepense, double montant, double tauxParticipation )
	{
		Participant participant = this.listeParticipant.get(this.listeParticipant.indexOf(nomParticipant));
		Depense depense 		= this.listDepense.get(this.listDepense.indexOf(libelleDepense));
		
		Participation participation = new Participation(participant, depense, montant, tauxParticipation);
		participant.addParticipation(participation);
		depense.addParticipation(participation);
	}
	
	
	/*################################################################################################
								ACCESSEUR
	##################################################################################################*/

}
