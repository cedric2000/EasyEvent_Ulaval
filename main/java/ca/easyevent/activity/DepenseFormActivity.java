package ca.easyevent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ca.easyevent.R;
import ca.easyevent.adapter.ParticipationAdapterListener;
import ca.easyevent.adapter.ParticipationFormAdapter;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Participation;
import ca.easyevent.utils.DateModifiable;

public class DepenseFormActivity extends Activity implements ParticipationAdapterListener {

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private Evenement evenement;
    private Depense depense;
    private ParticipationFormAdapter adapter;

    private EditText libelleText,  dateText;
    private TextView montantDepenseText;
    private ListView listParticipationView;


    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_form_activity);

        libelleText = (EditText)this.findViewById(R.id.lib_dep_text);
        montantDepenseText = (TextView)this.findViewById(R.id.budget_tot_dep_valor);
        dateText = (EditText)this.findViewById(R.id.date_dep_text);

        evenement = getIntent().getParcelableExtra("EVENEMENT");

            //On recupère la dépense si on est en mode édition
        String libelleDepense = getIntent().getStringExtra("LIB_DEPENSE");
        if(libelleDepense.equals(""))
            this.initFormNewDepense();
        else
            this.initFormWithDepense(libelleDepense);
    }


    /*##############################################################################################
                                    INIT FORMULAIRE
    ###############################################################################################*/

    public void initFormWithDepense(String libelle){
        this.depense = evenement.getDepense(libelle);

        libelleText.setText(this.depense.getLibelle(),TextView.BufferType.EDITABLE);
        dateText.setText(this.depense.getDate().toStringDate(),TextView.BufferType.EDITABLE);
        montantDepenseText.setText(this.depense.getMontantTotal()+"",TextView.BufferType.EDITABLE);
        adapter = new ParticipationFormAdapter(this, this.depense.getListeParticipation());
    }

    public void initFormNewDepense(){
        depense = new Depense();

        for(Participant participant : evenement.getListeParticipant()){
            Participation p = new Participation(participant,depense,0);
            depense.addParticipation(p);
            participant.addParticipation(p);
        }
        adapter = new ParticipationFormAdapter(this, depense.getListeParticipation());
    }

    /*##############################################################################################
                                    SUBMIT FORM
    ###############################################################################################*/

    public void submitForm(){
        depense.setLibelle(libelleText.getText().toString());
        depense.setDate(new DateModifiable("11/09/1991"));
        evenement.ajoutDepense(depense);

        Intent result = new Intent();
        result.putExtra("EVENEMENT", evenement);
        result.putExtra("LIB_DEPENSE", depense.getLibelle());
        setResult(RESULT_OK, result);
        finish();
    }

    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void updateCalcul() {
        depense.calculMontantTotal();
        this.montantDepenseText.setText(depense.getMontantTotal() +"");
    }

}
