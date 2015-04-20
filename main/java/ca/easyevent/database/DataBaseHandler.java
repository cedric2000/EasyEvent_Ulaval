package ca.easyevent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cédric on 07/04/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    /*##############################################################################################
									TABLE
	###############################################################################################*/

                /*====================================
                            evenement
                 ====================================*/

    public static final String EVENT_NAME_TABLE = "evenement";
    public static final String EVENT_ID = "id_event";
    public static final String EVENT_TITRE = "titre";
    public static final String EVENT_LIEU = "lieu";
    public static final String EVENT_DATE_DEBUT = "date_debut";
    public static final String EVENT_DATE_FIN= "date_fin";
    public static final String EVENT_IMAGE= "event_image";

    public static final String[] EVENT_COLONNE = {EVENT_ID,EVENT_TITRE,EVENT_LIEU, EVENT_DATE_DEBUT, EVENT_DATE_FIN,EVENT_IMAGE };

    public static final String EVENT_TABLE_DROP = "DROP TABLE IF EXISTS " + EVENT_NAME_TABLE + ";";
    public static final String EVENT_TABLE_CREATE =
            "CREATE TABLE " + EVENT_NAME_TABLE + " (" +
                    EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_TITRE + " TEXT, " +
                    EVENT_LIEU + " TEXT, " +
                    EVENT_DATE_DEBUT + " TEXT, " +
                    EVENT_DATE_FIN + " TEXT, " +
                    EVENT_IMAGE + " TEXT);";

                /*====================================
                            participant
                 ====================================*/

    public static final String PARTICIPANT_NAME_TABLE = "participant";
    public static final String PARTICIPANT_ID = "id_participant";
    public static final String PARTICIPANT_NAME = "nom";
    public static final String PARTICIPANT_TEL = "telephone";
    public static final String PARTICIPANT_MAIL = "mail";
    public static final String PARTICIPANT_EQUI = "equilibrePersoTotal";
    public static final String PARTICIPANT_IMAGE= "image";

    public static final String[] PARTICIPANT_COLONNE = {EVENT_ID, PARTICIPANT_ID, PARTICIPANT_NAME,
                        PARTICIPANT_TEL,PARTICIPANT_MAIL,PARTICIPANT_EQUI,PARTICIPANT_IMAGE  };

    public static final String PARTICIPANT_TABLE_DROP = "DROP TABLE IF EXISTS " + PARTICIPANT_NAME_TABLE + ";";
    public static final String PARTICIPANT_TABLE_CREATE =
            "CREATE TABLE " + PARTICIPANT_NAME_TABLE + " (" +
                    PARTICIPANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_ID + " INTEGER, " +
                    PARTICIPANT_NAME + " TEXT, " +
                    PARTICIPANT_TEL + " TEXT, " +
                    PARTICIPANT_MAIL + " TEXT, " +
                    PARTICIPANT_IMAGE + " TEXT, " +
                    PARTICIPANT_EQUI + " REAL);";


                /*====================================
                            depense
                 ====================================*/

    public static final String DEPENSE_NAME_TABLE = "depense";
    public static final String DEPENSE_ID = "id_depense";
    public static final String DEPENSE_LIBELLE = "libelle";
    public static final String DEPENSE_MONTANT = "montantTotal";
    public static final String DEPENSE_NB_PARTICIPANT= "nbParticipant";
    public static final String DEPENSE_DATE = "date";

    public static final String[] DEPENSE_COLONNE = {DEPENSE_ID, EVENT_ID, DEPENSE_LIBELLE, DEPENSE_MONTANT,DEPENSE_NB_PARTICIPANT, DEPENSE_DATE };

    public static final String DEPENSE_TABLE_DROP = "DROP TABLE IF EXISTS " + DEPENSE_NAME_TABLE + ";";
    public static final String DEPENSE_TABLE_CREATE =
            "CREATE TABLE " + DEPENSE_NAME_TABLE + " (" +
                    DEPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_ID + " INTEGER, " +
                    DEPENSE_LIBELLE + " TEXT, " +
                    DEPENSE_MONTANT + " TEXT, " +
                    DEPENSE_NB_PARTICIPANT + " INTEGER, " +
                    DEPENSE_DATE + " TEXT);";

                /*====================================
                            participation
                 ====================================*/

    public static final String PARTICIPATION_NAME_TABLE = "participation";
    public static final String PARTICIPATION_ID = "id_participation";
    public static final String PARTICIPATION_MONTANT = "montant";
    public static final String PARTICIPATION_EQUILIBRE = "equilibre";
    public static final String PARTICIPATION_IS_SELECT = "is_select";

    public static final String[] PARTICIPATION_COLONNE =
                {PARTICIPATION_ID, PARTICIPANT_ID, DEPENSE_ID, PARTICIPATION_MONTANT, PARTICIPATION_EQUILIBRE,PARTICIPATION_IS_SELECT  };

    public static final String PARTICIPATION_TABLE_DROP = "DROP TABLE IF EXISTS " + PARTICIPATION_NAME_TABLE + ";";
    public static final String PARTICIPATION_TABLE_CREATE =
            "CREATE TABLE " + PARTICIPATION_NAME_TABLE + " (" +
                    PARTICIPATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PARTICIPANT_ID + " INTEGER, " +
                    DEPENSE_ID + " INTEGER, " +
                    PARTICIPATION_MONTANT + " REAL, " +
                    PARTICIPATION_EQUILIBRE + " REAL, " +
                    PARTICIPATION_IS_SELECT + " INTEGER);";


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /*##############################################################################################
                                    UPGRADE
    ###############################################################################################*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EVENT_TABLE_DROP);
        db.execSQL(PARTICIPANT_TABLE_DROP);
        db.execSQL(DEPENSE_TABLE_DROP);
        db.execSQL(PARTICIPATION_TABLE_DROP);
        onCreate(db);
    }


    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EVENT_TABLE_CREATE);
        db.execSQL(PARTICIPANT_TABLE_CREATE);
        db.execSQL(DEPENSE_TABLE_CREATE);
        db.execSQL(PARTICIPATION_TABLE_CREATE);
    }
}
