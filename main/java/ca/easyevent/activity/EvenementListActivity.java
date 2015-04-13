package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.EvenementAdapter;
import ca.easyevent.adapter.EvenementAdapterListener;
import ca.easyevent.database.DAOEvenement;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.utils.DateModifiable;


public class EvenementListActivity extends ActionBarActivity implements EvenementAdapterListener{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Evenement> listEvenement = new ArrayList<>();

    private DAOEvenement evenementDAO;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_activity);

        //manageDataBase();

        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.add_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvenementListActivity.this, EvenementFormActivity.class);
                intent.putExtra("EVENEMENT", -1);
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
        evenementDAO = new DAOEvenement(this);
        evenementDAO.open();
        listEvenement = evenementDAO.getAllEvenements();
        evenementDAO.close();
        initView();
    }


    public void manageDataBase(){
            //Event
        Evenement evenement = new Evenement("Road Trip USA", "Boston", new DateModifiable("01/02/2014"));
        long id = evenementDAO.addEvenement(evenement);

            //Participant
        DAOParticipant participantDAO = new DAOParticipant(this);
        participantDAO.open();
        Participant participant1 = new Participant("Bob", "(844) 129-3359", "novi_enbe@yahoo.com");
        Participant participant2 = new Participant("Orel", "(844) 285-0242", "tiena_asfary@yahoo.co.id");
        long idPart1= participantDAO.addParticipant(evenementDAO.getAllEvenements().get(0).getId(), participant1);
        long idPart2= participantDAO.addParticipant(evenementDAO.getAllEvenements().get(0).getId(), participant2);
        participantDAO.close();
    }

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    public void initView(){
        EvenementAdapter adapter = new EvenementAdapter(this, listEvenement);
        adapter.addListener(this);

        ListView list = (ListView)findViewById(R.id.listEvenements);
        list.setAdapter(adapter);
        TextView emptyEvent = (TextView)this.findViewById(R.id.events_empty);
        if(adapter.getCount()<1){
            emptyEvent.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }
        else{
            emptyEvent.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }


    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void onClickEvenement(Evenement item, int position) {
        Intent intent = new Intent(EvenementListActivity.this, EvenementActivity.class);
        intent.putExtra("EVENEMENT", item.getId());
        startActivity(intent);
    }
}
