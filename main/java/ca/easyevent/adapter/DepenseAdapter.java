package ca.easyevent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Depense;


public class DepenseAdapter extends BaseAdapter {

	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Depense> listDepense;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DepenseAdapterListener> listListener = new ArrayList<>();

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DepenseAdapter(ArrayList<Depense> listDepense, Context context) {
        this.listDepense = listDepense;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    /*################################################################################################
                                        ACCESSEUR
    ##################################################################################################*/
    @Override
    public int getCount() {
        return listDepense.size();
    }

    public Object getItem(int position) {
        return listDepense.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        RelativeLayout layoutItem;
        if (convertView == null) {
            layoutItem = (RelativeLayout) inflater.inflate(R.layout.item_depense, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        TextView participantDepenseText = (TextView)layoutItem.findViewById(R.id.participantDepenseText);
        TextView libelleDepenseText = (TextView)layoutItem.findViewById(R.id.libelleDepenseText);
        TextView montantDepenseText = (TextView)layoutItem.findViewById(R.id.montantDepenseText);

        participantDepenseText.setText("Bob");
        libelleDepenseText.setText(listDepense.get(position).getLibelle());
        montantDepenseText.setText(listDepense.get(position).getMontantTotal()+"");

        participantDepenseText.setTag(position);
        participantDepenseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(listDepense.get(position), position);
            }
        });

        return layoutItem;
    }


	/*################################################################################################
								COMPORTEMENT LISTENER
	##################################################################################################*/

    public void addListener(DepenseAdapterListener listener) {
        listListener.add(listener);
    }

    private void sendListener(Depense item, int position) {
        for(int i = listListener.size()-1; i >= 0; i--)
            listListener.get(i).onClickDepense(item, position);
    }

}
