<%--
* Resumen		
* Objeto		: ordenServicioAtender.jsp
* Descripción		: ordenServicioAtender
* Fecha de Creación	: 26/07/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE791       |  17/08/2016   |   Yadira Piskulich           |     Abrir Supervision DSR
*
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioAtender.js" />' charset="utf-8"></script>
    	<style type="text/css">
    	.ui-jqgrid{
    		text-transform: none !important;
    	}
    </style>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSAte" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSAte" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSAte" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSAte" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSAte" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSAte" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdOrdenServicioOSAte" value="${r.ordenServicio.idOrdenServicio}">
        <!-- OSINE_SFS-Ajustes - mdiosesf - Inicio  -->
        <input type="hidden" id="txtNumeroOrdenServicioOSAte" value="${r.ordenServicio.numeroOrdenServicio}">
        <!-- OSINE_SFS-Ajustes - mdiosesf - Fin -->
        <input type="hidden" id="txtIteracionOSAte" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIdObligacionTipoAte" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProcesoAte" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividadAte"  />
        <input type="hidden" id="txtFlagSupervisionAte" value="${flagSupervision}">
        <input type="hidden" id="txtIdObligacionSubTipoOSAte" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        <input type="hidden" id="txtCodigoOsineOSAte" value="${r.unidadSupervisada.codigoOsinergmin}" />
        <input type="hidden" id="txtRucUnidadSupervisadaOSAte" value="${r.unidadSupervisada.ruc}" />
        
        <div class="form">
            <form>
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Orden de Servicio</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-medium"><label>Número Orden Servicio:</label></div>
                            <div class="ipt-medium">
                                <span class="txt-subtitle">${r.ordenServicio.numeroOrdenServicio}</span>
                            </div>
                                <div class="lbl-small"><label>Fecha Creación:</label></div>
                                <div class="ipt-small">
                                    <span class="txt-subtitle">${r.ordenServicio.fechaCreacionOS}</span>
                                </div>
                            <div class="lbl-medium"><label>Número Expediente:</label></div>
                            <div>
                                <span class="txt-subtitle">${r.numeroExpediente}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Datos de Asignación</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small"><label>Flujo Siged(*):</label></div>
                            <div>
                                <select id="slctIdFlujoSigedOSAte" class="slct-medium-small-medium" disabled>
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoFlujoSiged}" var="t">
                                        <option value='${t.idFlujoSiged}' codigoSiged="${t.codigoSiged}" <c:if test="${r.flujoSiged.idFlujoSiged==t.idFlujoSiged}">selected</c:if> >${t.nombreFlujoSiged}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>                        
                        <div class="filaForm">
	                    <div class="lbl-small"><label>Asunto Siged(*):</label></div>
	                    <div>
	                        <input id="txtAsuntoSigedOSAte" type="text" maxlength="500" class="ipt-medium-small-medium-small-medium" value='${r.asuntoSiged}' disabled />
	                    </div>                          	
                        </div>                        
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <select id="slctIdProcesoOSAte" class="slct-medium" disabled> 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoProceso}" var="p">
                                        <c:if test="${r.flagOrigen==1}">
                                        <option value='${p.idProceso}' <c:if test="${r.proceso.idProceso==p.idProceso}">selected</c:if>>${p.descripcion}</option>
                                        </c:if>
                                        <c:if test="${r.flagOrigen==0}">
                                        <option value='${p.idProceso}' <c:if test="${r.tramite.idProceso==p.idProceso}">selected</c:if>>${p.descripcion}</option>
                                        </c:if>
                                    </c:forEach> 
                                </select>
                            </div>

                            <c:if test="${r.flagOrigen == 0}">
                            <div class="lbl-small vam"><label>Etapa(*):</label></div>
                            <div class="vam">
                                <select id="slctIdEtapaOSAte" class="slct-medium" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>Trámite(*):</label></div>
                            <div class="vam">
                                <select id="slctIdTramiteOSAte" class="slct-medium" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="lbl-small vam"><label for="cmbTipoAsigOS">Tipo Asignación(*):</label></div>
                            <div class="vam">
                                <select class="slct-medium" disabled>   
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoAsignacion}" var="p">
                                        <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna}">selected</c:if>>${p.descripcion}</option>
                                    </c:forEach> 
                                </select>
                            </div>     
                            <c:if test="${r.flagOrigen == 1}">
                            <div class="lbl-small vam"><label for="">Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoSupervisionOSAte" class="slct-medium" disabled> 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>
                            
                            <div id="subTipoOSAte" class="filaForm noMargin" style="display: none;">
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"><label for="">Subtipo Supervisión(*):</label></div>
                                <div class="vam">
                                    <select id="cmbSubTipoSupervisionOSAte" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" disabled>
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Unidad Operativa</span>
                        <!--<input type="hidden" id="txtIdEmpresaSupervisadaOSAte" value="${r.empresaSupervisada.idEmpresaSupervisada}">-->
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small vam" ><label>Unidad Operativa(*):</label></div>
                            <div class="vam">
                                <select id="slctUnidSupeOSAte" class="slct-medium-small-medium" disabled> 
                                    <option value='${r.unidadSupervisada.idUnidadSupervisada}' >${r.unidadSupervisada.codigoOsinergmin} - ${r.unidadSupervisada.nombreUnidad}</option>
                                </select>
                            </div>   
                        </div>
                        <div>                        	                     	
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Rubro:</label></div>
                                <div class="vam">
                                    <input id="txtRubroOSAte" type="text" validate="[O]" class="ipt-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Operativa:</label></div>
                                <div class="vam">
                                	<input id="txtIdDirccionUnidadSuprvisadaOSAte" type="hidden" />  
                                	<input id="txtIdDepartamentoOSAte" type="hidden" />  
                                	<input id="txtIdProvinciaOSAte" type="hidden" /> 
                                	<input id="txtIdDistritoOSAte" type="hidden" /> 
                                        <input id="txtDireOperOSAte" type="text" class="ipt-medium-small-medium-small-medium" disabled /> 
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="cmbTipoDocumento">Tipo/Nro Documento:</label></div>
                                <div class="vam">
                                    <input type="text" validate="[O]" class="ipt-medium-small" value="RUC - ${r.unidadSupervisada.ruc}" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Razón Social:</label></div>
                                <div class="vam">
                                    <input type="text" class="ipt-medium-small-medium-small-medium" value="${r.unidadSupervisada.nombreUnidad}" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="filaForm tac">
            	<div class="lbl-medium">
                <c:if test="${flagSupervision==1 && r.ordenServicio.idOrdenServicio!=null}">
                	<input type="button" id="btnRegistroSupervisionAte" class="btn-azul btn-medium" value="Registrar Supervisi&oacute;n">
                </c:if>
                </div>
            </div>

            <div class="pui-subpanel ui-widget-content">
                <div class="pui-subpanel-subtitlebar">
                    <span class="ui-panel-title">Documentos</span>
                </div>
                <div class="pui-subpanel-content">
                    <div class="filaForm tar">
                        <div class="lbl-medium">
                            <input type="button" id="btnSubirDocumentoAte" class="btn-azul btn-small" value="Subir Documento">
                        </div>
                    </div>
                    <div class="filaForm tac">
                        <div>
                            <div class="fila">
                                <div id="gridContenedorFilesOrdenServicioAte" class="content-grilla"></div>
                                <div id="divContextMenuFilesOrdenServicioAte"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="botones">
            <input type="button" id="btnAtenderOrdenServicioAte" class="btn-azul btn-medium" value="Cerrar Orden de Servicio">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>