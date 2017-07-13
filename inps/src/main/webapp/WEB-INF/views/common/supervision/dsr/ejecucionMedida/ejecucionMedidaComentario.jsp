<%--
* Resumen		
* Objeto		: ejecucionMedidaComentario.jsp
* Descripción		: Registro de Ejecución Medida Comentario
* Fecha de Creación	: 07/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  07/09/2016   |   Luis García Reyna          |     Registrar Comentario (Detalle Supervision - Escenario Incumplido).
*                |               |                              |
*                |               |                              |
* 
--%>
<!--OSINE_SFS-791 - RSIS 16 - Inicio-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/ejecucionMedida/ejecucionMedidaComentario.js" />'
                charset="utf-8">
        </script>
    </head>
    <body>
        <input type="hidden" id="idDetalleSupervision" name="idDetalleSupervision" value="${detSup.idDetalleSupervision}">
        <input type="hidden" id="idEscenarioIncumplido" name="idEscenarioIncumplido" value="${escInDo.idEscenarioIncumplido}">
        
        <div class="form" id="formConsComentario">
            <form id="frmOI">
                <div id="divMensValidaEmComDetSup" class="errorMensaje" style="display: none"></div>
                <div class="filaForm">
                    <div class="lbl-large" style="padding: 10px 0px 10px 2px"><label>Ejecución Medida (*) :</label></div>
                    <textarea id="txtComentarioEjecucionMedida" rows="10" validate="[O]" class="" style="width:95%" maxlength="1000">${detSup.comentario}${escInDo.comentarioEjecucion}</textarea>  
                </div>
            </form>
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        <div class="botones">
            <input type="button" id='btnGuadarEmComentario' title="Guardar" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
<!--OSINE_SFS-791 - RSIS 16 - Fin-->