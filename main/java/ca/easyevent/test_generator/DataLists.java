package ca.easyevent.test_generator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Cédric on 18/03/2015.
 */
public class DataLists {

    static String[] nameList = {"Patrick", "Thomas", "Francis", "Maxime",
            "Marc-Antoine", "Jean-Philippe", "Samuel",
            "Vincent", "Michel", "Florent", "Isabel Cristina", "Olivier",
            "Hicham", "Mathieu", "Mikael", "Alex", "Marc-André", "Frédéric",
            "Anna Nga", "Jérémy", "Maxime", "Nicolas", "David", "Dominique",
            "Alexandre", "Rémi", "Guillaume", "Cyril", "Louis", "Mohamed Amine",
            "Frédéric", "Nicolas Arnaud", "Simon", "Marie", "Philippe" ,"Bechir",
            "Emily", "Jules Mubano", "Jean-Lou", "Claudia", "William", "Quentin",
            "Anne", "Pierre", "Kévin", "Nicolas", "Ramatoulaye", "Marc Philippe",
            "Léopold", "Etienne", "Keita", "Charles-Etienne", "David", "Cédric"};


    static String[] mailList = {"dos@novotelbali.com", "daniel@hotelpadma.com ", "daniel@balibless.com",
            "djoko_p@jayakartahotelsresorts.com", "expdepot@indosat.net.id", "feby.adamsyah@idn.xerox.com",
            "christian_rizal@interconti.com", "singgih93@mailcity.com", "idonk_gebhoy@yahoo.com",
            "info@houseofbali.com", "sm@ramayanahotel.com", "anekabeach@dps.centrin.net.id",
            "yogya@jayakartahotelsresorts.com", "garudawisatajaya@indo.net.id", "ketut@kbatur.com",
            "h4ntoro@yahoo.com", "novi_enbe@yahoo.com", "dila_dewata@yahoo.co.id", "tiena_asfary@yahoo.co.id",
            "da_lawoffice@yahoo.com", "rini@ncsecurities.biz", "sudarnoto_hakim@yahoo.com", "wastioke@yahoo.com",
            "leebahri@yahoo.com.", "lia_kiara97@yahoo.com", "rido@weddingku.com", "b_astuti@telkomsel.co.id",
            "garudawisata@indo.net.id", "grfurniture@yahoo.com", "gosyen2000@hotmail.com", "hvhfood@indosat.net.id",
            "hr@astonbali.com", "hary@wibisono-family.com", "fadlycak'p@yahoo.com", "ida_sampurniah@telkomsel.co.id",
            "muslim-pariwisata-bali@yahoogroups.com", "harisnira@yahoo.com", "sales@houseofbali.com",
            "baim_ron@yahoo.com", "ilhambali222@yahoo.com", "bungjon@gmail.com"};

    static String[] phoneList = {"(833) 460-9247", "(855) 434-2237", "(855) 025-5106",
            "(833) 068-2748", "(855) 092-2395", "(822) 200-5387",
            "(844) 129-3359", "(844) 285-0242", "(855) 445-7415",
            "(822) 162-5408", "(833) 289-6389", "(833) 756-9083",
            "(844) 382-8076", "(855) 487-8865", "(899) 447-8374"};

    static String[] montantList = {"157.34", "18.11", "24.80", "498.25", "274.47", "274.34",
            "117.57", "41.21", "89.50", "151.10", "434.06", "108.60", "492.40", "438.18",
            "147.45", "443.33", "141.23", "494.97", "82.27", "240.84", "402.39", "229.87",
            "408.01", "100.76", "106.81", "235.83", "339.10", "246.53", "240.52", "156.83",
            "243.89", "434.72", "118.03", "271.39", "314.84", "339.98", "233.13", "71.43",
            "16.31", "363.22", "220.64", "241.14", "392.31", "130.72", "79.06", "349.30",
            "288.13", "270.36", "419.18", "82.95", "317.95", "81.20", "259.42", "274.01",
            "483.35", "426.22", "272.26", "30.47", "209.36", "349.85"};

    static String[] dateList = {"08/01/2015","06/11/2015","07/29/2015","11/10/2015","10/02/2015",
            "08/02/2015","11/11/2015","08/24/2015","08/08/2015","11/11/2015","06/13/2015",
            "03/03/2016","09/10/2015","06/01/2015","07/07/2015","06/14/2015","01/09/2016",
            "07/10/2015","03/05/2016","06/27/2015","12/18/2015","05/20/2015","07/23/2015",
            "07/19/2015","11/28/2015","12/02/2015","11/05/2015","11/24/2015","01/04/2016",
            "09/26/2015","09/08/2015","02/15/2016","09/21/2015","01/16/2016","12/22/2015",
            "10/20/2015","09/25/2015","12/22/2015","12/21/2015","02/09/2016","01/16/2016",
            "02/07/2016","08/26/2015","10/01/2015","05/28/2015","02/01/2016","01/08/2016",
            "10/14/2015","01/29/2016","09/19/2015","09/03/2015","02/11/2016","05/24/2015",
            "06/24/2015","09/08/2015","06/03/2015","10/28/2015","02/09/2016","05/24/2015",
            "01/24/2016","09/14/2015","08/05/2015","01/11/2016","07/20/2015","12/25/2015",
            "10/23/2015","07/06/2015","09/28/2015","10/02/2015","01/24/2016","06/13/2015",
            "11/03/2015","09/29/2015","08/23/2015","10/12/2015","08/01/2015","10/19/2015",
            "02/26/2016","01/31/2016","11/19/2015","01/05/2016","02/24/2016","11/27/2015",
            "12/04/2015","08/07/2015","11/18/2015","01/02/2016","03/12/2016","06/03/2015",
            "11/04/2015","08/02/2015","08/22/2015","01/14/2016","06/19/2015","10/30/2015",
            "06/03/2015","09/12/2015","07/16/2015","08/17/2015","03/15/2016"};


