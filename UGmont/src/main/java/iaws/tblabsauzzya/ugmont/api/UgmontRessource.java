package iaws.tblabsauzzya.ugmont.api;

import com.sun.media.jfxmedia.Media;
import iaws.tblabsauzzya.ugmont.model.Film;
import org.w3c.dom.NodeList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.*;

/**
 * Created by terry on 25/03/15.
 */
@Path("/film")
public class UgmontRessource {

    @GET
    @Path("/recherche")
    public GenericEntity<List<String>> getFilms(@QueryParam("nom") String nom, @QueryParam("anneeSortie") String anneeSortie) throws XPathExpressionException {

        List<String> imdbIDs = new LinkedList<>();

        Client c = ClientBuilder.newClient();
        DOMSource returnXML = c.target("http://www.omdbapi.com")
                .queryParam("s", "star wars")
                .queryParam("y", "")
                .queryParam("plot", "short")
                .queryParam("r", "xml")
                .request(MediaType.TEXT_XML)
                .get(DOMSource.class);

        XPath xpath = XPathFactory.newInstance().newXPath();

        NodeList nodes = (NodeList) xpath.evaluate("//Movie", returnXML.getNode(), XPathConstants.NODESET);

        for(int i = 0; i < nodes.getLength(); i++)
            imdbIDs.add(nodes.item(i).getAttributes().getNamedItem("imdbID").getNodeValue());

        return new <List<String>>(imdbIDs) {};

    }
}
