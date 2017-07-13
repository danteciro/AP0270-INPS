package gob.osinergmin.inpsweb.service.exception;

public class DetalleCriterioException extends BaseException{
	public DetalleCriterioException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public DetalleCriterioException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public DetalleCriterioException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public DetalleCriterioException(String codigo, String message, Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public DetalleCriterioException(String codigo) {
		super(codigo);
	}

}
