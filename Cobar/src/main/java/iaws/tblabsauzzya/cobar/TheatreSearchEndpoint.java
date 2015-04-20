package iaws.tblabsauzzya.cobar;

import iaws.tblabsauzzya.ugmont.model.Salle;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Abel on 09/04/15.
 */

@Endpoint
public class TheatreSearchEndpoint {


    /**
     * namespace for hadled XML.
     */
    public static final String NAMESPACE = "http://projectCobart/namespace/scheme";
    /**
     * BackEnd Service for Restful API.
     */
    @Autowired
    private final UGmontBackendService service;

    /**
     *Constructor.
     */
    public TheatreSearchEndpoint() {
        service = UGmontBackendService.getInstance();
    }

    /**
     *@param movie .
     *@return sallesList
     *@throws SQLException .
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "MovieRequest")
    @ResponsePayload
    public final Element getSalleListHandler(@RequestPayload final Element movie)
            throws SQLException {

        Element sallesList = new Element("SallesListResponse", NAMESPACE);

        String filmId = movie.getChild("filmImdbId").getText();
        Set<Salle> salles = service.rechercheSalleAssocieeFilm(filmId);

        for (Iterator<Salle> it = salles.iterator(); it.hasNext();) {
            Element newSalle = buildElement(it.next(), NAMESPACE);
            sallesList.addContent(newSalle);
        }

        return sallesList;
    }

    /**
     *@param salle .
     *@param uri .
     *@return newSalle
     */
    private Element buildElement(final Salle salle,
                                 final String uri) {

        //Creation elements
        Element newSalle = new Element("Salle", uri);
        Element idSalle = new Element("idSalle");
        Element capacite = new Element("capacite");
        Element isMax = new Element("isMax");
        Element is3D = new Element("is3D");

        //setting content
        idSalle.setText(salle.idSalle.toString());
        capacite.setText(salle.capacite.toString());
        isMax.setText(salle.isIMAX.toString());
        is3D.setText(salle.is3D.toString());

        //building hierarchy
        newSalle.addContent(idSalle);
        newSalle.addContent(capacite);
        newSalle.addContent(isMax);
        newSalle.addContent(is3D);

        return newSalle;

    }
}
