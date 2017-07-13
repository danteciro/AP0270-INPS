/**
* Resumen
* Objeto			: SupeCampRechCargaException.java
* Descripción		: Objeto que maneja las excepciones sobre SupeCampRechCargaException.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernán Torres Sáenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.exception;

public class SupeCampRechCargaException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SupeCampRechCargaException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public SupeCampRechCargaException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public SupeCampRechCargaException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public SupeCampRechCargaException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public SupeCampRechCargaException(String codigo) {
            super(codigo);	
    }
}
