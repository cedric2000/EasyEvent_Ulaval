package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;


public class EvenementsActivity extends ActionBarActivity {


    public final static int PARTICIPANTS_REQUEST = 0;
    public final static int DEPENSES_REQUEST = 1;

    private ArrayList<Evenement> listEvenement = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenements);

        Button buttonParticipants = (Button)findViewById(R.id.button_participant);
        Button buttonDepenses = (Button)findViewById(R.id.button_depenses);

        buttonParticipants.setOnClickListener(new ParticipantButtonListener());
        buttonDepenses.setOnClickListener(new DepensesButtonListener());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    public class ParticipantButtonListener implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(EvenementsActivity.this, ParticipantsActivity.class);
            intent.putExtra("PARTICIPANTS", listEvenement.get(0));
            startActivityForResult(intent, PARTICIPANTS_REQUEST);
        }
    }

    public class DepensesButtonListener implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(EvenementsActivity.this, DepenseActivity.class);
            intent.putExtra("DEPENSES", listEvenement);
            startActivityForResult(intent, DEPENSES_REQUEST);
        }
    }
}
