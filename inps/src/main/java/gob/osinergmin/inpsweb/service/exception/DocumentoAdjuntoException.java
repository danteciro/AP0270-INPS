package gob.osinergmin.inpsweb.service.exception;

public class DocumentoAdjuntoException extends BaseException {

	public DocumentoAdjuntoException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public DocumentoAdjuntoException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public DocumentoAdjuntoException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public DocumentoAdjuntoException(String codigo, String message,
			Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public DocumentoAdjuntoException(String codigo) {
		super(codigo);
	}
}
