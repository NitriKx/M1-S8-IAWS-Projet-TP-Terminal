package iaws.tblabsauzzya.ugmont;

import org.glassfish.grizzly.utils.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Benoît Sauvère on 25/03/15.
 *
 * Cette classe permet de forcer le logging des exception survenues dans le serveur Grizzly.
 */
@Provider
public class GrizzlyServerExceptionLogger implements ExceptionMapper<Exception> {

    private static final Logger _log = LoggerFactory.getLogger(GrizzlyServerExceptionLogger.class);

    @Override
    public Response toResponse(Exception ex) {
        _log.error("Une erreur est survenue lors de l'exécution d'une requête", ex);
        return Response.status(500).build();
    }
}
