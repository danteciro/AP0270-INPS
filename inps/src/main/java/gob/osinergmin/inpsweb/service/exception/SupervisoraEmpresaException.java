/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.service.exception;
/**
 *
 * @author mdiosesf
 */
public class SupervisoraEmpresaException extends BaseException{   
	private static final long serialVersionUID = 1L;

	public SupervisoraEmpresaException(Exception exception) {
		super(exception);
	}

	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public SupervisoraEmpresaException(String message, Exception exception) {
		super(message, exception);
	}
	
	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public SupervisoraEmpresaException(String codigo, Exception exception, boolean buscarCodigo) {		
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * 
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public SupervisoraEmpresaException(String codigo, String message, Exception exception) {		
		super(codigo, message, exception);		
	}

	/**
	 * 
	 * @param codigo
	 */
	public SupervisoraEmpresaException(String codigo) {
		super(codigo);	
	}
}
