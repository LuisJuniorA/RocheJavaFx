package fr.araujo.voiturev2.classesmetier;

//import java.util.Vector;
import java.util.HashMap;

public class Garage {
	private int id;
	private String nom;
	private String ville;
	//private Vector<Voiture> voitures = new Vector<Voiture>();
	public HashMap<Integer, Voiture> voitures = new HashMap<Integer, Voiture>();
	public Garage(int id, String nom, String ville) {
		this.id = id;
		this.nom = nom;
		this.ville = ville;
	}

	@Override
	public String toString() {
		return "Garage [id=" + id + ", nom=" + nom + ", ville=" + ville + ", \n voitures=" + voitures + "]";
	}

	public Garage(int id, String nom, String ville, HashMap<Integer, Voiture> voitures) {
		this(id, nom, ville);
		this.voitures = voitures;
	}

	public void ajouterVoiture(Voiture voiture) {
		this.voitures.put(voiture.getId(), voiture);
	}
	
	

	public boolean estDansGarage(int idVoiture) {
		
			if (voitures.get(idVoiture) == null) {
				return false;
			}

		
		return true;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getVille() {
		return ville;
	}

	public Voiture getVoitures(int i) {
		return voitures.get(i);
	}

	public Voiture plusAncienneVoiture() {
		Voiture voiturePlusVieille = this.voitures.get(0);

		for (int i = 1; i < this.voitures.size(); i++) {
			Voiture voiture = this.voitures.get(i);
			if (voiturePlusVieille.getAnneeMiseEnService() > voiture.getAnneeMiseEnService())
				voiturePlusVieille = voiture;
		}
		/*
		 * for (Voiture voiture : this.voitures) { if (voiture.getAnneeMiseEnService() <
		 * anneePlusVieille ) { anneePlusVieille = voiture.getAnneeMiseEnService(); }
		 * 
		 * }
		 */
		return voiturePlusVieille;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void supprimerVoiture(int i) throws Exception {
		if (i >= this.voitures.size()) {
			throw new Exception(
					"L'index donné est trop grand. Il doit être compris entre 0 et n-1 avec n le nombre d'éléments dans le garage.");
		} else {
			this.voitures.remove(i);
		}

	}
}
