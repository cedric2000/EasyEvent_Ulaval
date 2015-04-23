package ca.easyevent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ca.easyevent.R;
import ca.easyevent.database.DAODepense;
import ca.easyevent.database.DAOEvenement;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Rapport;
import ca.easyevent.utils.ImageManager;


public class EvenementActivity extends ActionBarActivity {

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private Evenement evenement;
    private DAOEvenement evenementDAO;
    private ArrayList<Participant> listParticipant;
    private ArrayList<Depense> listDepense;

    /*##############################################################################################
									CREATION
	###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);


        initButtons();
    }

    protected  void onResume(){
        super.onResume();
        evenementDAO = new DAOEvenement(this);
        evenementDAO.open();

        long idEvenement = getIntent().getLongExtra("EVENEMENT", -1);
        evenement = evenementDAO.getEvenement(idEvenement);
        evenementDAO.close();

        initView();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /*##############################################################################################
                                    INIT VIEW
    ###############################################################################################*/

    public void initView(){
        TextView titreText = (TextView)findViewById(R.id.nameEventText),
                dateText = (TextView)findViewById(R.id.date_event);

             //Evenement
        titreText.setText(evenement.getTitre());

        String date;
        if(evenement.getDateFin() != null && !evenement.getDateFin().equals(""))
            date = "Du " + evenement.getDateDebut().toString() +
                    " Au " +evenement.getDateFin();
        else
            date = "Le " + evenement.getDateDebut().toString();
        dateText.setText(date);

        initImage();
        initParticipantCard();
        initDepenseCard();
        initRapportCard();
        initLieuCard();
    }


    public void initImage() {
        ImageView imageView = (ImageView)findViewById(R.id.eventCover);
        if (evenement.getImage() != null && !evenement.getImage().equals("default")) {
            Display display = getWindowManager().getDefaultDisplay();
            double deviceWidth = display.getWidth();

            ImageManager imageManager = new ImageManager(this, deviceWidth);
            BitmapFactory.Options options = new BitmapFactory.Options();

            Bitmap bitmap = BitmapFactory.decodeFile(evenement.getImage(), options);
            imageView.setImageDrawable(imageManager.getResizeImage(bitmap, options));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else
            imageView.setVisibility(View.GONE);
    }

    public void initParticipantCard(){

        TextView cardEmpty = (TextView)findViewById(R.id.part_card_empty);
        LinearLayout bestPartLayout= (LinearLayout)findViewById(R.id.best_part_layout),
                    worstPartLayout= (LinearLayout)findViewById(R.id.worst_part_layout);
                
        DAOParticipant participantDAO = new DAOParticipant(this);
        participantDAO.open();
        listParticipant = participantDAO.getAllParticipants(evenement.getId());
        participantDAO.close();
        if(listParticipant.size() < 2){
            bestPartLayout.setVisibility(View.GONE);
            worstPartLayout.setVisibility(View.GONE);
            cardEmpty.setVisibility(View.VISIBLE);
        }
        
        else{
            Collections.sort(listParticipant);

            TextView bestPartNameText = (TextView)findViewById(R.id.best_part_name),
                    bestPartEquiText = (TextView)findViewById(R.id.best_part_budget),
                    worstPartNameText= (TextView)findViewById(R.id.worst_part_name),
                    worstPartEquiText= (TextView)findViewById(R.id.worst_part_budget);
            
            bestPartNameText.setText(listParticipant.get(0).getName());
            bestPartEquiText.setText(((int)listParticipant.get(0).getEquiPersoTotal())+" $");
            worstPartNameText.setText(listParticipant.get(listParticipant.size()-1).getName());
            worstPartEquiText.setText(((int)listParticipant.get(listParticipant.size()-1).getEquiPersoTotal())+" $");
            
            bestPartLayout.setVisibility(View.VISIBLE);
            worstPartLayout.setVisibility(View.VISIBLE);
            cardEmpty.setVisibility(View.GONE);
        }
    }

    public void initDepenseCard(){

        TextView cardEmpty = (TextView)findViewById(R.id.dep_card_empty);
        LinearLayout budgDepLayout= (LinearLayout)findViewById(R.id.budg_dep_layout);

        DAODepense depenseDAO = new DAODepense(this);
        depenseDAO.open();
        listDepense = depenseDAO.getAllDepenses(evenement.getId());
        depenseDAO.close();
        if(listDepense.isEmpty()){
            budgDepLayout.setVisibility(View.GONE);
            cardEmpty.setVisibility(View.VISIBLE);
        }

        else{
                //Calcut budget total
            double montantTotaleDepenses =0;
            for (Depense depense :listDepense)
                montantTotaleDepenses += depense.getMontantTotal();

            TextView budgetTotDepenseText = (TextView)findViewById(R.id.budget_total);
            budgetTotDepenseText.setText(montantTotaleDepenses+"");

            budgDepLayout.setVisibility(View.VISIBLE);
            cardEmpty.setVisibility(View.GONE);
        }
    }

    public void initRapportCard(){
        TextView desequiText = (TextView)findViewById(R.id.rap_card_empty);
        TextView buttonSendRapport = (TextView)findViewById(R.id.button_send_rap);
        if(this.listParticipant.size()<2 || this.listDepense.isEmpty()) {
            desequiText.setText("Pas assez de données");
            buttonSendRapport.setVisibility(View.GONE);
        }
        else{
            Rapport rapport = new Rapport(this.listParticipant);
            desequiText.setText(rapport.toString());
            buttonSendRapport.setVisibility(View.VISIBLE);
        }
    }

    public void initLieuCard(){
        TextView placeText = (TextView)findViewById(R.id.place_name);
        placeText.setText(evenement.getLieu());
    }


    /*##############################################################################################
									INITIALISATION DES BOUTTONS
	###############################################################################################*/

    private void initButtons(){
        TextView buttonParticipants = (TextView)findViewById(R.id.button_participant),
                buttonDepenses = (TextView)findViewById(R.id.button_depenses),
                buttonSendRapport = (TextView)findViewById(R.id.button_send_rap),
                buttonPlace = (TextView)findViewById(R.id.button_place);


        buttonParticipants.setOnClickListener(new ButtonListener(ParticipantListActivity.class));
        buttonDepenses.setOnClickListener(new ButtonListener(DepenseListActivity.class));
        buttonSendRapport.setOnClickListener(new ButtonListener(SendResumeActivity.class));

            //Button edition
        final View editFlottingButton = findViewById(R.id.edit_button);
        editFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvenementActivity.this, EvenementFormActivity.class);
                intent.putExtra("EVENEMENT", evenement.getId());
                startActivity(intent);
            }
        });
    }

    public class ButtonListener implements View.OnClickListener{
        private Class<?> cls;

        public ButtonListener( Class<?> cls) {
            this.cls = cls;
        }

        public void onClick(View v)
        {
            Intent intent = new Intent(EvenementActivity.this, cls);
            intent.putExtra("EVENEMENT",evenement.getId());
            startActivity(intent);
        }
    }

}
