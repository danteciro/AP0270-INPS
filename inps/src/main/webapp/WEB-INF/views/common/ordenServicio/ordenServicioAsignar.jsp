<%--
* Resumen		
* Objeto		: ordenServicio.jsp
* Descripción		: ordenServicio
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  05/05/2016   |   Mario Dioses Fernandez     |     Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480  |  20/05/2016   |   Hernán Torres Saenz        |     Crear la interfaz "Anular orden de servicio" de acuerdo a especificaciones
* OSINE_SFS-480  |  31/05/2016   |   Mario Dioses Fernandez     |     Crear la interfaz "Editar asignaciones" de acuerdo a especificaciones
* OSINE_SFS-480  |  01/06/2016   |   Luis García Reyna          |     Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
* OSINE_SFS-480  |  08/06/2016   |   Mario Dioses Fernandez     |     Envio de Datos de Mensajeria a SIGED mediante WS
* OSINE_791-RSIS34| 10/10/2016   |   Cristopher Paucar Torre    |     Editar Orden de Servicio "Cambiar tipo de Asignación".
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioAsignar.js" />' charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSAsig" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSAsig" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSAsig" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSAsig" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSAsig" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSAsig" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdLocadorOSAsig" value="${r.ordenServicio.locador.idLocador}">
        <input type="hidden" id="txtIdSupervisoraEmpresaOSAsig" value="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa}">
        <input type="hidden" id="txtIdOrdenServicioOSAsig" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIdActividadAsig"  />
        <input type="hidden" id="txtFiltrosOSIAsig" value="${confFiltros}" />        
        <input type="hidden" id="descFiltroConfiguradoOSIAsig" value="${nombreXfiltroConfigurado}" />
        <input type="hidden" id="txtIdObligacionSubTipoOSAsig" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        
        <div class="form" id="divFormOS">
            <form id="frmOSAsig">
                <div id="divMensajeValidaFrmOSAsig" class="errorMensaje" style="display: none"></div>

                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Datos de Asignación</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small"><label>Flujo Siged(*):</label></div>
                            <div>
                                <select id="slctIdFlujoSigedOSAsig" class="slct-medium-small-medium" disabled>
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
	                        <input id="txtAsuntoSigedOSAsig" type="text" maxlength="500" class="ipt-medium-small-medium-small-medium" value="${r.asuntoSiged}" <c:if test="${(tipo=='asignar' && r.iteracionExpediente>0)}">disabled</c:if> />
	                    </div>                          	
                        </div>                        
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <select id="slctIdProcesoOSAsig" class="slct-medium" <c:if test="${(tipo=='asignar' && r.iteracionExpediente>0)}">disabled</c:if> > 
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
                                <select id="slctIdEtapaOSAsig" class="slct-medium">
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>Trámite(*):</label></div>
                            <div class="vam">
                                <select id="slctIdTramiteOSAsig" class="slct-medium">
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="lbl-small vam"><label for="cmbTipoAsigOS">Tipo Asignación(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoAsigOSAsig" class="slct-medium"> 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoAsignacion}" var="p">
                                        <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna || (tipo=='asignar' && r.iteracionExpediente>0 && p.descripcion=='SIN VISITA')}">selected</c:if>>${p.descripcion}</option>
                                    </c:forEach> 
                                </select>
                            </div>     
                            <c:if test="${r.flagOrigen == 1}">
                            <div class="lbl-small vam"><label for="">Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoSupervisionOSAsig" class="slct-medium" <c:if test="${(tipo=='asignar' && r.iteracionExpediente>0)}">disabled</c:if> > 
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>
                            
                            <div id="subTipoOSAsig" class="filaForm noMargin" style="display: none;">
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"><label for="">Subtipo Supervisión(*):</label></div>
                                <div class="vam">
                                    <select id="cmbSubTipoSupervisionOSAsig" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" <c:if test="${(tipo=='asignar' && r.iteracionExpediente>0)}">disabled</c:if> >
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
                                <select id="slctUnidSupeOSAsig" class="slct-medium-small-medium" <c:if test="${(tipo=='asignar' && r.iteracionExpediente>0)}">disabled</c:if>> 
                                    <c:if test="${r.unidadSupervisada.idUnidadSupervisada!=null}">
                                        <option value='${r.unidadSupervisada.idUnidadSupervisada}' >${r.unidadSupervisada.codigoOsinergmin} - ${r.unidadSupervisada.nombreUnidad}</option>
                                    </c:if>
                                    <c:if test="${r.unidadSupervisada.idUnidadSupervisada==null}">
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listadoUnidadSupervisada}" var="t">
                                            <option value='${t.idUnidadSupervisada}' <c:if test="${t.idUnidadSupervisada==r.unidadSupervisada.idUnidadSupervisada}">selected</c:if> >${t.codigoOsinergmin} - ${t.nombreUnidad}</option>
                                        </c:forEach>
                                    </c:if>    
                                </select>
                            </div>   
                        </div>                       
                        <div>                        	                     	
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Rubro:</label></div>
                                <div class="vam">
                                    <input id="txtRubroOSAsig" type="text" validate="[O]" class="ipt-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Operativa:</label></div>
                                <div class="vam">
                                    <input id="txtIdDepartamentoOSAsig" type="hidden" />  
                                    <input id="txtIdProvinciaOSAsig" type="hidden" /> 
                                    <input id="txtIdDistritoOSAsig" type="hidden" /> 
                                    <input id="txtDireOperOSAsig" type="text" class="ipt-medium-small-medium-small-medium" disabled /> 
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="cmbTipoDocumentoAsig">Tipo/Nro Documento:</label></div>
                                <div class="vam">
                                    <input id="nroDocumentoOSAsig" name="" type="text" validate="[O]" class="ipt-medium-small" value="RUC - ${r.unidadSupervisada.ruc}" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="razonSocialOSAsig">Razón Social:</label></div>
                                <div class="vam">
                                    <input id="razonSocialOSAsig" type="text" class="ipt-medium-small-medium-small-medium" value="${r.unidadSupervisada.nombreUnidad}" disabled />
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
                            <div class="lbl-small vam"><label for="slctTipoEmprSupeOSAsig">Tipo(*):</label></div>
                            <div class="vam">
                            	<select id="slctTipoEmprSupeOSAsig" class="slct-medium-small" disabled>
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
                                
                            <div class="lbl-medium vam"><label for="slctEmprSupeOSAsig">Empresa Supervisora(*):</label></div>
                            <div class="vam">
                                <select id="slctEmprSupeOSAsig" class="slct-medium-small" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>       
                        </div>
                    </div>
                    <div id="txtObligatorioUnidadOrganicaOSAsig" class="txt-obligatorio" style="display:none;"></div>
                </div>
            </form>
            
            <div class="pui-subpanel ui-widget-content">
                <div class="pui-subpanel-subtitlebar">
                    <span class="ui-panel-title">Documentos</span>
                </div>
                <div class="pui-subpanel-content">
                    <div class="filaForm tac">
                        <div>
                            <div class="fila">
                                <div id="gridContenedorFilesOrdenServicioAsig" class="content-grilla"></div>
                                <div id="divContextMenuFilesOrdenServicioAsig"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="botones">
            <input type="button" id="btnAsignarOSAsig" class="btn-azul btn-small" value="Generar Orden">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>