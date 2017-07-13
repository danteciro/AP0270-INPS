/**
* Resumen		
* Objeto			: LevantamientoException.java
* Descripción		: Clase Exception LevantamientoException
* Fecha de Creación	: 31/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez          Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 
package gob.osinergmin.inpsweb.service.exception;

public class LevantamientoException extends BaseException {
	private static final long serialVersionUID = 1L;

	public LevantamientoException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public LevantamientoException(String message, Exception exception) {
            super(message, exception);
    }
}
