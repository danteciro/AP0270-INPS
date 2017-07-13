/**
* Resumen		
* Objeto		: CorreoException.java
* Descripción		: Excepción de la clase Correo que indica la validación de una condición 
* Fecha de Creación	: 12/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                   Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     25/05/2016      Luis García Reyna        Correo de Alerta a Empresa Supervisora cuando se le asigne Orden de Servicio.
*
*/

package gob.osinergmin.inpsweb.service.exception;

/* OSINE_SFS-480 - RSIS 12 - Inicio */
public class CorreoException extends BaseException {

    public CorreoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public CorreoException(String message, Exception exception) {
            super(message, exception);
    }

    /**
     * @param message
     * @param exception
     */
    public CorreoException(String codigo, Exception exception,
                    boolean buscarCodigo) {
            super(codigo, exception, buscarCodigo);
    }

    /**
     * @param codigo
     * @param message
     * @param exception
     */
    public CorreoException(String codigo, String message, Exception exception) {
            super(codigo, message, exception);
    }

    /**
     * @param codigo
     */
    public CorreoException(String codigo) {
            super(codigo);
    }
}
/* OSINE_SFS-480 - RSIS 12 - Fin */