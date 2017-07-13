<%--
* Resumen		
* Objeto		: ordenServicioConsultar.jsp
* Descripción		: ordenServicioConsultar
* Fecha de Creación	: 02/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
*
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioConsultar.js" />' charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSCon" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSCon" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSCon" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSCon" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSCon" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSCon" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdLocadorOSCon" value="${r.ordenServicio.locador.idLocador}">
        <input type="hidden" id="txtIdSupervisoraEmpresaOSCon" value="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa}">
        <input type="hidden" id="txtIdOrdenServicioOSCon" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIteracionOSCon" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIdObligacionTipoCon" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProcesoCon" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividadCon" />
        <input type="hidden" id="txtIdObligacionSubTipoOSCon" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        
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
                                <select class="slct-medium-small-medium" disabled>
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
	                        <input type="text" maxlength="1000" class="ipt-medium-small-medium-small-medium" value="${r.asuntoSiged}" disabled />
	                    </div>                          	
                        </div>                        
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <select id="slctIdProcesoOSCon" class="slct-medium" disabled > 
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
                                <select id="slctIdEtapaOSCon" class="slct-medium" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>Trámite(*):</label></div>
                            <div class="vam">
                                <select id="slctIdTramiteOSCon" class="slct-medium" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="lbl-small vam"><label>Tipo Asignación(*):</label></div>
                            <div class="vam">
                                <select class="slct-medium" disabled> 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoAsignacion}" var="p">
                                        <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna}">selected</c:if>>${p.descripcion}</option>
                                    </c:forEach> 
                                </select>
                            </div>     
                            <c:if test="${r.flagOrigen == 1}">
                            <div class="lbl-small vam"><label>Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoSupervisionOSCon" class="slct-medium" disabled> 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>
                            
                            <div id="subTipoOSCon" class="filaForm noMargin" style="display: none;">
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"><label for="">Subtipo Supervisión(*):</label></div>
                                <div class="vam">
                                    <select id="cmbSubTipoSupervisionOSCon" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" disabled>
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
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small vam" ><label>Unidad Operativa(*):</label></div>
                            <div class="vam">
                                <select id="slctUnidSupeOSCon" class="slct-medium-small-medium" disabled> 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoUnidadSupervisada}" var="t">
                                        <option value='${t.idUnidadSupervisada}' <c:if test="${t.idUnidadSupervisada==r.unidadSupervisada.idUnidadSupervisada}">selected</c:if> >${t.codigoOsinergmin} - ${t.nombreUnidad}</option>
                                    </c:forEach>
                                </select>
                            </div>                               
                        </div>
                        <div>                        	                     	
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Rubro:</label></div>
                                <div class="vam">
                                    <input id="txtRubroOSCon" type="text" validate="[O]" class="ipt-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Operativa:</label></div>
                                <div class="vam">
                                    	<input id="txtIdDepartamentoOSCon" type="hidden" />  
                                	<input id="txtIdProvinciaOSCon" type="hidden" /> 
                                	<input id="txtIdDistritoOSCon" type="hidden" /> 
                                        <input id="txtDireOperOSCon" type="text" class="ipt-medium-small-medium-small-medium" disabled /> 
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Tipo/Nro Documento:</label></div>
                                <div class="vam">
                                    <input type="text" validate="[O]" class="ipt-medium-small" value="<c:choose><c:when test="${r.empresaSupervisada.ruc!='' && r.empresaSupervisada.ruc!=null}">RUC - ${r.empresaSupervisada.ruc}</c:when><c:otherwise>${r.empresaSupervisada.tipoDocumentoIdentidad.descripcion} - ${r.empresaSupervisada.nroIdentificacion}</c:otherwise></c:choose>" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Razón Social:</label></div>
                                <div class="vam">
                                    <input type="text" class="ipt-medium-small-medium-small-medium" value="${r.empresaSupervisada.razonSocial}" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Empresa Supervisora</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small vam"><label>Tipo(*):</label></div>
                            <div class="vam">
                            	<select id="slctTipoEmprSupeOSCon" class="slct-medium-small" disabled>
                                    <option value="" codigo="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervisor}" var="p">
                                        <c:choose>
                                            <c:when test="${r.ordenServicio.locador.idLocador!=null}">
                                                <option value='${p.idMaestroColumna}' codigo='${p.codigo}' <c:if test="${p.codigo==1}">selected</c:if> >${p.descripcion}</option>
                                            </c:when>
                                            <c:when test="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa!=null}">
                                                <option value='${p.idMaestroColumna}' codigo='${p.codigo}' <c:if test="${p.codigo==2}">selected</c:if> >${p.descripcion}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value='${p.idMaestroColumna}' codigo='${p.codigo}'>${p.descripcion}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>                             
                                </select>
                            </div>
                                
                            <div class="lbl-medium vam"><label>Empresa Supervisora(*):</label></div>
                            <div class="vam">
                                <select id="slctEmprSupeOSCon" class="slct-medium-small" style="display:none;" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                                <input type="text" class="ipt-medium-small" disabled value="${r.ordenServicio.locador.nombreCompleto} ${r.ordenServicio.supervisoraEmpresa.razonSocial}"/>                                
                            </div>       
                            
                        </div>
                    </div>
                </div>
            </form>
            <div class="filaForm tac">
            	<div class="lbl-medium">
                <c:if test="${flagSupervision==1 && r.ordenServicio.idOrdenServicio!=null}">
                    <input type="button" id="btnRegistroSupervisionCon" class="btn-azul btn-medium" value="Ver Supervisi&oacute;n">
                </c:if>
                </div>
            </div>

            <div class="pui-subpanel ui-widget-content">
                <div class="pui-subpanel-subtitlebar">
                    <span class="ui-panel-title">Documentos</span>
                </div>
                <div class="pui-subpanel-content">
                    <div class="filaForm tac">
                        <div>
                            <div class="fila">
                                <div id="gridContenedorFilesOrdenServicioCon" class="content-grilla"></div>
                                <div id="divContextMenuFilesOrdenServicioCon"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">            
        </div>
    </body>
</html>