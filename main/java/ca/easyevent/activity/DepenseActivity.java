package ca.easyevent.activity;

        import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.adapter.ParticipationAdapter;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;


public class DepenseActivity extends Activity  {

    /*##############################################################################################
									ATRIBUTS
	###############################################################################################*/

    private Evenement evenement;
    private Depense depense;

    private ParticipationAdapter adapter;

    private TextView libelleText, montantDepenseText, dateText;
    private ListView list;

    private static final int EDIT_DEPENSE = 101;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_activity);

        evenement = getIntent().getParcelableExtra("EVENEMENT");
        String libelleDepense = getIntent().getStringExtra("LIB_DEPENSE");
        this.depense = evenement.getDepense(libelleDepense);

                //Text
        libelleText = (TextView)this.findViewById(R.id.lib_dep_text);
        montantDepenseText = (TextView)this.findViewById(R.id.budget_tot_dep_valor);
        dateText = (TextView)this.findViewById(R.id.date_dep_text);

        libelleText.setText(depense.getLibelle());
        dateText.setText(depense.getDate().toStringDate());
        montantDepenseText.setText((int)depense.getMontantTotal()+ " $");


                //Participations
        adapter = new ParticipationAdapter(this, depense.getListeParticipation());
        list = (ListView)findViewById(R.id.list_participation);

                //Button edition
        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.edit_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepenseActivity.this, DepenseFormActivity.class);
                intent.putExtra("EVENEMENT", evenement);
                intent.putExtra("LIB_DEPENSE", depense.getLibelle());
                startActivityForResult(intent, EDIT_DEPENSE);
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.edit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlottingButton.performClick();
            }
        });

    }


    /*##############################################################################################
                              COMPORTEMENT D'ACTIVITY
    ###############################################################################################*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            evenement = data.getParcelableExtra("EVENEMENT");
            String libelleDepense = getIntent().getStringExtra("LIB_DEPENSE");
            this.depense = evenement.getDepense(libelleDepense);
            adapter = new ParticipationAdapter(this, depense.getListeParticipation());
            list.setAdapter(adapter);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        Intent result = new Intent();
        result.putExtra("EVENEMENT", evenement);
        setResult(RESULT_OK, result);
        finish();
    }

}
