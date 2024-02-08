/**
 * 
 */
package fr.araujo.voiturev2.programme;

import java.util.HashMap;

import fr.araujo.voiturev2.classesmetier.DbConnector;
import fr.araujo.voiturev2.classesmetier.Garage;
import fr.araujo.voiturev2.classesmetier.Marque;
import fr.araujo.voiturev2.classesmetier.Voiture;
import fr.araujo.voiturev2.enumerations.Couleur;
import fr.araujo.voiturev2.enumerations.Nationalite;

/**
 * @author luis.araujo
 *
 */
public class Programme {
	public HashMap<Integer, Garage> garages = new HashMap<Integer, Garage>();	/**
	 * @param args
	 */
	public static void main(String[] args) {
            
                

		try {
			//Marque ferrari = new Marque(1, "Ferrari", Nationalite.France);
			//Marque porsche = new Marque(1, "Porsche", Nationalite.Allemagne);
			//Marque lambo = new Marque(1, "Lamborghini", Nationalite.Italien);
			
			
			//Voiture voiture1 = new Voiture(1, 100.00, Couleur.Rouge, 2014, 70, 10, 1500, ferrari);
			//Voiture voiture2 = new Voiture(2, 97.00, Couleur.Jaune, 2019, 84, 15, 1550, porsche);
			//Voiture voiture3 = new Voiture(3, 130.00, Couleur.Noir, 2002, 50, 11, 1590, lambo);
			
			//Garage garage = new Garage(1, "Garage de Luis", "Toulon");
			//System.out.println(garage);
			//garage.ajouterVoiture(voiture1);
			//System.out.println("Voiture 1 mise dans le garage");
			//garage.ajouterVoiture(voiture2);
			//System.out.println("Voiture 2 mise dans le garage");
			//garage.ajouterVoiture(voiture3);
			//System.out.println("Voiture 3 mise dans le garage");
			//System.out.println(garage);
			//boolean premier = garage.estDansGarage(0);
			//boolean deuxieme = garage.estDansGarage(1);
			//System.out.println(premier);
			//System.out.println(deuxieme);

			
			//System.out.println(voiture1.toString());
			
			
			
			DbConnector.chargeDriver();
			HashMap<Integer, Garage> garages = DbConnector.initgarage();
			System.out.println("Garage initialisé.");
			DbConnector.deleteVoiture(4, 1);
			Voiture variable = new Voiture(4, 100.00, Couleur.Rouge, 2014, 70, 10, 1500, DbConnector.getMarque(1));
			DbConnector.addVoiture(variable, 1);
			variable = new Voiture(5, 100.00, Couleur.Rouge, 2014, 70, 10, 1500, DbConnector.getMarque(1));
			DbConnector.addVoiture(variable, 2);
			variable = new Voiture(7, 100.00, Couleur.Rouge, 2014, 70, 10, 1500, DbConnector.getMarque(1));
			DbConnector.addVoiture(variable);
			DbConnector.updateVoiture(5, 100.00, Couleur.Rouge, 2017, 70, 10.2, 1590, 2, 2);
			DbConnector.updateVoiture(5, 100.00, Couleur.Rouge, 2017, 70, 10.2, 1590, 2, null);
			DbConnector.deleteVoiture(5, 2);
			
			DbConnector.deleteVoiture(7, null);
			
			//System.out.println(garages.get(1));
			
			System.out.println("Fin normale du programme");
			
			
			
			
		}
		catch (Exception ex) {
			System.out.println("Exception Error: " + ex.getMessage());
			System.out.println("Fin anormale du programme");
		}
		

	}

}
