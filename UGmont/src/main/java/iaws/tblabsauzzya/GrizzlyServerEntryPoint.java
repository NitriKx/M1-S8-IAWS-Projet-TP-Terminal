package iaws.tblabsauzzya;

import com.sun.jersey.api.json.JSONConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * GrizzlyServerEntryPoint class.
 *
 */
public class GrizzlyServerEntryPoint {

    //
    //    CONFIGURATION / LOGGING
    //

    // URI de base sur laquelle Grizzly va écouter
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    private static final Logger _log = LoggerFactory.getLogger(GrizzlyServerEntryPoint.class);


    //
    //    INSTANCES STATIQUES
    //

    private static HttpServer grizzlyServer;



    //
    //    ENTRY POINT
    //

    /**
     * GrizzlyServerEntryPoint method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // Le loggueur par défault de Jersey est JUL (java.util.logging). Il faut donc rediriger ses logs à travers
        // SLF4J en utilisant le "jul-to-slf4j bridge"
        configureJerseyToLogThroughSLF4J();

        grizzlyServer = startServer();

        // On ferme proprement le serveur si le processus reçoit un signal d'extinction
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {

                stopServer();
            }
        }));

        _log.info("Hit enter to stop the server...");
        System.in.read();
        stopServer();
    }



    //
    //   OPERATIONS SERVEUR
    //

    /**
     * Démarre le serveur Grizzly HTTP server qui expose les resource JAX-RS définie dans l'application
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {

        _log.info("Starting Grizzly server with BASE_URI=[{}]...", BASE_URI);

        final ResourceConfig rc = new ResourceConfig()
                // Indique à Grizzly le package qu'il doit scanner pour trouver les classes
                .packages("iaws.tblabsauzzya");

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        _log.info("Jersey app started with WADL available at {}application.wadl", BASE_URI);

        return httpServer;
    }

    /**
     * Arrête le serveur grizzly
     * @return Grizzly HTTP server.
     */
    public static void stopServer() {

        _log.info("Stopping Grizzly server...");

        grizzlyServer.shutdown();

        _log.info("Grizzly server stopped");

    }



    //
    //   PRIVATE
    //


    private static void configureJerseyToLogThroughSLF4J() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);
    }


}

