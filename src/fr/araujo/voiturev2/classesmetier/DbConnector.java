/**
 * 
 */
package fr.araujo.voiturev2.classesmetier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;

import fr.araujo.voiturev2.enumerations.Couleur;
import fr.araujo.voiturev2.enumerations.Nationalite;

/**
 * 
 */
public class DbConnector {
	//init driver db
	static String url = "jdbc:oracle:thin:@freesio.lyc-bonaparte.fr:21521:slam";
	static String url2 = "jdbc:oracle:thin:@10.10.2.10:1521:slam";
	static String nom = "araujoapslam";
	static String pwd = "sio";
	static Connection connectionOracleSQl;

	/**
	 * Permet de charger les Drivers ojdbc et d'établir la connexion vers la base de donnée.
	 * @return {@link Connection}
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public static Connection chargeDriver() throws ClassNotFoundException, SQLException, Exception {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Test connexion");
			connectionOracleSQl = DriverManager.getConnection(url, nom, pwd);
			System.out.println("Connexion établi");
			return connectionOracleSQl;

		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(e.getMessage() + " Nom de la classe invalide/introuvable.");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

/**
 * Execute une requete SQL et renvoie le résultat de cette requete. 
 * @param sql
 * @return {@link ResultSet}
 * @throws SQLException
 */
	public static ResultSet chargeRequete(String sql) throws SQLException {
		Statement statement = connectionOracleSQl.createStatement();
		ResultSet result = statement.executeQuery(sql);
		return result;
	}

