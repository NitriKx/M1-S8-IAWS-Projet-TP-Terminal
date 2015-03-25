package iaws.tblabsauzzya.ugmont.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Created by benoit on 25/03/15.
 */
public interface IConfigurationProvider {

    /**
     * Charge le configuration a utilisé partout dans l'application.
     * @return
     */
    public Configuration getConfiguration() throws ConfigurationException;

}
