package ca.easyevent.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Participant;

public class ParticipantAdapter extends ArrayAdapter<Participant> {

    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Participant> listParticipant;
    private Activity activity;
    private ArrayList<ParticipantAdapterListener> listListener = new ArrayList<>();


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public ParticipantAdapter(Activity activity, ArrayList<Participant> listParticipant){
        super(activity, R.layout.participant_item, listParticipant);
        this.activity = activity;
        this.listParticipant=listParticipant;
    }

    /*##############################################################################################
									CONTROL VIEW
	###############################################################################################*/

    static class ViewHolder {
        public TextView nameParticipantText, balanceParticipantText;
        public LinearLayout goToParticipantLayout;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        View childView = arg1;
        if(childView == null || childView.getTag() == null){

            childView = activity.getLayoutInflater().inflate(R.layout.participant_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.nameParticipantText = (TextView)childView.findViewById(R.id.nameParticipantText);
            viewHolder.balanceParticipantText = (TextView)childView.findViewById(R.id.balanceParticipantText);
            viewHolder.goToParticipantLayout = (LinearLayout) childView.findViewById(R.id.goToParticipantLayout);

            childView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) childView.getTag();
        holder.nameParticipantText.setText(listParticipant.get(position).getName());
        holder.balanceParticipantText.setText((int)listParticipant.get(position).getEquiPersoTotal()+" $");
        if((int)listParticipant.get(position).getEquiPersoTotal()>0)
            holder.balanceParticipantText.setTextColor(Color.GREEN);
        else if((int)listParticipant.get(position).getEquiPersoTotal()<0)
            holder.balanceParticipantText.setTextColor(Color.RED);
        else
            holder.balanceParticipantText.setTextColor(Color.LTGRAY);


        /* Si image
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right);
        RoundImage roundedImage = new RoundImage(bm);
        participantImage.setImageDrawable(roundedImage);
        */

        holder.goToParticipantLayout.setTag(position);
        holder.goToParticipantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(listParticipant.get(position), position);
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

	/*################################################################################################
								COMPORTEMENT LISTENER
	##################################################################################################*/

    public void addListener(ParticipantAdapterListener listener) {
        listListener.add(listener);
    }

    private void sendListener(Participant item, int position) {
        for(int i = listListener.size()-1; i >= 0; i--)
            listListener.get(i).onClicParticipant(item, position);
    }


}
