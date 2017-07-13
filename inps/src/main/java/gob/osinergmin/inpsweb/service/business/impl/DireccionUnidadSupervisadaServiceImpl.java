/**
* Resumen
* Objeto		: DireccionUnidadSupervisadaServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz DireccionUnidadSupervisadaServiceNeg
* Fecha de Creación	: 05/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Mario Dioses Fernandez
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     05/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD).
* 
*/

package gob.osinergmin.inpsweb.service.business.impl;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gob.osinergmin.inpsweb.service.business.DireccionUnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.DireccionUnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.service.exception.DireccionUnidadSupervisadaException;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.DireccionUnidadSupervisadaFilter;

@Service("DireccionUnidadSupervisadaService")
public class DireccionUnidadSupervisadaServiceImpl implements DireccionUnidadSupervisadaServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(DireccionUnidadSupervisadaServiceImpl.class);
    @Inject
    private DireccionUnidadSupervisadaDAO direccionUnidadSupervisadaDAO;
	
    @Override
    public List<DireccionUnidadSupervisadaDTO> buscarDireccionesUnidad(DireccionUnidadSupervisadaFilter filtro) throws DireccionUnidadSupervisadaException {
        LOG.info("Neg burcarDireccionesUnidad");
        List<DireccionUnidadSupervisadaDTO> retorno=null;
        try{
            retorno = direccionUnidadSupervisadaDAO.findbuscarDireccionesUnidad(filtro);
        }catch(Exception ex){
            LOG.error("error buscarDireccionesUnidad ",ex);
            throw new DireccionUnidadSupervisadaException(ex.getMessage(),null);
        }
        return retorno;
    }
    
    @Override
    public DireccionUnidadSupervisadaDTO findById(Long idDirccionUnidadSuprvisada) throws DireccionUnidadSupervisadaException {
        LOG.info("Neg findById");
        DireccionUnidadSupervisadaDTO retorno=null;
        try{
            retorno = direccionUnidadSupervisadaDAO.findById(idDirccionUnidadSuprvisada);
        }catch(Exception ex){
            LOG.error("error buscarDireccionesUnidad ",ex);
            throw new DireccionUnidadSupervisadaException(ex.getMessage(),null);
        }
        return retorno;
    }
}