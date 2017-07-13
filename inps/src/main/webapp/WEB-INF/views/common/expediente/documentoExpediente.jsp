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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/expediente/documentoExpediente.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form" id="formOT">
            <div id="divMensajeValida" class="errorMensaje" style="display: none"></div>
            
            <div class="filaForm">
                <div class="lbl-medium"><label>Número Expediente:</label></div>
                <div>
                    <span class="txt-subtitle" id="txtNroExpeDocuExpe">${r.numeroExpediente}</span>
                </div>
            </div>
            <div class="filaForm tac">
                <div>
                    <div class="fila">
                        <div id="gridContenedorDocuExpe" class="content-grilla"></div>
                        <div id="divContextMenuDocuExpe"></div>
                    </div>
                </div>
            </div>
               
        </div>
        
        <div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
