/**
*
* Resumen		
* Objeto		: EscenarioIncumplidoException.java
* Descripción		: EscenarioIncumplidoException
* Fecha de Creación	: 05/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  05/09/2016   |   Luis García Reyna          |     Listar Escenarios Incumplidos de la Supervision.
*                |               |                              |
*                |               |                              |
* 
*/


package gob.osinergmin.inpsweb.service.exception;

/**
 *
 * @author Ubuntu
 */
public class EscenarioIncumplidoException extends BaseException{
    public EscenarioIncumplidoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public EscenarioIncumplidoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public EscenarioIncumplidoException(String codigo, Exception exception, boolean buscarCodigo) {		
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public EscenarioIncumplidoException(String codigo, String message, Exception exception) {		
            super(codigo, message, exception);		
    }

    /**
     * @param codigo
     */
    public EscenarioIncumplidoException(String codigo) {
            super(codigo);	
    }
}
