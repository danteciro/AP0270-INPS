/**
* Resumen		
* Objeto		: UnidadSupervisadaDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz UnidadSupervisadaDAO
* Fecha de Creación	: 25/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     25/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* OSINE_MAN_DSHL-003  	    	 |  20/06/2017   |   Claudio Chaucca Umana ::ADAPTER     |     Mostrar Unidades Operativas con RHO "VIGENTE" y Actividad de la Unidad Operativa de acuerdo a las actividades del Usuario Supervisor 
*/

package gob.osinergmin.inpsweb.service.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.osinergmin.inpsweb.domain.builder.UnidadSupervisadaBuilder;
import gob.osinergmin.inpsweb.service.dao.UnidadSupervisadaDAO;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.StringUtil;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;
import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* OSINE_SFS-480 - RSIS 26 - Inicio */
@Service("unidadSupervisadaDAO")
@Transactional
public class UnidadSupervisadaDAOImpl implements UnidadSupervisadaDAO{
	
	private static final Logger LOG = LoggerFactory.getLogger(UnidadSupervisadaDAOImpl.class);
	
	@Inject
    private CrudDAO crud;

    @Override
    public List<BusquedaUnidadSupervisadaDTO> buscarUnidadSupervisada(UnidadSupervisadaFilter filtro) {
        LOG.info("entro buscarUnidadSupervisada");
        List<BusquedaUnidadSupervisadaDTO> listaUnidaSupervisadaDTO = null;
        try {
			/* OSINE_MANT_DSHL_003 - Inicio */
			//Query query = getFindQueryUnidadSupervisada(filtro);
            Query query = getFindQueryUnidadSupervisada(filtro,Long.valueOf(Constantes.VALOR_VACIO));
			/* OSINE_MANT_DSHL_003 - Fin */
            listaUnidaSupervisadaDTO = UnidadSupervisadaBuilder.getListUnidadSupervisada(query.getResultList());
        } catch (Exception e) {
            LOG.error("error", e);
        }
        return listaUnidaSupervisadaDTO;
    }
	/* OSINE_MANT_DSHL_003 - Inicio */
    @Override
    public List<BusquedaUnidadSupervisadaDTO> buscarUnidadSupervisadaUsuario(UnidadSupervisadaFilter filtro,Long idUsuario) {
        LOG.info("entro buscarUnidadSupervisada");
        List<BusquedaUnidadSupervisadaDTO> listaUnidaSupervisadaDTO = null;
        try {
            Query query = getFindQueryUnidadSupervisada(filtro,idUsuario);
            listaUnidaSupervisadaDTO = UnidadSupervisadaBuilder.getListUnidadSupervisada(query.getResultList());
        } catch (Exception e) {
            LOG.error("error", e);
        }
        return listaUnidaSupervisadaDTO;
    }
	/* OSINE_MANT_DSHL_003 - Fin */
	
