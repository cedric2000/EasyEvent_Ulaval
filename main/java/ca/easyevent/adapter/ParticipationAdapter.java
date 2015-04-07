package ca.easyevent.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Participation;


public class ParticipationAdapter extends ArrayAdapter<Participation>{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Participation> participationList;
    private Activity activity;


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public ParticipationAdapter(Activity activity, ArrayList<Participation> inParticipationList){

        super(activity, R.layout.participation_maj_item, inParticipationList);
        this.activity = activity;
        this.participationList=inParticipationList;
    }


    /*##############################################################################################
									CONTROL VIEW
	###############################################################################################*/

    static class ViewHolder {
        public TextView nameParticipation;
        public TextView montantParticipation;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        View childView = arg1;
        if(childView == null || childView.getTag() == null){

            childView = activity.getLayoutInflater().inflate(R.layout.participation_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.nameParticipation = (TextView) childView.findViewById(R.id.nameParticipationText);
            viewHolder.montantParticipation = (TextView) childView.findViewById(R.id.montant_participation);
            childView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) childView.getTag();

        holder.nameParticipation.setText(participationList.get(position).getParticipant().getName());
        holder.montantParticipation.setText(participationList.get(position).getMontant()+"");

        return childView;
    }

    /*##############################################################################################
									ACCESSEURS
	###############################################################################################*/


    @Override
    public int getCount() {
        return participationList.size();
    }

    @Override
    public Participation getItem(int arg0) {
        return participationList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
}