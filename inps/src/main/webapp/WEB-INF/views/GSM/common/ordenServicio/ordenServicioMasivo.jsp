<%--
* Resumen		
* Objeto			: ordenServicioMasivo.jsp
* Descripción		: ordenServicioMasivo
* Fecha de Creación	: 25/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernan Torres.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
*
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/common/ordenServicio/ordenServicioMasivo.js" />' charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSMAS" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSMAS" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSMAS" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSMAS" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSMAS" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSMAS" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdLocadorOSMAS" value="${r.ordenServicio.locador.idLocador}">
        <input type="hidden" id="txtIdSupervisoraEmpresaOSMAS" value="${r.ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa}">
        <input type="hidden" id="txtIdOrdenServicioOSMAS" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIteracionOSMAS" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIdObligacionTipoMAS" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProcesoMAS" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividadMAS"  />
        <input type="hidden" id="txtFlagSupervisionMAS" value="${flagSupervision}">
        <input type="hidden" id="txtFiltrosMAS" value="${confFiltros}" />        
        <input type="hidden" id="descFiltroConfiguradoMAS" value="${nombreXfiltroConfigurado}" />
        
        <div class="form" id="divFormOSMAS">
            <form id="frmOSMAS">
            	<input type="hidden" name="personal.idPersonal" value="${idPersonal}"/>
            	<input type="hidden" name="flagEsUnidadMuestral" id="flagEsUnidadMuestral" value="0" />
                <div id="divMensajeValidaFrmOSMAS" class="errorMensaje" style="display: none"></div>
                <c:if test="${tipo!='documentos'}">
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Datos de Asignación</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-small"><label>Flujo Siged(*):</label></div>
                            <div>
                            	<input type="hidden" id="slctCodigoFlujoSigedOSMAS" name="flujoSiged.codigoSiged" />
                            	<input type="hidden" id="slctNombreFlujoSigedOSMAS" name="flujoSiged.nombreFlujoSiged" />
                                <select id="slctIdFlujoSigedOSMAS" name="flujoSiged.idFlujoSiged" class="slct-medium-small-medium" <c:if test="${tipo!='generar'}">disabled</c:if>>
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
                                <input id="txtAsuntoSigedOSMAS" type="text" name="asuntoSiged" maxlength="1000" class="ipt-medium-small-medium-small-medium" value="${r.asuntoSiged}" <c:if test="${tipo!='generar'}">disabled</c:if> />
                            </div>
                        </div>
                        <div class="filaForm noMargin" style="width: 810px;">
                            <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                            <div class="vam">
                                <select id="slctIdProcesoOSMAS" name="proceso.idProceso" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar') || (tipo=='asignar' && r.ordenServicio.iteracion>0)}">disabled</c:if> >
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
                            <div class="lbl-small vam"><label for="cmbTipoAsigOSMAS">Tipo Asignación(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoAsigOSMAS" name="ordenServicio.idTipoAsignacion" class="slct-medium" <c:if test="${tipo!='asignar' && tipo!='generar'}">disabled</c:if> >
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoAsignacion}" var="p">
                                        <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna || (tipo=='asignar' && r.ordenServicio.iteracion>0 && p.descripcion=='SIN VISITA')}">selected</c:if>>${p.descripcion}</option>
                                    </c:forEach> 
                                </select>
                            </div>     
                            <c:if test="${r.flagOrigen == 1}">
                            <div class="lbl-small vam"><label for="">Tipo Supervisión(*):</label></div>
                            <div class="vam">
                                <select id="cmbTipoSupervisionOSMAS" name="obligacionTipo.idObligacionTipo" class="slct-medium" <c:if test="${(tipo!='asignar' && tipo!='generar') || (tipo=='asignar' && r.ordenServicio.iteracion>0)}">disabled</c:if> >
                                    <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoSupervision}" var="p">
                                        <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                    </c:forEach> 
                                </select>
                            </div>
                            </c:if>

                        </div>
                        
                    </div>
                </div>
                </c:if>
                <c:if test="${tipo!='documentos'}">
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Unidad Operativa</span>
                        <input type="hidden" id="txtIdEmpresaSupervisadaOSMAS" value="${r.empresaSupervisada.idEmpresaSupervisada}">
                    </div>
                    <div class="pui-subpanel-content">
                        <c:if test="${tipo=='generar'}">
	                        <div class="filaForm" >
	                        
	                        	<div id="busquedaOsinergminMAS">
	                        
		                            <div class="lbl-small vam"><label for="">Código Titular Minero(*):</label></div>
		                            <div class="vam">
		                                <input id="txtCodigoTitularMinero" name="" type="text" maxlength="50" class="slct-large" />
		                            </div>
		                            <div class="lbl-small vam">
	                                        <input type="button" id="btnBuscarTitularMineroOSMasivo" class="btn-azul btn-small" value="Agregar">
		                            </div>
	                            </div>
							</div>
                        </c:if>
                        <div>
                            <div class="tac">
                                <div id="gridContenedorUnidadOperativaTitMinMAS" class="content-grilla"></div>
                                <div id="divContextMenuUnidadOperativaTitMinMAS"></div>
                                <div id="divCantidadRegistrosTitMinMAS"></div>
                            </div>
<!--                             <div id="unidOperativaSeleccionadaMAS" style="display:none;"></div>  -->
                            <div id="unidOperativaSeleccionadaMAS" ></div> 
                            <input type="hidden" id="txtIdActividadOSMAS"/>
                            <input type="hidden" id="txtIdDepartamentoOSMAS"/>
                            <input type="hidden" id="txtIdProvinciaOSMAS"/>
                            <input type="hidden" id="txtIdDistritoOSMAS"/>
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
                            <div class="lbl-small vam"><label for="slctTipoEmprSupeOSMAS">Tipo(*):</label></div>
                            <div class="vam">
                                <input type="hidden" id="slctCodigoTipoEmprSupeOSMAS"  name="codigoTipoSupervisor"  />
                                <select id="slctTipoEmprSupeOSMAS"  class="slct-medium-small" disabled>
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
                            <div class="lbl-medium vam"><label for="slctEmprSupeOSMAS">Empresa Supervisora(*):</label></div>
                            <div class="vam">
                                <input type="hidden" id="slctLocadorEmprSupeOSMAS" name="ordenServicio.locador.idLocador" />
                                <input type="hidden" id="slctSupervisoraEmprSupeOSMAS" name="ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa" />
                                <select id="slctEmprSupeOSMAS" name="" class="slct-medium-small" disabled>
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>                            
                        </div>
                    </div>
                    <div id="txtObligatorioUnidadOrganicaMAS" class="txt-obligatorio"></div>
                </div>
                </c:if>
            </form>
          	<div class="txt-obligatorio">(*) Campos obligatorios</div>          	
        </div>        
        <div class="botones">
            <div id="btnsAccPrinc" class="ilb">
                <c:if test="${tipo=='generar'}">
                <input type="button" id="btnGenerarOSMAS" class="btn-azul btn-small" value="Generar Orden">
                </c:if>
            </div>
            <input id="btnCerrarOrdenServicioMasivo" type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>