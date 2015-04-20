package ca.easyevent.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.easyevent.R;
import ca.easyevent.model.Evenement;
import ca.easyevent.utils.ImageManager;

/**
 * Created by Cédric on 26/03/2015.
 */
public class EvenementAdapter extends ArrayAdapter<Evenement>
{
    /*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

        private ArrayList<Evenement> listEvenement;
        private Activity activity;
        private ArrayList<EvenementAdapterListener> listListener = new ArrayList<>();
        private ImageManager imageManager;


    /*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

    public EvenementAdapter(Activity activity, ArrayList<Evenement> listEvenement, ImageManager imageManager ){
        super(activity, R.layout.event_item, listEvenement);
        this.activity = activity;
        this.listEvenement=listEvenement;
        this.imageManager = imageManager;
    }

    /*##############################################################################################
                                   INIT IMAGE
    ###############################################################################################*/



    /*##############################################################################################
									CONTROL VIEW
	###############################################################################################*/

    static class ViewHolder {
        public TextView nameEventText, dateEventText, placeEventText, goToEvenement;
        public ImageView eventImageView;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        View childView = arg1;
        if(childView == null || childView.getTag() == null){

            childView = activity.getLayoutInflater().inflate(R.layout.event_item, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.nameEventText  = (TextView) childView.findViewById(R.id.nameEventText);
            viewHolder.dateEventText  = (TextView) childView.findViewById(R.id.dateEventText);
            viewHolder.placeEventText = (TextView) childView.findViewById(R.id.placeEventText);
            viewHolder.goToEvenement  = (TextView) childView.findViewById(R.id.goToEvenement);
            viewHolder.eventImageView = (ImageView)childView.findViewById(R.id.eventImageView);

            childView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) childView.getTag();

            //Text
        holder.nameEventText.setText(listEvenement.get(position).getTitre());
        holder.placeEventText.setText(listEvenement.get(position).getLieu());

            //Image
        if(listEvenement.get(position).getImage()!=null  && !listEvenement.get(position).getImage().equals("default")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(listEvenement.get(position).getImage(), options);
            holder.eventImageView.setImageDrawable(this.imageManager.getResizeImage(bitmap, options));
            holder.eventImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
            holder.eventImageView.setVisibility(View.GONE);

            //Date
        String date;
        if( listEvenement.get(position).getDateFin() != null)
            date = "Du " + listEvenement.get(position).getDateDebut().toString() +
                    " Au " + listEvenement.get(position).getDateFin();
        else
            date = "Le " + listEvenement.get(position).getDateDebut().toString();
        holder.dateEventText.setText(date);

            //button
        holder.goToEvenement.setTag(position);
        holder.goToEvenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(listEvenement.get(position), position);
            }
        });

        return childView;
    }

    /*##############################################################################################
									ACCESSEURS
	###############################################################################################*/

    @Override
    public int getCount() {
        return listEvenement.size();
    }

    @Override
    public Evenement getItem(int arg0) {
        return listEvenement.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
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