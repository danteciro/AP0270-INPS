package gob.osinergmin.inpsweb.service.exception;

public class UnidadSupervisadaException extends BaseException{

	public UnidadSupervisadaException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public UnidadSupervisadaException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public UnidadSupervisadaException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public UnidadSupervisadaException(String codigo, String message, Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public UnidadSupervisadaException(String codigo) {
		super(codigo);
	}
}
