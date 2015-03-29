package ca.easyevent.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.DepenseAdapter;
import ca.easyevent.adapter.DepenseAdapterListener;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;

public class DepensesActivity extends ActionBarActivity implements DepenseAdapterListener{

    ArrayList<Depense> listDepense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depenses);

        Evenement evenement = getIntent().getParcelableExtra("EVENEMENT");
        listDepense = evenement.getListDepense();

        DepenseAdapter adapter = new DepenseAdapter(listDepense, this);
        adapter.addListener(this);

        ListView list = (ListView)findViewById(R.id.listDepense);
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
    public void onClickDepense(Depense item, int position) {

    }
}
