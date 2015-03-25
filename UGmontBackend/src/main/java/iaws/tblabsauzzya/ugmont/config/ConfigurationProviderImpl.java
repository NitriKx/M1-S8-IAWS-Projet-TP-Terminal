package iaws.tblabsauzzya.ugmont.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Created by benoit on 25/03/15.
 */
public class ConfigurationProviderImpl implements IConfigurationProvider {


    public Configuration getConfiguration() throws ConfigurationException {
        return new XMLConfiguration("configuration.xml");
    }
}
