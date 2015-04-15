package iaws.tblabsauzzya.ugmont.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class ConfigurationHolder {

    private static final Logger _log = LoggerFactory.getLogger(ConfigurationHolder.class);

    //
    //  ATTRIBUTES
    //

    private Configuration config;

    /**
     *
     * @param propertyName
     * @return
     */
    public String get(String propertyName) {
        if (config.containsKey(propertyName)) {
            return config.getString(propertyName);
        } else {
            throw new RuntimeException(String.format("La propriété [%s] n'existe pas dans la configuration", propertyName));
        }
    }


    //
    //   SINGLETON
    //

    private static ConfigurationHolder __instance = new ConfigurationHolder();

    private ConfigurationHolder() {

        _log.info("Chargement de la configuration...");

        // Charge la configuration depuis le fichier configuration.xml
        try {
            config = new ConfigurationProviderImpl().getConfiguration();

        } catch (ConfigurationException e) {
            throw new RuntimeException("Impossible de charger le fichier de configuration", e);
        }
    }

    public static ConfigurationHolder getInstance() {
        return __instance;
    }

}
