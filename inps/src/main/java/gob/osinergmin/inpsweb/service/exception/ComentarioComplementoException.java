package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author jpiro
 */
public class ComentarioComplementoException extends BaseException {
    public ComentarioComplementoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComentarioComplementoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComentarioComplementoException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public ComentarioComplementoException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public ComentarioComplementoException(String codigo) {
            super(codigo);	
    }
}
