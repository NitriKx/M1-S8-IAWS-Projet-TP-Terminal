package iaws.tblabsauzzya.ugmont.api;

import iaws.tblabsauzzya.ugmont.api.responses.RechercheFilmResponse;
import iaws.tblabsauzzya.ugmont.model.AssociationFilmSalle;
import iaws.tblabsauzzya.ugmont.model.Film;
import iaws.tblabsauzzya.ugmont.model.exceptions.SalleInconnueException;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;
import org.w3c.dom.NodeList;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Calendar;
import java.util.Date;
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


    /**
     * <p>Permet de définir une association film-salle. Si la date de début n'est pas spécifiée, la date courant sera prise.
     * Si la date de fin n'est pas précisée, la date {dateDebut+1 mois) sera prise.</p>
     *
     * <p>Codes d'erreur :
     *  <ul>
     *      <li>404: Salle inconnue</li>
     *      <li>400: Date de fin avant la date de début</li>
     *  </ul>
     * </p>
     *
     * @param filmId l'id OMDB du film
     * @param salleId l'id de la salle à affecter
     * @param timestampDebutAssociation
     * @param timestampFinAssociation
     * @return compte rendu textuel d'exécution
     */
    @POST
    @Path("/{filmId}/association/{salleId}")
    public Response posterNouvelleAssociationFilmSalle(@PathParam("filmId") String filmId, @PathParam("salleId") Integer salleId,
                                                     @QueryParam("dateDebut") Long timestampDebutAssociation,
                                                     @QueryParam("dateFin") Long timestampFinAssociation) throws Exception {

        Date dateDebut = (timestampDebutAssociation == null) ? new Date() : new Date(timestampDebutAssociation);
        Date dateFin;

        if (timestampFinAssociation == null) {
            final Calendar calendarAujourdhui = Calendar.getInstance();
            calendarAujourdhui.add(Calendar.MONTH, 1);
            dateFin = calendarAujourdhui.getTime();

        } else {
            dateFin = new Date(timestampFinAssociation);
        }

        // Vérifie que la date de fin est bien après la date de début
        if (dateDebut.compareTo(dateFin) > 0) {
            return Response.status(new Response.StatusType() {
                @Override
                public int getStatusCode() {
                    return Response.Status.BAD_REQUEST.getStatusCode();
                }

                @Override
                public Response.Status.Family getFamily() {
                    return Response.Status.BAD_REQUEST.getFamily();
                }

                @Override
                public String getReasonPhrase() {
                    return "La date de fin doit être après la date de début";
                }
            }).build();
        }

        AssociationFilmSalle associationFilmSalle = new AssociationFilmSalle(filmId, salleId, dateDebut, dateFin);

        try {
            UGmontBackendService.getInstance().posterNouvelleAssociationFilmSalles(associationFilmSalle);

        } catch (SalleInconnueException sie) {
            return Response.status(new Response.StatusType() {
                @Override
                public int getStatusCode() {
                    return Response.Status.NOT_FOUND.getStatusCode();
                }

                @Override
                public Response.Status.Family getFamily() {
                    return Response.Status.NOT_FOUND.getFamily();
                }

                @Override
                public String getReasonPhrase() {
                    return "Cet identifiant de salle n'est associé à aucune salle";
                }
            }).build();
        }

        return Response.ok().build();
    }


}
