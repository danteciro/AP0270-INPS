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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicio.js" />' charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOS" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOS" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOS" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOS" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOS" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOS" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdLocadorOS" value="${r.ordenServicio.locador.idLocador}">
        <input type="hidden" id="txtIdSupervisoraEmpresaOS" value="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa}">
        <!--<input type="hidden" id="txtRUCSupervisoraEmpresaOS" value="${r.ordenServicio.supervisoraEmpresa.ruc}">-->
        <input type="hidden" id="txtIdOrdenServicioOS" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIteracionOS" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIteracionExp" value="${r.iteracionExpediente}">
        <input type="hidden" id="txtIdObligacionTipo" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProceso" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividad"  />
        <input type="hidden" id="txtFlagSupervision" value="${flagSupervision}">
        <input type="hidden" id="txtComentarioAnulacion" value="${r.ordenServicio.comentarioDevolucion}">
        <input type="hidden" id="txtComentarioEditar" value="${r.ordenServicio.comentarioDevolucion}">
        <input type="hidden" id="txtFiltrosOSI" value="${confFiltros}" />        
        <input type="hidden" id="descFiltroConfiguradoOSI" value="${nombreXfiltroConfigurado}" />
        <input type="hidden" id="txtFlagMuestral" value="${r.flagMuestral}" />
        <input type="hidden" id="txtIdObligacionSubTipoOS" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        <input type="hidden" id="txtCodigoTipoSupervisor" value="<c:choose><c:when test="${r.ordenServicio.locador.idLocador!=null}">1</c:when><c:when test="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa!=null}">2</c:when></c:choose>" />
        <input type="hidden" id="flagEvaluaTipoAsignacion" value="${r.flagEvaluaTipoAsignacion}" />
        
        <div class="form" id="divFormOS">
            <form id="frmOS">
                <div id="divMensajeValidaFrmOS" class="errorMensaje" style="display: none"></div>

                <c:if test="${tipo!='asignar' && tipo!='generar'}">
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Orden de Servicio</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <c:if test="${tipo!='documentos'}">
                            <div class="lbl-medium"><label>Número Orden Servicio:</label></div>
                            <div class="ipt-medium">
                                <span class="txt-subtitle" id="lblNumeroOrdenServicio">${r.ordenServicio.numeroOrdenServicio}</span>
                            </div>
                            </c:if>
                            <c:if test="${tipo=='atender'}">
                                <div class="lbl-small"><label>Fecha Creación:</label></div>
                                <div class="ipt-small">
                                    <span class="txt-subtitle">${r.ordenServicio.fechaCreacionOS}</span>
                                </div>
                            </c:if>
                            <div class="lbl-medium"><label>Número Expediente:</label></div>
                            <div>
                                <span class="txt-subtitle">${r.numeroExpediente}</span>
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>

                <c:if test="${tipo!='documentos'}">
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Datos de Asignación</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small"><label>Flujo Siged(*):</label></div>
                            <div>
                                <select id="slctIdFlujoSigedOS" class="slct-medium-small-medium" <c:if test="${(tipo!='generar' && tipo!='editar') || r.flagMuestral=='1'}">disabled</c:if>>
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
	                        <input id="txtAsuntoSigedOS" type="text" maxlength="500" class="ipt-medium-small-medium-small-medium" value="${r.asuntoSiged}" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || (tipo=='asignar' && r.iteracionExpediente>0) || r.flagMuestral=='1'}">disabled</c:if> />
	                    </div>                          	
                        </div>                        
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <!-- OSINE_SFS-480 - RSIS 45 - Inicio -->
                                <select id="slctIdProcesoOS" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || (tipo=='asignar' && r.iteracionExpediente>0) || r.flagMuestral=='1'}">disabled</c:if> > 
                                <!-- OSINE_SFS-480 - RSIS 45 - Fin -->    
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
                                <select id="slctIdEtapaOS" class="slct-medium" <c:if test="${tipo!='asignar'}">disabled</c:if> >
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>Trámite(*):</label></div>
                            <div class="vam">
                                <select id="slctIdTramiteOS" class="slct-medium" <c:if test="${tipo!='asignar'}">disabled</c:if> >
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
                            </c:if>
                            <div class="lbl-small vam"><label for="cmbTipoAsigOS">Tipo Asignación(*):</label></div>
                            <div class="vam">
                                <!-- OSINE_SFS-480 - RSIS 45 - Inicio -->
                                <select id="cmbTipoAsigOS" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || r.flagMuestral=='1'}">disabled</c:if> > 
                                <!-- OSINE_SFS-480 - RSIS 45 - Fin -->    
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoAsignacion}" var="p">
                                        <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna || (tipo=='asignar' && r.iteracionExpediente>0 && p.descripcion=='SIN VISITA')}">selected</c:if>>${p.descripcion}</option>
                                    </c:forEach> 
                                </select>
                            </div>     
                            <c:if test="${r.flagOrigen == 1}">
                            <div class="lbl-small vam"><label for="">Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <!-- OSINE_SFS-480 - RSIS 45 - Inicio -->
                                <select id="cmbTipoSupervisionOS" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || (tipo=='asignar' && r.iteracionExpediente>0) || r.flagMuestral=='1'}">disabled</c:if> > 
                                <!-- OSINE_SFS-480 - RSIS 45 - Fin -->
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>
                            
                            <!-- OSINE_SFS-480 - RSIS 27 - Inicio -->
                            <div id="subTipoOS" class="filaForm noMargin" style="display: none;">
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"></div>
                                <div class="lbl-medium vam"></div>
                                <div class="lbl-small vam"><label for="">Subtipo Supervisión(*):</label></div>
                                <div class="vam">
                                    <select id="cmbSubTipoSupervisionOS" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar' && tipo!='editar') || (tipo=='asignar' && r.iteracionExpediente>0) || r.flagMuestral=='1'}">disabled</c:if> >
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                            </div>
                            <!-- OSINE_SFS-480 - RSIS 27 - Fin -->

                        </div>
                    </div>
                </div>
                </c:if>
                <c:if test="${tipo!='documentos'}">
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Unidad Operativa</span>
                        <!--<input type="hidden" id="txtIdEmpresaSupervisadaOS" value="${r.empresaSupervisada.idEmpresaSupervisada}">-->
                    </div>
                    <div class="pui-subpanel-content">
                        <c:if test="${tipo!='generar'}">
                        <div class="filaForm" <c:if test="${(tipo=='editar')}">style="display:none;"</c:if>>
                            <div class="lbl-small vam" ><label>Unidad Operativa(*):</label></div>
                            <div class="vam">
                                <select id="slctUnidSupeOS" class="slct-medium-small-medium" <c:if test="${(tipo!='asignar') || (tipo=='asignar' && r.iteracionExpediente>0)}">disabled</c:if> > 
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
                            <!--EMPSUP-->
