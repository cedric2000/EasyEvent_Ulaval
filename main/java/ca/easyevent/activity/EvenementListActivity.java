package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.EvenementAdapter;
import ca.easyevent.adapter.EvenementAdapterListener;
import ca.easyevent.model.Evenement;
import ca.easyevent.test_generator.EvenementsGenerator;


public class EvenementListActivity extends ActionBarActivity implements EvenementAdapterListener{

    private ArrayList<Evenement> listEvenement = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list_activity);

        listEvenement = EvenementsGenerator.getTestEvenementList();

        EvenementAdapter adapter = new EvenementAdapter(this, listEvenement);
        adapter.addListener(this);

        ListView list = (ListView)findViewById(R.id.listEvenements);
        list.setAdapter(adapter);
    }


    @Override
    public void onClickEvenement(Evenement item, int position) {
        Intent intent = new Intent(EvenementListActivity.this, EvenementActivity.class);
        intent.putExtra("EVENEMENT", listEvenement.get(position));
        startActivity(intent);
    }
}
