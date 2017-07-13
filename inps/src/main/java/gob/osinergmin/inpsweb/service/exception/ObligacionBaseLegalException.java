/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author zchaupis
 */
public class ObligacionBaseLegalException extends BaseException {
    public ObligacionBaseLegalException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ObligacionBaseLegalException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ObligacionBaseLegalException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public ObligacionBaseLegalException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public ObligacionBaseLegalException(String codigo) {
            super(codigo);	
    }
}
