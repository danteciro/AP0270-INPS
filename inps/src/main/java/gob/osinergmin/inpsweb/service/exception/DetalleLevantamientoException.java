/**
* Resumen		
* Objeto			: DetalleLevantamientoException.java
* Descripción		: Clase Exception DetalleLevantamientoException
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: mdiosesf
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     07/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*/ 
package gob.osinergmin.inpsweb.service.exception;

public class DetalleLevantamientoException extends BaseException {
	private static final long serialVersionUID = 1L;

	public DetalleLevantamientoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public DetalleLevantamientoException(String message, Exception exception) {
            super(message, exception);
    }
}
