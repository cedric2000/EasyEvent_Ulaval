package ca.easyevent.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ca.easyevent.R;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.database.DAOParticipation;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Participation;
import ca.easyevent.utils.ImageManager;

public class ParticipantFormActivity extends Activity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Participant participant;
    private long idEvenement;

    private EditText nameText, telText, mailText;
    private TextView nameTextError, emptyImage;
    private ImageView imageView;
    private boolean editMode;

    private DAOParticipant participantDAO;

    //Image
    public static final int SELECT_FILE_REQUEST_CODE = 200;
    private Uri fileUri; // file url to store image

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.participants_form_activity);

        nameText = (EditText)this.findViewById(R.id.name_part_text);
        telText = (EditText)this.findViewById(R.id.tel_part_text);
        mailText = (EditText)this.findViewById(R.id.mail_part_text);
        nameTextError = (TextView)this.findViewById(R.id.name_error);
        imageView = (ImageView)this.findViewById(R.id.upload_image_preview);
        emptyImage = (TextView)findViewById(R.id.empty_image_preview);

        participantDAO = new DAOParticipant(this);

        final long idParticipant = getIntent().getLongExtra("PARTICIPANT", -1);
        System.out.println(idParticipant);
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);

        if(idParticipant != -1) {   //Edition d'un participant existant
            participantDAO.open();
            participant = participantDAO.getParticipant(idParticipant);

            nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
            telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
            mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);


            editMode = true;
            participantDAO.close();
        }

        else {      //Nouveau Participant
            participant = new Participant();
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

        TextView deleteButton = (TextView)findViewById(R.id.delete);
        final DAOParticipation participationDAO = new DAOParticipation(this);
        if(editMode) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    participationDAO.open();
                    int cpt =0;
                    for(Participation  participation : participationDAO.getAllParticipationsForParticipant(participant.getId())){
                        if(participation.isSelected())
                            cpt++;
                    }
                    if(cpt<1){
                        participantDAO.open();
                        participantDAO.deleteParticipant(idParticipant);
                        participantDAO.close();
                    }
                    else{
                        Toast.makeText( getApplicationContext(),
                                        "Supression Impossible : participant concerné dans des dépenses",
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                    participationDAO.close();

                    Intent intent = new Intent(ParticipantFormActivity.this, ParticipantListActivity.class);
                    intent.putExtra("EVENEMENT", idEvenement);
                    startActivity(intent);
                }
            });
        }
        else
            deleteButton.setVisibility(View.GONE);
    }


    /*##############################################################################################
                       VERIFICATION FORMULAIRE
    ###############################################################################################*/

    public boolean checkForm(){
        return  setErrorField(nameText,nameTextError) ;
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
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){
        if(checkForm()) {
            participant.setNom(nameText.getText().toString());
            participant.setTelephone(telText.getText().toString());
            participant.setMail(mailText.getText().toString());

            participantDAO.open();
            if (editMode)
                participantDAO.updateParticipant(participant);
            else
                participantDAO.addParticipant(idEvenement, participant);
            participantDAO.close();
            finish();
        }
    }


    /*##############################################################################################
                              GESTION CAMERA
    ###############################################################################################*/

    private void selectImage() {
        final CharSequence[] items = {"Choisir une photo",
                "Vider",
                "Annuler"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ParticipantFormActivity.this);
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

        this.participant.setImage(path);
        imageView.setImageDrawable(imageManager.getResizeImage(bitmap,options));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setVisibility(View.VISIBLE);
        emptyImage.setVisibility(View.GONE);
    }

    private void emptyImage() {
        participant.setImage("default");
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
                String tempPath = ImageManager.getPath(selectedImageUri, ParticipantFormActivity.this);
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
