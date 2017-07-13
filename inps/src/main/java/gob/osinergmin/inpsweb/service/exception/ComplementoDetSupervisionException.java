package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author jpiro
 */
public class ComplementoDetSupervisionException extends BaseException {
    public ComplementoDetSupervisionException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComplementoDetSupervisionException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComplementoDetSupervisionException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public ComplementoDetSupervisionException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public ComplementoDetSupervisionException(String codigo) {
            super(codigo);	
    }
}
