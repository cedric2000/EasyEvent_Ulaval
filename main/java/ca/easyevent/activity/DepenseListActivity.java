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
import ca.easyevent.adapter.DepenseAdapter;
import ca.easyevent.adapter.DepenseAdapterListener;
import ca.easyevent.database.DAODepense;
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

        depenseDAO = new DAODepense(this);
        depenseDAO.open();
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);
        listDepense = depenseDAO.getAllDepenses(idEvenement);

        adapter = new DepenseAdapter(this, listDepense);
        adapter.addListener(this);

        listDepenseView = (ListView)findViewById(R.id.listDepense);
        listDepenseView.setAdapter(adapter);

        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.add_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepenseListActivity.this, DepenseFormActivity.class);
                intent.putExtra("EVENEMENT", idEvenement);
                intent.putExtra("DEPENSE", -1);
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

        depenseDAO.close();
    }


    protected  void onResume(){
        super.onResume();
        depenseDAO.open();
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);
        listDepense = depenseDAO.getAllDepenses(idEvenement);

        adapter = new DepenseAdapter(this, listDepense);
        adapter.addListener(this);
        listDepenseView = (ListView)findViewById(R.id.listDepense);
        listDepenseView.setAdapter(adapter);

        depenseDAO.close();
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
