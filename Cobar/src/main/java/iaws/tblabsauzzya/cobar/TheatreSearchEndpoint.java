package iaws.tblabsauzzya.cobar;

import iaws.tblabsauzzya.ugmont.model.Theatre;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;
import org.jdom.Element;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

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
     * @return theatreList
     */
    @PayloadRoot(localPart = "Movie", namespace = NAMESPACE)
    @ResponsePayload
    public Element theatreListRequest(@RequestPayload Element movie) {

        Element theatreList = new Element("TheatreList", NAMESPACE);

        int id = Integer.parseInt(movie.getChild("Id").getText());
        List<Theatre> response = service.getListTheatres(id);

        for(int i = 0; i<response.size(); i++) {
            Element theatre = buildElement(response.get(i),NAMESPACE);
            theatreList.addContent(theatre);
        }

        return theatreList;
    }

    /**
     * @param t
     * @param uri
     * @return
     */
    private Element buildElement(Theatre t, String uri) {

        //Creation elements
        Element theatre = new Element("Theatre", uri);
        Element name = new Element("Name");
        Element adresse = new Element("Adresse");
        Element city = new Element("City");

        //setting content
        name.setText(t.getName());
        adresse.setText(t.getAdresse());
        city.setText(t.getCity());

        //building hierarchy
        theatre.addContent(name);
        theatre.addContent(adresse);
        theatre.addContent(city);

        return theatre;

    }
}
