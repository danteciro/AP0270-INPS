/**
* Resumen		
* Objeto			: ExpedienteTareaException.java
* Descripción		: Clase DTO ExpedienteTareaException
* Fecha de Creación	: 23/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     23/10/2016      Mario Dioses Fernandez          Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/
package gob.osinergmin.inpsweb.service.exception;
public class ExpedienteTareaException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ExpedienteTareaException(Exception exception) {
            super(exception);
    }

    /**
     * @param message
     * @param exception
     */
    public ExpedienteTareaException(String message, Exception exception) {
            super(message, exception);
    }
}
