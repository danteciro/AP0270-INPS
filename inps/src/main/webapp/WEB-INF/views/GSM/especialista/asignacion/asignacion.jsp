<%--
* Resumen
* Objeto			: asignacion.jsp
* Descripción		: Jsp donde se centraliza las acciones para las asignaciones del Especialista, gerencia GSM.
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
            src='<c:url value="/javascript/app/GSM/especialista/asignacion/asignacion.js" />' charset="utf-8">
        </script>	
    </head>
    <body>
    	<div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="espeAsig.procesarGridAsignacion();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
        <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(espeAsig.procesarGridAsignacion,constant.identificadorBandeja.espAsignaciones);">
            <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
        </div>
        <div class="tac">
            <div id="gridContenedorAsignacion" class="content-grilla"></div>
            <div id="divContextMenuAsignacion"></div>
        </div>
        <div class="tal">
            <input type="button" id="btnAbrirNuevoExpedienteGSM" class="btn-azul btn-medium" value="Generar Orden de Servicio">
        </div>
    </body>
</html>
