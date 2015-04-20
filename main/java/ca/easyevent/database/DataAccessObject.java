package ca.easyevent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Cédric on 07/04/2015.
 */
public abstract class DataAccessObject {

	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    protected final static int VERSION = 46;
    protected final static String NOM = "db_easy_event.db";

    protected SQLiteDatabase database = null;
    protected DataBaseHandler databaseHandler = null;


	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DataAccessObject(Context pContext) {
        this.databaseHandler = new DataBaseHandler(pContext, NOM, null, VERSION);
    }


    /*##############################################################################################
									PRIMITIVE ACCES
	###############################################################################################*/


    public SQLiteDatabase open() {
        database = databaseHandler.getWritableDatabase();
        return database;
    }

    public void close() {
        database.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

}
