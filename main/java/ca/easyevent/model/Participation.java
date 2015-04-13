package ca.easyevent.model;


public class Participation{

	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private long id;
	private long idParticipant;
	private long idDepense;

	private double montant;
	private double equilibre;
	private boolean isSelected;

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/

	public Participation(){}

    public Participation( long idParticipant, long idDepense, double montant) {
        this.idParticipant = idParticipant;
        this.idDepense = idDepense;
        this.montant = montant;
    }

    public Participation(long id, long idParticipant, long idDepense, double montant) {
        this.id = id;
        this.idParticipant = idParticipant;
        this.idDepense = idDepense;
        this.montant = montant;
    }

    /*##############################################################################################
									CALCUL
	###############################################################################################*/
/*
	public void calculEquilibre(){
		this.equilibre = this.montant - (this.depense.getMontantTotal() / this.getDepense().getNbParticipants());
	}

    public void calculEquilibreWithPropag(){
        this.equilibre = this.montant - (this.depense.getMontantTotal() / this.getDepense().getNbParticipants());
        this.participant.calculEquiPersoTotal();
    }
*/
	/*##############################################################################################
									ACCESSEUR
	###############################################################################################*/

    public long getId() {
        return id;
    }

    public long getIdParticipant() {
        return idParticipant;
    }

    public long getIdDepense() {
        return idDepense;
    }

    public double getMontant() {
        return montant;
    }

    public double getEquilibre() {
        return equilibre;
    }

    public boolean isSelected() {
        return isSelected;
    }

    /*##############################################################################################
									MODIFICATEUR
	###############################################################################################*/

    public void setId(long id) {
        this.id = id;
    }

    public void setIdParticipant(long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public void setIdDepense(long idDepense) {
        this.idDepense = idDepense;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setEquilibre(double equilibre) {
        this.equilibre = equilibre;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /*##############################################################################################
									DESCRIPTEUR
	###############################################################################################*/

    @Override
    public String toString() {
        return "Participation{" +
                "participant=" + idParticipant +
                ", depense=" + idDepense +
                ", montant=" + montant +
                ", equilibre=" + equilibre +
                '}';
    }

}