	/**
	 * Execute la commande SQL entrée en paramètre (type PreparedStatement)
	 * @param sql
	 * @throws SQLException
	 */
	public static void chargeUpdate(PreparedStatement sql) throws SQLException {
		sql.executeUpdate();

	}

/**
 * Permets l'initialisation des voitures (objet de la classe Voiture rangé dans un Hashmap de voiture ayant pour clé l'id de la voiture)
 *  ayant pour id de garage le paramètre idGarage (type int)
 * @param idGarage
 * @return Hasmap<Integer, Voiture>
 * @throws SQLException
 */
	public static HashMap<Integer, Voiture> initVoitures(int idGarage) throws SQLException {

		HashMap<Integer, Voiture> voitures = new HashMap<Integer, Voiture>();
		String sql = "select * from voiture inner join marque on voiture.idmarque = marque.id  where idgarage = "
				+ idGarage;
		ResultSet result;
		try {
			result = chargeRequete(sql);
			while (result.next()) {
				Marque marque = new Marque(result.getInt("IDMARQUE"), result.getString("NOM"),
						Nationalite.valueOf(result.getString("NATIONALITE")));
				Voiture voiture = new Voiture(result.getInt("id"), result.getDouble("CAPACITERESERVOIR"),
						Couleur.valueOf(result.getString("COULEUR")), result.getInt("ANNEEMISEENSERVICE"),
						result.getInt("NBKILOMETRESCOMPTEUR"), result.getInt("NBLITRESCONTENUS"),
						result.getInt("PRIXACHAT"), marque);
				voitures.put(voiture.getId(), voiture);
				/**
				System.out.println(result.getString("id") + " " + result.getString("CAPACITERESERVOIR") + " "
						+ result.getString("COULEUR") + " " + result.getString("ANNEEMISEENSERVICE") + " "
						+ result.getString("NBKILOMETRESCOMPTEUR") + " " + result.getString("NBLITRESCONTENUS") + " "
						+ result.getString("PRIXACHAT") + " " + result.getString("IDMARQUE") + " "
						+ result.getString("IDGARAGE") + " Bien Enregistré");
				 */
			}

			result.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voitures;

	}

/**
 * Permets l'initialisation des garages (objet de la classe Garage rangé 
 * dans un Hashmap de voiture ayant pour clé l'id du garage)
 *  de la base de donnée dans l'application.
 * @return Hashmap<Integer, Garage> 
 * @throws SQLException
 */
	public static HashMap<Integer, Garage> initgarage() throws SQLException {
		String sql = "select * from garage";
		HashMap<Integer, Garage> garages = new HashMap<Integer, Garage>() ;
		try {
			ResultSet result = chargeRequete(sql);
			while (result.next()) {
				Garage garage = new Garage(result.getInt("id"), result.getString("nomgarage"), 
						result.getString("ville"), initVoitures(result.getInt("id")));		
				garages.put(garage.getId(), garage);
			} }
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return garages;
	}

/**
 * Prends en paramètre une id (type integer) et retourne la marque (objet de la classe Marque)
 * ayant pour clé primaire l'id
 * @param id
 * @return Marque
 * @throws Exception
 */
	public static Marque getMarque(int id) throws Exception {
		Marque marque;
		ResultSet result;
		try {
			String sql = "select * from marque where id = " + id;
			result = chargeRequete(sql);


		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new Exception("Erreur lors de la requete SQL dans la méthode getMarque");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Erreur inconnu dans la méthode getMarque");
		}
		result.next();
		marque = new Marque(result.getInt("ID"),result.getString("Nom") ,Nationalite.valueOf(result.getString("Nationalite")));
		result.close();
		return marque;


	}


	/**
	 * Prends en paramètre un tableau de type String et une id de garage (type integer),
	 *  et ajoute une voiture au programme.
	 * Si celui-ci est possible, ajoute la voiture 
	 * via addVoitureSql(Voiture voiture, Integer idGarage)
	 * @param args
	 * @throws Exception
	 */
	public static void addVoiture(String[] args, Integer idGarage) throws Exception {
		try {

			int id = Integer.valueOf(args[0]);
			double capaciteReservoir = Double.valueOf(args[1]);
			Couleur couleur = Couleur.valueOf(args[2]);
			int anneemiseenservice;
			int nbKilometresCompteur;
			double nbLitresContenus;
			Voiture voiture;
			int prixAchat;

			if (args.length == 8) {
				anneemiseenservice = Integer.valueOf(args[3]);
				nbKilometresCompteur = Integer.valueOf(args[4]);
				nbLitresContenus = Double.parseDouble(args[5]);
				prixAchat = Integer.valueOf(args[6]);
				int idMarque = Integer.valueOf(args[7]);
				voiture = new Voiture(id, capaciteReservoir, couleur, anneemiseenservice, nbKilometresCompteur, nbLitresContenus, prixAchat,getMarque(idMarque));
				System.out.println("=====Ajout de la voiture 8arguments effectué avec succès=====");
			}
			else if (args.length == 6) {
				anneemiseenservice = Integer.valueOf(args[3]);
				prixAchat = Integer.valueOf(args[3]);
				int idMarque = Integer.valueOf(args[4]);
				voiture = new Voiture(id, capaciteReservoir, couleur, anneemiseenservice, prixAchat,getMarque(idMarque));
				System.out.println("=====Ajout de la voiture 6arguments effectué avec succès=====");
			}
			else {
				prixAchat = Integer.valueOf(args[3]);
				int idMarque = Integer.valueOf(args[4]);
				voiture = new Voiture(id, capaciteReservoir, couleur, prixAchat,getMarque(idMarque));
				System.out.println("=====Ajout de la voiture 5arguments effectué avec succès=====");
			}
			addVoitureSql(voiture, idGarage);

		}catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	
	/**
	 * Prends en paramètre un tableau de type String (et mets idGarage à null), et ajoute une voiture au programme.
	 * Si celui-ci est possible, ajoute la voiture via addVoitureSql(Voiture voiture, Integer idGarage)
	 * @param args
	 * @throws Exception
	 */
	public static void addVoiture(String[] args) throws Exception {
		addVoiture(args, null);
	}


/**
 * Méthode qui prends en parametre une voiture (objet de la classe Voiture) et
 * une id de garage (type integer) et l'ajoute dans la base de donnée via chargeUpdate().
 * @param voiture
 * @param idGarage
 */
	public static void addVoitureSql(Voiture voiture, Integer idGarage) {

		try {
			PreparedStatement sql = connectionOracleSQl.prepareStatement("insert into voiture (id, capacitereservoir, couleur, anneemiseenservice, nbkilometrescompteur, nblitrescontenus, prixachat, idmarque, idgarage ) values (?,?,?,?,?,?,?,?,?)");
			sql.setInt(1, voiture.getId());
			sql.setDouble(2, voiture.getCapaciteReservoir());
			sql.setString(3, voiture.getCouleur().name());
			sql.setInt(4, voiture.getAnneeMiseEnService());
			sql.setInt(5, voiture.getNbKilometresCompteur());
			sql.setDouble(6, voiture.getNbLitresContenus());
			sql.setDouble(7, voiture.getPrixAchat());
			sql.setInt(8, voiture.getMarque().getId());
			sql.setObject(9, idGarage, java.sql.Types.INTEGER);
			chargeUpdate(sql);
			System.out.println("=====Ajout de la voiture dans la base de donnée éffectué avec succès=====");
		} catch (SQLIntegrityConstraintViolationException ex) {
			System.out.println("|||||Erreur de contrainte d'intégrité référentiel SQL lors de l'ajout de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
		} catch (SQLException ex) {
			System.out.println("|||||Erreur SQL lors de l'ajout de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
		} catch (Exception ex) {
			System.out.println("|||||Erreur inconnu lors de l'ajout de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
			ex.printStackTrace();
		}

	}


	//public static Garage chargerVoituresGarage(Garage garage) {
	//return garage.
	//}

}
