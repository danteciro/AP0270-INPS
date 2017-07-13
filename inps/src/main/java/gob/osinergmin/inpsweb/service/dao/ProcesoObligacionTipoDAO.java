/**
* Resumen		
* Objeto		: ProcesoObligacionTipoDAO.java
* Descripción		: ProcesoObligacionTipoDAO
* Fecha de Creación	: 
* PR de Creación	: 
* Autor			: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE791          17/08/2016      Yadira Piskulich                Abrir Supervision DSR
* 
*/ 

package gob.osinergmin.inpsweb.service.dao;

public interface ProcesoObligacionTipoDAO {
    public Integer buscarFlagSupervision(Long idObligacionTipo,Long idActividad,Long idProceso);
    public String buscarCodigoFlujoSupervINPS(Long idObligacionTipo,Long idActividad,Long idProceso);
}
