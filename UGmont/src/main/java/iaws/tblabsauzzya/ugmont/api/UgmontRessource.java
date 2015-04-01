package iaws.tblabsauzzya.ugmont.api;

import iaws.tblabsauzzya.ugmont.model.Film;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.transform.dom.DOMSource;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 25/03/15.
 */
@Path("/film")
public class UgmontRessource {

    @GET
    @Path("/recherche")
    public DOMSource getFilms(@QueryParam("nom") String nom) {

        List<Film> films = new LinkedList<>();

        Client c = ClientBuilder.newClient();
        DOMSource returnXML = c.target("http://www.omdbapi.com")
                .queryParam("s", nom)
                .queryParam("y", "")
                .queryParam("r", "xml")
                .request(MediaType.TEXT_XML)
                .get(DOMSource.class);

        return returnXML;

        //Récupère le XML


    }
}
