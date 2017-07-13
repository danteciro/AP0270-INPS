/**
* Resumen		
* Objeto		: DatoPlantillaDAOImpl.java
* Descripción		: Clase que contiene la implementación de los métodos declarados en la clase interfaz DatoPlantillaDAO
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     21/05/2016      Julio Piro Gonzales         Insertar imágenes de medios probatorios en plantillas de Informe de Supervisión
*
*/

package gob.osinergmin.inpsweb.service.dao.impl;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import gob.osinergmin.inpsweb.domain.builder.PghDocumentoAdjuntoBuilder;
import gob.osinergmin.inpsweb.dto.BombaIncendioDTO;
/* OSINE_SFS-480 - RSIS 01 - Fin */
import gob.osinergmin.inpsweb.dto.DatoPlantillaDTO;
import gob.osinergmin.inpsweb.dto.TanqueDTO;
import gob.osinergmin.inpsweb.dto.builder.DatoPlantillaBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.DatoPlantillaDAO;
import gob.osinergmin.inpsweb.service.exception.DatoPlantillaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.mdicommon.domain.dto.AlmacenamientoAguaDTO;
import gob.osinergmin.mdicommon.domain.dto.BaseLegalDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.TipoSancionDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import org.springframework.transaction.annotation.Transactional;
/* OSINE_SFS-480 - RSIS 01 - Fin */

/**
 *
 * @author jpiro
 */
@Service("DatoPlantillaDAO")
public class DatoPlantillaDAOImpl implements DatoPlantillaDAO {
    private static final Logger LOG = LoggerFactory.getLogger(DatoPlantillaDAOImpl.class);
    @Inject
    private CrudDAO crud;
    
    @Override
    public List<DetalleSupervisionDTO> obtenerDetalleSupervision(DetalleSupervisionFilter filtro) throws DatoPlantillaException{
    	List<DetalleSupervisionDTO> listaHallazgosRetorno = new ArrayList<DetalleSupervisionDTO>();
    	List<DetalleSupervisionDTO> listaHallazgosSupervision = obtenerDetalleSupervisionPlantilla(filtro);
        List<DetalleSupervisionDTO> listaHallazgosBaseLegalSupervision = obtenerBaseLegalHallazgosSupervisionPlantilla(filtro.getIdSupervision());
        int contador=0;
        for(DetalleSupervisionDTO hallazgo:listaHallazgosSupervision){            
        	contador++;
        	hallazgo.setIndice(contador);
        	List<BaseLegalDTO> listaBaseLegal = new ArrayList<BaseLegalDTO>();
        	for(DetalleSupervisionDTO hallazgoBaseLegal : listaHallazgosBaseLegalSupervision){
        		BaseLegalDTO baseLegal = new BaseLegalDTO();
        		if(hallazgoBaseLegal.getIdDetalleSupervision().equals(hallazgo.getIdDetalleSupervision())){
        			baseLegal.setIdBaseLegal(hallazgoBaseLegal.getIdBaseLegal());
        			baseLegal.setDescripcionGeneralBaseLegal(hallazgoBaseLegal.getDescripcionBaseLegal());
        			listaBaseLegal.add(baseLegal);        			
        		}
        	}
        	if(hallazgo.getTipificacion()!=null && hallazgo.getTipificacion().getIdTipificacion()!=null){
        		hallazgo.getTipificacion().setOtrasSanciones(obtenerSancionesAplicables(hallazgo.getTipificacion().getIdTipificacion()));
        	}
        	hallazgo.setListaBaseLegal(listaBaseLegal);
        	listaHallazgosRetorno.add(hallazgo);
        }
        return listaHallazgosRetorno;
    }
    
