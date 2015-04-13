package ca.easyevent.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Participant;

public class ParticipantFormActivity extends Activity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Participant participant;
    private long idEvenement;

    private EditText nameText, telText, mailText;
    private boolean editMode;

    private DAOParticipant participantDAO;


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

        participantDAO = new DAOParticipant(this);

        long idParticipant = getIntent().getLongExtra("PARTICIPANT", -1);
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

    /*##############################################################################################
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){
        participant.setNom(nameText.getText().toString());
        participant.setTelephone(telText.getText().toString());
        participant.setMail(mailText.getText().toString());

        participantDAO.open();
        if(editMode)
            participantDAO.updateParticipant(participant);
        else
            participantDAO.addParticipant(idEvenement,participant);
        participantDAO.close();
        finish();
    }
}
