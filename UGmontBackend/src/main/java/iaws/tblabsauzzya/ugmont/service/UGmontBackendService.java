package iaws.tblabsauzzya.ugmont.service;

import com.google.inject.Guice;
import com.google.inject.Inject;
import iaws.tblabsauzzya.ugmont.UGmontBackendModule;
import iaws.tblabsauzzya.ugmont.service.database.IUGmontDatabaseClient;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendService {

    //
    //    ATTRIBUTES
    //

    private IUGmontDatabaseClient databaseClient;


    public String getIt() {
        return "ok";
    }



    //
    //    SINGLETON
    //

    @Inject private static UGmontBackendService __instance;

    // Le paramètre Inject permet de définir à l'éxécution quelle implémentation de IUGmontDatabaseClient on souhaite
    // utiliser. C'est particulièrement pratique lorsqu'on souhaite tester le fonctionnement du service.
    @Inject
    private UGmontBackendService(IUGmontDatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public static UGmontBackendService getInstance() {
        return __instance;
    }

}
