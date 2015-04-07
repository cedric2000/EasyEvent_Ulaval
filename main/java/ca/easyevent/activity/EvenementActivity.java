package ca.easyevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;


public class EvenementActivity extends ActionBarActivity {

    private Evenement evenement = new Evenement();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);

        evenement = getIntent().getParcelableExtra("EVENEMENT");
        initButtons();
    }

    private void initButtons(){
        TextView buttonParticipants = (TextView)findViewById(R.id.button_participant),
                buttonDepenses = (TextView)findViewById(R.id.button_depenses),
                buttonSeeRapport = (TextView)findViewById(R.id.button_see_rap),
                buttonSendRapport = (TextView)findViewById(R.id.button_send_rap),
                buttonPlace = (TextView)findViewById(R.id.button_place);

        buttonParticipants.setOnClickListener(new ButtonListener(ParticipantListActivity.class));
        buttonDepenses.setOnClickListener(new ButtonListener(DepenseListActivity.class));
    }

    public class ButtonListener implements View.OnClickListener{
        private Class<?> cls;

        public ButtonListener( Class<?> cls) {
            this.cls = cls;
        }

        public void onClick(View v)
        {
            Intent intent = new Intent(EvenementActivity.this, cls);
            if(cls.equals(ParticipantListActivity.class)||cls.equals(DepenseListActivity.class))
                intent.putExtra("EVENEMENT", evenement);
            startActivity(intent);
        }
    }

}
