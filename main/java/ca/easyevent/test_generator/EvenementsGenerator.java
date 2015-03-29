package ca.easyevent.test_generator;

import java.util.ArrayList;
import java.util.Random;

import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.utils.DateModifiable;

/**
 * Created by Cédric on 18/03/2015.
 */
public class EvenementsGenerator {


    public static ArrayList<Evenement> getEvenementList(int numberToGenerate){
        ArrayList<Evenement> returnList= new ArrayList<Evenement>();
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
            int j;
            for (Depense depense :listDepense){
                int taille = listParticipant.size();
                j=0;
                for (Participant participant :listParticipant){
                    ArrayList<Double> listNumber = DataLists.randomDecompositionOf(depense.getMontantTotal(),taille);
                    evenement.ajouterParticipation(participant, depense,listNumber.get(j),100/taille);
                    j++;
                }
            }
            returnList.add(evenement);
        }
        return returnList;
    }


    public static ArrayList<Evenement> getTestEvenementList() {
        ArrayList<Evenement> returnList = new ArrayList<Evenement>();
        
                    /*==============================
                            EVENEMENT 1
                     ==============================*/
        Evenement evenement = new Evenement("Road Trip USA", "Boston", new DateModifiable("21/12/2014"));

        evenement.ajoutParticipant(new Participant("Bob", "(844) 129-3359", "novi_enbe@yahoo.com"));
        evenement.ajoutParticipant(new Participant("Orel", "(844) 285-0242", "tiena_asfary@yahoo.co.id"));
        evenement.ajoutParticipant(new Participant("Daweed", "(855) 445-7415", "dila_dewata@yahoo.co.id"));

        evenement.ajoutDepense(new Depense("Appartement ", new DateModifiable("02/11/2016")));
        evenement.ajoutDepense(new Depense("Location Voiture", new DateModifiable("06/03/2015")));
        evenement.ajoutDepense(new Depense("Resto", new DateModifiable("05/24/2015")));

        evenement.ajouterParticipation("Bob","Appartement",50,33.333333);
        evenement.ajouterParticipation("Orel","Appartement",0,33.333333);
        evenement.ajouterParticipation("Daweed","Appartement",0,33.333333);

        evenement.ajouterParticipation("Bob","Location Voiture",100,50);
        evenement.ajouterParticipation("Orel","Location Voiture",0,50);
        evenement.ajouterParticipation("Daweed","Location Voiture",0,0);

        evenement.ajouterParticipation("Bob","Resto",100,50);
        evenement.ajouterParticipation("Orel","Resto",0,0);
        evenement.ajouterParticipation("Daweed","Resto",50,50);

        returnList.add(evenement);
        
        
                    /*==============================
                            EVENEMENT 2
                     ==============================*/

        Evenement evenement2 = new Evenement("Week-end au chalet", "Gaspesie", new DateModifiable("26/04/2015"));

        evenement2.ajoutParticipant(new Participant("Colin", "(415) 129-3359", "novi_enbe@yahoo.com"));
        evenement2.ajoutParticipant(new Participant("Luke", "(415) 285-0242", "tiena_asfary@yahoo.co.id"));
        evenement2.ajoutParticipant(new Participant("Gui", "(415) 445-7415", "dila_dewata@yahoo.co.id"));
        evenement2.ajoutParticipant(new Participant("Alice", "(415) 168-6498", "dila_dewata@yahoo.co.id"));

        evenement2.ajoutDepense(new Depense("Location Chalet", new DateModifiable("02/03/2015")));
        evenement2.ajoutDepense(new Depense("Soirée Raclette", new DateModifiable("22/03/2015")));
        evenement2.ajoutDepense(new Depense("Equipement Sky", new DateModifiable("23/03/2015")));

        evenement2.ajouterParticipation("Colin","Location Chalet",200,25);
        evenement2.ajouterParticipation("Luke","Location Chalet",3000,25);
        evenement2.ajouterParticipation("Gui","Location Chalet",0,25);
        evenement2.ajouterParticipation("Gui","Location Chalet",0,25);

        evenement2.ajouterParticipation("Colin","Soirée Raclette",20,33.33);
        evenement2.ajouterParticipation("Luke","Soirée Raclette",0,0);
        evenement2.ajouterParticipation("Gui","Soirée Raclette",40,33.33);
        evenement2.ajouterParticipation("Alice","Soirée Raclette",0,33.33);

        evenement2.ajouterParticipation("Colin","Equipement Sky",100,50);
        evenement2.ajouterParticipation("Luke","Equipement Sky",0,50);
        evenement2.ajouterParticipation("Gui","Equipement Sky",0,0);
        evenement2.ajouterParticipation("Alice","Equipement Sky",0,0);

        returnList.add(evenement2);



        return returnList;
    }
}
