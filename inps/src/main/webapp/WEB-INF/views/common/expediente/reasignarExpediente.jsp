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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/expediente/reasignarExpediente.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
            <form id="frmReasignarExpediente">
                <div id="divMensajeValidaRE" class="errorMensaje" style="display: none"></div>
                <div class="filaForm">
                    <div class="lbl-medium"><label class="fwb">Número Expediente:</label></div>
                    <div class="ipt-medium-small">
                        <span class="fwb">${r.numeroExpediente}</span>
                        <input type="hidden" id="idExpedienteRE" value="${r.idExpediente}">
                        <input type="hidden" id="numeroExpediente" value="${r.numeroExpediente}">
                    </div>
                </div>
                <div class="filaForm">
                    <div class="lbl-medium"><label for="slctIdPersonalDestRE">Especialista(*):</label></div>
                    <div>
                        <select id="slctIdPersonalDestRE" validate="[O]" class="slct-medium-small-medium" >
                            <option value="">--Seleccione--</option>
                            <c:forEach items="${listadoDestinatario}" var="t">
                                <option value='${t.idPersonal}'>${t.nombre} ${t.apellidoPaterno} ${t.apellidoMaterno}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="filaForm">
                    <div class="lbl-medium vat"><label for="txtMotivoReasignacionRE">Motivo(*):</label></div>
                    <div>
                        <textarea validate="[O]" id="txtMotivoReasignacionRE" class="ipt-medium-small-medium" maxlength="400"></textarea>
                    </div>
                </div>
            </form>
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="botones">
            <input type="button" id="btnGuardarReasignarExpediente" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
