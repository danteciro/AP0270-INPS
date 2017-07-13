<%--
* Resumen		
* Objeto		: ordenServicioConcluir.jsp
* Descripción		: ordenServicioConcluir
* Fecha de Creación	: 10/08/2016
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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioConcluir.js" />' charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSConc" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSConc" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSConc" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSConc" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSConc" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSConc" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdLocadorOSConc" value="${r.ordenServicio.locador.idLocador}">
        <input type="hidden" id="txtIdSupervisoraEmpresaOSConc" value="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa}">
        <input type="hidden" id="txtIdOrdenServicioOSConc" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIteracionOSConc" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIdObligacionTipoConc" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProcesoConc" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividadConc"  />
        <input type="hidden" id="txtIdObligacionSubTipoOSConc" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        
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
	                        <input type="text" maxlength="1000" class="ipt-medium-small-medium-small-medium" value="${r.asuntoSiged}" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || (tipo=='asignar' && r.iteracionExpediente>0) || r.flagMuestral=='1'}">disabled</c:if> />
	                    </div>                          	
                        </div>                        
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <select id="slctIdProcesoOSConc" class="slct-medium" disabled > 
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
                                <select id="slctIdEtapaOSConc" class="slct-medium" disabled >
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>Trámite(*):</label></div>
                            <div class="vam">
                                <select id="slctIdTramiteOSConc" class="slct-medium" disabled >
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
                            <div class="lbl-small vam"><label for="">Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoSupervisionOSConc" class="slct-medium" disabled > 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>
                            
                            <div id="subTipoOSConc" class="filaForm noMargin" style="display: none;">
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"><label for="">Subtipo Supervisión(*):</label></div>
                                <div class="vam">
                                    <select id="cmbSubTipoSupervisionOSConc" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || (tipo=='asignar' && r.iteracionExpediente>0) || r.flagMuestral=='1'}">disabled</c:if> >
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
                        <input type="hidden" value="${r.empresaSupervisada.idEmpresaSupervisada}">
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small vam" ><label>Unidad Operativa(*):</label></div>
                            <div class="vam">
                                <select id="slctUnidSupeOSConc" class="slct-medium-small-medium" disabled > 
                                    <option value='${r.unidadSupervisada.idUnidadSupervisada}' >${r.unidadSupervisada.codigoOsinergmin} - ${r.unidadSupervisada.nombreUnidad}</option>
                                </select>
                            </div>                               
                        </div>
                        
                        <div>                        	                     	
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Rubro:</label></div>
                                <div class="vam">
                                    <input id="txtRubroOSConc" type="text" validate="[O]" class="ipt-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Operativa:</label></div>
                                <div class="vam">
                                	<input id="txtIdDepartamentoOSConc" type="hidden" />  
                                	<input id="txtIdProvinciaOSConc" type="hidden" /> 
                                	<input id="txtIdDistritoOSConc" type="hidden" /> 
                                        <input id="txtDireOperOSConc" type="text" class="ipt-medium-small-medium-small-medium" disabled /> 
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Tipo/Nro Documento:</label></div>
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
                
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Empresa Supervisora</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small vam"><label>Tipo(*):</label></div>
                            <div class="vam">
                            	<select id="slctTipoEmprSupeOSConc" class="slct-medium-small" disabled>
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
                                <select id="slctEmprSupeOSConc" class="slct-medium-small" disabled style="display:none;">
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
                    <input type="button" id="btnRegistroSupervisionConc" class="btn-azul btn-medium" value="Ver Supervisi&oacute;n">
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
                                <div id="gridContenedorFilesOrdenServicioConc" class="content-grilla"></div>
                                <div id="divContextMenuFilesOrdenServicioConc"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="botones">
            <div class="ilb">
                <c:if test="${destinatario=='1'}">
            	<input type="button" id="btnConcluirOrdenServicioConc" class="btn-azul btn-small" value="Concluir OS">
                </c:if>                
            </div>
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            
            <div id="divOficioFechaFinConc" style="display: none;" class="dialog">
                <form id="frmConcluirOSConc">
                    <div style="display: none" class="errorMensaje" id="divMensajeValidaFrmConcluirOSConc"></div>
                    <div class="filaForm">
                        <div class="lbl-small"><label for="slctOficio">Oficio(*):</label></div>
                        <div class="vam">
                            <select id="slctOficioConc" class="slct-medium" validate="[O]"></select>
                        </div>
                    </div>		
                    <div class="filaForm">
                        <div class="lbl-small"><label for="fechaRecepcionConc">Fecha Recepción(*):</label></div>
                        <div class="vam"><input id="fechaRecepcionConc" type="text" validate="[O]" maxlength="10" class="ipt-medium" /></div>
                    </div>
                </form>
                <div class="botones">					
                    <input type="button" id="btnAceptarRecepcionConc" class="btn-azul btn-small" value="Guardar">
                    <input type="button"  title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">                    
                </div>
            </div>
            
        </div>
    </body>
</html>