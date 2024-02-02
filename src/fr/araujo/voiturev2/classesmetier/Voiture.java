package fr.araujo.voiturev2.classesmetier;

import java.time.LocalDate;

import fr.araujo.voiturev2.enumerations.Couleur;

public class Voiture {
	private static final int consommationAuxCentKilometres = 8;
	private int anneeMiseEnService;
	private double capaciteReservoir;
	private Couleur couleur;
	private int id;
	private Marque marque;
	private int nbKilometresCompteur;
	private double nbLitresContenus;
	private int prixAchat;

	public Voiture(int id, double capaciteReservoir, Couleur couleur, int anneeMiseEnService, int nbKilometresCompteur,
			double nbLitresContenus, int prixAchat, Marque marque) throws Exception {
		this.id = id;
		this.setCapaciteReservoir(capaciteReservoir);
		this.couleur = couleur;
		this.setAnneeMiseEnService(anneeMiseEnService);
		this.marque = marque;
		this.nbKilometresCompteur = nbKilometresCompteur;
		this.setNbLitresContenus(nbLitresContenus);
		this.setPrixAchat(prixAchat);
	}

	public Voiture(int id, double capaciteReservoir, Couleur couleur, int anneeMiseEnService, int prixAchat,
			Marque marque) throws Exception {
		this(id, capaciteReservoir, couleur, anneeMiseEnService, 0, 0, prixAchat, marque);
	}

	public Voiture(int id, double capaciteReservoir, Couleur couleur, int prixAchat, Marque marque) throws Exception {
		this(id, capaciteReservoir, couleur, LocalDate.now().getYear(), 0, 0, prixAchat, marque);
	}

	public static int getConsommationauxcentkilometres() {
		return consommationAuxCentKilometres;
	}

	public int getAnneeMiseEnService() {
		return anneeMiseEnService;
	}

	public double getCapaciteReservoir() {
		return capaciteReservoir;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public int getId() {
		return id;
	}

	public Marque getMarque() {
		return marque;
	}

	public int getNbKilometresCompteur() {
		return nbKilometresCompteur;
	}

	public double getNbLitresContenus() {
		return nbLitresContenus;
	}

	public int getPrixAchat() {
		return prixAchat;
	}

	@Override
	public String toString() {
		return "Voiture [marque=" + marque.toString() + ", prixAchat=" + prixAchat + ", couleur=" + couleur
				+ ", anneeMiseEnService=" + anneeMiseEnService + ", capaciteReservoir=" + capaciteReservoir
				+ ", nbLitresContenus=" + nbLitresContenus + ", nbKilometresCompteurs=" + nbKilometresCompteur
				+ ", consommationAuxCentKilometres=" + consommationAuxCentKilometres + "]";
	}

	public boolean rouler(int nbKilometresEffectuer) throws Exception {
		double litreTotal = this.nbLitresContenus - (nbKilometresEffectuer * consommationAuxCentKilometres) / 100;
		if (litreTotal > 0) {
			if (litreTotal < 10) {
				System.out.println("Vous êtes sur la réserve, pensez à faire de l'essence");
			}
			this.setNbLitresContenus(litreTotal);
			this.setNbKilometresCompteur(this.nbKilometresCompteur + nbKilometresEffectuer);
			return true;
		}
		System.out.println("Impossible d'effectuer le trajet par manque d'essence");
		return false;
	}

	public void remplir(double quantite) {
		if (quantite < this.capaciteReservoir - this.nbLitresContenus) {
			this.nbLitresContenus += quantite;
		} else {
			this.nbLitresContenus = this.capaciteReservoir;
		}
	}

	public void remplir() {
		remplir(this.capaciteReservoir);

	}

	private void setAnneeMiseEnService(int anneeMiseEnService) throws Exception {
		if (anneeMiseEnService > LocalDate.now().getYear()) {
			throw new Exception("L'année de mise en service ne peut pas dépasser l'année actuelle.");
		} else {
			this.anneeMiseEnService = anneeMiseEnService;
		}

	}

	private void setNbLitresContenus(double litreReservoir) throws Exception {
		if (litreReservoir > this.capaciteReservoir) {
			throw new Exception("La quantité à ajouter est supérieur à la capacité du réservoir.");
		} else if (litreReservoir < 0) {
			throw new Exception("La quantité à ajouter est négative.");
		}
		
		else {
			this.nbLitresContenus = litreReservoir;
		}
		
	}

	private void setNbKilometresCompteur(int nbKilometresCompteur) {
		this.nbKilometresCompteur = nbKilometresCompteur;

	}

	private void setCapaciteReservoir(double litreTotal) {
		this.capaciteReservoir = litreTotal;

	}

	private void setPrixAchat(int prixAchat) throws Exception {
		if (prixAchat < 0) {
			throw new Exception("Le prix est n�gatif.");
		}else {
		this.prixAchat = prixAchat;	
		}
		
	}
}
