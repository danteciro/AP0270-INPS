/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author zchaupis
 */
public class RegistroSuspenHabiliException extends BaseException {
    public RegistroSuspenHabiliException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public RegistroSuspenHabiliException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public RegistroSuspenHabiliException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public RegistroSuspenHabiliException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public RegistroSuspenHabiliException(String codigo) {
            super(codigo);	
    }
}
