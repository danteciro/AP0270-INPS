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
public class DatoPlantillaException extends BaseException {
    public DatoPlantillaException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DatoPlantillaException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DatoPlantillaException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public DatoPlantillaException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public DatoPlantillaException(String codigo) {
            super(codigo);	
    }
}
