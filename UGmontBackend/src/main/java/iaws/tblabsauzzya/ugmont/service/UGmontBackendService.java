package iaws.tblabsauzzya.ugmont.service;

import iaws.tblabsauzzya.ugmont.model.AssociationFilmSalle;
import iaws.tblabsauzzya.ugmont.model.Salle;
import iaws.tblabsauzzya.ugmont.model.Theatre;
import iaws.tblabsauzzya.ugmont.service.database.IUGmontDatabaseClient;
import iaws.tblabsauzzya.ugmont.service.database.UGmontH2DatabaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendService {

    private static final Logger _log = LoggerFactory.getLogger(UGmontBackendService.class);

    //
    //    ATTRIBUTES
    //

    private IUGmontDatabaseClient databaseClient = new UGmontH2DatabaseClient();


    //
    //    PUBLIC
    //

    /**
     *
     * @return
     * @throws SQLException
     */
    public Set<Salle> getListSalles() throws SQLException {

        return databaseClient.getSalles();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public Set<AssociationFilmSalle> getAssociationFilmSalle() throws SQLException {

        return databaseClient.getAssociationFilmSalle();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public Set<Salle> rechercheSalleAssocieeFilm(String filmImdbId) throws SQLException {

        return databaseClient.rechercheSalleAffectee(filmImdbId);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public void posterNouvelleAssociationFilmSalles(AssociationFilmSalle nouvelleAssociation) throws SQLException {

        databaseClient.posterAssociationSalle(nouvelleAssociation);
    }

    /**
     *
     * @param salleContenantCriteres Un objet salle contenant les critères à rechercher. Mettre les attributs à null si pas utilisé pour la recherche.
     *                               L'identifiant contenu dans l'objet salle sera ignoré.
     * @return La liste des salles correspondant aux critères
     * @throws SQLException
     */
    public Set<Salle> rechercheSalleAvecCritere(Salle salleContenantCriteres) throws SQLException {

        return databaseClient.rechercheSalleAvecCriteres(salleContenantCriteres);
    }


    /**
     *
     * @param id
     * @return
     */
    public List<Theatre> getListTheatres(int id) {
        return new ArrayList<Theatre>();
    }


    /**
     *
     */
    public void resetDatabase() {
        try {
            databaseClient.resetDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Impossible de remettre à zéro la base de données", e);
        }
    }


    //
    //    SINGLETON
    //

    private static UGmontBackendService __instance = new UGmontBackendService();

    private UGmontBackendService() {
        resetDatabase();
    }

    public static UGmontBackendService getInstance() {
        return __instance;
    }


}
