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
            src='<c:url value="/javascript/app/jefe/derivado/derivado.js" />' charset="utf-8">
        </script>	
    </head>
    <body>
    	<div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="espeDeriDeri.procesarGridDerivado();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
        <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(espeDeriDeri.procesarGridDerivado,constant.identificadorBandeja.espDerivados);">
            <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
        </div>
        <div class="tac">
            <div id="gridContenedorDerivado" class="content-grilla"></div>
            <div id="divContextMenuDerivado"></div>
        </div>
        <!-- dialogs -->
        <div id="dialogAsignarOrdeServ" class="dialog" style="display:none;"></div>
    </body>
</html>
