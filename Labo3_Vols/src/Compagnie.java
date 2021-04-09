import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Compagnie {
	
	public static final int MAX_PLACES = 340;
	
	private String nomComp;
	private int nbMaxVols;
	private String nomFichier = "";
	private ArrayList<Vol> tabVols;
	private int volsActifs = 0; 
	
	
	public Compagnie (String nomComp, int nbMaxVols) throws IOException {
		this.nomComp = nomComp;
		this.nbMaxVols = nbMaxVols;	
		this.tabVols = new ArrayList<>();
		
		remplirTabVols();
	}
	
	
	public String getNomComp() {
		return nomComp;
	}
	

	private void remplirTabVols() throws IOException {
		
		JFileChooser choixFichier = new JFileChooser();
		FileNameExtensionFilter typeFichier = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		choixFichier.setFileFilter(typeFichier);
		choixFichier.setDialogTitle("CHOISIR UN FICHIER DE VOLS");
		
		if (choixFichier.showOpenDialog(choixFichier) == JFileChooser.APPROVE_OPTION) {
			this.nomFichier = choixFichier.getSelectedFile().getPath();
		}
		else {
			System.exit(0);
		}
		
		BufferedReader fichierCie = new BufferedReader(new FileReader(nomFichier));
		String ligne = fichierCie.readLine();
		
		//Format d'un ligne: numero;destination;jj;mm;aaaa;reserv
		//Tableaux temporaire contenant les 6 composantes d'une ligne 
		String tabTempo[] = new String[6];
		Date dateTempo;
		
		while (ligne!=null && volsActifs <= nbMaxVols) {
			tabTempo = ligne.split(";");
			dateTempo = new Date (Integer.parseInt(tabTempo[2]), Integer.parseInt(tabTempo[3]), Integer.parseInt(tabTempo[4]));
			//this.tabVols[this.volsActifs] = new...
			this.tabVols.add(new Vol(Integer.parseInt(tabTempo[0]), tabTempo[1], dateTempo, Integer.parseInt(tabTempo[5])));
			this.volsActifs++;
			ligne = fichierCie.readLine();
		}
		fichierCie.close();
	}
	
	
//*************************************************************************************	
	
	public void listerVols () {
		
		JTextArea affichage = new JTextArea((this.volsActifs + 5), 30);
		affichage.setFont(new Font("Courier", Font.BOLD, 14));
		affichage.setTabSize(20);
		affichage.setEnabled(false);
		affichage.setBackground(Color.blue);
		affichage.setDisabledTextColor(Color.green);
		affichage.setMargin(new Insets(10,10,10,10));
		
		String texte = 	"\tLISTE DES VOLS\n\n" +
						"Num�ro\tDestination\t\tDate d�part\tR�servations";
		
		for (int i = 0; i < this.tabVols.size() && i < this.volsActifs; i++) {
			texte += tabVols.get(i);
		}			
		affichage.setText(texte);
		JOptionPane.showMessageDialog(null, affichage, "Compagnie a�rienne " + this.nomComp, JOptionPane.PLAIN_MESSAGE);
	}

	
//*************************************************************************************	
	
	public void insererVol() {
		
		if (verifMaxAtteint()) { 
			return; 
		}
			
		int numeroVol;	
		String destination;
		Date dateDepart;
		
		numeroVol = demanderNumeroVol("NOUVEAU VOL");
		
		if (verifierNumVol(numeroVol) != -1) {
			JOptionPane.showMessageDialog(null, "Ce numero de vol est d�j� utilis�\nRetour au menu princnipal", 
					"VOL EXISTANT", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		destination = JOptionPane.showInputDialog(null, "Entrer la destination", 
				"NOUVEAU VOL", JOptionPane.QUESTION_MESSAGE);
			
		dateDepart = demanderDate("Vol � destination de " + destination);
		
		this.tabVols.add(new Vol(numeroVol, destination, dateDepart, 0));
		this.volsActifs++;
		
		trierTabVols();
		JOptionPane.showMessageDialog(null, "Nouveau vol � destination de " + destination + " ajout� avec succ�s", 
				"NOUVEAU VOL", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	private boolean verifMaxAtteint () {
		
		if (this.volsActifs >= this.nbMaxVols) {
			JOptionPane.showMessageDialog(null, "Le maximum de vols est atteint pour cette compagnie\nRetour au menu princnipal", 
												"AJOUT IMPOSSIBLE", JOptionPane.ERROR_MESSAGE);
			return true;
		} 
		return false;
	}
	    
	
	private int demanderNumeroVol(String entete) {
		
		return Integer.parseInt(JOptionPane.showInputDialog(null, "Entrer le numero de de vol", 
				entete, JOptionPane.QUESTION_MESSAGE));	
	}
	
	
	private int verifierNumVol(int numeroVol) {
		
		boolean volExiste = false;
		int posiTab = -1;
		int iStart = 0, iMiddle, iEnd = volsActifs - 1;
		
		//for (iEnd = 0; iEnd < this.tabVols.length && tabVols[iEnd] != null; iEnd++); ***l'attribut volsActifs fait le travail 
		
		while (iStart <= iEnd && !volExiste) {
			iMiddle = (iStart + iEnd) / 2;
			if (numeroVol < this.tabVols.get(iMiddle).getNumeroVol()) {
				iEnd = iMiddle - 1;
			} 
			else if (numeroVol > this.tabVols.get(iMiddle).getNumeroVol()) {
				iStart = iMiddle + 1;
			}
			else {
				volExiste = true;
				posiTab = iMiddle;
			}
		}
		return posiTab;
	}
	
	
	//� FAIRE: VALIDATION DES ENTR�ES
	private Date demanderDate(String entete) {
		
		JPanel dateForm = new JPanel(new GridBagLayout());
		JLabel labelJour = new JLabel ("Jour");
		JLabel labelMois = new JLabel ("Mois");
		JLabel labelAn = new JLabel ("Ann�e");
		
		JTextField textJour = new JTextField(2);
		JTextField textMois = new JTextField(2);
		JTextField textAn = new JTextField(4);
		
	    GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(4, 0, 4, 20);
         
        constraints.gridx = 0;
        constraints.gridy = 0;     
        dateForm.add(labelJour, constraints);
        constraints.gridx = 1;
        dateForm.add(textJour, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;     
        dateForm.add(labelMois, constraints);
        constraints.gridx = 1;
        dateForm.add(textMois, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;     
        dateForm.add(labelAn, constraints);
        constraints.gridx = 1;
        dateForm.add(textAn, constraints);
		
        dateForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Date"));
    	
        boolean valide;
        
    	do {
    		valide = true;
    		textJour.setText("");
    		textMois.setText("");
    		textAn.setText("");
    		int choix = JOptionPane.showConfirmDialog(null, dateForm, entete, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	        
    		try {     
				if (choix == JOptionPane.OK_OPTION) {
					return (new Date(Integer.parseInt(textJour.getText()), Integer.parseInt(textMois.getText()), Integer.parseInt(textAn.getText())));
				}	
				else {
					break;
				}
	        }
	        catch (NumberFormatException e) {
	        	JOptionPane.showMessageDialog(null, "Date invalide", "ERREUR", JOptionPane.ERROR_MESSAGE);
	        	valide = false;
	        }
        } while (!valide);
    	return null;
	}

	
	private void trierTabVols () {
		
		int i, iEnd = this.volsActifs - 1;
		Vol tempo;
		
		do {
			for (i = 0; i < iEnd;i++) {
				if (this.tabVols.get(i).getNumeroVol() > this.tabVols.get(i+1).getNumeroVol()) {
					tempo = (Vol)this.tabVols.get(i).clone();
					
					this.tabVols.set(i, this.tabVols.get(i+1));
					this.tabVols.set((i+1), tempo);
				}
			}
			iEnd--;
		} while (iEnd > 0);
	}

	
//*************************************************************************************		
	
	public void retirerVol() {
		
		int numeroVol = demanderNumeroVol("RETIRER UN VOL");
		int posiTab = verifierNumVol(numeroVol);
		
		if (posiTab == -1) {
			JOptionPane.showMessageDialog(null, "Ce numero de vol n'existe pas\nRetour au menu princnipal", 
					"VOL INEXISTANT", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int choixConfirm = JOptionPane.showConfirmDialog(null, "ATTENTION! Vous �tes sur le point de supprimer ce vol\n" +
											"\nDestination: " + tabVols.get(posiTab).getDestination() + 
											"\nDate de d�part: " + tabVols.get(posiTab).getDepart() + 
											"\nNombre de r�servations: " + tabVols.get(posiTab).getTotalReservs(), 
											"RETIRER UN VOL", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		if (choixConfirm != JOptionPane.OK_OPTION) {
			return;
		}
		
		tabVols.remove(posiTab);

		volsActifs--;
		JOptionPane.showMessageDialog(null, "Vol retir� avec succ�s", 
				"RETIRER UN VOL", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
//*************************************************************************************			

	public void modifierDate() {
		
		int numeroVol = demanderNumeroVol("MODIFIER DATE DE D�PART");
		int posiTab = verifierNumVol(numeroVol);
		
		if (posiTab == -1) {
			JOptionPane.showMessageDialog(null, "Ce numero de vol n'existe pas\nRetour au menu princnipal", 
					"VOL INEXISTANT", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Date dateActuelle = tabVols.get(posiTab).getDepart();
		Date nouvelleDate = demanderDate("DATE ACTUELLE " + dateActuelle);
		
		if (nouvelleDate != null) {
			tabVols.get(posiTab).setDepart(nouvelleDate);
			JOptionPane.showMessageDialog(null, "Date modifi�e avec succ�s", 
					"MODIFIER DATE DE D�PART", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
//*************************************************************************************		
	
	public void reserverVol() {
		
		int numeroVol = demanderNumeroVol("R�SERVATION");
		int posiTab = verifierNumVol(numeroVol);
		int placesRestantes;
		int nbPlaces;
		
		if (posiTab == -1) {
			JOptionPane.showMessageDialog(null, "Ce numero de vol n'existe pas\nRetour au menu princnipal", 
					"VOL INEXISTANT", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		placesRestantes = (MAX_PLACES - tabVols.get(posiTab).getTotalReservs());
		
		if (placesRestantes <= 0) {
			JOptionPane.showMessageDialog(null, "Il ne reste plus de places pour ce vol\nRetour au menu princnipal", 
					"VOL COMPLET (" + tabVols.get(posiTab).getDestination() + " " + tabVols.get(posiTab).getDepart() + ")", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		nbPlaces = Integer.parseInt(JOptionPane.showInputDialog(null, "Destination: " + tabVols.get(posiTab).getDestination() +
										  "\nD�part: " + tabVols.get(posiTab).getDepart() + 
										  "\nPlaces disponibles: " + placesRestantes + 
										  "\n\n Entrer le nombre de places � r�server", "R�SERVATION", JOptionPane.QUESTION_MESSAGE));
		
		if (nbPlaces > placesRestantes) {
			JOptionPane.showMessageDialog(null, "R�servation impossible\nIl ne reste pas assez de places sur ce vol" + 
							"\n\nRetour au menu princnipal", "VOL COMPLET (" + tabVols.get(posiTab).getDestination() + " " + tabVols.get(posiTab).getDepart() + ")", 
							JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tabVols.get(posiTab).setTotalReservs(tabVols.get(posiTab).getTotalReservs() + nbPlaces);
		
		JOptionPane.showMessageDialog(null, "R�servation pour " + nbPlaces + " personne(s) effectu�e avec succ�s", 
				"CONFIRMATION R�SERVATION (" + tabVols.get(posiTab).getDestination() + " " + tabVols.get(posiTab).getDepart() + ")", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
//*************************************************************************************			
	
	//R��crire fichier et quitter
	public void terminer() throws IOException {
		
		BufferedWriter fichier = new BufferedWriter (new FileWriter(this.nomFichier));
		
		for (int i = 0; i < tabVols.size() && i < volsActifs; i++) {
			fichier.write(tabVols.get(i).getNumeroVol() + ";" + tabVols.get(i).getDestination() + ";" + 
						  tabVols.get(i).getDepart().getJour() + ";" + tabVols.get(i).getDepart().getMois() + ";" + tabVols.get(i).getDepart().getAn() + ";" + 
						  tabVols.get(i).getTotalReservs());
			fichier.newLine();
		}
		fichier.close();
		JOptionPane.showMessageDialog(null, "Fichier de vol sauvegard�", this.nomComp, JOptionPane.INFORMATION_MESSAGE);
	}	
	
}
