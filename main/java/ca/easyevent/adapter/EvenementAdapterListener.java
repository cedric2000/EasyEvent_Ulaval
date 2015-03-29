package ca.easyevent.adapter;

import ca.easyevent.model.Evenement;

/**
 * Created by Cédric on 26/03/2015.
 */
public interface EvenementAdapterListener {
    public void onClickEvenement(Evenement item, int position);
}
