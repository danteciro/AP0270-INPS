/**
* Resumen
* Objeto			: CnfUnidOrgaException.java
* Descripción		: Objeto que maneja las excepciones sobre CnfUnidOrgaException.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernán Torres Sáenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.exception;

public class CnfUnidOrgaException extends BaseException {
    
	public CnfUnidOrgaException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public CnfUnidOrgaException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public CnfUnidOrgaException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public CnfUnidOrgaException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public CnfUnidOrgaException(String codigo) {
            super(codigo);	
    }
}
