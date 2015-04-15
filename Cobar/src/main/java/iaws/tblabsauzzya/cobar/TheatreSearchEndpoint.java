package iaws.tblabsauzzya.cobar;

import iaws.tblabsauzzya.ugmont.model.Salle;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;
import org.jdom.Element;
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


    public static final String NAMESPACE = "http://projectCobart/namespace";
    private final UGmontBackendService service = UGmontBackendService.getInstance();

    /**
     *
     */
    //@Autowired
    /*public TheatreSearchEndpoint() {
        service = UGmontBackendService.getInstance();
    }*/

    /**
     * @param movie
     * @return sallesList
     * @throws SQLException
     */
    @PayloadRoot(localPart = "Movie", namespace = NAMESPACE)
    @ResponsePayload
    public Element getSalleListHandler(@RequestPayload Element movie) throws SQLException {

        Element sallesList = new Element("SallesList", NAMESPACE);

        String filmId = movie.getChild("filmImdbId").getText();
        Set<Salle> salles = service.rechercheSalleAssocieeFilm(filmId);

        for (Iterator<Salle> it = salles.iterator(); it.hasNext();) {
            Element newSalle = buildElement(it.next(), filmId, NAMESPACE);
            sallesList.addContent(newSalle);
        }

        return sallesList;
    }

    /**
     * @param salle
     * @param uri
     * @param movieID
     * @return
     */
    private Element buildElement(Salle salle,String movieID ,String uri) {

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
