package ca.easyevent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.adapter.DepenseAdapter;
import ca.easyevent.adapter.DepenseAdapterListener;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Evenement;

public class DepenseListActivity extends Activity implements DepenseAdapterListener{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    Evenement evenement;
    ArrayList<Depense> listDepense;
    DepenseAdapter adapter;

    ListView listDepenseView;
    private static final int ADD_NEW_DEPENSE=100;

    /*##############################################################################################
                                    CREATION
    ###############################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depense_list_activity);

        evenement = getIntent().getParcelableExtra("EVENEMENT");

        adapter = new DepenseAdapter(this, evenement.getListDepense());
        adapter.addListener(this);


        ListView list = (ListView)findViewById(R.id.listDepense);
        list.setAdapter(adapter);
        final LinearLayout addFlottingButton = (LinearLayout)findViewById(R.id.add_button_layout);
        addFlottingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepenseListActivity.this, DepenseFormActivity.class);
                intent.putExtra("EVENEMENT", evenement);
                intent.putExtra("LIB_DEPENSE", "");
                startActivityForResult(intent, ADD_NEW_DEPENSE);
            }
        });

        ImageView addButton = (ImageView)findViewById(R.id.add_button);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            evenement = getIntent().getParcelableExtra("EVENEMENT");
            adapter = new DepenseAdapter(this, evenement.getListDepense());
            adapter.addListener(this);
            listDepenseView.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent result = new Intent();
        result.putExtra("EVENEMENT", evenement);
        setResult(RESULT_OK, result);
        finish();
    }


    /*##############################################################################################
                       COMPORTEMENT ADAPTER LISTENER
    ###############################################################################################*/

    @Override
    public void onClickDepense(Depense item, int position) {
        Intent intent = new Intent(DepenseListActivity.this, DepenseActivity.class);
        intent.putExtra("EVENEMENT", evenement);
        intent.putExtra("LIB_DEPENSE", item.getLibelle());
        startActivityForResult(intent,0);
    }
}
