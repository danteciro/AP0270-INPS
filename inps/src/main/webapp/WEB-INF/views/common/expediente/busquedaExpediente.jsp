<%--
* Resumen		
* Objeto		: busquedaExpediente.jsp
* Descripción		: busquedaExpediente
* Fecha de Creación	: 05/11/2015
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                 |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  02/05/2016   |   Hernan Torres Saenz    |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación  y notificación/archivado
*                |               |                          |
*                |               |                          |
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/expediente/busquedaExpediente.js" />' charset="utf-8"></script>
    </head>
    <body>
    	<input type="hidden" id="idGridBusqExpe" value="${grid}">
    	<div class="form">
            <form id="frmBusqExpe">
                <div id="divMensajeValidaFrmBusqExpe" class="errorMensaje" style="display: none"></div>
                <div>
                    <!-- OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio -->
                    <div id="divNumeroRH" style="display:none;">
                    	<div class="filaForm">
	                        <div class="lbl-small vam"><label for="txtNumeroRH">Número RH:</label></div>
	                        <div>
	                        	<input id="txtNumeroRH" type="text" maxlength="20" class="ipt-medium" />
	                        </div>
	                    </div>
                    </div>
                    <!-- OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin -->
                    <div class="filaForm">
                            <div class="lbl-small"><label for="txtCodigoOsiBusqExpe">Código OSINERGMIN:</label></div>
                            <div>
                                <input id="txtCodigoOsiBusqExpe" type="text" maxlength="12" class="ipt-medium" />
                             </div>
                    </div>
                    <div class="filaForm">
                        <div class="lbl-small vam"><label for="txtNumeExpeBusqExpe">Número Expediente:</label></div>
                        <div class="vam">
                            <input id="txtNumeExpeBusqExpe" type="text" maxlength="25" class="ipt-medium" />
                         </div>
                    </div>
                    <div class="filaForm" id="divNumeOSBusqExpe">
                        <div class="lbl-small"><label for="txtNumeOSBusqExpe">Número OS:</label></div>
                        <div>
                            <input id="txtNumeOSBusqExpe" type="text" maxlength="25" class="ipt-medium" />
                         </div>
                    </div>   
                    <div class="filaForm" id="divFechaOrdenServicio">
                        <div class="lbl-small"><label  for="txtFechaInicioOS">Fecha inicio:</label></div>
                        <div>
                            <input id="txtFechaInicioOS" type="text" maxlength="10" class="ipt-medium" />
                         </div>
                         <div class="lbl-small" style="width:80px !Important"><label  for="txtFechaFinalOS">Fecha fin:</label></div>
                        <div>
                            <input id="txtFechaFinalOS" type="text" maxlength="10" class="ipt-medium" />
                         </div>
                    </div>  
                    <div id="divEstadoOS">
	                    <div class="filaForm">
	                        <div class="lbl-small vam"><label for="slctEstadoOS">Estado Orden Servicio:</label></div>
	                        <div class="vam">
	                        	<select id="slctEstadoOS" class="slct-medium">
	                            </select>
	                        </div>
	                    </div>
	                </div>
                    <div id="divEstadoProceso" style="display:none;"> <!-- mdiosesf -->
	                    <div class="filaForm">
	                        <div class="lbl-small vam"><label for="slctEstadoProceso">Estado:</label></div>
	                        <div class="vam">
	                        	<select id="slctEstadoProceso" class="slct-medium">
	                            </select>
	                        </div>
	                    </div>
	                </div> <!-- mdiosesf -->
                    <div class="filaForm">
                    	<div class="lbl-small"><label for="txtRazoSociBusqExpe">Razón Social:</label></div>
                        	<div>
                            	<input id="txtRazoSociBusqExpe" type="text" maxlength="25" class="ipt-medium-small-medium" />
                           </div>
                    </div>
                    <!-- OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio -->
                    <div id="divUbigeo" style="display:none;">
                    	<div class="filaForm">
		                    <div class="lbl-small vam"><label for="slctDepartamento">Departamento:</label></div>
	                        <div class="vam">
	                            <select id="slctDepartamento" class="slct-medium" name="distrito.idDepartamento" validate="[O]">
	                                
	                            </select>
	                        </div>
	                    </div>
	                    <div class="filaForm">
	                        <div class="lbl-small vam"><label for="slctProvincia">Provincia:</label></div>
	                        <div class="vam">
	                            <select id="slctProvincia" class="slct-medium" name="distrito.idProvincia" validate="[O]">
	                            </select>
	                        </div>
	                    </div>  
	                    <div class="filaForm">  
	                        <div class="lbl-small vam"><label for="slctDistrito">Distrito:</label></div>
	                        <div class="vam">
	                            <select id="slctDistrito"class="slct-medium" name="distrito.idDistrito" validate="[O]">
	                                <option value="">--Seleccione--</option>
	                            </select>
	                        </div>
	                    </div>
	                </div>
                    <!-- OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin -->
                    <div id="divEmprSupeBusqExpe">
	                    <div class="filaForm">
	                        <div class="lbl-small vam"><label for="slctTipoEmprSupeBusqExpe">Tipo Empresa:</label></div>
	                        <div class="vam">
	                        	<select id="slctEntidadOSBusqExpe" style="display:none;">                            
                                </select>
	                            <select id="slctTipoEmprSupeBusqExpe" class="slct-medium">                           
	                            </select>
	                        </div>
	                    </div>
	                    <div class="filaForm">
	                    	<div class="lbl-small vam"><label for="slctEmprSupeOSBusqExpe">Empresa Supervisora:</label></div>
                            <div class="vam">
                                <select id="slctEmprSupeOSBusqExpe" class="slct-medium-small-medium">
                                    <option value="">--Seleccione--</option>
                                </select>
                            </div>
	                    </div>
                    
                    </div>
                    <!-- OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio -->
                    <div id="divPeticion" style="display:none;">
	                    <div class="filaForm">
	                        <div class="lbl-small vam"><label for="slctPeticion">Petición:</label></div>
	                        <div class="vam">
	                        	<select id="slctPeticion" class="slct-medium">
	                            </select>
	                        </div>
	                    </div>
	                </div>
	                <div id="divEstadoCumplimiento">
	                    <div class="filaForm">
	                        <div class="lbl-small vam"><label for="slctEstadoCumplimiento">Estado Supervisi&oacuten:</label></div>
	                        <div class="vam">
	                        	<select class="slct-medium" id="slctEstadoCumplimiento">
                                	<option value=""  >--Seleccione--</option>
                                    <option value="1" >CON INCUMPLIMIENTOS</option>
                                    <option value="0" >SIN INCUMPLIMIENTOS</option>                                              
                               </select>
	                        </div>
	                    </div>
	                </div>
                    <!-- OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin -->
                </div>
            </form>
        </div>
        <div class="botones">
            <input id="btnBusqExpe" type="button" class="btn-azul btn-small" value="Buscar">
            <input id="btnCerrarBusqExpe" type="button" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>