package iaws.tblabsauzzya.ugmont.api;

import iaws.tblabsauzzya.ugmont.GrizzlyServerEntryPoint;
import iaws.tblabsauzzya.ugmont.api.responses.RechercheFilmResponse;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by terry on 19/04/15.
 */
public class UgmontRessourceFilmTest {

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

        UGmontBackendService.getInstance().resetDatabase();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testGetFilms() {

        RechercheFilmResponse responseMsg = target.path("film/recherche")
                .queryParam("nom", "star wars")
                .queryParam("anneeSortie", "2000")
                .request(MediaType.APPLICATION_JSON).get(RechercheFilmResponse.class);

        assertEquals("tt0241986",
                responseMsg.listeFilms.get(1).getImdbID());
    }


    //
    //
    //        POSTER NOUVELLE ASSOCIATION FILM SALLE
    //
    //


    @Test
    public void testPosterNouvelleAssociationAvecToutLesParametres() {

        String idFilm = "tt0944947";
        Integer idSalleValide = 1;

        final Response response = target.path(String.format("film/%s/association/%d", idFilm, idSalleValide))
                .queryParam("dateDebut", new Date().getTime())
                        // aujourd'hui + 20 jours
                .queryParam("dateFin", new Date().getTime() + 1000 * 60 * 60 * 24 * 20)
                .request(MediaType.APPLICATION_JSON).post(Entity.text(""));

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testPosterNouvelleAssociationSansDate() {

        String idFilm = "tt0944947";
        Integer idSalleValide = 1;

        final Response response = target.path(String.format("film/%s/association/%d", idFilm, idSalleValide))
                .request(MediaType.APPLICATION_JSON).post(Entity.text(""));

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testPosterNouvelleAssociationAvecSalleInvalide() {

        String idFilm = "tt0944947";
        Integer idSalleInvalide = 20000;

        final Response response = target.path(String.format("film/%s/association/%d", idFilm, idSalleInvalide))
                .request(MediaType.APPLICATION_JSON).post(Entity.text(""));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void testPosterNouvelleAssociationAvecSalleValideEtDatesInvalide() {

        String idFilm = "tt0944947";
        Integer idSalleValide = 1;

        final Response response = target.path(String.format("film/%s/association/%d", idFilm, idSalleValide))
                // aujourd'hui + 20 jours
                .queryParam("dateDebut", new Date().getTime() + 1000 * 60 * 60 * 24 * 20)
                .queryParam("dateFin", new Date().getTime())
                .request(MediaType.APPLICATION_JSON).post(Entity.text(""));

        Assert.assertEquals(400, response.getStatus());
    }

}
