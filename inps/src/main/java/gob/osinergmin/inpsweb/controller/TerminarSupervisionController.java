/**
 * Resumen Objeto	: TerminarSupervisionController.java 
 * Descripción    	: Controla el flujo de datos para terminar una supervision con orden de levantamiento
 * Fecha de Creación	: 11/10/2016
 * Creación	        : OSINE_791
 * Autor         	: GMD
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones       Fecha                Nombre                     Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE791–RSIS40   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD la cual tenga todas sus obligaciones incumplidas subsanadas. 
*/

package gob.osinergmin.inpsweb.controller;

import gob.osinergmin.inpsweb.dto.GenerarResultadoDTO;
import gob.osinergmin.inpsweb.service.business.DocumentoAdjuntoServiceNeg;
import gob.osinergmin.inpsweb.service.business.MaestroColumnaServiceNeg;
import gob.osinergmin.inpsweb.service.business.ObligacionDsrServiceNeg;
import gob.osinergmin.inpsweb.service.business.SupervisionServiceNeg;
import gob.osinergmin.inpsweb.service.exception.DocumentoAdjuntoException;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.ConstantesWeb;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.ui.DocumentoAdjuntoFilter;
import gob.osinergmin.mdicommon.domain.ui.SupervisionFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author GMD
 */
@Controller
@RequestMapping("/terminarSupervision")
public class TerminarSupervisionController {
    private static final Logger LOG = LoggerFactory.getLogger(TerminarSupervisionController.class);
        
