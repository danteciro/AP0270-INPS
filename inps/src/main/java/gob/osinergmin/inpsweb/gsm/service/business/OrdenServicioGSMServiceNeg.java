/**
* Resumen
* Objeto			: OrdenServicioGSMServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos 
* 					  a implementarse en el OrdenServicioGSMServiceNegImpl, gerencia GSM.
* Fecha de Creación	: 27/110/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernán Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.gsm.service.business;

import gob.osinergmin.mdicommon.domain.dto.DmTitularMineroDTO;
import gob.osinergmin.mdicommon.domain.ui.DmTitularMineroFilter;
import java.util.List;

public interface OrdenServicioGSMServiceNeg {
    
    public List<DmTitularMineroDTO> listarTitularMinero(DmTitularMineroFilter filtro);
    
}
