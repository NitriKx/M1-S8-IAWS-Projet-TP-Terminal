package iaws.tblabsauzzya.ugmont.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendTestModule {


    @Test
    public void testCreationBaseDeDonnees() throws Exception {
        Assert.assertEquals(4, UGmontBackendService.getInstance().getListSalles().size());
        Assert.assertEquals(3, UGmontBackendService.getInstance().getAssociationFilmSalle().size());
    }



}