    public List<DetalleSupervisionDTO> obtenerDetalleSupervisionPlantilla(DetalleSupervisionFilter filtro) {
        LOG.info("obtenerDetalleSupervisionPlantilla");
        List<DetalleSupervisionDTO> resultado=null;
        try{
        	String queryString;
            StringBuilder jpql = new StringBuilder();
             jpql.append("SELECT " 
            	//+ "DSUP.ID_DETALLE_SUPERVISION ,DSUP.DESCRIPCION_RESULTADO,DSUP.FLAG_RESULTADO, " 
            	+ "DSUP.ID_DETALLE_SUPERVISION, DE.DESCRIPCION_RESULTADO, DE.FLAG_RESULTADO, "	// mdioses - RSIS5	 
            	+ "OBLI.ID_OBLIGACION, OBLI.DESCRIPCION, " 
            	+ "TIPI.ID_TIPIFICACION, TIPI.COD_TIPIFICACION, TIPI.DESCRIPCION AS DESCRIPCIONTIPI, TIPI.SANCION_MONETARIA, "
            	+ "DSUPANT.ID_DETALLE_SUPERVISION AS DETASUPANT, DEANT.DESCRIPCION_RESULTADO AS DESRESUANT " 
            	+ "FROM PGH_SUPERVISION SUP  "
            	+ "LEFT JOIN PGH_DETALLE_SUPERVISION DSUP ON SUP.ID_SUPERVISION = DSUP.ID_SUPERVISION "
            	+ "LEFT JOIN PGH_OBLIGACION OBLI ON DSUP.ID_OBLIGACION = OBLI.ID_OBLIGACION "
            	+ "LEFT JOIN PGH_DETALLE_EVALUACION DE ON DSUP.ID_DETALLE_SUPERVISION = DE.ID_DETALLE_SUPERVISION AND DSUP.FLAG_RESULTADO = DE.FLAG_RESULTADO " // mdioses - RSIS5	
            	+ "LEFT JOIN PGH_TIPIFICACION TIPI ON DE.ID_TIPIFICACION = TIPI.ID_TIPIFICACION AND TIPI.ESTADO = 1 "
            	+ "LEFT JOIN PGH_DETALLE_SUPERVISION DSUPANT ON DSUP.ID_DETALLE_SUPERVISION_ANT = DSUPANT.ID_DETALLE_SUPERVISION "            	
            	+ "LEFT JOIN PGH_DETALLE_EVALUACION DEANT ON DSUPANT.ID_DETALLE_SUPERVISION = DEANT.ID_DETALLE_SUPERVISION " // mdioses - RSIS5	
            	+ "AND DSUPANT.FLAG_RESULTADO = DEANT.FLAG_RESULTADO " // mdioses - RSIS5	
            	+ "AND (DEANT.ID_TIPIFICACION = DE.ID_TIPIFICACION OR DEANT.ID_TIPIFICACION IS NULL) " // mdioses - RSIS5	
                + "AND (DEANT.ID_CRITERIO = DE.ID_CRITERIO OR DEANT.ID_CRITERIO IS NULL) "); // mdioses - RSIS5	
            jpql.append(" WHERE SUP.ESTADO = 1 ");
            jpql.append(" AND DSUP.ESTADO = 1 ");
            jpql.append(" AND OBLI.ESTADO = 1 ");
            if(filtro.getFlagResultado()!=null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagResultado().trim())){
            	jpql.append(" AND DSUP.FLAG_RESULTADO = :flagResultado ");
            }
            if(filtro.getIdSupervision()!=null){
            	jpql.append(" AND SUP.ID_SUPERVISION = :idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            if(filtro.getFlagResultado()!=null && !Constantes.CONSTANTE_VACIA.equals(filtro.getFlagResultado().trim())){
            	query.setParameter("flagResultado",filtro.getFlagResultado());
            }
            if(filtro.getIdSupervision()!=null){
            	query.setParameter("idSupervision",filtro.getIdSupervision());
            }
            List<Object[]> lista = query.getResultList();
            resultado= DatoPlantillaBuilder.toListPlantillaResultadoSupervisionHallazgosDto(lista);
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return resultado;
    }
    
    public List<DetalleSupervisionDTO> obtenerBaseLegalHallazgosSupervisionPlantilla(Long idSupervision) {
        LOG.info("obtenerBaseLegalHallazgosSupervisionPlantilla");
        List<DetalleSupervisionDTO> resultado=null;
        try{
        	String queryString;
            StringBuilder jpql = new StringBuilder();
             jpql.append("SELECT " 
            	+ "DSUP.ID_DETALLE_SUPERVISION,DSUP.FLAG_RESULTADO ,BL.ID_BASE_LEGAL, BL.DESCRIPCION " 
            	+ "FROM PGH_SUPERVISION SUP  "
            	+ "LEFT JOIN PGH_DETALLE_SUPERVISION DSUP ON SUP.ID_SUPERVISION = DSUP.ID_SUPERVISION "
            	+ "LEFT JOIN PGH_OBLIGACION OBL ON DSUP.ID_OBLIGACION = OBL.ID_OBLIGACION "
            	+ "LEFT JOIN PGH_OBLIGACION_BASE_LEGAL OBBL ON OBL.ID_OBLIGACION = OBBL.ID_OBLIGACION "
            	+ "LEFT JOIN PGH_BASE_LEGAL BL ON OBBL.ID_BASE_LEGAL = BL.ID_BASE_LEGAL ");
            //where
            jpql.append(" WHERE SUP.ESTADO = 1 ");
            jpql.append(" AND DSUP.ESTADO = 1 ");
            jpql.append(" AND OBL.ESTADO = 1 ");            
            jpql.append(" AND OBBL.ESTADO = 1 ");
            jpql.append(" AND BL.ESTADO = 1 ");
            //parametro id
            if(idSupervision!=null){
            jpql.append(" AND SUP.ID_SUPERVISION = :idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            //settear parametros
            query.setParameter("idSupervision",idSupervision);
            List<Object[]> lista = query.getResultList();
            resultado= DatoPlantillaBuilder.toListPlantillaResultadoSupervisionHallazgosBaseLegalDto(lista);
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return resultado;
    }
    
    public String obtenerSancionesAplicables(Long idTipificacion){
    	String resultado = "";
    	List<TipoSancionDTO> listaTipoSancion= new ArrayList<TipoSancionDTO>();
    	try {
    		String queryString;
            StringBuilder jpql = new StringBuilder();
             jpql.append("SELECT " 
            	+ "TSAN.ID_TIPO_SANCION,TSAN.ABREVIATURA " 
            	+ "FROM PGH_TIPO_SANCION TSAN "
            	+ "LEFT JOIN PGH_TIPIFICACION_SANCION TISAN ON TSAN.ID_TIPO_SANCION = TISAN.ID_TIPO_SANCION ");
            jpql.append(" WHERE TSAN.ESTADO= 1 ");
            jpql.append(" AND TISAN.ESTADO = 1 ");
            jpql.append(" AND TISAN.NIVEL = (SELECT M.ID_MAESTRO_COLUMNA FROM MDI_MAESTRO_COLUMNA M WHERE M.APLICACION='MYC' AND M.DOMINIO='NIVEL_TIPIFICACION' AND M.ESTADO='1') ");
            //parametro id
            if(idTipificacion!=null){
            jpql.append(" AND TISAN.ID_TIPIFICACION = :idTipificacion ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            //settear parametros
            if(idTipificacion!=null){
            	query.setParameter("idTipificacion",idTipificacion);
            }
            List<Object[]> lista = query.getResultList();
            listaTipoSancion= DatoPlantillaBuilder.toListPlantillaResultadoTipoSancionDto(lista);
            if(!listaTipoSancion.isEmpty()){
            	for(TipoSancionDTO tipoSancion:listaTipoSancion){
            		resultado+=tipoSancion.getAbreviatura()+",";
            	}
            	if(!resultado.isEmpty()){
            		resultado = resultado.substring(0, (resultado.length()-1));
            	}
            	LOG.info(resultado);            	
            }
		} catch (Exception e) {
			LOG.error("error en obtenerSancionesAplicables",e);
            e.printStackTrace();
		}
    	return resultado;
    }
    
    @Override
    public List<DocumentoAdjuntoDTO> obtenerDocumentoSupervisionPlantilla(Long idSupervision) throws DatoPlantillaException{
        LOG.info("obtenerDocumentoSupervisionPlantilla");
        List<DocumentoAdjuntoDTO> resultado=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
             jpql.append("SELECT " 
            	+ "ADJ.DESCRIPCION_DOCUMENTO,ADJ.NOMBRE_DOCUMENTO, CONCAT(CONCAT(CONCAT(ADJ.DESCRIPCION_DOCUMENTO,' ('),ADJ.NOMBRE_DOCUMENTO),')') AS RESUMEN " 
            	+ "FROM PGH_SUPERVISION SUP "
            	+ "LEFT JOIN PGH_DOCUMENTO_ADJUNTO ADJ ON SUP.ID_SUPERVISION = ADJ.ID_SUPERVISION " );
            //where
            jpql.append(" WHERE SUP.ESTADO = 1 ");
            jpql.append(" AND ADJ.ESTADO = 1 ");
            jpql.append(" AND ADJ.ID_DETALLE_SUPERVISION IS NULL ");
            //parametro id
            if(idSupervision!=null){
            jpql.append(" AND SUP.ID_SUPERVISION = :idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            //settear parametros
            query.setParameter("idSupervision",idSupervision);
            List<Object[]> lista = query.getResultList();
            resultado = DatoPlantillaBuilder.toListPlantillaResultadoSupervisionDocumentosDto(lista);
        }catch(Exception e){
            LOG.error("error en find",e);
            e.printStackTrace();
        }
        return resultado;
    }
    
    @Override
    public List<AlmacenamientoAguaDTO> listarAlmacenamientoAgua(Long idUnidadSupervisada) {
    	List<AlmacenamientoAguaDTO> listado = new ArrayList<AlmacenamientoAguaDTO>();
    	Query query = null;
    	try{
    		if(idUnidadSupervisada != null){
    			StringBuilder queryBuilder = new StringBuilder();
    			queryBuilder.append("select ");
    			queryBuilder.append("a.id_almacenamiento_agua,");
    			queryBuilder.append("a.capacidad_almacenamiento ");
    			queryBuilder.append("from ");
    			queryBuilder.append("pgh_almacenamiento_agua a ");
    			queryBuilder.append("where ");
    			queryBuilder.append("a.estado = '1' ");
    			queryBuilder.append("and a.id_unidad_supervisada = :idUnidadSupervisada ");
    			queryBuilder.append("order by a.id_almacenamiento_agua");
    			query = crud.getEm().createNativeQuery(queryBuilder.toString());
    			query.setParameter("idUnidadSupervisada", idUnidadSupervisada);
    			List<Object[]> listObjects = query.getResultList();
    			for (Object[] object : listObjects) {
    				AlmacenamientoAguaDTO almacen = new AlmacenamientoAguaDTO();
    				almacen.setIdAlmacenamientoAgua(Long.parseLong(object[0].toString()));
    				Double capacidad = Double.parseDouble(object[1] == null ? "0" : object[1].toString());
    				almacen.setCapacidadAlmacenamiento(BigDecimal.valueOf(capacidad));
    				listado.add(almacen);
				}
    		}
    	}catch(Exception ex){
    		LOG.error("Error en consultar listarAlmacenamientoAgua:" + ex.getMessage());
    	}
    	return listado;
    }
    
    @Override
    public List<BombaIncendioDTO> obtenerBombasParaPlantilla(Long idUnidadSupervisada) {
    	List<BombaIncendioDTO> listado = new ArrayList<BombaIncendioDTO>();
    	Query query = null;
    	try
    	{
    		if(idUnidadSupervisada != null){
    			StringBuilder queryBuilder = new StringBuilder();
    			queryBuilder.append("select ");
    			queryBuilder.append("b.caudal_nominal,");
    			queryBuilder.append("(select mc.descripcion from mdi_maestro_columna mc where mc.id_maestro_columna = b.id_tipo_motor and mc.estado = '1') ");
    			queryBuilder.append("from ");
    			queryBuilder.append("pgh_bomba_incendio b ");
    			queryBuilder.append("where ");
    			queryBuilder.append("b.id_unidad_supervisada = :idUnidadSupervisada ");
    			queryBuilder.append("and b.estado = '1'");
    			
    			query = crud.getEm().createNativeQuery(queryBuilder.toString());
    			query.setParameter("idUnidadSupervisada", idUnidadSupervisada);
    			List<Object[]> listaTanques = query.getResultList();
    			for (Object[] tanque : listaTanques) {
					BombaIncendioDTO bomba = new BombaIncendioDTO();
					bomba.setCapacidadNominal(tanque[0] == null ? 0 : Long.parseLong(tanque[0].toString()));
					bomba.setDescripcionTipoMotor(tanque[1] == null ? "" : tanque[1].toString());
					listado.add(bomba);
				}
    		}
    	}catch(Exception ex){
    		LOG.error("Error obtenerBombasParaPlantilla: "+ex.getMessage());
    	}
    	return listado;
    }
    
    @Override
    public List<TanqueDTO> obtenerTanquesParaPlantilla(Long idUnidadSupervisada){
    	List<TanqueDTO> listado=new ArrayList<TanqueDTO>();
    	Query query=null;        
		try{
			if(idUnidadSupervisada != null){
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("select ");
				queryBuilder.append("t.numero,");
				queryBuilder.append("(select mc.descripcion from mdi_maestro_columna mc where mc.id_maestro_columna = t.id_tipo_instalacion and mc.estado = '1'),");
				queryBuilder.append("t.capacidad_nominal,");
				queryBuilder.append("t.serie ");
				queryBuilder.append("from pgh_tanque t ");
				queryBuilder.append("where ");
				queryBuilder.append("t.id_unidad_supervisada = :idUnidadSupervisada ");
				queryBuilder.append("and t.estado = '1' ");
				queryBuilder.append("order by t.numero asc");
	            query = crud.getEm().createNativeQuery(queryBuilder.toString());
		        
	            query.setParameter("idUnidadSupervisada",idUnidadSupervisada);
		        
		        List<Object[]> listaTanques = query.getResultList();
		        for (Object[] tanque : listaTanques) {
		        	TanqueDTO nuevoTanque = new TanqueDTO();
					nuevoTanque.setNumeroTanque(tanque[0] == null ? 0 :Long.parseLong(tanque[0].toString()));
					nuevoTanque.setDescripcionTipoInstalacion(tanque[1] == null ? "" : tanque[1].toString());
					nuevoTanque.setCapacidadNominal(tanque[2] == null ? 0 : Long.parseLong(tanque[2].toString()));
					nuevoTanque.setSerie(tanque[3] == null ? "" : tanque[3].toString());
					listado.add(nuevoTanque);
				}
			}
		}catch(Exception e){
			LOG.error("Error obtenerTanquesParaPlantilla: "+e.getMessage());
		}		
    	return listado;
    }
    
    @Override
    public DatoPlantillaDTO obtenerDatosPlantilla(Long idSupervision) throws DatoPlantillaException{
        LOG.info("obtener datos plantilla--------idSupervision-------->"+idSupervision);
        DatoPlantillaDTO retorno=null;
        List<DatoPlantillaDTO> resultado=null;
        try{
            String queryString;
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT SUP.ID_SUPERVISION, OS.ID_ORDEN_SERVICIO, " 
            	+ "OS.NUMERO_ORDEN_SERVICIO, PR.DESCRIPCION AS DESCPROCESO,"
            	
				
            	+ "UD.DESCRIPCION AS DESCDIVISION, "
            	+ "UO.DESCRIPCION AS DESCUNIDADORGANICA, "
            	
            	+ "ACT.NOMBRE, "
//                + "ES.RAZON_SOCIAL, ES.ID_EMPRESA_SUPERVISADA, "
                + "US.NOMBRE_UNIDAD, '', "
                + "US.ID_UNIDAD_SUPERVISADA, " 
            	+ "US.CODIGO_OSINERGMIN, EX.NUMERO_EXPEDIENTE, "
            	//"TO_CHAR(SUP.FECHA_INICIO, 'fmDD')||' de '||TO_CHAR(SUP.FECHA_INICIO, 'Month')||' de '||TO_CHAR(SUP.FECHA_INICIO, 'YYYY') AS FECHINICIOSUP , " 
            	+ "TO_CHAR(SUP.FECHA_INICIO, 'DD/MM/YYYY') AS FECHINICIOSUP,TO_CHAR(SUP.FECHA_FIN, 'DD/MM/YYYY')AS FECHFINSUP , "//"TO_CHAR(SUP.FECHA_FIN, 'fmDD')||' de '||TO_CHAR(SUP.FECHA_FIN, 'Month')||' de '||TO_CHAR(SUP.FECHA_FIN, 'YYYY') AS FECHFINSUP , "  
            	+ "DUS.DIRECCION_COMPLETA, UDEP.NOMBRE AS NOMBREDEPARTAMENTO, UPRO.NOMBRE AS NOMBREPROVINCIA , UDIS.NOMBRE AS NOMBREDISTRITO ,ER.CARGO ,ER.NOMBRE_COMPLETO," 
            	+ "MSE.ID_SUPERVISORA_EMPRESA, MSE.RAZON_SOCIAL AS RAZONSUPERVISORA ,ML.ID_LOCADOR, ML.NOMBRE_COMPLETO AS NOMBRELOCADOR, SUP.CARTA_VISITA," 
            	+ "MRH.NUMERO_REGISTRO_HIDROCARBURO,"
//                + "ES.RUC, "
                + "US.RUC, "
            	+ "(SELECT MCS.DESCRIPCION FROM MDI_MAESTRO_COLUMNA MCS WHERE MCS.ESTADO = '1' AND MCS.ID_MAESTRO_COLUMNA = PSPG.CARGO) AS CARGOSUPERVISION, "
                + "(PPG.NOMBRES_PERSONA ||' '||PPG.APELLIDO_PATERNO_PERSONA||' '||PPG.APELLIDO_MATERNO_PERSONA ) AS NOMBREPERSONAATIENDE, "
                + "MNS.DESCRIPCION MOTIVONOSUPERVISION "
                + "FROM PGH_SUPERVISION SUP "
                + "LEFT JOIN PGH_SUPERVISION_PERSONA_GRAL PSPG ON SUP.ID_SUPERVISION = PSPG.ID_SUPERVISION AND PSPG.ESTADO = '1' "
                + "LEFT JOIN PGH_PERSONA_GENERAL PPG ON PSPG.ID_PERSONA_GENERAL = PPG.ID_PERSONA_GENERAL AND PPG.ESTADO = '1'"
            	+ "LEFT JOIN PGH_ORDEN_SERVICIO OS ON SUP.ID_ORDEN_SERVICIO = OS.ID_ORDEN_SERVICIO AND OS.ESTADO = 1 "
                + "LEFT JOIN PGH_MOTIVO_NO_SUPERVISION MNS  ON SUP.ID_MOTIVO_NO_SUPERVISION=MNS.ID_MOTIVO_NO_SUPERVISION AND MNS.ESTADO = '1' "
                + "LEFT JOIN MDI_SUPERVISORA_EMPRESA MSE ON OS.ID_SUPERVISORA_EMPRESA = MSE.ID_SUPERVISORA_EMPRESA "
            	+ "LEFT JOIN MDI_LOCADOR ML ON OS.ID_LOCADOR = ML.ID_LOCADOR "
                + "LEFT JOIN PGH_EXPEDIENTE EX ON OS.ID_EXPEDIENTE = EX.ID_EXPEDIENTE AND EX.ESTADO = 1 "
            	+ "LEFT JOIN PGH_PROCESO PR ON EX.ID_PROCESO = PR.ID_PROCESO AND PR.ESTADO = 1 "
                + "LEFT JOIN PGH_PERSONAL PE ON EX.ID_PERSONAL = PE.ID_PERSONAL AND PE.ESTADO = 1 "
            	//+ "LEFT JOIN MDI_UNIDAD_ORGANICA UO ON PE.ID_UNIDAD_ORGANICA = UO.ID_UNIDAD_ORGANICA AND UO.ESTADO = 1 "                
              	+ "LEFT JOIN PGH_PERSONAL_UNIDAD_ORGANICA PUO ON PUO.ID_PERSONAL = PE.ID_PERSONAL AND PUO.FLAG_DEFAULT = 1 " //mdiosesf - RSIS5
              	+ "LEFT JOIN MDI_UNIDAD_ORGANICA UO ON UO.ID_UNIDAD_ORGANICA = PUO.ID_UNIDAD_ORGANICA " //mdiosesf - RSIS5
                + "LEFT JOIN MDI_UNIDAD_ORGANICA UD ON UO.ID_UNIDAD_ORGANICA_SUPERIOR = UD.ID_UNIDAD_ORGANICA AND UD.ESTADO = 1 "
                + "LEFT JOIN PGH_CNF_ACT_UNI_ORGANICA CUO ON UO.ID_UNIDAD_ORGANICA = CUO.ID_UNIDAD_ORGANICA AND CUO.ESTADO = 1 "                
//            	+ "LEFT JOIN MDI_EMPRESA_SUPERVISADA ES ON EX.ID_EMPRESA_SUPERVISADA = ES.ID_EMPRESA_SUPERVISADA AND ES.ESTADO = 1 "
                + "LEFT JOIN MDI_UNIDAD_SUPERVISADA US ON EX.ID_UNIDAD_SUPERVISADA = US.ID_UNIDAD_SUPERVISADA AND US.ESTADO = 1 "
                + "LEFT JOIN MDI_ACTIVIDAD ACT ON US.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD "
            	+ "LEFT JOIN MDI_REGISTRO_HIDROCARBURO MRH ON US.ID_UNIDAD_SUPERVISADA = MRH.ID_UNIDAD_SUPERVISADA AND MRH.ESTADO = 1 "
                //cambiando la direccion unidad supervisada para que traiga la direccion de instalacion en vez de la operativa
            	//+ "LEFT JOIN MDI_DIRCCION_UNIDAD_SUPRVISADA DUS ON US.ID_UNIDAD_SUPERVISADA = DUS.ID_UNIDAD_SUPERVISADA AND DUS.ESTADO = 1 AND DUS.ID_TIPO_DIRECCION IN (SELECT M.ID_MAESTRO_COLUMNA FROM MDI_MAESTRO_COLUMNA M WHERE M.ESTADO = 1 AND M.CODIGO = '1' AND M.DOMINIO = 'TIPO_DIRE_UNIDAD' AND M.APLICACION = 'INPS') "
            	+ "LEFT JOIN MDI_DIRCCION_UNIDAD_SUPRVISADA DUS ON US.ID_UNIDAD_SUPERVISADA = DUS.ID_UNIDAD_SUPERVISADA AND DUS.ESTADO = 1 AND DUS.ID_TIPO_DIRECCION = 1127 AND ROWNUM = 1"
            	+ "LEFT JOIN MDI_UBIGEO UDEP ON DUS.ID_DEPARTAMENTO = UDEP.ID_DEPARTAMENTO AND UDEP.ID_DISTRITO = '00' AND UDEP.ID_PROVINCIA = '00' "
                + "LEFT JOIN MDI_UBIGEO UPRO ON DUS.ID_PROVINCIA = UPRO.ID_PROVINCIA AND UPRO.ID_DEPARTAMENTO = DUS.ID_DEPARTAMENTO AND UPRO.ID_DISTRITO = '00' "
            	+ "LEFT JOIN MDI_UBIGEO UDIS ON DUS.ID_DISTRITO = UDIS.ID_DISTRITO AND UDIS.ID_DEPARTAMENTO = DUS.ID_DEPARTAMENTO AND UDIS.ID_PROVINCIA = DUS.ID_PROVINCIA "
//                + "LEFT JOIN MDI_EMPRESA_CONTACTO ER ON ES.ID_EMPRESA_SUPERVISADA = ER.ID_EMPRESA_SUPERVISADA  AND ER.ESTADO = 1 AND ER.ID_TIPO_CONTACTO IN (SELECT M.ID_MAESTRO_COLUMNA FROM MDI_MAESTRO_COLUMNA M WHERE M.ESTADO = 1 AND M.CODIGO = 'LEGAL' AND M.DOMINIO = 'TIPO_CONTACTO' AND M.APLICACION = 'SGLSS') "
                + "LEFT JOIN MDI_EMPRESA_CONTACTO ER ON US.ID_UNIDAD_SUPERVISADA = ER.ID_UNIDAD_SUPERVISADA AND ER.ESTADO = 1 AND ER.ID_TIPO_CONTACTO IN (SELECT M.ID_MAESTRO_COLUMNA FROM MDI_MAESTRO_COLUMNA M WHERE M.ESTADO = 1 AND M.CODIGO = 'LEGAL' AND M.DOMINIO = 'TIPO_CONTACTO' AND M.APLICACION = 'SIGUO') "

            	+ "WHERE SUP.ESTADO = 1 "); 
            if(idSupervision!=null){
            jpql.append(" AND SUP.ID_SUPERVISION = :idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            //settear parametros
            query.setParameter("idSupervision",idSupervision);
            List<Object[]> lista = query.getResultList();
            resultado= DatoPlantillaBuilder.toListPlantillaResultadoSupervisionDto(lista);
            LOG.info((resultado!=null)?"datosPlantilla:"+resultado.get(0):"datosPlantilla is null");
            retorno = resultado.get(0);
        }catch(Exception e){
            LOG.error("error en find",e);
        }
        return retorno;
    }
    
    @Override
    public DatoPlantillaDTO obtenerFechasCartasVisitas(Long idSupervision) throws DatoPlantillaException{
    	DatoPlantillaDTO plantillaRetorno = new DatoPlantillaDTO();
    	List<DatoPlantillaDTO> plantilla = new ArrayList<DatoPlantillaDTO>();
    	try {
    		String queryString;
            StringBuilder jpql = new StringBuilder();
             jpql.append("SELECT " 
            	+ "CONCAT(CONCAT(TO_CHAR(SUP.FECHA_INICIO, 'DD/MM/YYYY'),', '),TO_CHAR(SUPE.FECHA_INICIO, 'DD/MM/YYYY') ), "
            	+ "TO_CHAR(SUP.FECHA_INICIO, 'DD/MM/YYYY') , TO_CHAR(SUPE.FECHA_INICIO, 'DD/MM/YYYY'), "
            	+ "CONCAT(CONCAT(SUP.CARTA_VISITA,', '),SUPE.CARTA_VISITA), SUP.CARTA_VISITA AS CARTAACTUAL, SUPE.CARTA_VISITA AS CARTAANTERIOR " 
            	+ "FROM PGH_SUPERVISION SUP "
            	+ "LEFT JOIN PGH_SUPERVISION SUPE ON SUP.ID_SUPERVISION_ANT = SUPE.ID_SUPERVISION AND SUPE.ESTADO = 1  ");
            //where
            jpql.append(" WHERE SUP.ESTADO = 1 ");
            //parametro id
            if(idSupervision!=null){
            jpql.append(" AND SUP.ID_SUPERVISION = :idSupervision ");
            }
            //Crear QUERY
            queryString = jpql.toString();
            Query query = this.crud.getEm().createNativeQuery(queryString);
            //settear parametros
            query.setParameter("idSupervision",idSupervision);
            List<Object[]> lista = query.getResultList();
            plantilla= DatoPlantillaBuilder.toListPlantillaResultadoFechasVisitaDto(lista);
            if(!plantilla.isEmpty()){
            	plantillaRetorno.setFechasVisitas(plantilla.get(0).getFechasVisitas());        
            	plantillaRetorno.setCartasVisitas(plantilla.get(0).getCartasVisitas());
            	LOG.info("FECHAS VISITAS : ---> "+ plantillaRetorno.getFechasVisitas() +"CARTAS VISITAS : --> "+plantillaRetorno.getCartasVisitas());
            }
            
		} catch (Exception e) {
			LOG.error("error en find",e);
		}
    	return plantillaRetorno;
    }
    
    /* OSINE_SFS-480 - RSIS 01 - Inicio */
    @Override
    @Transactional
    public List<DocumentoAdjuntoDTO> obtenerImagenesHallazgo(Long idSupervision) {
        LOG.info("buscaPghDocumentoAdjunto");
        List<DocumentoAdjuntoDTO> retorno=new ArrayList<DocumentoAdjuntoDTO>();
        try{
            Query query = crud.getEm().createNamedQuery("PghDocumentoAdjunto.findFromDetalleSupervAndSuperv");
            query.setParameter("idSupervision",idSupervision);
            retorno = PghDocumentoAdjuntoBuilder.toListDocumentoAdjuntoDTO(query.getResultList());
        }catch(Exception ex){
            LOG.error("Error en obtenerImagenesHallazgo",ex);
        }
        return retorno;
    }
    /* OSINE_SFS-480 - RSIS 01 - Fin */
}
