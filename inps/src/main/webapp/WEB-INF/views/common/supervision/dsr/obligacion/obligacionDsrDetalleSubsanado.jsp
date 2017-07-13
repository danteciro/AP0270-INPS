<%-- 
    Document   : obligacionDsrDetalleSubsanado
    Created on : 06/10/2016, 11:29:42 AM
    Author     : pmoscosoa
   
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/obligacion/obligacionDsrDetalleSubsanado.js" />'
        charset="utf-8"></script>
    </head>
    <body>
        <div class="form" id="formRegSupervisionDsrOblDet">     	
            
            <div class="obligacionDetalleContainer">		
                <div class="pui-panel-content">                    
                    
                    <input id="oblDsrDetIdDetalleSupervision" type="hidden" value="-1"  />
                    <input type="hidden" id="oblDsrDetIdDetalleSupervisionSub" value="">
                    <input type="hidden" id="oblDsrDetIdDetalleSupervisionAntSub" value="">
                    <input id="oblProductoSuspender" type="hidden" value="-1"  />
                    <input id="oblDsrPrioridad" type="hidden" value="-1"  />
                    <input id="oblDsrTotalPrioridad" type="hidden" value="-1"  />	
                    <input id="idResultadoObligacionDetSupActual" type="hidden" value="-1" codigo="-1"   />
                    <input id="idInfraccionOblDetSup" type="hidden" value=""  />
                    <input id="idResultadoObligacionAntOblDetSup" type="hidden" value="" codigo=""  />
                    <input id="FlagInicialResultadoObligacionOblDetSup" type="hidden" value="" codigo =""  />
                    <input id="ObliDsrComentarioSubsanacion" type="hidden" value=""  />
                    <input id="countComeDetSup" type="hidden" value=""  />
                    <input id="idOblDsrDetIdDetalleLevantamiento" type="hidden" value=""  />
                    <!--OSINE_SFS-791 - RSIS 23 - Inicio-->
                    <input type="hidden" id="flagEjecMedida" value="${sup.flagEjecucionMedida}"/> 
                    <!--OSINE_SFS-791 - RSIS 23 - Fin-->
                    
                    <div class="filaForm">
                        <div class="lbl-medium vam"><label>Prioridad:</label></div>
                        <div class="vam">
                            <span class="prioridad"></span>/<span class="totalPrioridad"></span>
                        </div>
                    </div>
                    <div class="pui-subpanel ui-widget-content" id='InfraccionDsrMediosRev' style="">
                            <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title">Infracciones</span>
                        </div>
                        <div class="pui-subpanel-content">
                    <div class="filaForm">
                        <div class="lbl-medium vam"><label>Descripción infracción:</label></div>
                        <div class="vam">
                            <textarea class="descripcionInfraccion" style="width: 600px" disabled="disabled" readonly="readonly" rows="4" maxlength="4000" ></textarea>                		                         
                        </div>
                        <a id="descargaAdjuntoInfraccion" href="#" title="Descargar" class="button_2 btn_small">
                            <span class="ui-icon ui-icon-circle-arrow-s"></span>
                        </a>
                    </div>
                    
                    <div class="filaForm oblDsrRadiosIncumplimiento" hidden="true">
                        <div class="lbl-medium vam"><label hidden="true">Incumplimiento<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                        <div class="vam">    
                            
                            <div id="divradioDsrOblDelIncumplimientoSi">            	
                                <input id="radioDsrOblDelIncumplimientoSi" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoSi}" codigo="${CodigoradioDsrOblDelIncumplimientoSi}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoSi" class="radio" hidden="true">SI</label>
                            </div>
                            <!--  OSINE791 - RSIS10 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoNo">
                                <input id="radioDsrOblDelIncumplimientoNo" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoNo}" codigo="${CodigoradioDsrOblDelIncumplimientoNo}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoNo" class="radio" hidden="true">NO</label>
                            </div>
                            <!--  OSINE791 - RSIS10 - Fin -->
                            <!--  OSINE791 - RSIS12 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoPorVerificar">
                                <input id="radioDsrOblDelIncumplimientoPorVerificar" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoPorVerificar}" codigo="${CodigoradioDsrOblDelIncumplimientoPorVerificar}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                            <label for="radioDsrOblDelIncumplimientoPorVerificar" class="radio" hidden="true">POR VERIFICAR</label>
                            </div>	
                            <!--  OSINE791 - RSIS12 - Fin -->
                            <!--  OSINE791 - RSIS11 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoNoAplica">					
                                <input id="radioDsrOblDelIncumplimientoNoAplica" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoNoAplica}" codigo="${CodigoradioDsrOblDelIncumplimientoNoAplica}"<c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoNoAplica" class="radio" hidden="true">NO APLICA</label>
                            </div>
                            <!--  OSINE791 - RSIS11 - Fin -->
                            <!--  OSINE791 - RSIS12 - Inicio -->
                            <div id="divradioDsrOblDelIncumplimientoObstaculizado">					
                                <input id="radioDsrOblDelIncumplimientoObstaculizado" type="radio" name="radioDsrOblDelIncumplimiento" value="${ValorradioDsrOblDelIncumplimientoObstaculizado}" codigo="${CodigoradioDsrOblDelIncumplimientoObstaculizado}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblDelIncumplimientoObstaculizado" class="radio" hidden="true">OBSTACULIZADO</label>
                            </div>
                            <!--  OSINE791 - RSIS12 - Fin -->
                            
                            <div id="divComentarioSubsanacion" class="vam" style="display:none;">		
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
                                    <div class="lbl-medium vam"><label>Escenario del incumplimiento:</label></div>		
                                </td>
                                <td>
                                    <div class="">
                                        <div id="gridContenedorObligacionDtlEscenarioSub" class="content-grilla"></div>
                                        
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    
                    <div class="containerSinEscenario" id="DivcontainerSinEscenario">
                        <div class="filaForm">
                            <div class="lbl-medium vam"><label>Comentario de infracci&oacute;n:</label></div>
                            <div class="vam">
                                <div class="button_2 btn_small" title="Buscar" id="btnAbrirComInctoSinEscenario">
                                    <span id="spnAbrirComInctoSinEscenarioSub" class="ui-icon ui-icon-search"></span>
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
                                    <span onclick="coSupeDsrOblOblDetSub.abrirPopUpEjecucionMedidaIncumplimiento()" class="ui-icon ui-icon-search"></span>
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
                                        <div id="gridContenedorObligacionDtlPrdtosSub" class="content-grilla"></div>
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
                            <textarea id="txtComentarioObstaculizado" class="ipt-medium-small-medium-small-medium " validate="[O]" rows="4" maxlength="4000" <c:if test="${modo=='consulta'}">disabled</c:if>></textarea>                		                         
                        </div>
                    </div>
              <!--  Medios probatorios -->    
             <div class="pui-subpanel ui-widget-content" id='medioProbatorioDsrMediosRev' style="">
                                         <div class="pui-subpanel-subtitlebar">
                                        <span class="ui-panel-title">Medios probatorios</span>
                                         </div>
                  <div class="pui-subpanel-content">
                      
                   <div class="tac">
            	 <div iclass="">
                            <div id="gridContenedorArchivosMPDsrAnt" class="content-grilla"></div>
                    </div> 
               
                         </div>
                  </div>
              </div>
              </div>
                    </div>
                      <div class="pui-subpanel ui-widget-content" id='informacionLeventamiento' style="">
                        <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title">Informaci&oacute;n de Levantamiento</span>
                        </div>
                        <div class="pui-subpanel-content">
                           <div class="filaForm">
                        <div class="lbl-medium vam"><label>Levantamiento:</label></div>
                        <div class="vam">
                            <textarea id="txtdescripcionLevantamiento" class="descripcionLevantamiento" style="width: 600px" disabled="disabled" readonly="readonly" rows="4" maxlength="4000" ></textarea>                		                         
                        </div>
                       
                    </div> 
                            
                            <div class="pui-subpanel ui-widget-content" id='medioProbatorioDsrMediosLEVtr' style="">
                                         <div class="pui-subpanel-subtitlebar">
                                        <span class="ui-panel-title">Medios probatorios</span>
                                         </div>
                  <div class="pui-subpanel-content">
                      
                   <div class="tac">
                            <div id="gridContenedorArchivosMPDsrDetalleLevSub" class="content-grilla"></div>

                         </div>
                  </div>
              </div>
                            
                        </div>
                 
                    </div>    
                    <!--  OSINE791 - RSIS13 - Fin -->
                    
                    <!--  OSINE791 - RSIS38 - Ini -->
                   
                    
                   
                    
               <!--  OSINE791 - RSIS38 - Fin -->     
                    <!--  OSINE791 - RSIS38 - Inicio -->
                    <div class="pui-subpanel ui-widget-content" id='medioProbatorioDsr' style="">
                        <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title">Revisi&oacute;n</span>
                        </div>
                        <div class="pui-subpanel-content">
                           
                            <div class="filaForm oblDsrRadiosSubsana">
                        <div class="lbl-medium vam"><label>Subsano Infracci&oacute;n<c:if test="${modo=='registro'}">(*)</c:if>:</label></div>
                        <div class="vam">    
                            
                             <div class="filaForm oblDsrRadiosSubsanar" >
                             <div id="divradioDsrOblSiSubsana">            	
                                <input id="radioDsrOblSubsanadoSi" type="radio" name="radioDsrSubsana" value="${ValorradioSupervisionSubsanadoSi}" codigo="${CodigoradioSupervisionSubsanadoSi}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblSubsanadoSi" class="radio">SI</label>
                            </div>
                            <!--  OSINE791 - RSIS10 - Inicio -->
                            <div id="divradioDsrOblNoSubsana">
                                <input id="radioDsrOblSubsanadoNo" type="radio" name="radioDsrSubsana" value="${ValorradioSupervisionSubsanadoNo}" codigo="${CodigoradioSupervisionSubsanadoNo}" <c:if test="${modo=='consulta'}">disabled</c:if>>
                                <label for="radioDsrOblSubsanadoNo" class="radio">NO</label>
                            </div>
                         
                        </div>
                          
                        </div>
                    </div>
                            
                                   <div class="pui-subpanel ui-widget-content" id='medioProbatorioDsrMediosRev' style="">
                                         <div class="pui-subpanel-subtitlebar">
                                        <span class="ui-panel-title">Medios probatorios</span>
                                         </div>
                                           <div class="pui-subpanel-content">
                                                  <div class="filaForm tar">
                                                   <div class="lbl-medium">
                                              <input type="button" id="btnAbrirRegistrarMedioProbSub" class="btn-azul btn-small" value="Agregar Archivo" <c:if test="${modo=='consulta'}">style="display: none;"</c:if>>
                                          </div>
                                            </div>
                                         <div class="tac"> 
                                                 <div id="gridContenedorArchivosMPDsrSub" class="content-grilla"></div>
                                                 <div id="divContextArchivosMPDsrSub"></div>
                                            </div>  
                                          </div> 
                                 </div>    
                        </div>
                    </div>    
  
                </div>	
           <c:if test="${modo=='registro'}"><div class="txt-obligatorio">(*) Campos obligatorios</div></c:if>
            </div>
            <!-- OSINE_SFS-791 - RSIS 25 - Inicio -->
        		<div id=obligacionDsrDialogEjecucionMedidaIncumplimiento class="dialog" style="display:none;"></div>
        	<!-- OSINE_SFS-791 - RSIS 25 - Fin -->
            
            <div class="botones">
                <input id="btnAnteriorDetalleObligacion" type="button" title="Anterior" class="btn-azul btn-small" value="Anterior">		
                <input id="btnBackListadoObligacion" type="button" title="Listado" class="btn-azul btn-small" value="Listado">
                <input id="btnSiguienteDetalleObligacion" type="button" title="Siguiente" class="btn-azul btn-small" value="Siguiente">
            </div>
              
        </div>
    </body>
</html>