package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;


public class EvenementActivity extends ActionBarActivity {

    private Evenement evenement = new Evenement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement);

        evenement = getIntent().getParcelableExtra("EVENEMENT");

        Button buttonParticipants = (Button)findViewById(R.id.button_participant);
        Button buttonDepenses = (Button)findViewById(R.id.button_depenses);
        buttonParticipants.setOnClickListener(new ParticipantButtonListener());
        buttonDepenses.setOnClickListener(new DepensesButtonListener());
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


    public class ParticipantButtonListener implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(EvenementActivity.this, ParticipantsActivity.class);
            intent.putExtra("EVENEMENT", evenement);
            startActivity(intent);
        }
    }

    public class DepensesButtonListener implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent = new Intent(EvenementActivity.this, DepensesActivity.class);
            intent.putExtra("EVENEMENT", evenement);
            startActivity(intent);
        }
    }
}
