package ca.easyevent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ca.easyevent.model.Depense;
import ca.easyevent.utils.DateModifiable;

/**
 * Created by Cédric on 07/04/2015.
 */
public class DAODepense extends DataAccessObject {

    /*##############################################################################################
									CONSTRUCTOR
	###############################################################################################*/

    public DAODepense(Context pContext) {
        super(pContext);
    }

    /*##############################################################################################
									AJOUT
	###############################################################################################*/

    public long addDepense(long idEvent,  Depense depense) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.EVENT_ID, idEvent);
        values.put(DataBaseHandler.DEPENSE_LIBELLE, depense.getLibelle());
        values.put(DataBaseHandler.DEPENSE_MONTANT, depense.getMontantTotal());
        values.put(DataBaseHandler.DEPENSE_NB_PARTICIPANT, depense.getNbParticipants());
        values.put(DataBaseHandler.DEPENSE_DATE, depense.getDate().toString());

        return database.insert(DataBaseHandler.DEPENSE_NAME_TABLE, null, values);
    }

    public long addTitleDepense(long idEvent,  Depense depense) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.EVENT_ID, idEvent);
        values.put(DataBaseHandler.DEPENSE_LIBELLE, depense.getLibelle());
        values.put(DataBaseHandler.DEPENSE_DATE, depense.getDate().toString());

        return database.insert(DataBaseHandler.DEPENSE_NAME_TABLE, null, values);
    }

    /*##############################################################################################
									SUPPRESSION
	###############################################################################################*/

    public void deleteDepense(long idDepense) {
        database.delete(DataBaseHandler.DEPENSE_NAME_TABLE,
                        DataBaseHandler.DEPENSE_ID + " = ?", new String[] {String.valueOf(idDepense)});
    }

    /*##############################################################################################
									  UPDATE
	###############################################################################################*/

    public void updateDepense(Depense depense) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.DEPENSE_LIBELLE, depense.getLibelle());
        values.put(DataBaseHandler.DEPENSE_MONTANT, depense.getMontantTotal());
        values.put(DataBaseHandler.DEPENSE_NB_PARTICIPANT, depense.getNbParticipants());
        values.put(DataBaseHandler.DEPENSE_DATE, depense.getDate().toString());
        database.update(DataBaseHandler.DEPENSE_NAME_TABLE, values,
                DataBaseHandler.DEPENSE_ID + " = ?", new String[]{String.valueOf(depense.getId())});
    }

    public void updateMontantDepense(long idDepense, double montant) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.DEPENSE_MONTANT, montant);

        database.update(DataBaseHandler.DEPENSE_NAME_TABLE, values,
                DataBaseHandler.DEPENSE_ID + " = ?", new String[]{String.valueOf(idDepense)});
    }

    public void updateNombreParticipant(long idDepense, int nbParticipant) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.DEPENSE_MONTANT, nbParticipant);

        database.update(DataBaseHandler.DEPENSE_NAME_TABLE, values,
                DataBaseHandler.DEPENSE_ID + " = ?", new String[]{String.valueOf(idDepense)});
    }



    /*##############################################################################################
									ACCESSEUR
	###############################################################################################*/

    public Depense getDepense(long idDepense){
        Cursor cursor = database.query(
                DataBaseHandler.DEPENSE_NAME_TABLE,
                DataBaseHandler.DEPENSE_COLONNE,
                DataBaseHandler.DEPENSE_ID + " = ?",
                new String[] {String.valueOf(idDepense)}, null, null, null);
        cursor.moveToFirst();
        Depense d;
        if(!cursor.isAfterLast())
            d = cursorToDepense(cursor);
        else
            d = new Depense();
        cursor.close();
        return d;
    }


    public ArrayList<Depense> getAllDepenses(long idEvent) {
        ArrayList<Depense> depenses = new ArrayList<Depense>();

        Cursor cursor = database.query(
                DataBaseHandler.DEPENSE_NAME_TABLE,
                DataBaseHandler.DEPENSE_COLONNE,
                DataBaseHandler.EVENT_ID + " = ?",
                new String[] {String.valueOf(idEvent)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Depense depense = cursorToDepense(cursor);
            depenses.add(depense);
            cursor.moveToNext();
        }
        cursor.close();
        return depenses;
    }




    /*##############################################################################################
                                ACCESSEUR
    ###############################################################################################*/

    private Depense cursorToDepense(Cursor cursor) {
        Depense depense = new Depense();
        depense.setId(cursor.getLong(0));
        depense.setLibelle(cursor.getString(2));
        depense.setMontantTotal(cursor.getDouble(3));
        depense.setNbParticipant(cursor.getInt(4));
        depense.setDate( new DateModifiable(cursor.getString(5)) );
        return depense;
    }


}
