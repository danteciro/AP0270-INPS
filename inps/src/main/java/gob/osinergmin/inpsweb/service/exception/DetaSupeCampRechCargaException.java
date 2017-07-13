/**
* Resumen
* Objeto			: DetaSupeCampRechCargaException.java
* Descripción		: Objeto que maneja las excepciones sobre DetaSupeCampRechCarga.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernán Torres Sáenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.exception;

public class DetaSupeCampRechCargaException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DetaSupeCampRechCargaException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DetaSupeCampRechCargaException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DetaSupeCampRechCargaException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public DetaSupeCampRechCargaException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public DetaSupeCampRechCargaException(String codigo) {
            super(codigo);	
    }
}
