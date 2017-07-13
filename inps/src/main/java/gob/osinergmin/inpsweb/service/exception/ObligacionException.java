package gob.osinergmin.inpsweb.service.exception;

public class ObligacionException extends BaseException {

	public ObligacionException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public ObligacionException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public ObligacionException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public ObligacionException(String codigo, String message,
			Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public ObligacionException(String codigo) {
		super(codigo);
	}
}
