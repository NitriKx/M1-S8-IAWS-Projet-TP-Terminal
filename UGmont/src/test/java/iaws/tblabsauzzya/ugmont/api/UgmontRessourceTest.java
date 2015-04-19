package iaws.tblabsauzzya.ugmont.api;

import iaws.tblabsauzzya.ugmont.GrizzlyServerEntryPoint;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by terry on 19/04/15.
 */
public class UgmontRessourceTest {

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
    public void testGetFilms() {

        List<HashMap> responseMsg = target.path("film/recherche")
                .queryParam("nom", "star wars")
                .queryParam("anneeSortie", "2000")
                .request(MediaType.APPLICATION_JSON).get(List.class);

        assertEquals("tt0824442",
                responseMsg.get(2).get("imdbID"));
    }

}
