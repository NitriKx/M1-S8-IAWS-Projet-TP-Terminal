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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssociationFilmSalle that = (AssociationFilmSalle) o;

        if (dateDebut != null ? !dateDebut.equals(that.dateDebut) : that.dateDebut != null) return false;
        if (dateFin != null ? !dateFin.equals(that.dateFin) : that.dateFin != null) return false;
        if (filmImdbId != null ? !filmImdbId.equals(that.filmImdbId) : that.filmImdbId != null) return false;
        if (salleId != null ? !salleId.equals(that.salleId) : that.salleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filmImdbId != null ? filmImdbId.hashCode() : 0;
        result = 31 * result + (salleId != null ? salleId.hashCode() : 0);
        result = 31 * result + (dateDebut != null ? dateDebut.hashCode() : 0);
        result = 31 * result + (dateFin != null ? dateFin.hashCode() : 0);
        return result;
    }
}
