package iaws.tblabsauzzya.ugmont.api.responses;

import iaws.tblabsauzzya.ugmont.model.Film;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by terry on 20/04/15.
 */
@XmlRootElement
public class RechercheFilmResponse {

    public List<Film> listeFilms = new ArrayList<>();

    public RechercheFilmResponse() {}

    public RechercheFilmResponse(List<Film> listeFilms) {
        this.listeFilms = listeFilms;
    }
}
