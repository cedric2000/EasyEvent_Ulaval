package ca.easyevent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ca.easyevent.model.Participant;

/**
 * Created by Cédric on 07/04/2015.
 */
public class DAOParticipant extends DataAccessObject  {



	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DAOParticipant(Context pContext) {
        super(pContext);
    }
    
    
    /*##############################################################################################
									AJOUT
	###############################################################################################*/

    public long addParticipant(long idEvent, Participant participant) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.EVENT_ID, idEvent);
        values.put(DataBaseHandler.PARTICIPANT_NAME, participant.getName());
        values.put(DataBaseHandler.PARTICIPANT_TEL, participant.getTelephone());
        values.put(DataBaseHandler.PARTICIPANT_MAIL, participant.getMail());
        values.put(DataBaseHandler.PARTICIPANT_EQUI, 0);

        return database.insert(DataBaseHandler.PARTICIPANT_NAME_TABLE, null, values);
    }

    /*##############################################################################################
									SUPPRESSION
	###############################################################################################*/

    public void deleteParticipant(long id) {
        database.delete(DataBaseHandler.PARTICIPANT_NAME_TABLE, DataBaseHandler.PARTICIPANT_ID + " = ?", new String[] {String.valueOf(id)});
    }

    /*##############################################################################################
									  UPDATE
	###############################################################################################*/

    public void updateParticipant(Participant participant) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.PARTICIPANT_NAME, participant.getName());
        values.put(DataBaseHandler.PARTICIPANT_TEL, participant.getTelephone());
        values.put(DataBaseHandler.PARTICIPANT_MAIL, participant.getMail());
        values.put(DataBaseHandler.PARTICIPANT_EQUI, participant.getEquiPersoTotal());

        database.update(DataBaseHandler.PARTICIPANT_NAME_TABLE, values,
                DataBaseHandler.PARTICIPANT_ID + " = ?", new String[]{String.valueOf(participant.getId())});
    }

    public void updateEquilibreTotale(long idParticipant, double equilibreTotal){
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.PARTICIPANT_EQUI, equilibreTotal);

        database.update(DataBaseHandler.PARTICIPANT_NAME_TABLE, values,
                DataBaseHandler.PARTICIPANT_ID + " = ?", new String[]{String.valueOf(idParticipant)});
    }

    /*##############################################################################################
									ACCESSEUR
	###############################################################################################*/

    public Participant getParticipant(long idParticipant){
        Cursor cursor = database.query(
                DataBaseHandler.PARTICIPANT_NAME_TABLE,
                DataBaseHandler.PARTICIPANT_COLONNE,
                DataBaseHandler.PARTICIPANT_ID + " = ?",
                new String[] {String.valueOf(idParticipant)}, null, null, null);
        cursor.moveToFirst();
        Participant p;
        if(!cursor.isAfterLast())
            p= cursorToParticipant(cursor);
        else
            p= new Participant();
        cursor.close();
        return  p;
    }

    public ArrayList<Participant> getAllParticipants(long idEvenement) {
        ArrayList<Participant> participants = new ArrayList<Participant>();

        Cursor cursor = database.query(
                DataBaseHandler.PARTICIPANT_NAME_TABLE,
                DataBaseHandler.PARTICIPANT_COLONNE,
                DataBaseHandler.EVENT_ID + " = ?",
                new String[] {String.valueOf(idEvenement)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Participant participant = cursorToParticipant(cursor);
            participants.add(participant);
            cursor.moveToNext();
        }
        cursor.close();
        return participants;
    }


    /*##############################################################################################
                                ACCESSEUR
    ###############################################################################################*/

    private Participant cursorToParticipant(Cursor cursor) {
        Participant participant = new Participant();
        participant.setId(cursor.getLong(1));
        participant.setNom(cursor.getString(2));
        participant.setTelephone(cursor.getString(3));
        participant.setMail(cursor.getString(4));
        participant.setEquilibrePersoTotal(cursor.getDouble(5));
        return participant;
    }
}
