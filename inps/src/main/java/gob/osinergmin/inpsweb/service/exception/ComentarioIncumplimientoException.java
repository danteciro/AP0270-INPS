package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author jpiro
 */
public class ComentarioIncumplimientoException extends BaseException {
    public ComentarioIncumplimientoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComentarioIncumplimientoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ComentarioIncumplimientoException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public ComentarioIncumplimientoException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public ComentarioIncumplimientoException(String codigo) {
            super(codigo);	
    }
}
