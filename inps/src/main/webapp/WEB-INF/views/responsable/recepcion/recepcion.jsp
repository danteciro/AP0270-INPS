<%--
    Document   : inicio
    Created on : 16/06/2015, 04:25:47 PM
    Author     : jpiro
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript"
            src='<c:url value="/javascript/app/responsable/recepcion/recepcion.js" />' charset="utf-8">
    </script>

</head>
<body>
    <div class="botones tar">
        <input type="button" id="btnVerDerivadosResponsable" title="Ver Derivados" class="btn-azul btn-small" value="Ver Derivados">
    </div>
    <div class="form form-large">
        <div class="filaForm">
            <div class="lbl-large"><label class="fwb">Seleccione Expediente(s) a Derivar:</label></div>
        </div>
    </div>
    <div class="button_2 btnRefrescarExpe" title="Refrescar" onclick="respReceRece.procesarGridExpeResponsable();">
            <span class="pui-button-icon-left ui-icon ui-icon-refresh"></span>
        </div>
    <div class="button_2 btnBuscarExpe" title="Buscar" onclick="common.abrirBusquedaExpediente(respReceRece.procesarGridExpeResponsable,constant.identificadorBandeja.resRecepcion);">
        <span class="pui-button-icon-left ui-icon ui-icon-search"></span>
    </div>
    <div class="tac">
        <div id="gridContenedorExpeResponsable" class="content-grilla"></div>
        <div id="divContextMenuExpeResponsable"></div>
    </div>
    <div class="form form-large">
        <form id="derivarExpedidiente">
            <div id="divMensValidaDerivarExpedidiente" class="errorMensaje" tabindex='1' style="display: none" ></div>
            <div class="form form-small vat">
                <div class="filaForm">
                    <div class="lbl-large"><label class="fwb">Expediente(s) seleccionado(s)(*):</label></div>
                </div>
                <div id="expeSeleccionados"></div>
            </div>
            <div class="form form-small vat">
                <div class="filaForm">
                    <div class="lbl-large"><label class="fwb">Destinatario(*):</label></div>
                </div>
                <div class="filaForm">
                    <div>
                        <select id="idDestinatarioDerivar" class="slct-large" validate="[O]">
                            <option value="">--Seleccione--</option>
                            <c:forEach items="${listadoDestinatario}" var="t">
                                <option value='${t.idPersonal}'>${t.nombre} ${t.apellidoPaterno} ${t.apellidoMaterno}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="txt-obligatorio">(*) Campos obligatorios</div>
    </div>
    
    <div class="botones">
        <input type="button" id="btnDerivarExpedientes" title="Derivar" class="btn-azul btn-small" value="Derivar">
    </div>
    
    <!-- dialogs -->
    <div id="dialogExpeDeriResponsable" class="dialog" style="display:none;"></div>
</body>
</html>