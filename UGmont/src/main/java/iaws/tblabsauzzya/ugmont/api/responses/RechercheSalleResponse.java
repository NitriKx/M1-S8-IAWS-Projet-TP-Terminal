package iaws.tblabsauzzya.ugmont.api.responses;

import iaws.tblabsauzzya.ugmont.model.Salle;
import org.glassfish.grizzly.utils.ArraySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by terry on 20/04/15.
 */
public class RechercheSalleResponse {

    public Set<Salle> listeSalles = new HashSet<>();

    public RechercheSalleResponse() {}

    public RechercheSalleResponse(Set<Salle> listeSalles) {
        this.listeSalles = listeSalles;
    }
}
