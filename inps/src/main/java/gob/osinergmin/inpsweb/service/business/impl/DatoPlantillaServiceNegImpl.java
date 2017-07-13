/**
* Resumen
* Objeto		: DatoPlantillaServiceNegImpl.java
* Descripción		: Clase de la capa de negocio que contiene la implementación de los métodos declarados en la clase interfaz DatoPlantillaServiceNeg
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

package gob.osinergmin.inpsweb.service.business.impl;

import gob.osinergmin.inpsweb.dto.BombaIncendioDTO;
import gob.osinergmin.inpsweb.dto.DatoPlantillaDTO;
import gob.osinergmin.inpsweb.dto.PoiCellDTO;
import gob.osinergmin.inpsweb.dto.PoiParrafoDTO;
import gob.osinergmin.inpsweb.dto.PoiRowDTO;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import gob.osinergmin.inpsweb.dto.PoiPictureDTO;
/* OSINE_SFS-480 - RSIS 01 - Fin */
import gob.osinergmin.inpsweb.dto.PoiRunDTO;
import gob.osinergmin.inpsweb.dto.PoiTableDTO;
import gob.osinergmin.inpsweb.dto.TanqueDTO;
import gob.osinergmin.inpsweb.service.business.ArchivoServiceNeg;
import gob.osinergmin.inpsweb.service.business.DatoPlantillaServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisoraEmpresaServiceNeg;
import gob.osinergmin.inpsweb.service.business.UnidadSupervisadaServiceNeg;
import gob.osinergmin.inpsweb.service.dao.DatoPlantillaDAO;
import gob.osinergmin.inpsweb.service.exception.DatoPlantillaException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.AlmacenamientoAguaDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.ui.DetalleSupervisionFilter;
import gob.osinergmin.mdicommon.domain.ui.UnidadSupervisadaFilter;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import org.apache.commons.lang.WordUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.quartz.impl.calendar.MonthlyCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jpiro
 */
@Service("DatoPlantillaServiceNeg")
public class DatoPlantillaServiceNegImpl implements DatoPlantillaServiceNeg {
    private static final Logger LOG = LoggerFactory.getLogger(DatoPlantillaServiceNegImpl.class);
    @Inject
    private DatoPlantillaDAO datoPlantillaDAO;
    
    @Inject
    private SupervisoraEmpresaServiceNeg supervisoraEmpresaServiceNeg;
    
    @Inject
    private UnidadSupervisadaServiceNeg unidadSupervisadaServiceNeg;
    
    @Inject
    private ArchivoServiceNeg archivoServiceNeg;
    
