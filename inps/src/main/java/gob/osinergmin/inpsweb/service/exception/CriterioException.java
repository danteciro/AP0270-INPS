package gob.osinergmin.inpsweb.service.exception;

public class CriterioException extends BaseException {

	public CriterioException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public CriterioException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public CriterioException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public CriterioException(String codigo, String message, Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public CriterioException(String codigo) {
		super(codigo);
	}
}
