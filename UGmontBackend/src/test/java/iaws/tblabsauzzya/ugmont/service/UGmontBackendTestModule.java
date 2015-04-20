package iaws.tblabsauzzya.ugmont.service;

import iaws.tblabsauzzya.ugmont.model.AssociationFilmSalle;
import iaws.tblabsauzzya.ugmont.model.Salle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Set;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendTestModule {

    @Before
    public void beforeEachTest() {
        UGmontBackendService.getInstance().resetDatabase();
    }

    @Test
    public void testCreationBaseDeDonnees() throws Exception {
        final Set<Salle> listeDesSallesALaCreation = UGmontBackendService.getInstance().getListSalles();
        Assert.assertEquals(4, listeDesSallesALaCreation.size());
        Assert.assertEquals(3, UGmontBackendService.getInstance().getAssociationFilmSalle().size());

        // Vérifie que le getSalle fonctionne bien
        for (Salle s : listeDesSallesALaCreation) {
            final Salle r = UGmontBackendService.getInstance().getSalle(s.idSalle);
            Assert.assertEquals(s, r);
        }

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

    @Test
    public void testCreationBaseDeDonneesPosterAssociationFilmSalle() throws Exception {

        Date dateDebut = new Date();
        Date dateFin = new Date(new Date().getTime() + 24*60*60*1000*5);

        // RRrr !
        AssociationFilmSalle nouvelleAssociation = new AssociationFilmSalle("tt0357111", 1, dateDebut, dateFin);
        UGmontBackendService.getInstance().posterNouvelleAssociationFilmSalles(nouvelleAssociation);

        Assert.assertEquals(1, UGmontBackendService.getInstance().rechercheSalleAssocieeFilm("tt0357111").size());
    }



}
