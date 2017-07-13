<%--
* Resumen
* Objeto			: evaluacion.jsp
* Descripción		: Jsp donde se centraliza las acciones para las evaluaciones del Especialista, gerencia GSM.
* Fecha de Creación	: 24/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernán Torres.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
            src='<c:url value="/javascript/app/GSM/especialista/evaluacion/evaluacion.js" />' charset="utf-8">
        </script>	
    </head>
    <body>
    	<div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="espeGSMEvalEval.procesarGridEvaluacion();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
        <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(espeGSMEvalEval.procesarGridEvaluacion,constant.identificadorBandeja.espEvaluacion);">
            <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
        </div>
        <div class="tac">
            <div id="gridContenedorEvaluacion" class="content-grilla"></div>
            <div id="divContextMenuEvaluacion"></div>
        </div>
        <!-- dialogs -->
        <div id="dialogNotificarExpe" class="dialog" style="display:none;"></div>
        <div id="dialogRevisarExpe" class="dialog" style="display:none;"></div>
        <div id="dialogAprobarExpe" class="dialog" style="display:none;"></div>
    </body>
</html>
