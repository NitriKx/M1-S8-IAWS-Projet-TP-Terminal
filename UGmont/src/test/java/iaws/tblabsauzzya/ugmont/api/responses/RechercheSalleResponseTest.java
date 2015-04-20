package iaws.tblabsauzzya.ugmont.api.responses;

import iaws.tblabsauzzya.ugmont.model.Salle;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by terry on 20/04/15.
 */
public class RechercheSalleResponseTest {

    @Test
    public void testCreation() {

        RechercheSalleResponse response = new RechercheSalleResponse(new HashSet<Salle>());

    }

}
