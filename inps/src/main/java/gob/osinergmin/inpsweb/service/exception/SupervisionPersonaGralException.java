package gob.osinergmin.inpsweb.service.exception;

public class SupervisionPersonaGralException extends BaseException{

	public SupervisionPersonaGralException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public SupervisionPersonaGralException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public SupervisionPersonaGralException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public SupervisionPersonaGralException(String codigo, String message,
			Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public SupervisionPersonaGralException(String codigo) {
		super(codigo);
	}
}
