package iaws.tblabsauzzya.ugmont.config;

import com.google.inject.Inject;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Created by Benoît Sauvère on 25/03/15.
 */
public class ConfigurationHolder {


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

    @Inject private static ConfigurationHolder __instance;

    @Inject
    private ConfigurationHolder(IConfigurationProvider configurationProvider) {

        // Charge la configuration depuis le fichier
        try {
            config = configurationProvider.getConfiguration();

        } catch (ConfigurationException e) {
            throw new RuntimeException("Impossible de charger le fichier de configuration", e);
        }
    }

    public static ConfigurationHolder getInstance() {
        return __instance;
    }

}
