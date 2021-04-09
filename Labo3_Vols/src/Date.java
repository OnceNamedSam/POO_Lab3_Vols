import java.text.*;

import javax.swing.JOptionPane;

public class Date {

	private int jour, mois, an;
	
	public Date () {
		this(java.time.LocalDate.now().getDayOfMonth(), 
			 java.time.LocalDate.now().getMonthValue(), 
			 java.time.LocalDate.now().getYear());
	}
	
	public Date (int jour, int mois, int an) {
		setJour(jour);
		setMois(mois);
		setAn(an);
		
		boolean valide = validerDate();
		
		while (!valide) {
			redemanderDate();
			valide = validerDate();
		} 
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getAn() {
		return an;
	}

	public void setAn(int an) {
		this.an = an;
	}
		
	private boolean validerDate() {
		
		int tabDateAjd[] = new int[3];
		tabDateAjd[0] = java.time.LocalDate.now().getDayOfMonth();
		tabDateAjd[1] = java.time.LocalDate.now().getMonthValue();
		tabDateAjd[2] = java.time.LocalDate.now().getYear();
		
		boolean bissextile = ((this.an % 4 == 0 && this.an % 100 != 0) || (this.an % 400 == 0));
		int tabMaxJours[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		if (bissextile) {
			tabMaxJours[1]++;
		}
		
		if (this.jour < 1 || this.jour > tabMaxJours[this.mois - 1]) {
			return false;
		}
		
		if (this.mois < 1 || this.mois > 12) {
			return false;
		}
		
		//Les années incluses dans le fichier sont avant 2021
		//if (this.an < tabDateAjd[2]) {
		//	return false;
		//} 
		
		//if (this.an == tabDateAjd[2] && this.mois <= tabDateAjd[1] && this.jour < tabDateAjd[0]) {
		//	return false;
		//}
		
		return true;
	}
	
	
	//VALIDER LES ENTRÉES
	private void redemanderDate() {
		boolean valide;
		do {
			valide = true;
			try {
				this.jour = Integer.parseInt(JOptionPane.showInputDialog(null, "Date invalide! Veuillez recommencer\n\nEntrer le jour (JJ)", 
						"VALIDATION DE LA DATE", JOptionPane.ERROR_MESSAGE));
				this.mois = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrer le mois (MM)", 
						"VALIDATION DE LA DATE", JOptionPane.ERROR_MESSAGE));
				this.an = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrer l'année (AAAA)", 
						"VALIDATION DE LA DATE", JOptionPane.ERROR_MESSAGE));							
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Date invalide", "ERREUR", JOptionPane.ERROR_MESSAGE);
	        	valide = false;
			}
		} while (!valide);
	}
	
	public String toString() {
		
		DecimalFormat deuxChiffres = new DecimalFormat("00");
		return deuxChiffres.format(this.jour) + "/" + deuxChiffres.format(this.mois) + "/" + this.an;
	}
}
