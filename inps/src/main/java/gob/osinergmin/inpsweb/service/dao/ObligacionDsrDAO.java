/**
 * Resumen 
 * Objeto				: ObligacionDsrDAO.java 
 * Descripción	        : DAO de las Obligaciones DSR. 
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0025  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Agrega funcion de verificacion de obstaculizados
 */
package gob.osinergmin.inpsweb.service.dao;

/* OSINE_MAN_DSR_0025 - Inicio */
import gob.osinergmin.inpsweb.dto.ObligacionDsrUpdtDTO;
/* OSINE_MAN_DSR_0025 - Fin */
import gob.osinergmin.mdicommon.domain.dto.ObligacionDsrDTO;
import gob.osinergmin.mdicommon.domain.dto.ProductoDsrScopDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionDsrFilter;

import java.util.List;

public interface ObligacionDsrDAO {
	
    public List<ObligacionDsrDTO> find(ObligacionDsrFilter filtro); 
    /* OSINE_MAN_DSR_0025 - Inicio */
    public List<ObligacionDsrUpdtDTO> findUpdt(ObligacionDsrFilter filtro);     
    /* OSINE_MAN_DSR_0025 - Fin */
    public List<ProductoDsrScopDTO> findPdtos(ObligacionDsrFilter filtro);
    
    public ObligacionDsrDTO findByPrioridad(Long idSupervision, Long prioridad);
    
    public ObligacionDsrDTO guardarDsrObligacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto);
    
    public ProductoDsrScopDTO guardarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto);
    
    public ProductoDsrScopDTO eliminarDsrProductoSuspender(ProductoDsrScopDTO productoDsrScopDTO, UsuarioDTO usuarioDto);
    
    /* OSINE791 – RSIS12 - Inicio */
    public String VerificarObstaculizados(ObligacionDsrDTO filtro);
    /* OSINE791 – RSIS12 - Fin */
    public ObligacionDsrDTO editarDsrComentarioSubsanacion(ObligacionDsrDTO obligacionDstDTO, UsuarioDTO usuarioDto);
}
