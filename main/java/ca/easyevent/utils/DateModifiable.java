package ca.easyevent.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * <b>DateModifiable est la classe représentant l'Horloge du systéme.</b>
 * <p>
 * Une DateModifiable est caractérisé par l'attributs suivants :
 * <ul>
 * <li>GregorianCalendar c. Correspond au calentrier qui permet d'avoir une base
 * fixe pour les dates</li>
 * </ul>
 * </p>
 * 
 * @author Equipe PB8 2014
 * @version 1.0
 */
public class DateModifiable implements Parcelable {

	private GregorianCalendar c = new GregorianCalendar();

	// ##############################################################################################
	//                                  CONSTRUCTEUR
	// ##############################################################################################

	/**
	 * <b>Constructeur de DateModifiable</b>
	 * <p>
	 * A la construction de la l'objet DateModifiable ,on initialise la date
	 * avec la date courante du systéme
	 * </p>
	 * 
	 * @see
	 * @see
	 * 
	 */
	public DateModifiable() {
		c.setTime(new Date());
	}

	public DateModifiable(DateModifiable date) {
		c.set(date.getAnnee(), date.getMois()+1, date.getJourDuMois(),
				date.getHeure(), date.getMinute(), date.getSeconde());
	}

	    /**
     * <b>Constructeur de DateModifiable.</b>
     * <p>
     * A la construction de la l'objet DateModifiable ,on inialise la date avec
     * la date courante du systéme
     * </p>
     *
     * @param jour
     *            - Inialisation du jour du Calendrier
     * @param mois
     *            - Inialisation du mois du Calendrier
     * @param annee
     *            - Inialisation du annee du Calendrier
     * @param heure
     *            - Inialisation de l'heure de l'horloge
     * @param minute
     *            - Inialisation des minutes de l'horloge
     * @param seconde
     *            - Inialisation des secondes de l'horloge
     *
     */
    public DateModifiable(int jour, int mois, int annee, int heure, int minute, int seconde) {
        c.set(annee, mois - 1, jour, heure, minute, seconde);
    }

    /**
     * <b>Constructeur de DateModifiable.</b>
     * <p>
     * A la construction de la l'objet DateModifiable ,on inialise la date avec
     * la date courante du systéme
     * </p>
     *
     * @param date
     *            - Date sous la forme d'un string au format "JJ/MM/AAAA"
     *
     */
    public DateModifiable(String date) {
        int jour = Integer.valueOf(date.substring(0,2)),
                mois = Integer.valueOf(date.substring(3,5)),
                annee = Integer.valueOf(date.substring(6,10));
        c.set(annee, mois - 1, jour,0,0,0);
    }

    public DateModifiable(Parcel source){
        if(source.dataSize()>0){
            c.set(source.readInt(),source.readInt()+1,source.readInt(),
                    source.readInt(),source.readInt(),source.readInt());
        }
    }
    // ##############################################################################################
	// 									Incremente Seconde
	// ##############################################################################################
	
	
	/**
	 * Incremente la date de x secondes
	 * @param value - nombre de seconde a ajouter é la date modiiable
	 */
	public DateModifiable addSecond(int value) 
	{
		c.add(Calendar.SECOND, value);
		return this;
	}

	// ##############################################################################################
	// Temps Ecoule
	// ##############################################################################################

	/**
	 * <b>Temps écoulé, en heure, entre deux dates </b>
	 * 
	 * @param autre
	 *            - Date avec laquelle on va calculer le délai écoulé.
	 * @return Un entier positif représentant le nombre d'heures écoulé entre
	 *         les deux dates.
	 * 
	 */
	public long getElapsedTimeInHour(DateModifiable autre) {
		long elapsedTime;
		long millisA = this.c.getTimeInMillis();
		long millisB = autre.c.getTimeInMillis();
		long difMillis = millisB - millisA;

		elapsedTime = difMillis / (60 * 60 * 1000);

		if (this.before(autre))
			return elapsedTime;
		else
			return -elapsedTime;
	}

