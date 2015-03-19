package ca.easyevent.test_generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.utils.DateModifiable;

/**
 * Created by Cédric on 18/03/2015.
 */
public class EvenementsGenerator {


    public static List<Evenement> getEvenementList(int numberToGenerate){
        List<Evenement> returnList= new ArrayList<Evenement>();
        for(int i=0;i<numberToGenerate;i++){

            Evenement evenement = new Evenement();
            Random random = new Random();

            evenement.setTitre(DataLists.randomStringGenerator(random.nextInt(4)));
            evenement.setDateDebut(new DateModifiable(DataLists.dateList[random.nextInt(DataLists.dateList.length)]));
            evenement.setDateFin(null);
            evenement.setLieu(DataLists.placeList[random.nextInt(DataLists.placeList.length)]);

                    //Liste de participants aléatoires
            ArrayList<Participant> listParticipant = ParticipantGenerator.getParticipantList(4);
            for (Participant participant :listParticipant)
                evenement.ajoutParticipant(participant);

                    //Liste de dépenses aléatoires
            ArrayList<Depense> listDepense = DepenseGenerator.getDepenseList(4);
            for (Depense depense :listDepense)
                evenement.ajoutDepense(depense);

                    //Ajout des participation de chaque participant à chaque dépense
            for (Depense depense :listDepense){
                int j=0,
                    taille = listParticipant.size();
                for (Participant participant :listParticipant){
                    ArrayList<Double> listNumber = DataLists.randomDecompositionOf(depense.getMontantTotal(),taille);
                    evenement.ajouterParticipation(participant, depense,listNumber.get(i),100/taille);
                    j++;
                }
            }
            returnList.add(evenement);
        }


        return returnList;
    }

}
