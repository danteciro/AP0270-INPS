<%--
* Resumen		
* Objeto		: obligacionListado.jsp
* Descripción		: Crear la funcionalidad de consultar las obligaciones a verificar en una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística supervisión realizada
* Fecha de Creación	: 18/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* REQF-0007      |  18/08/2016   |   Zosimo Chaupis Santur      |    Crear la funcionalidad de consultar las obligaciones a verificar en una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística supervisión realizada
*                |               |                              |
*                |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/obligacion/obligacionListado.js" />'
        charset="utf-8"></script>
    </head>
    <body>            
        <div class="form" id="formRegSupervisionDsrOblLst">    	
            <div class="tac" >

                <div id="gridContenedorObligacionListado"  class="content-grilla"></div>
                <div id="divContextMenuObligacionListado" ></div>
            </div>

        </div>
       
        <div class="btnNavSupervisionDsr">	
            <input type="button" title ="Anterior" id="btnAtrasObligacionesSP" class="btn-azul btn-small" value="Anterior" >   
            
             <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
           	 	<input type="button" title="Siguiente" id="btnObligaciones" class="btn-azul btn-small" value="Siguiente">
            </c:if> 
             
            <c:if test="${ sup.ordenServicioDTO.iteracion > 1}">
           	 	<input type="button" title="Siguiente" id="btnTerminarSupervision" class="btn-azul btn-small" value="Siguiente">
            </c:if>                
             
        </div>
    </body>
</html>