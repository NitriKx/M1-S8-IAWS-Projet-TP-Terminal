package iaws.tblabsauzzya.cobar;

import org.jdom.Element;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Abel on 09/04/15.
 */

@Endpoint
public class TheatreSearchEndpoint {


    @PayloadRoot(localPart = "Movie", namespace = "http://projectCobart/namespace")
    @ResponsePayload
    public Element theatreListRequest(@RequestPayload Element movie) {

        Element theatreList = movie;

        return theatreList;
    }
}
