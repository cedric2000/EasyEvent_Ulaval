package ca.easyevent.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
    private TextView libTextError,dateTextError, nbParticipantTextError, montantTextError;

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
        participantDAO = new DAOParticipant(this);
        participationDAO = new DAOParticipation(this);

        depenseDAO.open();

        final long idDepense = getIntent().getLongExtra("DEPENSE", -1);
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);


        libelleText = (EditText)this.findViewById(R.id.lib_dep_text);
        montantDepenseText = (TextView)this.findViewById(R.id.budget_tot_dep_valor);
        dateText = (TextView)this.findViewById(R.id.date_dep_text);
        listParticipationView = (ListView)findViewById(R.id.list_participation);

        libTextError = (TextView)this.findViewById(R.id.lib_error);
        nbParticipantTextError = (TextView)this.findViewById(R.id.nb_participant_error);
        montantTextError = (TextView)findViewById(R.id.montant_error);
        dateTextError = (TextView)this.findViewById(R.id.date_error);


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

                /*=============================
                      Button Delete
                =============================*/

        TextView deleteButton = (TextView)findViewById(R.id.delete);
        if(editMode) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    participationDAO.open();
                    participantDAO.open();
                    depenseDAO.open();

                    ArrayList<Participation> listParticipation = participationDAO.getAllParticipationsForDepense(depense.getId());
                    for(Participation p : listParticipation) {
                        Participant participant = participantDAO.getParticipant(p.getIdParticipant());
                        double equilibreGlobale = participant.getEquiPersoTotal() - p.getEquilibre();
                        participantDAO.updateEquilibreTotale(participant.getId(),equilibreGlobale);
                    }
                    participantDAO.close();

                    for (Participation participation : listParticipation)
                        participationDAO.deleteParticipation(participation.getId());
                    participationDAO.close();


                    depenseDAO.deleteDepense(idDepense);
                    depenseDAO.close();

                    finish();
                }
            });
        }
        else
            deleteButton.setVisibility(View.GONE);
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
            public void onClick(DialogInterface dialog, int which) { }
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
        if(checkForm()) {
            //get data
            depense.setLibelle(libelleText.getText().toString());
            depense.setDate(new DateModifiable(dateText.getText().toString()));
            updateCalcul();

            //Update depense
            depenseDAO.open();
            long idDepense;
            if (editMode) {
                depenseDAO.updateDepense(depense);
                idDepense = depense.getId();
            } else
                idDepense = depenseDAO.addDepense(idEvenement, depense);


            //Update participant
            participantDAO.open();
            participationDAO.open();
            for (Participation p : adapter.getListParticipation()) {
                Participant participant = participantDAO.getParticipant(p.getIdParticipant());
                double ancienneValeur = 0;
                if (editMode)
                    ancienneValeur = participationDAO.getParticipation(p.getId()).getEquilibre();
                double equilibreGlobale = participant.getEquiPersoTotal() + p.getEquilibre() - ancienneValeur;
                participantDAO.updateEquilibreTotale(participant.getId(), equilibreGlobale);
                System.out.println("Participant : " + participant+" | ancienne valeur : " + ancienneValeur
                                    +" | equilibre : " + equilibreGlobale);
            }


            //Update participation
            for (Participation participation : adapter.getListParticipation()){
                participation.setIdDepense(idDepense);
                if (participation.isSelected()) {
                    double equilibre = participation.getMontant() - (depense.getMontantTotal() / depense.getNbParticipants());
                    participation.setEquilibre(equilibre);
                }
                if (editMode)
                    participationDAO.updateParticipation(participation);
                else
                    participationDAO.addParticipation(participation);
            }

            participationDAO.close();
            participantDAO.close();
            depenseDAO.close();

            finish();
        }
    }


    /*##############################################################################################
                       VERIFICATION FORMULAIRE
    ###############################################################################################*/

    public boolean checkForm(){
        boolean textOK = setErrorField(libelleText,libTextError),numberOK=true,
                montantOK = setErrorMontant(montantDepenseText, montantTextError),
                dateOK = setErrorField(dateText,dateTextError);

            //Nombre participant dépense
        int number =0;
        for (Participation participation : adapter.getListParticipation()) {
            if (participation.isSelected())
                number++;
        }
        if(number<2){
            nbParticipantTextError.setVisibility(View.VISIBLE);
            numberOK = false;
        }
        else
            nbParticipantTextError.setVisibility(View.GONE);

        return  textOK && dateOK && numberOK  && montantOK ;
    }

    public boolean setErrorField(TextView editText, TextView textError ){
        Drawable imageResource;
        boolean isOK;
        if(editText.getText()==null ||editText.getText().toString().equals("")){
            imageResource = getResources().getDrawable( R.drawable.edit_text_error);
            textError.setVisibility(View.VISIBLE);
            isOK = false;
        }
        else {
            imageResource = getResources().getDrawable(R.drawable.edit_text);
            textError.setVisibility(View.GONE);
            isOK = true;
        }
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackgroundDrawable(imageResource);
        } else {
            editText.setBackground(imageResource);
        }

        return isOK;
    }

    public boolean setErrorMontant(TextView editText, TextView textError ){
        boolean isOK;
        if(editText.getText()==null || Double.valueOf(editText.getText().toString()) <= 0){
            textError.setVisibility(View.VISIBLE);
            isOK = false;
        }
        else {
            textError.setVisibility(View.GONE);
            isOK = true;
        }

        return isOK;
    }


    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void updateCalcul() {

        double montantDepense = 0;
        int nbParticipant=0;
        for (Participation participation : adapter.getListParticipation()) {
            if (participation.isSelected()) {
                montantDepense += participation.getMontant();
                nbParticipant++;
            }
        }
        depense.setMontantTotal(montantDepense);
        depense.setNbParticipant(nbParticipant);

        for (Participation participation : adapter.getListParticipation()) {
            if (participation.isSelected()) {
                double equilibre = participation.getMontant() - (depense.getMontantTotal() / depense.getNbParticipants());
                participation.setEquilibre(equilibre);
            }
        }

        this.montantDepenseText.setText(depense.getMontantTotal() +"");
    }

}
