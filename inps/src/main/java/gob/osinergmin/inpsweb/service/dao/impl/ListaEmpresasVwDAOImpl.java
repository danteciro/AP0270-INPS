package gob.osinergmin.inpsweb.service.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gob.osinergmin.inpsweb.domain.NpsSupervisionRechCarga;
import gob.osinergmin.inpsweb.domain.builder.ListaEmpresasVwBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupeCampRechCargaBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupervisionRechCargaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ListaEmpresasVwDAO;
import gob.osinergmin.inpsweb.service.exception.ListaEmpresasVwException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;

import gob.osinergmin.mdicommon.domain.dto.EmpresaViewDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;

import gob.osinergmin.mdicommon.domain.ui.EmpresaViewFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("listaEmpresasVwDAO")
public class ListaEmpresasVwDAOImpl implements ListaEmpresasVwDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ListaEmpresasVwDAO.class);
	@Inject
    private CrudDAO crud;

	    @Override
		@Transactional(readOnly=true)
	public List<EmpresaViewDTO> findEmpresasVw(EmpresaViewFilter filtro) throws ListaEmpresasVwException {
		LOG.info("inicio findEmpresasVw");
		Query query=null;
		String queryString = "";
		List<EmpresaViewDTO> listado= new ArrayList<EmpresaViewDTO>();
        try{
        	
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT x.* FROM ( SELECT le.idempresa, " +
       " le.empresa, " +
       " le.ruc, " +
       " le.idtipo, " +
       " le.tipo, " +
       " le.anio, " +
       " rc.numero_expediente, " +
       " rc.nombre_oficio_doc, " +
       " rc.id_oficio_doc, " +
       " rc.estado, " +
       " src.flg_observado, " +
       " src.id_informe_supe_rech_carga, " +
       " src.nombre_informe_doc, " +
       " src.id_informe_doc, " +
       " TO_CHAR(rc.anio, 'yyyy') " +
  " FROM nps_lista_empresas_vw le " +
  " LEFT JOIN nps_supervision_rech_carga rc " +
   " ON le.idempresa = rc.emp_codemp " +
 " LEFT JOIN nps_informe_supe_rech_carga src " +
  "  ON rc.id_supervision_rech_carga = src.id_supervision_rech_carga " +
" WHERE 1 = 1 " +
  " AND le.anio = "  + filtro.getAnio() +
  " AND TO_CHAR(rc.anio, 'yyyy') = "  + filtro.getAnio());          
jpql.append(" UNION " +
" SELECT DISTINCT le.idempresa, " +
              "  le.empresa, " +
              "  le.ruc, " +
              " le.idtipo, " +
              " le.tipo, " +
              "  le.anio, " +
              //" rc.numero_expediente, " +
              " ''," +
              "  '', " +
              "  0, " +
               " '', " +
               " '',"+ //src.flg_observado, " +
              "  0, " +
              "  '', " +
              "  0, " +
              "  '' " +
              " FROM nps_lista_empresas_vw le " +
             // " LEFT JOIN nps_supervision_rech_carga rc " +
             //  " ON le.idempresa = rc.emp_codemp " +
             //" LEFT JOIN nps_informe_supe_rech_carga src " +
             // "  ON rc.id_supervision_rech_carga = src.id_supervision_rech_carga " +
 " WHERE le.anio = " + filtro.getAnio() +
  "  AND le.idempresa NOT IN " +
     "  (SELECT rc.emp_codemp " +
     "     FROM nps_supervision_rech_carga rc " +
     "    WHERE TO_CHAR(rc.anio, 'yyyy') = "+ filtro.getAnio() +") "  + 
  "  ) x Where 1=1 " );
         
            //if(filtro.getAnio()!=null && filtro.getAnio()!="")
            //	jpql.append(" and le.anio =" + filtro.getAnio());
            if(!StringUtil.isEmpty(filtro.getIdTipo())){
            	jpql.append(" and x.idtipo='" + filtro.getIdTipo()+"'");
            }
            
            if(!StringUtil.isEmpty(filtro.getRuc())){
            	jpql.append(" and x.ruc = '" + filtro.getRuc()+"'"); 
            } 
            
            if(!StringUtil.isEmpty(filtro.getEmpresa())){
            	jpql.append(" and x.empresa LIKE '%" + filtro.getEmpresa()+"%'");
            }
            	
            if(!StringUtil.isEmpty(filtro.getNumeroExpediente())){
            	jpql.append(" and x.numero_expediente =" +  filtro.getNumeroExpediente());
            }            	
            
            if(filtro.getFlagObservado()!=null && filtro.getFlagObservado()!="" && filtro.getFlagObservado()!="null"){
            	if(filtro.getFlagObservado().contains("NULL")){
            		jpql.append(" and (x.flg_observado  " +  filtro.getFlagObservado()+ " or x.flg_observado=0) ");
            	}else{
            		jpql.append(" and x.flg_observado  " +  filtro.getFlagObservado());
            	}
            	
            }
            
            jpql.append(" order by idEmpresa asc  ");
            
            queryString = jpql.toString();
  
            query = this.crud.getEm().createNativeQuery(queryString);
            
    		listado = ListaEmpresasVwBuilder.toListaEmpresasVwDTO(query.getResultList()); 
    		
    		
        } catch(Exception e){
            LOG.error("Error findEmpresasVw: " + e);
            throw new ListaEmpresasVwException(e);
        }
        
        if(listado!=null && listado.size()>0)
			return listado;
		return listado;
	}
	    
		@Override
		public SupervisionRechCargaDTO registrarSupervisionRechazoCarga(
				SupervisionRechCargaDTO supervisionRechCargaDTO,
				UsuarioDTO usuarioDTO) throws ListaEmpresasVwException {
			  LOG.info("registrarSupervisionRechazoCarga : registrar-inicio");
			  SupervisionRechCargaDTO retorno = null;
		        try {
		            NpsSupervisionRechCarga supervisionRechazoCarga = SupervisionRechCargaBuilder.toSupervisionRechCargaDomain(supervisionRechCargaDTO);
		            supervisionRechazoCarga.setDatosAuditoria(usuarioDTO);
		         //   supervisionRechazoCarga.setIdOficioDoc(supervisionRechCargaDTO.getIdOficioDoc());
		           
		            supervisionRechazoCarga.setEstado(Constantes.ESTADO_ACTIVO);
		            crud.create(supervisionRechazoCarga);
		            retorno = SupervisionRechCargaBuilder.toSupervisionRechCargaDTO(supervisionRechazoCarga);
		           
		        } catch (Exception e) {
		            LOG.error("Error en registrar", e);
		            ListaEmpresasVwException ex = new ListaEmpresasVwException(e.getMessage(), e);
		            throw ex;
		        }
		        LOG.info("registrarSupervisionRechazoCarga : registrar-fin");
		        return retorno;
		}



	    @Override
		@Transactional(readOnly=true)
	public List<SupeCampRechCargaDTO> listaActasExpediente(String nroExpediente) throws ListaEmpresasVwException {
		LOG.info("inicio listaActasExpediente");
		Query query=null;
		String queryString = "";
		List<SupeCampRechCargaDTO> listado=null;
        try{
            StringBuilder jpql = new StringBuilder();
            jpql.append(" SELECT sc.id_supe_camp_rech_carga, sc.id_supervision_rech_carga "+
		  " FROM nps_supe_camp_rech_carga sc "+
		  " LEFT JOIN nps_supervision_rech_carga rc "+
		   " ON sc.id_supervision_rech_carga = rc.id_supervision_rech_carga "+ 
		 " WHERE sc.nombre_acta_doc IS NOT NULL " + 
		  " AND rc.numero_expediente="+nroExpediente);
            queryString = jpql.toString();
            query = this.crud.getEm().createNativeQuery(queryString);
    		listado = SupeCampRechCargaBuilder.toListSupeCampRechCargaActaDTO(query.getResultList());
        } catch(Exception e){
            LOG.error("Error listaActasExpediente: " + e);
            throw new ListaEmpresasVwException(e);
        }
		return listado;
	}

	    @Override
		@Transactional
		 public List<SupeCampRechCargaDTO> listaActasZona(String numeroExpediente) throws ListaEmpresasVwException{
				LOG.info("inicio listaActasZona");
				Query query=null;
				String queryString = "";
				List<SupeCampRechCargaDTO> listado = new ArrayList<SupeCampRechCargaDTO>();

		        try{
		            StringBuilder jpql = new StringBuilder();
		            jpql.append(" SELECT src.nombre_zona, "+
						    "   src.id_acta_doc, "+
						     "  src.nombre_acta_doc "+
						     " FROM nps_supe_camp_rech_carga src "+
						     " INNER JOIN nps_supervision_rech_carga rc "+
						     "    ON rc.id_supervision_rech_carga = src.id_supervision_rech_carga "+
		            		" WHERE src.id_acta_doc IS NOT NULL  AND rc.numero_expediente = " +numeroExpediente);
		            queryString = jpql.toString();
		            query = this.crud.getEm().createNativeQuery(queryString);
		            
		    		listado = SupeCampRechCargaBuilder.toListSupeCampRechCargaActaDTO(query.getResultList());
		    		
		    		
		        } catch(Exception e){
		            LOG.error("Error listaActasZona: " + e);
		            throw new ListaEmpresasVwException(e);
		        }
				return listado;
			}

	    

	   

 
     

}
