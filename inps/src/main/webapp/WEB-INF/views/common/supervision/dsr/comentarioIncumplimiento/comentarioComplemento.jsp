<%--
* Resumen		
* Objeto		: comentarioIncumplimiento.jsp
* Descripción		: comentarioIncumplimiento
* Fecha de Creación	: 07/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: JPIRO
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*                |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/supervision/dsr/comentarioIncumplimiento/comentarioComplemento.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
            <input type="hidden" id="idComentarioIncumplimientoCoCo" value="${comeDetSup.comentarioIncumplimiento.idComentarioIncumplimiento}">
            <input type="hidden" id="idDetalleSupervisionCoCo" value="${comeDetSup.detalleSupervision.idDetalleSupervision}">
            <input type="hidden" id="idComentarioDetSupervisionCoCo" value="${comeDetSup.idComentarioDetSupervision}">
            
            <input type="hidden" id="idEsceIncumplimientoCoCo" value="${comeDetSup.idEsceIncumplimiento}">
            <input type="hidden" id="idInfraccionCoCo" value="${comeDetSup.idInfraccion}">
            <input type="hidden" id="codigoOsinergminCoCo" value="${comeDetSup.codigoOsinergmin}">
            <form id="frmComInc">
                <div id="divMensajeValidaFrmComInc" class="errorMensaje" style="display: none"></div>
                
            </form>
        </div>
        <div class="txt-obligatorio" id="txtObligComInc" style="display:none;">(*) Campos obligatorios</div>
        <div class="botones">	
            <input type="button" id="btnGuardarComentarioComplemento" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar" />         
        </div>
    </body>
</html>
