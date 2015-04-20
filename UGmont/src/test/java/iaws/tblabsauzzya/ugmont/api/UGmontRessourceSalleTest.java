package iaws.tblabsauzzya.ugmont.api;

import iaws.tblabsauzzya.ugmont.GrizzlyServerEntryPoint;
import iaws.tblabsauzzya.ugmont.api.responses.RechercheFilmResponse;
import iaws.tblabsauzzya.ugmont.api.responses.RechercheSalleResponse;
import iaws.tblabsauzzya.ugmont.model.Salle;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;

/**
 * Created by terry on 20/04/15.
 */
public class UGmontRessourceSalleTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = GrizzlyServerEntryPoint.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(GrizzlyServerEntryPoint.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testRechercherListeSallesSelonCriteres() {

        RechercheSalleResponse responseMsg = target.path("salle/recherche")
                .queryParam("capacite", "100")
                .queryParam("isIMAX", "true")
                .queryParam("is3D", "true")
                .request(MediaType.APPLICATION_JSON).get(RechercheSalleResponse.class);

        Assert.assertEquals(new Integer(1), responseMsg.listeSalles.iterator().next().idSalle);

    }

}
