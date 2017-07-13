<%--
* Resumen       
* Objeto            : ordenServicioLevantamiento.jsp
* Descripción       : ordenServicioLevantamiento
* Fecha de Creación : 06/10/2016
* PR de Creación    : OSINE_SFS-791
* Autor             : Mario Dioses Fernandez
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791     06/10/2016       Mario Dioses Fernandez           Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioLevantamiento.js" />' charset="utf-8"></script>
        <style type="text/css">
            .top{margin-top:10px;}
        </style>
    </head>
    <body>
        <input type="hidden" id="tipoAccionOSLev" value="${tipo}">
        <input type="hidden" id="txtNumeroExpedienteOSLev" value="${r.numeroExpediente}">
        <input type="hidden" id="txtIdExpedienteOSLev" value="${r.idExpediente}">
        <input type="hidden" id="txtflagOrigenOSLev" value="${r.flagOrigen}">
        <input type="hidden" id="txtIdTramiteOSLev" value="${r.tramite.idTramite}">
        <input type="hidden" id="txtIdEtapaTrOSLev" value="${r.tramite.idEtapa}">
        <input type="hidden" id="txtIdOrdenServicioOSLev" value="${r.ordenServicio.idOrdenServicio}">
        <input type="hidden" id="txtIteracionOSLev" value="${r.ordenServicio.iteracion}">
        <input type="hidden" id="txtIdObligacionTipoLev" value="${r.obligacionTipo.idObligacionTipo}">
        <input type="hidden" id="txtIdProcesoLev" value="${r.proceso.idProceso}">
        <input type="hidden" id="txtIdActividadLev"  />
        <input type="hidden" id="txtFlagSupervisionLev" value="${flagSupervision}">
        <input type="hidden" id="txtIdObligacionSubTipoOSLev" value="${r.obligacionSubTipo.idObligacionSubTipo}" />
        <input type="hidden" id="codOsinergminLev" value="${r.unidadSupervisada.codigoOsinergmin}">         
        <div class="form">
            <form>
                <div class="pui-subpanel ui-widget-content">
                    <div class="pui-subpanel-subtitlebar">
                        <span class="ui-panel-title">Orden de Servicio</span>
                    </div>
                    <div class="pui-subpanel-content">
                        <div class="filaForm">
                            <div class="lbl-medium"><label>N&uacute;mero Orden Servicio:</label></div>
                            <div class="ipt-medium">
                                <span class="txt-subtitle">${r.ordenServicio.numeroOrdenServicio}</span>
                            </div>
                                <div class="lbl-small"><label>Fecha y Hora Creaci&oacute;n Orden:</label></div>
                                <div class="ipt-medium">
                                    <span class="txt-subtitle">${r.ordenServicio.fechaHoraAnalogicaCreacionOS}</span>
                                </div>
                            <div class="lbl-medium"><label>N&uacute;mero Expediente:</label></div>
                            <div>
                                <span class="txt-subtitle">${r.numeroExpediente}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="tabsLevantamiento"> 
                    <ul>
                        <li><a id="liTabOrdenServicio" href="#tabOrdenServicio"><legend>Orden Servicio</legend></a></li>
                        <li><a id="liTabLevantamiento" href="#tabLevantamiento"><legend>Levantamientos</legend></a></li>      
                    </ul>
                    <div id="tabOrdenServicio">
                        <!-- Contenido tab Orden Servicio -->                    
                        <div class="pui-subpanel ui-widget-content">
                            <div class="pui-subpanel-subtitlebar">
                                <span class="ui-panel-title">Datos de Asignaci&oacute;n</span>
                            </div>
                            <div class="pui-subpanel-content">
                                <div class="filaForm">
                                    <div class="lbl-small"><label>Flujo Siged(*):</label></div>
                                    <div>
                                        <select id="slctIdFlujoSigedOSLev" class="slct-medium-small-medium" disabled>
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
                                    <input id="txtAsuntoSigedOSLev" type="text" maxlength="500" class="ipt-medium-small-medium-small-medium" value='${r.asuntoSiged}' disabled />
                                </div>                              
                                </div>                        
                                <div class="filaForm noMargin" style="width: 810px;">
                                    <div class="lbl-small vam"><label for="">Proceso(*):</label></div>
                                    <div class="vam">
                                        <select id="slctIdProcesoOSLev" class="slct-medium" disabled> 
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
                                        <select id="slctIdEtapaOSLev" class="slct-medium" disabled>
                                            <option value="">--Seleccione--</option>
                                        </select>
                                    </div>
                                    <div class="lbl-small vam"><label>Tr&aacute;mite(*):</label></div>
                                    <div class="vam">
                                        <select id="slctIdTramiteOSLev" class="slct-medium" disabled>
                                            <option value="">--Seleccione--</option>
                                        </select>
                                    </div>
                                    </c:if>
                                    <div class="lbl-small vam"><label for="slctTipoAsigLev">Tipo Asignaci&oacute;n(*):</label></div>
                                    <div class="vam">
                                        <select id="slctTipoAsigLev" class="slct-medium" disabled>   
                                            <option value="">--Seleccione--</option>
                                            <c:forEach items="${listadoTipoAsignacion}" var="p">
                                                <option value='${p.idMaestroColumna}' <c:if test="${r.ordenServicio.idTipoAsignacion==p.idMaestroColumna}">selected</c:if>>${p.descripcion}</option>
                                            </c:forEach> 
                                        </select>
                                    </div>     
                                    <c:if test="${r.flagOrigen == 1}">
                                    <div class="lbl-small vam"><label for="">Tipo Supervisi&oacute;n(*):</label></div>
                                    <div class="vam">
                                        <select id="cmbTipoSupervisionOSLev" class="slct-medium" disabled> 
                                            <option value="">--Seleccione--</option>
                                            <c:forEach items="${listadoTipoSupervision}" var="p">
                                                <option value='${p.idObligacionTipo}' codigo="${p.tieneMuestra}" <c:if test="${r.obligacionTipo.idObligacionTipo==p.idObligacionTipo}">selected</c:if>>${p.nombre}</option>
                                            </c:forEach> 
                                        </select>
                                    </div>
                                    </c:if>                                 
                                    <div id="subTipoOSLev" class="filaForm noMargin" style="display: none;">
                                        <div class="lbl-small vam"></div>
                                        <div class="lbl-medium vam"></div>
                                        <div class="lbl-small vam"></div>
                                        <div class="lbl-medium vam"></div>
                                        <div class="lbl-small vam"><label for="">Subtipo Supervisi&oacute;n(*):</label></div>
                                        <div class="vam">
                                            <select id="cmbSubTipoSupervisionOSLev" name="obligacionSubTipo.idObligacionSubTipo" class="slct-medium" disabled>
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
                                <input type="hidden" id="txtIdEmpresaSupervisadaOSLev" value="${r.empresaSupervisada.idEmpresaSupervisada}">
                            </div>
                            <div class="pui-subpanel-content">
                                <div class="filaForm">
                                    <div class="lbl-small vam" ><label>Unidad Operativa(*):</label></div>
                                    <div class="vam">
                                        <select id="slctUnidSupeOSLev" class="slct-medium-small-medium" disabled> 
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
                                            <input id="txtRubroOSLev" type="text" validate="[O]" class="ipt-medium-small-medium" disabled />
                                        </div>
                                    </div>
                                    <div class="filaForm">
                                        <div class="lbl-small vam"><label for="">Direcci&oacute;n Operativa:</label></div>
                                        <div class="vam">
                                            <input id="txtIdDepartamentoOSLev" type="hidden" />  
                                            <input id="txtIdProvinciaOSLev" type="hidden" /> 
                                            <input id="txtIdDistritoOSLev" type="hidden" /> 
                                            <input id="txtDireOperOSLev" type="text" class="ipt-medium-small-medium-small-medium" disabled /> 
                                        </div>
                                    </div>
                                    <div class="filaForm">
                                        <div class="lbl-small vam"><label for="cmbTipoDocumento">Tipo/Nro Documento:</label></div>
                                        <div class="vam">
                                            <input type="text" validate="[O]" class="ipt-medium-small" value="<c:choose><c:when test="${r.empresaSupervisada.ruc!='' && r.empresaSupervisada.ruc!=null}">RUC - ${r.empresaSupervisada.ruc}</c:when><c:otherwise>${r.empresaSupervisada.tipoDocumentoIdentidad.descripcion} - ${r.empresaSupervisada.nroIdentificacion}</c:otherwise></c:choose>" disabled />
                                        </div>
                                    </div>
                                    <div class="filaForm">
                                        <div class="lbl-small vam"><label for="">Raz&oacute;n Social:</label></div>
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
                                        <select id="slctTipoEmprSupeOSLev" class="slct-medium-small" disabled>
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
                                        <select id="slctEmprSupeOSLev" class="slct-medium-small" style="display:none;" disabled>
                                            <option value="">--Seleccione--</option>
                                        </select>
                                        <input type="text" class="ipt-medium-small" disabled value="${r.ordenServicio.locador.nombreCompleto} ${r.ordenServicio.supervisoraEmpresa.razonSocial}"/>                                
                                    </div>      
                                </div>
                            </div>
                        </div>
                        <div class="txt-obligatorio top">(*) Campos obligatorios</div>
                    	<!-- Fin Contenido tab Orden Servicio -->      
                    </div>
                    <div id="tabLevantamiento">
                    	<!-- Contenido tab Levantamientos -->
                        <div class="fila">
	                        <div id="gridContenedorInfraccionLev" class="content-grilla"></div>
	                        <div id="divContextMenuInfraccionLev"></div>
                        </div>
                        <div id="dialogLevantamientoInfraccionSup" class="dialog" style="display:none;"></div>	
                        <!-- Fin Contenido tab Levantamientos -->
                    </div>
                </div>
            </form>
        </div>        
        <div class="botones top">
            <input type="button" id="btnConfirmarVisitaLevantamiento" class="btn-azul btn-small" value="Confirmar Visita">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>