    @SuppressWarnings("unused")
	/* OSINE_MANT_DSHL_003 - Inicio */
	//private Query getFindQueryUnidadSupervisada(UnidadSupervisadaFilter unidadFilter) {
    private Query getFindQueryUnidadSupervisada(UnidadSupervisadaFilter unidadFilter,Long idUsuario) {
	/* OSINE_MANT_DSHL_003 - Fin */	
		String queryString = "";
		StringBuilder jpql = new StringBuilder();		
		jpql.append("SELECT us.idUnidadSupervisada, us.codigoOsinergmin," +
				"(select rhw.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo rhw where rhw.estado=1 "
                        + " and rhw.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada "
                        + " and rhw.estadoProceso IN ("
                            + " select mc.idMaestroColumna "
                            + " FROM MdiMaestroColumna mc "
                            + " where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') "
                            + " and rownum=1 ),"
//                            + "es.razonSocial,"
                            + "'',"
                            + "a.codigo||' - '|| a.nombre,");
		//direccion UO - inicio
                jpql.append(" (select dus.direccionCompleta from MdiDirccionUnidadSuprvisada dus where dus.estado=1"
	                + "and dus.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada ");
                if (!StringUtil.isEmpty(unidadFilter.getCadCodigosTipoDireccion())) {
                    jpql.append( "and dus.idTipoDireccion.idMaestroColumna IN (select mc.idMaestroColumna from MdiMaestroColumna mc "
	                + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
	                + "mc.codigo IN (:cadCodigosTipoDireccion) and rownum=1) ");
                }
                jpql.append( " and rownum=1) as direccionCompleta, ");
	        //direccion UO - fin        
		jpql.append("us.nombreUnidad,"
//                        + "es.ruc,"
                        + "us.ruc,"
                        + "a.idActividad,"
//                        + "es.numeroIdentificacion,es.idEmpresaSupervisada,tdies.descripcion, "
                        + "'','','',"
                        + "a.nombre as nombreActividad ,us.observacion," +
		            "(select rhw.idRegistroHidrocarburo from MdiRegistroHidrocarburo rhw where rhw.estado=1 "
                        + " and rhw.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada "
                        + " and rhw.estadoProceso IN ("
                            + " select mc.idMaestroColumna "
                            + " FROM MdiMaestroColumna mc "
                            + " where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') "
                            + " and rownum=1 )");	
		
		jpql.append(", (select dus.idDirccionUnidadSuprvisada from MdiDirccionUnidadSuprvisada dus where dus.estado=1"
	                + "and dus.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada ");
                if (!StringUtil.isEmpty(unidadFilter.getCadCodigosTipoDireccion())) {
                    jpql.append( "and dus.idTipoDireccion.idMaestroColumna IN (select mc.idMaestroColumna from MdiMaestroColumna mc "
	                + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
	                + "mc.codigo IN (:cadCodigosTipoDireccion) and rownum=1) ");
                }
                jpql.append( " and rownum=1 ) as idDirccionUnidadSuprvisada ");
                
                jpql.append(", (select dus.mdiUbigeo.mdiUbigeoPK.idDepartamento from MdiDirccionUnidadSuprvisada dus where dus.estado=1"
	                + "and dus.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada ");
                if (!StringUtil.isEmpty(unidadFilter.getCadCodigosTipoDireccion())) {
                    jpql.append( "and dus.idTipoDireccion.idMaestroColumna IN (select mc.idMaestroColumna from MdiMaestroColumna mc "
	                + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
	                + "mc.codigo IN (:cadCodigosTipoDireccion) and rownum=1) ");
                }
                jpql.append( " and rownum=1 ) as idDepartamento ");                
                jpql.append(", (select dus.mdiUbigeo.mdiUbigeoPK.idProvincia from MdiDirccionUnidadSuprvisada dus where dus.estado=1"
	                + "and dus.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada ");
                if (!StringUtil.isEmpty(unidadFilter.getCadCodigosTipoDireccion())) {
                    jpql.append( "and dus.idTipoDireccion.idMaestroColumna IN (select mc.idMaestroColumna from MdiMaestroColumna mc "
	                + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
	                + "mc.codigo IN (:cadCodigosTipoDireccion) and rownum=1) ");
                }
                jpql.append( " and rownum=1 ) as idProvincia ");
                jpql.append(", (select dus.mdiUbigeo.mdiUbigeoPK.idDistrito from MdiDirccionUnidadSuprvisada dus where dus.estado=1"
	                + "and dus.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada ");
                if (!StringUtil.isEmpty(unidadFilter.getCadCodigosTipoDireccion())) {
                    jpql.append( "and dus.idTipoDireccion.idMaestroColumna IN (select mc.idMaestroColumna from MdiMaestroColumna mc "
	                + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
	                + "mc.codigo IN (:cadCodigosTipoDireccion) and rownum=1) ");
                }
                jpql.append( " and rownum=1 ) as idDistrito ");
		
                if(!StringUtil.isEmpty(unidadFilter.getFlagEsUnidadMuestral()) && unidadFilter.getFlagEsUnidadMuestral().equals(Constantes.ESTADO_ACTIVO)){
			jpql.append(",(case when tsm.descripcion!=null then tsm.descripcion else '"+unidadFilter.getTipoSeleccion()+"' end) ");
                        jpql.append(",(case when tsm.descripcion!=null then '1' else '0' end) as flagMuestral ");
		}else { 
			jpql.append(",'"+unidadFilter.getTipoSeleccion()+"' ");
                        jpql.append(",'0' as flagMuestral ");
		}			
		jpql.append(" FROM MdiUnidadSupervisada us ");
		jpql.append(" left join us.pghUnidObliSubTipoList usm ");
		jpql.append(" left join usm.tipoSeleccion tsm ");
//		jpql.append(" left join us.idEmpresaSupervisada es ");
//                jpql.append(" left join es.tipoDocumentoIdentidad tdies ");
		jpql.append(" left join us.idActividad a ");
		jpql.append(" where us.estado = '1' ");
		
		if(unidadFilter.getFlagBusqueda()!=null && unidadFilter.getFlagBusqueda().equals(Constantes.ESTADO_ACTIVO)){
			jpql.append("and (" +
					"(usm.idUnidObliSubTipo!=null and usm.estado = '1' and usm.flagSupOrdenServicio='0' ");
			if (unidadFilter.getIdObligacionSubTipo() != null){
				jpql.append(" and usm.idObligacionSubTipo.idObligacionSubTipo = "+unidadFilter.getIdObligacionSubTipo());
			}
			if(unidadFilter.getPeriodo()!=null){
				jpql.append(" and usm.periodo = "+unidadFilter.getPeriodo());
			}
			jpql.append(")");
			jpql.append(" or (usm.idUnidObliSubTipo is null )) ");
		}else{
			if(!StringUtil.isEmpty(unidadFilter.getFlagEsUnidadMuestral()) && unidadFilter.getFlagEsUnidadMuestral().equals(Constantes.ESTADO_ACTIVO)){
				jpql.append(" and (usm.idUnidObliSubTipo!=null and usm.estado = '1' and usm.flagSupOrdenServicio='0' )");
				if (unidadFilter.getIdObligacionSubTipo() != null){
					jpql.append(" and usm.idObligacionSubTipo.idObligacionSubTipo = "+unidadFilter.getIdObligacionSubTipo());
				}
				if(unidadFilter.getPeriodo()!=null){
					jpql.append(" and usm.periodo = "+unidadFilter.getPeriodo());
				}
			}
		}		
	
//		jpql.append(" and es.estado = '1' ");
		
		if (!StringUtil.isEmptyNum(unidadFilter.getActividad())) {
		    jpql.append(" and a.idActividad=:idActividad");
		}
		
		if (!StringUtil.isEmpty(unidadFilter.getIdsBusqueda())) {
		    jpql.append(" and us.idUnidadSupervisada IN ("+unidadFilter.getIdsBusqueda()+")");
		}
		
		if (!StringUtil.isEmpty(unidadFilter.getRegistroHidrocarburos())) {
			jpql.append(" and (select rhw.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo rhw where rhw.estado=1 "
	                + " and rhw.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada "
	                + " and rhw.estadoProceso IN ("
	                    + " select mc.idMaestroColumna "
	                    + " FROM MdiMaestroColumna mc "
	                    + " where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') "
	                + " and rownum=1 ) like :registroHidrocarburos ");   
		}
		
		if (!StringUtil.isEmpty(unidadFilter.getCadCodigoOsinerg())) {
		    jpql.append(" and  us.codigoOsinergmin IN ("+unidadFilter.getCadCodigoOsinerg()+")");
		}
//		if (!StringUtil.isEmpty(unidadFilter.getNroDoc())) {
//		    jpql.append(" and es.tipoDocumentoIdentidad=" + unidadFilter.getNroDoc());
//		    jpql.append(" and es.numeroIdentificacion=:numeroIdentificacion ");
//		}
		if (!StringUtil.isEmpty((String) unidadFilter.getRazonSocial())) {
//		    jpql.append(" and  upper(es.razonSocial) like :razonSocial ");
                    jpql.append(" and  upper(us.nombreUnidad) like :razonSocial ");
		}
		
		if (!StringUtil.isEmpty(unidadFilter.getIdDepartamento()) && unidadFilter.getIdDepartamento() != "-1") {
			jpql.append(" and (select COUNT(*) from MdiDirccionUnidadSuprvisada dd where dd.estado=1"
	                + "and dd.idUnidadSupervisada.idUnidadSupervisada=us.idUnidadSupervisada "
	                + "and dd.idTipoDireccion IN  (select mc.idMaestroColumna from MdiMaestroColumna mc "
	                + "where mc.dominio='"+Constantes.DOMINIO_TIPO_DIRECCION+"' and "
	                + "mc.codigo IN (select mc1.codigo from MdiMaestroColumna mc1 "
	                + "where mc1.dominio='"+Constantes.DOMINIO_DIRE_INPS_UO+"') and rownum=1) "
	                + "and dd.mdiUbigeo.mdiUbigeoPK.idDepartamento =:idDepartamento ");
		    		if (!StringUtil.isEmpty(unidadFilter.getIdProvincia()) && unidadFilter.getIdProvincia() != "-1") {
	                	jpql.append(" and dd.mdiUbigeo.mdiUbigeoPK.idProvincia =:idProvincia ");
	                }
	                if (!StringUtil.isEmpty(unidadFilter.getIdDistrito()) && unidadFilter.getIdDistrito() != "-1") {
	                	jpql.append(" and dd.mdiUbigeo.mdiUbigeoPK.idDistrito =:idDistrito ");
	                }
	        jpql.append(") > 0");
		}
		
		if (!StringUtil.isEmpty((String) unidadFilter.getNombreUnidad())) {
		    jpql.append(" and upper(us.nombreUnidad) like :nombreUnidad ");
		}
		if (!StringUtil.isEmpty(unidadFilter.getDireccionOperativa())) {
		    jpql.append(" and upper(dd.direccionCompleta) like :direccion ");
		}
		if (!StringUtil.isEmpty(unidadFilter.getCadIdUnidadSupervisada())) {
		    jpql.append(" and us.idUnidadSupervisada in ( " + unidadFilter.getCadIdUnidadSupervisada() + ")");
		}	
		if (unidadFilter.getIdEmpresaSupervisada() != null) {
		    jpql.append(" and us.idEmpresaSupervisada.idEmpresaSupervisada = "+unidadFilter.getIdEmpresaSupervisada());
		}
		
		/* OSINE_MAN_DSHL-003 - Inicio */
		//Mostrar Unidades Operativas con RHO "VIGENTE" (id_Maestro_Columna=estadoProceso=1104)
		jpql.append(" and us.idUnidadSupervisada IN (select rhw.idUnidadSupervisada from MdiRegistroHidrocarburo rhw where rhw.estado=1 "
                        + " and rhw.estadoProceso IN ( "
                            + " select mc.idMaestroColumna "
                            + " FROM MdiMaestroColumna mc "
                            + " where mc.aplicacion ='"+ Constantes.APLICACION_SIGUO +"' and mc.dominio='"+ Constantes.DOMINIO_ESTADO_RHO +"' and mc.codigo='"+Constantes.DOMINIO_ESTADO_RHO_CODIGO_ACTIVO+"') )  ");
		
		//Mostrar Unidades Operativas de actvidades relacionadas al usuario
		if(idUsuario>Long.valueOf(Constantes.VALOR_VACIO)){
			jpql.append(" and us.idActividad IN ( select au.idActividad from PghCnfActUniOrganica au where au.estado=1 and au.idUnidadOrganica " 
					+ " IN (  "
					+ " select s.idUnidadOrganicaSuperior from MdiUnidadOrganica s where s.idUnidadOrganica IN ( "
					+ " select u.idUnidadOrganicaSuperior from PghPersonalUnidadOrganica p  "
					+ " inner join p.idUnidadOrganica u "
					+ " where p.idPersonal="+String.valueOf(idUsuario)+" ) ) )  ");	
		}
		/* OSINE_MAN_DSHL-003 - Fin */
		
		jpql.append(" order by us.fechaCreacion desc ");
		
		LOG.info("query unidad Supervisada: " + queryString);
		Query query = crud.getEm().createQuery(jpql.toString());
		
		if (!StringUtil.isEmptyNum(unidadFilter.getActividad())) {
		    query.setParameter("idActividad", unidadFilter.getActividad());
		}
		
		if (!StringUtil.isEmpty(unidadFilter.getRegistroHidrocarburos())) {
		    query.setParameter("registroHidrocarburos", "%" +unidadFilter.getRegistroHidrocarburos()+"%");
		}
		if (!StringUtil.isEmpty(unidadFilter.getRazonSocial())) {
		    query.setParameter("razonSocial", "%" + unidadFilter.getRazonSocial().toUpperCase() + "%");
		}
		if (!StringUtil.isEmpty(unidadFilter.getDireccionOperativa())) {
		    query.setParameter("direccion", "%" + unidadFilter.getDireccionOperativa().toUpperCase() + "%");
		}
		if (!StringUtil.isEmpty(unidadFilter.getCadCodigosTipoDireccion())) {
			query.setParameter("cadCodigosTipoDireccion", unidadFilter.getCadCodigosTipoDireccion());
		}
		if (!StringUtil.isEmpty(unidadFilter.getNombreUnidad())) {
		    query.setParameter("nombreUnidad", unidadFilter.getNombreUnidad());
		}
//		if (!StringUtil.isEmpty(unidadFilter.getNroDoc())) {
//		    query.setParameter("tipoDocumentoIdentidad", unidadFilter.getTipoDoc());
//		}
		if (!StringUtil.isEmpty(unidadFilter.getIdDepartamento()) && unidadFilter.getIdDepartamento() != "-1") {
		    query.setParameter("idDepartamento", unidadFilter.getIdDepartamento());
		}
		if (!StringUtil.isEmpty(unidadFilter.getIdDistrito()) && unidadFilter.getIdDistrito() != "-1") {
		    query.setParameter("idDistrito", unidadFilter.getIdDistrito());
		}
		if (!StringUtil.isEmpty(unidadFilter.getIdProvincia()) && unidadFilter.getIdProvincia() != "-1") {
		    query.setParameter("idProvincia", unidadFilter.getIdProvincia());
		}
		return query;
	}

