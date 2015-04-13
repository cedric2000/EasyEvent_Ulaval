package ca.easyevent.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.database.DAOParticipant;
import ca.easyevent.model.Participation;


public class ParticipationFormAdapter extends ArrayAdapter<Participation>{

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Participation> participationList;
    private Activity activity;
    private ArrayList<ParticipationAdapterListener> listListener = new ArrayList<>();

    private DAOParticipant participantDAO;

    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public ParticipationFormAdapter(Activity activity, ArrayList<Participation> inParticipationList){

        super(activity, R.layout.participation_maj_item, inParticipationList);
        this.activity = activity;
        this.participationList=inParticipationList;
        participantDAO = new DAOParticipant(activity);
        participantDAO.open();
    }


    /*##############################################################################################
									CONTROL VIEW
	###############################################################################################*/

    static class ViewHolder {
        public CheckBox checkBox;
        public TextView nameParticipation;
        public EditText montantParticipation;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        View childView = arg1;
        if(childView == null || childView.getTag() == null){

            childView = activity.getLayoutInflater().inflate(R.layout.participation_maj_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.checkBox = (CheckBox) childView.findViewById(R.id.checkBox);
            viewHolder.nameParticipation = (TextView) childView.findViewById(R.id.nameParticipationText);
            viewHolder.montantParticipation = (EditText) childView.findViewById(R.id.montant_participation);
            childView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) childView.getTag();

        Participation participation = participationList.get(position);
        String participantName = participantDAO.getParticipant(participation.getIdParticipant()).getName();

        holder.checkBox.setChecked(participation.isSelected());
        holder.nameParticipation.setText(participantName);
        holder.montantParticipation.setText(participation.getMontant()+"");

        final int pos = position;
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    participationList.get(pos).setSelected(isChecked);
                    sendListener();
            }
        });
        holder.montantParticipation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
             if (!hasFocus){
                 participationList.get(pos).setMontant(
                         Double.valueOf(holder.montantParticipation.getText().toString())
                     );
                 sendListener();
             }
             }
         });
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

    public ArrayList<Participation> getListParticipation() {
        return participationList;
    }

    /*################################################################################################
								COMPORTEMENT LISTENER
	##################################################################################################*/

    public void addListener(ParticipationAdapterListener listener) {
        listListener.add(listener);
    }

    private void sendListener() {
        for(int i = listListener.size()-1; i >= 0; i--)
            listListener.get(i).updateCalcul();
    }

}