package iaws.tblabsauzzya.ugmont.service;

import iaws.tblabsauzzya.ugmont.model.Salle;
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

    @Test
    public void testCreationBaseDeDonneesRechercheAssociation() throws Exception {
        Assert.assertEquals(1, UGmontBackendService.getInstance().rechercheSalleAssocieeFilm("tt1040019").size());
    }

    @Test
    public void testCreationBaseDeDonneesRechercheSalleCritere() throws Exception {
        Assert.assertEquals(2, UGmontBackendService.getInstance().rechercheSalleAvecCritere(new Salle(null, 100, null, null)).size());
        Assert.assertEquals(2, UGmontBackendService.getInstance().rechercheSalleAvecCritere(new Salle(null, null, true, null)).size());
    }

}
