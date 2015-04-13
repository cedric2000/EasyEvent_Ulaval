package ca.easyevent.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ca.easyevent.R;
import ca.easyevent.database.DAOEvenement;
import ca.easyevent.model.Evenement;
import ca.easyevent.utils.DateModifiable;

public class EvenementFormActivity extends Activity {

    
    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private EditText titreText, placeText;
    private TextView dateDebutText,dateFinText;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private Evenement evenement;
    private DAOEvenement evenementDAO;
    private boolean editMode;


    
    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_form_activity);

        titreText = (EditText)this.findViewById(R.id.titre_event_text);
        dateDebutText = (TextView)this.findViewById(R.id.date_debut_event_text);
        dateFinText = (TextView)this.findViewById(R.id.date_fin_event_text);
        placeText = (EditText)findViewById(R.id.place_event_text);

        setDateTimeField();

        evenementDAO = new DAOEvenement(this);
        long idEvenement = getIntent().getLongExtra("EVENEMENT", -1);

        if(idEvenement != -1) {   //Edition d'un participant existant
            evenementDAO.open();
            evenement = evenementDAO.getEvenement(idEvenement);


            titreText.setText(this.evenement.getTitre(), TextView.BufferType.EDITABLE);
            dateDebutText.setText(this.evenement.getDateDebut().toString(),TextView.BufferType.EDITABLE);
            dateFinText.setText(this.evenement.getDateFin().toString(),TextView.BufferType.EDITABLE);
            placeText.setText(this.evenement.getLieu()+"",TextView.BufferType.EDITABLE);

            editMode = true;
            evenementDAO.close();
        }

        else {      //Nouveau Participant
            evenement = new Evenement();
            editMode = false;
        }

                /*=============================
                      Button Upload Photo
                =============================*/

        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.choose_photo_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.choose_photo);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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


    private void setDateTimeField() {

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH);

        dateDebutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        dateFinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateDebutText.setText(dateFormatter.format(newDate.getTime()));
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
                dateDebutText.setText(dateFormatter.format(newDate.getTime()));
            }
        });
        fromDatePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ANNULER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(evenement.getDateDebut() != null)
                    dateDebutText.setText(evenement.getDateDebut().toString());
                else
                    dateDebutText.setText("");
            }
        });


        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFinText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.setTitle("Date de la depense :");
        toDatePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "VALIDER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar newDate = Calendar.getInstance();
                int year = toDatePickerDialog.getDatePicker().getYear(),
                        monthOfYear = toDatePickerDialog.getDatePicker().getMonth(),
                        dayOfMonth = toDatePickerDialog.getDatePicker().getDayOfMonth();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFinText.setText(dateFormatter.format(newDate.getTime()));
            }
        });
        toDatePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ANNULER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(evenement.getDateFin() != null)
                    dateFinText.setText(evenement.getDateFin().toString());
                else
                    dateFinText.setText("");
            }
        });
    }


    /*##############################################################################################
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){
        evenement.setTitre(titreText.getText().toString());
        evenement.setDateDebut(new DateModifiable(dateDebutText.getText().toString()));
        if(!dateFinText.getText().toString().equals(""))
            evenement.setDateFin(new DateModifiable(dateFinText.getText().toString()));
        else
            evenement.setDateFin(null);
        evenement.setLieu(placeText.getText().toString());
        finish();

        evenementDAO.open();
        if(editMode)
            evenementDAO.updateEvenement(evenement);
        else
            evenementDAO.addEvenement(evenement);
        evenementDAO.close();

    }

}