    private String formatearPalabrasConMayusculas(String cadena){
    	List<String> palabrasPorOmitir = Arrays.asList("S.A.C.","S.A.C","S.A.","S.A","GLP");
    	String nuevacadena = "";
    	if(cadena != null && !cadena.equals("")){
    		String[] palabras = cadena.split(" ");
            for (int i = 0; i < palabras.length; i++) {
            	boolean contiene = palabrasPorOmitir.contains(palabras[i]);
            	if(!contiene){
            		nuevacadena += palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1, palabras[i].length()).toLowerCase() + " ";
            	}else{
            		nuevacadena += palabras[i] + " ";
            	}
            }
            nuevacadena = nuevacadena.trim();
    	}
    	return nuevacadena;
    }
    
    private Map<String, Object> obtenerMapParrafos(DatoPlantillaDTO plantilla) throws UnsupportedEncodingException, ParseException{
        LOG.info("obtenerMapParrafos");
        Map<String, Object> parrafos=new HashMap<String, Object>();
        if(plantilla.getSupervisoraEmpresa() != null && plantilla.getSupervisoraEmpresa().getIdSupervisoraEmpresa() != null){
        	SupervisoraEmpresaDTO supervisoraEmpresa = new SupervisoraEmpresaDTO();
        	supervisoraEmpresa = supervisoraEmpresaServiceNeg.getById(plantilla.getSupervisoraEmpresa().getIdSupervisoraEmpresa());
        	if(supervisoraEmpresa != null){
        		parrafos.put("nombre_empresa_supervisora", formatearPalabrasConMayusculas(supervisoraEmpresa.getRazonSocial() == null ? supervisoraEmpresa.getNombreConsorcio() : supervisoraEmpresa.getRazonSocial()));
        	}else{
        		parrafos.put("nombre_empresa_supervisora","");
        	}
        }else{
        	parrafos.put("nombre_empresa_supervisora","");
        }
        parrafos.put("unidad_organica", formatearPalabrasConMayusculas((plantilla.getUnidadOrganica()!=null && plantilla.getUnidadOrganica().getDescripcion()!=null)?plantilla.getUnidadOrganica().getDescripcion():""));
        parrafos.put("descripcion_unidad_receptora", (plantilla.getUnidadOrganica()!=null && plantilla.getUnidadOrganica().getDescripcion()!=null)?plantilla.getUnidadOrganica().getDescripcion():"");
        parrafos.put("anio_en_curso", Constantes.YEAR);      
        parrafos.put("fecha_visita", (plantilla.getSupervision()!=null && plantilla.getSupervision().getFechaInicio()!=null)? plantilla.getSupervision().getFechaInicio():"");
        parrafos.put("fecha_inicio_supervision", (plantilla.getSupervision()!=null && plantilla.getSupervision().getFechaInicio()!=null)? plantilla.getSupervision().getFechaInicio():"");  
        parrafos.put("descripcion_rubro", formatearPalabrasConMayusculas((plantilla.getIdActividad()!=null && plantilla.getIdActividad().getNombre()!=null)?plantilla.getIdActividad().getNombre():""));
        parrafos.put("razon_social_empresa", formatearPalabrasConMayusculas((plantilla.getEmpresaSupervisada()!=null && plantilla.getEmpresaSupervisada().getRazonSocial()!=null)?plantilla.getEmpresaSupervisada().getRazonSocial():""));
        parrafos.put("direccion_planta", (plantilla.getDirUnidadSupervisada()!=null && plantilla.getDirUnidadSupervisada().getDireccionCompleta()!=null)?plantilla.getDirUnidadSupervisada().getDireccionCompleta():"");
        parrafos.put("distrito_planta", plantilla.getDistrito()!=null?plantilla.getDistrito():"");
        parrafos.put("provincia_planta", plantilla.getProvincia()!=null?plantilla.getProvincia():"");
        parrafos.put("departamento_planta", plantilla.getDepartamento()!=null?plantilla.getDepartamento():"");
        parrafos.put("numero_orden_de_servicio", (plantilla.getOrdenServicio()!=null && plantilla.getOrdenServicio().getNumeroOrdenServicio()!=null)?plantilla.getOrdenServicio().getNumeroOrdenServicio():"");
        parrafos.put("representante_empresa", (plantilla.getEmpresaContacto()!=null && plantilla.getEmpresaContacto().getNombreCompleto()!=null)?plantilla.getEmpresaContacto().getNombreCompleto():"");
        parrafos.put("cargo_representante", (plantilla.getEmpresaContacto()!=null && plantilla.getEmpresaContacto().getCargo()!=null)?plantilla.getEmpresaContacto().getCargo():"");
        parrafos.put("descripcion_proceso", formatearPalabrasConMayusculas((plantilla.getIdProceso()!=null && plantilla.getIdProceso().getDescripcion()!=null)?plantilla.getIdProceso().getDescripcion():""));
        parrafos.put("codigo_osinergmin_unidad_supervisada", (plantilla.getUnidadSupervisada()!=null && plantilla.getUnidadSupervisada().getCodigoOsinergmin()!=null)?plantilla.getUnidadSupervisada().getCodigoOsinergmin():"");
        parrafos.put("numero_expediente_siged", (plantilla.getExpediente()!=null && plantilla.getExpediente().getNumeroExpediente()!=null)?plantilla.getExpediente().getNumeroExpediente():"");                   
        parrafos.put("«osiexpediente»", (plantilla.getExpediente()!=null && plantilla.getExpediente().getNumeroExpediente()!=null)?plantilla.getExpediente().getNumeroExpediente():"");
        String mesanio=Constantes.NAME_MONTH + " - " +Constantes.YEAR;
        mesanio=WordUtils.capitalize(mesanio);
//        parrafos.put("mes_anio", Constantes.NAME_MONTH + " - " +Constantes.YEAR);
        parrafos.put("mes_anio", mesanio);
        parrafos.put("distrito_osinergmin", Constantes.DISTRITO_OSINERGMIN);
        parrafos.put("numero_carta_visita", (plantilla.getSupervision()!=null && plantilla.getSupervision().getCartaVisita()!=null)?plantilla.getSupervision().getCartaVisita():"");
        parrafos.put("fecha_fin_supervision", (plantilla.getSupervision()!=null && plantilla.getSupervision().getFechaFin()!=null)? plantilla.getSupervision().getFechaFin():"");
        parrafos.put("fecha_actual",Constantes.DATE_NOW);
        parrafos.put("fechas_visitas",(plantilla.getFechasVisitas()!=null)? plantilla.getFechasVisitas():"");
        parrafos.put("numero_cartas_visitas",(plantilla.getCartasVisitas()!=null)? plantilla.getCartasVisitas():"");
        parrafos.put("persona_atiende", (plantilla.getNombrePersonaAtiende()!=null && !plantilla.getNombrePersonaAtiende().trim().equals(""))?plantilla.getNombrePersonaAtiende():"");
        parrafos.put("cargo_atiende", (plantilla.getCargoPersonaAtiende()!=null)?plantilla.getCargoPersonaAtiende():"");
        parrafos.put("motivo_no_supervision", (plantilla.getMotivoNoSupervision()!=null)?plantilla.getMotivoNoSupervision():"");
        parrafos.put("fecha_supervision_actual",(plantilla.getFechaSupervisionActual()!=null)? plantilla.getFechaSupervisionActual():"");
        parrafos.put("fecha_supervision_anterior",(plantilla.getFechaSupervisionAnterior()!=null)? plantilla.getFechaSupervisionAnterior():"");
        parrafos.put("«osifecha»", Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " de " + Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "ES")) + " de " + Calendar.getInstance().get(Calendar.YEAR));
        parrafos.put("numero_hallazgos", plantilla.getNumeroHallazgos().toString());
        parrafos.put("numero_ficha_registro", plantilla.getNroRegistroHidrocarburo() == null ? "" : plantilla.getNroRegistroHidrocarburo());
        
        List<DocumentoAdjuntoDTO> listaDocumentos = new ArrayList<DocumentoAdjuntoDTO>();
        String numeroInforme = "";
        if(plantilla.getExpediente() != null){
        	if(plantilla.getExpediente().getNumeroExpediente() != null){
        		listaDocumentos=archivoServiceNeg.listarDocumentosSiged(plantilla.getExpediente().getNumeroExpediente());
        		for (DocumentoAdjuntoDTO documento : listaDocumentos) {
					if(documento.getIdTipoDocumento() != null){
						if(documento.getIdTipoDocumento().getCodigo() != null && documento.getIdTipoDocumento().getCodigo().equals(Constantes.TIPO_DOCUMENTO_INFORME_SUPERVISION)){
							numeroInforme = documento.getNroDocumento() == null ? "" : documento.getNroDocumento();
						}
					}
				}
        	}
        }
        parrafos.put("numero_informe", numeroInforme);
        
        BigDecimal sumaCapacidad = new BigDecimal(0.0);
        if(plantilla.getUnidadSupervisada() != null){
        	UnidadSupervisadaFilter unidadFilt=new UnidadSupervisadaFilter();
        	unidadFilt.setCodigoOsinerg(plantilla.getUnidadSupervisada().getCodigoOsinergmin());
        	UnidadSupervisadaDTO unidad=unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadFilt).get(0);
        	if(unidad != null){
        		List<AlmacenamientoAguaDTO> listaAlmacenamientoAgua = new ArrayList<AlmacenamientoAguaDTO>();
                listaAlmacenamientoAgua = datoPlantillaDAO.listarAlmacenamientoAgua(unidad.getIdUnidadSupervisada());
                if(listaAlmacenamientoAgua.size()>0){
        			for (AlmacenamientoAguaDTO registro : listaAlmacenamientoAgua) {
        				sumaCapacidad= sumaCapacidad.add(registro.getCapacidadAlmacenamiento());
        			}
        	    }
        	}
        }
        parrafos.put("capacidad_total_agua_almacenada", sumaCapacidad.toString());
        
        
        return parrafos;
    }
    
    @Override
    public Map<String, Map<String, Object>> obtenerDatosPlantillaUno(Long idSupervision) throws DatoPlantillaException{
        LOG.info("obtenerDatosPlantillaUno");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try{
        	List<TanqueDTO> listaTanques = new ArrayList<TanqueDTO>();
        	List<BombaIncendioDTO> listaBombas = new ArrayList<BombaIncendioDTO>();
            DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);
            
            //obteniendo datos de la unidad supervisada
            if(plantilla.getUnidadSupervisada() != null){
            	UnidadSupervisadaFilter unidadFilt=new UnidadSupervisadaFilter();
            	unidadFilt.setCodigoOsinerg(plantilla.getUnidadSupervisada().getCodigoOsinergmin());
            	UnidadSupervisadaDTO unidad=unidadSupervisadaServiceNeg.getUnidadSupervisadaDTO(unidadFilt).get(0);
            	if(unidad != null){
            		listaTanques = datoPlantillaDAO.obtenerTanquesParaPlantilla(unidad.getIdUnidadSupervisada());
            		listaBombas = datoPlantillaDAO.obtenerBombasParaPlantilla(unidad.getIdUnidadSupervisada());
            	}
            }
            
            List<DocumentoAdjuntoDTO> listaDocumentosSupervision = datoPlantillaDAO.obtenerDocumentoSupervisionPlantilla(idSupervision);
            List<DetalleSupervisionDTO> listaHallazgosSupervision = datoPlantillaDAO.obtenerDetalleSupervision(new DetalleSupervisionFilter(idSupervision,Constantes.ESTADO_INACTIVO));
            if(listaHallazgosSupervision != null){
            	plantilla.setNumeroHallazgos(Long.parseLong(String.valueOf(listaHallazgosSupervision.size())));
            }
            
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            List<DocumentoAdjuntoDTO> listaImagenesHallazgos = datoPlantillaDAO.obtenerImagenesHallazgo(idSupervision);
            /* OSINE_SFS-480 - RSIS 01 - Fin */
            boolean flgIncumplimiento=false;
            for(DetalleSupervisionDTO reg : listaHallazgosSupervision){if(reg.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){flgIncumplimiento=true;break;}}
            
            //TABLA LISTADO de hallazgos_base_legal
            Map<String, Object> listadoTablas=new HashMap<String, Object>();
            listadoTablas.put("tabla_hallazgo_base_legal", armaTablaHallazgoBaseLegal(listaHallazgosSupervision));
            listadoTablas.put("tabla_tanques_glp", armaTablaTanquesUnidadSupervisada(listaTanques));
            listadoTablas.put("tabla_bombas_incendio", armaTablaBombasUnidadSupervisada(listaBombas));
            //ETIQUETAS PARA PARRAFOS
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
            parrafos.put("texto_copia_documentos", (listaDocumentosSupervision!=null && listaDocumentosSupervision.size()>0)?
                    new String("Durante la visita de supervisi\u00f3n se recibieron copias de los siguientes documentos: ".getBytes("UTF-8"),"UTF-8"):
                    new String("Durante la visita de supervisi\u00f3n no se recibieron documentos.".getBytes("UTF-8"),"UTF-8"));
            parrafos.put("conclusion_plantilla", flgIncumplimiento?
                    new String("De la evaluaci\u00f3n efectuada y la visita de supervisi\u00f3n realizada el ".getBytes("UTF-8"),"UTF-8")+parrafos.get("fecha_visita")+new String(", se verific\u00f3 que la planta envasadora de la empresa".getBytes("UTF-8"),"UTF-8")+" "+parrafos.get("razon_social_empresa")+" "+new String("presenta incumplimientos a la normativa vigente.".getBytes("UTF-8"),"UTF-8"):
                    new String("De la evaluaci\u00f3n efectuada y la visita de supervisi\u00f3n realizada el ".getBytes("UTF-8"),"UTF-8")+parrafos.get("fecha_visita")+new String(", se verific\u00f3 que la planta envasadora de la empresa".getBytes("UTF-8"),"UTF-8") +" "+parrafos.get("razon_social_empresa")+" "+ new String("no presenta incumplimientos a la normativa vigente.".getBytes("UTF-8"),"UTF-8"));
            parrafos.put("recomendacion_plantilla", flgIncumplimiento?
                    new String("Iniciar el proceso administrativo de sanci\u00f3n a la empresa ".getBytes("UTF-8"),"UTF-8")+parrafos.get("razon_social_empresa")+new String(", por el incumplimiento en la aplicaci\u00f3n de la normativa vigente en sus instalaciones de la ".getBytes("UTF-8"),"UTF-8")+parrafos.get("descripcion_rubro")+" ubicada "+parrafos.get("direccion_planta")+".":
                    new String("Archivar la presente instrucci\u00f3n preliminar iniciada a la ".getBytes("UTF-8"),"UTF-8") +parrafos.get("descripcion_rubro")+ new String(" operada por la empresa ".getBytes("UTF-8"),"UTF-8")+ parrafos.get("razon_social_empresa")+new String(", ubicada en ".getBytes("UTF-8"),"UTF-8")+ parrafos.get("direccion_planta") + new String(", distrito ".getBytes("UTF-8"),"UTF-8") + parrafos.get("distrito_planta") + new String(", provincia ".getBytes("UTF-8"),"UTF-8") + parrafos.get("provincia_planta") + new String(" y departamento de ".getBytes("UTF-8"),"UTF-8") + parrafos.get("provincia_planta") +".");
            parrafos.put("texto_observaciones",flgIncumplimiento?
                    "se ha verificado lo siguiente:":
                    "no se encontraron observaciones.");
            //LISTADOS DE PARRAFOS 
            LOG.info((listaDocumentosSupervision!=null)?"listaDocumentosSupervision:"+listaDocumentosSupervision.size():"listaDocumentosSupervision:es nulo");
            List<PoiParrafoDTO> listaParrafosDocuSupe=new ArrayList<PoiParrafoDTO>();
            for(DocumentoAdjuntoDTO reg : listaDocumentosSupervision){
                PoiParrafoDTO par=new PoiParrafoDTO();
                par.setEstilo("vinetaguion");
                    List<PoiRunDTO> listRp=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r=new PoiRunDTO(reg.getDescripcionDocumento()); 
                    listRp.add(r);
                par.setListRun(listRp);
                listaParrafosDocuSupe.add(par);
            }
            Map<String, Object> listadoListas=new HashMap<String, Object>();
            listadoListas.put("lista_documentos", listaParrafosDocuSupe);
            //
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            //LISTADOS IMAGENES, HALLAZGOS
            List<PoiPictureDTO> listaImgHallazgos = new ArrayList<PoiPictureDTO>();
            int i=0;
            for(DocumentoAdjuntoDTO reg : listaImagenesHallazgos){
                int pictureType=Utiles.Poi.obtenerPictureType(reg.getNombreArchivo().substring(reg.getNombreArchivo().lastIndexOf(".")+1));
                if(pictureType!=0){
                    i++;
                    PoiPictureDTO imagen = new PoiPictureDTO();
                    imagen.setNombre(reg.getNombreArchivo());
                    imagen.setExtension(reg.getNombreArchivo().substring(reg.getNombreArchivo().lastIndexOf(".")));
                    imagen.setImagen(reg.getArchivoAdjunto());
                    imagen.setPictureType(pictureType);
                    imagen.setWidth(450);
                    imagen.setHeight(370);
                    
                    for(DetalleSupervisionDTO hallazgo : listaHallazgosSupervision){
                    	if(hallazgo!=null && hallazgo.getFlagResultado()!=null && hallazgo.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){
                    		if(hallazgo.getIdDetalleSupervision().equals(reg.getDetalleSupervision().getIdDetalleSupervision())){
                    			if(reg.getDetalleSupervision().getIdDetalleSupervision().equals(hallazgo.getIdDetalleSupervision())){
                    				imagen.setDescripcion("Hallazgo N° "+hallazgo.getIndice()+" : "+reg.getDescripcionDocumento());
                    			}
                    		}
                    	}
                    }
                    
                    listaImgHallazgos.add(imagen);
                }
            }
            Map<String, Object> listadoImagenes=new HashMap<String, Object>();
            listadoImagenes.put("lista_img_hallazgo", listaImgHallazgos);
            /* OSINE_SFS-480 - RSIS 01 - Fin */
            
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);
            datos.put(Utiles.Poi.LISTADO_TABLAS,listadoTablas);
            datos.put(Utiles.Poi.LISTADO_LISTAS,listadoListas);
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            datos.put(Utiles.Poi.LISTADO_IMAGENES,listadoImagenes);
            //datos.put(Utiles.Poi.LISTADO_IMAGENES,listadoImagenesHallazgo);
            /* OSINE_SFS-480 - RSIS 01 - Fin */
        }catch(Exception e){
            LOG.error("Error en obtenerDatosPlantillaUno",e);
            throw new DatoPlantillaException(e.getMessage(),e);
        }
        return datos;
    }
    
    
    @Override
    public Map<String, Map<String, Object>> obtenerDatosPlantillaDos(Long idSupervision) throws DatoPlantillaException{
        LOG.info("obtenerDatosPlantillaDos");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
            DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);            
            List<DocumentoAdjuntoDTO> listaDocumentosSupervision = datoPlantillaDAO.obtenerDocumentoSupervisionPlantilla(idSupervision);
            DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
            filtro.setIdSupervision(idSupervision);
            LOG.info("--------idSupervision---------->"+idSupervision);
            List<DetalleSupervisionDTO> listaHallazgosSupervision = datoPlantillaDAO.obtenerDetalleSupervision(filtro); 
            DatoPlantillaDTO fechasCartasVisitas = datoPlantillaDAO.obtenerFechasCartasVisitas(idSupervision);   
            plantilla.setFechasVisitas(fechasCartasVisitas.getFechasVisitas());
            
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            List<DocumentoAdjuntoDTO> listaImagenesHallazgos = datoPlantillaDAO.obtenerImagenesHallazgo(idSupervision);
            /* OSINE_SFS-480 - RSIS 01 - Fin */
            
            boolean flgIncumplimiento=false;
            for(DetalleSupervisionDTO reg : listaHallazgosSupervision){
                if(reg.getFlagResultado()!=null && reg.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){
                    flgIncumplimiento=true;break;
                }
            }
            
            //TABLA LISTADO de hallazgos_base_legal
            Map<String, Object> listadoTablas=new HashMap<String, Object>();
            listadoTablas.put("tabla_hallazgo_base_legal", armaTablaHallazgoBaseLegal(listaHallazgosSupervision));
            
            //ETIQUETAS PARA PARRAFOS
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
            parrafos.put("texto_copia_documentos", (listaDocumentosSupervision!=null && listaDocumentosSupervision.size()>0)?
                    new String("Durante la visita de supervisi\u00f3n se recibieron copias de los siguientes documentos: ".getBytes("UTF-8"),"UTF-8"):
                    new String("Durante la visita de supervisi\u00f3n no se recibieron documentos.".getBytes("UTF-8"),"UTF-8"));
            parrafos.put("conclusion_plantilla", flgIncumplimiento?
                    new String("De la evaluaci\u00f3n efectuada y la visita de supervisi\u00f3n realizada el ".getBytes("UTF-8"),"UTF-8")+parrafos.get("fecha_visita")+new String(", se verific\u00f3 que la planta envasadora de la empresa ".getBytes("UTF-8"),"UTF-8")+parrafos.get("razon_social_empresa")+new String(" presenta incumplimientos a la normativa vigente.".getBytes("UTF-8"),"UTF-8"):
                    new String("De la evaluaci\u00f3n efectuada y la visita de supervisi\u00f3n realizada el ".getBytes("UTF-8"),"UTF-8")+parrafos.get("fecha_visita")+new String(", se verific\u00f3 que la planta envasadora de la empresa".getBytes("UTF-8"),"UTF-8") +parrafos.get("razon_social_empresa")+ new String("no presenta incumplimientos a la normativa vigente.".getBytes("UTF-8"),"UTF-8"));
            parrafos.put("recomendacion_plantilla", flgIncumplimiento?
                    new String("Iniciar el proceso administrativo de sanci\u00f3n a la empresa ".getBytes("UTF-8"),"UTF-8")+parrafos.get("razon_social_empresa")+new String(", por el incumplimiento en la aplicaci\u00f3n de la normativa vigente en sus instalaciones de la ".getBytes("UTF-8"),"UTF-8")+parrafos.get("descripcion_rubro")+" ubicada "+parrafos.get("direccion_planta")+".":
                    new String("Archivar la presente instrucci\u00f3n preliminar iniciada a la ".getBytes("UTF-8"),"UTF-8") +parrafos.get("descripcion_rubro")+ new String(" operada por la empresa ".getBytes("UTF-8"),"UTF-8")+ parrafos.get("razon_social_empresa")+new String(", ubicada en ".getBytes("UTF-8"),"UTF-8")+ parrafos.get("direccion_planta") + new String(", distrito ".getBytes("UTF-8"),"UTF-8") + parrafos.get("distrito_planta") + new String(" , provincia ".getBytes("UTF-8"),"UTF-8") + parrafos.get("provincia_planta") + new String(" y departamento de ".getBytes("UTF-8"),"UTF-8") + parrafos.get("provincia_planta") +".");
            parrafos.put("texto_observaciones",flgIncumplimiento?
                    "se ha verificado lo siguiente:":
                    "no se encontraron observaciones.");  
            //LISTADOS DE PARRAFOS 
            LOG.info((listaDocumentosSupervision!=null)?"listaDocumentosSupervision:"+listaDocumentosSupervision.size():"listaDocumentosSupervision:es nulo");
            List<PoiParrafoDTO> listaParrafosDocuSupe=new ArrayList<PoiParrafoDTO>();
            for(DocumentoAdjuntoDTO reg : listaDocumentosSupervision){
                PoiParrafoDTO par=new PoiParrafoDTO();
                par.setEstilo("vinetaguion");
                    List<PoiRunDTO> listRp=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r=new PoiRunDTO(reg.getDescripcionConcatenadaDocumento()); 
                    listRp.add(r);
                par.setListRun(listRp);
                listaParrafosDocuSupe.add(par);
            }
            Map<String, Object> listadoListas=new HashMap<String, Object>();
            listadoListas.put("lista_documentos", listaParrafosDocuSupe);
            
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            //LISTADOS IMAGENES, HALLAZGOS
            List<PoiPictureDTO> listaImgHallazgos = new ArrayList<PoiPictureDTO>();
            int i=0;
            for(DocumentoAdjuntoDTO reg : listaImagenesHallazgos){
                int pictureType=Utiles.Poi.obtenerPictureType(reg.getNombreArchivo().substring(reg.getNombreArchivo().lastIndexOf(".")+1));
                if(pictureType!=0){
                    i++;
                    PoiPictureDTO imagen = new PoiPictureDTO();
                    imagen.setNombre(reg.getNombreArchivo());
                    imagen.setExtension(reg.getNombreArchivo().substring(reg.getNombreArchivo().lastIndexOf(".")));
                    imagen.setDescripcion("Foto "+i+": "+reg.getDescripcionDocumento());
                    imagen.setImagen(reg.getArchivoAdjunto());
                    imagen.setPictureType(pictureType);
                    imagen.setWidth(350);
                    imagen.setHeight(200);
                    listaImgHallazgos.add(imagen);
                }
            }
            Map<String, Object> listadoImagenes=new HashMap<String, Object>();
            listadoImagenes.put("lista_img_hallazgo", listaImgHallazgos);
            /* OSINE_SFS-480 - RSIS 01 - Fin */
            
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);       
            datos.put(Utiles.Poi.LISTADO_LISTAS,listadoListas);
            datos.put(Utiles.Poi.LISTADO_TABLAS,listadoTablas);
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            datos.put(Utiles.Poi.LISTADO_IMAGENES,listadoImagenes);
            /* OSINE_SFS-480 - RSIS 01 - Fin */
        } catch (Exception e) {
            LOG.error("Error en obtenerDatosPlantillaDos",e);
            throw new DatoPlantillaException(e.getMessage(),e);
        }
        return datos;
    }
    
    private PoiTableDTO armaTablaBombasUnidadSupervisada(List<BombaIncendioDTO> listaBombas) throws UnsupportedEncodingException{
    	PoiTableDTO tablaBombas=new PoiTableDTO();
    	int contFilas=0;
    	List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
    	for (BombaIncendioDTO bomba : listaBombas) {
    		contFilas++;
    		PoiRowDTO row=new PoiRowDTO();
    		List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
    		
    		PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            List<PoiParrafoDTO> listPc1 = new ArrayList<PoiParrafoDTO>();
                PoiParrafoDTO p1c1=new PoiParrafoDTO();
                    List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r1p1c1=new PoiRunDTO(String.valueOf(contFilas),false,UnderlinePatterns.NONE,"Calibri",10); 
                    listRp1c1.add(r1p1c1);
                p1c1.setAlign(ParagraphAlignment.CENTER);
                p1c1.setListRun(listRp1c1);
            listPc1.add(p1c1);
            cell1.setListParrafo(listPc1);
    		
            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c2=new PoiRunDTO(bomba.getCapacidadNominal().toString(),false,UnderlinePatterns.NONE,"Calibri",10); 
                        listRp1c2.add(r1p1c2);
                    p1c2.setAlign(ParagraphAlignment.CENTER);
                    p1c2.setListRun(listRp1c2);
                listPc2.add(p1c2);
            cell2.setListParrafo(listPc2);
            
            
            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            List<PoiParrafoDTO> listPc3 = new ArrayList<PoiParrafoDTO>();
                PoiParrafoDTO p1c3=new PoiParrafoDTO();
                    List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r1p1c3=new PoiRunDTO(formatearPalabrasConMayusculas(bomba.getDescripcionTipoMotor()),false,UnderlinePatterns.NONE,"Calibri",10); 
                    listRp1c3.add(r1p1c3);
                p1c3.setAlign(ParagraphAlignment.CENTER);
                p1c3.setListRun(listRp1c3);
            listPc3.add(p1c3);
            cell3.setListParrafo(listPc3);
            
            listCell.add(cell1);
            listCell.add(cell2);
            listCell.add(cell3);
            row.setListCell(listCell);
            listRow.add(row);
		}
    	LOG.info("bombas en flagResultado=0-->"+contFilas);
    	tablaBombas.setListRow(listRow);
        return tablaBombas;
    }
    
    private PoiTableDTO armaTablaTanquesUnidadSupervisada(List<TanqueDTO> listaTanques) throws UnsupportedEncodingException{
    	PoiTableDTO tablaTanques=new PoiTableDTO();
    	int contFilas=0;
    	List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
    	for (TanqueDTO tanque : listaTanques) {
    		contFilas++;
    		PoiRowDTO row=new PoiRowDTO();
    		List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
            PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c1=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c1=new PoiRunDTO(tanque.getNumeroTanque().toString(),false,UnderlinePatterns.NONE,"Calibri",10); 
                        listRp1c1.add(r1p1c1);
                    p1c1.setAlign(ParagraphAlignment.CENTER);
                    p1c1.setListRun(listRp1c1);
                listPc1.add(p1c1);
            cell1.setListParrafo(listPc1);
            
            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                PoiParrafoDTO p1c2=new PoiParrafoDTO();
                    List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r1p1c2=new PoiRunDTO(formatearPalabrasConMayusculas(tanque.getDescripcionTipoInstalacion()),false,UnderlinePatterns.NONE,"Calibri",10); 
                    listRp1c2.add(r1p1c2);
                p1c2.setAlign(ParagraphAlignment.CENTER);
                p1c2.setListRun(listRp1c2);
            listPc2.add(p1c2);
            cell2.setListParrafo(listPc2);
            
            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            List<PoiParrafoDTO> listPc3 = new ArrayList<PoiParrafoDTO>();
                PoiParrafoDTO p1c3=new PoiParrafoDTO();
                    List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r1p1c3=new PoiRunDTO(tanque.getCapacidadNominal().toString(),false,UnderlinePatterns.NONE,"Calibri",10); 
                    listRp1c3.add(r1p1c3);
                p1c3.setAlign(ParagraphAlignment.CENTER);
                p1c3.setListRun(listRp1c3);
            listPc3.add(p1c3);
            cell3.setListParrafo(listPc3);
            
            PoiCellDTO cell4=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            List<PoiParrafoDTO> listPc4 = new ArrayList<PoiParrafoDTO>();
                PoiParrafoDTO p1c4 =new PoiParrafoDTO();
                    List<PoiRunDTO> listRp1c4=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r1p1c4=new PoiRunDTO(tanque.getSerie(),false,UnderlinePatterns.NONE,"Calibri",10); 
                    listRp1c4.add(r1p1c4);
                p1c4.setAlign(ParagraphAlignment.CENTER);
                p1c4.setListRun(listRp1c4);
            listPc4.add(p1c4);
            cell4.setListParrafo(listPc4);
            
            listCell.add(cell1);
            listCell.add(cell2);
            listCell.add(cell3);
            listCell.add(cell4);
            row.setListCell(listCell);
            listRow.add(row);
		}
    	LOG.info("tanques en flagResultado=0-->"+contFilas);
    	tablaTanques.setListRow(listRow);
        return tablaTanques;
    }
    
    private PoiTableDTO armaTablaHallazgoConImagenes(List<DetalleSupervisionDTO> listaHallazgosSupervision,List<DocumentoAdjuntoDTO> listadoImagenes) throws UnsupportedEncodingException{
        PoiTableDTO tablaHallazgos=new PoiTableDTO();
        int contFilas=0;
        int contHallazgos=0;
        List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
        for(DetalleSupervisionDTO hallazgo : listaHallazgosSupervision){
        	contFilas = 0;
        	if(hallazgo!=null && hallazgo.getFlagResultado()!=null && hallazgo.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){
        		contFilas++;
        		contHallazgos++;
        		String cadenaImgHallazgo = "";
        		
        		cadenaImgHallazgo = "lista_img_hallazgo_" + contHallazgos;
        		PoiRowDTO rowImg=new PoiRowDTO();
                List<PoiCellDTO> listCellImg=new ArrayList<PoiCellDTO>();
                    PoiCellDTO cell1Img=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                        List<PoiParrafoDTO> listPc1Img=new ArrayList<PoiParrafoDTO>();
                            PoiParrafoDTO p1c1Img=new PoiParrafoDTO();
                                List<PoiRunDTO> listRp1c1Img=new ArrayList<PoiRunDTO>();
                                    PoiRunDTO r1p1c1Img=new PoiRunDTO(cadenaImgHallazgo,false,UnderlinePatterns.NONE,"Calibri",10); 
                                listRp1c1Img.add(r1p1c1Img);
                            p1c1Img.setAlign(ParagraphAlignment.CENTER);
                            p1c1Img.setListRun(listRp1c1Img);
                        listPc1Img.add(p1c1Img);
                    cell1Img.setListParrafo(listPc1Img);
                
	            listCellImg.add(cell1Img);
	            rowImg.setListCell(listCellImg);
	            listRow.add(rowImg);
	            contHallazgos++;
        		
        		PoiRowDTO row=new PoiRowDTO();
                List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
                    PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                        List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                            PoiParrafoDTO p1c1=new PoiParrafoDTO();
                                List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                                    PoiRunDTO r1p1c1=new PoiRunDTO("Hallazgo : " + hallazgo.getDescripcionResultado(),false,UnderlinePatterns.NONE,"Calibri",10); 
                                listRp1c1.add(r1p1c1);
                            p1c1.setAlign(ParagraphAlignment.CENTER);
                            p1c1.setListRun(listRp1c1);
                        listPc1.add(p1c1);
                    cell1.setListParrafo(listPc1);
                
	            listCell.add(cell1);
	            row.setListCell(listCell);
	            listRow.add(row);
        	}
        }
        LOG.info("hallazgos en flagResultado=0-->"+contFilas);
        tablaHallazgos.setListRow(listRow);
        return tablaHallazgos;
    }
    private PoiTableDTO armaTablaHallazgoBaseLegal(List<DetalleSupervisionDTO> listaHallazgosSupervision) throws UnsupportedEncodingException{
        PoiTableDTO tablaHallazgos=new PoiTableDTO();
        int contFilas=0;
        List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
        for(DetalleSupervisionDTO hallazgo : listaHallazgosSupervision){
            if(hallazgo!=null && hallazgo.getFlagResultado()!=null && hallazgo.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){
                contFilas++;
                PoiRowDTO row=new PoiRowDTO();
                    List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
                        PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                            List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                                PoiParrafoDTO p1c1=new PoiParrafoDTO();
                                    List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                                        PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(contFilas),false,UnderlinePatterns.NONE,"Calibri",10); 
                                    listRp1c1.add(r1p1c1);
                                p1c1.setAlign(ParagraphAlignment.CENTER);
                                p1c1.setListRun(listRp1c1);
                            listPc1.add(p1c1);
                        cell1.setListParrafo(listPc1);
                        PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                            List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                                /*PoiParrafoDTO p1c2=new PoiParrafoDTO();
                                    List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                                        PoiRunDTO r1p1c2=new PoiRunDTO(new String("Observaci\u00f3n N\u00b0 ".getBytes("UTF-8"),"UTF-8")+contFilas,true,UnderlinePatterns.NONE,"Calibri",11); 
                                    listRp1c2.add(r1p1c2);
                                p1c2.setListRun(listRp1c2);
                        listPc2.add(p1c2);*/
                                PoiParrafoDTO p2c2=new PoiParrafoDTO();
                        listPc2.add(p2c2);
                                PoiParrafoDTO p3c2=new PoiParrafoDTO();
                                    List<PoiRunDTO> listRp3c2=new ArrayList<PoiRunDTO>();
                                        PoiRunDTO r1p3c2=new PoiRunDTO("Hallazgo", false, UnderlinePatterns.SINGLE,"Calibri",10); 
                                    listRp3c2.add(r1p3c2);
                                p3c2.setListRun(listRp3c2);
                        listPc2.add(p3c2);
                                PoiParrafoDTO p4c2=new PoiParrafoDTO();
                                    List<PoiRunDTO> listRp4c2=new ArrayList<PoiRunDTO>();
                                        PoiRunDTO r1p4c2=new PoiRunDTO(hallazgo.getDescripcionResultado(),false,UnderlinePatterns.NONE,"Calibri",10);
                                    listRp4c2.add(r1p4c2);
                                p4c2.setListRun(listRp4c2);
                        listPc2.add(p4c2);
                                PoiParrafoDTO p5c2=new PoiParrafoDTO();
                        listPc2.add(p5c2);
                                PoiParrafoDTO p6c2=new PoiParrafoDTO();
                                    List<PoiRunDTO> listRp6c2=new ArrayList<PoiRunDTO>();
                                        PoiRunDTO r1p6c2=new PoiRunDTO("Base Legal", false, UnderlinePatterns.SINGLE,"Calibri",10); 
                                    listRp6c2.add(r1p6c2);
                                p6c2.setListRun(listRp6c2);
                        listPc2.add(p6c2);
                        
                        for(int i=0;i<hallazgo.getListaBaseLegal().size();i++){
                            PoiParrafoDTO p7c2=new PoiParrafoDTO();
                                List<PoiRunDTO> listRp7c2=new ArrayList<PoiRunDTO>();
                                    PoiRunDTO r1p7c2=new PoiRunDTO(hallazgo.getListaBaseLegal().get(i).getDescripcionGeneralBaseLegal(),false,UnderlinePatterns.NONE,"Calibri",10); 
                                listRp7c2.add(r1p7c2);
                            p7c2.setListRun(listRp7c2);
                            listPc2.add(p7c2); 
                        }
//                                PoiParrafoDTO p7c2=new PoiParrafoDTO();
//                                    List<PoiRunDTO> listRp7c2=new ArrayList<PoiRunDTO>();
//                                        String bl="";
//                                        for(int i=0;i<hallazgo.getListaBaseLegal().size();i++){
//                                            String coma=(i<hallazgo.getListaBaseLegal().size()-1)?", ":".";
//                                            bl=bl+hallazgo.getListaBaseLegal().get(i).getDescripcionGeneralBaseLegal()+coma;
//                                        }
//                                        PoiRunDTO r1p7c2=new PoiRunDTO(bl,false,UnderlinePatterns.NONE,"Calibri",11); 
//                                    listRp7c2.add(r1p7c2);
//                                p7c2.setListRun(listRp7c2);
//                        listPc2.add(p7c2);    
                        
                        cell2.setListParrafo(listPc2);
                    listCell.add(cell1);
                    listCell.add(cell2);
                    row.setListCell(listCell);
                listRow.add(row);
            }
        }
        LOG.info("hallazgos en flagResultado=0-->"+contFilas);
        tablaHallazgos.setListRow(listRow);
        return tablaHallazgos;
    }
    
    @Override
    public Map<String, Map<String, Object>> obtenerDatosPlantillaTres(Long idSupervision) throws DatoPlantillaException {
    	LOG.info("obtenerDatosPlantillaTres");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
            DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision); 
            DatoPlantillaDTO fechasCartasVisitas = datoPlantillaDAO.obtenerFechasCartasVisitas(idSupervision);   
            plantilla.setFechasVisitas(fechasCartasVisitas.getFechasVisitas());
            plantilla.setCartasVisitas(fechasCartasVisitas.getCartasVisitas());
            DetalleSupervisionFilter filtro = new DetalleSupervisionFilter();
            filtro.setIdSupervision(idSupervision);
            List<DetalleSupervisionDTO> listaIncumplidosSupervision = datoPlantillaDAO.obtenerDetalleSupervision(filtro);
            List<DetalleSupervisionDTO> listaIncumplidosSanciona= obtenerIncumplidosSanciona(listaIncumplidosSupervision);
            //TABLA LISTADO
            Map<String, Object> listadoTablas=new HashMap<String, Object>();
            listadoTablas.put("tabla_incumplimiento_base_legal", obtenerTablaIncumplidos(listaIncumplidosSupervision));
            listadoTablas.put("tabla_determinacion_sancion", obtenerTablaIncumplidosInicial(listaIncumplidosSupervision));
            listadoTablas.put("tabla_sancion", obtenerTablaIncumplidosSanciona(listaIncumplidosSanciona));
            listadoTablas.put("tabla_numero_incumplimiento", obtenerTablaIncumplidosSancionaMontoMulta(listaIncumplidosSanciona));
            
            //ETIQUETAS PARA PARRAFOS
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
          //LISTADOS DE PARRAFOS 
            LOG.info((listaIncumplidosSupervision!=null)?"listaIncumplidosSupervision:"+listaIncumplidosSupervision.size():"listaDocumentosSupervision:es nulo");
            List<PoiParrafoDTO> listaParrafosIncuSupe=new ArrayList<PoiParrafoDTO>();
            int contFilasDescargo=0;
            for(DetalleSupervisionDTO reg : listaIncumplidosSupervision){
            	contFilasDescargo++;
                PoiParrafoDTO par=new PoiParrafoDTO();
                par.setEstilo("vinetaguion");
                    List<PoiRunDTO> listRp=new ArrayList<PoiRunDTO>();
                        PoiRunDTO r=new PoiRunDTO("Respecto al incumplimiento N° "+contFilasDescargo+", indica que "+reg.getDescripcionResultado()+".");
                    listRp.add(r);
                par.setListRun(listRp);
                listaParrafosIncuSupe.add(par);
            }
            Map<String, Object> listadoListas=new HashMap<String, Object>();
            listadoListas.put("lista_incumplimiento_descargo", listaParrafosIncuSupe);
            
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);	
            datos.put(Utiles.Poi.LISTADO_TABLAS,listadoTablas);
            datos.put(Utiles.Poi.LISTADO_LISTAS,listadoListas);
        } catch (Exception e) {
            LOG.error("Error en obtenerDatosPlantillaTres",e);
            throw new DatoPlantillaException(e.getMessage(),e);
        }
        return datos;
    }
    
    @Override
    public Map<String, Map<String, Object>> obtenerDatosPlantillaCuatro(Long idSupervision) throws DatoPlantillaException {
        LOG.info("obtenerDatosPlantillaCuatro");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
            DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);  
            DatoPlantillaDTO fechasCartasVisitas = datoPlantillaDAO.obtenerFechasCartasVisitas(idSupervision);
            List<DocumentoAdjuntoDTO> listaDocumentosSupervision = datoPlantillaDAO.obtenerDocumentoSupervisionPlantilla(idSupervision);
            plantilla.setFechasVisitas(fechasCartasVisitas.getFechasVisitas());
            List<DetalleSupervisionDTO> listaIncumplimientoSupervision = datoPlantillaDAO.obtenerDetalleSupervision(new DetalleSupervisionFilter(idSupervision,Constantes.ESTADO_INACTIVO));            
            //TABLA LISTADO de incumplimientos
            Map<String, Object> listadoTablas=new HashMap<String, Object>();
            List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
            int contFilas=0;
            for(DetalleSupervisionDTO incumplimiento:listaIncumplimientoSupervision){
            	contFilas++;
                PoiRowDTO row=new PoiRowDTO();
                List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
                //Primera celda
                PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                	//parrafo
	                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
	                	//Primer parrafo    
	                	PoiParrafoDTO p1c1=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
	                            PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(contFilas),false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c1.add(r1p1c1);
	                    p1c1.setListRun(listRp1c1);
	                listPc1.add(p1c1);
	            cell1.setListParrafo(listPc1);
	            //Segunda celda
	            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
	                            PoiRunDTO r1p1c2=new PoiRunDTO(incumplimiento.getDescripcionResultado(),false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c2.add(r1p1c2);
	                    p1c2.setListRun(listRp1c2);
	                listPc2.add(p1c2);
	            cell2.setListParrafo(listPc2);    
	            //Tercera celda
	            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc3=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c3=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
		                        String bl="";
	                            for(int i=0;i<incumplimiento.getListaBaseLegal().size();i++){
	                                String coma=(i<incumplimiento.getListaBaseLegal().size()-1)?", ":".";
	                                bl=bl+incumplimiento.getListaBaseLegal().get(i).getDescripcionGeneralBaseLegal()+coma;
	                            }
	                            PoiRunDTO r1p1c3=new PoiRunDTO(bl,false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c3.add(r1p1c3);
	                    p1c3.setListRun(listRp1c3);
	                listPc3.add(p1c3);
	            cell3.setListParrafo(listPc3);
	            //Cuarta celda
	            PoiCellDTO cell4=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc4=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c4=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c4=new ArrayList<PoiRunDTO>();
	                            PoiRunDTO r1p1c4=new PoiRunDTO(incumplimiento.getObligacion().getDescripcion(),false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c4.add(r1p1c4);
	                    p1c4.setListRun(listRp1c4);
	                listPc4.add(p1c4);
	            cell4.setListParrafo(listPc4);
	            //Quinta celda
	            PoiCellDTO cell5=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc5=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c5=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c5=new ArrayList<PoiRunDTO>();
	                        	String tipificacion = incumplimiento.getTipificacion()!=null?incumplimiento.getTipificacion().getCodTipificacion()+"-"+incumplimiento.getTipificacion().getDescripcion():"";
	                            PoiRunDTO r1p1c5=new PoiRunDTO(tipificacion,false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c5.add(r1p1c5);
	                    p1c5.setListRun(listRp1c5);
	                listPc5.add(p1c5);
	            cell5.setListParrafo(listPc5);
	            //Sexta celda
	            PoiCellDTO cell6=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc6=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c6=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c6=new ArrayList<PoiRunDTO>();
	                        String otrasSanciones = incumplimiento.getTipificacion()!=null?incumplimiento.getTipificacion().getOtrasSanciones():"";
	                            PoiRunDTO r1p1c6=new PoiRunDTO(otrasSanciones,false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c6.add(r1p1c6);
	                    p1c6.setListRun(listRp1c6);
	                listPc6.add(p1c6);
	            cell6.setListParrafo(listPc6);
	            listCell.add(cell1);
	            listCell.add(cell2);
	            listCell.add(cell3);
	            listCell.add(cell4);
	            listCell.add(cell5);
	            listCell.add(cell6);
                row.setListCell(listCell);
            listRow.add(row);
            }
            PoiTableDTO tablaIncumplidos=new PoiTableDTO();
            tablaIncumplidos.setListRow(listRow);
            listadoTablas.put("tabla_incumplimiento_base_legal", tablaIncumplidos);
            //ETIQUETAS PARA PARRAFOS
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
            String cabecera = " y de la evaluaci\u00f3n de los documentos presentados ";
            String listaDocumentos="";
            if(!listaDocumentosSupervision.isEmpty()){
            	for(DocumentoAdjuntoDTO reg:listaDocumentosSupervision){
            		listaDocumentos+=reg.getDescripcionConcatenadaDocumento()+",";
            	}
            }
            if(!Constantes.CONSTANTE_VACIA.equals(listaDocumentos.trim())){
            	listaDocumentos = listaDocumentos.substring(0, (listaDocumentos.length()-1));
            }
            parrafos.put("texto_evaluacion_documentos", (listaDocumentosSupervision!=null && listaDocumentosSupervision.size()>0)?
                    new String((cabecera+listaDocumentos).getBytes("UTF-8"),"UTF-8"):
                    new String(""));
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);
            datos.put(Utiles.Poi.LISTADO_TABLAS,listadoTablas);
        } catch (Exception e) {
            LOG.error("Error en obtenerDatosPlantillaCuatro",e);
            throw new DatoPlantillaException(e.getMessage(),e);
        }
        return datos;
    }
    
    @Override
	public Map<String, Map<String, Object>> obtenerDatosPlantillaCinco(Long idSupervision) throws DatoPlantillaException {
		LOG.info("obtenerDatosPlantillaCinco");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
        	DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);    
        	List<DetalleSupervisionDTO> listaHallazgosSupervision = datoPlantillaDAO.obtenerDetalleSupervision(new DetalleSupervisionFilter(idSupervision,Constantes.ESTADO_INACTIVO)); //mdioses - RSIS5
        	
        	boolean flgIncumplimiento=false; //mdioses - RSIS5
            for(DetalleSupervisionDTO reg : listaHallazgosSupervision){if(reg.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){flgIncumplimiento=true;break;}} 
            //TABLA LISTADO de hallazgos_base_legal
            Map<String, Object> listadoTablas=new HashMap<String, Object>();
            listadoTablas.put("tabla_hallazgo_base_legal", armaTablaHallazgoBaseLegal(listaHallazgosSupervision)); //mdioses - RSIS5
            
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);
            datos.put(Utiles.Poi.LISTADO_TABLAS,listadoTablas);  //mdioses - RSIS5
		} catch (Exception e) {
			LOG.error("Error en obtenerDatosPlantillaCinco",e);
            throw new DatoPlantillaException(e.getMessage(),e);
		}
        return datos;
	}    
	@Override
	public Map<String, Map<String, Object>> obtenerDatosPlantillaSeis(Long idSupervision) throws DatoPlantillaException {
		LOG.info("obtenerDatosPlantillaSeis");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
        	DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);     
        	DatoPlantillaDTO fechasCartasVisitas = datoPlantillaDAO.obtenerFechasCartasVisitas(idSupervision);          	
        	plantilla.setFechasVisitas(fechasCartasVisitas.getFechasVisitas());
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);            
            parrafos.put("numero_registro_hidrocarburos", (plantilla.getNroRegistroHidrocarburo()!=null)?plantilla.getNroRegistroHidrocarburo():"");
                datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);
		} catch (Exception e) {
			LOG.error("Error en obtenerDatosPlantillaSeis",e);
            throw new DatoPlantillaException(e.getMessage(),e);
		}
        return datos;
	}
	@Override
	public Map<String, Map<String, Object>> obtenerDatosPlantillaSiete(Long idSupervision) throws DatoPlantillaException {
		LOG.info("obtenerDatosPlantillaSiete");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
        	DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);
        	DatoPlantillaDTO fechasCartasVisitas = datoPlantillaDAO.obtenerFechasCartasVisitas(idSupervision);        	
        	plantilla.setFechasVisitas(fechasCartasVisitas.getFechasVisitas());
        	List<DetalleSupervisionDTO> listaIncumplimientoSupervision = datoPlantillaDAO.obtenerDetalleSupervision(new DetalleSupervisionFilter(idSupervision,Constantes.ESTADO_INACTIVO));            
            //TABLA LISTADO de incumplimientos
            Map<String, Object> listadoTablas=new HashMap<String, Object>();
            List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
            int contFilas=0;
            for(DetalleSupervisionDTO incumplimiento:listaIncumplimientoSupervision){
            	contFilas++;
                PoiRowDTO row=new PoiRowDTO();
                List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
                //Primera celda
                PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                	//parrafo
	                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
	                	//Primer parrafo    
	                	PoiParrafoDTO p1c1=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
	                            PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(contFilas),false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c1.add(r1p1c1);
	                    p1c1.setListRun(listRp1c1);
	                listPc1.add(p1c1);
	            cell1.setListParrafo(listPc1);
	            //Segunda celda
	            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
	                            PoiRunDTO r1p1c2=new PoiRunDTO(incumplimiento.getDescripcionResultado(),false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c2.add(r1p1c2);
	                    p1c2.setListRun(listRp1c2);
	                listPc2.add(p1c2);
	            cell2.setListParrafo(listPc2);    
	            //Tercera celda
	            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc3=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c3=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
		                        String bl="";
	                            for(int i=0;i<incumplimiento.getListaBaseLegal().size();i++){
	                                String coma=(i<incumplimiento.getListaBaseLegal().size()-1)?", ":".";
	                                bl=bl+incumplimiento.getListaBaseLegal().get(i).getDescripcionGeneralBaseLegal()+coma;
	                            }
	                            PoiRunDTO r1p1c3=new PoiRunDTO(bl,false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c3.add(r1p1c3);
	                    p1c3.setListRun(listRp1c3);
	                listPc3.add(p1c3);
	            cell3.setListParrafo(listPc3);
	            //Cuarta celda
	            PoiCellDTO cell4=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc4=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c4=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c4=new ArrayList<PoiRunDTO>();
	                            PoiRunDTO r1p1c4=new PoiRunDTO(incumplimiento.getObligacion().getDescripcion(),false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c4.add(r1p1c4);
	                    p1c4.setListRun(listRp1c4);
	                listPc4.add(p1c4);
	            cell4.setListParrafo(listPc4);
	            //Quinta celda
	            PoiCellDTO cell5=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc5=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c5=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c5=new ArrayList<PoiRunDTO>();
	                        	String tipificacion = incumplimiento.getTipificacion()!=null?incumplimiento.getTipificacion().getCodTipificacion()+"-"+incumplimiento.getTipificacion().getDescripcion():"";
	                            PoiRunDTO r1p1c5=new PoiRunDTO(tipificacion,false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c5.add(r1p1c5);
	                    p1c5.setListRun(listRp1c5);
	                listPc5.add(p1c5);
	            cell5.setListParrafo(listPc5);
	            //Sexta celda
	            PoiCellDTO cell6=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
	                List<PoiParrafoDTO> listPc6=new ArrayList<PoiParrafoDTO>();
	                    PoiParrafoDTO p1c6=new PoiParrafoDTO();
	                        List<PoiRunDTO> listRp1c6=new ArrayList<PoiRunDTO>();
	                        String otrasSanciones = incumplimiento.getTipificacion()!=null?incumplimiento.getTipificacion().getOtrasSanciones():"";
	                            PoiRunDTO r1p1c6=new PoiRunDTO(otrasSanciones,false,UnderlinePatterns.NONE,"Calibri",11); 
	                        listRp1c6.add(r1p1c6);
	                    p1c6.setListRun(listRp1c6);
	                listPc6.add(p1c6);
	            cell6.setListParrafo(listPc6);
	            listCell.add(cell1);
	            listCell.add(cell2);
	            listCell.add(cell3);
	            listCell.add(cell4);
	            listCell.add(cell5);
	            listCell.add(cell6);
                row.setListCell(listCell);
            listRow.add(row);
            }
            PoiTableDTO tablaIncumplidos=new PoiTableDTO();
            tablaIncumplidos.setListRow(listRow);
            listadoTablas.put("tabla_incumplimiento_base_legal", tablaIncumplidos);          
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
            parrafos.put("numero_ruc_empresa", plantilla.getRucEmpresaSupervisada()!=null?plantilla.getRucEmpresaSupervisada():"");
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);
            datos.put(Utiles.Poi.LISTADO_TABLAS,listadoTablas);
		} catch (Exception e) {
			LOG.error("Error en obtenerDatosPlantillaSiete",e);
            throw new DatoPlantillaException(e.getMessage(),e);
		}
        return datos;
	}  
	
	public Map<String, Map<String, Object>> obtenerDatosPlantillaNoSupervisada(Long idSupervision) throws DatoPlantillaException { //mdiosesf - RSIS5
		LOG.info("obtenerDatosPlantillaNoSupervisada");
        Map<String, Map<String, Object>> datos=new HashMap<String, Map<String, Object>>();
        try {
        	DatoPlantillaDTO plantilla = datoPlantillaDAO.obtenerDatosPlantilla(idSupervision);            
           
            
            DatoPlantillaDTO fechasCartasVisitas = datoPlantillaDAO.obtenerFechasCartasVisitas(idSupervision);   
            plantilla.setFechaSupervisionActual(fechasCartasVisitas.getFechaSupervisionActual());
            plantilla.setFechaSupervisionAnterior(fechasCartasVisitas.getFechaSupervisionAnterior());
            
            Map<String, Object> parrafos=obtenerMapParrafos(plantilla);
            datos.put(Utiles.Poi.LISTADO_ETIQUETAS,parrafos);
		} catch (Exception e) {
			LOG.error("Error en obtenerDatosPlantillaNoSupervisada",e);
            throw new DatoPlantillaException(e.getMessage(),e);
		}
        return datos;
	} 
	
	private PoiTableDTO obtenerTablaIncumplidos(List<DetalleSupervisionDTO> listaIncumplidosSupervision){
		PoiTableDTO tablaIncumplidos=new PoiTableDTO();
        List<PoiRowDTO> listRow=new ArrayList<PoiRowDTO>();
        for(DetalleSupervisionDTO incumplimiento:listaIncumplidosSupervision){
            PoiRowDTO row=new PoiRowDTO();
            List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
            //Primera celda
            PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            	//parrafo
                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                	//Primer parrafo    
                	PoiParrafoDTO p1c1=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(incumplimiento.getIndice()),false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c1.add(r1p1c1);
                    p1c1.setListRun(listRp1c1);
                listPc1.add(p1c1);
            cell1.setListParrafo(listPc1);
            //Segunda celda
            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                        	String descripcionAnterior = incumplimiento.getDetalleSupervisionAnt()!=null?incumplimiento.getDetalleSupervisionAnt().getDescripcionResultado():"";
                            PoiRunDTO r1p1c2=new PoiRunDTO(descripcionAnterior,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c2.add(r1p1c2);
                    p1c2.setListRun(listRp1c2);
                listPc2.add(p1c2);
            cell2.setListParrafo(listPc2);    
            //Tercera celda
            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc3=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c3=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
	                        String bl="";
                            for(int i=0;i<incumplimiento.getListaBaseLegal().size();i++){
                                String coma=(i<incumplimiento.getListaBaseLegal().size()-1)?", ":".";
                                bl=bl+incumplimiento.getListaBaseLegal().get(i).getDescripcionGeneralBaseLegal()+coma;
                            }
                            PoiRunDTO r1p1c3=new PoiRunDTO(bl,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c3.add(r1p1c3);
                    p1c3.setListRun(listRp1c3);
                listPc3.add(p1c3);
            cell3.setListParrafo(listPc3);
            //Cuarta celda
            PoiCellDTO cell4=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc4=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c4=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c4=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c4=new PoiRunDTO(incumplimiento.getObligacion().getDescripcion(),false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c4.add(r1p1c4);
                    p1c4.setListRun(listRp1c4);
                listPc4.add(p1c4);
            cell4.setListParrafo(listPc4);
            //Quinta celda
            PoiCellDTO cell5=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc5=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c5=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c5=new ArrayList<PoiRunDTO>();
                        	String tipificacion = incumplimiento.getTipificacion()!=null?incumplimiento.getTipificacion().getCodTipificacion()+"-"+incumplimiento.getTipificacion().getDescripcion():"";
                            PoiRunDTO r1p1c5=new PoiRunDTO(tipificacion,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c5.add(r1p1c5);
                    p1c5.setListRun(listRp1c5);
                listPc5.add(p1c5);
            cell5.setListParrafo(listPc5);
            //Sexta celda
            PoiCellDTO cell6=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc6=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c6=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c6=new ArrayList<PoiRunDTO>();
                        String otrasSanciones = incumplimiento.getTipificacion()!=null?incumplimiento.getTipificacion().getOtrasSanciones():"";
                            PoiRunDTO r1p1c6=new PoiRunDTO(otrasSanciones,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c6.add(r1p1c6);
                    p1c6.setListRun(listRp1c6);
                listPc6.add(p1c6);
            cell6.setListParrafo(listPc6);
            listCell.add(cell1);
            listCell.add(cell2);
            listCell.add(cell3);
            listCell.add(cell4);
            listCell.add(cell5);
            listCell.add(cell6);
            row.setListCell(listCell);
        listRow.add(row);
        }
        tablaIncumplidos.setListRow(listRow);
        return tablaIncumplidos;
	}
	
	private PoiTableDTO obtenerTablaIncumplidosInicial(List<DetalleSupervisionDTO> listaIncumplidosSupervision){
		PoiTableDTO tablaIncumplidosIniciales=new PoiTableDTO();
        List<PoiRowDTO> listRowInicial=new ArrayList<PoiRowDTO>();
        for(DetalleSupervisionDTO incumplimientoInicial:listaIncumplidosSupervision){
            PoiRowDTO row=new PoiRowDTO();
            List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
            //Primera celda
            PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            	//parrafo
                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                	//Primer parrafo    
                	PoiParrafoDTO p1c1=new PoiParrafoDTO(ParagraphAlignment.CENTER);
                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(incumplimientoInicial.getIndice()),false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c1.add(r1p1c1);
                    p1c1.setListRun(listRp1c1);
                listPc1.add(p1c1);
            cell1.setListParrafo(listPc1);
            //Segunda celda
            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                        	String tipificacion = incumplimientoInicial.getTipificacion()!=null?incumplimientoInicial.getTipificacion().getCodTipificacion():"";
                            PoiRunDTO r1p1c2=new PoiRunDTO(tipificacion,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c2.add(r1p1c2);
                    p1c2.setListRun(listRp1c2);
                listPc2.add(p1c2);
            cell2.setListParrafo(listPc2);    
            //Tercera celda
            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc3=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c3=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
	                        String descripcionTipi = incumplimientoInicial.getTipificacion()!=null?incumplimientoInicial.getTipificacion().getDescripcion():"";
                            PoiRunDTO r1p1c3=new PoiRunDTO(descripcionTipi,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c3.add(r1p1c3);
                    p1c3.setListRun(listRp1c3);
                listPc3.add(p1c3);
            cell3.setListParrafo(listPc3);
            //Cuarta celda
            PoiCellDTO cell4=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
		            List<PoiParrafoDTO> listPc4=new ArrayList<PoiParrafoDTO>();
	                PoiParrafoDTO p1c4=new PoiParrafoDTO();
	                    List<PoiRunDTO> listRp1c4=new ArrayList<PoiRunDTO>();
	                        PoiRunDTO r1p1c4=new PoiRunDTO(); 
	                    listRp1c4.add(r1p1c4);
	                p1c4.setListRun(listRp1c4);
	            listPc4.add(p1c4);
	        cell4.setListParrafo(listPc4);
            //Quinta celda
            PoiCellDTO cell5=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc5=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c5=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c5=new ArrayList<PoiRunDTO>();
                        	String sancionAplica = incumplimientoInicial.getTipificacion()!=null?incumplimientoInicial.getTipificacion().getSancionMonetaria()+" UIT":"";
                            sancionAplica += incumplimientoInicial.getTipificacion()!=null && !incumplimientoInicial.getTipificacion().getOtrasSanciones().equals(Constantes.CONSTANTE_VACIA)
                            		?" - "+incumplimientoInicial.getTipificacion().getOtrasSanciones():"";
                        	PoiRunDTO r1p1c5=new PoiRunDTO(sancionAplica,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c5.add(r1p1c5);
                    p1c5.setListRun(listRp1c5);
                listPc5.add(p1c5);
            cell5.setListParrafo(listPc5);
            listCell.add(cell1);
            listCell.add(cell2);
            listCell.add(cell3);
            listCell.add(cell4);
            listCell.add(cell5);
            row.setListCell(listCell);
        listRowInicial.add(row);
        }
        tablaIncumplidosIniciales.setListRow(listRowInicial);
        return tablaIncumplidosIniciales;
	}
	
	private PoiTableDTO obtenerTablaIncumplidosSanciona(List<DetalleSupervisionDTO> listaIncumplidosSanciona){
		PoiTableDTO tablaIncumplidosSanciona=new PoiTableDTO();
        List<PoiRowDTO> listRowInicial=new ArrayList<PoiRowDTO>();
        for(DetalleSupervisionDTO registro:listaIncumplidosSanciona){
            PoiRowDTO row=new PoiRowDTO();
            List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
            //Primera celda
            PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            	//parrafo
                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                	//Primer parrafo    
                	PoiParrafoDTO p1c1=new PoiParrafoDTO(ParagraphAlignment.CENTER);
                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(registro.getIndice()),false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c1.add(r1p1c1);
                    p1c1.setListRun(listRp1c1);
                listPc1.add(p1c1);
            cell1.setListParrafo(listPc1);
            //Segunda celda
            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c2=new PoiRunDTO(registro.getDescripcionResultado(),false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c2.add(r1p1c2);
                    p1c2.setListRun(listRp1c2);
                listPc2.add(p1c2);
            cell2.setListParrafo(listPc2);    
            //Tercera celda
            PoiCellDTO cell3=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc3=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c3=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c3=new ArrayList<PoiRunDTO>();
                        	String codTipificacion = registro.getTipificacion()!=null?registro.getTipificacion().getCodTipificacion():"";
                            PoiRunDTO r1p1c3=new PoiRunDTO(codTipificacion,false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c3.add(r1p1c3);
                    p1c3.setListRun(listRp1c3);
                listPc3.add(p1c3);
            cell3.setListParrafo(listPc3);
            listCell.add(cell1);
            listCell.add(cell2);
            listCell.add(cell3);
            row.setListCell(listCell);
        listRowInicial.add(row);
        }
        tablaIncumplidosSanciona.setListRow(listRowInicial);
        return tablaIncumplidosSanciona;
	}
	
	
	private PoiTableDTO obtenerTablaIncumplidosSancionaMontoMulta(List<DetalleSupervisionDTO> listaIncumplidosSanciona){
		PoiTableDTO tablaIncumplidosSanciona=new PoiTableDTO();
        List<PoiRowDTO> listRowInicial=new ArrayList<PoiRowDTO>();
        for(DetalleSupervisionDTO registro:listaIncumplidosSanciona){
            PoiRowDTO row=new PoiRowDTO();
            List<PoiCellDTO> listCell=new ArrayList<PoiCellDTO>();
            //Primera celda
            PoiCellDTO cell1=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
            	//parrafo
                List<PoiParrafoDTO> listPc1=new ArrayList<PoiParrafoDTO>();
                	//Primer parrafo    
                	PoiParrafoDTO p1c1=new PoiParrafoDTO(ParagraphAlignment.CENTER);
                        List<PoiRunDTO> listRp1c1=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c1=new PoiRunDTO(Integer.toString(registro.getIndice()),false,UnderlinePatterns.NONE,"Calibri",11); 
                        listRp1c1.add(r1p1c1);
                    p1c1.setListRun(listRp1c1);
                listPc1.add(p1c1);
            cell1.setListParrafo(listPc1);
            //Segunda celda
            PoiCellDTO cell2=new PoiCellDTO(XWPFTableCell.XWPFVertAlign.CENTER);
                List<PoiParrafoDTO> listPc2=new ArrayList<PoiParrafoDTO>();
                    PoiParrafoDTO p1c2=new PoiParrafoDTO();
                        List<PoiRunDTO> listRp1c2=new ArrayList<PoiRunDTO>();
                            PoiRunDTO r1p1c2=new PoiRunDTO(); 
                        listRp1c2.add(r1p1c2);
                    p1c2.setListRun(listRp1c2);
                listPc2.add(p1c2);
            cell2.setListParrafo(listPc2);    
            listCell.add(cell1);
            listCell.add(cell2);
            row.setListCell(listCell);
        listRowInicial.add(row);
        }
        tablaIncumplidosSanciona.setListRow(listRowInicial);
        return tablaIncumplidosSanciona;
	}
	
	private List<DetalleSupervisionDTO> obtenerIncumplidosSanciona(List<DetalleSupervisionDTO> listaIncumplidosSupervision){
		List<DetalleSupervisionDTO> listaIncumplidosSanciona = new ArrayList<DetalleSupervisionDTO>();
		for(DetalleSupervisionDTO registro: listaIncumplidosSupervision){
			if(registro.getFlagResultado().equals(Constantes.ESTADO_INACTIVO)){
				listaIncumplidosSanciona.add(registro);
			}
		}
		return listaIncumplidosSanciona;
	}
}
