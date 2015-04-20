package ca.easyevent.activity;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.SendRapportAdapter;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Rapport;

public class SendResumeActivity extends Activity {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/


    private SendRapportAdapter adapter;
    private ListView list ;

    private DAOParticipant participantDAO;
    private ArrayList<Participant> listParticipant;

    private long idEvenement ;
    private Rapport rapport;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_resume_activity);

        participantDAO = new DAOParticipant(this);

                /*=============================
                      Button Submit
                =============================*/
        View sendButton = (View)findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRapport();
            }
        });

    }

    protected  void onResume(){
        super.onResume();
        idEvenement = getIntent().getLongExtra("EVENEMENT", 0);

        participantDAO.open();
        listParticipant = participantDAO.getAllParticipants(idEvenement);
        participantDAO.close();

        adapter = new SendRapportAdapter(this,listParticipant);
        list = (ListView)findViewById(R.id.listParticipant);
        list.setAdapter(adapter);

        rapport = new Rapport(this.listParticipant);
    }

    /*##############################################################################################
                                   SEND RAPPORT
    ###############################################################################################*/

    public void sendRapport(){
        ArrayList<Participant> listEnvoie = new ArrayList<>();
        for(Participant p : adapter.getListParticipant()) {
            if(p.isSelected())
                listEnvoie.add(p);
        }
        sensSMS(listEnvoie);
        participantDAO.close();
        finish();
    }

	/*##############################################################################################
		                SENS SMS
	###############################################################################################*/

    public void sensSMS(ArrayList<Participant> listEnvoie){

        for(Participant participant : listEnvoie) {
            String phoneNo = participant.getTelephone();
            String sms = rapport.toString();
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS non envoyé à " + participant.getName(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

}
