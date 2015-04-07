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
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;

public class ParticipantListActivity extends Activity implements ParticipantAdapterListener{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private Evenement evenement;
    private ArrayList<Participant> listParticipant;

    private static final int ADD_NEW_PARTICIPANT=100;

    private ParticipantAdapter adapter;
    private ListView list ;


    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_list_activity);

        evenement = getIntent().getParcelableExtra("EVENEMENT");
        listParticipant = evenement.getListeParticipant();

        adapter = new ParticipantAdapter(this,listParticipant);
        adapter.addListener(this);

        list = (ListView)findViewById(R.id.listParticipant);
        list.setAdapter(adapter);

        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.add_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipantListActivity.this, ParticipantFormActivity.class);
                intent.putExtra("EVENEMENT", evenement);
                intent.putExtra("NAME_PART", "");
                startActivityForResult(intent, ADD_NEW_PARTICIPANT);
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


    /*##############################################################################################
                              COMPORTEMENT D'ACTIVITY
    ###############################################################################################*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            evenement = data.getParcelableExtra("EVENEMENT");

            listParticipant = evenement.getListeParticipant();
            System.out.println("LIST : " + listParticipant);

            adapter = new ParticipantAdapter(this,listParticipant);
            adapter.addListener(this);

            list = (ListView)findViewById(R.id.listParticipant);
            list.setAdapter(adapter);
        }
    }

    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void onClicParticipant(Participant item, int position) {
        Intent intent = new Intent(ParticipantListActivity.this, ParticipantActivity.class);
        intent.putExtra("EVENEMENT", evenement);
        intent.putExtra("NAME_PART", item.getName());
        startActivityForResult(intent,0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParticipantListActivity.this, EvenementActivity.class);
        intent.putExtra("EVENEMENT", evenement);
        startActivity(intent);
    };

}
