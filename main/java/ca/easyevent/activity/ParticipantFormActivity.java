package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;

public class ParticipantFormActivity extends ActionBarActivity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Participant participant;
    private Evenement evenement;

    private EditText nameText, telText, mailText;
    private boolean editMode;


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

        evenement = getIntent().getParcelableExtra("EVENEMENT");

        String nameParticipant = getIntent().getStringExtra("NAME_PART");
        if(nameParticipant.equals("")) {
            participant = new Participant();
            editMode = false;
        }
        else {
            this.initFormWithParticipant(nameParticipant);
            editMode = true;
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
        Button sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    /*##############################################################################################
                                    INIT FORMULAIRE
    ###############################################################################################*/

    public void initFormWithParticipant(String name){
        this.participant = evenement.getParticipant(name);

        nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
        telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
        mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);
    }


    /*##############################################################################################
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){

        Intent result = new Intent();
        participant.setNom(nameText.getText().toString());
        participant.setTelephone(telText.getText().toString());
        participant.setMail(mailText.getText().toString());
        result.putExtra("EVENEMENT", evenement);

        if(!editMode)
            evenement.ajoutParticipant(participant);
        else
            result.putExtra("NAME_PART", participant.getName());
        result.putExtra("EVENEMENT", evenement);
        setResult(RESULT_OK, result);
        finish();
    }
}
