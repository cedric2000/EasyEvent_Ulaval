package ca.easyevent.model;

import java.util.ArrayList;

/**
 * Created by Cédric on 11/04/2015.
 */
public class Rapport {

	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    ArrayList<Participant> listParticipant;
    ArrayList<Dette> listDette = new ArrayList();


	/*##############################################################################################
								Constructeur
	###############################################################################################*/

    public Rapport(ArrayList<Participant> listParticipant) {
        super();
        this.listParticipant = cloneList(listParticipant);
        calculListeDette();
    }

	/*##############################################################################################
						Calcul liste dette
	###############################################################################################*/

    public void calculListeDette(){

        this.equilibreDoublon();

        int indexDebut=0, indexFin=listParticipant.size()-1;

        while(indexDebut < indexFin){

            double r = listParticipant.get(indexDebut).getEquiPersoTotal() + listParticipant.get(indexFin).getEquiPersoTotal();
            Participant creancier = listParticipant.get(indexDebut);
            Participant debiteur = listParticipant.get(indexFin);

            if(r<0){
                listDette.add(new Dette(creancier, debiteur, creancier.getEquiPersoTotal()));
                debiteur.setEquilibrePersoTotal(r);
                indexDebut++;
            }
            else if (r>0){
                listDette.add(new Dette(creancier, debiteur, -debiteur.getEquiPersoTotal()));
                creancier.setEquilibrePersoTotal(r);
                indexFin--;
            }
            else{
                listDette.add(new Dette(creancier, debiteur, creancier.getEquiPersoTotal()));
                indexDebut++;
                indexFin--;
            }
        }
    }

	/*##############################################################################################
		                Genere les dettes sur les doublons
	###############################################################################################*/

    public void equilibreDoublon(){

        Participant p = this.listParticipant.get(0);
        int i=0;
        while (p != null && p.getEquiPersoTotal()>0){
            int indexFind = -1;
            int j=0;
            for(Participant participant : this.listParticipant){
                if(p.getEquiPersoTotal() == -participant.getEquiPersoTotal()){
                    listDette.add(new Dette(p, participant, p.getEquiPersoTotal()));
                    indexFind = j;
                }
                j++;
            }
            if(indexFind == -1) {
                i++;
                p = this.listParticipant.get(i);
            }
            else{
                this.listParticipant.remove(i);
                this.listParticipant.remove(indexFind-1);
                p = this.listParticipant.get(i);
            }
        }
    }


	/*##############################################################################################
							Clone Liste
	###############################################################################################*/

    public static ArrayList<Participant> cloneList(ArrayList<Participant> list) {
        ArrayList<Participant> clone = new ArrayList<Participant>(list.size());
        for(Participant item: list)
            clone.add((Participant)item.clone());
        return clone;
    }

    /*##############################################################################################
                            Descripteur
    ###############################################################################################*/
    @Override
    public String toString() {
        String str = " ";
        for(Dette dette :listDette)
            str += dette.toString() +"\n";
        return str;
    }

}
