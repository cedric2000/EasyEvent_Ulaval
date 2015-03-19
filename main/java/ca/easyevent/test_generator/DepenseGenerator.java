package ca.easyevent.test_generator;

import java.util.ArrayList;
import java.util.Random;

import ca.easyevent.model.Depense;
import ca.easyevent.utils.DateModifiable;


public class DepenseGenerator {

    public static ArrayList<Depense> getDepenseList(int numberToGenerate){
        ArrayList<Depense> returnList= new ArrayList<>();
        for(int i=0;i<numberToGenerate;i++){
            Depense depense = new Depense();
            Random random = new Random();
            depense.setLibelle(DataLists.randomStringGenerator(random.nextInt(4)));
            depense.setDate(new DateModifiable( DataLists.dateList[random.nextInt(DataLists.dateList.length)]));
            depense.setMontantTotal(Double.valueOf(DataLists.montantList[random.nextInt(DataLists.montantList.length)]));

            returnList.add(depense);
        }
        return returnList;
    }
}
