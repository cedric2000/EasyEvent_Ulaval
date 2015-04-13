package ca.easyevent.activity;

        import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        depenseDAO = new DAODepense(this);
        long idDepense = getIntent().getLongExtra("DEPENSE", 0);
        depenseDAO.open();
        depense = depenseDAO.getDepense(idDepense);

        libelleText = (TextView)this.findViewById(R.id.lib_dep_text);
        montantDepenseText = (TextView)this.findViewById(R.id.budget_tot_dep_valor);
        dateText = (TextView)this.findViewById(R.id.date_dep_text);

                //Button edition
        final LinearLayout editFlottingButton = (LinearLayout)findViewById(R.id.edit_button_layout);
        editFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepenseActivity.this, DepenseFormActivity.class);
                intent.putExtra("DEPENSE", depense.getId());
                startActivity(intent);
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.edit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFlottingButton.performClick();
            }
        });

    }

    protected  void onResume(){
        super.onResume();
        long idDepense = getIntent().getLongExtra("DEPENSE", 0);

        depenseDAO.open();
        depense = depenseDAO.getDepense(idDepense);

            //Text
        libelleText.setText(depense.getLibelle());
        dateText.setText(depense.getDate().toStringDate());
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
        updateCalcul();

        depenseDAO.close();
        participationDAO.close();
    }



    public void updateCalcul() {
        double montantDepense = 0;
        for (Participation participation : adapter.getParticipationList()) {
            if (participation.isSelected())
                montantDepense += participation.getMontant();
        }
        depense.setMontantTotal(montantDepense);
        depense.setNbParticipant(adapter.getCount());

        for(Participation p : adapter.getParticipationList()) {
            if (p.isSelected()) {
                double equilibre = p.getMontant() - (montantDepense / depense.getNbParticipants());
                p.setEquilibre(equilibre);
            }
        }

        this.montantDepenseText.setText(depense.getMontantTotal() +" $ ");
    }
}
