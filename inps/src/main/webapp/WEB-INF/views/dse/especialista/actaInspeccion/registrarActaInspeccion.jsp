<%--
* Resumen		
* Objeto			: registrarActaInspeccion.jsp
* Descripción		: Registrar actas y reles de la inspeccion.
* Fecha de Creación	: 14/09/2016
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres Saenz.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                 |     Descripción
* =====================================================================================================================================================================
*
--%> 

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/actaInspeccion/registrarActaInspeccion.js"/>' charset="utf-8"></script>
    </head>
    <body>
    	
        <div class="form">
	        <input type="hidden" id="idEmpresaRA" value="${idEmpresaRA}">
	    	<input type="hidden" id="idAnioRA" value="${idAnioRA}">
	    	<input type="hidden" id="idZonaRA" value="${idZonaRA}">
	    	<input type="hidden" id="idFechaActual" name="idFechaActual" value="${fechaActual}">
	    	<input type="hidden" id="idSupeCampRechCargaRA" name="idSupeCampRechCargaRA" value="${supeCamp.idSupeCampRechCarga}">
	    	
	    	<input type="hidden" id="comboDepartamento" value="${comboDepartamento}">
	    	<input type="hidden" id="comboProvincia" value="${comboProvincia}">
	    	<input type="hidden" id="comboDistrito" value="${comboDistrito}">
	    	
	            
			
				<div id="tabsActaInspeccion" style="position:relative;"  class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="width:920px;">
			        
			        <ul>
			             <li><a href="#tabInformacionInicio">Información Inicial</a></li>
			             <li><a href="#tabReles">Reles</a></li>
			             <li><a href="#tabObservaciones">Observaciones</a></li>
			        </ul> 
					<div id="tabInformacionInicio">
		                <form id="formMantRegistroActa" action="/siguoweb/pages/tanque/guardarCertificado" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
		                	<div id="divMensajeValidaFormRegistrarActa" class="errorMensaje" style="display: none"></div>
			                <div class="pui-subpanel ui-widget-content">                	
			                    <div class="pui-subpanel-content">
						            <div class="filaForm">
						                <div class="lbl-small-small vam" ><label>Fecha Inicio(*):</label></div>
						                <div class="vam">
						                    <input id="idFechaInicioRA" name="idFechaInicioRA" type="text" class="ipt-small" value="${supeCamp.fechaInicio}" validate="[O]"/>
						                </div>
						                <div class="lbl-espacio"></div>
						                <div class="lbl-small-small vam" ><label>Hora Inicio(*):</label></div>
						                <div class="vam">
						                	<select id="slctHoraInicio" name="slctHoraInicio" style="width:65px;" >
			                                    <c:forEach items="${listaHoras}" var="t">
			                                        <option value='${t.codigo}' <c:if test="${supeCamp.horaInicio==t.codigo}">selected</c:if> >${t.descripcion}</option>
			                                    </c:forEach>
						                    </select>
						                </div>
						                <div><label>:</label></div>
						                <div class="vam">
						                	<select id="slctMinutoInicio" name="slctMinutoInicio" style="width:65px;" >
			                                    <c:forEach items="${listaMinutos}" var="t">
			                                        <option value='${t.codigo}' <c:if test="${supeCamp.minutoInicio==t.codigo}">selected</c:if> >${t.descripcion}</option>
			                                    </c:forEach>
						                    </select>
						                </div>
						            </div>
						            
						            <div class="filaForm">
						                <div class="lbl-small-small vam" ><label>Fecha Fin(*):</label></div>
						                <div class="vam">
						                    <input id="idFechaFinRA" name="idFechaFinRA" type="text" class="ipt-small" value="${supeCamp.fechaFin}" validate="[O]"/>
						                </div>
						                <div class="lbl-espacio"></div>
						                <div class="lbl-small-small vam" ><label>Hora Fin(*):</label></div>
						                <div class="vam">
						                	<select id="slctHoraFin" name="slctHoraFin" style="width:65px;" >
						                		<c:forEach items="${listaHoras}" var="t">
			                                        <option value='${t.codigo}' <c:if test="${supeCamp.horaFin==t.codigo}">selected</c:if> >${t.descripcion}</option>
			                                    </c:forEach>
						                    </select>
						                </div>
						                <div><label>:</label></div>
						                <div class="vam">
						                	<select id="slctMinutoFin" name="slctMinutoFin" style="width:65px;" >
						                		<c:forEach items="${listaMinutos}" var="t">
			                                        <option value='${t.codigo}' <c:if test="${supeCamp.minutoFin==t.codigo}">selected</c:if> >${t.descripcion}</option>
			                                    </c:forEach>
						                    </select>
						                </div>
						            </div>
						            
						            <div class="filaForm">
						                <div class="lbl-medium vam"><label>Supervisor Empresa(*):</label></div>
						                <div class="vam">
						                    <input id="idSupervisorEmpresa" name="idSupervisorEmpresa" maxlength="100" value="${supeCamp.nombreSupervisorEmpresa}" type="text" class="ipt-large" validate="[O]"/>
						                </div>
						            </div>
						            
						            <div class="filaForm">
						                <div class="lbl-medium vam"><label>Cargo Supervisor(*):</label></div>
						                <div class="vam">
						                    <input id="idCargoSupervisor" name="idCargoSupervisor" maxlength="100" value="${supeCamp.cargoSupervisorEmpresa}" type="text" class="ipt-large" validate="[O]"/>
						                </div>
						            </div>
						            
						            <div class="filaForm">
						                <div class="lbl-medium vam"><label>Supervisor Osinergmin(*):</label></div>
						                <div class="vam">
						                    <input id="idSupervisorOsinergmin" name="idSupervisorOsinergmin" maxlength="100" value="${supeCamp.nombreSupervisorOsinergmin}" type="text" class="ipt-large" validate="[O]"/>
						                </div>
						            </div>
						            
						            <div class="filaForm">
						                <div class="lbl-medium vam"><label>Cargo Supervisor Osinergmin(*):</label></div>
						                <div class="vam">
						                    <input id="idCargoSupervisorOsinergmin" name="idCargoSupervisorOsinergmin" maxlength="100" value="${supeCamp.cargoSupervisorOsinergmin}" type="text" class="ipt-large" validate="[O]"/>
						                </div>
						            </div>
						            
						            <div class="filaForm">
										<div class="lbl-medium vam"><label for="slctDepartamentoRA">Departamento(*):</label></div>
										   <div class="vam">
										       <select id="slctDepartamentoRA" class="slct-medium" name="slctDepartamentoRA" validate="[O]">
										       </select>
										   </div>
						            </div>
						             
						            <div class="filaForm">
						                <div class="lbl-medium vam"><label for="slctProvinciaRA">Provincia(*):</label></div>
						                <div class="vam">
						                    <select id="slctProvinciaRA" class="slct-medium" name="slctProvinciaRA" validate="[O]">
						                    </select>
						                </div>
						            </div>  
						             
						            <div class="filaForm">
						                <div class="lbl-medium vam"><label for="slctDistritoRA">Distrito(*):</label></div>
						                <div class="vam">
						                    <select id="slctDistritoRA"class="slct-medium" name="slctDistritoRA" validate="[O]">
						                    </select>
						                </div>
						            </div>
						            <div class="txt-obligatorio">(*) Campos obligatorios</div>
							    </div>
				    		</div>  
			    		</form>    
		        	</div>
	        	<div id="tabReles">
			        <div class="pui-subpanel ui-widget-content">
			            <div class="pui-subpanel-content">
			            	<div class="filaForm">
				                <div style="width: 120px;"><label for="slctSubEstacionRA">Sub Estación(S.E.):</label></div>
				                <div class="vam">
				                    <select id="slctSubEstacionRA" class="slct-medium" name="slctSubEstacion">
				                    </select>
				                </div>
				            </div>
				            <div style="margin-top: 20px;"></div>
				            <div id="idListaReles">
				            </div>
							<div id="idPaginacionReles">
							</div>
			            </div>
			        </div>
		       	</div>
		       	<div id="tabObservaciones">
		           	<div class="pui-subpanel ui-widget-content">
		               	<div class="pui-subpanel-content">
		               		<div class="lbl-large" ><label>Observaciones de la inspección</label></div>
		               		
		               	 	<div style="text-align: left;">
				               	<div class="filaForm">
			                          <div style="text-align: left; width:125px;"><label>Ajustes de los Reles:</label></div>
			                          <div class="vam">
			                              <textarea  id="idAjusteReles" name ="idAjusteReles" maxlength="4000" style="width: 450px;">${supeCamp.ajusteRele}</textarea>
			                          </div>
				                </div>
			                    <div class="filaForm">
									<div style="text-align: left; width:125px;"><label>Habilitación de los disparos de los reles:</label></div>
			                        <div class="vam">
										<textarea  id="idDisparosReles" name ="idDisparosReles" maxlength="4000" style="width: 450px;">${supeCamp.habilitacionRele}</textarea>
			                        </div>
			                    </div>
		                     	<div class="filaForm">	
			                      	<div style="text-align: left; width:125px;"><label>Protocolo de pruebas de los ajustes de los reles:</label></div>
			                        <div class="vam">
										<textarea  id="idPruebasReles" name ="idPruebasReles" maxlength="4000" style="width: 450px;">${supeCamp.protocoloRele}</textarea>
			                        </div>
		                     	</div>
			                    <div class="filaForm">
			                    	<div style="text-align: left; width:125px;"><label>Reporte de eventos de los réles:</label></div>
			                        <div class="vam">
			                        	<textarea  id="idEventosReles" name ="idEventosReles" maxlength="4000" style="width: 450px;">${supeCamp.reporteRele}</textarea>
			                        </div>
			                    </div>
		                     	<div class="filaForm">	
		                      		<div style="text-align: left; width:125px;"><label>Otras Observaciones:</label></div>
		                          	<div class="vam">
		                            	<textarea  id="idObservacionesReles" name ="idObservacionesReles" maxlength="4000" style="width: 450px;">${supeCamp.otrasObservaciones}</textarea>
		                          	</div>
		                     	</div>
		                    </div>
		                    <div class="filaForm">	
		                      <div class="lbl-medium"><label>Notas de la Empresa:</label></div>
		                    </div>
	                     	<div class="filaForm" style="text-align: center;">
	                     		<div class="vam">
	                              <textarea  id="idNotasEmpresa" name ="idNotasEmpresa" style="width: 450px;">${supeCamp.notasEmpresa}</textarea>
	                          </div>
	                     	</div>
	                     
	                     	
		               </div>
		               
		           </div>
		           
		        </div>
		        <div class="botones">
            		<input type="button" id="btnGuardarActaRegistro" class="btn-azul btn-small" value="Guardar">
            		<input type="button" id="btnTerminarActaRegistro" class="btn-azul btn-small" value="Terminar">
            		<input type="button" id="btnCerrarActaRegistro" title="Cancelar" class="btn-azul btn-small" value="Cancelar">
		        </div>
	        </div>
        </div>
    </body>
</html>
