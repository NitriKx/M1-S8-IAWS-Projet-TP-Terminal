package iaws.tblabsauzzya.ugmont.service;

import iaws.tblabsauzzya.ugmont.service.database.IUGmontDatabaseClient;
import iaws.tblabsauzzya.ugmont.service.database.UGmontH2DatabaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendService {

    private static final Logger _log = LoggerFactory.getLogger(UGmontBackendService.class);

    //
    //    ATTRIBUTES
    //

    private IUGmontDatabaseClient databaseClient = new UGmontH2DatabaseClient();


    public String getIt() {
        return "ok";
    }



    //
    //    SINGLETON
    //

    private static UGmontBackendService __instance = new UGmontBackendService();

    private UGmontBackendService() {
        this.databaseClient = databaseClient;
    }

    public static UGmontBackendService getInstance() {
        return __instance;
    }

}
