package iaws.tblabsauzzya.ugmont.api;

import iaws.tblabsauzzya.ugmont.api.responses.RechercheFilmResponse;
import iaws.tblabsauzzya.ugmont.model.Film;
import org.w3c.dom.NodeList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by terry on 25/03/15.
 */
@Path("/film")
public class UgmontRessourceFilm {

    @GET
    @Path("/recherche")
    @Produces(MediaType.APPLICATION_JSON)
    // E1 : de récupérer une liste de films (identifiés par leur ID IMDb) à partir de
    // leur titre et/ou de leur année de sortie
    public RechercheFilmResponse getFilms(@QueryParam("nom") String nom, @QueryParam("anneeSortie") String anneeSortie) throws XPathExpressionException {

        List<Film> imdbIDs = new LinkedList<>();

        Client c = ClientBuilder.newClient();
        DOMSource returnXML = c.target("http://www.omdbapi.com")
                .queryParam("s", nom)
                .queryParam("y", anneeSortie)
                .queryParam("plot", "short")
                .queryParam("r", "xml")
                .request(MediaType.TEXT_XML)
                .get(DOMSource.class);

        XPath xpath = XPathFactory.newInstance().newXPath();

        NodeList nodes = (NodeList) xpath.evaluate("//Movie", returnXML.getNode(), XPathConstants.NODESET);

        for(int i = 0; i < nodes.getLength(); i++)
            imdbIDs.add(new Film(
                    nodes.item(i).getAttributes().getNamedItem("Title").getNodeValue(),
                    nodes.item(i).getAttributes().getNamedItem("Type").getNodeValue(),
                    nodes.item(i).getAttributes().getNamedItem("Year").getNodeValue(),
                    nodes.item(i).getAttributes().getNamedItem("imdbID").getNodeValue()));

        return new RechercheFilmResponse(imdbIDs);

    }
}
