package iaws.tblabsauzzya.ugmont.service;

import iaws.tblabsauzzya.ugmont.model.Salle;
import iaws.tblabsauzzya.ugmont.model.Theatre;
import iaws.tblabsauzzya.ugmont.service.database.IUGmontDatabaseClient;
import iaws.tblabsauzzya.ugmont.service.database.UGmontH2DatabaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendService {

    private static final Logger _log = LoggerFactory.getLogger(UGmontBackendService.class);

    //
    //    ATTRIBUTES
    //

    private IUGmontDatabaseClient databaseClient = new UGmontH2DatabaseClient();


    //
    //    PUBLIC
    //

    public Set<Salle> getListSalles() throws SQLException {

        return databaseClient.getSalles();
    }

    public List<Theatre> getListTheatres(int id) {
        return new ArrayList<Theatre>();
    }


    //
    //    SINGLETON
    //

    private static UGmontBackendService __instance = new UGmontBackendService();

    private UGmontBackendService() {
        try {
            this.databaseClient.resetDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Impossible de remettre à zéro la base de données", e);
        }
    }

    public static UGmontBackendService getInstance() {
        return __instance;
    }

}
