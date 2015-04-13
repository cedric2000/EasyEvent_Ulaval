package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Participant;


public class ParticipantActivity extends ActionBarActivity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Participant participant;

    private TextView nameText, telText, mailText;
    private static final int EDIT_PARTICIPANT = 101;

    private DAOParticipant participantDAO;


    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);

        participantDAO = new DAOParticipant(this);

        long idParticipant = getIntent().getLongExtra("PARTICIPANT", 0);

        participantDAO.open();
        participant = participantDAO.getParticipant(idParticipant);
        participantDAO.close();

        nameText = (TextView)this.findViewById(R.id.name_part_text);
        telText = (TextView)this.findViewById(R.id.tel_part_text);
        mailText = (TextView)this.findViewById(R.id.mail_part_text);

        nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
        telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
        mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);

            //Button edition
        final LinearLayout editFlottingButton = (LinearLayout)findViewById(R.id.edit_button_layout);
        editFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipantActivity.this, ParticipantFormActivity.class);
                intent.putExtra("PARTICIPANT", participant.getId());
                startActivity(intent);
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.edit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFlottingButton.performClick();
            }
        });
    }

    protected  void onResume(){
        super.onResume();
        participantDAO.open();
        long idParticipant = getIntent().getLongExtra("PARTICIPANT", 0);
        participant = participantDAO.getParticipant(idParticipant);

        nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
        telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
        mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);

        participantDAO.close();
    }

}
