package fr.araujo.voiturev2.classeTest;

import fr.araujo.voiturev2.classesmetier.Voiture;
import fr.araujo.voiturev2.enumerations.Couleur;

public class VoitureTest {
	public static void testeInstancieVoitureOccasion() throws Exception{
		String nomMethode = new Object() {}.getClass().getEnclosingMethod().getName();
		try {
			getVoitureOccasionOK();
			System.out.println("OK : " + nomMethode);
		} catch(Exception ex) {
			System.out.println("KO : " + nomMethode +" - " + ex.getMessage());
		}
		
		
		
	}
	public static Voiture getVoitureOccasionOK() throws Exception {
		return new Voiture(1,2.0,Couleur.Blanc, 80, 0, 0, 0, null);
	}
}
