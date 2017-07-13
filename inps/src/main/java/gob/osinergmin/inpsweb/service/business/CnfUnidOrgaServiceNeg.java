/**
* Resumen
* Objeto			: CnfUnidOrgaServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de 
* 					  métodos a implementarse en el CnfUnidOrgaServiceNegImpl.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernán Torres.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.inpsweb.service.exception.CnfUnidOrgaException;
import gob.osinergmin.mdicommon.domain.dto.CnfUnidOrgaDTO;
import gob.osinergmin.mdicommon.domain.ui.CnfUnidOrgaFilter;

import java.util.List;

public interface CnfUnidOrgaServiceNeg {

	public List<CnfUnidOrgaDTO> listarCnfUnidOrga(CnfUnidOrgaFilter filtro) throws CnfUnidOrgaException ;

}
