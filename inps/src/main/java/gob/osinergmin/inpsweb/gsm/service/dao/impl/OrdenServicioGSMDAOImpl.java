/**
* Resumen		
* Objeto			: OrdenServicioGSMDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz OrdenServicioDAO
* Fecha de Creación	: 27/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.gsm.service.dao.impl;

import gob.osinergmin.inpsweb.domain.builder.DmTitularMineroBuilder;
import gob.osinergmin.inpsweb.domain.builder.SupeCampRechCargaBuilder;
import gob.osinergmin.inpsweb.gsm.service.dao.OrdenServicioGSMDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.ExpedienteDAO;
import gob.osinergmin.inpsweb.service.dao.HistoricoEstadoDAO;
import gob.osinergmin.inpsweb.service.dao.MaestroColumnaDAO;
import gob.osinergmin.mdicommon.domain.dto.DmTitularMineroDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.ui.DmTitularMineroFilter;
import gob.osinergmin.mdicommon.domain.ui.SupeCampRechCargaFilter;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ordenServicioGSMDAO")
public class OrdenServicioGSMDAOImpl implements OrdenServicioGSMDAO {
    private static final Logger LOG = LoggerFactory.getLogger(OrdenServicioGSMDAOImpl.class);    
    @Inject
    private CrudDAO crud;
    @Inject
    private MaestroColumnaDAO maestroColumnaDAO;
    @Inject
    private HistoricoEstadoDAO historicoEstadoDAO;
    @Inject
    private ExpedienteDAO expedienteDAO;
    
	@Override
	public List<DmTitularMineroDTO> listarTitularMinero(DmTitularMineroFilter filtro) {
		LOG.info("DAO listarTitularMinero");
        List<DmTitularMineroDTO> listado=null;
        try{
            Query query = getFindQueryListarTitularMinero(filtro);
            listado = DmTitularMineroBuilder.toListDmTitularMineroDto(query.getResultList());
        }catch(Exception e){
            LOG.info("Error en listarTitularMinero",e);
        }
        return listado;
	}
	
	private Query getFindQueryListarTitularMinero(DmTitularMineroFilter filtro) {
    	LOG.info("DAO getFindQueryListarTitularMinero");
        Query query=null;
        try{
            if(filtro.getCodigoTitularMinero()!=null && !filtro.getCodigoTitularMinero().equals("")){
                query = crud.getEm().createNamedQuery("DmTitularMinero.findCodigoTitularMinero");
            }
            
            if(filtro.getCodigoTitularMinero()!=null && !filtro.getCodigoTitularMinero().equals("")){
                query.setParameter("codigoTitularMinero",filtro.getCodigoTitularMinero());
            }
            
        }catch(Exception e){
            LOG.error("Error getFindQueryListarTitularMinero: "+e.getMessage());
        }
        return query;
    }
    
    /*
    @Override
    public OrdenServicioDTO cambiarEstadoProceso(Long idOrdenServicio, Long idPersonalOri,Long idPersonalDest,Long idEstadoProceso,String motivoReasignacion,UsuarioDTO usuarioDTO,Long idPeticion, Long idMotivo) throws OrdenServicioException{ 
        LOG.info("cambiarEstadoProceso");
        OrdenServicioDTO retorno=null;
        try{
            PghOrdenServicio registroDAO = crud.find(idOrdenServicio, PghOrdenServicio.class);
            registroDAO.setDatosAuditoria(usuarioDTO);
            registroDAO.setIdEstadoProceso(new PghEstadoProceso(idEstadoProceso));
            registroDAO.setFechaEstadoProceso(new Date());
            if(idPeticion!=null){registroDAO.setIdPeticion(new MdiMaestroColumna(idPeticion));}
            if(idMotivo!=null){registroDAO.setIdMotivo(idMotivo);}
            if(motivoReasignacion!=null){registroDAO.setComentarioDevolucion(motivoReasignacion);}
            crud.update(registroDAO);
            
            MaestroColumnaDTO tipoHistorico=maestroColumnaDAO.findMaestroColumna(Constantes.DOMINIO_TIPO_COMPONENTE, Constantes.APLICACION_INPS, Constantes.CODIGO_TIPO_COMPONENTE_ORDEN_SERVICIO).get(0);
            HistoricoEstadoDTO historicoEstado=historicoEstadoDAO.registrar(null, idOrdenServicio, idEstadoProceso, idPersonalOri, idPersonalDest, tipoHistorico.getIdMaestroColumna(),idPeticion,idMotivo,motivoReasignacion, usuarioDTO);   
            LOG.info("historicoEstado:"+historicoEstado);
            retorno=OrdenServicioBuilder.toOrdenServicioDto(registroDAO);
        }catch(Exception e){
            LOG.error("error en cambiarEstadoProceso",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    @Override
    public OrdenServicioDTO registrar(Long idExpediente,Long idTipoAsignacion,Long idEstadoProceso,String codigoTipoSupervisor,Long idLocador,Long idSupervisoraEmpresa,UsuarioDTO usuarioDTO,String sigla) throws OrdenServicioException{
        LOG.error("registrar");
        OrdenServicioDTO retorno=null;
        try{
            Long iteracion=expedienteDAO.obtenerIteracionOrdenServicio(idExpediente);
            if(iteracion==null){
                throw new OrdenServicioException("Expediente no tiene Iteracion.",null);
            }
            
            PghOrdenServicio ordenServicio=new PghOrdenServicio();
            ordenServicio.setDatosAuditoria(usuarioDTO);
            ordenServicio.setIdExpediente(new PghExpediente(idExpediente));
            ordenServicio.setIdTipoAsignacion(idTipoAsignacion);
            ordenServicio.setEstado(Constantes.ESTADO_ACTIVO);
            ordenServicio.setIdEstadoProceso(new PghEstadoProceso(idEstadoProceso));
            ordenServicio.setFechaEstadoProceso(new Date());
            ordenServicio.setIteracion(iteracion);
            if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_NATURAL)){
                ordenServicio.setIdLocador(new MdiLocador(idLocador));
            }else if(codigoTipoSupervisor.equals(Constantes.CODIGO_TIPO_SUPERVISOR_PERSONA_JURIDICA)){
                ordenServicio.setIdSupervisoraEmpresa(new MdiSupervisoraEmpresa(idSupervisoraEmpresa));
            }
            String numeroOrdenServicio=generarNumeroOrdenServicio(sigla);
            LOG.info("numeroOrdenServicio-->"+numeroOrdenServicio);
            ordenServicio.setNumeroOrdenServicio(numeroOrdenServicio);
            crud.create(ordenServicio);
            
            retorno=OrdenServicioBuilder.toOrdenServicioDto(ordenServicio);
        }catch(Exception e){
            LOG.error("error en registrar",e);
            OrdenServicioException ex = new OrdenServicioException(e.getMessage(), e);
            throw ex;
        }
        return retorno;
    }
    
    public String generarNumeroOrdenServicio(String sigla){
        Long seq=crud.findSequence("PGH_NUMERO_OS_SEQ");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");
        return Utiles.padLeft(seq.toString(), 8,'0')+"-"+formateador.format(new Date())+"-OS-"+sigla;
    }
    
    @Override
    public List<OrdenServicioDTO> find(OrdenServicioFilter filtro) throws OrdenServicioException{
        LOG.info("find");
        List<OrdenServicioDTO> retorno=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            //arma campos
            jpql.append("SELECT new PghOrdenServicio(os.idOrdenServicio,os.fechaCreacion,os.numeroOrdenServicio,us.codigoOsinergmin,es.razonSocial,"
                    + "ex.numeroExpediente,ep.nombreEstado,os.fechaEstadoProceso,os.flagPresentoDescargo,os.iteracion,ep.idEstadoProceso) "
                    + "FROM PghOrdenServicio os "
                    + "LEFT JOIN os.idEstadoProceso ep "
                    + "LEFT JOIN os.idLocador lo "
                    + "LEFT JOIN os.idSupervisoraEmpresa se "
                    + "LEFT JOIN lo.pghPersonalList pelo "
                    + "LEFT JOIN se.pghPersonalList pese "
                    + "LEFT JOIN os.idExpediente ex "
                    + "LEFT JOIN ex.idUnidadSupervisada us "
                    + "LEFT JOIN ex.idEmpresaSupervisada es ");
            
            //arma where
            jpql.append("WHERE os.estado=1 and ex.estado ='1' ");
            if(filtro.getIdPersonal()!=null){
            	jpql.append(" and (pese.idPersonal=:idPersonal or pelo.idPersonal=:idPersonal) ");
            }
            
            if(filtro.getIdEstadoProceso()!=null){
                jpql.append(" and ep.idEstadoProceso=:idEstadoProceso ");
            }
            if(filtro.getFechaInicioEstadoProceso()!=null && filtro.getFechaFinEstadoProceso()!=null){
                jpql.append(" and os.fechaEstadoProceso between :fechaInicioEstadoProceso and :fechaFinEstadoProceso ");
            }
            if(filtro.getIdExpediente()!=null){
            	jpql.append(" and ex.idExpediente=:idExpediente ");
            }
            if(filtro.getIteracion()!=null){
            	jpql.append(" and os.iteracion=:iteracion ");
            }
            if(filtro.getNumeroExpediente()!=null && !filtro.getNumeroExpediente().equals("")){
            	jpql.append(" and ex.numeroExpediente like :numeroExpediente ");
            }
            if(filtro.getNumeroOrdenServicio()!=null && !filtro.getNumeroOrdenServicio().equals("")){
            	jpql.append(" and os.numeroOrdenServicio like :numeroOrdenServicio ");
            }
            if(filtro.getCodigoOsinergmin()!=null && !filtro.getCodigoOsinergmin().equals("")){
            	jpql.append(" and us.codigoOsinergmin like :codigoOsinergmin ");
            }
            
            //order
            jpql.append(" order by os.idOrdenServicio ");
            
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            if(filtro.getIdPersonal()!=null){
            	query.setParameter("idPersonal",filtro.getIdPersonal());
            }
            if(filtro.getIdEstadoProceso()!=null){
                query.setParameter("idEstadoProceso",filtro.getIdEstadoProceso());
            }
            if(filtro.getFechaInicioEstadoProceso()!=null && filtro.getFechaFinEstadoProceso()!=null){
                query.setParameter("fechaInicioEstadoProceso",filtro.getFechaInicioEstadoProceso());
                query.setParameter("fechaFinEstadoProceso",Utiles.sumarFechasDias(filtro.getFechaFinEstadoProceso(),1));
            }
            if(filtro.getIdExpediente()!=null){
            	query.setParameter("idExpediente",filtro.getIdExpediente());
            }
            if(filtro.getIteracion()!=null){
            	query.setParameter("iteracion",filtro.getIteracion());
            }
            if(filtro.getNumeroExpediente()!=null && !filtro.getNumeroExpediente().equals("")){
            	query.setParameter("numeroExpediente","%"+(filtro.getNumeroExpediente().trim().toUpperCase())+"%");
            }
            if(filtro.getNumeroOrdenServicio()!=null && !filtro.getNumeroOrdenServicio().equals("")){
            	query.setParameter("numeroOrdenServicio","%"+(filtro.getNumeroOrdenServicio().trim().toUpperCase())+"%");
            }
            if(filtro.getCodigoOsinergmin()!=null && !filtro.getCodigoOsinergmin().equals("")){
            	query.setParameter("codigoOsinergmin","%"+(filtro.getCodigoOsinergmin().trim())+"%");
            }

            retorno= OrdenServicioBuilder.toListOrdenServicioDto(query.getResultList());
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return retorno;
    }
    */
}
