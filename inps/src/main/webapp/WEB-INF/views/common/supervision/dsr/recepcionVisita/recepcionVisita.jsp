<%--
* Resumen		
* Objeto		: recepcionVisita.jsp
* Descripción		: Registro de los Datos de Recepcion de la Visita de una supervision
* Fecha de Creación	: 18/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* REQF-0006      |  18/08/2016   |   Zosimo Chaupis Santur      |     Crear la funcionalidad para registrar los datos de Recepción de Visita de una supervisión de orden de supervisión DSR-Criticidad de la casuística SUPERVISIÓN REALIZADA
* REQF-0022      |  22/08/2016   |   Jose Herrera Pajuelo       |     Crear la funcionalidad para consultar una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD.
* OSINE791-RSIS25|  09/09/2016   |	Alexander Vilca Narvaez 	| Modificar la funcionalidad para el modo registro y consulta en los Tabs
*                |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/recepcionVisita/recepcionVisita.js" />'
        charset="utf-8"></script>
    </head>
    <body>
        <div id="divMensajeValidaFrmSup" class="errorMensaje" style="display: none"></div>
        <br>        
        <fieldset id="fldstPersonaAtiende" <c:if test="${sup.idSupervision=='' || sup.idSupervision==null}">disabled</c:if>>
                <!-- OSINE_SFS-480 - RSIS 13 - Fin -->
                <legend>Persona que atiende la visita</legend>
                <div class="form" id="divFrmPersonaAtiendeSP">
                    
                    <div class="filaForm">
                        <div class="lbl-small vam"><label>¿Persona no quiso identificarse?</label></div>
                        <div class="vam">
                            <div class="vam">
                                <input id="chkIdentificaRS" type="checkbox" name="chkIdentifica" <c:if test="${sup.flagIdentificaPersona==0}">checked</c:if>  <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if> ><label for="chkIdentificaRS" class="checkbox"></label>
                            </div>
                        </div>
                    </div>
                    <div class="filaForm">
                            <div class="lbl-small vam"><label>Tipo Documento Identidad<span style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                        <div>
                                <select class="slct-medium" id="slctTipoDocumentoRS" name="" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>
                                <option value="">--Seleccione--</option>
                            <c:forEach items="${listadoTipoDocumento}" var="t">
                                <option value='${t.idMaestroColumna}' <c:if test="${t.idMaestroColumna==sup.supervisionPersonaGral.personaGeneral.idTipoDocumento}">selected</c:if>>${t.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="lbl-small vam"><label>N&uacute;mero Documento Identidad<span style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                    <div>
                        <input class="ipt-medium" type="text" id="txtNumeroDocumentoRS" value="${sup.supervisionPersonaGral.personaGeneral.numeroDocumento}" validate="" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                        <c:if test="${modo=='registro'}">
                        <div class="botones">						
                            <input type="button" id="btnBuscarPersonaAtiende" class="btn-azul btn-small" 
                                   value="Buscar" <c:if test="${asignacion!=1 || modo=='consulta'}">style="display: none;"</c:if>>
                        </div>
                        </c:if>
                    </div>
                    </div>
                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Apellido Paterno<span style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                        <div>
                            <input class="ipt-medium" maxlength="100" type="text" id="txtApellidoPaternoRS" value="${sup.supervisionPersonaGral.personaGeneral.apellidoPaternoPersona}" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                        </div>
                        <div class="lbl-small vam"><label>Apellido Materno<span id="spApeMaterno" style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                        <div>
                            <input class="ipt-medium" maxlength="100" type="text" id="txtApellidoMaternoRS" value="${sup.supervisionPersonaGral.personaGeneral.apellidoMaternoPersona}" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                        </div>
                        <div class="lbl-small vam"><label>Nombres<span style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                        <div >
                            <input class="ipt-medium" maxlength="100" type="text" id="txtNombresRS" name="txtNombresRS"  value="${sup.supervisionPersonaGral.personaGeneral.nombresPersona}" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>
                        </div>
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Relaci&oacute;n con el titular<span style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                        <div >
                            <select class="slct-medium" id="slctCargoAtiendeRS" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>
                                <option value="">--Seleccione--</option>
                            <c:forEach items="${listadoCargo}" var="t">
                                <option value='${t.idMaestroColumna}'<c:if test="${t.idMaestroColumna==sup.supervisionPersonaGral.cargo.idMaestroColumna}">selected</c:if>>${t.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="lbl-small vam"><label>Correo Electr&oacute;nico<span style="display: inline" class="obligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                    <div>
                        <input class="ipt-large" type="text" id="txtCorreoElectronicoRS" name="txtCorreoElectronicoRS" value="${sup.supervisionPersonaGral.correo}" validate="[CORREO]" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>/>

                        </div>
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Observaciones<span style="display: none" class="notObligatorioPV"><c:if test="${modo=='registro'}">(*)</c:if></span>:</label></div>
                        <div class="vam">
                            <textarea class="ipt-medium-small-medium-small-medium" rows="4" id="txtObservacionPersonaAtiendeRS" name="txtObservacionPersonaAtiendeRS" maxlength="4000" <c:if test="${asignacion!=1 || modo=='consulta'}">disabled</c:if>>${sup.observacionIdentificaPers}</textarea>
                        </div>
                    </div>
                    <c:if test="${modo=='registro'}"><div class="txt-obligatorio">(*) Campos obligatorios</div></c:if>
                    <div class="btnNavSupervisionDsr">
                        <!--/* OSINE791 – RSIS22 - Inicio */--> 
                        <c:if test="${modo=='registro'}">
                        <input type="button" id="btnAtrasPersonaAtiendeSP" class="btn-azul btn-small" value="Anterior">
                        <input type="button" id="btnGuardarPersonaAtiendeSP" class="btn-azul btn-small" value="Siguiente">
                        </c:if>
                        <c:if test="${modo=='consulta'}">
                        <!--/* OSINE791 - RSIS25 - Inicio */--> 
                        <input type="button" id="btnAnteriorDatosIniciales" class="btn-azul btn-small" value="Anterior" style="display: none;" >
                        <input type="button" id="btnSiguienteEjecucionMedida" class="btn-azul btn-small" value="Siguiente" style="display: none;">
                        <!--/* OSINE791 - RSIS25 - Fin */--> 
                        </c:if>
                        <!--/* OSINE791 – RSIS22 - Fin */--> 
                </div>
                </div>
        </fieldset>
        
    </body>
</html>
