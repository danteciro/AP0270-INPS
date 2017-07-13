/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jpiro
 */
public class PropertyUtils {
    private static final Properties properties;
    private static final String INPS_PROPERTIES = "messages.properties";
    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtils.class);

    static {
            properties = new Properties();
            try {
                    InputStream stream = Thread.currentThread().getContextClassLoader()
                                    .getResourceAsStream(INPS_PROPERTIES);
                    properties.load(stream);
            } catch (IOException ie) {
                    LOG.error("No se pudo inicializar el PropertyUtils", ie);
            } catch (Exception ex) {
                    LOG.error("No se pudo inicializar el PropertyUtils", ex);
            }
    }

    private PropertyUtils() {
    }

    public static String getProperty(String name) {
            return (properties == null) ? null : properties.getProperty(name);
    }
}
