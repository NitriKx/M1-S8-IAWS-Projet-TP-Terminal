package iaws.tblabsauzzya.ugmont.service.database;

import iaws.tblabsauzzya.ugmont.model.AssociationFilmSalle;
import iaws.tblabsauzzya.ugmont.model.Salle;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Benoît Sauvères on 25/03/15.
 */
public interface IUGmontDatabaseClient {

    /**
     * Efface l'intégralité de la base et la reconstruit avec des données initiales.
     */
    public void resetDatabase() throws SQLException;

    /**
     * Recherche une salle en se basant sur les critère de la salle passée en paramètre.
     * @param salleAvecCriteres La salle à rechercher. Mettre les attribus ignorés à null
     * @return La liste des salles correspondant à tout les critères
     */
    public List<Salle> rechercheSalleAvecCriteres(Salle salleAvecCriteres);

    /**
     * Créer une nouvelle association entre un salle et un film
     * @param nouvelleAssociation
     */
    public void posterAssociationSalle(AssociationFilmSalle nouvelleAssociation);

    /**
     * Recherche toute les salles associée à un film
     * @param filmImdbId
     * @return
     */
    public Set<Salle> rechercheSalleAffectee(String filmImdbId);

    /**
     * Retourne toute les salles contenues dans la base de données.
     * @return toute les salles contenues dans la base de données
     */
    public Set<Salle> getSalles() throws SQLException;
}
