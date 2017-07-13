/**
* Resumen		
* Objeto			: CnfUnidOrgaDAO.java
* Descripción		: Clase que proporciona una interfaz para la implementación de métodos
* 					  en CnfUnidOrgaDAOImpl.
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1063
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/

package gob.osinergmin.inpsweb.service.dao;

import gob.osinergmin.mdicommon.domain.dto.CnfUnidOrgaDTO;
import gob.osinergmin.mdicommon.domain.ui.CnfUnidOrgaFilter;

import java.util.List;

public interface CnfUnidOrgaDAO {
	
	public List<CnfUnidOrgaDTO> listarCnfUnidOrga(CnfUnidOrgaFilter filtro);
        
}