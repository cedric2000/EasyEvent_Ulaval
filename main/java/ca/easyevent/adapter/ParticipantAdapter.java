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
import ca.easyevent.model.Participant;

/**
 * Created by Cédric on 13/03/2015.
 */
public class ParticipantAdapter extends BaseAdapter {

    private ArrayList<Participant> listParticipant;
    private Context context;
    private LayoutInflater inflater;


    public ParticipantAdapter(ArrayList<Participant> listParticipant, Context context) {
        this.listParticipant = listParticipant;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    private ArrayList<ParticipantAdapterListener> listListener = new ArrayList<ParticipantAdapterListener>();
    public void addListener(ParticipantAdapterListener listener) {
        listListener.add(listener);
    }
    private void sendListener(Participant item, int position) {
        for(int i = listListener.size()-1; i >= 0; i--)
            listListener.get(i).onClickNom(item, position);
    }

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

        nameParticipantText.setText(listParticipant.get(position).getName());
        balanceParticipantText.setText(listParticipant.get(position).getEquiPersoTotal()+"");

        nameParticipantText.setTag(position);
        nameParticipantText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(listParticipant.get(position), position);
            }
        });

        return layoutItem;
    }
}
