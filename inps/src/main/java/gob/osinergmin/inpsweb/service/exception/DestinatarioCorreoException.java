/**
*
* Resumen		
* Objeto		: DetalleSupervisionException.java
* Descripción		: DetalleSupervisionException
* Fecha de Creación	: 01/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  02/09/2016   |   Luis García Reyna          |     Ejecucion Medida - Listar Obligaciones
*                |               |                              |
*                |               |                              |
* 
*/

package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author Ubuntu
 */
public class DestinatarioCorreoException extends BaseException {
    public DestinatarioCorreoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DestinatarioCorreoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DestinatarioCorreoException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public DestinatarioCorreoException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public DestinatarioCorreoException(String codigo) {
            super(codigo);	
    }
}
