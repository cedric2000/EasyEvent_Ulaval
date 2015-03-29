package ca.easyevent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;

/**
 * Created by Cédric on 26/03/2015.
 */
public class EvenementAdapter extends BaseAdapter {

	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private ArrayList<Evenement> listEvenement;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<EvenementAdapterListener> listListener = new ArrayList<EvenementAdapterListener>();

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public EvenementAdapter(ArrayList<Evenement> listEvenement, Context context) {
        this.listEvenement = listEvenement;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    /*################################################################################################
                                        ACCESSEUR
    ##################################################################################################*/
    @Override
    public int getCount() {
        return listEvenement.size();
    }

    public Object getItem(int position) {
        return listEvenement.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;
        if (convertView == null) {
            layoutItem = (RelativeLayout) inflater.inflate(R.layout.item_evenement, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        TextView nameEventText = (TextView) layoutItem.findViewById(R.id.nameEventText);
        TextView dateEventText = (TextView) layoutItem.findViewById(R.id.dateEventText);
        TextView placeEventText = (TextView) layoutItem.findViewById(R.id.placeEventText);
        TextView goToEvenement = (TextView) layoutItem.findViewById(R.id.goToEvenement);
        ImageView eventImageView = (ImageView) layoutItem.findViewById(R.id.eventImageView);

        nameEventText.setText(listEvenement.get(position).getTitre());
        dateEventText.setText(listEvenement.get(position).getDateDebut().toStringDate() + "  - " +
                listEvenement.get(position).getDateFin());
        placeEventText.setText(listEvenement.get(position).getLieu());
/*
        int imageResource = context.getResources().getIdentifier("drawable/road.png", null, context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
        eventImageView.setImageBitmap(bitmap);
*/
        goToEvenement.setTag(position);
        goToEvenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(listEvenement.get(position), position);
            }
        });

        return layoutItem;
    }

	/*################################################################################################
								COMPORTEMENT LISTENER
	##################################################################################################*/

    public void addListener(EvenementAdapterListener listener) {
        listListener.add(listener);
    }

    private void sendListener(Evenement item, int position) {
        for (int i = listListener.size() - 1; i >= 0; i--)
            listListener.get(i).onClickEvenement(item, position);
    }

}