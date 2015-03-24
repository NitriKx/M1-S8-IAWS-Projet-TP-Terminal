package iaws.tblabsauzzya.ugmont.service;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendService {

    //
    //    ATTRIBUTES
    //








    //
    //    SINGLETON
    //

    private static final UGmontBackendService __instance = new UGmontBackendService();

    private UGmontBackendService() {}

    public static UGmontBackendService getInstance() {
        return __instance;
    }

}
