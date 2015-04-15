package iaws.tblabsauzzya.ugmont.service.database;

import iaws.tblabsauzzya.ugmont.model.AssociationFilmSalle;
import iaws.tblabsauzzya.ugmont.model.Salle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontH2DatabaseClient implements IUGmontDatabaseClient {

    //
    //   ATTRIBUTS
    //

    private static final Logger _log = LoggerFactory.getLogger(UGmontH2DatabaseClient.class);

    private Connection dbConn;



    //
    //  CONSTRUCTEUR
    //


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



    //
    //   PUBLIC
    //


    /**
     * {@inheritDoc}
     */
    public void resetDatabase() throws SQLException {

        _log.info("Remise à zéro de la base de données ...");

        // Suppression des données précédente
        PreparedStatement dropDatabasePreparedStatement = dbConn.prepareStatement("DROP TABLE IF EXISTS ASSOCIATIONS; DROP TABLE IF EXISTS SALLES;");
        dropDatabasePreparedStatement.executeUpdate();

        // Création des tables
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

        // Peuplement des tables précédemment crées
        PreparedStatement peuplementDatabasePreparedStatement = dbConn.prepareStatement("" +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(200, true, true); " +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(50, false, false); " +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(100, false, true); " +
                "INSERT INTO SALLES (capacite, isIMAX, is3D) VALUES(50, true, false); " +

                // Fast and Furious 7
                "INSERT INTO ASSOCIATIONS (idSalle, idImdbFilm, dateDebut, dateFin) VALUES(1, 'tt2820852', {ts '2015-04-01 00:00:00.000'}, {ts '2015-04-05 23:59:59.999'}); " +

                // Die Hard
                "INSERT INTO ASSOCIATIONS (idSalle, idImdbFilm, dateDebut, dateFin) VALUES(2, 'tt0095016', {ts '2015-04-03 00:00:00.000'}, {ts '2015-04-10 23:59:59.999'}); " +

                // Hitman le cobra
                "INSERT INTO ASSOCIATIONS (idSalle, idImdbFilm, dateDebut, dateFin) VALUES(3, 'tt1040019', {ts '2015-04-05 00:00:00.000'}, {ts '2015-04-10 23:59:59.999'}); " +
                "");
        peuplementDatabasePreparedStatement.executeUpdate();

        _log.info("Base de données remise à zéro");
    }

    /**
     * {@inheritDoc}
     */
    public Set<Salle> rechercheSalleAvecCriteres(Salle salleAvecCriteres) throws SQLException {

        // Contruction de la requête de recherche

        StringBuffer requeteRecherche = new StringBuffer();
        requeteRecherche.append("SELECT * FROM SALLES WHERE 1 = 1");

        if (salleAvecCriteres.capacite != null) {
            requeteRecherche.append(" AND capacite >= " + salleAvecCriteres.capacite);
        }

        if (salleAvecCriteres.isIMAX != null) {
            requeteRecherche.append(" AND isIMAX = " + salleAvecCriteres.isIMAX);
        }

        if (salleAvecCriteres.is3D != null) {
            requeteRecherche.append(" AND is3D = " + salleAvecCriteres.is3D);
        }

        PreparedStatement rechercheSalleAvecCriteresPreparedStatement = dbConn.prepareStatement(requeteRecherche.toString());

        if (!rechercheSalleAvecCriteresPreparedStatement.execute()) {
            throw new RuntimeException("Impossible d'exécuter la requête de recherche des salles par critères");
        }

        // Convertit la liste des resultats en objets salles
        Set<Salle> result = new LinkedHashSet<Salle>();
        ResultSet listeDesResultatRecherche = null;
        try {
            listeDesResultatRecherche = rechercheSalleAvecCriteresPreparedStatement.getResultSet();

            while (listeDesResultatRecherche.next()) {
                result.add(creerSalleAPartirResultSet(listeDesResultatRecherche));
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer la liste des salles à partir du résultat de la requête de recherche", e);


        } finally {
            try {
                if (listeDesResultatRecherche != null) {
                    listeDesResultatRecherche.close();
                }
            } catch (Exception e) {
                // Exception ingorée
            }

            try {
                rechercheSalleAvecCriteresPreparedStatement.close();
            } catch (Exception e) {
                // Exception ingorée
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void posterAssociationSalle(AssociationFilmSalle nouvelleAssociation) throws SQLException {


        PreparedStatement nouvelleAssociationSalleFilmPreparedRequest = dbConn.prepareStatement(
                "INSERT INTO ASSOCIATIONS (idSalle, idImdbFilm, dateDebut, dateFin) VALUES(?, ?, ?, ?);");

        nouvelleAssociationSalleFilmPreparedRequest.setInt(1, nouvelleAssociation.salleId);
        nouvelleAssociationSalleFilmPreparedRequest.setString(2, nouvelleAssociation.filmImdbId);
        nouvelleAssociationSalleFilmPreparedRequest.setDate(3, new java.sql.Date(nouvelleAssociation.dateDebut.getTime()));
        nouvelleAssociationSalleFilmPreparedRequest.setDate(4, new java.sql.Date(nouvelleAssociation.dateFin.getTime()));

        if (nouvelleAssociationSalleFilmPreparedRequest.executeUpdate() <= 0) {
            throw new RuntimeException(String.format("Impossible de poster la nouvelle association film salle pour le film=[%s] dateDebut=[%s] dateFin=[%s]", nouvelleAssociation.filmImdbId, nouvelleAssociation.dateDebut, nouvelleAssociation.dateFin));
        }
    }

    /**
     * {@inheritDoc}
     */
    public Set<Salle> rechercheSalleAffectee(String filmImdbId) throws SQLException {

        PreparedStatement rechercheSalleAffecteePreparedStatement = dbConn.prepareStatement("SELECT s.idSalle, s.capacite, s.isIMAX, s.is3D FROM ASSOCIATIONS a, SALLES s WHERE a.idImdbFilm = ? AND s.idSalle = a.idSalle");

        rechercheSalleAffecteePreparedStatement.setString(1, filmImdbId);

        if (!rechercheSalleAffecteePreparedStatement.execute()) {
            throw new RuntimeException("Impossible d'exécuter la requête de recherche des salles affectée");
        }

        // Convertit la liste des resultats en objets salles
        Set<Salle> result = new LinkedHashSet<Salle>();
        ResultSet listeDesResultatRecherche = null;
        try {
            listeDesResultatRecherche = rechercheSalleAffecteePreparedStatement.getResultSet();

            while (listeDesResultatRecherche.next()) {
                result.add(creerSalleAPartirResultSet(listeDesResultatRecherche));
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer la liste des salles à partir du résultat de la requête de recherche", e);


        } finally {
            try {
                if (listeDesResultatRecherche != null) {
                    listeDesResultatRecherche.close();
                }
            } catch (Exception e) {
                // Exception ingorée
            }

            try {
                rechercheSalleAffecteePreparedStatement.close();
            } catch (Exception e) {
                // Exception ingorée
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    public Set<Salle> getSalles() throws SQLException  {
        PreparedStatement listeDesSallesPreparedStatement = dbConn.prepareStatement("SELECT * FROM SALLES");

        if (!listeDesSallesPreparedStatement.execute()) {
            throw new RuntimeException("Impossible d'exécuter la requête de récupération de la liste des salles");
        }

        // Convertit la liste des resultats en objets salles
        Set<Salle> result = new LinkedHashSet<Salle>();
        ResultSet listeDesSalles = null;
        try {
            listeDesSalles = listeDesSallesPreparedStatement.getResultSet();

            while (listeDesSalles.next()) {
                result.add(creerSalleAPartirResultSet(listeDesSalles));
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer la liste des salles à partir du résultat de la requête", e);


        } finally {
            try {
                if (listeDesSalles != null) {
                    listeDesSalles.close();
                }
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

    /**
     * {@inheritDoc}
     */
    public Set<AssociationFilmSalle> getAssociationFilmSalle() throws SQLException {

        PreparedStatement listeDesAssociationFilmSallesPreparedStatement = dbConn.prepareStatement("SELECT * FROM ASSOCIATIONS");

        if (!listeDesAssociationFilmSallesPreparedStatement.execute()) {
            throw new RuntimeException("Impossible d'exécuter la requête de récupération de la liste des associations film salle");
        }

        // Convertit la liste des resultats en objets salles
        Set<AssociationFilmSalle> result = new LinkedHashSet<AssociationFilmSalle>();
        ResultSet listeDesAssociationFilmSalle = null;
        try {
            listeDesAssociationFilmSalle = listeDesAssociationFilmSallesPreparedStatement.getResultSet();

            while (listeDesAssociationFilmSalle.next()) {
                result.add(creerAssociationFilmSalleAPartirResultSet(listeDesAssociationFilmSalle));
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer la liste des associations film salle à partir du résultat de la requête", e);


        } finally {
            try {
                if (listeDesAssociationFilmSalle != null) {
                    listeDesAssociationFilmSalle.close();
                }
            } catch (Exception e) {
                // Exception ingorée
            }

            try {
                listeDesAssociationFilmSallesPreparedStatement.close();
            } catch (Exception e) {
                // Exception ingorée
            }
        }
    }





    //
    //   PRIVATE HELPERS
    //


    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Salle creerSalleAPartirResultSet(ResultSet resultSet) throws SQLException {

        final int idSalle = resultSet.getInt("idSalle");
        final int capacite = resultSet.getInt("capacite");
        final boolean isIMAX = resultSet.getBoolean("isIMAX");
        final boolean is3D = resultSet.getBoolean("is3D");
        return new Salle(idSalle, capacite, isIMAX, is3D);
    }

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private AssociationFilmSalle creerAssociationFilmSalleAPartirResultSet(ResultSet resultSet) throws SQLException {

        final String filmImdbId = resultSet.getString("idImdbFilm");
        final Integer salleId = resultSet.getInt("idSalle");
        final Date dateDebut = resultSet.getDate("dateDebut");
        final Date dateFin = resultSet.getDate("dateFin");
        return new AssociationFilmSalle(filmImdbId, salleId, dateDebut, dateFin);
    }

}
