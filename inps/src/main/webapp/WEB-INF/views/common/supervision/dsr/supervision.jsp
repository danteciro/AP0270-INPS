<%--
* Resumen		
* Objeto		: supervision.jsp
* Descripción		: supervision
* Fecha de Creación	: 17/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* REQF-0005      |  17/08/2016   |   Zosimo Chaupis Santur      |     Crear la funcionalidad para registrar los datos iniciales de una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística SUPERVISIÓN REALIZADA                           |
* REQF-0015      |  22/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
* REQF-0022      |  22/08/2016   |   Jose Herrera Pajuelo       |     Crear la funcionalidad para consultar una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD.
* OSINE791-RSIS25|  09/09/2016   |	Alexander Vilca Narvaez     | Modificar la funcionalidad para el modo registro y consulta en los Tabs
* OSINE791-RSIS36|  11/10/2016   |	Alexander Vilca Narvaez     | Modificar la funcionalidad para registrar los datos iniciales de supervisión de órdenes de levantamiento 
* OSINE_MAN_DSR_0029| 19/06/2017 |   Carlos Quijano Chavez::ADAPTER      |     Cambiar el nombre de la pestanha Otros Incumplimientos a Situaciones Especiales
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/supervision.js" />'
        charset="utf-8"></script>
    </head>
    <body>
        <div class="form" id="formRegSupervisionDsr">

            <input type="hidden" id="DatochkIdentificaRS" name="DatochkIdentificaRS" value="${sup.flagIdentificaPersona}" >
            <input type="hidden" id="DatoslctTipoDocumentoRS" name="DatoslctTipoDocumentoRS" value="${sup.supervisionPersonaGral.personaGeneral.idTipoDocumento}" >
            <input type="hidden" id="DatotxtNumeroDocumentoRS" name="DatotxtNumeroDocumentoRS" value="${sup.supervisionPersonaGral.personaGeneral.numeroDocumento}" >
            <input type="hidden" id="DatotxtApellidoPaternoRS" name="DatotxtApellidoPaternoRS" value="${sup.supervisionPersonaGral.personaGeneral.apellidoPaternoPersona}" >
            <input type="hidden" id="DatotxtApellidoMaternoRS" name="DatotxtApellidoMaternoRS" value="${sup.supervisionPersonaGral.personaGeneral.apellidoMaternoPersona}" >
            <input type="hidden" id="DatotxtNombresRS" name="DatotxtNombresRS" value="${sup.supervisionPersonaGral.personaGeneral.nombresPersona}" >
            <input type="hidden" id="DatoslctCargoAtiendeRS" name="DatoslctCargoAtiendeRS" value="${sup.supervisionPersonaGral.cargo.idMaestroColumna}" >
            <input type="hidden" id="DatotxtCorreoElectronicoRS" name="DatotxtCorreoElectronicoRS" value="${sup.supervisionPersonaGral.correo}" >
            <input type="hidden" id="DatotxtObservacionPersonaAtiendeRS" name="DatotxtObservacionPersonaAtiendeRS" value="${sup.observacionIdentificaPers}" >
            
            <input type="hidden" id="FechaInicioSup" value="${sup.fechaInicio}">
            <input type="hidden" id="HoraInicioSup" value="${sup.horaInicio}">
            <input type="hidden" id="idSupervision" value="${sup.idSupervision}">
            <input type="hidden" id="idSupervisionAnt" value="${sup.supervisionAnterior.idSupervision}">
            <input type="hidden" id="idIteracionOS" value="${sup.ordenServicioDTO.iteracion}">
            <input type="hidden" id="idTipoAsignacion" value="${sup.ordenServicioDTO.idTipoAsignacion}">
            <input type="hidden" id="idOrdenServicioRS" value="${sup.ordenServicioDTO.idOrdenServicio}">
            <input type="hidden" id="fechaCreacionOS" value="${sup.ordenServicioDTO.fechaHoraCreacionOS}">
            <input type="hidden" id="asignacionOS" value="${asignacion}">
            <input type="hidden" id="modoSupervision" value="${modo}">
            <input type="hidden" id="flagSupervision" value="${sup.flagSupervision}">
            <input type="hidden" id="idLocadorOSSupAnt" value="${sup.supervisionAnterior.ordenServicioDTO.locador.idLocador}">
            <input type="hidden" id="idSupervisoraEmpresaOSSupAnt" value="${sup.supervisionAnterior.ordenServicioDTO.supervisoraEmpresa.idSupervisoraEmpresa}">
            <input type="hidden" id="txtNumeroOrdenServicio" value="${sup.ordenServicioDTO.numeroOrdenServicio}">
            <input type="hidden" id="txtIdObligacionTipoSup" value="${sup.ordenServicioDTO.expediente.obligacionTipo.idObligacionTipo}">
            <input type="hidden" id="txtIdProcesoSup" value="${sup.ordenServicioDTO.expediente.proceso.idProceso}">
            <input type="hidden" id="txtIdActividadSup" value="${sup.ordenServicioDTO.expediente.unidadSupervisada.actividad.idActividad}">
            <input type="hidden" id="txtCodOsinergSup" value="${sup.ordenServicioDTO.expediente.unidadSupervisada.codigoOsinergmin}">
            <input type="hidden" id="txtIdPersonaRS" value="${sup.supervisionPersonaGral.personaGeneral.idPersonaGeneral}"/> 
            <input type="hidden" id="DireccionOperativaObliDsrSup" name="DireccionIperativaObliDsrSup" value=""/>
            <input type="hidden" id="idDepartamentoObliDsrSup" name="idDepartamentoObliDsrSup" value=""/>
           <input type="hidden" id="idProvinciaObliDsrSup" name="idProvinciaObliDsrSup" value=""/>
           <input type="hidden" id="idDistritoObliDsrSup" name="idDistritoObliDsrSup" value=""/>
            <!--OSINE_SFS-791 - RSIS 24 - Inicio-->
            <input type="hidden" id="flagEjecucionMedida" value="${sup.flagEjecucionMedida}"/> 
            <!--OSINE_SFS-791 - RSIS 24 - Inicio-->
           
            <input type="hidden" id="validarTabs" value="0"/> 

            <input type="hidden" id="EstadoResultadoSupervision" name="EstadoResultadoSupervision" value="${sup.resultadoSupervisionDTO.idResultadosupervision}" codigo ="${sup.resultadoSupervisionDTO.codigo}" >
            
            <input type="hidden" id="EstadoResultadoSupervisionInicial" name="EstadoResultadoSupervisionInicial" value="${sup.resultadoSupervisionInicialDTO.idResultadosupervision}" codigo ="${sup.resultadoSupervisionInicialDTO.codigo}" >
            
            
            <div id="divMensajeValidaFrmInicialSupDsr" class="errorMensaje" style="display: none"></div>
            <div class="pui-panel-content">    

                <fieldset class="tac" style="width:840px;">
                    <div class="filaForm">
                        <div class="lbl-small"><label>N&uacute;mero Orden Servicio:</label></div>
                        <div class="ipt-medium">
                            <span class="txt-subtitle">${sup.ordenServicioDTO.numeroOrdenServicio}</span>
                        </div>
                        <div class="lbl-small"><label>Fecha y Hora Creaci&oacute;n Orden:</label></div>
                        <div class="ipt-medium">
                            <span class="txt-subtitle">${sup.ordenServicioDTO.fechaHoraAnalogicaCreacionOS}</span>
                        </div>
                        <div class="lbl-small"><label>N&uacute;mero Expediente:</label></div>
                        <div>
                            <span class="txt-subtitle">${sup.ordenServicioDTO.expediente.numeroExpediente}</span>
                        </div>
                    </div>
                </fieldset>
                <fieldset id="fldstDatosSuper">
                    <!--/* OSINE791 – RSIS22 - Inicio */-->
                    <c:if test="${modo=='registro'}">
                    <!--/* OSINE791 – RSIS22 - Fin */-->
                    <div id="tabsSupervision" style="position:relative;">
                        <ul>
                            <li><a id="tabDatosInicialesVer" href="#tabDatosIniciales"><legend>Datos Iniciales</legend></a></li>
                            <li><a  id="tabRecepcionVisitaVer" href="#tabRecepcionVisita"><legend>Recepci&oacute;n Visita</legend></a></li>       
                            <li><a  id="tabObligacionesVer" href="#tabObligaciones"><legend>Infracciones</legend></a></li>  
                             <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
							<!--	OSINE_MAN_DSR_0029 - Inicio -->
							<!--	<li><a  id="tabOtrosIncumplientosVer" href="#tabOtrosIncumplientos" ><legend>Otros Incumplimientos</legend></a></li>	-->
                            	<li><a  id="tabOtrosIncumplientosVer" href="#tabOtrosIncumplientos" ><legend>Situaciones Especiales</legend></a></li>  
							<!--  	OSINE_MAN_DSR_0029 - Fin -->
                            	<li><a  id="tabEjecucionMedidaVer" href="#tabEjecucionMedida"><legend>Ejecuci&oacute;n Medida</legend></a></li>
                            </c:if> 
                             <!--/* OSINE791 - RSIS36 - Inicio */-->  
                             <c:if test="${sup.ordenServicioDTO.iteracion > 1}">
                            	<li><a  id="tabterminarSupervisionVer" href="#tabterminarSupervision"><legend>Terminar Supervisi&oacute;n</legend></a></li>
                            </c:if> 
                             <!--/* OSINE791 - RSIS36 - Fin */-->  
                        </ul>
                        <div id="tabDatosIniciales">
                            <jsp:include page="../dsr/datoInicial/datoInicial.jsp"/>
                        </div>
                        <div id="tabRecepcionVisita">
                            <jsp:include page="../dsr/recepcionVisita/recepcionVisita.jsp"/>
                        </div>
                        <div id="tabObligaciones">
                        	<div class="oblDsrDivListado">
                            <jsp:include page="../dsr/obligacion/obligacionListado.jsp"/>
                            </div>
                            <div class="oblDsrDivDetalle" style="display: none;">
                            <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
                            <jsp:include page="../dsr/obligacion/obligacionDsrDetalle.jsp"/>
                            </c:if>
                            <c:if test="${sup.ordenServicioDTO.iteracion > 1}">
                                <jsp:include page="../dsr/obligacion/obligacionDsrDetalleSubsanado.jsp"/>
                            </c:if>
                            </div>
                        </div>
                        <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
	                        <div id="tabOtrosIncumplientos" >
	                            <!-- OSINE_SFS-791 - RSIS 15 - Inicio -->
	                            <jsp:include page="../dsr/otrosIncumplimientos/otrosIncumplimientos.jsp"/>
	                            <!-- OSINE_SFS-791 - RSIS 15 - Fin -->
	                        </div>
	                        <div id="tabEjecucionMedida">
	                            <jsp:include page="../dsr/ejecucionMedida/ejecucionMedida.jsp"/>
	                        </div>
                         </c:if> 
                                  <!--/* OSINE791 - RSIS36 - Inicio */-->  
                         <c:if test="${sup.ordenServicioDTO.iteracion>1}">
                         	<div id="tabterminarSupervision">
                            <jsp:include page="../dsr/terminarSupervision/terminarSupervision.jsp"/> 
                    		</div> 
                         </c:if>
                                  <!--/* OSINE791 - RSIS36 - Fin */-->  
                    </div>
                    <!--/* OSINE791 – RSIS22 - Inicio */-->
                    </c:if>
                    <c:if test="${modo=='consulta'}">
                        <div id="tabsSupervision" style="position:relative;">
                            <ul>
                                <li><a id="tabDatosInicialesVer" href="#tabDatosIniciales"><legend>Datos Iniciales</legend></a></li>
                                <li><a  id="tabRecepcionVisitaVer" href="#tabRecepcionVisita"><legend>Recepci&oacute;n Visita</legend></a></li>        
                                <c:if test="${sup.ordenServicioDTO.iteracion > 1}">
                                <li><a  id="tabObligacionesVer" href="#tabObligaciones"><legend>Infracciones</legend></a></li>  
                                </c:if> 
                                <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
                                <li><a  id="tabOtrosIncumplientosVer" href="#tabOtrosIncumplientos" ><legend>Otros Incumplimientos</legend></a></li>
                                 <!--/* OSINE791 - RSIS25 - Inicio */-->  
                                 <li><a  id="tabEjecucionMedidaVer" href="#tabEjecucionMedida"><legend>Ejecuci&oacute;n Medida</legend></a></li>
                                 <!--/* OSINE791 - RSIS25 - Fin */-->   
                              </c:if> 
                            </ul>
                            <div id="tabDatosIniciales">
                                <jsp:include page="../dsr/datoInicial/datoInicial.jsp"/>
                            </div>
                            <div id="tabRecepcionVisita">
                                <jsp:include page="../dsr/recepcionVisita/recepcionVisita.jsp"/>
                            </div>
                            <c:if test="${sup.ordenServicioDTO.iteracion > 1}">
                            <div id="tabObligaciones">
                            <div class="oblDsrDivListado">
                            <jsp:include page="../dsr/obligacion/obligacionListado.jsp"/>
                            </div>
                            <div class="oblDsrDivDetalle" style="display: none;">                   
                                <jsp:include page="../dsr/obligacion/obligacionDsrDetalleSubsanado.jsp"/>
                            </div>
                          </div>  
                            </c:if>
                            <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
	                            <!--/* OSINE791 - RSIS25 - Inicio */-->
	                             <div id="tabOtrosIncumplientos" >
	                            <jsp:include page="../dsr/otrosIncumplimientos/otrosIncumplimientos.jsp"/>
	                        	</div>
	                        	<!--/* OSINE791 - RSIS25 - Fin */-->
	                            <div id="tabEjecucionMedida">
	                                <div class="ejeMedOblLst">
	                                <jsp:include page="../dsr/ejecucionMedida/ejecucionMedida.jsp"/>
	                                </div>
	                                <div class="ejeMedOblDivDetalle" style="display: none;">
	                                <jsp:include page="../dsr/obligacion/obligacionDsrDetalle.jsp"/>
	                                </div>
	                            </div>
                          </c:if>
                        </div>
                    </c:if>
                <!--/* OSINE791 – RSIS22 - Fin */-->
                </fieldset>    
            </div>	
        </div>
        <div class="botones">		
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