	/**
	 * <b>Temps écoulé, en minute, entre deux dates </b>
	 * 
	 * @param autre
	 *            - Date avec laquelle on va calculer le délai écoulé.
	 * @return Un entier positif représentant le nombre de minutes écoulé entre
	 *         les deux dates.
	 * 
	 */
	public long getElapsedTimeInMinute(DateModifiable autre) {
		long elapsedTime;
		long millisA = this.c.getTimeInMillis();
		long millisB = autre.c.getTimeInMillis();
		long difMillis = millisB - millisA;

		elapsedTime = difMillis / (60 * 1000);

		if (this.before(autre))
			return elapsedTime;
		else
			return -elapsedTime;
	}

	/**
	 * <b>Temps écoulé, en seconde, entre deux dates </b>
	 * 
	 * @param autre
	 *            - Date avec laquelle on va calculer le délai écoulé.
	 * @return Un entier positif représentant le nombre de minutes écoulé entre
	 *         les deux dates.
	 * 
	 */
	public long getElapsedTimeInSecond(DateModifiable autre) {
		long elapsedTime;
		long millisA = this.c.getTimeInMillis();
		long millisB = autre.c.getTimeInMillis();
		long difMillis = millisB - millisA;

		elapsedTime = difMillis / 1000;

		if (this.before(autre))
			return elapsedTime;
		else
			return -elapsedTime;
	}

	// ##############################################################################################
	// Récupéré une nouvelle instance de Date du jour
	// ##############################################################################################

	public DateModifiable getInstance() {
		c.setTime(new Date());
		return this;
	}

	// ##############################################################################################
	// COMPARATEUR
	// ##############################################################################################

	/**
	 * <b>Comparateur de DateModifiable.</b>
	 * 
	 * @param o
	 *            - Objet DateModifiable que l'on veut comparé avec la
	 *            DateModifiable courante.
	 * 
	 * @return Une valeur négative positive, négative ou null suivant si la date
	 *         placée en paramétre est avant, aprés ou égale é la date courante
	 * 
	 */
	private double compare(Object o) {
		DateModifiable autre = (DateModifiable) o;// Date é laquelle on compara
													// la date courante
		double res = 0;

		if (c.get(Calendar.YEAR) == autre.c.get(Calendar.YEAR)) // Compare
																// l'année
		{
			if (c.get(Calendar.DAY_OF_YEAR) == autre.c
					.get(Calendar.DAY_OF_YEAR)) // Compare jour
			{
				if (c.get(Calendar.HOUR_OF_DAY) == autre.c
						.get(Calendar.HOUR_OF_DAY)) // Compare Heure
				{
					res = ((double) c.get(Calendar.MINUTE) * 60 + (double) c
							.get(Calendar.SECOND))
							- ((double) autre.c.get(Calendar.MINUTE) * 60 + (double) autre.c
									.get(Calendar.SECOND));
				} else
					res = c.get(Calendar.HOUR_OF_DAY)
							- autre.c.get(Calendar.HOUR_OF_DAY);
			} else
				res = c.get(Calendar.DAY_OF_YEAR)
						- autre.c.get(Calendar.DAY_OF_YEAR);
		} else
			res = c.get(Calendar.YEAR) - autre.c.get(Calendar.YEAR);

		return res;
	}

	/**
	 * <b>Comparateur de DateModifiable.</b>
	 * 
	 * @param autre
	 *            - DateModifiable que l'on veut comparé avec la DateModifiable
	 *            courante.
	 * 
	 * @return <i>true</i> : si les deux dates correspondent é la seconde
	 *         prés</br> <i>false</i> : sinon
	 * 
	 */
	public boolean equals(DateModifiable autre) {
		return compare(autre) == 0;
	}

	/**
	 * <b>Comparateur de DateModifiable.</b>
	 * 
	 * @param autre
	 *            - DateModifiable que l'on veut comparé avec la DateModifiable
	 *            courante.
	 * 
	 * @return <i>true</i> : si la date courante est avant la date en paramétre
	 *         é la seconde prés</br> <i>false</i> : sinon
	 * 
	 */
	public boolean before(DateModifiable autre) {
		return compare(autre) < 0;
	}

