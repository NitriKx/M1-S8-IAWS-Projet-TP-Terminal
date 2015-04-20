package iaws.tblabsauzzya.ugmont.model.exceptions;

/**
 * Created by Benoît Sauvère on 20/04/15.
 */
public class SalleInconnueException extends Exception {

    public SalleInconnueException(Integer idSalleInconnu) {
        super(String.format("La salle avec id=[%d] est inconnue", idSalleInconnu));
    }


}
