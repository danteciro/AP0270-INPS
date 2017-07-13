/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author zchaupis
 */
public class PeriodoMedidaSeguridadException extends BaseException{
    
	public PeriodoMedidaSeguridadException(Exception exception) {
	        super(exception);
	}
	
	/**
	 * @param message
	 * @param exception
	 */
	public PeriodoMedidaSeguridadException(String message, Exception exception) {
	        super(message, exception);
	}
	
	/**
	 * @param message
	 * @param exception
	 */
	public PeriodoMedidaSeguridadException(String codigo, Exception exception, boolean buscarCodigo) {		
	        super(codigo, exception, buscarCodigo);
	}
	
	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public PeriodoMedidaSeguridadException(String codigo, String message, Exception exception) {		
	        super(codigo, message, exception);		
	}
	
	/**
	 * @param codigo
	 */
	public PeriodoMedidaSeguridadException(String codigo) {
	        super(codigo);	
	}
}