	/**
	 * <b>Comparateur de DateModifiable.</b>
	 * 
	 * @param autre
	 *            - DateModifiable que l'on veut comparé avec la DateModifiable
	 *            courante.
	 * 
	 * @return <i>true</i> : si la date courante est aprés la date en paramétre
	 *         é la seconde prés</br> <i>false</i> : sinon
	 * 
	 */
	public boolean after(DateModifiable autre) 
	{
		return compare(autre) > 0;
	}

	// ##############################################################################################
	// 								DESCRIPTEUR
	// ##############################################################################################

	public String toString() 
	{
		StringTokenizer st = new StringTokenizer(c.getTime().toString(), " ");
		String[] s = new String[6];
		int i;
		for (i = 0; i < 6; i++)
			s[i] = st.nextToken();

		return s[2] + ' ' + s[1] + ' ' + s[5] + ' ' + s[3];

	}

	public String toStringDate() 
	{
		StringTokenizer st = new StringTokenizer(c.getTime().toString(), " ");
		String[] s = new String[6];
		int i;
		for (i = 0; i < 6; i++)
			s[i] = st.nextToken();

		return s[0] + ' ' + s[2] + ' ' + s[1] + ' ' + s[5];
	}

	public String toStringHour() 
	{
		StringTokenizer st = new StringTokenizer(c.getTime().toString(), " ");
		String[] s = new String[6];
		int i;
		for (i = 0; i < 6; i++)
			s[i] = st.nextToken();

		return s[3];
	}

	// ##############################################################################################
	//										 ACCESSEUR
	// ##############################################################################################

	public int getJourDuMois() 
	{
		return c.get(Calendar.DAY_OF_MONTH);
	}
	public int getJourDeAnnee() 
	{
		return c.get(Calendar.DAY_OF_YEAR);
	}
	public int getMois() 
	{
		return c.get(Calendar.MONTH) - 1;
	}
	public int getAnnee() 
	{
		return c.get(Calendar.YEAR);
	}
	public int getHeure() 
	{
		return c.get(Calendar.HOUR_OF_DAY);
	}
	public int getMinute() 
	{
		return c.get(Calendar.MINUTE);
	}
	public int getSeconde() {
		return c.get(Calendar.SECOND);
	}
	
	// ##############################################################################################
	//										 MODIFICATEUR
	// ##############################################################################################

	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Jour du mois" du calendrier 
	 */
	public void setJourDuMois(int value) 
	{
		c.set(Calendar.DAY_OF_MONTH, value);
	}
	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Jour de l'année" du calendrier 
	 */
	public void setJourDeAnnee(int value) 
	{
		c.set(Calendar.DAY_OF_YEAR, value);
	}
	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Mois" du calendrier 
	 */
	public void setMois(int value) 
	{
		c.set(Calendar.MONTH, value);
	}
	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Année" du calendrier 
	 */
	public void setAnnee(int value) 
	{
		 c.set(Calendar.YEAR, value);
	}
	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Heure" du calendrier 
	 */
	public void setHeure(int value) 
	{
		 c.set(Calendar.HOUR_OF_DAY, value);
	}
	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Minute" du calendrier 
	 */
	public void setMinute(int value) 
	{
		 c.set(Calendar.MINUTE, value);
	}
	
	/**
	 * @param value - La valeur de mis à jour à metre dans le champs "Seconde" du calendrier 
	 */
	public void setSeconde(int value) 
	{
		 c.set(Calendar.SECOND, value);
	}


                /* ###################################
                         COMPORTEMENT PARCEABLE
                #####################################*/

    public final static Creator<DateModifiable> CREATOR =
            new Creator<DateModifiable>()
            {
                public DateModifiable createFromParcel(Parcel in) {
                    return new DateModifiable(in);
                }

                public DateModifiable[] newArray(int size) {
                    return new DateModifiable[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getJourDuMois());
        dest.writeInt(this.getMois());
        dest.writeInt(this.getAnnee());
        dest.writeInt(this.getHeure());
        dest.writeInt(this.getMinute());
        dest.writeInt(this.getSeconde());
    }

}