    static String[] placeList = {"Franks Field", "Verona", "Seven Mile Ford", "Trezevant", "Neoga",
            "South Lancaster", "Columbia City", "Waycross", "Bishop Hills", "Old Orchard Beach",
            "Middletown", "Gandy", "Catano", "Lauderdale", "Oilton", "New Houlka", "Cuylerville",
            "Shelly", "Unicoi", "Hersey", "Tyaskin", "Easton", "Milbank", "Pleasant Plains", "Anmoore"};
    
    static String[] stringList ={"Bacon", "ipsum", "dolor", "sit", "amet", "doner", "chuck", "kielbasa", "landjaeger", "hamburger",
            "short", "ribs", "drumstick", "leberkas", "porchetta", "ground", "round", "sausage", "pancetta", "ribeye.", "Pork", "chop",
            "beef", "ribs", "short", "loin", "t-bone", "sirloin", "bacon", "shankle", "prosciutto", "jerky.", "Shankle", "bresaola",
            "strip", "steak", "short", "loin,", "turducken", "shoulder", "kielbasa.", "Ball", "tip", "bacon", "shoulder", "chicken",
            "beef", "ribs", "andouille", "short", "loin", "pork", "porchetta.", "Sirloin", "filet", "mignon", "chuck", "ham", "hock",
            "t-bone", "turducken", "fatback,", "pork", "andouille", "meatball", "ball", "tip", "ground", "round", "short", "ribs",
            "pork", "chop", "chicken.", "Salami", "shoulder", "pork", "belly", "rump", "pastrami", "jerky", "meatball,", "filet",
            "mignon", "meatloaf", "ham", "bresaola", "tri-tip", "kevin.", "Venison", "pastrami", "t-bone", "tail", "pork", "chop",
            "ham", "pork", "loin", "brisket", "chuck", "fatback", "jerky", "turducken", "ball", "tip", "porchetta", "ham", "hock.",
            "Bresaola", "kielbasa", "chuck", "shank", "hamburger", "capicola", "ham.", "Venison", "strip", "steak", "ground",
            "round,", "meatball", "short", "ribs", "t-bone", "kielbasa.", "Chuck", "turducken", "ground", "round", "biltong",
            "cow,", "boudin", "pig", "drumstick", "andouille", "flank", "spare", "ribs.", "Pork", "belly", "tri-tip", "leberkas",
            "beef", "ribs", "hamburger", "chicken,", "drumstick", "fatback", "porchetta", "ball", "tip", "turducken", "tongue",
            "venison", "ribeye", "ground", "round.", "Salami", "meatloaf", "jerky", "andouille", "ham", "hock", "pork", "corned",
            "beef", "fatback", "short", "loin", "pig", "bresaola", "tail", "frankfurter", "prosciutto.", "Prosciutto",
            "tri-tip", "ground", "round", "strip", "steak", "short", "loin", "leberkas", "Shoulder", "venison", "rump", "fatback",
            "meatloaf", "hamburger", "turducken", "leberkas", "porchetta", "tri-tip", "drumstick", "brisket", "pastrami.",
            "Meatloaf", "ribeye", "tenderloin", "kielbasa", "shankle", "shoulder", "frankfurter.", "Drumstick", "pastrami",
            "shoulder", "strip", "steak,", "t-bone", "biltong", "leberkas", "turducken", "meatloaf", "beef", "ribs.", "Beef", "jowl",
            "hamburger", "andouille.", "Filet", "mignon", "biltong", "corned", "beef,", "pancetta", "leberkas", "sirloin", "short",
            "ribs", "swine", "kevin", "shoulder", "tail", "turducken", "pork", "belly", "strip", "steak", "meatloaf.", "Short", "ribs",
            "ball", "tip", "bacon,", "shoulder", "chuck", "leberkas", "pig", "ham", "hamburger."};


        //Renvoie une chaine de caractère de [nbWord] mots
    public static String randomStringGenerator(int nbWord){
        String str = "";
        for(int i=0; i<nbWord;i++ ){
            Random rand = new Random();
            str += stringList[rand.nextInt(nameList.length)];
        }
        return str;
    }

    //Renvoie une liste d'entier dont la somme est égale à [number]
    // !! Proportion de 0 importante !!
    public static ArrayList<Double> randomDecompositionOf(double number, int nbDecomposition){
        ArrayList<Double> list = new ArrayList<Double>();
        double currentNumber = number;
        for(int i=0; i<nbDecomposition-1;i++) {
            Random rand = new Random();
            //if (rand.nextBoolean() == true)
            //    list.add(0.0);
            //else {
                double newNumber = rand.nextDouble() * currentNumber;
                list.add(newNumber);
                currentNumber -= newNumber;
            //}
        }
        list.add(currentNumber);
        return list;
    }

}
