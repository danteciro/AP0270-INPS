<%--
* Resumen		
* Objeto		: otrosIncumplimientos.jsp
* Descripción		: Registro de otros Incumplimientos
* Fecha de Creación	: 22/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  22/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
* OSINE791-RSIS25|  09/09/2016   |	Alexander Vilca Narvaez 	| Modificar la funcionalidad para el modo registro y consulta en los Tabs
*                |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/otrosIncumplimientos/otrosIncumplimientos.js" />'
                charset="utf-8">
        </script>
    </head>
    <body>
        <input type="hidden" id="datoTxtOtrosIncumplimientos" name="datoTxtOtrosIncumplimientos" value="${sup.otrosIncumplimientos}" >
        <form id="frmOI">
            <div id="divMensValidaOI" class="errorMensaje" style="display: none"></div>
            <div class="filaForm">
                <div class="lbl-large" style="padding: 10px 0px 10px 2px"><label>Otros Incumplimientos :</label></div>
                <textarea id="txtOtrosIncumplimientos" rows="10" class="" style="width:95%" maxlength="1000" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>${sup.otrosIncumplimientos}</textarea>  
            </div>
        </form>
        <div class="btnNavSupervisionDsr">
        	 <!--/* OSINE791 - RSIS25 - Inicio */--> 
        	<c:if test="${modo=='registro'}">
        	<input type="button" id='btnAnteriorIn' title="Anterior" class="btn-azul btn-small" value="Anterior">
            <input type="button" id='btnSiguiente' title="Siguiente" class="btn-azul btn-small" value="Siguiente">
        	</c:if>
            <c:if test="${modo=='consulta'}">
            <input type="button" id='btnAnteriorIn' title="Anterior" class="btn-azul btn-small" value="Anterior" style="display: none;">
            <input type="button" id='btnSiguiente' title="Siguiente" class="btn-azul btn-small" value="Siguiente" style="display: none;">
            </c:if>
            <!--/* OSINE791 - RSIS25 - Fin */-->
        </div>
        <div id="dialogOtrosIncumplimientos" class="dialog" style="display:none;"></div>
    </body>
</html>
