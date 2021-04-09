
public class Vol {	
	
	private int numeroVol;
	private String destination;   
	private Date depart;		 
	private int totalReservs;
	
	public Vol (int numeroVol, String destination, Date depart, int totalReservs) {
		this.numeroVol = numeroVol;
		this.destination = destination;
		this.depart = depart;
		this.totalReservs = totalReservs;
	}

	public int getNumeroVol() {
		return numeroVol;
	}

	public String getDestination() {
		return destination;
	}
		   
	public Date getDepart() {
		return depart;
	}

	public int getTotalReservs() {
		return totalReservs;
	}


	public void setNumeroVol(int numeroVol) {
		this.numeroVol = numeroVol;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setDepart(Date depart) {
		this.depart = depart;
	}
	
	public void setTotalReservs(int totalReservs) {
		this.totalReservs = totalReservs;
	}
	
	
	public String toString() {
		
		String status = "";
		if (this.totalReservs == Compagnie.MAX_PLACES) {
			status = " COMPLET";
		}
		
		return "\n" + this.numeroVol + "\t" + this.destination + "\t\t" + this.depart + "\t" + totalReservs + status;
	}
	
	//Redéfinition de la méthode clone()
	public Object clone() {
		return new Vol(numeroVol, destination, depart, totalReservs);
	}
	
    /*public int compareTo(Vol v) {
        if (this.numeroVol != v.getNumeroVol()) {
            return this.numeroVol - v.getNumeroVol();
        }
        return v.getNumeroVol();
    }*/	
	
}
