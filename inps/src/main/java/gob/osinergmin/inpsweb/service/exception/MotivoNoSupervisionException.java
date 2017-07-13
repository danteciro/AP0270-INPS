package gob.osinergmin.inpsweb.service.exception;

public class MotivoNoSupervisionException extends BaseException {
	
	public MotivoNoSupervisionException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public MotivoNoSupervisionException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public MotivoNoSupervisionException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public MotivoNoSupervisionException(String codigo, String message,
			Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public MotivoNoSupervisionException(String codigo) {
		super(codigo);
	}

}
