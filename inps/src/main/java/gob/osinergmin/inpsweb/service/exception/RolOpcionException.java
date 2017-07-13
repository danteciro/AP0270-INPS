package gob.osinergmin.inpsweb.service.exception;

public class RolOpcionException extends BaseException {

	public RolOpcionException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public RolOpcionException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public RolOpcionException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public RolOpcionException(String codigo, String message, Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public RolOpcionException(String codigo) {
		super(codigo);
	}
}
