package iaws.tblabsauzzya.ugmont.model;

import java.util.Date;

/**
 * Created by Benoît Sauvère on 01/04/15.
 */
public class AssociationFilmSalle {

    public String filmImdbId;

    public Integer salleId;

    public Date dateDebut;

    public Date dateFin;

    public AssociationFilmSalle(String filmImdbId, Integer salleId, Date dateDebut, Date dateFin) {
        this.filmImdbId = filmImdbId;
        this.salleId = salleId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}
