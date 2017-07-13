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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/unidadOperativa/unidadOperativa.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form" id="formOT">
            <div id="divMensajeValida" class="errorMensaje" style="display: none"></div>
            
            <div class="pui-subpanel ui-widget-content">
                <div class="pui-subpanel-subtitlebar">
                    <span class="ui-panel-title">Datos de Asignación</span>
                </div>
                <div class="pui-subpanel-content">
                    <div class="filaForm">
                        <div class="lbl-small"><label>Flujo Siged:</label></div>
                        <div>
                            <select class="slct-medium" disabled>
                                <option value="">--Seleccione--</option>
                                <c:forEach items="${listadoFlujoSiged}" var="t">
                                    <option value='${t.idFlujoSiged}' <c:if test="${r.flujoSiged.idFlujoSiged==t.idFlujoSiged}">selected</c:if> >${t.nombreFlujoSiged}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="lbl-small"><label>Asunto Siged:</label></div>
                        <div>
                            <input name="" type="text" maxlength="25" class="ipt-medium-small-medium" value="${r.asuntoSiged}" disabled />
                        </div>
                    </div>                    
                </div>
            </div>
            
            <div class="pui-subpanel ui-widget-content">
                <div class="pui-subpanel-subtitlebar">
                    <span class="ui-panel-title">Unidad Operativa</span>
                </div>
                <form id="frmAsignarUO">
                    <input type="hidden" name="idExpediente" value="${r.idExpediente}">
                    <input type="hidden" id="idEmpresaSupervisada" name="idEmpresaSupervisada" value="${r.empresaSupervisada.idEmpresaSupervisada}" />
 					<input type="hidden" id="txtflagOrigenOS" value="${r.flagOrigen}" />
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small"><label>Unidad Operativa:</label></div>
                            <div>
                                <select id="slctUnidSupe" name="unidadSupervisada.idUnidadSupervisada" validate="[O]" class="slct-medium-small-medium" >
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoUnidadSupervisada}" var="t">
                                        <option value='${t.idUnidadSupervisada}' <c:if test="${t.idUnidadSupervisada==r.unidadSupervisada.idUnidadSupervisada}">selected</c:if> >${t.codigoOsinergmin} - ${t.nombreUnidad}</option>
                                    </c:forEach>
                                </select>
                            </div>   
                            <div class="lbl-medium vam">
                                <input type="button" id="btnCrearUnidOper" class="btn-azul btn-medium" value="Crear Unidad Operativa">
                            </div>
                        </div>
                        <div id="contentCliente">
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Rubro:</label></div>
                                <div class="vam">
                                    <input id="txtRubroUO" type="text" maxlength="25" class="ipt-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Operativa:</label></div>
                                <div class="vam">
                                    <input id="txtDireOperUO" type="text" maxlength="25" class="ipt-medium-small-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="cmbTipoDocumento">Tipo/Nro Documento:</label></div>
                                <div class="vam">
                                    <input id="txtRucUO" type="text" maxlength="25" class="ipt-medium-small" value="${r.empresaSupervisada.tipoDocumentoIdentidad.descripcion} - ${r.empresaSupervisada.nroIdentificacion}" disabled />
                                </div>
                            <div class="lbl-medium vam"><label for="">Razón Social:</label></div>
                                <div class="vam">
                                    <input id="txtRazonSocialUO" type="text" maxlength="25" class="ipt-medium-small" value="${r.empresaSupervisada.razonSocial}" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>            
            
        </div>
        <div class="botones">
            <input type="button" id="btnAsignarUnidadOperativa" class="btn-azul btn-small" value="Asignar">
            <input type="button" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
