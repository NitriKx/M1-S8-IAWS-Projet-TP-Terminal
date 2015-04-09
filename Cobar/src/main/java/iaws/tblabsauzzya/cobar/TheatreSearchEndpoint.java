package iaws.tblabsauzzya.cobar;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Abel on 09/04/15.
 */

@Endpoint
public class TheatreSearchEndpoint {


    public static final String NAMESPACE = "http://projectCobart/namespace";

    @PayloadRoot(localPart = "Movie", namespace = NAMESPACE)
    @ResponsePayload
    public Element theatreListRequest(@RequestPayload Element movie) throws JAXBException, IOException, JDOMException {

        Element theatreList = null;

        // To revise
        String uri = "http://localhost:8080/myapp/"+movie.getAttributeValue("id");
        URL url = new URL(uri);

        //Request to the REST API
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        //building jdom document from inputStream
        SAXBuilder sb = new SAXBuilder();
        InputStream xml = connection.getInputStream();
        sb.build(xml);

        //need to parse Element from jdom document
        //theatreList =


        connection.disconnect();


        return theatreList;
    }
}
