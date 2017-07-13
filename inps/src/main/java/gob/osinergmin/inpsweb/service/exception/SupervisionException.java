package gob.osinergmin.inpsweb.service.exception;

public class SupervisionException extends BaseException{

	public SupervisionException(Exception exception) {
	        super(exception);
	}
	
	/**
	 * @param message
	 * @param exception
	 */
	public SupervisionException(String message, Exception exception) {
	        super(message, exception);
	}
	
	/**
	 * @param message
	 * @param exception
	 */
	public SupervisionException(String codigo, Exception exception, boolean buscarCodigo) {		
	        super(codigo, exception, buscarCodigo);
	}
	
	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public SupervisionException(String codigo, String message, Exception exception) {		
	        super(codigo, message, exception);		
	}
	
	/**
	 * @param codigo
	 */
	public SupervisionException(String codigo) {
	        super(codigo);	
	}

}
