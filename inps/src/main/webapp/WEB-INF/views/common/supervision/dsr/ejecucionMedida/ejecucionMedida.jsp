<%--
* Resumen		
* Objeto		: ejecucionMedida.jsp
* Descripción		: Crear la funcionalidad de consultar las obligaciones a verificar en una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística supervisión realizada
* Fecha de Creación	: 29/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  29/08/2016   |   Luis García Reyna          |     Registrar Ejecucion Medida
* OSINE791-RSIS25|  09/09/2016   |	Alexander Vilca Narvaez 	| Modificar la funcionalidad para el modo registro y consulta en los Tabs               
* OSINE_MAN_DSR_0022|16/06/2017  |  Carlos Quijano Chavez::ADAPTER       |Cambiar el tipo de Cierre automatico a manual al generar supervision
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/ejecucionMedida/ejecucionMedida.js" />'
        charset="utf-8"></script>
    </head>
    <body>
        <input type="hidden" id="flagInfracciones" name="flagInfracciones" value="">
        <input type="hidden" id="fechaCreacionOS" name="fechaCreacionOS" value="${sup.ordenServicioDTO.fechaHoraCreacionOS}">
        <input type="hidden" id="datoFlagEjecucionMedida" name="datoFlagEjecucionMedida" value="${sup.flagEjecucionMedida}" >
        <input type="hidden" id="datoFlagObstaculizados" name="datoFlagObstaculizados" value="" >
        <input type="hidden" id="datoCartaVisita" name="datoCartaVisita" value="${sup.cartaVisita}" >
        <input type="hidden" id="datoFechaTerminoRS" name="datoFechaTerminoRS" value="${sup.fechaFin}" >
        <input type="hidden" id="datoHoraTerminoRS" name="datoHoraTerminoRS" value="${sup.horaFin}" >
        
        
        
        <div class="form" style="width:100%">
            <form id="frmEM">
                <div id="divMensajeValidaFrmEM" class="errorMensaje" style="display: none"></div>
                <div id='obligacionesIncumplidas' style="margin: 15px 0px">
                    <div <c:if test="${modo=='registro'}">class="pui-subpanel ui-widget-content"</c:if>>
                        <c:if test="${modo=='registro'}">
                        <div class="pui-subpanel-subtitlebar"><span class="ui-panel-title">Infracciones Encontradas</span></div>
                        </c:if>
                        <div class="pui-subpanel-content">

                            <div class="filaForm tac" id="seccChkEM">
                                <div class="lbl-large vam"><label>¿Se ejecut&oacute la medida de Seguridad? <c:if test="${modo=='registro'}">: (*)</c:if></label></div>
                                <div class="vam">                           
                                    <input id="radioSiEjecMed" type="radio" name="radioEjecutaMedida" value="1" onclick="coSuDsrEmEm.fxRegistraEM.procesaRegistroFlagEjecucionMedidaSi();" <c:if test="${sup.flagEjecucionMedida=='1'}">checked="checked"</c:if> <c:if test="${modo=='consulta'}">disabled</c:if> />
                                    <label for="radioSiEjecMed" class="radio">Si</label>
                                </div>
                                <div class="vam" style="margin-left : 8px;">
                                    <input id="radioNoEjecMed" type="radio" name="radioEjecutaMedida" value="0" onclick="coSuDsrEmEm.fxRegistraEM.procesaRegistroFlagEjecucionMedidaNo();" <c:if test="${sup.flagEjecucionMedida=='0'}">checked="checked"</c:if> <c:if test="${modo=='consulta'}">disabled</c:if> />
                                    <label for="radioNoEjecMed" class="radio">No</label>
                                </div>
                            </div>
							<!--  OSINE_MAN_DSR_0022 - Inicio  -->
							<div id="medidaSeguridad" class="filaForm tac medidaSeguridadCombo" >
								<div class="lbl-large">
									<label>Medida de Seguridad <c:if test="${modo=='registro'}">: (*)</c:if></label>
								</div>

								<div>
									<select id="comboCierre">
										<option value="" selected>Seleccionar:</option>
										<option value="1">Cierre Total</option>
										<option value="2">Cierre Parcial</option>

									</select>
								</div>

							</div>
							<!--  OSINE_MAN_DSR_0022 - Fin  -->
                            <div class="tac" style="margin: 20px 0px;" id="seccGridDetaSupIncum">
                                <div id="gridContenedorFilesObligacionIncumplidas" class="content-grilla"></div>
                            </div>

                            <div class="filaForm tal" id="seccMsjNoInfrac">
                                <div class="lbl-large vam">
                                    <label>No se identificaron Infracciones</label>
                                </div>
                            </div> 
                        </div>   
                    </div>
                </div>
                <c:if test="${modo=='registro'}">
                <div id='divDatosFinalesSupervision' style="margin: 15px 0px">
                    <div class="pui-subpanel ui-widget-content">
                        <div class="tac">
                            <div class="pui-subpanel-subtitlebar"><span class="ui-panel-title">Datos Finales Supervisi&oacuten</span></div>
                            <div class="pui-subpanel-content form">
                                <div class="filaForm tal" id="seccCartaVisita">
