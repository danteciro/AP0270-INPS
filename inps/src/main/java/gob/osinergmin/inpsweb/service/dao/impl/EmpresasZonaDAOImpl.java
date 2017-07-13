/**
* Resumen		
* Objeto			: EmpresasZonaDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la 
* 					  clase interfaz EmpresasZonaDAO.
* Fecha de Creación	: 23/09/2016.
* PR de Creación	: OSINE_SFS-480
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                          Descripción
* ---------------------------------------------------------------------------------------------------
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import gob.osinergmin.inpsweb.domain.NpsConfiguracionReles;
import gob.osinergmin.inpsweb.domain.NpsDetaConfReles;
import gob.osinergmin.inpsweb.domain.NpsDetaSupeCampRechCarga;
import gob.osinergmin.inpsweb.domain.NpsSupeCampRechCarga;
import gob.osinergmin.inpsweb.domain.builder.ConfiguracionRelesBuilder;
import gob.osinergmin.inpsweb.domain.builder.DetaConfRelesBuilder;
import gob.osinergmin.inpsweb.domain.builder.DetaSupeCampRechCargaBuilder;
import gob.osinergmin.inpsweb.domain.builder.EmpresasZonaVwBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupeCampRechCargaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.EmpresasZonaDAO;
import gob.osinergmin.inpsweb.service.exception.DetaSupeCampRechCargaException;
import gob.osinergmin.inpsweb.service.exception.SupeCampRechCargaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.ConfiguracionRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaConfRelesDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresasZonaVwDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.UsuarioDTO;
import gob.osinergmin.mdicommon.domain.ui.ConfiguracionRelesFilter;
import gob.osinergmin.mdicommon.domain.ui.DetaSupeCampRechCargaFilter;
import gob.osinergmin.mdicommon.domain.ui.EmpresasZonaVwFilter;
import gob.osinergmin.mdicommon.domain.ui.SupeCampRechCargaFilter;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service("EmpresasZonaDAO")
public class EmpresasZonaDAOImpl implements EmpresasZonaDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmpresasZonaDAOImpl.class);

    @Inject
    private CrudDAO crud;
	
    @Override
	@Transactional(readOnly=true)
	public List<EmpresasZonaVwDTO> listarEmpresasZona(EmpresasZonaVwFilter filtro) { 
    	LOG.info("inicio listarEmpresasZona");
		Query query=null;
		String queryString = "";
		List<EmpresasZonaVwDTO> listado=null;
        try{
            StringBuilder jpql = new StringBuilder();
            jpql.append("select np.id_supervision_rech_carga, npssc.id_supe_camp_rech_carga,n.idtipo, n.tipo,n.ruc,n.idempresa,n.empresa, "
            		+ "np.numero_expediente,n.idzona,n.zona,n.anio, np.id_oficio_doc, np.nombre_oficio_doc,npssc.id_acta_doc,npssc.nombre_acta_doc,"
            		+ "npssc.flg_cerrado  "
            		+ "from nps_supervision_rech_carga np "
            		+ "inner join NPS_LISTA_EMPRESAS_Y_ZONAS_VW n on np.emp_codemp=n.idempresa and np.numero_expediente is not null "
            		+ "left join nps_supe_camp_rech_carga npssc on npssc.id_supervision_rech_carga=np.id_supervision_rech_carga "
            		+ "and npssc.id_zona=n.idzona and npssc.estado='1' "
            		+ "where np.estado='1' ");
            
            if(filtro.getIdSupeCampRechCarga()!=null){
            	jpql.append(" and npssc.id_supe_camp_rech_carga = :idSupe ");
            }
            
            //if(filtro.getAnio()!=null && filtro.getAnio()!=""){
            if(!StringUtil.isEmpty(filtro.getAnio())){
            	jpql.append("and n.anio = :anio ");
            	jpql.append("and TO_CHAR(np.anio,'YYYY')= :anio ");
            }
            
            //if(filtro.getIdTipo()!=null && filtro.getIdTipo()!="")
            if(!StringUtil.isEmpty(filtro.getIdTipo())){
            	jpql.append("and n.idtipo= :idTipo ");
            }
            
            //if(filtro.getIdZona()!=null && filtro.getIdZona()!=""){
            if(!StringUtil.isEmpty(filtro.getIdZona())){
            	jpql.append("and n.idzona = :idZona ");
            }
            
            //if(filtro.getRuc()!=null && filtro.getRuc()!="")
            if(!StringUtil.isEmpty(filtro.getRuc()))
            	jpql.append("and n.ruc = :ruc ");
            
            //if(filtro.getDescripcionEmpresa()!=null && filtro.getDescripcionEmpresa()!=""){
            if(!StringUtil.isEmpty(filtro.getDescripcionEmpresa())){
            	jpql.append("and n.empresa like :empresa ");
            }
            
            //if(filtro.getNumeroExpediente()!=null && filtro.getNumeroExpediente()!=""){
            if(!StringUtil.isEmpty(filtro.getNumeroExpediente())){
            	jpql.append("and np.numero_expediente = :numeroExpediente ");
            }
            
            jpql.append("order by n.empresa, n.idzona asc ");
            
            queryString = jpql.toString();
            query = this.crud.getEm().createNativeQuery(queryString);
            
            if(filtro.getIdSupeCampRechCarga()!=null){
            	query.setParameter("idSupe",filtro.getIdSupeCampRechCarga());
            }
            
            //if(filtro.getAnio()!=null && filtro.getAnio()!=""){
            if(!StringUtil.isEmpty(filtro.getAnio())){
            	query.setParameter("anio",filtro.getAnio());
            }
            
            //if(filtro.getIdTipo()!=null && filtro.getIdTipo()!=""){
            if(!StringUtil.isEmpty(filtro.getIdTipo())){
            	query.setParameter("idTipo",filtro.getIdTipo());
            }
            
            //if(filtro.getIdZona()!=null && filtro.getIdZona()!=""){
            if(!StringUtil.isEmpty(filtro.getIdZona())){
            	query.setParameter("idZona",filtro.getIdZona());
            }
            
            //if(filtro.getRuc()!=null && filtro.getRuc()!=""){
            if(!StringUtil.isEmpty(filtro.getRuc())){
            	query.setParameter("ruc",filtro.getRuc());
            }
            
            //if(filtro.getDescripcionEmpresa()!=null && filtro.getDescripcionEmpresa()!=""){
            if(!StringUtil.isEmpty(filtro.getDescripcionEmpresa())){
            	query.setParameter("empresa","%"+filtro.getDescripcionEmpresa()+"%");
            }
            
            //if(filtro.getNumeroExpediente()!=null && filtro.getNumeroExpediente()!=""){
            if(!StringUtil.isEmpty(filtro.getNumeroExpediente())){
            	query.setParameter("numeroExpediente",filtro.getNumeroExpediente());
            }
            
    		listado = EmpresasZonaVwBuilder.toListaEmpresasZonaVwDTO(query.getResultList());
        } catch(Exception e){
        	LOG.error("Error listarEmpresasZona: " + e);
        }
		return listado;	
	}
    
    public List<SupeCampRechCargaDTO> listarSupeCampRechCarga(SupeCampRechCargaFilter filtro){
    	LOG.info("DAO listarSupeCampRechCarga");
        List<SupeCampRechCargaDTO> listado=null;
        try{
            Query query = getFindQueryListarSupeCamp(filtro);
            listado = SupeCampRechCargaBuilder.toListSupeCampRechCargaDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en listarSupeCampRechCarga",e);
        }
        return listado;
    }
    
    private Query getFindQueryListarSupeCamp(SupeCampRechCargaFilter filtro) {
    	LOG.info("DAO getFindQueryListarSupeCamp");
        Query query=null;
        try{
            if(filtro.getIdSupeCampRechCarga()!=null && !filtro.getIdSupeCampRechCarga().equals("")){
                query = crud.getEm().createNamedQuery("NpsSupeCampRechCarga.findByIdSupeCampRechCarga");
            }
            
            if(filtro.getIdSupeCampRechCarga()!=null && !filtro.getIdSupeCampRechCarga().equals("")){
                query.setParameter("idSupeCampRechCarga",filtro.getIdSupeCampRechCarga());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQueryListarSupeCamp: "+e.getMessage());
        }
        return query;
    }
    
	@Override
	public SupeCampRechCargaDTO insertarSupeCampRechCarga(SupeCampRechCargaDTO supeCampRechCargaDto, UsuarioDTO usuarioDTO)
			throws SupeCampRechCargaException {
		LOG.error("insertarSupeCampRechCarga");
		SupeCampRechCargaDTO retorno=null;
        try{
        	NpsSupeCampRechCarga registro = SupeCampRechCargaBuilder.getNpsSupeCampRechCarga(supeCampRechCargaDto);
        	registro.setDatosAuditoria(usuarioDTO);
        	registro.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(registro);
            
            retorno=SupeCampRechCargaBuilder.toSupeCampRechCargaDto(registro);
        }catch(Exception e){
            LOG.error("error en insertarSupeCampRechCarga",e);
            SupeCampRechCargaException ex = new SupeCampRechCargaException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
	}
	
	@Override
	public SupeCampRechCargaDTO actualizarSupeCampRechCarga(SupeCampRechCargaDTO supeCampRechCargaDto, UsuarioDTO usuarioDTO,
			String pantalla) throws SupeCampRechCargaException {
		SupeCampRechCargaDTO retorno=null;
		
		try{
			
			NpsSupeCampRechCarga supe = crud.find(supeCampRechCargaDto.getIdSupeCampRechCarga(), NpsSupeCampRechCarga.class);
			supe.setDatosAuditoria(usuarioDTO);
			
			if(pantalla.equals(Constantes.TIPO_PANTALLA_INFORMACION)){
				String horas="";
	        	String minutos="";
	        	String turno="";
	        	if(Integer.valueOf(supeCampRechCargaDto.getHoraInicio())>12){
	        		horas=String.valueOf(Integer.valueOf(supeCampRechCargaDto.getHoraInicio())-12);
	        		minutos=String.valueOf(supeCampRechCargaDto.getMinutoInicio());
	        		turno="PM";
	        		supeCampRechCargaDto.setFechaInicio(supeCampRechCargaDto.getFechaInicio()+" "+horas+":"+minutos+" "+turno);
	        	}else{
	        		horas=String.valueOf(Integer.valueOf(supeCampRechCargaDto.getHoraInicio()));
	        		minutos=String.valueOf(supeCampRechCargaDto.getMinutoInicio());
	        		turno="AM";
	        		supeCampRechCargaDto.setFechaInicio(supeCampRechCargaDto.getFechaInicio()+" "+horas+":"+minutos+" "+turno);
	        		
	        	}
	        	
	        	if(Integer.valueOf(supeCampRechCargaDto.getHoraFin())>12){
	        		horas=String.valueOf(Integer.valueOf(supeCampRechCargaDto.getHoraFin())-12);
	        		minutos=String.valueOf(supeCampRechCargaDto.getMinutoFin());
	        		turno="PM";
	        		supeCampRechCargaDto.setFechaFin(supeCampRechCargaDto.getFechaFin()+" "+horas+":"+minutos+" "+turno);
	        	}else{
	        		horas=String.valueOf(Integer.valueOf(supeCampRechCargaDto.getHoraFin()));
	        		minutos=String.valueOf(supeCampRechCargaDto.getMinutoFin());
	        		turno="AM";
	        		supeCampRechCargaDto.setFechaFin(supeCampRechCargaDto.getFechaFin()+" "+horas+":"+minutos+" "+turno);
	        		
	        	}
				
				supe.setFechaInicio(Utiles.stringToDate(supeCampRechCargaDto.getFechaInicio(),Constantes.FORMATO_FECHA_LARGE));
				supe.setFechaFin(Utiles.stringToDate(supeCampRechCargaDto.getFechaFin(),Constantes.FORMATO_FECHA_LARGE));
				supe.setNombreSupervisorEmpresa(supeCampRechCargaDto.getNombreSupervisorEmpresa().toUpperCase());
				supe.setCargoSupervisorEmpresa(supeCampRechCargaDto.getCargoSupervisorEmpresa().toUpperCase());
				supe.setNombreSupervisorOsinergmin(supeCampRechCargaDto.getNombreSupervisorOsinergmin().toUpperCase());
				supe.setCargoSupervisorOsinergmin(supeCampRechCargaDto.getCargoSupervisorOsinergmin().toUpperCase());
				supe.setIdUbigeo(supeCampRechCargaDto.getIdUbigeo());
				
			} else if(pantalla.equals(Constantes.TIPO_PANTALLA_OBSERVACION)){
				supe.setAjusteRele(supeCampRechCargaDto.getAjusteRele().toUpperCase());
				supe.setHabilitacionRele(supeCampRechCargaDto.getHabilitacionRele().toUpperCase());
				supe.setProtocoloRele(supeCampRechCargaDto.getProtocoloRele().toUpperCase());
				supe.setReporteRele(supeCampRechCargaDto.getReporteRele().toUpperCase());
				supe.setOtrasObservaciones(supeCampRechCargaDto.getOtrasObservaciones().toUpperCase());
				supe.setNotasEmpresa(supeCampRechCargaDto.getNotasEmpresa().toUpperCase());
				
			} else if(pantalla.equals(Constantes.TIPO_PANTALLA_ADJUNTAR)){
				supe.setNombreActaDoc(supeCampRechCargaDto.getNombreActaDoc().toUpperCase());
				supe.setIdActaDoc(supeCampRechCargaDto.getIdActaDoc());
				
			} else if(pantalla.equals(Constantes.TIPO_FLAG_REGISTRO)){
				supe.setFlgCerrado(supeCampRechCargaDto.getFlgCerrado());
				
			} else if(pantalla.equals(Constantes.TIPO_PANTALLA_CAMBIAR_ESTADO)){
				supe.setEstado(supeCampRechCargaDto.getEstado());
			}
			
			
			crud.update(supe);
			retorno = SupeCampRechCargaBuilder.toSupeCampRechCargaDto(supe);
		}catch(Exception e){
            LOG.error("error en actualizarSupeCampRechCarga");
            SupeCampRechCargaException ex = new SupeCampRechCargaException(e.getMessage(), e);
            throw ex;
        }
		LOG.info("EmpresasZonaDAOImpl: actualizarSupeCampRechCarga-fin");
		return retorno;
	}
	
	public List<DetaSupeCampRechCargaDTO> listarDetaSupeCampRechCarga(DetaSupeCampRechCargaFilter filtro){
    	LOG.info("DAO listarDetaSupeCampRechCarga");
        List<DetaSupeCampRechCargaDTO> listado=null;
        try{
            Query query = getFindQueryListarDetaSupeCamp(filtro);
            listado = DetaSupeCampRechCargaBuilder.toListDetaSupeCampRechCargaDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en listarSupeCampRechCarga",e);
        }
        return listado;
    }
    
    private Query getFindQueryListarDetaSupeCamp(DetaSupeCampRechCargaFilter filtro) {
    	LOG.info("DAO getFindQueryListarDetaSupeCamp");
        Query query=null;
        try{
        	if(filtro.getIdSupeCampRechCarga()!=null && filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null &&
        			!filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga().equals("") &&
        			filtro.getEstado()!=null && !filtro.getEstado().equals("") && filtro.getListaIdRele()==null){
        		query = crud.getEm().createNamedQuery("NpsDetaSupeCampRechCarga.findByIdSupeCampRechCarga");
        	}
        	
        	if(filtro.getIdSupeCampRechCarga()!=null && filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null &&
        			!filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga().equals("") &&
        			filtro.getEstado()==null){
        		query = crud.getEm().createNamedQuery("NpsDetaSupeCampRechCarga.findByIdSupeCampRechCargaAll");
        	}
        	
            if(filtro.getIdDetaSupeCampRechCarga()!=null && !filtro.getIdDetaSupeCampRechCarga().equals("")){
                query = crud.getEm().createNamedQuery("NpsDetaSupeCampRechCarga.findByIdDetaSupeCampRechCarga");
            }
            
            if(filtro.getIdSupeCampRechCarga()!=null && filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null &&
        			!filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga().equals("") && filtro.getListaIdRele()!=null && filtro.getListaIdRele().size()>0){
                query = crud.getEm().createNamedQuery("NpsDetaSupeCampRechCarga.findByIdRele");
            }
            
            if(filtro.getIdSupeCampRechCarga()!=null && filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null &&
        			!filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga().equals("") &&
        			filtro.getEstado()!=null && !filtro.getEstado().equals("") && filtro.getListaIdRele()==null){
                query.setParameter("idSupeCampRechCarga",filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga());
                query.setParameter("estado",filtro.getEstado());
            }
            
            if(filtro.getIdSupeCampRechCarga()!=null && filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null &&
        			!filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga().equals("") &&
        			filtro.getEstado()==null){
                query.setParameter("idSupeCampRechCarga",filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga());
            }
            
            if(filtro.getIdDetaSupeCampRechCarga()!=null && !filtro.getIdDetaSupeCampRechCarga().equals("")){
                query.setParameter("idDetaSupeCampRechCarga",filtro.getIdDetaSupeCampRechCarga());
            }
            
            if(filtro.getIdSupeCampRechCarga()!=null && filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null &&
        			!filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga().equals("") && filtro.getListaIdRele()!=null && filtro.getListaIdRele().size()>0){
            	query.setParameter("idSupeCampRechCarga",filtro.getIdSupeCampRechCarga().getIdSupeCampRechCarga());
            	query.setParameter("idRele",filtro.getListaIdRele());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQueryListarDetaSupeCamp: "+e.getMessage());
        }
        return query;
    }
	
	@Override
	public DetaSupeCampRechCargaDTO insertarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO,
			UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException {
		DetaSupeCampRechCargaDTO retorno=null;
        try{
        	NpsDetaSupeCampRechCarga registro = DetaSupeCampRechCargaBuilder.getNpsDetaSupeCampRechCarga(detaSupeCampRechCargaDTO);
        	registro.setDatosAuditoria(usuarioDTO);
        	registro.setEstado(Constantes.ESTADO_ACTIVO);
            crud.create(registro);
            
            retorno=DetaSupeCampRechCargaBuilder.toDetaSupeCampRechCargaDto(registro);
        }catch(Exception e){
            LOG.error("error en insertarSupeCampRechCarga",e);
            DetaSupeCampRechCargaException ex = new DetaSupeCampRechCargaException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
	}
	
	@Override
	public DetaSupeCampRechCargaDTO actualizarDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO detaSupeCampRechCargaDto,
			UsuarioDTO usuarioDTO) throws DetaSupeCampRechCargaException {
		DetaSupeCampRechCargaDTO retorno=null;
		
		try{
			NpsDetaSupeCampRechCarga detaSupe = crud.find(detaSupeCampRechCargaDto.getIdDetaSupeCampRechCarga(), NpsDetaSupeCampRechCarga.class);
			detaSupe.setDatosAuditoria(usuarioDTO);
			// Fila Etapa 2
			detaSupe.setReleUmbralHz(detaSupeCampRechCargaDto.getReleUmbralHz());
			detaSupe.setReleUmbralS(detaSupeCampRechCargaDto.getReleUmbralS());
			detaSupe.setReleDerivadaHz(detaSupeCampRechCargaDto.getReleDerivadaHz());
			detaSupe.setReleDerivadaHzs(detaSupeCampRechCargaDto.getReleDerivadaHzs());
			detaSupe.setReleDerivadaS(detaSupeCampRechCargaDto.getReleDerivadaS());
			detaSupe.setPotr(detaSupeCampRechCargaDto.getPotr());
			detaSupe.setDemandaMaxima(detaSupeCampRechCargaDto.getDemandaMaxima());
			detaSupe.setDemandaMedia(detaSupeCampRechCargaDto.getDemandaMedia());
			detaSupe.setDemandaMinima(detaSupeCampRechCargaDto.getDemandaMinima());
			
			// Fila Codigo Osinergmin
			detaSupe.setDemandaMw(detaSupeCampRechCargaDto.getDemandaMw());
			if(detaSupeCampRechCargaDto.getHora()!=null && !detaSupeCampRechCargaDto.getHora().equals("")){
				detaSupe.setHora(Utiles.stringToDate(detaSupeCampRechCargaDto.getHora(),Constantes.FORMATO_FECHA_LARGE));
			}else{
				detaSupe.setHora(null);
			}
			
			// CheckBoxs
			detaSupe.setFlgExisteReleUmbral(detaSupeCampRechCargaDto.getFlgExisteReleUmbral());
			detaSupe.setFlgExisteReleDerivada(detaSupeCampRechCargaDto.getFlgExisteReleDerivada());
			detaSupe.setFlgAjusteReleUmbral(detaSupeCampRechCargaDto.getFlgAjusteReleUmbral());
			detaSupe.setFlgAjusteReleDerivada(detaSupeCampRechCargaDto.getFlgAjusteReleDerivada());
			detaSupe.setFlgOtroAjusteUmbral(detaSupeCampRechCargaDto.getFlgOtroAjusteUmbral());
			detaSupe.setFlgOtroAjusteDerivada(detaSupeCampRechCargaDto.getFlgOtroAjusteDerivada());
			detaSupe.setFlgProtocoloUmbral(detaSupeCampRechCargaDto.getFlgProtocoloUmbral());
			detaSupe.setFlgProtocoloDerivada(detaSupeCampRechCargaDto.getFlgProtocoloDerivada());
			
			detaSupe.setFlgFiscalizado(detaSupeCampRechCargaDto.getFlgFiscalizado());
			
			detaSupe.setEstado(detaSupeCampRechCargaDto.getEstado());
			
			// Text Area de Observaciones
			detaSupe.setObservaciones(detaSupeCampRechCargaDto.getObservaciones());
			
			crud.update(detaSupe);
			retorno = DetaSupeCampRechCargaBuilder.toDetaSupeCampRechCargaDto(detaSupe);
		}catch(Exception e){
            LOG.error("error en actualizarDetaSupeCampRechCarga");
            DetaSupeCampRechCargaException ex = new DetaSupeCampRechCargaException(e.getMessage(), e);
            throw ex;
        }
		LOG.info("EmpresasZonaDAOImpl: actualizarDetaSupeCampRechCarga-fin");
		return retorno;
	}
	
	public List<ConfiguracionRelesDTO> listarConfiguracionReles(ConfiguracionRelesFilter filtro){
    	LOG.info("DAO listarSupeCampRechCarga");
        List<ConfiguracionRelesDTO> listado=null;
        try{
            Query query = getFindQueryListarConfiguracionReles(filtro);
            List<NpsConfiguracionReles> lista = query.getResultList();
            if(!CollectionUtils.isEmpty(lista)){
            	listado = ConfiguracionRelesBuilder.toListConfiguracionRelesDto(lista);
            }
            
        }catch(Exception e){
            LOG.info("Error en listarSupeCampRechCarga",e);
        }
        return listado;
    }
    
    private Query getFindQueryListarConfiguracionReles(ConfiguracionRelesFilter filtro) {
    	LOG.info("DAO getFindQueryListarConfiguracionReles");
        Query query=null;
        try{
            if(filtro.getAnio()!=null && !filtro.getAnio().equals("") &&
            	filtro.getEmpCodemp()!=null && !filtro.getEmpCodemp().equals("") &&
            	filtro.getIdSubestacion()!=null && filtro.getIdSubestacion().longValue()!=0L){
                query = crud.getEm().createNamedQuery("NpsConfiguracionReles.findByAnioEmpSubEst");
            }
            
            if(filtro.getAnio()!=null && !filtro.getAnio().equals("") &&
                filtro.getEmpCodemp()!=null && !filtro.getEmpCodemp().equals("") &&
                filtro.getIdSubestacion()!=null && filtro.getIdSubestacion().longValue()==0L && 
                filtro.getIdZona()!=null){
                query = crud.getEm().createNamedQuery("NpsConfiguracionReles.findByAnioEmpIdZona");
            }
            
            if(filtro.getAnio()!=null && !filtro.getAnio().equals("") &&
                filtro.getEmpCodemp()!=null && !filtro.getEmpCodemp().equals("") &&
                filtro.getIdSubestacion()!=null && filtro.getIdSubestacion().longValue()!=0L){
                query.setParameter("anio",filtro.getAnio());
                query.setParameter("empresa",filtro.getEmpCodemp());
                query.setParameter("subEstacion",filtro.getIdSubestacion());
            }
            
            if(filtro.getAnio()!=null && !filtro.getAnio().equals("") &&
                filtro.getEmpCodemp()!=null && !filtro.getEmpCodemp().equals("") &&
                filtro.getIdSubestacion()!=null && filtro.getIdSubestacion().longValue()==0L && 
                filtro.getIdZona()!=null){
                query.setParameter("anio",filtro.getAnio());
                query.setParameter("empresa",filtro.getEmpCodemp());
                query.setParameter("idZona",filtro.getIdZona());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQueryListarConfiguracionReles: "+e.getMessage());
        }
        return query;
    }
	
    @Override
	@Transactional(readOnly=true)
	public SupeCampRechCargaDTO obtenerRegistroEmpresa(Long idSupeCampRechCarga) throws SupeCampRechCargaException {
		LOG.error("Obtener Registro Empresa");
		SupeCampRechCargaDTO retorno=null;		
        try{
        Query query= null;
      	query=crud.getEm().createNamedQuery("NpsSupeCampRechCarga.findByIdSupeCampRechCarga");
      	if(idSupeCampRechCarga!=null){
      		query.setParameter("idSupeCampRechCarga",idSupeCampRechCarga);
      	}      	
      	List<NpsSupeCampRechCarga> lista = query.getResultList();      	
      	if(!CollectionUtils.isEmpty(lista)){
      		retorno=SupeCampRechCargaBuilder.toSupeCampRechCargaDto(lista.get(0));
      	}   	
      		
        }catch(Exception e){
            LOG.error("error en insertarSupeCampRechCarga",e);
            SupeCampRechCargaException ex = new SupeCampRechCargaException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
	}
    
    @Override
    public ConfiguracionRelesDTO registrarConfiguracionReles(
                 ConfiguracionRelesDTO configuracionReleDTO,
                 UsuarioDTO usuarioDTO) throws SupeCampRechCargaException {
            LOG.info("registrarConfiguracionReles : registrar-inicio");
            ConfiguracionRelesDTO retorno = null;
            try {
            	
                NpsConfiguracionReles configuracionReles = ConfiguracionRelesBuilder.getNpsConfiguracionReles(configuracionReleDTO);
                configuracionReles.setDatosAuditoria(usuarioDTO);
                configuracionReles.setEstado(Constantes.ESTADO_ACTIVO);
                
                crud.create(configuracionReles);
                
                retorno = ConfiguracionRelesBuilder.toConfiguracionRelesDto(configuracionReles);
            } catch (Exception e) {
                LOG.error("Error en registrarConfiguracionReles", e);
                SupeCampRechCargaException ex = new SupeCampRechCargaException(e.getMessage(), e);
                throw ex;
            }
            LOG.info("registrarConfiguracionReles : registrar-fin");
            return retorno;
    }


    
    @Override
    public DetaConfRelesDTO detalleConfiguracionReles(DetaConfRelesDTO detaConfigRelDTO , UsuarioDTO usuarioDTO) throws SupeCampRechCargaException {
          DetaConfRelesDTO retorno=null;
            LOG.info("detalleConfiguracionReles : registrar-inicio");
     try{
          NpsDetaConfReles registro = DetaConfRelesBuilder.getNpsDetaConfReles(detaConfigRelDTO);
          registro.setDatosAuditoria(usuarioDTO);
          registro.setEstado(Constantes.ESTADO_ACTIVO);
           
         crud.create(registro);
         
         retorno=DetaConfRelesBuilder.toDetaConfRelesDto(registro);
     }catch(Exception e){
         LOG.error("error en detalleConfiguracionReles",e);
         SupeCampRechCargaException ex = new SupeCampRechCargaException(e.getMessage(), e);
         throw ex;
     }
     return retorno;
    }
    
    @Override
    public Long existeConfiguracionRele(ConfiguracionRelesDTO configuracionReleDTO)
    {
		StringBuilder jpql = new StringBuilder();
		Long valor=0L;
		Query query = null;
		
			jpql.append("SELECT cr FROM NpsConfiguracionReles cr "+
						" WHERE TO_CHAR(cr.anio,'YYYY')=" + configuracionReleDTO.getAnio() +
						" AND cr.empCodemp='" + configuracionReleDTO.getEmpCodemp() + "' " +
						" AND cr.idSubestacion=" + configuracionReleDTO.getIdSubestacion() +
						" AND cr.estado="+ Constantes.ESTADO_ACTIVO);
			query = crud.getEm().createQuery(jpql.toString());		
			
			List<NpsConfiguracionReles> lista = query.getResultList();
			
			if(!CollectionUtils.isEmpty(lista)){
				ConfiguracionRelesDTO relesote = ConfiguracionRelesBuilder.toRelesote(lista.get(0));
				valor=relesote.getIdConfiguracionReles();
			}
			 
			 return valor;
		
    }
    
    @Override
    public boolean borrarDetalleConfiguracionReles(ConfiguracionRelesDTO configuracionReleDTO)
    {
    	NpsDetaConfReles objNpsDetaConfReles=crud.find(configuracionReleDTO.getIdConfiguracionReles(), NpsDetaConfReles.class);
	    crud.delete(objNpsDetaConfReles);
	    return true;    	
    }
    
    @Override
    public List<ConfiguracionRelesDTO> listarSubEstacionConfiguracion(String idEmpresa, String anio)
    {
    	List<ConfiguracionRelesDTO> listaRetorno =null;
    	StringBuilder jpql = new StringBuilder();
		Query query = null;
		
		jpql.append(" SELECT cr FROM NpsConfiguracionReles cr "+
					" WHERE TO_CHAR(cr.anio,'YYYY')=" + anio +
					" AND cr.empCodemp='" + idEmpresa +"'");
    	
		query = crud.getEm().createQuery(jpql.toString());		
		
		List<NpsConfiguracionReles> lista = query.getResultList();
		
		if(!CollectionUtils.isEmpty(lista)){
			listaRetorno = ConfiguracionRelesBuilder.toListRelesote(lista);

		}
		return listaRetorno;
    }

	@Override
	public List<DetaConfRelesDTO> findDetaConfReles(ConfiguracionRelesDTO configuracionReleDTO) {
		List<DetaConfRelesDTO> listaRetorno =null;
    	StringBuilder jpql = new StringBuilder();
		Query query = null;
		
		jpql.append(" SELECT cr FROM NpsDetaConfReles cr "+
					" WHERE cr.estado ='1'  "+
					" AND cr.idConfiguracionReles.idConfiguracionReles ='" + configuracionReleDTO.getIdConfiguracionReles() +"'");
    	
		query = crud.getEm().createQuery(jpql.toString());		
		
		List<NpsDetaConfReles> lista = query.getResultList();
		
		if(!CollectionUtils.isEmpty(lista)){
			listaRetorno = DetaConfRelesBuilder.toListDetaConfRelesDto(lista);
		}
		return listaRetorno;
	}

	@Override
	@Transactional(readOnly=true)
	public void deleteDetaRele(DetaConfRelesDTO detalle) {
		try {
			NpsDetaConfReles objNpsDetaConfReles=crud.find(detalle.getIdDetaConfReles(), NpsDetaConfReles.class);
		    crud.delete(objNpsDetaConfReles);
		} catch (Exception e) {
			LOG.error("error en borrar",e);

		}
		
	}

	@Override
	public ConfiguracionRelesDTO findConfiguracionRele(String idEmpresa,String anio, String idSubEstacion) {
		ConfiguracionRelesDTO configuracion=null;
		StringBuilder jpql = new StringBuilder();
		Long valor=0L;
		Query query = null;
		
			jpql.append("SELECT cr FROM NpsConfiguracionReles cr "+
						" WHERE TO_CHAR(cr.anio,'YYYY')=" + anio +
						" AND cr.empCodemp='" + idEmpresa + "' " +
						" AND cr.idSubestacion=" + idSubEstacion +
						" AND cr.estado="+ Constantes.ESTADO_ACTIVO);
			query = crud.getEm().createQuery(jpql.toString());		
			
			List<NpsConfiguracionReles> lista = query.getResultList();
			
			if(!CollectionUtils.isEmpty(lista)){
				configuracion = ConfiguracionRelesBuilder.toRelesote(lista.get(0));
			}
			 
			 return configuracion;
	}

	@Override
	// Test
	//public List<ConfiguracionRelesDTO> findAllCnfReles(String idEmpresa, String anio) {
	public List<ConfiguracionRelesDTO> findAllCnfReles(String idEmpresa, String anio, Long idZona) {
	// Test
		List<ConfiguracionRelesDTO> configuracion=null;
		StringBuilder jpql = new StringBuilder();
		Long valor=0L;
		Query query = null;
		
			jpql.append("SELECT cr FROM NpsConfiguracionReles cr "+
						" WHERE TO_CHAR(cr.anio,'YYYY')=" + anio +
						" AND cr.empCodemp='" + idEmpresa + "' " +
						// Test
						" AND cr.idZona='" + idZona + "'"+
						// Test
						" AND cr.estado="+ Constantes.ESTADO_ACTIVO);
			query = crud.getEm().createQuery(jpql.toString());		
			
			List<NpsConfiguracionReles> lista = query.getResultList();
			
			if(!CollectionUtils.isEmpty(lista)){
				configuracion = ConfiguracionRelesBuilder.toListRelesote(lista);
			}
			 
			 return configuracion;
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteCabeRele(ConfiguracionRelesDTO configuracionRele) {
		try {
			NpsConfiguracionReles cab=crud.find(configuracionRele.getIdConfiguracionReles(), NpsConfiguracionReles.class);
		    crud.delete(cab);
		} catch (Exception e) {
			LOG.error("error en borrar",e);

		}
	}
	
    
}