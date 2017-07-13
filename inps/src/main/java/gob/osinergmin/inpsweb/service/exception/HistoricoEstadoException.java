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
public class HistoricoEstadoException extends BaseException {
    public HistoricoEstadoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public HistoricoEstadoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public HistoricoEstadoException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public HistoricoEstadoException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public HistoricoEstadoException(String codigo) {
            super(codigo);	
    }
}
