package ca.easyevent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.ParticipantAdapter;
import ca.easyevent.adapter.ParticipantAdapterListener;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Participant;

public class ParticipantListActivity extends Activity implements ParticipantAdapterListener{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Participant> listParticipant;

    private ParticipantAdapter adapter;
    private ListView list ;

    private DAOParticipant participantDAO;
    private long idEvenement ;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_list_activity);

        participantDAO = new DAOParticipant(this);



        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.add_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipantListActivity.this, ParticipantFormActivity.class);
                intent.putExtra("EVENEMENT", idEvenement);
                intent.putExtra("PARTICIPANT", -1);
                startActivity(intent);
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlottingButton.performClick();
            }
        });
    }


    protected  void onResume(){
        super.onResume();
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);

        participantDAO.open();
        listParticipant = participantDAO.getAllParticipants(idEvenement);
        participantDAO.close();

        adapter = new ParticipantAdapter(this,listParticipant);
        adapter.addListener(this);
        list = (ListView)findViewById(R.id.listParticipant);
        list.setAdapter(adapter);
    }

    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void onClicParticipant(Participant item, int position) {
        Intent intent = new Intent(ParticipantListActivity.this, ParticipantActivity.class);
        intent.putExtra("EVENEMENT", idEvenement);
        intent.putExtra("PARTICIPANT", item.getId());
        startActivity(intent);
    }
}
