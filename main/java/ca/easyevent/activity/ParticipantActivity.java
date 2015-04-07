package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;


public class ParticipantActivity extends ActionBarActivity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Participant participant;
    private Evenement evenement;

    private TextView nameText, telText, mailText;
    private static final int EDIT_PARTICIPANT = 101;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);

        evenement = getIntent().getParcelableExtra("EVENEMENT");
        String nameParticipant = getIntent().getStringExtra("NAME_PART");
        this.participant = evenement.getParticipant(nameParticipant);


        nameText = (TextView)this.findViewById(R.id.name_part_text);
        telText = (TextView)this.findViewById(R.id.tel_part_text);
        mailText = (TextView)this.findViewById(R.id.mail_part_text);

        nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
        telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
        mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);

            //Button edition
        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.edit_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipantActivity.this, ParticipantFormActivity.class);
                intent.putExtra("EVENEMENT", evenement);
                intent.putExtra("NAME_PART", participant.getName());
                startActivityForResult(intent, EDIT_PARTICIPANT);
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.edit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlottingButton.performClick();
            }
        });
    }

    /*##############################################################################################
                              COMPORTEMENT D'ACTIVITY
    ###############################################################################################*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            evenement = data.getParcelableExtra("EVENEMENT");

            String nameParticipant = data.getStringExtra("NAME_PART");
            this.participant = evenement.getParticipant(nameParticipant);

            nameText.setText(this.participant.getName(), TextView.BufferType.EDITABLE);
            telText.setText(this.participant.getTelephone(),TextView.BufferType.EDITABLE);
            mailText.setText(this.participant.getMail()+"",TextView.BufferType.EDITABLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParticipantActivity.this, ParticipantListActivity.class);
        intent.putExtra("EVENEMENT", evenement);
        startActivity(intent);
    };



}
