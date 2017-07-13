package gob.osinergmin.inpsweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author diego.gil
 */
public class StringUtil {

    private static Logger log = LoggerFactory.getLogger(StringUtil.class);
    public static final String REGEX_BLANK_SPACE = "\\s{2,}";
    public static final String REGEX_BLANK_SPACE_SIMPLE = " ";

    public static boolean isEmpty(String obj) {
        if ((obj == null) || (obj.trim().length() == 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isEmptyNum(Long obj) {
        if ((obj == null) || (obj.longValue() == 0)) {
            return true;
        } else {
            return false;
        }
    }
    

    public static String fixer(String cadena) {
        if (!isEmpty(cadena)) {
            cadena = (cadena.replaceAll(REGEX_BLANK_SPACE, " ")).trim();
        }
        return cadena;
    }

    public static String removeBlank(String cadena) {
        if (!isEmpty(cadena)) {
            cadena = (cadena.replaceAll(REGEX_BLANK_SPACE_SIMPLE, " ")).trim();
        }
        return cadena;
    }
    
    public static String fixerUpper(String cadena) {
        if (!isEmpty(cadena)) {
            cadena = (cadena.replaceAll(REGEX_BLANK_SPACE, " ")).trim().toUpperCase();
        }
        return cadena;
    }
}