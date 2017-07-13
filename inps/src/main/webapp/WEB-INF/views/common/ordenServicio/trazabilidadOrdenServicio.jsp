<%-- 
    Document   : trazabilidadOrdenServicio
    Created on : 21/07/2015, 09:47:10 AM
    Author     : jpiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/trazabilidadOrdenServicio.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
            <input type="hidden" id="idExpedienteTrazaOS" value="${r.idExpediente}">
            <input type="hidden" id="idOrdenServicioTrazaOS" value="${r.ordenServicio.idOrdenServicio}">
            <div class="filaForm">
                <div class="lbl-medium"><label>Número Expediente:</label></div>
                <div class="ipt-medium-small">
                    <span class="txt-subtitle" id="txtNroExpediente">${r.numeroExpediente}</span>
                </div>
                <div class="lbl-medium"><label>Número Orden de Servicio:</label></div>
                <div>
                    <span class="txt-subtitle" id="txtNroExpediente">${r.ordenServicio.numeroOrdenServicio}</span>
                </div>
                
            </div>
            <div class="filaForm tac">
                <div>
                    <div class="fila">
                        <div id="gridContenedorTrazaOrdenServ" class="content-grilla"></div>
                        <div id="divContextMenuTrazaOrdenServ"></div>
                    </div>
                </div>
            </div>
               
        </div>
        
        <div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
