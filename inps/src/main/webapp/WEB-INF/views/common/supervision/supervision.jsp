<%--
* Resumen		
* Objeto		: supervision.jsp
* Descripción		: supervision
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  03/05/2016   |   Hernán Torres Saenz        |     Cargar Lista de Obligaciones luego de Registrar la Fecha-Hora de Inicio del "Registro de Supervisión"
* OSINE_791-066  |  28/10/2016   |   Zosimo Chaupis Santur      |     Modificar la funcionalidad de registro de datos de supervisión en la interfaz "Registrar Supervisión", el cual debe validar el registro obligatorio de adjuntos.
* OSINE_791-068  |  31/10/2016   |   Zosimo Chaupis Santur      |     Crear el campo de resultado de supervisión en la sección de datos de supervisión de la interfaz "Registrar Supervisión".
* OSINE_791-069  |  02/11/2016   |   Zosimo Chaupis Santur      |     Implementar la funcionalidad para guardar el valor del campo Resultado de supervisión (REQF-0068) al registrar los datos de supervisión en la interfaz “Registrar Supervisión”.
* OSINE_MAN-001  |  15/06/2017   |   Claudio Chaucca Umana::ADAPTER      |     Realizar la validacion de existencia de cambios desde la interface de usuario 
*                |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/supervision.js" />'
        charset="utf-8"></script>
        
        <!-- OSINE_MAN-001 - Inicio -->
        <script type="text/javascript" charset="utf-8">
        	$('#fldstPersonaAtiende :input').change(function () {
	        	$( "#btnGuardarPersonaAtiende" ).attr("disabled", false);
	        	$("#btnGuardarPersonaAtiende").removeClass('btn-opaco');
	        	$("#btnGuardarPersonaAtiende").addClass('btn-azul');
			});
	        
	        $('#fldstDatosSuper :input').change(function () {
	        	$( "#btnGuardarDatosSupervision" ).attr("disabled", false);
	        	$("#btnGuardarDatosSupervision").removeClass('btn-opaco');
	        	$("#btnGuardarDatosSupervision").addClass('btn-azul');
			});
	        
	        $('#fldstPersonaAtiende :input').keyup(function(){
		            $(this).change();
		    });
		      
		    $('#fldstDatosSuper :input').keyup(function(){
			      $(this).change();
			});
		    
        </script>
        <!-- OSINE_MAN-001 - Fin -->   
    </head>
    <body>
        <div class="form" id="formRegSupervision">
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
            <input type="hidden" id="flagInfraccionesDSHL" value="">
            <input type="hidden" id="flagCumplimientoPrevio" value="${sup.flagCumplimientoPrevio}">
            <input type="hidden" id="flagCumplimientoPrevioAnt" value="${sup.supervisionAnterior.flagCumplimientoPrevio}">
            
            <div id="divMensajeValidaFrmSup" class="errorMensaje" style="display: none"></div>
            <div class="pui-panel-content">
                <div class="filaForm tac">
                    <div class="lbl-medium vam">
                        <label>
                            <c:if test="${sup.ordenServicioDTO.iteracion==1}">¿Se realiz&oacute; supervisi&oacute;n?</c:if>
                            <c:if test="${sup.ordenServicioDTO.iteracion==2}">¿Se present&oacute; descargos?</c:if>:
                            </label>
                        </div>
                        <div class="vam">
                            <!-- OSINE_SFS-480 - RSIS 13 - Inicio -->
                            <!-- 
                            <input id="radioSiSupervision" type="radio" name="radioSupervision" value="1" 
                        <c:if test="${sup.flagSupervision=='1'}">checked="checked"</c:if> 
                        <c:if test="${modo=='consulta'||sup.ordenServicioDTO.iteracion==2}">disabled</c:if>>
                        <label for="radioSiSupervision" class="radio">Si</label>
                            -->
                            <input id="radioSiSupervision" type="radio" name="radioSupervision" value="1" 
                            <c:if test="${sup.flagSupervision=='1' || sup.idSupervision=='' || sup.idSupervision==null}">checked="checked"</c:if> 
                            <c:if test="${modo=='consulta'}">disabled</c:if>>
                            <!-- OSINE_SFS-480 - RSIS 13 - Fin -->
                            <label for="radioSiSupervision" class="radio">Si</label>
                        </div>
                        <div class="vam">
                            <!-- OSINE_SFS-480 - RSIS 13 - Inicio -->
                            <!-- 
                            <input id="radioNoSupervision" type="radio" name="radioSupervision" value="0" 
                        <c:if test="${sup.flagSupervision=='0'}">checked="checked"</c:if> 
                        <c:if test="${modo=='consulta'||sup.ordenServicioDTO.iteracion==2}">disabled</c:if>>
                            -->
                            <input id="radioNoSupervision" type="radio" name="radioSupervision" value="0" 
                            <c:if test="${sup.flagSupervision=='0'}">checked="checked"</c:if> 
                            <c:if test="${modo=='consulta'}">disabled</c:if>>
                        <!-- OSINE_SFS-480 - RSIS 13 - Fin -->
                        <label for="radioNoSupervision" class="radio">No</label>
                    </div>
                </div>
                    <fieldset class="tac" style="width:825px;">
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
                <br>
                <div id="divSuperAnterior" style="display: none;">
                    <fieldset id="fldstSuperAnterior">
                        <legend>Supervisi&oacute;n Anterior</legend>
                        <div class="form" id="divFrmDatosSuperAnt">
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Orden de servicio:</label></div>
                                <div class="vam">
                                    <input id="ordenServicioRSAnt" type="text"  class="ipt-medium" value="${sup.supervisionAnterior.ordenServicioDTO.numeroOrdenServicio}" disabled="disabled" />
                                </div>
                                <div class="lbl-small vam"><label>Fecha y Hora Creaci&oacute;n Orden:</label></div>
                                <div class="vam">
                                    <input id="fechaHoraOrdenRSAnt" type="text"  class="ipt-medium" value="${sup.supervisionAnterior.ordenServicioDTO.fechaHoraAnalogicaCreacionOS}" disabled="disabled" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="slctTipoEmprSupeOSSuper">Tipo Empresa:</label></div>
                                <div class="vam">
                                    <select id="slctEntidadOSSuper" style="display:none;">
                                        <c:forEach items="${listadoEntidadSuper}" var="t">
                                            <option value='${t.codigo}' <c:if test="${t.codigo == '1'}">selected</c:if>>${t.descripcion}</option>
                                        </c:forEach>                             
                                    </select>
                                    <select id="slctTipoEmprSupeOSSuper" class="slct-medium" disabled="disabled">
                                        <option value="" codigo="">--Seleccione--</option>
                                        <c:forEach items="${listadoTipoSupervisorSuper}" var="p">
                                            <c:choose>
                                                <c:when test="${sup.supervisionAnterior.ordenServicioDTO.locador.idLocador!=null}">
                                                    <option value='${p.idMaestroColumna}' codigo='${p.codigo}' <c:if test="${p.codigo==1}">selected</c:if> >${p.descripcion}</option>
                                                </c:when>
                                                <c:when test="${sup.supervisionAnterior.ordenServicioDTO.supervisoraEmpresa.idSupervisoraEmpresa!=null}">
                                                    <option value='${p.idMaestroColumna}' codigo='${p.codigo}' <c:if test="${p.codigo==2}">selected</c:if> >${p.descripcion}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${p.idMaestroColumna}' codigo='${p.codigo}'>${p.descripcion}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>                             
                                    </select>
                                </div>
                                <div class="lbl-small vam"><label for="slctEmprSupeOSSuper">Empresa Supervisora:</label></div>
                                <div class="vam">
                                    <select id="slctEmprSupeOSSuper" class="slct-medium-small" disabled="disabled">
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Fecha Inicio:</label></div>
                                <div class="vam">
                                    <input id="fechaInicioRSAnt" type="text"  class="ipt-medium" value="${sup.supervisionAnterior.fechaInicio}" disabled="disabled" />
                                </div>

                                <div class="lbl-small vam"><label>Hora Inicio:</label></div>
                                <div class="vam">
                                    <input class="ipt-medium" type="text" id="horaInicioRSAnt"  value="${sup.supervisionAnterior.horaInicio}" disabled="disabled" />
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Fecha Fin:</label></div>
                                <div class="vam">
                                    <input id="fechaFinRSAnt" type="text" class="ipt-medium" value="${sup.supervisionAnterior.fechaFin}" disabled="disabled"/>
                                </div>

                                <div class="lbl-small vam"><label>Hora Fin:</label></div>
                                <div class="vam">
                                    <input class="ipt-medium" type="text" id="horaFinRSAnt" value="${sup.supervisionAnterior.horaFin}" disabled="disabled" />
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>N° acta probatoria:</label></div>
                                <div class="vam">
                                    <input id="txtActaProbatoriaAnt" type="text" class="ipt-medium" value="${sup.supervisionAnterior.actaProbatoria}" disabled="disabled"/>
                                </div>
                                <!-- OSINE_SFS-791 - RSIS 67 - Inicio -->
                                <div class="lbl-small vam"><label>N° Acta de visita:</label></div>
                                <!-- OSINE_SFS-791 - RSIS 67 - Fin -->
                                <div class="vam">
                                    <input id="txtCartaVisitaAnt" type="text" maxlength="25" class="ipt-medium" value="${sup.supervisionAnterior.cartaVisita}" disabled="disabled" />
                                </div>
                                <div class="lbl-small vam"><label>Se realiz&oacute; supervisi&oacute;n:</label></div>
                                <div class="lbl-medium vam">
                                    <div class="vam">
                                        <input id="radio1Ant" type="radio" name="radioAnt" value="1"
                                               <c:if test="${sup.supervisionAnterior.flagSupervision==null || sup.supervisionAnterior.flagSupervision=='1'}">checked="checked"</c:if> 
                                                   disabled="disabled">
                                               <label for="radio1Ant" class="radio">SI</label>
                                        </div>
                                        <div class="vam">
                                            <input id="radio2Ant" type="radio" name="radioAnt" value="0" 
                                            <c:if test="${sup.supervisionAnterior.flagSupervision=='0'}">checked="checked"</c:if>
                                                disabled="disabled">
                                            <label for="radio2Ant" class="radio" >NO</label>
                                        </div>
                                    </div>
                                <%-- 	                    <c:if test="${sup.supervisionAnterior.flagSupervision=='0'}"> --%>
                                <!-- 	                    	<div class="lbl-small vam"><label>Motivo de no supervisi&oacute;n:</label></div> -->
                                <!-- 		                    <div class="vam"> -->
                                <!-- 		                        <select class="slct-medium" id="slctMotivoNoSuprvsnRSAnt" disabled="disabled"> -->
                                <!-- 		                        	<option value="">--Seleccione--</option> -->
                                <%-- 	                                   <c:forEach items="${listadoMotivoNoSuprvsn}" var="t"> --%>
                                <%-- 	                                   	<option value='${t.idMaestroColumna}' <c:if test="${t.idMaestroColumna==sup.supervisionAnterior.motivoNoSuprvsn}">selected</c:if>>${t.descripcion}</option> --%>
                                <%-- 	                                   </c:forEach> --%>
                                <!-- 		                        </select> -->
                                <!-- 		                    </div> -->
                                <%-- 	                    </c:if> --%>
                            </div>
                            <!-- OSINE_SFS-791 - RSIS 68 - Inicio -->
                            <div class="filaForm">
                                <div id="divResultadoSupervisionRSAnt" style="display: none;">
                                    <div class="lbl-small vam"><label>Resultado de Supervisi&oacute;n(*):</label></div>
                                    <div class="vam">
                                    <select class="slct-medium" id="slctResultadoSupervisionRSAnt" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                            <option value=""  >--Seleccione--</option>
                                            <option value="1" >CON INCUMPLIMIENTOS</option>
                                            <option value="0" >SIN INCUMPLIMIENTOS</option>                                              
                                    </select>
                                    </div>
                                </div>
                            </div>
                               <!-- OSINE_SFS-791 - RSIS 68 - Fin -->
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Observaci&oacute;n de la supervisi&oacute;n:</label></div>
                                <div class="vam">
                                    <textarea class="ipt-medium-small-medium-small-medium" rows="4" id="txtObservacionSupervisionAnt" maxlength="4000" disabled="disabled">${sup.supervisionAnterior.observacion}</textarea>
                                </div>
                            </div>
                            <div class="botones">
                                <input type="button" id="btnAdjuntosSuperAnt" class="btn-azul btn-small" value="Ver Adjuntos">
                            </div>
                            <div class="filaForm">
                                <div class="lbl-medium"><span class="txt-subtitle" id="txtNroExpediente">Persona que atiende la visita :</span></div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>¿Persona no quiso identificarse?</label></div>
                                <div class="vam">
                                    <div class="vam">
                                        <input id="chkIdentificaRSAnt" type="checkbox" <c:if test="${sup.supervisionAnterior.flagIdentificaPersona==0}">checked</c:if> disabled="disabled"><label for="chkIdentificaRSAnt" class="checkbox"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="filaForm">
                                    <div class="lbl-small vam"><label>Tipo Documento Identidad:</label></div>
                                    <div>
                                        <select class="slct-medium" id="slctTipoDocumentoRSAnt" disabled="disabled">
                                            <option value="">--Seleccione--</option>
                                        <c:forEach items="${listadoTipoDocumento}" var="t">
                                            <option value='${t.idMaestroColumna}' <c:if test="${t.idMaestroColumna==sup.supervisionAnterior.supervisionPersonaGral.personaGeneral.idTipoDocumento}">selected</c:if>>${t.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="lbl-small vam"><label>N&uacute;mero Documento Identidad:</label></div>
                                <div >
                                    <input class="ipt-medium" type="text" id="txtNumeroDocumentoAnt" value="${sup.supervisionAnterior.supervisionPersonaGral.personaGeneral.numeroDocumento}" disabled="disabled" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Apellido Paterno:</label></div>
                                <div>
                                    <input class="ipt-medium" type="text" id="txtApellidoPaternoAnt" value="${sup.supervisionAnterior.supervisionPersonaGral.personaGeneral.apellidoPaternoPersona}" disabled="disabled" />
                                </div>
                                <div class="lbl-small vam"><label>Apellido Materno:</label></div>
                                <div>
                                    <input class="ipt-medium" type="text" id="txtApellidoMaternoAnt" value="${sup.supervisionAnterior.supervisionPersonaGral.personaGeneral.apellidoMaternoPersona}" disabled="disabled"/>
                                </div>
                                <div class="lbl-small vam"><label>Nombres:</label></div>
                                <div >
                                    <input class="ipt-medium" type="text" id="txtNombresAnt" value="${sup.supervisionAnterior.supervisionPersonaGral.personaGeneral.nombresPersona}" disabled="disabled"/>
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Cargo:</label></div>
                                <div >
                                    <select class="slct-medium" id="slctCargoAtiendeRSAnt"  disabled="disabled">
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listadoCargo}" var="t">
                                            <option value='${t.idMaestroColumna}'<c:if test="${t.idMaestroColumna==sup.supervisionAnterior.supervisionPersonaGral.cargo.idMaestroColumna}">selected</c:if>>${t.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Observaciones:</label></div>
                                <div class="vam">
                                    <textarea class="ipt-medium-small-medium-small-medium" rows="4" id="txtObservacionPersonaAtiende" maxlength="4000" disabled="disabled">${sup.supervisionAnterior.observacionIdentificaPers}</textarea>
                                </div>
                            </div>

                        </div>
                    </fieldset>
                </div>
                <fieldset id="fldstDatosSuper">
                    <legend>Datos de supervisi&oacute;n</legend>
                    <div class="form" id="divFrmDatosSuper">
                        <c:if test="${modo=='consulta'}">
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Tipo Empresa:</label></div>
                                <div class="vam">
                                    <input type="text" class="ipt-medium" value="${tipoEmpresa}" disabled="disabled" />
                                </div>
                                <div class="lbl-small vam"><label>Empresa Supervisora:</label></div>
                                <div class="vam">
                                    <input type="text" class="ipt-medium-small-medium" value="${empresaSupervisora}" disabled="disabled" />
                                </div>
                            </div>
                        </c:if>
                        <div class="filaForm">
                            <div class="lbl-small vam"><label>Fecha Inicio(*):</label></div>
                            <div class="vam">
                                <input id="fechaInicioRS" type="text" validate="[O]" maxlength="10" class="ipt-medium" value="${sup.fechaInicio}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                                </div>

                                <div class="lbl-small vam"><label>Hora Inicio(*):</label></div>
                                <div class="vam">
                                    <input class="timepicker ipt-medium" type="text" validate="[O]" maxlength="8" id="horaInicioRS"  name="horaInicioRS" value="${sup.horaInicio}"  <c:if test="${modo=='consulta'}">disabled</c:if> >
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Fecha Fin(*):</label></div>
                                <div class="vam">
                                    <input id="fechaFinRS" type="text" validate="[O]" maxlength="10" class="ipt-medium" value="${sup.fechaFin}"  <c:if test="${modo=='consulta'}">disabled</c:if>/>
                                </div>

                                <div class="lbl-small vam"><label>Hora Fin(*):</label></div>
                                <div class="vam">
                                    <input class="timepicker ipt-medium" type="text" validate="[O]" maxlength="8" id="horaFinRS"  name="horaFinRS" value="${sup.horaFin}"  <c:if test="${modo=='consulta'}">disabled</c:if> >
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>N° acta probatoria:</label></div>
                                <div class="vam">
                                    <input id="txtActaProbatoriaRS" type="text" maxlength="20" class="ipt-medium" value="${sup.actaProbatoria}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                                </div>
                                <!-- OSINE_SFS-791 - RSIS 67 - Inicio -->
                                <div class="lbl-small vam"><label>N° Acta de visita:</label></div>
                                <!-- OSINE_SFS-791 - RSIS 67 - Fin -->
                                <div class="vam">
                                    <input id="txtCartaVisitaRS" type="text" maxlength="20" class="ipt-medium" value="${sup.cartaVisita}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                                </div>
                                <div class="botones">
                                    <input type="button" id="btnAdjuntoSupervision" class="btn-azul btn-medium" <c:if test="${modo=='consulta'}">value="Ver Adjuntos"</c:if> <c:if test="${modo!='consulta'}">value="Registrar Adjuntos"</c:if>>			            
                                    <c:if test="${ rol == 'SUPERVISOR'}">
                                    	<input type="button" id="btnVerAdjuntoSupervision" class="btn-azul btn-small" <c:if test="${modo=='consulta'}">value="Ver Adjuntos"</c:if> <c:if test="${modo!='consulta'}">value="Ver Adjuntos"</c:if>>
                                    </c:if>
                                </div>	                                       	                    
                            </div>
                                 <!-- OSINE_SFS-791 - RSIS 68 - Inicio -->
                            <div class="filaForm">
                                <div id="divResultadoSupervisionRS" style="display: none;">
                                    <div class="lbl-small vam"><label>Resultado de Supervisi&oacute;n(*):</label></div>
                                    <div class="vam">
                                    <select class="slct-medium" id="slctResultadoSupervisionRS" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                            <option value=""  >--Seleccione--</option>
                                            <option value="1" >CON INCUMPLIMIENTOS</option>
                                            <option value="0" >SIN INCUMPLIMIENTOS</option>                                              
                                    </select>
                                    </div>
                                </div>
                            </div>
                               <!-- OSINE_SFS-791 - RSIS 68 - Fin -->
                            <div class="filaForm">
                                <div id="divMotivoNoSuprvsnRS" style="display: none;">
                                    <div class="lbl-small vam"><label>Motivo no supervisi&oacute;n(*):</label></div>
                                    <div class="vam">
                                            <select class="slct-medium" id="slctMotivoNoSuprvsnRS" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                            <option value="" especifica="">--Seleccione--</option>
                                        <c:forEach items="${listadoMotivoNoSuprvsn}" var="t">
                                            <option value='${t.idMotivoNoSupervision}' especifica="${t.flagEspecificar}" <c:if test="${t.idMotivoNoSupervision==sup.motivoNoSupervision.idMotivoNoSupervision}">selected</c:if>>${t.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div id="divEspecificarRS" style="display: none;">
                                <div class="lbl-small vam"><label>Especificar(*):</label></div>
                                <div class="vam">
                                    <input id="txtEspecificarRS" type="text" maxlength="100" class="ipt-medium-small-medium" value="${sup.descripcionMtvoNoSuprvsn}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                                    </div>
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Observaci&oacute;n de la supervisi&oacute;n<span style="display: none" class="obligatorioDS">(*)</span>:</label></div>
                                <div class="vam">
                                    <textarea style="text-transform: none !important;" class="ipt-medium-small-medium-small-medium" rows="4" id="txtObservacionRS" maxlength="4000" <c:if test="${modo=='consulta'}">disabled</c:if>>${sup.observacion}</textarea>
                                </div>
                            </div>


                            <div class="botones">
                                <input type="button" id="btnGuardarDatosSupervision" class="btn-azul btn-small" value="Guardar" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
                            </div>
                        </div>
                    </fieldset>
                    <!-- OSINE_SFS-480 - RSIS 13 - Inicio -->
                    <!--
                    <fieldset id="fldstObligacion">
                    -->
                    <fieldset id="fldstObligacion" <c:if test="${sup.idSupervision=='' || sup.idSupervision==null}">disabled</c:if>>
                        <!-- OSINE_SFS-480 - RSIS 13 - Fin -->
                        <legend>Obligaciones a supervisar</legend>
                        <div class="form" id="divFrmObligaciones">
                        	
                        	<div class="filaForm">
	                                <div class="lbl-small vam"><label>Descripci&oacute;n de base legal:</label></div>
	                                <div>
	                                    <input class="ipt-medium-small-medium-small-medium" type="text" id="txtDescripcionBaseLegal"  maxlength="200"/>
	                                </div>
                            </div>
                        
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>C&oacute;d. base legal:</label></div>
                                <div >
                                    <input class="ipt-medium" type="text" id="txtCodigoBase" maxlength="10"/>
                                </div>
                                <div class="lbl-small vam"><label>Clasificaci&oacute;n:</label></div>
                                <div>
                                    <select class="slct-medium" id="cmbClasificacion" name=""></select>
                                </div>
                                <div class="lbl-small vam"><label>Criticidad:</label></div>
                                <div >
                                    <select class="slct-medium" id="cmbCriticidad" name=""></select>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam">
                                    <label>
                                    <c:if test="${sup.ordenServicioDTO.iteracion==1}">Cumple</c:if>
                                    <c:if test="${sup.ordenServicioDTO.iteracion==2}">Sanciona</c:if>:
                                    </label>
                                </div>
                                <div >
                                    <select class="slct-medium" id="cmbCumple" name=""></select>
                                    <input type="hidden" id="hiddenCumple">
                                </div>
                                <div class="lbl-small vam"><label>Trabajado:</label></div>
                                <div >
                                    <select class="slct-medium" id="cmbRegistrado" name=""></select>
                                    <input type="hidden" id="hiddenRegistrado">
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Descripci&oacute;n de obligaci&oacute;n normativa:</label></div>
                                <div>
                                    <input class="ipt-medium-small-medium-small-medium" type="text" id="txtDescripcionObli"  maxlength="200"/>
                                </div>
                            </div>

                            <div class="botones">
                                <input type="button" id="btnBuscarObligaciones" class="btn-azul btn-small" value="Buscar">
                                <input type="button" id="btnLimpiar" class="btn-azul btn-small" value="Limpiar">
                                <input type="button" id="btnOblIncumplida" class="btn-azul btn-small" value="Obl. Incumplidas">
                            </div>

                            <div class="filaForm tac">
                                <div>
                                    <div class="fila">
                                        <div id="gridContenedorObligaciones" class="content-grilla"></div>
                                        <div id="divContextMenuContenedorObligaciones"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="filaForm tal">
                                <div style="color:blue;">
                                    <label>Nota:Para consultar el contenido completo de la "Obligaci&oacute;n Normativa" y "Base Legal" coloque el mouse sobre el campo requerido.</label>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <!-- OSINE_SFS-480 - RSIS 13 - Inicio -->
                    <!--
                    <fieldset id="fldstPersonaAtiende">
                    -->
                    <fieldset id="fldstPersonaAtiende" <c:if test="${sup.idSupervision=='' || sup.idSupervision==null}">disabled</c:if>>
                        <!-- OSINE_SFS-480 - RSIS 13 - Fin -->
                        <legend>Persona que atiende la visita</legend>
                        <div class="form" id="divFrmPersonaAtiende">
                            <input type="hidden" id="txtIdPersonaRS" value="${sup.supervisionPersonaGral.personaGeneral.idPersonaGeneral}"/> 
                        <div class="filaForm">
                            <div class="lbl-small vam"><label>¿Persona no quiso identificarse?</label></div>
                            <div class="vam">
                                <div class="vam">
                                    <input id="chkIdentificaRS" type="checkbox" name="chkIdentifica" <c:if test="${sup.flagIdentificaPersona==0}">checked</c:if>  <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if> ><label for="chkIdentificaRS" class="checkbox"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Tipo Documento Identidad<span style="display: inline" class="obligatorioPV">(*)</span>:</label></div>
                                <div>
                                        <select class="slct-medium" id="slctTipoDocumentoRS" name="" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>
                                        <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoTipoDocumento}" var="t">
                                        <option value='${t.idMaestroColumna}' <c:if test="${t.idMaestroColumna==sup.supervisionPersonaGral.personaGeneral.idTipoDocumento}">selected</c:if>>${t.descripcion}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="lbl-small vam"><label>N&uacute;mero Documento Identidad<span style="display: inline" class="obligatorioPV">(*)</span>:</label></div>
                            <div>
                                <input class="ipt-medium" type="text" id="txtNumeroDocumentoRS" value="${sup.supervisionPersonaGral.personaGeneral.numeroDocumento}" validate="" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                                    <div class="botones">						
                                        <input type="button" id="btnBuscarPersonaAtiende" class="btn-azul btn-small" 
                                               value="Buscar" <c:if test="${asignacion!=1 || modo=='consulta'}">style="display: none;"</c:if>>
                                    </div>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Apellido Paterno<span style="display: inline" class="obligatorioPV">(*)</span>:</label></div>
                                <div>
                                    <input class="ipt-medium" maxlength="100" type="text" id="txtApellidoPaternoRS" value="${sup.supervisionPersonaGral.personaGeneral.apellidoPaternoPersona}" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                                </div>
                                <div class="lbl-small vam"><label>Apellido Materno<span id="spApeMaterno" style="display: inline" class="obligatorioPV">(*)</span>:</label></div>
                                <div>
                                    <input class="ipt-medium" maxlength="100" type="text" id="txtApellidoMaternoRS" value="${sup.supervisionPersonaGral.personaGeneral.apellidoMaternoPersona}" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                                </div>
                                <div class="lbl-small vam"><label>Nombres<span style="display: inline" class="obligatorioPV">(*)</span>:</label></div>
                                <div >
                                    <input class="ipt-medium" maxlength="100" type="text" id="txtNombresRS"  value="${sup.supervisionPersonaGral.personaGeneral.nombresPersona}" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                                </div>
                            </div>

                            <div class="filaForm">
                                <div class="lbl-small vam"><label>Cargo<span style="display: inline" class="obligatorioPV">(*)</span>:</label></div>
                                <div >
                                    <select class="slct-medium" id="slctCargoAtiendeRS" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>
                                        <option value="">--Seleccione--</option>
                                    <c:forEach items="${listadoCargo}" var="t">
                                        <option value='${t.idMaestroColumna}'<c:if test="${t.idMaestroColumna==sup.supervisionPersonaGral.cargo.idMaestroColumna}">selected</c:if>>${t.descripcion}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="filaForm">
                            <div class="lbl-small vam"><label>Observaciones<span style="display: none;" class="notObligatorioPV">(*)</span>:</label></div>
                            <div class="vam">
                                <textarea class="ipt-medium-small-medium-small-medium" style="text-transform: none !important;" rows="4" id="txtObservacionPersonaAtiendeRS" maxlength="4000" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>${sup.observacionIdentificaPers}</textarea>
                                </div>
                            </div>
                            <div class="botones">
                                <input type="button" id="btnGuardarPersonaAtiende" class="btn-azul btn-small" value="Guardar" <c:if test="${asignacion!=1 || modo=='consulta'}">style="display: none;"</c:if>>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="txt-obligatorio">(*) Campos obligatorios</div>
            </div>

            <div class="botones">
                <input type="button" id="btnGenerar" class="btn-azul btn-medium" value="Generar Resultados" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
            <input type="button" title="Cerrar"
                   class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