<!--                            < test="${tipo=='asignar' && (r.iteracionExpediente==null || r.iteracionExpediente==0)}">    
                            <div class="lbl-medium vam">
                                <input type="button" id="btnCrearUnidOperOrdeServ" class="btn-azul btn-medium" value="Crear Unidad Operativa">
                            </div>
                            <>-->
                                
                        </div>
                        </c:if>
                        <!--OSINE_SFS-480 - RSIS 06 - Inicio --> 
                        <c:if test="${tipo=='editar'}">
                        <div class="filaForm">
                            <div class="lbl-small vam"><label for="">Código OSINERGMIN(*):</label></div>
                            <div class="vam">
                                <input id="codigoOsinergOS" name="" type="text" maxlength="25" class="ipt-medium" value="${r.unidadSupervisada.codigoOsinergmin}" <c:if test="${r.flagMuestral=='1'}">disabled</c:if>/>
                                <input id="txtIdUnidadSupervisadaOS" type="hidden" />
                                <input id="txtRucUnidadSupervisadaOS" type="hidden" />
                            </div>
                            <div class="lbl-small vam">
                                <input type="button" id="btnBuscarClienteOSEditar" class="btn-azul btn-small" value="Buscar" <c:if test="${r.flagMuestral=='1'}">disabled</c:if>>                            
                            </div>
                        </div>
                        </c:if>
                        <!--OSINE_SFS-480 - RSIS 06 - Fin --> 
                        <c:if test="${tipo=='generar'}">
                        <div class="filaForm">
                            <div class="lbl-small vam"><label for="">Código OSINERGMIN(*):</label></div>
                            <div class="vam">
                                <input id="codigoOsinergOS" name="" type="text" maxlength="25" class="ipt-medium" />
                                <input id="txtIdUnidadSupervisadaOS" type="hidden" />
                                <input id="txtRucUnidadSupervisadaOS" type="hidden" />
                            </div>
                            <div class="lbl-small vam">
                                <input type="button" id="btnBuscarClienteOS" class="btn-azul btn-small" value="Buscar">                            
                            </div>
                        </div>
                        </c:if>
                        <div>                        	                     	
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Rubro:</label></div>
                                <div class="vam">
