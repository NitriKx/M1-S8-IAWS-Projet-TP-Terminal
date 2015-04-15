package iaws.tblabsauzzya.ugmont.service.database;

import iaws.tblabsauzzya.ugmont.model.AssociationFilmSalle;
import iaws.tblabsauzzya.ugmont.model.Salle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontH2DatabaseClient implements IUGmontDatabaseClient {

    private static final Logger _log = LoggerFactory.getLogger(UGmontH2DatabaseClient.class);



    private Connection dbConn;

    public UGmontH2DatabaseClient() {

        _log.info("Ouverture de la base de données...");

        try {
            Class.forName("org.h2.Driver");
            dbConn = DriverManager.getConnection("jdbc:h2:mem:ugmont_memory_db", "sa", "");

        } catch (Exception e) {
            throw new RuntimeException("Impossible d'ouvrir la base de données in-memory", e);
        }

        _log.info("Base de données ouverte");
    }


    public void resetDatabase() throws SQLException {

        _log.info("Remise à zéro de la base de données ...");

        PreparedStatement dropDatabasePreparedStatement = dbConn.prepareStatement("DROP TABLE IF EXISTS ASSOCIATIONS; DROP TABLE IF EXISTS SALLES;");

        dropDatabasePreparedStatement.executeUpdate();

        PreparedStatement createDatabasePreparedStatement = dbConn.prepareStatement("" +
                "CREATE TABLE SALLES (\n" +
                "idSalle INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "capacite INT NOT NULL,\n" +
                "isIMAX BOOLEAN NOT NULL,\n" +
                "is3D BOOLEAN NOT NULL\n" +
                ");\n" +

                "CREATE TABLE ASSOCIATIONS (\n" +
                "idSalle INT NOT NULL,\n" +
                "idImdbFilm VARCHAR NOT NULL,\n" +
                "dateDebut DATE NOT NULL,\n" +
                "dateFin DATE NOT NULL\n" +
                ");\n" +

                "ALTER TABLE ASSOCIATIONS ADD PRIMARY KEY (idSalle, idImdbFilm, dateDebut);" +

                "");

        createDatabasePreparedStatement.executeUpdate();

        PreparedStatement peuplementDatabasePreparedStatement = dbConn.prepareStatement("" +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(200, true, true);" +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(50, false, false);" +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(100, false, true);" +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(50, true, false);" +
                "");



        peuplementDatabasePreparedStatement.executeUpdate();

        _log.info("Remise à zéro de la base de données ...");
    }

    public List<Salle> rechercheSalleAvecCriteres(Salle salleAvecCriteres) {
        return null;
    }

    public void posterAssociationSalle(AssociationFilmSalle nouvelleAssociation) {

    }

    public Set<Salle> rechercheSalleAffectee(String filmImdbId) {
        return null;
    }


    public Set<Salle> getSalles() throws SQLException  {
        PreparedStatement listeDesSallesPreparedStatement = dbConn.prepareStatement("" +
                "SELECT * FROM SALLES");

        if (listeDesSallesPreparedStatement.execute() == false) {
            throw new RuntimeException("Impossible d'exécuter la requête de récupération de la liste des salles");
        }

        Set<Salle> result = new LinkedHashSet<Salle>();
        ResultSet listeDesSalles = null;
        try {
            listeDesSalles = listeDesSallesPreparedStatement.getResultSet();

            while (listeDesSalles.next()) {

                final int idSalle = listeDesSalles.getInt("idSalle");
                final int capacite = listeDesSalles.getInt("capacite");
                final boolean isIMAX = listeDesSalles.getBoolean("isIMAX");
                final boolean is3D = listeDesSalles.getBoolean("is3D");
                result.add(new Salle(idSalle, capacite, isIMAX, is3D));
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer la liste des salles à partir du résultat de la requête", e);


        } finally {
            try {
                listeDesSalles.close();
            } catch (Exception e) {
                // Exception ingorée
            }

            try {
                listeDesSallesPreparedStatement.close();
            } catch (Exception e) {
                // Exception ingorée
            }
        }


    }
}
