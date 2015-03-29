package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.EvenementAdapter;
import ca.easyevent.adapter.EvenementAdapterListener;
import ca.easyevent.model.Evenement;
import ca.easyevent.test_generator.EvenementsGenerator;


public class EvenementsActivity extends ActionBarActivity implements EvenementAdapterListener{

    private ArrayList<Evenement> listEvenement = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenements);

        listEvenement = EvenementsGenerator.getTestEvenementList();

        EvenementAdapter adapter = new EvenementAdapter(listEvenement, this);
        adapter.addListener(this);

        ListView list = (ListView)findViewById(R.id.listEvenements);
        list.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickEvenement(Evenement item, int position) {
        Intent intent = new Intent(EvenementsActivity.this, EvenementActivity.class);
        intent.putExtra("EVENEMENT", listEvenement.get(position));
        startActivity(intent);
    }
}