<!--                                	<input id="txtIdActividadOS" type="hidden" />   OSINE_SFS-480 - RSIS11 - Inicio     -->
                                    <input id="txtRubroOS" type="text" validate="[O]" class="ipt-medium-small-medium" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Operativa:</label></div>
                                <div class="vam">
                                    <!-- OSINE_SFS-480 - RSIS 11 - Inicio  --> 
                                	<input id="txtIdDepartamentoOS" type="hidden" />  
                                	<input id="txtIdProvinciaOS" type="hidden" /> 
                                	<input id="txtIdDistritoOS" type="hidden" /> 
                                        <input id="txtDireOperOS" type="text" class="ipt-medium-small-medium-small-medium" disabled /> 
                                    <!-- OSINE_SFS-480 - RSIS 11 - Fin  --> 
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="cmbTipoDocumento">Tipo/Nro Documento:</label></div>
                                <div class="vam">
                                    <input id="nroDocumentoOS" name="" type="text" validate="[O]" class="ipt-medium-small" value="RUC - ${r.unidadSupervisada.ruc}" disabled />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Razón Social:</label></div>
                                <div class="vam">
                                    <input id="razonSocialOS" type="text" class="ipt-medium-small-medium-small-medium" value="${r.unidadSupervisada.nombreUnidad}" disabled />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>

                <c:if test="${tipo!='documentos' && tipo!='atender'}">
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Empresa Supervisora</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small vam"><label for="slctTipoEmprSupeOS">Tipo(*):</label></div>
                            <div class="vam">
                            	<!-- /* OSINE_SFS-480 - RSIS 11 - Inicio -->
                                <!--  <select id="slctEntidadOS" style="display:none;">                                 
                                    <c:forEach items="${listadoEntidad}" var="t">
                                        <option value='${t.codigo}' <c:if test="${t.codigo == '1'}">selected</c:if>>${t.descripcion}</option>
                                    </c:forEach>                             
                                </select> 								
                                <!-- <select id="slctTipoEmprSupeOS" class="slct-medium-small" <c:if test="${tipo!='asignar' && tipo!='generar'}">disabled</c:if>> -->  
                                <!-- /* OSINE_SFS-480 - RSIS 11 - Fin -->
                                <select id="slctTipoEmprSupeOS" class="slct-medium-small" disabled>
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
                                
                            <div class="lbl-medium vam"><label for="slctEmprSupeOS">Empresa Supervisora(*):</label></div>
                            <div class="vam">
                                <select id="slctEmprSupeOS" class="slct-medium-small" disabled <c:if test="${tipo!='asignar' && tipo!='generar' && tipo!='editar'}">style="display:none;"</c:if>>
                                    <option value="">--Seleccione--</option>
                                </select>
                                <c:if test="${tipo!='asignar' && tipo!='generar' && tipo!='editar'}">
                                    <input type="text" class="ipt-medium-small" disabled value="${r.ordenServicio.locador.nombreCompleto} ${r.ordenServicio.supervisoraEmpresa.razonSocial}"/>                                
                                </c:if>
                            </div>       
                            
                        </div>
                    </div>
                    <c:if test="${tipo=='editar' || tipo=='asignar'}">
                    	<div id="txtObligatorioUnidadOrganicaOS" class="txt-obligatorio" style="display:none;"></div>
                    </c:if>                    
                </div>
                </c:if>
                <!-- OSINE_SFS-480 - RSIS 42 - Inicio -->
                <c:if test="${tipo=='anular'}">
                	<div class="pui-subpanel ui-widget-content" disabled="false">
	                    <div class="pui-subpanel-subtitlebar">
	                        <span class="ui-panel-title">Anulación de Orden de Servicio</span>
	                    </div>
	                    <div class="pui-subpanel-content">
	                        <div class="filaForm">
	                            <div class="lbl-small vam"><label for="slctMotivoAnular">Motivo:</label></div>
		                        <div class="vam">
		                            <select id="slctMotivoAnular" class="slct-medium" name="slctMotivoAnular"  disabled="">
		                            	<c:forEach items="${tipoMotivo}" var="p">
                                        	<option value='${p.idMaestroColumna}'>${p.descripcion}</option>

                                    </c:forEach> 
		                            </select>
		                        </div>
	                        </div>
	                        <div class="filaForm">
			                  <div class="lbl-small vat"><label for="txtComentarioAnular">Descripción:</label></div>
			                  <div>
			                      <textarea id="txtComentarioAnular" style="width: 620px" name="txtComentarioAnular" disabled="">${r.ordenServicio.comentarioDevolucion}</textarea>
			                  </div>
			              </div>
	                    </div>
                	</div>
                </c:if>
                <!-- OSINE_SFS-480 - RSIS 42 - Fin -->
                <!-- OSINE_SFS-480 - RSIS 47- Inicio -->               
                <c:if test="${tipo=='editar'}">
                	<div class="pui-subpanel ui-widget-content" disabled="false">
	                    <div class="pui-subpanel-subtitlebar">
	                        <span class="ui-panel-title">Editar Orden de Servicio</span>
	                    </div>
	                    <div class="pui-subpanel-content">
	                        <div class="filaForm">
	                            <div class="lbl-small vam"><label for="slctMotivoEditar">Motivo:</label></div>
		                        <div class="vam">
		                            <select id="slctMotivoEditar" class="slct-medium-small" name="slctMotivoEditar"  disabled="">
		                            	<c:forEach items="${tipoMotivo}" var="p">
                                                <!-- OSINE791 - RSIS34 - Inicio -->
                                        	<option codigo='${p.codigo}' value='${p.idMaestroColumna}' >${p.descripcion}</option>
                                                <!-- OSINE791 - RSIS34 - Fin -->
                                    </c:forEach> 
		                            </select>
		                        </div>
	                        </div>
	                        <div class="filaForm">
			                  <div class="lbl-small vat"><label for="txtComentarioAnular">Descripción:</label></div>
			                  <div>
			                      <textarea id="txtComentarioAnular" style="width: 620px" name="txtComentarioAnular" disabled="">${r.ordenServicio.comentarioDevolucion}</textarea>
			                  </div>
			              </div>
	                    </div>
                	</div>
                </c:if>
                <!-- OSINE_SFS-480 - RSIS 47 - Fin -->
            </form>
            <div class="filaForm tac">
            	<div class="lbl-medium">
                <c:if test="${flagSupervision==1 && r.ordenServicio.idOrdenServicio!=null && tipo!='editar'}">
                	<input type="button" id="btnRegistroSupervision" class="btn-azul btn-medium" 
                	value="<c:if test="${tipo=='atender'}">Registrar Supervisi&oacute;n</c:if><c:if test="${tipo!='atender'}">Ver Supervisi&oacute;n </c:if>">
                </c:if>
                </div>
            </div>

            <c:if test="${tipo!='generar'}">
            <div class="pui-subpanel ui-widget-content">
                <div class="pui-subpanel-subtitlebar">
                    <span class="ui-panel-title">Documentos</span>
                </div>
                <div class="pui-subpanel-content">
                    <c:if test="${tipo=='atender'}">
                    <div class="filaForm tar">
                        <div class="lbl-medium">
                            <input type="button" id="btnSubirDocumento" class="btn-azul btn-small" value="Subir Documento">
                        </div>
                    </div>
                    </c:if>
                    <div class="filaForm tac">
                        <div>
                            <div class="fila">
                                <div id="gridContenedorFilesOrdenServicio" class="content-grilla"></div>
                                <div id="divContextMenuFilesOrdenServicio"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>   
            
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        
        <div class="form" id="formObserva" style="display: none;">
            <form id="frmObservarOS">
                <div id="divMensajeValidaObserva" class="errorMensaje" style="display: none"></div>
                <div class="filaForm">
                    <div class="lbl-medium vat"><label class="fwb" for="txtMotivoObser">Motivo(*):</label></div>
                    <div>
                        <textarea id="txtMotivoObserOS" style="width: 620px" name="txtMotivoObser" validate="[O]" maxlength="400"></textarea>
                    </div>
                </div>
            </form>
        </div>
        
        <div class="botones">
            <div id="btnsAccPrinc" class="ilb">
                <c:if test="${tipo=='atender'}">
                <input type="button" id="btnAtenderOrdenServicio" class="btn-azul btn-medium" value="Cerrar Orden de Servicio">
                </c:if>
                <!-- OSINE_SFS-480 - RSIS 42 - Inicio -->
            	<c:if test="${tipo=='anular'}">
                <input type="button" id="btnAnularOrdenServicio" class="btn-azul btn-small" value="Anular">
                </c:if>
            	<!-- OSINE_SFS-480 - RSIS 42 - Fin -->
                 <!-- OSINE_SFS-480 - RSIS 47 - Inicio -->
            	<c:if test="${tipo=='editar'}">
                <input type="button" id="btnEditarOrdenServicio" class="btn-azul btn-small" value="Editar">
                </c:if>
            	<!-- OSINE_SFS-480 - RSIS 47 - Fin -->
                <!-- htorress - RSIS 18 - Inicio -->
            	<c:if test="${destinatario=='1'}">
            	<!-- htorress - RSIS 18 - Fin -->
                <c:if test="${tipo=='revisar'}">
                <input type="button" id="btnRevisarOrdenServicio" class="btn-azul btn-small" value="Conforme">
                </c:if>
                <c:if test="${tipo=='aprobar'}">
                <input type="button" id="btnAprobarOrdenServicio" class="btn-azul btn-small" value="Aprobar">
                </c:if>
                <c:if test="${tipo=='notificar'}">
                <input type="button" id="btnNotificarOrdenServicio" class="btn-azul btn-small" value="Notificar">
                </c:if>
                <c:if test="${tipo=='concluir'}">
                <input type="button" id="btnConcluirOrdenServicio" class="btn-azul btn-small" value="Concluir OS">
                </c:if>
                <!-- htorress - RSIS 18 - Inicio -->
                </c:if>
                <!-- htorress - RSIS 18 - Fin -->
                <c:if test="${tipo=='asignar'}">
                <input type="button" id="btnAsignarOS" class="btn-azul btn-small" value="Generar Orden">
                </c:if>
                <c:if test="${tipo=='generar'}">
                <input type="button" id="btnGenerarOS" class="btn-azul btn-small" value="Generar Orden">
                </c:if>
                <!-- htorress - RSIS 18 - Inicio -->
            	<!-- <c:if test="${destinatario=='1'}">-->
            	<!-- htorress - RSIS 18 - Fin -->
                <c:if test="${tipo=='confirmardescargo'}">
                <input type="button" id="btnConfirmarDescargoOS" class="btn-azul btn-medium" value="Confirmar Descargo">
                </c:if>
                <!-- htorress - RSIS 18 - Inicio -->
                <!-- </c:if> -->
                <!-- htorress - RSIS 18 - Fin -->
            </div>
            <!-- htorress - RSIS 18 - Inicio -->
            <c:if test="${destinatario=='1'}">
            <!-- htorress - RSIS 18 - Fin -->
            <c:if test="${tipo=='revisar'}">
            <input type="button" id="btnObservarOS" class="btn-azul btn-medium" value="Observar / Devolver">
            <input type="button" id="btnConfirmaObservarOS" class="btn-azul btn-small" value="Confirmar" style="display:none;">
            </c:if>
            <!-- htorress - RSIS 16 - Inicio -->
            <c:if test="${tipo=='aprobar'}">
            <input type="button" id="btnObservarOSAprobar" class="btn-azul btn-medium" value="Observar / Devolver">
            <input type="button" id="btnConfirmaObservarOSAprobar" class="btn-azul btn-small" value="Confirmar" style="display:none;">
            </c:if>
            <!-- htorress - RSIS 16 - Fin -->
            <!-- htorress - RSIS 18 - Inicio -->
            </c:if>
            <!-- htorress - RSIS 18 - Fin -->
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            <!-- OSINE_SFS-480 - RSIS 17 - Inicio -->
            <div id="divOficioFechaFin" style="display: none;" class="dialog">
                <form id="frmConcluirOS">
                    <div style="display: none" class="errorMensaje" id="divMensajeValidaFrmConcluirOS"></div>
                    <div class="filaForm">
                        <div class="lbl-small"><label for="slctOficio">Oficio(*):</label></div>
                        <div class="vam">
                            <select id="slctOficio" class="slct-medium" validate="[O]"></select>
                        </div>
                    </div>		
                    <div class="filaForm">
                        <div class="lbl-small"><label for="fechaRecepcion">Fecha Recepción(*):</label></div>
                        <div class="vam"><input id="fechaRecepcion" type="text" validate="[O]" maxlength="10" class="ipt-medium" /></div>
                    </div>
                </form>
                <div class="botones">					
                    <div class="vam">
                        <input type="button" id="btnAceptarRecepcion" class="btn-azul btn-small" value="Guardar">
                        <input type="button" id="btnCerrarRecepcion" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
                    </div>
                </div>
            </div>
            <!-- OSINE_SFS-480 - RSIS 17 - Fin -->
        </div>
    </body>
</html>