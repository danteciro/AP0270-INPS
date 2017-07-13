<%--
* Resumen		
* Objeto		: consultaMensajeriaExpediente.jsp
* Descripción		: consultaMensajeriaExpediente
* Fecha de Creación	: 27/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                 |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  27/05/2016   |   Luis García Reyna      |     Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
*                |               |                          |
*                |               |                          |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/expediente/consultaMensajeriaExpediente.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
        	<input type="hidden" id="tipoAccionOS" value="${tipo}">
            
            <div class="filaForm tac">
	            <div class="fila">
	                <div id="gridContenedorFilesMensajeriaDocumentos" class="content-grilla"></div>
	                <div id="divContextMenuFilesMensajeriaDocumentos"></div>
	            </div>
            </div>
                       
            <div id="tabsConsMensajeriaExpediente" style="position:relative;"  class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="width:920px;">
               <ul>
                    <li><a href="#tabDatosEnvio">Datos de Envío</a></li>
                    <li><a href="#tabDatosCargo">Datos de Cargo</a></li>
                    <li><a href="#tabDocumentos">Documentos</a></li>
                </ul> 
                <div id="tabDatosEnvio">
                    <div class="pui-subpanel ui-widget-content">
                        <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title">Datos del Documento</span>
                        </div>
                        <div class="pui-subpanel-content">
                            <div class="filaForm">
                                <div class="lbl-small"><label>Tipo Documento:</label></div>
                                <div class="vam">
                                    <input  id="tipoDocumento" type="text" class="ipt-medium" value="${tipoDocumento}" disabled />
                                </div>
                                <div class="lbl-small"><label>Nro Documento:</label></div>
                                <div class="vam">
                                    <input id="numeroDocumento" type="text" class="ipt-medium-medium-medium" value="${numeroDocumento}" disabled />
                                </div>                                
                            </div>    
                            <div class="filaForm">
                                <div class="lbl-small"><label>Fecha de Envío:</label></div>
                                <div class="vam">
                                    <input id="fechaEnvio" type="text" class="ipt-medium" value="${fechaEnvio}" disabled />
                                </div>                                
                                <div class="lbl-small"><label>Destinatario:</label></div>
                                <div>
                                    <input id="destinatario" type="text" class="ipt-medium-medium-medium" value="${destinatario}" disabled />
                                </div>                                 
                            </div>
                            <div class="filaForm"> 
                                <div class="lbl-small"><label>Ubigeo:</label></div>
                                <div>
                                    <input id="ubigeo" type="text" class="ipt-medium-small-medium-small-small-small" value="${ubigeo}" disabled />
                                </div>
                            </div>
                            <div class="filaForm">     
                                <div class="lbl-small"><label>Dirección:</label></div>
                                <div>
                                    <input id="direccion" type="text" class="ipt-medium-small-medium-small-small-small" value="${direccion}" disabled />
                                </div>                                
                            </div>   

                        </div>
                    </div>                            
                </div>
                <div id="tabDatosCargo">
                    <div class="pui-subpanel ui-widget-content">
                        <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title"></span>
                        </div>
                        <div class="pui-subpanel-content">
                        	<div id="idMensaje" class="filaForm" style="display: none">                               
                                <div class="lbl-large"><label>Registro en Proceso</label></div>
                            </div>
                            <div id="idEstadoMensajeria" class="filaForm" style="display: none">
                                <div class="lbl-medium"><label>Estado:</label></div>
                                <div class="vam">
                                    <input id="idEstado" type="text" class="ipt-medium"  disabled />
                                </div>
                            </div>
                            <!-- Devolución style="display: none"-->
                            <div id="idDevuelto" class="filaForm" style="display: none">
                            	<div class="filaForm">
		                           	<div class="lbl-medium" ><label>Motivo Devolución:</label></div>
		                               <div class="vam">
		                                   <input id="idMotivoDevolucion" type="text" class="ipt-medium"  disabled />
		                               </div>
                                </div> 
                            </div>
                            
                            <!-- NotificadoDevuelto style="display: none"-->
                            <div id="idNotificadoDevuelto" style="display: none">
                            	<div class="filaForm">
                            		<div class="lbl-medium"><label>Fecha Devolución Cargo:</label></div>
	                                <div>
	                                    <input id="idFechaDevolucionCargo" type="text" class="ipt-medium"  disabled />
	                                </div>
	                                <div class="lbl-medium"><label>Número de Remito: </label></div>
	                                <div>
	                                    <input id="idNumeroRemito" type="text" class="ipt-medium"  disabled />
	                                </div>
	                            </div>
	                            <div class="filaForm">
	                                <div class="lbl-medium"><label>Primera Visita:</label></div>
	                                <div>
	                                    <input id="idPrimeraVisita" type="text" class="ipt-medium"  disabled />
	                                </div>
	                            </div>
                            </div>
                            
                            <!-- Robado/Extraviado  style="display: none"-->
                           	<div id="idRobado" class="filaForm" style="display: none">
                           		<div class="filaForm">
	                            	<div class="lbl-medium"><label>Fecha Denuncia Policial:</label></div>
	                                <div class="vam" >
	                                    <input id="idFechaDenunciaPolicial" type="text" class="ipt-medium"  disabled />
	                                </div>
                                </div>
                            </div>
                            
                            <!-- Notificación style="display: none"-->
                            <div id="idNotificado" style="display: none">
	                            <div class="filaForm">
	                            	<div class="lbl-medium"><label>Segunda Visita:</label></div>
	                                <div>
	                                    <input id="idSegundaVisita" type="text" class="ipt-medium"  disabled />
	                                </div>
	                            	<div class="lbl-medium"><label>Notificacion por Acta:</label></div>
	                                <div class="vam">
	                                    <input id="idNotificacionPorActa" type="text" class="ipt-medium"  disabled />
	                                </div>
	                            </div>
	                            <div class="filaForm">
	                                <div class="lbl-medium"><label>Motivo Notificacion con Acta:</label></div>
	                                <div class="vam">
	                                    <input id="idMotivoNotificacionConActa" type="text" class="ipt-medium"  disabled />
	                                </div>
	                                <div class="lbl-medium"><label>Fecha Entrega Destinatario:</label></div>
	                                <div>
	                                    <input id="idFechaEntregaDestinatario" type="text" class="ipt-medium"  disabled />
	                                </div>  
	                            </div>    
	                            <div class="filaForm">
	                                <div class="lbl-medium"><label>Hora Entrega Destinatario:</label></div>
	                                <div>
	                                    <input id="idHoraEntregaDestinatario" type="text" class="ipt-medium"  disabled />
	                                </div>   
	                            </div>
	                        </div>
	                        <div id="idFechaDevolucionAreaMensajeria" class="filaForm" style="display: none">
		                        <div class="lbl-medium"><label>Fecha Devolución Área: </label></div>
                                <div>
                                    <input id="idFechaDevolucionArea" type="text" class="ipt-medium"  disabled />
                                </div>
	                         </div>
                        </div>
                    </div>
                    <!-- style="display: none" -->
                    <div id="idReceptor" class="pui-subpanel ui-widget-content" style="display: none">
                        <div class="pui-subpanel-subtitlebar">
                            <span class="ui-panel-title">Datos del Receptor</span>
                        </div>
                        <div class="pui-subpanel-content">
                            <div class="filaForm">                               
                                <div class="lbl-medium"><label>¿Recepcionado por Otro Destinatario?</label></div>
                                <div class="vam">
                                    <input id="idRecepcionadoPorOtro" type="text" class="ipt-medium" disabled />
                                </div> 
                                <div class="lbl-medium"><label>Nombre del Receptor:</label></div>
                                <div>
                                    <input id="idNombreReceptor" type="text" class="ipt-medium"  disabled />
                                </div>  
                            </div>    
                            <div class="filaForm">
                                <div class="lbl-medium"><label>Apellido Paterno:</label></div>
                                <div>
                                    <input id="idApellidoPaterno" type="text" class="ipt-medium"  disabled />
                                </div>
                                <div class="lbl-medium"><label>Apellido Materno:</label></div>
                                <div>
                                    <input id="idApellidoMaterno" type="text" class="ipt-medium"  disabled />
                                </div>                                                                                      
                            </div> 
                            <div class="filaForm">
                                <div class="lbl-medium"><label>Documento de Identidad:</label></div>
                                <div>
                                    <input id="idDocumentoIdentidad" type="text" class="ipt-medium"  disabled />
                                </div>    
                                <div class="lbl-medium"><label>Relacion con Destinatario:</label></div>
                                <div>
                                    <input id="idRelacionConDestinatario" type="text" class="ipt-medium"  disabled />
                                </div>
                            </div>     
                        </div>
                    </div>
                </div>
                <div id="tabDocumentos">
                    <div class="vam" style="margin-top: 20px;">
                        <div id="gridContenedorFilesOrdenServicioMensajeria" class="content-grilla"></div>                                  
                    </div>
                </div>
            </div>
        </div>                          
        <div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>                       
    </body>
</html>