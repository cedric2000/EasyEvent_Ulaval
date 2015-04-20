package ca.easyevent.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Participant;

public class SendRapportAdapter extends ArrayAdapter<Participant> {

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Participant> listParticipant;
    private Activity activity;


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public SendRapportAdapter(Activity activity, ArrayList<Participant> listParticipant){
        super(activity, R.layout.send_resume_item, listParticipant);
        this.activity = activity;
        this.listParticipant=listParticipant;
    }

    /*##############################################################################################
									CONTROL VIEW
	###############################################################################################*/

    static class ViewHolder {
        public TextView nameParticipantText;
        public ImageView smsIcon;
        public boolean isClick;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        View childView = arg1;
        if(childView == null || childView.getTag() == null){

            childView = activity.getLayoutInflater().inflate(R.layout.participant_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.nameParticipantText = (TextView)childView.findViewById(R.id.nameParticipantText);
            viewHolder.smsIcon = (ImageView)childView.findViewById(R.id.smsIcon);

            childView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) childView.getTag();
        holder.nameParticipantText.setText(listParticipant.get(position).getName());

        holder.smsIcon.setTag(position);
        holder.smsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();

                if(listParticipant.get(position).isSelected()) {
                    ((ImageView) v).setColorFilter(Color.argb(255, 158, 158, 158));
                    listParticipant.get(position).setSelected(false);
                }
                else {
                    ((ImageView) v).setColorFilter(Color.argb(255, 48, 53, 159));
                    listParticipant.get(position).setSelected(true);
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
        return listParticipant.size();
    }

    @Override
    public Participant getItem(int arg0) {
        return listParticipant.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public ArrayList<Participant> getListParticipant() {
        return listParticipant;
    }

    public void setListParticipant(ArrayList<Participant> listParticipant) {
        this.listParticipant = listParticipant;
    }
}
