package iaws.tblabsauzzya.ugmont.model;


/**
 * Created by Benoît Sauvère on 01/04/15.
 */
public class Salle {

    public Integer idSalle;

    public Integer capacite;

    public Boolean isIMAX;

    public Boolean is3D;

    public Salle(Integer idSalle, Integer capacite, Boolean isIMAX, Boolean is3D) {
        this.idSalle = idSalle;
        this.capacite = capacite;
        this.isIMAX = isIMAX;
        this.is3D = is3D;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salle salle = (Salle) o;

        if (capacite != null ? !capacite.equals(salle.capacite) : salle.capacite != null) return false;
        if (idSalle != null ? !idSalle.equals(salle.idSalle) : salle.idSalle != null) return false;
        if (is3D != null ? !is3D.equals(salle.is3D) : salle.is3D != null) return false;
        if (isIMAX != null ? !isIMAX.equals(salle.isIMAX) : salle.isIMAX != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSalle != null ? idSalle.hashCode() : 0;
        result = 31 * result + (capacite != null ? capacite.hashCode() : 0);
        result = 31 * result + (isIMAX != null ? isIMAX.hashCode() : 0);
        result = 31 * result + (is3D != null ? is3D.hashCode() : 0);
        return result;
    }
}
