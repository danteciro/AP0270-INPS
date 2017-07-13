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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioSupervisor.js" />' charset="utf-8"></script>
    </head>
    <body>
        <fieldset class="tac">
        <div class="form">
            <form id="frmBuscarOrdenServSupervisor">
                <input type="hidden" id="idLocadorOSSuper" value="${p.locador.idLocador}">
                <input type="hidden" id="idSupervisoraEmpresaOSSuper" value="${p.supervisoraEmpresa.idSupervisoraEmpresa}">
                <div class="filaForm">
                    <div class="lbl-medium"><label class="fwb">Estado:</label></div>
                    <div>
                        <select id="slctIdEstaProcOSSup" class="slct-medium">
                            <option value="">--Seleccione--</option>
                            <c:forEach items="${listadoEstadoProcesoOS}" var="t">
                                <option value='${t.idEstadoProceso}'>${t.nombreEstado}</option>
                            </c:forEach>
                        </select>
                    </div>                
                    <div class="lbl-medium"><label class="fwb">Código OSINERGMIN:</label></div>
                    <div>
                        <input id="txtCodOsinergOSSup" name="" type="text" maxlength="12" class="ipt-medium" value="" />
                    </div>                    
                </div>
                <div class="filaForm">
                    <div class="lbl-medium"><label class="fwb">Número Expediente:</label></div>
                    <div>
                        <input id="txtNumeroExpedienteOSSup" name="" type="text" maxlength="25" class="ipt-medium" value="" />
                    </div>                    
                    <div class="lbl-medium"><label class="fwb">Número Orden Servicio:</label></div>
                    <div>
                        <input id="txtNumeroOrdenServicioOSSup" name="" type="text" maxlength="25" class="ipt-medium" value="" />
                    </div>
                </div>
                <div class="filaForm">
                    <div class="lbl-medium"><label class="fwb">Fecha Inicio:</label></div>
                    <div>
                        <input id="txtFechaIniOSSup" name="" type="text" maxlength="25" class="ipt-medium" value="" />
                    </div>
                    <div class="lbl-medium"><label class="fwb">Fecha Fin:</label></div>
                    <div>
                        <input id="txtFechaFinOSSup" name="" type="text" maxlength="25" class="ipt-medium" value="" />
                    </div>
                </div>
            </form>
            <div class="botones">
                <input type="button" id="btnOrdeServSupervisor" class="btn-azul btn-small" value="Buscar">
                <input type="button" id="btnLimpiarOrdeServSupervisor" class="btn-azul btn-small" value="Limpiar">
            </div>
        </div>
        </fieldset>
        <div class="filaForm tac">
            <div>
                <div class="fila">
                    <div id="gridContenedorOrdeServSupe" class="content-grilla"></div>
                    <div id="divContextMenuOrdeServSupe"></div>
                </div>
            </div>
        </div>
        <div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
