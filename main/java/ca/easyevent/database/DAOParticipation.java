package ca.easyevent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ca.easyevent.model.Participation;

public class DAOParticipation  extends DataAccessObject {


	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DAOParticipation(Context pContext) {
        super(pContext);
    }
    
    
    /*##############################################################################################
									AJOUT
	###############################################################################################*/

    public long addParticipation(Participation participation) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.PARTICIPATION_MONTANT, participation.getMontant());
        values.put(DataBaseHandler.PARTICIPATION_EQUILIBRE, participation.getEquilibre());
        values.put(DataBaseHandler.PARTICIPANT_ID, participation.getIdParticipant());
        values.put(DataBaseHandler.DEPENSE_ID, participation.getIdDepense());
        values.put(DataBaseHandler.PARTICIPATION_EQUILIBRE, participation.getEquilibre());

        if(participation.isSelected())
            values.put(DataBaseHandler.PARTICIPATION_IS_SELECT, 1);
        else
            values.put(DataBaseHandler.PARTICIPATION_IS_SELECT, 0);

        return database.insert(DataBaseHandler.PARTICIPATION_NAME_TABLE, null, values);
    }


    /*##############################################################################################
									SUPPRESSION
	###############################################################################################*/

    public void deleteParticipation(long id) {
        database.delete(DataBaseHandler.PARTICIPATION_NAME_TABLE,
                        DataBaseHandler.PARTICIPATION_ID + " = ?", new String[] {String.valueOf(id)});
    }


    /*##############################################################################################
									  UPDATE
	###############################################################################################*/

    public void updateParticipation( Participation participation) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.PARTICIPATION_MONTANT, participation.getMontant());
        values.put(DataBaseHandler.PARTICIPANT_ID, participation.getIdParticipant());
        values.put(DataBaseHandler.DEPENSE_ID, participation.getIdDepense());
        values.put(DataBaseHandler.PARTICIPATION_IS_SELECT, participation.isSelected());
        values.put(DataBaseHandler.PARTICIPATION_EQUILIBRE, participation.getEquilibre());

        if(participation.isSelected())
            values.put(DataBaseHandler.PARTICIPATION_IS_SELECT, 1);
        else
            values.put(DataBaseHandler.PARTICIPATION_IS_SELECT, 0);

        database.update(DataBaseHandler.PARTICIPATION_NAME_TABLE, values,
                DataBaseHandler.PARTICIPATION_ID + " = ?", new String[]{String.valueOf(participation.getId())});
    }

    public void updateEquilibre(long idParticipation, double montant) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.PARTICIPATION_EQUILIBRE, montant);

        database.update(DataBaseHandler.PARTICIPATION_NAME_TABLE, values,
                DataBaseHandler.PARTICIPATION_ID + " = ?", new String[]{String.valueOf(idParticipation)});
    }

    /*##############################################################################################
									ACCESSEUR
	###############################################################################################*/

    public Participation getParticipation(long idParticipation){
        Cursor cursor = database.query(
                DataBaseHandler.PARTICIPATION_NAME_TABLE,
                DataBaseHandler.PARTICIPATION_COLONNE,
                DataBaseHandler.PARTICIPATION_ID + " = ?",
                new String[]{String.valueOf(idParticipation)}, null, null, null);
        cursor.moveToFirst();
        Participation p;
        if(!cursor.isAfterLast())
            p= cursorToParticipation(cursor);
        else
            p= new Participation();
        cursor.close();
        return  p;
    }

    public ArrayList<Participation> getAllParticipationsForParticipant(long idParticipant){
        ArrayList<Participation> participations = new ArrayList<Participation>();
        Cursor cursor = database.query(
                DataBaseHandler.PARTICIPATION_NAME_TABLE,
                DataBaseHandler.PARTICIPATION_COLONNE,
                DataBaseHandler.PARTICIPANT_ID + " = ?",
                new String[]{String.valueOf(idParticipant)}, null, null, null);
        cursor.moveToFirst();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Participation participation = cursorToParticipation(cursor);
            participations.add(participation);
            cursor.moveToNext();
        }
        cursor.close();
        return participations;
    }

    public ArrayList<Participation> getAllParticipationsForDepense(long idDepense){
        ArrayList<Participation> participations = new ArrayList<Participation>();
        Cursor cursor = database.query(
                DataBaseHandler.PARTICIPATION_NAME_TABLE,
                DataBaseHandler.PARTICIPATION_COLONNE,
                DataBaseHandler.DEPENSE_ID + " = ?",
                new String[]{String.valueOf(idDepense)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Participation participation = cursorToParticipation(cursor);
            participations.add(participation);
            cursor.moveToNext();
        }
        cursor.close();
        return participations;
    }

    public ArrayList<Participation> getAllParticipations() {
        ArrayList<Participation> participations = new ArrayList<Participation>();

        Cursor cursor = database.query(
                DataBaseHandler.PARTICIPATION_NAME_TABLE,
                DataBaseHandler.PARTICIPATION_COLONNE,null,null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Participation participation = cursorToParticipation(cursor);
            participations.add(participation);
            cursor.moveToNext();
        }
        cursor.close();
        return participations;

    }


    /*##############################################################################################
                                ACCESSEUR
    ###############################################################################################*/

    private Participation cursorToParticipation(Cursor cursor) {
        Participation participation = new Participation();
        participation.setId(cursor.getLong(0));
        participation.setIdParticipant(cursor.getInt(1));
        participation.setIdDepense(cursor.getInt(2));
        participation.setMontant(cursor.getDouble(3));
        participation.setEquilibre(cursor.getDouble(4));
        if(cursor.getInt(5) == 0)
            participation.setSelected(false);
        else
            participation.setSelected(true);

        return participation;
    }
}