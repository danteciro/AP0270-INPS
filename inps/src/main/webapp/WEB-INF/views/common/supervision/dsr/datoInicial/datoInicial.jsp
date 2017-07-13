<%--
* Resumen
* Objeto	: datoInicial.jsp
* Descripción	: Registro de datos Iniciales para la Generacion de una Supervision
* Fecha de Creación	: 17/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor	: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* REQF-0005      |  17/08/2016   |   Zosimo Chaupis Santur      |     Crear la funcionalidad para registrar los datos iniciales de una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística SUPERVISIÓN REALIZADA                           |
* REQF-0022      |  22/08/2016   |   Jose Herrera Pajuelo       |     Crear la funcionalidad para consultar una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD.
* OSINE791-RSIS25|  09/09/2016   |	Alexander Vilca Narvaez | Modificar la funcionalidad para el modo registro y consulta en los Tabs                                                             
* OSINE791-RSIS04 | 05/10/2016   |   Zosimo Chaupis Santur      |     CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
* OSINE_SFS-791  |  06/10/2016   |   Luis García Reyna          |     Registrar Supervisión No Iniciada
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/datoInicial/datoInicial.js" />'
        charset="utf-8"></script>
    </head>
    <body>
<!--        <input type="hidden" id="idSupervision" value="${sup.idSupervision}">-->
        <input type="hidden" id="idResultSuperInicial" value="${sup.resultadoSupervisionInicialDTO.idResultadosupervision}">
        <input type="hidden" id="codidoResultadoSupervision" value="${sup.resultadoSupervisionInicialDTO.codigo}">
        <input type="hidden" id="idResultadoSupervision" value="${sup.resultadoSupervisionDTO.idResultadosupervision}">
        <input type="hidden" id="codidoIdResultadoSupervision" value="${sup.resultadoSupervisionDTO.codigo}">
        <input type="hidden" id="fechaTerminoRS" value="${sup.fechaFin}" >
        <input type="hidden" id="horaTerminoRS" value="${sup.horaFin}" >

        <div class="tac">
            <div class="form">
                <c:if test="${sup.ordenServicioDTO.iteracion == 1}">
                <div id="divRadiosDT" class="filaForm tac" style="margin: 35px 0px;">
                    <div class="lbl-medium">
                        <label>¿Se realiza supervisi&oacute;n?<c:if test="${modo=='registro'}">(*)</c:if></label>
                        </div> 
                        <div id="divInicioPrincipal">
                            <div id="supervisionSi">
                                <input id="radioSiSupervision" type="radio" onclick="coDatoInicial.RegistrarFechaSistemaSi();" name="radioSupervision" value="${ValorradioSupervisionInicialSi}" codigo="${CodigoradioSupervisionInicialSi}">
                            <label for="radioSiSupervision" class="radio">SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        </div>
                        <div id="supervisionNo">
                            <input id="radioNoSupervision" type="radio"  onclick="coDatoInicial.RegistrarFechaSistemaNo();" name="radioSupervision" value="${ValorradioSupervisionInicialNo}" codigo="${CodigoradioSupervisionInicialNo}">
                            <label for="radioNoSupervision" class="radio">NO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        </div>
                        <c:if test="${modo=='registro'}">
                            <div id="supervisionPorVerificar">
                                <input id="radioPorVerificar" type="radio"  name="radioSupervision" onclick="coDatoInicial.RegistrarFechaSistemaPorVerificar();" value="${ValorradioSupervisionInicialPorVerificar}" codigo="${CodigoradioSupervisionInicialPorVerificar}">
                                <label for="radioPorVerificar" class="radio">POR VERIFICAR&nbsp;&nbsp;&nbsp;</label>
                            </div>
                        </c:if>
                    </div>
                    <div id="DivradioObstaculizado">
                        <div >
                            <input id="radioObstaculizacionSupervision" type="radio" disabled="disabled"  name="radioSupervision"  value="${ValorradioSupervisionInicialObstaculizado}" codigo="${CodigoradioSupervisionInicialObstaculizado}">
                            <label for="radioObstaculizacionSupervision" class="radio">OBSTACULIZADO</label>
                        </div>
                    </div>
                </div>
                </c:if>
                <div id="divFechas" class="tac" style="margin: 0px 0px 50px;">
                    <div id="divFrmDatosInicialSuper">
                        <div class="filaForm ">
                            <div class="lbl-small" ><label for="fechaInicioSP">Fecha Inicio<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                                <div>
                                    <input id="fechaInicioSP" type="text" validate="[O]" maxlength="10" class="ipt-medium" name="fechaInicioSP" value="" disabled="disabled" />
                                </div>
                                <div class="lbl-small" ><label for="horaInicioSP">Hora Inicio<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                                <div>
                                    <input id="horaInicioSP" class="ipt-medium" type="text" validate="[O]" maxlength="8"   name="horaInicioSP" value="" disabled="disabled" >
                                </div>
                                <c:if test="${sup.ordenServicioDTO.iteracion > 1}">
                                <div class="vam">	    
                                    <div class="button_2 btn_small"  id="btnObtenerFechaHoraSistema">
                                        <span class="ui-icon ui-icon-check"></span>
                                    </div>
	                  	</div> 
                                </c:if>
                            </div>

                            <!--/* OSINE791 – RSIS22 - Inicio */-->    
                        <c:if test="${modo=='consulta'}">
                            <c:if test="${sup.ordenServicioDTO.iteracion == 1 || sup.ordenServicioDTO.iteracion > 1}">
                            <div class="filaForm" style="margin: 30px 0px">
                                <div class="lbl-small vam"><label>Fecha Fin:</label></div>
                                <div class="vam">
                                    <input id="fechaFinDI" type="text" validate="[O]" maxlength="10" disabled="disabled" class="ipt-medium" name="fechaFinRS" value="${sup.fechaFin}"/>
                                </div>
                                <div class="lbl-small vam"><label>Hora Fin:</label></div>
                                <div class="vam">
                                    <input id="horaFinDI" type="text" validate="[O]" maxlength="8" disabled="disabled" class="timepicker ipt-medium" name="horaFinRS" value="${sup.horaFin}"/>
                                </div>
                            </div>
                            </c:if>        
                        </c:if>
                        <!--/* OSINE791 – RSIS22 - Fin */-->
                    </div>   
                </div>                
            </div>
        </div>

        <!--OSINE_SFS-791 - RSIS 03 - Inicio -->
        <div id="divMensajeValidaFrmSupervision" class="errorMensaje" style="display: none"></div>
        <fieldset id="fldstDatosFinalesSupervision" style="margin-top: -15px">
            <legend>Datos Finales Supervisi&oacute;n</legend>
            <div class="form" id="divFrmDatosFinalesSupervision">
                <div class="filaForm">
                    <div class="lbl-small vam"><label>Fecha Inicio(*):</label></div>
                    <div class="vam"> 
                        <input id="fechaInicioSup" name="fechaInicioSup" type="text" validate="[O]" maxlength="10" class="ipt-medium" value="${sup.fechaInicio}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                        </div>
                        <div class="lbl-small vam"><label>Hora Inicio(*):</label></div>
                        <div class="vam">
                            <input id="horaInicioSup"  name="horaInicioSup" class="timepicker ipt-medium" type="text" validate="[O]" maxlength="8" value="${sup.horaInicio}"  <c:if test="${modo=='consulta'}">disabled</c:if> >
                        </div>
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>N° Carta Visita (*):</label></div>
                        <div class="vam">
                            <input id="txtCartaVisitaSupervision" validate="[O]" type="text" maxlength="10" class="ipt-medium" value="${sup.cartaVisita}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                        </div>                                                          
                    </div>

                    <div class="filaForm">
                        <div id="divMotivoNoSupervision">
                            <div class="lbl-small vam"><label>Motivo no supervisi&oacute;n (*):</label></div>
                            <div class="vam">
                                <select validate="[O]" class="slct-medium" id="slctMotivoNoSupervision" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                    <option value="" especifica="">--Seleccione--</option>
                                    <c:forEach items="${listadoMotivoNoSuprvsn}" var="t">
                                        <option value='${t.idMotivoNoSupervision}' especifica="${t.flagEspecificar}" <c:if test="${t.idMotivoNoSupervision==sup.motivoNoSupervision.idMotivoNoSupervision}">selected</c:if>>${t.descripcion}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>  
                        <div id="divEspecificar" style="display: none;">            
                            <div class="lbl-small vam"><label>Especifique (*):</label></div>
                            <div class="vam">
                                <input id="txtEspecificarSupervision" type="text" maxlength="100" class="ipt-medium-small-medium" value="${sup.descripcionMtvoNoSuprvsn}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                            </div>        
                        </div>        
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Comentarios de la supervisi&oacute;n (*):</label></div>
                        <div class="vam">
                            <textarea class="ipt-medium-small-medium-small-small" validate="[O]" rows="4" id="txtComentariosSupervision" maxlength="4000" <c:if test="${modo=='consulta'}">disabled</c:if>>${sup.observacion}</textarea>
                        </div>
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Fecha Fin(*):</label></div>
                        <div class="vam">
                            <input id="fechaFinSup" name="fechaFinSup" type="text" maxlength="10" class="ipt-medium" value="${sup.fechaFin}"  <c:if test="${modo=='consulta'}">disabled</c:if>/>
                        </div>

                        <div class="lbl-small vam"><label>Hora Fin(*):</label></div>
                        <div class="vam">
                            <input id="horaFinSup"  name="horaFinSup" class="timepicker ipt-medium" type="text" maxlength="8" value="${sup.horaFin}"  <c:if test="${modo=='consulta'}">disabled</c:if> >
                        </div>
                    </div>

                    <div class="botones" style="margin-top: 15px">
                        <input type="button" id="btnGuardarDatosFinalesSupervision" class="btn-azul btn-small" value="Guardar" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
                    </div>

                </div>
            </fieldset>    

            <fieldset id="fldstAdjuntosSupervision">
                <legend>Adjuntos Supervisi&oacute;n</legend>
                <div class="pui-subpanel-content">
                    <div class="filaForm tar">
                        <div class="lbl-medium">
                            <input type="button" id="btnAbrirRegistrarArchivosAdjuntos" class="btn-azul btn-small" value="Agregar Archivo" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
                        </div>
                    </div>
                </div>

                <div class="tac" style="margin: 20px 0px;">
                    <div>
                        <div id="gridContenedorAdjuntarArchivosSupervision" class="content-grilla"></div>
                        <div id="divContextMenuAdjuntarArchivosSupervision"></div>
                    </div>
                </div>

            </fieldset>
            
            <div class="botones" style="margin-top: 15px">
                <input type="button" id="btnGenerarResultadosNo" class="btn-azul btn-medium" value="Generar Resultados" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
            </div>
                        
            <!--OSINE_SFS-791 - RSIS 03 - Fin -->
            <!--OSINE_SFS-791 - RSIS 04 - Inicio -->
            <fieldset id="fldstDatosFinalesSupervisionObstaculizado">
                <legend>Registrar Obstaculizaci&oacute;n</legend>
                <div class="form" id="divFrmDatosFinalesSupervisionObstaculizado">
                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Fecha Inicio(*):</label></div>
                        <div class="vam"> 
                            <input id="fechaInicioSupObstaculizado" name="fechaInicioSupObstaculizado" type="text" validate="[O]" maxlength="10" class="ipt-medium" value="${sup.fechaInicioPorVerificar}" disabled />
                    </div>
                    <div class="lbl-small vam"><label>Hora Inicio(*):</label></div>
                    <div class="vam">
                        <input id="horaInicioSupObstaculizado"  name="horaInicioSupObstaculizado" class="timepicker ipt-medium" type="text" validate="[O]" maxlength="8" value="${sup.horaInicioPorVerificar}"  disabled/>
                    </div>
                </div>

                <div class="filaForm">
                    <div class="lbl-small vam"><label>N° Carta Visita (*):</label></div>
                    <div class="vam">
                        <input id="txtCartaVisitaSupervisionObstaculizado" validate="[O]" type="text" maxlength="10" class="ipt-medium" value="${sup.cartaVisita}" <c:if test="${modo=='consulta'}">disabled</c:if> />
                        </div>                                       	                    
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Comentario de Obstaculizaci&oacute;n (*)</label></div>
                        <div class="vam">
                            <textarea class="ipt-medium-small-medium-small-small" validate="[O]" rows="4" id="txtComentariosSupervisionObstaculizado" maxlength="4000" <c:if test="${modo=='consulta'}">disabled</c:if>>${sup.observacion}</textarea>
                        </div>
                    </div>

                    <div class="filaForm">
                        <div class="lbl-small vam"><label>Fecha Fin(*):</label></div>
                        <div class="vam">
                            <input id="fechaFinSupObstaculizado" name="fechaFinSupObstaculizado" type="text" maxlength="10" class="ipt-medium" value="${sup.fechaFin}"  disabled/>
                    </div>

                    <div class="lbl-small vam"><label>Hora Fin(*):</label></div>
                    <div class="vam">
                        <input id="horaFinSupObstaculizado"  name="horaFinSupObstaculizado" class="timepicker ipt-medium" type="text" maxlength="8" value="${sup.horaFin}"  disabled />
                    </div>
                </div>

                <div id="divbtnGuardarSupObstaculiza" class="botones" style="margin-top: 15px">
                    <input type="button" id="btnGuardarDatosFinalesSupervisionObstaculizado" class="btn-azul btn-small" value="Guardar" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
                    </div>
                    <div id="divbtnGenerarResultadosSupObstaculiza" class="botones" style="margin-left: 255px;margin-top: -30px">
                        <input type="button" id="btnGenerarResultadoSupervisionObstaculizado" title="Generar Resultados" class="btn-azul btn-medium" value="Generar Resultados" >
                </div>

            </div>
                            </fieldset>                        
            <!--OSINE_SFS-791 - RSIS 04 - Fin -->

            <!--/* OSINE791 – RSIS4 - Inicio */-->
            <div class="btnGuardarSupervisionDsrPorVerificar" id="DivbtnGuardarSupervisionDsrPorVerificar">
            <c:if test="${modo=='registro'}">
                <input type="button" id="btnGuardarDatosInicialSupervisionPorVerificar" class="btn-azul btn-small" value="Guardar">
            </c:if> 
        </div>
        <!--/* OSINE791 – RSIS4 - Fin */-->
        <!--/* OSINE791 – RSIS22 - Inicio */-->
        <c:if test="${modo=='registro'}"><div class="txt-obligatorio"  id="obligatorio"></div></c:if>
            <div class="btnNavSupervisionDsr">
            <c:if test="${modo=='registro'}">
                <input type="button" id="btnGuardarDatosInicialSupervision" class="btn-azul btn-small" value="Siguiente">
            </c:if>
            <c:if test="${modo=='consulta'}">
                <!--/* OSINE791 - RSIS25 - Inicio */-->
                <input type="button" id="btnSiguienteRecepcionVisita" class="btn-azul btn-small" value="Siguiente" style="display: none;">
                <!--/* OSINE791 - RSIS25 - Fin */-->
            </c:if>
            <!--/* OSINE791 – RSIS22 - Fin */-->
        </div>
        <!--   OSINE791 - RSIS04 - Inicio -->
        <div id="divGenerarResultadosDsrInicial" style="display: none;" class="dialog" title="RESULTADOS">
            <div id="gridContenedorGenerarResultadosDsrInicial" class="content-grilla"></div>
            <div id="divContextMenuContenedorGenerarResultadosDsrInicial"></div>
            <div class="botones">
                <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>
        </div>
        <!--   OSINE791 - RSIS04 - Fin -->
    </body>
</html>