    @Inject
    private ObligacionDsrServiceNeg obligacionDsrNeg;
    @Inject
    private DocumentoAdjuntoServiceNeg documentoAdjuntoServiceNeg;
    @Inject
    private MaestroColumnaServiceNeg maestroColumnaServiceNeg;
    @Inject
    private SupervisionServiceNeg supervisionServiceNeg;
    /*OSINE_SFS-791 - RSIS 40 - Inicio */
    @RequestMapping(value = "/generarResultadosTerminarSupervision", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> generarResultadosTerminarSupervision(GenerarResultadoDTO generarResultadoDTO ,HttpServletRequest request, HttpSession session) {
        LOG.info("generarResultadosTerminarSupervision - inicio");
        LOG.info("Idsupervision - inicio->"+generarResultadoDTO.getIdSupervision());
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
        	/*SupervisionFilter filtro=new SupervisionFilter();
            filtro.setIdSupervision(generarResultadoDTO.getIdSupervision());
            filtro.setCartaVisita(generarResultadoDTO.getNroCarta());
            if(supervisionServiceNeg.verificarNroCartaVista(filtro)){*/
	            Map<String, Object> retornoServ = obligacionDsrNeg.generarResultadosSupTerminarSupervision(generarResultadoDTO, request, session);
	            retorno.put("registroDocumentoSupervision", retornoServ.get("registroDocumentoSupervision"));
	            retorno.put("supervision", retornoServ.get("supervision"));
	            retorno.put("correos", retornoServ.get("correos"));
	            retorno.put("cantidad", retornoServ.get("cantidad"));
	            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_EXITO);
	            retorno.put(ConstantesWeb.VV_MENSAJE, "OK");
            /*} else {
            	retorno.put("errorNroCarta", ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
                retorno.put(ConstantesWeb.VV_MENSAJE, ConstantesWeb.mensajes.MSG_OPERATION_DUPLICATE_CARTA_VISITA);
            }*/
        } catch (Exception ex) {
            retorno.put(ConstantesWeb.VV_MENSAJE, ex.getMessage());
            retorno.put(ConstantesWeb.VV_RESULTADO, ConstantesWeb.VV_ERROR);
            LOG.error("Error generarResultadosTerminarSupervision", ex);
        }
        LOG.info("generarResultadosTerminarSupervision - fin");
        return retorno;
    }
    /* OSINE791 - RSIS40 - Inicio */
    @RequestMapping(value = "/generarResultadosDsrTerminarSupervision", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> generarResultadosDsrTerminarSupervision(DocumentoAdjuntoFilter filtro, int rows, int page, HttpSession session, HttpServletRequest request) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            LOG.info("INICIO generarResultadosDsrTerminarSupervision");
            
            LOG.info("IdSupervision->"+filtro.getIdSupervision());
            //LOG.info("IdTipoDocumento->"+filtro.getIdTipoDocumento());
            //List<DocumentoAdjuntoDTO> listado= documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtro);
            List<DocumentoAdjuntoDTO> listado= verDocumentosSupervision(filtro.getIdSupervision());
            LOG.info("HAY |"+listado.size()+"| ELEMENTOS EN LA LISTA DE DOCUMENTOS");
            int indiceInicial = -1;
            int indiceFinal = -1;
            Long contador = (long) listado.size();
            Long numeroFilas = (contador / rows);
            if ((contador % rows) > 0) {
                numeroFilas = numeroFilas + 1L;
            }
            if (numeroFilas < page) {
                page = numeroFilas.intValue();
            }
            if (page == 0) {
                rows = 0;
            }
            indiceInicial = (page - 1) * rows;
            indiceFinal = indiceInicial + rows;
            List<DocumentoAdjuntoDTO> listadoPaginado = listado.subList(indiceInicial > listado.size() ? listado.size() : indiceInicial, indiceFinal > listado.size() ? listado.size() : indiceFinal);
            LOG.info("HAY |"+listadoPaginado.size()+"| ELEMENTOS LA LISTA DE ENVIO");
            

            retorno.put("total", numeroFilas);
            retorno.put("pagina", page);
            retorno.put("registros", contador);
            retorno.put("filas", listadoPaginado);
            
        } catch (Exception ex) {
            LOG.error("Error findgenerarResultados", ex);
        }
        LOG.info("findgenerarResultados - fin");
        return retorno;

    }
    /*OSINE_SFS-791 - RSIS 40 - Fin */
    /*OSINE_SFS-791 - RSIS 41 - Inicio */
    public List<DocumentoAdjuntoDTO> verDocumentosSupervision(Long idSupervision){
        List<DocumentoAdjuntoDTO> listaprincipal = new ArrayList<DocumentoAdjuntoDTO>();
        
        //para acta de habilitacion de RH
        DocumentoAdjuntoFilter filtrohabRH = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDochabRH = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CONSTANCIA_HABILITACION_REGIS_HIDRO).get(0);
        filtrohabRH.setIdSupervision(idSupervision);
        filtrohabRH.setIdTipoDocumento(idTipoDochabRH.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadohabRH;
        try {
            listadohabRH = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtrohabRH);
        } catch (DocumentoAdjuntoException ex) {
            listadohabRH = null;
        }
        //Para acta de levantamiento
        DocumentoAdjuntoFilter filtroactaleva = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDocactaleva = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_ACTA_LEVANTAMIENTO_MEDIDA_SEGURIDAD).get(0);
        filtroactaleva.setIdSupervision(idSupervision);
        filtroactaleva.setIdTipoDocumento(idTipoDocactaleva.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadoactaleva;
        try {
            listadoactaleva = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtroactaleva);
        } catch (DocumentoAdjuntoException ex) {
            listadoactaleva = null;
        }
        //para carta de visita
        DocumentoAdjuntoFilter filtrocarVisi = new DocumentoAdjuntoFilter();
        MaestroColumnaDTO idTipoDoccarVisi = maestroColumnaServiceNeg.findMaestroColumnaByDominioAplicDesc(Constantes.DOMINIO_TIP_DOC_SUP_SIGED, Constantes.APLICACION_INPS, Constantes.DESCRIPCION_TIP_DOC_SUP_SIGED_CARTA_VISITA).get(0);
        filtrocarVisi.setIdSupervision(idSupervision);
        filtrocarVisi.setIdTipoDocumento(idTipoDoccarVisi.getIdMaestroColumna());
        List<DocumentoAdjuntoDTO> listadocarVisi;
        try {
            listadocarVisi = documentoAdjuntoServiceNeg.listarPghDocumentoAdjunto(filtrocarVisi);
        } catch (DocumentoAdjuntoException ex) {
            listadocarVisi = null;
        }        
        if(listadohabRH != null && listadohabRH.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOrh : listadohabRH) {
                listaprincipal.add(documentoAdjuntoDTOrh);
            }
        }
        if(listadoactaleva != null && listadoactaleva.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOactaleva : listadoactaleva) {
                listaprincipal.add(documentoAdjuntoDTOactaleva);
            }
        }
        if(listadocarVisi != null && listadocarVisi.size() > Constantes.LISTA_VACIA){
            for (DocumentoAdjuntoDTO documentoAdjuntoDTOcarVisi : listadocarVisi) {
                listaprincipal.add(documentoAdjuntoDTOcarVisi);
            }
        }
        return listaprincipal;
    }
    /*OSINE_SFS-791 - RSIS 41 - Fin */
}
