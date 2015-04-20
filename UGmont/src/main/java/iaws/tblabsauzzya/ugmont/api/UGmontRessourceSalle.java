package iaws.tblabsauzzya.ugmont.api;

import com.sun.org.apache.xpath.internal.operations.Bool;
import iaws.tblabsauzzya.ugmont.api.responses.RechercheSalleResponse;
import iaws.tblabsauzzya.ugmont.model.Salle;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by terry on 20/04/15.
 */
@Path("/salle")
public class UGmontRessourceSalle {

    @GET
    @Path("/recherche")
    @Produces(MediaType.APPLICATION_JSON)
    // E2 : de rechercher une liste de salles selon une liste de critères
    public RechercheSalleResponse rechercherListeSallesSelonCriteres (@QueryParam("capacite") Integer capacite, @QueryParam("isIMAX") Boolean isIMAX, @QueryParam("is3D") Boolean is3D) throws SQLException {

        // on met l'idSalle = null car on ne tient pas compte de l'id dans la recherche
        Salle salle = new Salle(null, capacite, isIMAX, is3D);
        UGmontBackendService service = UGmontBackendService.getInstance();
        Set<Salle> salleSet = service.rechercheSalleAvecCritere(salle);

        return new RechercheSalleResponse(salleSet);

    }

}
