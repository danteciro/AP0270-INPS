/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;
/**
 *
 * @author mdiosesf
 */
public class LocadorException extends BaseException{   
        private static final long serialVersionUID = 7916000439260127933L;
	
	public LocadorException(Exception exception) {
		super(exception);
	}

	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public LocadorException(String message, Exception exception) {
		super(message, exception);
	}
	
	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public LocadorException(String codigo, Exception exception, boolean buscarCodigo) {		
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * 
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public LocadorException(String codigo, String message, Exception exception) {		
		super(codigo, message, exception);		
	}

	/**
	 * 
	 * @param codigo
	 */
	public LocadorException(String codigo) {
		super(codigo);	
	}
}
