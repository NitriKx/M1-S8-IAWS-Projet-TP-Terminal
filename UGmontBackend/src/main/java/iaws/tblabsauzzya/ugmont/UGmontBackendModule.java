package iaws.tblabsauzzya.ugmont;

import com.google.inject.AbstractModule;
import iaws.tblabsauzzya.ugmont.config.ConfigurationHolder;
import iaws.tblabsauzzya.ugmont.config.ConfigurationProviderImpl;
import iaws.tblabsauzzya.ugmont.config.IConfigurationProvider;
import iaws.tblabsauzzya.ugmont.service.UGmontBackendService;
import iaws.tblabsauzzya.ugmont.service.database.IUGmontDatabaseClient;
import iaws.tblabsauzzya.ugmont.service.database.UGmontH2DatabaseClient;

import javax.security.auth.login.Configuration;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class UGmontBackendModule  extends AbstractModule {


    @Override
    protected void configure() {

        // Demande l'injection statique des services singletons
        // Note: Cette méthode est dépréciée selon le directives de Google Guice car trop peu
        //       testable et dépendant trop du contexte.
        requestStaticInjection(UGmontBackendService.class);
        requestStaticInjection(ConfigurationHolder.class);

        bind(IUGmontDatabaseClient.class).to(UGmontH2DatabaseClient.class);

        bind(IConfigurationProvider.class).to(ConfigurationProviderImpl.class);

    }
}
