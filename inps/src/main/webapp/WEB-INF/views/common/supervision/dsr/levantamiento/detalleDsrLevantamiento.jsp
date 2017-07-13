<%--
* Resumen		
* Objeto			:detalleDrsLevantamiento.jsp
* Descripción		: detalleDrsLevantamiento
* Fecha de Creación	: 07/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791     07/10/2016       Mario Dioses Fernandez           Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
    	<script type="text/javascript" src='<c:url value="/javascript/app/common/supervision/dsr/levantamiento/detalleDrsLevantamiento.js" />' charset="utf-8"></script>
    	<style type="text/css">
            .top{margin-top:10px;}
            .subpanelContent{margin:0 auto; width:95%;}
        </style>
    </head>
   	<body>
    	<div class="form">
    		<input id="idInfraccionOblDetSupLev" type="hidden" value="${detalleSupervision.infraccion.idInfraccion}"  />
    		<input id="idOblDsrDetIdDetalleSupervisionLev" type="hidden" value="${detalleSupervision.idDetalleSupervision}"  />
    		<input id="idOblDsrDetIdDetalleLevantamiento" type="hidden" value="${detalleLevantamiento.idDetalleLevantamiento}"  />
    		<div class="pui-subpanel ui-widget-content" style="width:750px;">
                  <div class="pui-subpanel-subtitlebar">
                      	<span class="ui-panel-title">Infracci&oacute;n</span>
                  </div>
                  <div class="pui-subpanel-content">
                  		<div class="subpanelContent">
		                	<div class="fila top">
		                    	<div class="lbl-medium vam"><label><b>Descripci&oacute;n infracci&oacute;n:</b></label></div>
		                    </div>
                      		<div class="fila top">
                        		<div class="vam">
                            		<textarea id="descripcionInfraccionLev" style="width:680px" disabled="disabled" readonly="readonly" rows="4" maxlength="4000" >${detalleSupervision.infraccion.descripcionInfraccion}</textarea>                		                         
                        		</div>
                        	</div>                        	
                        	<div id="divEscIncumplimientoLev">
	                        	<div class="fila top">
			                    	<div class="lbl-large vam"><label><b>Escenario del incumplimiento:</b></label></div>
			                    </div>
			                    <div class="fila top">
			                    	<div id="gridContenedorObligacionDtlEscenarioLev" class="content-grilla"></div>
			                    	<div id="dialogComentarioIncumplimientoLev" class="dialog" style="display:none;"></div>	
			                    </div>
			                </div>			                
			                <div id="divSinEscIncumplimientoLev">
		                		<div class="filaForm top">
	                            	<div class="vam"><label style="margin-left:4px;"><b>Comentario de infracci&oacute;n </b></label></div>
	                            	<div class="vam">
	                                	<div class="button_2 btn_small" title="Buscar" id="btnAbrirComInctoSinEscenarioLev">
	                                    	<span class="ui-icon ui-icon-search"></span>
	                                	</div>                                
	                            	</div>
	                            </div>
			                </div>		                    
		                    <div class="fila top">
		                    	<div class="lbl-medium vam"><label><b>Medios probatorios:</b></label></div>
		                    </div>
		                    <div class="fila top">
		                    	<div id="gridContenedorArchivosMPDsrLev" class="content-grilla"></div>
		                    </div>
                    	</div>
                  </div>
            </div>
            <div class="pui-subpanel ui-widget-content" style="width:750px;">
                  <div class="pui-subpanel-subtitlebar">
                      	<span class="ui-panel-title">Informaci&oacute;n de levantamiento</span>
                  </div>
                  <div class="pui-subpanel-content">
                  		<div class="subpanelContent">
		                	<div class="fila top">
		                    	<div class="lbl-large vam"><label><b>Levantamiento:</b></label></div>
		                    </div>
                      		<div class="fila top">
                        		<div class="vam">
                            		<textarea id="descripcionLevantamiento" style="width:680px" disabled="disabled" readonly="readonly" rows="4" maxlength="4000">${detalleLevantamiento.descripcion}</textarea>                		                         
                        		</div>
                        	</div>                        	
                        	<div class="fila top">
                        		<div class="pui-subpanel ui-widget-content" style="width:700px;">
					                  <div class="pui-subpanel-subtitlebar">
					                      	<span class="ui-panel-title">Medios Probatorios</span>
					                  </div>
					                  <div class="pui-subpanel-content">
					                  		<div class="subpanelContent">
					                  			<div id="gridContenedorArchivosMPDsrDetalleLev" class="content-grilla"></div>
					                  		</div>
					                  </div>
					             </div>
                        	</div>                        	
                    	</div>
                  </div>
            </div>
        </div>
        <div class="botones top">
        	<input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
