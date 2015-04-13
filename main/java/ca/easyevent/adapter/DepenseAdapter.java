package ca.easyevent.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Depense;


public class DepenseAdapter extends ArrayAdapter<Depense>{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Depense> listDepense;
    private Activity activity;
    private ArrayList<DepenseAdapterListener> listListener = new ArrayList<>();


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DepenseAdapter(Activity activity, ArrayList<Depense> listDepense){
        super(activity, R.layout.depense_item, listDepense);
        this.activity = activity;
        this.listDepense=listDepense;
    }




    /*##############################################################################################
									CONTROL VIEW
	###############################################################################################*/

    static class ViewHolder {
        public TextView libelleText, depenseursText, montantDepenseText;
        public LinearLayout goToDepenseLayout;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        View childView = arg1;
        if(childView == null || childView.getTag() == null){

            childView = activity.getLayoutInflater().inflate(R.layout.depense_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.libelleText = (TextView)childView.findViewById(R.id.lib_dep);
            viewHolder.depenseursText = (TextView)childView.findViewById(R.id.depenseurs);
            viewHolder.montantDepenseText = (TextView)childView.findViewById(R.id.montant_depense);
            viewHolder.goToDepenseLayout = (LinearLayout)childView.findViewById(R.id.goToDepenseLayout);

            childView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) childView.getTag();

        holder.libelleText.setText(listDepense.get(position).getLibelle());
        holder.depenseursText.setText("Bob");
        holder.montantDepenseText.setText( ((int)listDepense.get(position).getMontantTotal()) + " $");

        holder.goToDepenseLayout.setTag(position);
        holder.goToDepenseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(listDepense.get(position), position);
            }
        });

        return childView;
    }


    /*##############################################################################################
									ACCESSEURS
	###############################################################################################*/

    @Override
    public int getCount() {
        return listDepense.size();
    }

    @Override
    public Depense getItem(int arg0) {
        return listDepense.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
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
