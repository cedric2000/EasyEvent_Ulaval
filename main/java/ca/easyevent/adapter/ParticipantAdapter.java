package ca.easyevent.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ca.easyevent.R;
import ca.easyevent.model.Participant;

/**
 * Created by Cédric on 13/03/2015.
 */
public class ParticipantAdapter extends BaseAdapter {

	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Participant> listParticipant;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ParticipantAdapterListener> listListener = new ArrayList<ParticipantAdapterListener>();

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public ParticipantAdapter(ArrayList<Participant> listParticipant, Context context) {
        this.listParticipant = listParticipant;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    /*################################################################################################
                                        ACCESSEUR
    ##################################################################################################*/
    @Override
    public int getCount() {
        return listParticipant.size();
    }

    public Object getItem(int position) {
        return listParticipant.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;
        if (convertView == null) {
            layoutItem = (RelativeLayout) inflater.inflate(R.layout.item_participant, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        TextView nameParticipantText = (TextView)layoutItem.findViewById(R.id.nameParticipantText);
        TextView balanceParticipantText = (TextView)layoutItem.findViewById(R.id.balanceParticipantText);

        LinearLayout participantImage = (LinearLayout) layoutItem.findViewById(R.id.contactLayout);

        ImageView goToParticipantIcon = (ImageView) layoutItem.findViewById(R.id.goToParticipantIcon);


        nameParticipantText.setText(listParticipant.get(position).getName());
        balanceParticipantText.setText(listParticipant.get(position).getEquiPersoTotal()+"");

        int[] androidColors = context.getResources().getIntArray(R.array.primary_color);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        GradientDrawable bgShape = (GradientDrawable)participantImage.getBackground();
        bgShape.setColor(randomAndroidColor);

        /* Si image
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right);
        RoundImage roundedImage = new RoundImage(bm);
        participantImage.setImageDrawable(roundedImage);
        */

        goToParticipantIcon.setTag(position);
        goToParticipantIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(listParticipant.get(position), position);
                System.out.println("Click sur : ");
            }
        });

        return layoutItem;
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
