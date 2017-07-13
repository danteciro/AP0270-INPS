package gob.osinergmin.inpsweb.service.exception;

public class ConfObligacionException extends BaseException {

	public ConfObligacionException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public ConfObligacionException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public ConfObligacionException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public ConfObligacionException(String codigo, String message,
			Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public ConfObligacionException(String codigo) {
		super(codigo);
	}
}
