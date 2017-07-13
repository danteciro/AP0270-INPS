package gob.osinergmin.inpsweb.service.exception;

public class EmpresaSupervisionViewException extends BaseException {

	public EmpresaSupervisionViewException(Exception exception) {
		super(exception);
	}

	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public EmpresaSupervisionViewException(String message, Exception exception) {
		super(message, exception);
	}
	
	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public EmpresaSupervisionViewException(String codigo, Exception exception, boolean buscarCodigo) {		
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * 
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public EmpresaSupervisionViewException(String codigo, String message, Exception exception) {		
		super(codigo, message, exception);		
	}

	/**
	 * 
	 * @param codigo
	 */
	public EmpresaSupervisionViewException(String codigo) {
		super(codigo);	
	}
}
