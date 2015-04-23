package ca.easyevent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.ParticipationAdapter;
import ca.easyevent.database.DAODepense;
import ca.easyevent.database.DAOParticipation;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Participation;


public class DepenseActivity extends Activity  {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private long idEvent;
    private Depense depense;

    private ParticipationAdapter adapter;

    private TextView libelleText, montantDepenseText, dateText;
    private ListView list;

    private DAODepense depenseDAO;
    private DAOParticipation participationDAO;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_activity);

        libelleText = (TextView)this.findViewById(R.id.lib_dep_text);
        montantDepenseText = (TextView)this.findViewById(R.id.budget_tot_dep_valor);
        dateText = (TextView)this.findViewById(R.id.date_dep_text);

                //Button edition
        final View editFlottingButton = findViewById(R.id.edit_button);
        editFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepenseActivity.this, DepenseFormActivity.class);
                intent.putExtra("DEPENSE", depense.getId());
                startActivity(intent);
            }
        });

    }

    protected  void onResume(){
        super.onResume();

        long idDepense = getIntent().getLongExtra("DEPENSE", 0);
        depenseDAO = new DAODepense(this);
        depenseDAO.open();
        depense = depenseDAO.getDepense(idDepense);

            //Text
        libelleText.setText(depense.getLibelle());
        dateText.setText(depense.getDate().toString());
        montantDepenseText.setText((int)depense.getMontantTotal()+ " $");

        //Participations
        participationDAO = new DAOParticipation(this);
        participationDAO.open();
        ArrayList<Participation> listParticipation = new ArrayList<>();
        for (Participation participation : participationDAO.getAllParticipationsForDepense(idDepense)) {
            if(participation.isSelected())
                listParticipation.add(participation);
        }
        adapter = new ParticipationAdapter(this, listParticipation);
        list = (ListView)findViewById(R.id.list_participation);
        list.setAdapter(adapter);

        depenseDAO.close();
        participationDAO.close();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
