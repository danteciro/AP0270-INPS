package gob.osinergmin.inpsweb.service.exception;

public class TipificacionSancionException extends BaseException{

	public TipificacionSancionException(Exception exception) {
		super(exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public TipificacionSancionException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * @param message
	 * @param exception
	 */
	public TipificacionSancionException(String codigo, Exception exception,
			boolean buscarCodigo) {
		super(codigo, exception, buscarCodigo);
	}

	/**
	 * @param codigo
	 * @param message
	 * @param exception
	 */
	public TipificacionSancionException(String codigo, String message, Exception exception) {
		super(codigo, message, exception);
	}

	/**
	 * @param codigo
	 */
	public TipificacionSancionException(String codigo) {
		super(codigo);
	}
}
