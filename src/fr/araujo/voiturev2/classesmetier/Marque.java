package fr.araujo.voiturev2.classesmetier;

import fr.araujo.voiturev2.enumerations.*;

public class Marque {
	private int id;
	private Nationalite nationalite;
	private String nom;

	public Marque(int id, String nom, Nationalite nationalite) {
		this.id = id;
		this.nom = nom;
		this.nationalite = nationalite;
	}

	@Override
	public String toString() {
		return "Marque [id=" + id + ", nationalite=" + nationalite + ", nom=" + nom + "] \n";
	}

	public int getId() {
		return this.id;
	}

	public Nationalite getNationalite() {
		return this.nationalite;
	}

	public String getNom() {
		return this.nom;
	}

}
