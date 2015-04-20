package ca.easyevent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.DepenseAdapter;
import ca.easyevent.adapter.DepenseAdapterListener;
import ca.easyevent.database.DAODepense;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Depense;

public class DepenseListActivity extends Activity implements DepenseAdapterListener{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Depense> listDepense;

    private DepenseAdapter adapter;
    private ListView listDepenseView;

    private DAODepense depenseDAO;
    private long idEvenement ;


    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_list_activity);

        final View addFlottingButton = findViewById(R.id.add_button);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepenseListActivity.this, DepenseFormActivity.class);
                intent.putExtra("EVENEMENT", idEvenement);
                intent.putExtra("DEPENSE", -1);
                startActivity(intent);
            }
        });

    }


    protected  void onResume(){
        super.onResume();
        DAODepense depenseDAO = new DAODepense(this);
        depenseDAO.open();
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);
        listDepense = depenseDAO.getAllDepenses(idEvenement);
        depenseDAO.close();

        DAOParticipant participantDAO = new DAOParticipant(this);
        participantDAO.open();
        adapter = new DepenseAdapter(this, listDepense, participantDAO.getAllParticipants(idEvenement));
        adapter.addListener(this);
        listDepenseView = (ListView)findViewById(R.id.listDepense);
        listDepenseView.setAdapter(adapter);
        participantDAO.close();

        TextView emptyEvent = (TextView)this.findViewById(R.id.dep_empty);
        if(adapter.getCount()<1){
            emptyEvent.setVisibility(View.VISIBLE);
            listDepenseView.setVisibility(View.GONE);
        }
        else{
            emptyEvent.setVisibility(View.GONE);
            listDepenseView.setVisibility(View.VISIBLE);
        }

    }


    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void onClickDepense(Depense item, int position) {
        Intent intent = new Intent(DepenseListActivity.this, DepenseActivity.class);
        intent.putExtra("EVENEMENT", idEvenement);
        intent.putExtra("DEPENSE", item.getId());
        startActivity(intent);
    }
}
