package ca.easyevent.model;

public class Participant implements Comparable{


	/*##############################################################################################
									ATTRIBUTS
	###############################################################################################*/

    private long id;
	private String nom = new String(""); 
	private String telephone = new String(""); 
	private String mail = new String(""); 

	private double equilibrePersoTotal = 0;
	

	/*##############################################################################################
									CONSTRUCTEUR
	###############################################################################################*/
	
	public Participant() {}
	
	public Participant(String name) {
		this.nom = name;
	}
	
	public Participant(String name, String telephone, String mail) {
		this.nom = name;
		this.telephone = telephone;
		this.mail = mail;
	}


	/*##############################################################################################
								CALCUL 
	##############################################################################################*/

	public void calculEquiPersoTotal()
	{

    }
	

	/*##############################################################################################
									ACCESSEUR 
	##############################################################################################*/
	
	public String getName() {
		return nom;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public String getMail() {
		return mail;
	}

    public double getBudgetPersoTotal(){
        return 0;
    }

	public double getEquiPersoTotal(){
		return equilibrePersoTotal;
	}

    public long getId() {
        return id;
    }

    /*##############################################################################################
									MODIFICATEUR 
	##############################################################################################*/

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEquilibrePersoTotal(double equilibrePersoTotal) {
        this.equilibrePersoTotal = equilibrePersoTotal;
    }

    /*##############################################################################################
									DESCRIPTEUR 
	##############################################################################################*/
	
	@Override
	public String toString() {
		return "Participant [nom=" + nom + ", telephone=" + telephone
				+ ", mail=" + mail + ", BudgetPersoTotal=" + getBudgetPersoTotal()
				+ ", equilibrePersoTotal=" + equilibrePersoTotal + "]";
	}

    /*################################################################################################
                        RELATION D'ORDRE POUR COMPARAISON
    ##################################################################################################*/
    @Override
    public int compareTo(Object another) {
        Double equiPart1 = ((Participant) another).getEquiPersoTotal();
        Double equiPart2 = this.getEquiPersoTotal();
        if (equiPart1<equiPart2)  return -1;
        else if(equiPart1 == equiPart2) return 0;
        else return 1;
    }

    public Object clone() {
        Participant participant = null;
        try {
            participant = (Participant) super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return participant;
    }
}
