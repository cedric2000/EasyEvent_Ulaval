package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.EvenementAdapter;
import ca.easyevent.adapter.EvenementAdapterListener;
import ca.easyevent.database.DAOEvenement;
import ca.easyevent.model.Evenement;
import ca.easyevent.utils.ImageManager;


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

        final View addFlottingButton = (View)findViewById(R.id.add_button);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvenementListActivity.this, EvenementFormActivity.class);
                intent.putExtra("EVENEMENT", -1);
                startActivity(intent);
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

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    public void initView(){

        Display display = getWindowManager().getDefaultDisplay();
        double deviceWidth = display.getWidth();
        ImageManager imageManager = new ImageManager(this, deviceWidth-40);

        EvenementAdapter adapter = new EvenementAdapter(this, listEvenement,imageManager);
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
