/**
* Resumen		
* Objeto		: ObligacionSubTipoDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz ObligacionSubTipoDAO
* Fecha de Creación	: 23/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     23/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.PghObligacionSubTipo;
import gob.osinergmin.inpsweb.dto.builder.ObligacionSubTipoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ObligacionSubTipoDAO;
import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionSubTipoFilter;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/* OSINE_SFS-480 - RSIS 26 - Inicio */
@Service("ObligacionSubTipoDAO")
public class ObligacionSubTipoDAOImpl implements  ObligacionSubTipoDAO{

    private static final Logger LOG = LoggerFactory.getLogger(ObligacionSubTipoDAOImpl.class);
    @Inject
    private CrudDAO crud;
	@Override
	public List<ObligacionSubTipoDTO> listaObligacionSubTipo(ObligacionSubTipoFilter filtro) {
            LOG.info("listarObligacionesSubTipo");
            List<ObligacionSubTipoDTO> listado=null;
            try{
                Query query = crud.getEm().createNamedQuery("PghObligacionSubTipo.findByParameters");
                LOG.info("filtro.getIdObligacionTipo()--------------->"+filtro.getIdObligacionTipo());
                LOG.info("filtro.getIdentificadorSeleccion()--------------->"+filtro.getIdentificadorSeleccion());
                LOG.info("filtro.getEstado()--------------->"+filtro.getEstado());
                query.setParameter("idObligacionTipo", filtro.getIdObligacionTipo());
                query.setParameter("identificadorMuestral",filtro.getIdentificadorSeleccion());
                query.setParameter("estado", filtro.getEstado());
                List<PghObligacionSubTipo> lista=query.getResultList();
                LOG.info("listaObligacionSubTipoBD--------->"+lista);
                LOG.info("listaObligacionSubTipoBD--------->"+lista.size());
                listado = ObligacionSubTipoBuilder.toListObligacionSubTipoDto(lista);        
                LOG.info("listaObligacionSubTipo--------->"+listado);
                LOG.info("listaObligacionSubTipo--------->"+listado.size());
            }catch(Exception e){
                LOG.error("Error en listaObligacionSubTipo",e);
            }
            return listado;    
	}    
}
/* OSINE_SFS-480 - RSIS 26 - Inicio */
