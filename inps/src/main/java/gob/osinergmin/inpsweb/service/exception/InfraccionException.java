/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author zchaupis
 */
public class InfraccionException extends BaseException {

    public InfraccionException(Exception exception) {
        super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public InfraccionException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public InfraccionException(String codigo, Exception exception, boolean buscarCodigo) {
        super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public InfraccionException(String codigo, String message, Exception exception) {
        super(codigo, message, exception);
    }

    /**
     * @param codigo
     */
    public InfraccionException(String codigo) {
        super(codigo);
    }
}
