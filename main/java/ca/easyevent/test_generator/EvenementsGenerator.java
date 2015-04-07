package ca.easyevent.test_generator;

import java.util.ArrayList;

import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Participation;
import ca.easyevent.utils.DateModifiable;

/**
 * Created by Cédric on 18/03/2015.
 */
public class EvenementsGenerator {


    public static ArrayList<Evenement> getTestEvenementList() {
        ArrayList<Evenement> returnList = new ArrayList<Evenement>();
        
                    /*==============================
                            EVENEMENT 1
                     ==============================*/

        Evenement evenement = new Evenement("Road Trip USA", "Boston", new DateModifiable("21/12/2014"));

        Participant participant1 = new Participant("Bob", "(844) 129-3359", "novi_enbe@yahoo.com");
        Participant participant2 = new Participant("Orel", "(844) 285-0242", "tiena_asfary@yahoo.co.id");
        evenement.ajoutParticipant(participant1);
        evenement.ajoutParticipant(participant2);

        Depense depense1 = new Depense("Appartement", new DateModifiable("02/11/2016"));

        Participation participation1 = new Participation(participant1,depense1,100);
        Participation participation2 = new Participation(participant2,depense1,0);
        participant1.addParticipation(participation1);
        participant2.addParticipation(participation2);
        depense1.addParticipation(participation1);
        depense1.addParticipation(participation2);

        evenement.ajoutDepense(depense1);


        /*evenement.ajouterParticipation("Bob","Location Voiture",100,50);
        evenement.ajouterParticipation("Orel","Location Voiture",0,50);

        evenement.ajouterParticipation("Bob","Resto",100,50);
        evenement.ajouterParticipation("Orel","Resto",0,0);*/

        returnList.add(evenement);


                    /*==============================
                            EVENEMENT 2
                     ==============================*/

        Evenement evenement2 = new Evenement("Week-end au chalet", "Gaspesie", new DateModifiable("26/04/2015"));

        Participant p1 = new Participant("Colin", "(415) 129-3359", "novi_enbe@yahoo.com");
        Participant p2 = new Participant("Luke", "(415) 285-0242", "tiena_asfary@yahoo.co.id");
        Participant p3 = new Participant("Gui", "(415) 445-7415", "dila_dewata@yahoo.co.id");

        evenement2.ajoutParticipant(p1);
        evenement2.ajoutParticipant(p2);
        evenement2.ajoutParticipant(p3);


        Depense d1 = new Depense("Location Chalet", new DateModifiable("02/03/2015"));
        d1.addParticipation(new Participation(p1,d1,200));
        d1.addParticipation(new Participation(p2,d1,0));

        Depense d2 = new Depense("Soirée Raclette", new DateModifiable("22/03/2015"));
        d2.addParticipation(new Participation(p1,d2,300));
        d2.addParticipation(new Participation(p2,d2,0));
        d2.addParticipation(new Participation(p3,d2,0));

        Depense d3 = new Depense("Equipement Sky", new DateModifiable("23/03/2015"));
        d3.addParticipation(new Participation(p1,d3,200));
        d3.addParticipation(new Participation(p3,d3,0));

        evenement2.ajoutDepense(d1);
        evenement2.ajoutDepense(d2);
        evenement2.ajoutDepense(d3);

        returnList.add(evenement2);

        return returnList;
    }
}