    @Override
    public List<UnidadSupervisadaDTO> getUnidadSupervisadaDTO(UnidadSupervisadaFilter filtro) {
       LOG.info("getUnidadSupervisadaDTO: find-inicio");
        List<UnidadSupervisadaDTO> retorno = null;
        try {
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT distinct new MdiUnidadSupervisada ");
            jpql.append(" ( ");
            jpql.append(" uniSup.idUnidadSupervisada , uniSup.nombreUnidad , uniSup.codigoOsinergmin , uniSup.direccion , uniSup.estado , ");
//            jpql.append(" emSup.idEmpresaSupervisada , emSup.razonSocial , emSup.numeroIdentificacion , emSup.nombreComercial , emSup.telefono , emSup.ruc , emSup.correoElectronico ,   ");
            jpql.append(" uniSup.nombreComercial ,uniSup.ruc, ");
            jpql.append(" actSup.idActividad ,actSup.nombre ,actSup.codigo, ");
            jpql.append(" (select distinct rh.numeroRegistroHidrocarburo from MdiRegistroHidrocarburo rh where rh.idUnidadSupervisada.idUnidadSupervisada = uniSup.idUnidadSupervisada and rh.estado = :estadoActivo and rownum = 1 ) as numeroRegistroHidrocarburo ");
//            jpql.append(" mc.idMaestroColumna,mc.codigo,mc.descripcion ");
            jpql.append(" ) ");
            jpql.append(" FROM MdiUnidadSupervisada uniSup ");            
            jpql.append(" LEFT JOIN uniSup.idActividad actSup ");
//            jpql.append(" LEFT JOIN uniSup.idEmpresaSupervisada emSup ");
//            jpql.append(" LEFT JOIN emSup.tipoDocumentoIdentidad mc ");
            /* OSINE791 - RSIS21 - Fin */
            jpql.append(" WHERE uniSup.estado =:estadoActivo ");
           // jpql.append(" AND emSup.estado =:estadoActivo ");
           // jpql.append(" AND actSup.estado =:estadoActivo ");
            //jpql.append(" AND mc.estado =:estadoActivo ");
            if (filtro.getIdUnidadSupervisada()!= null) {
                jpql.append(" AND uniSup.idUnidadSupervisada =:idUnidadSupervisada ");
            }
            /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */
            if (filtro.getCodigoOsinerg()!= null) {
                jpql.append(" AND uniSup.codigoOsinergmin =:codigoOsinergmin ");
            }
            /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createQuery(queryString);
            //settear parametros
            query.setParameter("estadoActivo", Constantes.ESTADO_ACTIVO);
            if (filtro.getIdUnidadSupervisada() != null) {
                query.setParameter("idUnidadSupervisada", filtro.getIdUnidadSupervisada());
            }
            /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */
            if (filtro.getCodigoOsinerg() != null) {
                query.setParameter("codigoOsinergmin", filtro.getCodigoOsinerg());
            }
            /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */
            retorno = UnidadSupervisadaBuilder.toListUnidadSupervisadaDTO(query.getResultList());
        } catch (Exception e) {
            LOG.error("error en find", e);
        }
        LOG.info("getUnidadSupervisadaDTO: find-fin");
        return retorno; 
    }

}
/* OSINE_SFS-480 - RSIS 26 - Fin */

