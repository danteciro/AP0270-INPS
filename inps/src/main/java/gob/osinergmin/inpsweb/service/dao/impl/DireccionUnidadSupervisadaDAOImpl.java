/**
* Resumen		
* Objeto		: DireccionUnidadSupervisadaDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz DireccionUnidadSupervisadaDAO
* Fecha de Creación	: 05/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Mario Dioses Fernandez
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;
import gob.osinergmin.inpsweb.domain.MdiDirccionUnidadSuprvisada;
import gob.osinergmin.inpsweb.domain.builder.DireccionUnidadSupervisadaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DireccionUnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.service.exception.DireccionUnidadSupervisadaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.DireccionUnidadSupervisadaFilter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class DireccionUnidadSupervisadaDAOImpl implements DireccionUnidadSupervisadaDAO {	
	private static final Logger LOG = LoggerFactory.getLogger(DireccionUnidadSupervisadaDAOImpl.class);
	@Inject
    private CrudDAO crud;	
	
	@Override
	public List<DireccionUnidadSupervisadaDTO> findbuscarDireccionesUnidad(DireccionUnidadSupervisadaFilter filtro) {
		List<DireccionUnidadSupervisadaDTO> listaDireccion = new ArrayList<DireccionUnidadSupervisadaDTO>();
		StringBuilder jpql = new StringBuilder();
		try {
			jpql.append("select d.idDirccionUnidadSuprvisada,d.idUnidadSupervisada,d.numeroVia,d.descripcionVia,d.interior,d.manzana,d.lote," 
					  + "d.kilometro,d.blockChallet,d.etapa,d.numeroDepartamento,d.urbanizacion,d.direccionCompleta,d.referencia,d.estado,"
					  + "u.mdiUbigeoPK.idDepartamento,u.nombre,u1.mdiUbigeoPK.idProvincia,u1.nombre,u2.mdiUbigeoPK.idDistrito,u2.nombre,"
					  + "m.idMaestroColumna,m.descripcion,m.codigo,m2.idMaestroColumna,m2.descripcion,m2.codigo ");
			jpql.append("from MdiDirccionUnidadSuprvisada d, ");
			jpql.append("MdiUbigeo u,MdiUbigeo u1,MdiUbigeo u2, ");
			jpql.append("MdiMaestroColumna m,MdiMaestroColumna m2 ");
			
			if(filtro.getIdUnidadSupervisada()!=null && filtro.getIdDirccionUnidadSuprvisada()==null){			
				jpql.append(" where d.idUnidadSupervisada.idUnidadSupervisada=" + filtro.getIdUnidadSupervisada());
			}else if(filtro.getIdDirccionUnidadSuprvisada()!=null && filtro.getIdUnidadSupervisada()==null){		
	    		jpql.append(" where d.idDirccionUnidadSuprvisada=" + filtro.getIdDirccionUnidadSuprvisada());	
			} else {
				jpql.append(" where d.idUnidadSupervisada="+ filtro.getIdUnidadSupervisada() + " and d.idDirccionUnidadSuprvisada=" + filtro.getIdDirccionUnidadSuprvisada());
			}
			jpql.append(" and d.estado='1' ");
			if(filtro.getCadCodigosTipoDireccion()!=null){
				jpql.append(" and m.idMaestroColumna=d.idTipoDireccion and m.codigo in (" + filtro.getCadCodigosTipoDireccion() + ") ");
			}
//			jpql.append(" and m2.idMaestroColumna=d.idTipoVia and m2.aplicacion='"+ Constantes.APLICACION_SGLSS+"' and m2.dominio='"+Constantes.DOMINIO_TIPO_VIA+"' ");
			jpql.append(" and m2.idMaestroColumna=d.idTipoVia and m2.dominio='"+Constantes.DOMINIO_TIPO_VIA+"' ");
			jpql.append(" and d.mdiUbigeo.mdiUbigeoPK.idDepartamento=u.mdiUbigeoPK.idDepartamento and u.mdiUbigeoPK.idProvincia='00' and u.mdiUbigeoPK.idDistrito='00'");
			jpql.append(" and d.mdiUbigeo.mdiUbigeoPK.idDepartamento=u1.mdiUbigeoPK.idDepartamento and d.mdiUbigeo.mdiUbigeoPK.idProvincia=u1.mdiUbigeoPK.idProvincia and u1.mdiUbigeoPK.idDistrito='00'");
			jpql.append(" and d.mdiUbigeo.mdiUbigeoPK.idDepartamento=u2.mdiUbigeoPK.idDepartamento and d.mdiUbigeo.mdiUbigeoPK.idProvincia=u2.mdiUbigeoPK.idProvincia and d.mdiUbigeo.mdiUbigeoPK.idDistrito=u2.mdiUbigeoPK.idDistrito");
			
			Query query = crud.getEm().createQuery(jpql.toString());			
    		List<Object[]> lista = query.getResultList();
    		listaDireccion = DireccionUnidadSupervisadaBuilder.getDireccionUnidadDTO(lista);
		} catch (Exception ex) {
			LOG.error("error findbuscarDireccionesUnidad",ex);
		}    		
    	return listaDireccion;
	}
        
    @Override
    public DireccionUnidadSupervisadaDTO findById(Long idDirccionUnidadSuprvisada) throws DireccionUnidadSupervisadaException{
        DireccionUnidadSupervisadaDTO retorno=null;
        try{
            MdiDirccionUnidadSuprvisada reg = crud.find(idDirccionUnidadSuprvisada,MdiDirccionUnidadSuprvisada.class);
            if(reg.getEstado().equals(Constantes.ESTADO_ACTIVO)){
                retorno=DireccionUnidadSupervisadaBuilder.toDireccionUnidadDTO(reg);
            }            
        }catch(Exception e){
            LOG.error("Error en findbyid",e);
        }
        return retorno;
    }
}