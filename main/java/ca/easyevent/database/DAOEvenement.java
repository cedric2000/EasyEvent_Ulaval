package ca.easyevent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ca.easyevent.model.Evenement;
import ca.easyevent.utils.DateModifiable;

/**
 * Created by Cédric on 07/04/2015.
 */
public class DAOEvenement extends DataAccessObject {

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DAOEvenement(Context pContext) {
        super(pContext);
    }

    /*##############################################################################################
									AJOUT
	###############################################################################################*/

    public long addEvenement(Evenement evenement) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.EVENT_TITRE, evenement.getTitre());
        values.put(DataBaseHandler.EVENT_LIEU, evenement.getLieu());
        values.put(DataBaseHandler.EVENT_DATE_DEBUT, evenement.getDateDebut().toString());
        if(evenement.getDateFin() != null)
            values.put(DataBaseHandler.EVENT_DATE_FIN, evenement.getDateFin().toString());
        else
            values.put(DataBaseHandler.EVENT_DATE_FIN, "");

        return database.insert(DataBaseHandler.EVENT_NAME_TABLE, null, values);
    }

    /*##############################################################################################
									SUPPRESSION
	###############################################################################################*/

    public void deleteEvenement(long id) {
        database.delete(DataBaseHandler.EVENT_NAME_TABLE, DataBaseHandler.EVENT_ID + " = ?", new String[] {String.valueOf(id)});
    }

    /*##############################################################################################
									  UPDATE
	###############################################################################################*/

    public void updateEvenement( Evenement evenement) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.EVENT_TITRE, evenement.getTitre());
        values.put(DataBaseHandler.EVENT_LIEU, evenement.getLieu());
        values.put(DataBaseHandler.EVENT_DATE_DEBUT, evenement.getDateDebut().toString());
        if(evenement.getDateFin() != null)
            values.put(DataBaseHandler.EVENT_DATE_FIN, evenement.getDateFin().toString());
        else
            values.put(DataBaseHandler.EVENT_DATE_FIN, "");

        database.update(DataBaseHandler.EVENT_NAME_TABLE, values,
                DataBaseHandler.EVENT_ID + " = ?", new String[]{String.valueOf(evenement.getId())});
    }

    /*##############################################################################################
									ACCESSEUR
	###############################################################################################*/

    public Evenement getEvenement(long idEvenement){
        Cursor cursor = database.query(
                DataBaseHandler.EVENT_NAME_TABLE,
                DataBaseHandler.EVENT_COLONNE,
                DataBaseHandler.EVENT_ID + " = ?",
                new String[] {String.valueOf(idEvenement)}, null, null, null);
        cursor.moveToFirst();
        Evenement e;
        if(!cursor.isAfterLast())
            e = cursorToEvenement(cursor);
        else
            e= new Evenement();
        cursor.close();
        return e;
    }

    public ArrayList<Evenement> getAllEvenements() {
        ArrayList<Evenement> evenements = new ArrayList<Evenement>();

        Cursor cursor = database.query(
                DataBaseHandler.EVENT_NAME_TABLE,
                DataBaseHandler.EVENT_COLONNE,null,null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Evenement evenement = cursorToEvenement(cursor);
            evenements.add(evenement);
            cursor.moveToNext();
        }
        cursor.close();
        return evenements;
    }

    /*##############################################################################################
                                ACCESSEUR
    ###############################################################################################*/

    private Evenement cursorToEvenement(Cursor cursor) {
        Evenement evenement = new Evenement();
        evenement.setId(cursor.getLong(0));
        evenement.setTitre(cursor.getString(1));
        evenement.setLieu(cursor.getString(2));
        evenement.setDateDebut( new DateModifiable(cursor.getString(3)) );

        String strDate = cursor.getString(4);
        if(strDate !=null  && !strDate.equals("") )
            evenement.setDateFin( new DateModifiable(strDate) );
        else
            evenement.setDateFin(null);
        return evenement;
    }

}
