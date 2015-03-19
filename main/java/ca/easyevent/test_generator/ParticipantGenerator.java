package ca.easyevent.test_generator;

import java.util.ArrayList;
import java.util.Random;

import ca.easyevent.model.Participant;

public class ParticipantGenerator {

    public static ArrayList<Participant> getParticipantList(int numberToGenerate){
        ArrayList<Participant> returnList= new ArrayList<>();
        for(int i=0;i<numberToGenerate;i++){
            Participant participant = new Participant();
            Random random = new Random();
            participant.setNom(DataLists.nameList[random.nextInt(DataLists.nameList.length)]);
            participant.setMail(DataLists.mailList[random.nextInt(DataLists.mailList.length)]);
            participant.setTelephone(DataLists.phoneList[random.nextInt(DataLists.phoneList.length)]);

            returnList.add(participant);
        }
        return returnList;
    }
}
