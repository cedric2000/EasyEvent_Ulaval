package ca.easyevent.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ca.easyevent.R;
import ca.easyevent.database.DAODepense;
import ca.easyevent.database.DAOEvenement;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.database.DAOParticipation;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Participation;
import ca.easyevent.utils.DateModifiable;
import ca.easyevent.utils.ImageManager;

public class EvenementFormActivity extends Activity {

    
    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private EditText titreText, placeText;
    private TextView dateDebutText,dateFinText;

    private TextView dateDebutTextError,titreTextError, placeTextError, emptyImage;

    private ImageView imageView;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private Evenement evenement;
    private DAOEvenement evenementDAO;
    private boolean editMode;

    //Image
    public static final int SELECT_FILE_REQUEST_CODE = 200;
    private Uri fileUri; // file url to store image
    
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
        imageView = (ImageView)findViewById(R.id.upload_image_preview);

        titreTextError = (TextView)this.findViewById(R.id.titre_error);
        dateDebutTextError = (TextView)this.findViewById(R.id.date_error);
        placeTextError = (TextView)findViewById(R.id.place_error);

        emptyImage = (TextView)findViewById(R.id.empty_image_preview);

        setDateTimeField();

        evenementDAO = new DAOEvenement(this);
        final long idEvenement = getIntent().getLongExtra("EVENEMENT", -1);

        if(idEvenement != -1) {   //Edition d'un participant existant
            evenementDAO.open();
            evenement = evenementDAO.getEvenement(idEvenement);
            evenementDAO.close();

            titreText.setText(this.evenement.getTitre(), TextView.BufferType.EDITABLE);

            //date
            dateDebutText.setText(this.evenement.getDateDebut().toString(),TextView.BufferType.EDITABLE);
            if(this.evenement.getDateFin() != null)
                dateFinText.setText(this.evenement.getDateFin().toString(),TextView.BufferType.EDITABLE);
            placeText.setText(this.evenement.getLieu()+"",TextView.BufferType.EDITABLE);

            //Image
            if(evenement.getImage()!=null  && !evenement.getImage().equals("default")) {
                initImage(this.evenement.getImage());
            }

            editMode = true;
        }

        else {      //Nouveau Participant
            evenement = new Evenement();
            editMode = false;
        }

                /*=============================
                      Button Upload Photo
                =============================*/

        final View addFlottingButton = findViewById(R.id.choose_photo);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


                /*=============================
                      Button Control
                =============================*/

        TextView sendButton = (TextView)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });


        final TextView deleteButton = (TextView)findViewById(R.id.delete);
        final DAOParticipant participantDAO = new DAOParticipant(this);
        final DAODepense depenseDAO = new DAODepense(this);
        final DAOParticipation participationDAO = new DAOParticipation(this);

        participantDAO.open();
        if(editMode) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    evenementDAO.open();
                    evenementDAO.deleteEvenement(idEvenement);
                    evenementDAO.close();

                    participantDAO.open();
                    for (Participant participant :participantDAO.getAllParticipants(idEvenement))
                        participantDAO.deleteParticipant(participant.getId());
                    participantDAO.close();


                    depenseDAO.open();
                    ArrayList<Depense>  listDepense = depenseDAO.getAllDepenses(idEvenement);
                    depenseDAO.open();

                    participationDAO.open();
                    for (Depense depense :listDepense) {
                        depenseDAO.deleteDepense(depense.getId());
                        for(Participation p : participationDAO.getAllParticipationsForDepense(depense.getId())){
                            participationDAO.deleteParticipation(p.getId());
                        }
                    }
                    participationDAO.close();

                    Intent intent = new Intent(EvenementFormActivity.this, EvenementListActivity.class);
                    startActivity(intent);
                }
            });
            participantDAO.close();
        }
        else
            deleteButton.setVisibility(View.GONE);
    }


    /*##############################################################################################
                                   DATE PICKER
    ###############################################################################################*/

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
        toDatePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "AUCUNE",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                evenement.setDateFin(null);
                dateFinText.setText(null);
            }
        });
    }


    /*##############################################################################################
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){
        if(checkForm()) {
            evenement.setTitre(titreText.getText().toString());
            evenement.setDateDebut(new DateModifiable(dateDebutText.getText().toString()));
            if (!dateFinText.getText().toString().equals(""))
                evenement.setDateFin(new DateModifiable(dateFinText.getText().toString()));
            else
                evenement.setDateFin(null);
            evenement.setLieu(placeText.getText().toString());
            finish();

            evenementDAO.open();
            if (editMode)
                evenementDAO.updateEvenement(evenement);
            else
                evenementDAO.addEvenement(evenement);
            evenementDAO.close();
        }
    }

    public boolean checkForm(){
        boolean textOK = setErrorField(titreText,titreTextError),
                dateOK = setErrorField(dateDebutText,dateDebutTextError),
                placeOK = setErrorField(placeText,placeTextError);
        return  textOK && dateOK  && placeOK ;
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

    /*##############################################################################################
                              GESTION CAMERA
    ###############################################################################################*/

    private void selectImage() {
        final CharSequence[] items = {"Choisir une photo",
                "Vider",
                "Annuler"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EvenementFormActivity.this);
        builder.setTitle("Ajouter une photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Choisir une photo")) {
                    chooseImage();
                } else if (items[item].equals("Vider")) {
                    emptyImage();
                } else if (items[item].equals("Annuler")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /*##############################################################################################
                              MODE UPLOAD IMAGE
    ###############################################################################################*/


    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choisir une image"), SELECT_FILE_REQUEST_CODE);
    }

    /*##############################################################################################
                                   INIT IMAGE
    ###############################################################################################*/

    private void initImage(String path){
        Display display = getWindowManager().getDefaultDisplay();
        double deviceWidth = display.getWidth();
        ImageManager imageManager = new ImageManager(this, deviceWidth);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        this.evenement.setImage(path);
        imageView.setImageDrawable(imageManager.getResizeImage(bitmap,options));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setVisibility(View.VISIBLE);
        emptyImage.setVisibility(View.GONE);
    }

    private void emptyImage() {
        evenement.setImage("default");
        imageView.setVisibility(View.GONE);
        emptyImage.setVisibility(View.VISIBLE);
    }

    /*##############################################################################################
                              COMPORTEMENT D'ACTIVITY
    ###############################################################################################*/


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                //On a pris une photo
             if (requestCode == SELECT_FILE_REQUEST_CODE) {
                Uri selectedImageUri = data.getData();
                String tempPath = ImageManager.getPath(selectedImageUri, EvenementFormActivity.this);
                initImage(tempPath);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

}
