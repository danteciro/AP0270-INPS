<%--
* Resumen		
* Objeto		: asignacion.jsp
* Descripción		: asignacion
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación  y notificación/archivado 
* OSINE_SFS-480  |  17/05/2016   |   Luis García Reyna          |     Crear opción "devolver asignaciones" en la bandeja del supervisor el cual direccionará a la interfaz "devolver asignaciones".
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
            src='<c:url value="/javascript/app/GSM/supervisor/asignacion/asignacion.js" />' charset="utf-8">            
        </script>
    </head>
    <body>
       	<div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="supeAsigAsig.procesarGridAsignacion();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
        <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(supeAsigAsig.procesarGridAsignacion,constant.identificadorBandeja.supAsignaciones);">
            <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
        </div>
        <div class="tac">
            <div id="gridContenedorAsignacion" class="content-grilla"></div>
            <div id="divContextMenuAsignacion"></div>
        </div>
<!--         <div class="filaForm"> -->
<!--             <div> -->
<!--                 <input type="button" value="&Oacute;rdenes de Servicio" class="btn-azul btn-medium" id="btnAbrirOrdenesServicioSupervisor"> -->
<!--             </div> -->
<!--             OSINE_SFS-480 - RSIS 37 - Inicio   -->
<!--             <div style="margin: 0px 10px;"> -->
<!--                 <input type="button" value="Devolver Asignaciones" class="btn-azul btn-medium" id="btnDevolverAsignaciones"> -->
<!--             </div> -->
<!--             OSINE_SFS-480 - RSIS 37 - Fin -->
<!--         </div> -->
    </body>
</html>
