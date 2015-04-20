package ca.easyevent.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.database.DAODepense;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.database.DAOParticipation;
import ca.easyevent.model.Depense;
import ca.easyevent.model.Participant;
import ca.easyevent.model.Participation;


public class DepenseAdapter extends ArrayAdapter<Depense>{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Depense> listDepense;
    private ArrayList<Participant> listParticipantEvent;
    private Activity activity;
    private ArrayList<DepenseAdapterListener> listListener = new ArrayList<>();


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public DepenseAdapter(Activity activity, ArrayList<Depense> listDepense, ArrayList<Participant> listParticipantEvent){
        super(activity, R.layout.depense_item, listDepense);
        this.activity = activity;
        this.listDepense=listDepense;
        this.listParticipantEvent=listParticipantEvent;
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
        holder.depenseursText.setText(getParticipantString(listDepense.get(position).getId()));
        holder.montantDepenseText.setText( ((int)listDepense.get(position).getMontantTotal()) + " $");

        final int pos = position;
        holder.goToDepenseLayout.setTag(position);
        holder.goToDepenseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendListener(listDepense.get(pos), pos);
            }
        });

        return childView;
    }


    /*##############################################################################################
									PARTICIPANT NAME
	###############################################################################################*/


    public String getParticipantString(long idDepense) {

            //Depense
        DAODepense depenseDAO = new DAODepense (activity);
        depenseDAO.open();
        Depense depense = depenseDAO.getDepense(idDepense);
        depenseDAO.close();

            //Participation à la dépense
        DAOParticipation participationDAO = new DAOParticipation (activity);
        participationDAO.open();
        ArrayList<Participation> listParticipation = participationDAO.getAllParticipationsForDepense(idDepense);
        participationDAO.close();

            //Participant à la dépense
        DAOParticipant participantDAO = new DAOParticipant (activity);
        participantDAO.open();
        ArrayList<Participant> participantDepense = new ArrayList<>();
        ArrayList<Participant> unParticipantDepense = new ArrayList<>();
        for (Participation participation : listParticipation) {
            Participant p = participantDAO.getParticipant(participation.getIdParticipant());
            if(participation.isSelected())
                participantDepense.add(p);
            else
                unParticipantDepense.add(p);
        }
        participantDAO.close();

            //Construction String
        String result ="";
        int sizeParticipantDepense = participantDepense.size(),
                sizeUnparticipantDepense = unParticipantDepense.size();

        if(sizeUnparticipantDepense == 0){
            result = "Tous";
        }
        else if(sizeUnparticipantDepense < sizeParticipantDepense){
            result = "Tous sauf ";
            for (Participant participant :unParticipantDepense)
                result += participant.getName()+", ";
        }
        else{
            for (Participant participant : participantDepense)
                result += participant.getName()+", ";
        }

        return result;
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
