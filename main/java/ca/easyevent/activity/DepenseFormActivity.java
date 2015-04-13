package ca.easyevent.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ca.easyevent.R;
import ca.easyevent.adapter.ParticipationAdapterListener;
import ca.easyevent.adapter.ParticipationFormAdapter;
import ca.easyevent.database.DAODepense;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.database.DAOParticipation;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Participation;
import ca.easyevent.utils.DateModifiable;

public class DepenseFormActivity extends Activity implements ParticipationAdapterListener {

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private long idEvenement;
    private Depense depense;

    ArrayList<Participation> listParticipation;
    private ParticipationFormAdapter adapter;

    private EditText libelleText;
    private TextView dateText, montantDepenseText;
    private ListView listParticipationView;

    private DAODepense depenseDAO;
    private DAOParticipation participationDAO;
    private DAOParticipant participantDAO;

    private boolean editMode;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_form_activity);

        depenseDAO = new DAODepense(this);
        participationDAO = new DAOParticipation(this);
        depenseDAO.open();

        long idDepense = getIntent().getLongExtra("DEPENSE", -1);
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);

        libelleText = (EditText)this.findViewById(R.id.lib_dep_text);
        montantDepenseText = (TextView)this.findViewById(R.id.budget_tot_dep_valor);
        dateText = (TextView)this.findViewById(R.id.date_dep_text);
        listParticipationView = (ListView)findViewById(R.id.list_participation);

        if(idDepense != -1){
            this.depense = depenseDAO.getDepense(idDepense);
            initFormWithDepense();
        }
        else
            initFormNewDepense();

        setDateTimeField();

        depenseDAO.close();

                /*=============================
                      Button Submit
                =============================*/
        TextView sendButton = (TextView)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }



    /*##############################################################################################
                                    DATE PICKER DIALOG
    ###############################################################################################*/

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private void setDateTimeField() {

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateText.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.setTitle("Date de la depense :");
        fromDatePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "VALIDER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar newDate = Calendar.getInstance();
                int year = fromDatePickerDialog.getDatePicker().getYear(),
                    monthOfYear = fromDatePickerDialog.getDatePicker().getMonth(),
                    dayOfMonth = fromDatePickerDialog.getDatePicker().getDayOfMonth();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateText.setText(dateFormatter.format(newDate.getTime()));
            }
        });
        fromDatePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ANNULER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(depense.getDate() != null)
                    dateText.setText(depense.getDate().toString());
                else
                    dateText.setText("");
            }
        });
    }



    /*##############################################################################################
                                    INIT FORMULAIRE
    ###############################################################################################*/

        //Edition d'un depense existant
    public void initFormWithDepense(){

        libelleText.setText(this.depense.getLibelle(), TextView.BufferType.EDITABLE);
        dateText.setText(this.depense.getDate().toString(),TextView.BufferType.EDITABLE);
        montantDepenseText.setText(this.depense.getMontantTotal()+"",TextView.BufferType.EDITABLE);

        participationDAO.open();
        ArrayList<Participation> listParticipation = participationDAO.getAllParticipationsForDepense(depense.getId());


        adapter = new ParticipationFormAdapter(this, listParticipation);
        adapter.addListener(this);
        listParticipationView.setAdapter(adapter);
        updateCalcul();
        editMode = true;
        participationDAO.close();
    }


    public void initFormNewDepense(){
        depense = new Depense();
        listParticipation = new ArrayList<>();

        participantDAO = new DAOParticipant(this);
        participantDAO.open();

        for(Participant participant : participantDAO.getAllParticipants(idEvenement)){
            Participation participation = new Participation();
            participation.setIdParticipant(participant.getId());
            participation.setSelected(false);
            listParticipation.add(participation);
        }
        adapter = new ParticipationFormAdapter(this, listParticipation);
        adapter.addListener(this);
        listParticipationView.setAdapter(adapter);
        participantDAO.close();

        editMode = false;
    }

    /*##############################################################################################
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){

            //get data
        depense.setLibelle(libelleText.getText().toString());
        depense.setDate(new DateModifiable(dateText.getText().toString()));
        updateCalcul();

            //Update data
        depenseDAO.open();
        long idDepense;
        if(editMode) {
            depenseDAO.updateDepense(depense);
            idDepense = depense.getId();
        }
        else
            idDepense = depenseDAO.addDepense(idEvenement, depense);

        participationDAO.open();
        for (Participation participation : adapter.getListParticipation()) {
            participation.setIdDepense(idDepense);
            if(participation.isSelected()) {
                double equilibre = participation.getMontant() - (depense.getMontantTotal() / depense.getNbParticipants());
                participation.setEquilibre(equilibre);
            }
            if(editMode)
                participationDAO.updateParticipation(participation);
            else
                participationDAO.addParticipation(participation);
        }

            //MAJ des participants
        participantDAO = new DAOParticipant(this);
        participantDAO.open();
        for(Participation p : adapter.getListParticipation()) {
            Participant participant = participantDAO.getParticipant(p.getIdParticipant());
            double equilibreGlobale = participant.getEquiPersoTotal() + p.getEquilibre();
            participantDAO.updateEquilibreTotale(participant.getId(),equilibreGlobale);
        }

        participationDAO.close();
        participantDAO.close();
        depenseDAO.close();

        finish();
    }

    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void updateCalcul() {

        double montantDepense = 0;
        for (Participation participation : adapter.getListParticipation()) {
            if (participation.isSelected())
                montantDepense += participation.getMontant();
        }
        depense.setMontantTotal(montantDepense);
        depense.setNbParticipant(adapter.getCount());

        for (Participation participation : adapter.getListParticipation()) {
            if (participation.isSelected()) {
                double equilibre = participation.getMontant() - (depense.getMontantTotal() / depense.getNbParticipants());
                participation.setEquilibre(equilibre);
            }
        }
        this.montantDepenseText.setText(depense.getMontantTotal() +"");
    }

}
