import java.io.*;
import javax.swing.*;


public class GestionCompagnie {

	public static void main(String[] args) throws IOException {
		
		Compagnie compagnie1 = creerCompagnie();
		
		char choixMenu;
		do {
			choixMenu = reponseMenu(compagnie1.getNomComp());
			
			switch (choixMenu) {
				case '1' : compagnie1.listerVols();
					break;
				case '2' : compagnie1.insererVol();
					break;
				case '3' : compagnie1.retirerVol();
					break;
				case '4' : compagnie1.modifierDate();
					break;
				case '5' : compagnie1.reserverVol();
					break;
				case '0' :
					if (JOptionPane.showConfirmDialog(null, "Enregistrer les modifications\navant de quitter?", 
						"TERMINER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						compagnie1.terminer();					
					} 
					break;
				default : JOptionPane.showMessageDialog(null, "Choix invalide!", 
						"ERREUR", JOptionPane.ERROR_MESSAGE);
					break;
			}
		} while (choixMenu != '0');
		
		System.exit(0);
	}	

	
	public static Compagnie creerCompagnie() throws IOException {
		
		String nomComp = demanderNom();
		int nbMaxVols = demanderMaxVols(nomComp);
			
		return (new Compagnie(nomComp, nbMaxVols));
	}
	
	static char reponseMenu(String nomComp) {
		
		String choix = JOptionPane.showInputDialog(null, "\tGESTION DES VOLS\n\n" +
									"1. Liste des vols\n" +
									"2. Ajout d'un vol\n" +
									"3. Retrait d'un vol\n" +
									"4. Modification de la date de départ\n" +
									"5. Réservation d'un vol (client)\n" +
									"0. Terminer\n" +
									"\nFaites votre choix:", nomComp, JOptionPane.QUESTION_MESSAGE);
		if (choix == null) {
			System.exit(0);
		} 
	
		return ((choix.equals("") || choix.length() > 1) ? '$' : choix.charAt(0));
	}
	
	static String demanderNom() {
		
		String nom;
		nom = JOptionPane.showInputDialog(null, "Systeme de gestion AirAid\n\n" +
				 "Entrez un nom de compagnie", "AIRAID - Accueil", JOptionPane.QUESTION_MESSAGE);
		
		if (nom == null) {
			System.exit(0);
		}
	
		return (nom.equals("") ? "N/A" : nom);
	}

	static int demanderMaxVols(String nomComp) {
 
		String saisie;
		boolean valide;
		
		do {	
			valide = true;
			saisie = JOptionPane.showInputDialog(null, "Compagnie aérienne " + nomComp + " enregistrée" +
					"\n\nEntrez le nombre maximum de vols offerts par la compagnie", "AIRAID - Nouvelle compagnie", JOptionPane.QUESTION_MESSAGE);
			
			if (saisie == null) {
				System.exit(0);
			}
			
			if (saisie.equals("") || !Character.isDigit(saisie.charAt(0))) {
				JOptionPane.showMessageDialog(null, "Entrée invalide!", "ERREUR", JOptionPane.ERROR_MESSAGE);
				valide = false;
			}
		} while (!valide);
		
		return Integer.parseInt(saisie);
	}

}
