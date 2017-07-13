/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author jpiro
 */
public class ComentarioDetSupervisionException extends BaseException {
    public ComentarioDetSupervisionException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComentarioDetSupervisionException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComentarioDetSupervisionException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public ComentarioDetSupervisionException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public ComentarioDetSupervisionException(String codigo) {
            super(codigo);	
    }
}
