<%--
* Resumen		
* Objeto		: obligacionDsrDetalle.jsp
* Descripción		: supervision
* Fecha de Creación	: 17/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*OSINE791–RSIS10 | 26/08/2016    | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar una obligación como "CUMPLIDA" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
*OSINE791–RSIS11 | 26/08/2016    | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar una obligación como "NO APLICA" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
*OSINE791–RSIS12 | 26/08/2016    | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar una obligación como "POR VERIFICAR" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
*OSINE791–RSIS13 |31/08/2016     | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar información de una obligación "OBSTACULIZADA" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
*OSINE791–RSIS14 |01/09/2016     | Zosimo Chaupis Santur        | Considerar la funcionalidad para subsanar una obligación marcada como "INCUMPLIDA" de una supervisión  de orden de supervisión DSR-CRITICIDAD
*OSINE791-RSIS23 |01/09/2016     | José Herrera Pajuelo         | Crear la funcionalidad para consultar la obligación de una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD
*OSINE791-RSIS25 |08/09/2016     |	Alexander Vilca Narvaez 	| Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
*OSINE_MAN_DSR_0030|  27/06/2017   |   Carlos Quijano Chavez::ADAPTER      |    Corregir la funcionalidad para que se muestre el comentario                        
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/obligacion/obligacionDsrDetalle.js" />'
        charset="utf-8"></script>
    </head>
    <body>
        <div class="form" id="formRegSupervisionDsrOblDet">     	
            <!--     	<div class="tac"> -->
            <!--             <div id="gridContenedorObligacionListado" class="content-grilla"></div> -->
            <!--             <div id="divContextMenuObligacionListado"></div> -->
            <!--         </div>     -->

            <!--         <div id="divMensajeValidaFrmSupDsr" class="errorMensaje" style="display: none"></div> -->
            <div class="obligacionDetalleContainer">		
                <div class="pui-panel-content">                    
                    <!--             <fieldset style="width:825px;">            	 -->
                    <input id="oblDsrDetIdDetalleSupervision" type="hidden" value="-1"  />
                    <input id="oblProductoSuspender" type="hidden" value="-1"  />
                    <input id="oblDsrPrioridad" type="hidden" value="-1"  />
                    <input id="oblDsrTotalPrioridad" type="hidden" value="-1"  />	
                    <input id="idResultadoObligacionDetSupActual" type="hidden" value="-1" codigo="-1"   />
                    <input id="idInfraccionOblDetSup" type="hidden" value=""  />
                    <input id="idResultadoObligacionAntOblDetSup" type="hidden" value="" codigo=""  />
                    <input id="FlagInicialResultadoObligacionOblDetSup" type="hidden" value="" codigo =""  />
                    <input id="ObliDsrComentarioSubsanacion" type="hidden" value=""  />
                    <input id="countComeDetSup" type="hidden" value=""  />
                    <!--OSINE_SFS-791 - RSIS 23 - Inicio-->
                    <input type="hidden" id="flagEjecMedida" value="${sup.flagEjecucionMedida}"/> 
                    <!--OSINE_SFS-791 - RSIS 23 - Fin-->
                    <div class="filaForm">
                        <div class="lbl-medium vam"><label>Prioridad:</label></div>
                        <div class="vam">
                            <span class="prioridad"></span>/<span class="totalPrioridad"></span>
                        </div>
                    </div>
                    <div class="filaForm">
                        <div class="lbl-medium vam"><label>Descripción infracción:</label></div>
                        <div class="vam">
                            <textarea class="descripcionInfraccion" style="width: 600px" disabled="disabled" readonly="readonly" rows="4" maxlength="4000" ></textarea>                		                         
                        </div>
                        <a id="descargaAdjuntoInfraccion" href="#" title="Descargar" class="button_2 btn_small">
                            <span class="ui-icon ui-icon-circle-arrow-s"></span>
                        </a>
                    </div>
                    <div class="filaForm oblDsrRadiosIncumplimiento">
                        <div class="lbl-medium vam"><label>Incumplimiento<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                        <div class="vam">    
                            
                            <div id="divradioDsrOblDelIncumplimientoSi">            	
                                <input id="radioDsrOblDelIncumplimientoSi" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoSi}" codigo="${CodigoradioDsrOblDelIncumplimientoSi}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoSi" class="radio">SI</label>
                            </div>
                            <!--  OSINE791 - RSIS10 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoNo" style="margin-left : 8px;">
                                <input id="radioDsrOblDelIncumplimientoNo" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoNo}" codigo="${CodigoradioDsrOblDelIncumplimientoNo}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoNo" class="radio">NO</label>
                            </div>
                            <!--  OSINE791 - RSIS10 - Fin -->
                            <!--  OSINE791 - RSIS12 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoPorVerificar" style="margin-left : 8px;">
                                <input id="radioDsrOblDelIncumplimientoPorVerificar" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoPorVerificar}" codigo="${CodigoradioDsrOblDelIncumplimientoPorVerificar}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoPorVerificar" class="radio">POR VERIFICAR</label>
                            </div>	
                            <!--  OSINE791 - RSIS12 - Fin -->
                            <!--  OSINE791 - RSIS11 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoNoAplica" style="margin-left : 8px;">					
                                <input id="radioDsrOblDelIncumplimientoNoAplica" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoNoAplica}" codigo="${CodigoradioDsrOblDelIncumplimientoNoAplica}"<c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoNoAplica" class="radio">NO APLICA</label>
                            </div>
                            <!--  OSINE791 - RSIS11 - Fin -->
                            <!--  OSINE791 - RSIS12 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoObstaculizado">					
                                <input id="radioDsrOblDelIncumplimientoObstaculizado" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoObstaculizado}" codigo="${CodigoradioDsrOblDelIncumplimientoObstaculizado}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoObstaculizado" class="radio">OBSTACULIZADO</label>
                            </div>
                            <!--  OSINE791 - RSIS12 - Fin -->
                            
                            <div id="divComentarioSubsanacion" class="vam" style="margin: 0 0 0 10px">		
                                <div id="btnAbrirEditarComentarioSubsanacion" class="button_2 btn_small">
                                    <span class="ui-icon ui-icon-search"></span>
                                </div>
                            </div>
                            <div id="divBtnSubsanar" class="vam" style="margin: 0 0 0 10px" style="display:none;">		
                                <div id="btnSubsanar" class="button_2 btn_small">SUBSANAR</div>
                            </div>
                          
                        </div>
                    </div>
                    <div class="containerEscenearioIncumplimiento" id ="DivcontainerEscenearioIncumplimiento">                                 
                        <table>
                            <tr>
                                <td style="width: 164px">
                                    <div class="lbl-medium vam"><label>Escenario del incumplimiento<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>		
                                </td>
                                <td>
                                    <div class="">
                                        <div id="gridContenedorObligacionDtlEscenario" class="content-grilla"></div>
                                        <div id="divContextMenuObligacionDtlEscenario"></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    
                    <div class="containerSinEscenario" id="DivcontainerSinEscenario">
                        <div class="filaForm">
                            <div class="lbl-medium vam"><label>Comentario de infracci&oacute;n<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                            <div class="vam">
                                <div class="button_2 btn_small" title="Buscar" id="btnAbrirComInctoSinEscenario">
                                    <span id="spnAbrirComInctoSinEscenario" class="ui-icon ui-icon-comment"></span>
                                </div>                                
                            </div>
                        </div>
                        <!--OSINE_SFS-791 - RSIS 23 - Inicio-->
                        <c:if test="${modo=='consulta' && sup.flagEjecucionMedida=='1'}">
                        <!--OSINE_SFS-791 - RSIS 23 - Fin-->
                        <div class="filaForm">
                            <div class="lbl-medium vam"><label>Ejecuci&oacute;n de Medida:</label></div>
                            <div class="vam">
                                <div class="button_2 btn_small" title="Buscar" id="btnAbrirComInctoSinEscenario">
                                    <span onclick="coSupeDsrOblOblDet.abrirPopUpEjecucionMedidaIncumplimiento()" class="ui-icon ui-icon-search"></span>
                                </div>  
                            </div>
                        </div>
                        </c:if>
                    </div>
                    <div class="containerEscenearioProductoSuspendeer" id="DivcontainerEscenearioProductoSuspendeer">           
                        <table>
                            <tr>
                                <td style="width: 164px">
                                    <div class="lbl-medium vam"><label>Producto a suspender SCOP:</label></div>		
                                </td>
                                <td>
                                    <div class="">
                                        <div id="gridContenedorObligacionDtlPrdtos" class="content-grilla"></div>
                                        <div id="divContextMenuObligacionDtlPrdtos"></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!--  OSINE791 - RSIS13 - Inicio -->
                    <div class="filaForm" id="containerComentarioObstaculizado">
                        <div class="lbl-medium vam"><label>Comentario de Obstaculizaci&oacute;n<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                        <div class="vam">
                            <textarea id="txtComentarioObstaculizado" class="ipt-medium-small-medium-small-small " validate="[O]" rows="4" maxlength="4000" <c:if test="${modo=='consulta'}">disabled</c:if>></textarea>                		                         
                        </div>
                    </div>
                    <!--  OSINE791 - RSIS13 - Fin -->
                    <!--  OSINE791 - RSIS8 - Inicio -->
                    <div class="pui-subpanel ui-widget-content" id='medioProbatorioDsr' style="display: none;">
                        <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title">Medios probatorios</span>
                        </div>
                        <div class="pui-subpanel-content">
                            <div class="filaForm tar">
                                <div class="lbl-medium">
                                    <input type="button" id="btnAbrirRegistrarMedioProb" class="btn-azul btn-small" value="Agregar Archivo" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
                                </div>
                            </div>
                            <div class="tac">
                                <div id="gridContenedorArchivosMPDsr" class="content-grilla"></div>
                                <div id="divContextArchivosMPDsr"></div>
                            </div>                	
                        </div>
                    </div>    
                    <!--  OSINE791 - RSIS8 - Fin -->  
                    <!--  OSINE791 - RSIS14 - Inicio -->  
                    <div id="dialogComentarioSubsanacion" class="dialog" style="display:none;"></div>
                    <!--  OSINE791 - RSIS14 - Fin -->  
                    
                </div>	
            </div>
            <!-- OSINE_SFS-791 - RSIS 25 - Inicio -->
        		<div id=obligacionDsrDialogEjecucionMedidaIncumplimiento class="dialog" style="display:none;"></div>
        	<!-- OSINE_SFS-791 - RSIS 25 - Fin -->
        	<!-- OSINE_MAN_DSR_0030 - Inicio -->
        	<div id=dialogComentarioIncumplimiento class="dialog" style="display:none;"></div>
        	<!-- OSINE_MAN_DSR_0030 - Fin -->
            <c:if test="${modo=='registro'}"><div class="txt-obligatorio">(*) Campos obligatorios</div></c:if>
            <div class="botones">
                <input id="btnAnteriorDetalleObligacion" type="button" title="Anterior" class="btn-azul btn-small" value="Anterior">		
                <input id="btnBackListadoObligacion" type="button" title="Listado" class="btn-azul btn-small" value="Listado">
                <input id="btnSiguienteDetalleObligacion" type="button" title="Siguiente" class="btn-azul btn-small" value="Siguiente">
            </div>
              
        </div>
    </body>
</html>
