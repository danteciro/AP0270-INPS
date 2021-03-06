<%--
* Resumen		
* Objeto		: ordenServicioAprobar.jsp
* Descripción		: ordenServicioAprobar
* Fecha de Creación	: 09/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioAprobar.js" />' charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSApr" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSApr" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSApr" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSApr" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSApr" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSApr" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdLocadorOSApr" value="${r.ordenServicio.locador.idLocador}">
        <input type="hidden" id="txtIdSupervisoraEmpresaOSApr" value="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa}">
        <input type="hidden" id="txtIdOrdenServicioOSApr" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIteracionOSApr" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIdObligacionTipoApr" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProcesoApr" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividadApr"  />
        <input type="hidden" id="txtIdObligacionSubTipoOSApr" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        <!-- OSINE791 - RSIS2 - Inicio -->
        <input type="hidden" id="txtMetodoObservarAprobar" value="${metodoObservarAprobar}">
        <!-- OSINE791 - RSIS2 - Fin -->
        
        <div class="form" id="divFormOSApr">
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
	                        <input id="txtAsuntoSigedOSApr" type="text" maxlength="1000" class="ipt-medium-small-medium-small-medium" value="${r.asuntoSiged}" disabled />
	                    </div>                          	
                        </div>                        
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <select id="slctIdProcesoOSApr" class="slct-medium" disabled > 
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
                                <select id="slctIdEtapaOSApr" class="slct-medium" disabled >
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>Trámite(*):</label></div>
                            <div class="vam">
                                <select id="slctIdTramiteOSApr" class="slct-medium" disabled >
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="lbl-small vam"><label>Tipo Asignación(*):</label></div>
                            <div class="vam">
                                <select class="slct-medium" disabled > 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoAsignacion}" var="p">
                                        <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna || (tipo=='asignar' && r.iteracionExpediente>0 && p.descripcion=='SIN VISITA')}">selected</c:if>>${p.descripcion}</option>
                                    </c:forEach> 
                                </select>
                            </div>     
                            <c:if test="${r.flagOrigen == 1}">
                            <div class="lbl-small vam"><label>Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoSupervisionOSApr" class="slct-medium" disabled > 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>
                            
                            <div id="subTipoOSApr" class="filaForm noMargin" style="display: none;">
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"><label for="">Subtipo Supervisión(*):</label></div>
                                <div class="vam">
                                    <select id="cmbSubTipoSupervisionOSApr" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" disabled >
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                </div>
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Unidad Supervisada</span>
                    </div>
                    <div>
                            <div class="tac">
                                <div id="gridContenedorUnidadOperativaTitMinMAS" class="content-grilla"></div>
                                <div id="divContextMenuUnidadOperativaTitMinMAS"></div>
                            </div>
                            <div id="unidOperativaSeleccionadaMAS" style="display:none;"></div> 
                            <input type="hidden" id="txtIdActividadOSMAS"/>
                            <input type="hidden" id="txtIdDepartamentoOSMAS"/>
                            <input type="hidden" id="txtIdProvinciaOSMAS"/>
                            <input type="hidden" id="txtIdDistritoOSMAS"/>
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
                                <select id="slctTipoEmprSupeOSApr" class="slct-medium-small" disabled>
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
                                
                            <div class="lbl-medium vam"><label for="slctEmprSupeOSApr">Empresa Supervisora(*):</label></div>
                            <div class="vam">
                                <select id="slctEmprSupeOSApr" class="slct-medium-small" disabled style="display:none;">
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
                	<input type="button" id="btnRegistroSupervisionApr" class="btn-azul btn-medium" value="Ver Supervisi&oacute;n">
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
                                <div id="gridContenedorFilesOrdenServicioApr" class="content-grilla"></div>
                                <div id="divContextMenuFilesOrdenServicioApr"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="form" id="formObservaApr" style="display: none;">
            <form id="frmObservarOSApr">
                <div id="divMensajeValidaObservaApr" class="errorMensaje" style="display: none"></div>
                <div class="filaForm">
                    <div class="lbl-medium vat"><label class="fwb" for="txtMotivoObser">Motivo(*):</label></div>
                    <div>
                        <textarea id="txtMotivoObserOSApr" style="width: 620px" name="txtMotivoObser" validate="[O]" maxlength="400"></textarea>
                    </div>
                </div>
            </form>
        </div>
        
        <div class="botones">
            <div id="btnsAccPrincApr" class="ilb">
                <c:if test="${destinatario=='1'}">
                    <input type="button" id="btnAprobarOrdenServicioApr" class="btn-azul btn-small" value="Aprobar">
                </c:if>                
            </div>
            <c:if test="${destinatario=='1'}">
                <input type="button" id="btnObservarOSAprobarApr" class="btn-azul btn-medium" value="Observar / Devolver">
                <input type="button" id="btnConfirmaObservarOSAprobarApr" class="btn-azul btn-small" value="Confirmar" style="display:none;">
            </c:if>
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">            
        </div>
    </body>
</html>