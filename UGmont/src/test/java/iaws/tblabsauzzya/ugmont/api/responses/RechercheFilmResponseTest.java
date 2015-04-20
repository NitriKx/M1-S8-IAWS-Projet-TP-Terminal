package iaws.tblabsauzzya.ugmont.api.responses;

import iaws.tblabsauzzya.ugmont.model.Film;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by terry on 20/04/15.
 */
public class RechercheFilmResponseTest {

    @Test
    public void testCreation() {
        RechercheFilmResponse response = new RechercheFilmResponse(new ArrayList<Film>());
    }
}
