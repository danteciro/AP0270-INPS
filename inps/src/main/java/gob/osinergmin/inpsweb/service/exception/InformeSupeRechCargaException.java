package gob.osinergmin.inpsweb.service.exception;

public class InformeSupeRechCargaException extends BaseException{
	    public InformeSupeRechCargaException(Exception exception) {
	            super(exception);
	    }

	    /**
	     * @param message
	     * @param exception
	     */
	    public InformeSupeRechCargaException(String message, Exception exception) {
	            super(message, exception);
	    }

	    /**
	     * @param message
	     * @param exception
	     */
	    public InformeSupeRechCargaException(String codigo, Exception exception, boolean buscarCodigo) {		
	            super(codigo, exception, buscarCodigo);
	    }

	    /**
	     * @param codigo
	     * @param message
	     * @param exception
	     */
	    public InformeSupeRechCargaException(String codigo, String message, Exception exception) {		
	            super(codigo, message, exception);		
	    }

	    /**
	     * @param codigo
	     */
	    public InformeSupeRechCargaException(String codigo) {
	            super(codigo);	
	    }
	

}
