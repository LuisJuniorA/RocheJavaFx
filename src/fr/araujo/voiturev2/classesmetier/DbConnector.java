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
import java.time.LocalDate;
import java.util.HashMap;

import fr.araujo.voiturev2.enumerations.Couleur;
import fr.araujo.voiturev2.enumerations.Nationalite;

/**
 * Classe permettant toutes interactions entre l'application et la base de donnée.
 */
public class DbConnector {
	static HashMap<Integer, Garage> garages = new HashMap<Integer, Garage>() ;
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
			connectionOracleSQl = DriverManager.getConnection(url2, nom, pwd);
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
			throw new Exception("|||||Erreur lors de la requete SQL dans la méthode getMarque|||||");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("|||||Erreur inconnu dans la méthode getMarque|||||");
		}
		result.next();
		marque = new Marque(result.getInt("ID"),result.getString("Nom") ,Nationalite.valueOf(result.getString("Nationalite")));
		result.close();
		return marque;


	}

	/**
	 * Prends en paramètre la requête sql, une voiture, et un id de garage, afin d'ajouter les valeurs de la voiture dans la requête.
	 * @param sql
	 * @param voiture
	 * @param idGarage
	 * @throws SQLException
	 */
	private static void chargePreparedStatementVoiture(PreparedStatement sql, Voiture voiture, Integer idGarage) throws SQLException {
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
	}

	/**
	 * Prends en paramètre les informations nécessaire à la création d'une voiture pour l'ajouter dans la base de donnée.
	 * @param id
	 * @param capaciteReservoir
	 * @param couleur
	 * @param anneeMiseEnService
	 * @param nbKilometresCompteur
	 * @param nbLitresContenus
	 * @param prixAchat
	 * @param idMarque
	 * @param idGarage
	 */
	public static void addVoiture(int id, double capaciteReservoir, Couleur couleur, int anneeMiseEnService, int nbKilometresCompteur,
			double nbLitresContenus, int prixAchat, int idMarque, Integer idGarage) {
		try {
			Voiture voiture = new Voiture(id, capaciteReservoir, couleur, anneeMiseEnService, nbKilometresCompteur, nbLitresContenus, prixAchat,getMarque(idMarque));
			System.out.println("=====Ajout de la voiture 8 arguments effectué avec succès=====");
			addVoitureSql(voiture, idGarage);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}


	/**
	 * Prends en paramètre les informations nécessaire à la création d'une voiture pour l'ajouter dans la base de donnée. Surcharge de la méthode addVoiture.
	 * @param id
	 * @param capaciteReservoir
	 * @param couleur
	 * @param anneeMiseEnService
	 * @param prixAchat
	 * @param idMarque
	 * @param idGarage
	 */
	public static void addVoiture(int id, double capaciteReservoir, Couleur couleur,
			int anneeMiseEnService, int prixAchat, int idMarque, Integer idGarage) {
		addVoiture(id, capaciteReservoir, couleur, anneeMiseEnService, 0, 0.0, prixAchat,idMarque, idGarage);
		System.out.println("=====Ajout de la voiture 6 arguments effectué avec succès=====");
	}


	/**
	 * Prends en paramètre les informations nécessaire à la création d'une voiture pour l'ajouter dans la base de donnée. Surcharge de la méthode addVoiture.
	 * @param id
	 * @param capaciteReservoir
	 * @param couleur
	 * @param prixAchat
	 * @param idMarque
	 * @param idGarage
	 */
	public static void addVoiture(int id, double capaciteReservoir, Couleur couleur,
			int prixAchat, int idMarque, Integer idGarage) {
		addVoiture(id, capaciteReservoir, couleur, LocalDate.now().getYear(), prixAchat,idMarque, idGarage);
		System.out.println("=====Ajout de la voiture 5 arguments effectué avec succès=====");
	}

	/**
	 * Prends en paramètre les informations nécessaire à la création d'une voiture pour l'ajouter dans la base de donnée. Surcharge de la méthode addVoiture.
	 * @param id
	 * @param capaciteReservoir
	 * @param couleur
	 * @param prixAchat
	 * @param idMarque
	 */
	public static void addVoiture(int id, double capaciteReservoir, Couleur couleur,
			int prixAchat, int idMarque) {
		addVoiture(id, capaciteReservoir, couleur, LocalDate.now().getYear(), prixAchat,idMarque, null);
		System.out.println("=====Ajout de la voiture 5 arguments effectué avec succès=====");
	}

	/**
	 * Prends en paramètre une voiture pour l'ajouter dans la base de donnée. Surcharge de la méthode addVoiture.
	 * @param voiture
	 * @param idGarage
	 */
	public static void addVoiture(Voiture voiture, Integer idGarage) {
		addVoitureSql(voiture, idGarage);
		System.out.println("=====Ajout de la voiture via Voiture effectué avec succès=====");
	}

	/**
	 * * Prends en paramètre une voiture pour l'ajouter dans la base de donnée. Surcharge de la méthode addVoiture.
	 * @param voiture
	 */
	public static void addVoiture(Voiture voiture) {
		addVoiture(voiture, null);
	}


	/**
	 * Méthode qui prends en parametre une voiture (objet de la classe Voiture) et
	 * une id de garage (type integer) et l'ajoute dans la base de donnée via chargeUpdate().
	 * @param voiture
	 * @param idGarage
	 */
	private static void addVoitureSql(Voiture voiture, Integer idGarage) {

		try {
			PreparedStatement sql = connectionOracleSQl.prepareStatement("insert into voiture (id, capacitereservoir, couleur, anneemiseenservice, nbkilometrescompteur, nblitrescontenus, prixachat, idmarque, idgarage ) values (?,?,?,?,?,?,?,?,?)");
			chargePreparedStatementVoiture(sql, voiture, idGarage);
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

	/**
	 * Prends en paramètre un id de voiture et un id de garage afin de supprimer dans le programme et dans la base de donnée la voiture.
	 * @param idVoiture
	 * @param idGarage
	 */
	public static void deleteVoiture(int idVoiture, Integer idGarage) {
		try {
			if (idGarage != null) {
				HashMap<Integer, Voiture> voitures = garages.get(idGarage).voitures;
				voitures.remove(idVoiture);
			}
			deleteSql("VOITURE", idVoiture);
			System.out.println("=====Suppresion de la voiture dans la base de donnée éffectué avec succès=====");

		} catch (SQLException e) {
			System.out.println("|||||Erreur lors de la supression de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
			e.printStackTrace();
		}
	}

	/**
	 * Prends en paramètre une table et un id et supprime les valeurs correspondantes dans la table.
	 * @param table
	 * @param id
	 * @throws SQLException
	 */
	private static void deleteSql(String table, int id) throws SQLException {
		PreparedStatement sql = connectionOracleSQl.prepareStatement("DELETE FROM " + table + " where id = ?");
		sql.setInt(1, id);
		chargeUpdate(sql);
		System.out.println("=====Suppresion de la donnée dans la base de donnée éffectué avec succès=====");

	}


	/**
	 * Prends en paramètre les données d'une voiture afin de la changer dans la base de donnée.
	 * @param id
	 * @param capaciteReservoir
	 * @param couleur
	 * @param anneeMiseEnService
	 * @param nbKilometresCompteur
	 * @param nbLitresContenus
	 * @param prixAchat
	 * @param idMarque
	 * @param idGarage
	 */
	public static void updateVoiture(int id, double capaciteReservoir, Couleur couleur, int anneeMiseEnService, int nbKilometresCompteur,
			double nbLitresContenus, int prixAchat, int idMarque, Integer idGarage) {
		try {
			Voiture voiture = new Voiture(id, capaciteReservoir, couleur, anneeMiseEnService, nbKilometresCompteur, nbLitresContenus, prixAchat, getMarque(idMarque));
			PreparedStatement sql = connectionOracleSQl.prepareStatement("update VOITURE set id = ?, capacitereservoir = ?, couleur = ?, anneemiseenservice = ?, nbkilometrescompteur = ?, nblitrescontenus = ?,prixachat = ?, idmarque = ?, idgarage = ?  where id = " + id );
			chargePreparedStatementVoiture(sql, voiture, idGarage);
			System.out.println("=====Update de la voiture dans la base de donnée éffectué avec succès=====");
		} catch (SQLIntegrityConstraintViolationException ex) {
			System.out.println("|||||Erreur de contrainte d'intégrité référentiel SQL lors de l'update de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
		} catch (SQLException ex) {
			System.out.println("|||||Erreur SQL lors de l'update de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("|||||Erreur inconnu lors de l'update de la voiture dans la base de données, veuillez contactez la personne en charge de l'application.|||||");
			ex.printStackTrace();
		}


	}









}
