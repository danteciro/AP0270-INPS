<%-- 
    Document   : asignacion
    Created on : 25/03/2016, 09:32:18 AM
    Author     : htorress
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
            src='<c:url value="/javascript/app/jefe/asignacion/asignacion.js" />' charset="utf-8">
        </script>	
    </head>
    <body>
    	<div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="espeAsigAsig.procesarGridAsignacion();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
        <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(espeAsigAsig.procesarGridAsignacion,constant.identificadorBandeja.espAsignaciones);">
            <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
        </div>
        <div class="tac">
            <div id="gridContenedorAsignacion" class="content-grilla"></div>
            <div id="divContextMenuAsignacion"></div>
        </div>
        <div class="tal">
            <input type="button" id="btnAbrirNuevoExpediente" class="btn-azul btn-medium" value="Generar Orden de Servicio">
        </div>
    </body>
</html>
