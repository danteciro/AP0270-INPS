package gob.osinergmin.inpsweb.service.exception;

public class PersonaGeneralException extends BaseException {

	public PersonaGeneralException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public PersonaGeneralException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public PersonaGeneralException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public PersonaGeneralException(String codigo, String message,
			Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public PersonaGeneralException(String codigo) {
		super(codigo);
	}
}
