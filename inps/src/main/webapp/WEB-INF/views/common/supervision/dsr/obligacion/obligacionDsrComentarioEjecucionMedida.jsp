<%--
* Resumen		
* Objeto		: obligacionDsrComentarioEjecucionMedida.jsp
* Descripción		: Mostrar comentario.
* Fecha de Creación	: 08/09/2016
* PR de Creación	: OSINE-791
* Autor			: Alexander Vilca Narvaez
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================              |               |                              |
*                |               |                              |
* 
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
     <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/obligacion/obligacionDsrComentarioEjecucionMedida.js" />'
        charset="utf-8"></script>
</head>
<body>
    <div class="filaForm">
        <div class="lbl-large" style="padding: 10px 0px 10px 2px"><label>Ejecución Medida (*) :</label></div>
        <textarea id="txtComentario" rows="10"  disabled="disabled" class="" style="width:95%" maxlength="1000">${comentario}</textarea>  
    </div>
    <div class="botones">
        <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
    </div>
</body>
</html>