<%-- 
    Document   : asignarOT
    Created on : 17/06/2015, 12:59:51 PM
    Author     : jpiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/responsable/recepcion/popUpDerivados.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
            <div class="tac">
                <div id="gridContenedorExpeDeriResponsable" class="content-grilla" style="overflow: auto; width: 1090px;"></div>
            </div>
        </div>
        <div class="botones">           
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>