<!--                                    
                                    flagInfracciones=='0'
                                        -->
                                    <div class="lbl-small vam"><label>Carta Visita : (*)</label></div>
                                    <div class="vam">
                                        <input  id="cartaVisita" validate="[O]" maxlength="10" type="text" class="ipt-medium" value="${sup.cartaVisita}" />
                                    </div>

                                </div>
                                <div class="filaForm tac">                  
                                    <div class="lbl-small vam"><label>Fecha Fin (*):</label></div>
                                    <div class="vam">
                                        <input id="fechaTerminoRSEM" validate="" type="text" maxlength="10" class="ipt-medium" name="fechaTerminoRSEM" value="${sup.fechaFin}" disabled="disabled" <c:if test="${modo=='consulta'}">disabled</c:if>/>
                                    </div>

                                    <div class="lbl-small vam"><label>Hora Fin (*):</label></div>
                                    <div class="vam">
                                        <input id="horaTerminoRSEM" validate="" type="text" maxlength="8" class="timepicker ipt-medium" name="horaTerminoRSEM" value="${sup.horaFin}" disabled="disabled" <c:if test="${modo=='consulta'}">disabled</c:if>/>
                                        <input id="horaActualSistema" type="hidden" class="timepicker ipt-medium" name="horaActualSistema" value=""/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>
            </form>
            <c:if test="${modo=='registro'}"><div class="txt-obligatorio">(*) Campos obligatorios</div></c:if>
            
            <!-- Grilla Modo Consulta --> 
            <c:if test="${modo=='consulta'}">
            <div class="tac" >
                <div id="gridContenedorEjecucionMedidaCons"  class="content-grilla"></div>
            </div>
            </c:if>
        </div>
        <!--   OSINE791 - RSIS17 - Inicio -->
        <div id="divGenerarResultadosDsr" style="display: none;" class="dialog" title="RESULTADOS">
            <div id="gridContenedorGenerarResultadosDsr" class="content-grilla"></div>
            <div id="divContextMenuContenedorGenerarResultadosDsr"></div>
            <div class="botones">
                <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>
        </div>
        <!--   OSINE791 - RSIS17 - Fin -->
        <c:if test="${modo=='registro'}">
        <div class="botones">
            <input type="button" id='btnGenerarResultados' title="Generar Resultados" class="btn-azul btn-medium" value="Generar Resultados">
        </div>
        </c:if>
        <div class="btnNavSupervisionDsr">
            <c:if test="${modo=='registro'}">
            <input type="button" id='btnAnteriorOI' title="Anterior" class="btn-azul btn-small" value="Anterior">
            </c:if>
            <c:if test="${modo=='consulta'}">
             <!--OSINE791 - RSIS25 - Inicio--> 
            <input type="button" id="btnAnteriorRecepcionVisitaCons" class="btn-azul btn-small" value="Anterior" style="display: none;">
            <!--OSINE791 - RSIS25 - Fin-->
            </c:if>
        </div>
    </body>
</html>