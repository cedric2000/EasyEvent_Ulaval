package ca.easyevent.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.ParticipantAdapter;
import ca.easyevent.adapter.ParticipantAdapterListener;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;

public class ParticipantsActivity extends ActionBarActivity implements ParticipantAdapterListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        ArrayList<Evenement> listEvenement = getIntent().getParcelableArrayListExtra("EVENEMENT");

        ParticipantAdapter adapter = new ParticipantAdapter(listEvenement.get(0).getListeParticipant(), this);
        adapter.addListener(this);

        ListView list = (ListView)findViewById(R.id.listStudent);
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
    public void onClickNom(Participant item, int position) {

    }
}
