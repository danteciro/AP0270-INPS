/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author zchaupis
 */
public class RegistroHidrocarburoException extends BaseException{
    public RegistroHidrocarburoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public RegistroHidrocarburoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public RegistroHidrocarburoException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public RegistroHidrocarburoException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public RegistroHidrocarburoException(String codigo) {
            super(codigo);	
    }
}
