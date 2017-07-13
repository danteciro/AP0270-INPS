<%--
* Resumen		
* Objeto		: notificadoArchivado.jsp
* Descripción		: notificadoArchivado
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  06/06/2016   |    Hernán Torres Saen         |     Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
*                |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
            src='<c:url value="/javascript/app/supervisorRegional/notificadoArchivado/notificadoArchivado.js" />' charset="utf-8">
        </script>	
    </head>
    <body>
    	<div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="espeNotiArchNotiArch.procesarGridArchivadoNotificado();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
        <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(espeNotiArchNotiArch.procesarGridArchivadoNotificado,constant.identificadorBandeja.espNotificadoArchivado);">
            <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
        </div>
        <div class="tac">
            <div id="gridContenedorNotificadoArchivado" class="content-grilla"></div>
            <div id="divContextMenuNotificadoArchivado"></div>
        </div>
        <!-- OSINE_SFS-480 - RSIS 06 - Inicio -->
        <div class="tal">
            <input type="button" id="btnConsultarMensajeriaSupervisorRegional" class="btn-azul btn-medium" value="Consultar Mensajeria">
        </div>
        <!-- OSINE_SFS-480 - RSIS 06 - Fin -->
        <!-- dialogs -->
        <div id="dialogConcluirExpe" class="dialog" style="display:none;"></div>
    </body>
</html>
