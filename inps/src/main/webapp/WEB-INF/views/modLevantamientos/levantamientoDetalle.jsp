<!-- 
/**
* Resumen		
* Objeto			: levatamientoDetalle.jsp
* Descripci&oacute;n		: Diseño levatamientoDetalle
* Fecha de Creaci&oacute;n	: 25/10/2016
* PR de Creaci&oacute;n	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripci&oacute;n
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisi&oacute;n DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisi&oacute;n DSR-CRITICIDAD.
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisi&oacute;n DSR-CRITICIDAD.
* OSINE_SFS-791     28/10/2016      Paul Moscoso                Crear una funcionalidad que permita Agregar Medios Provatorios del Levantamiento
*/ 
 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmtj" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<script type="text/javascript" src="/inps/javascript/app/levantamiento/levantamientoDetalle.js"></script>
</head>
<body class="bar-white">
	<div class="colum-bandeja colum03">
		<input type="hidden" id="idDetalleSupervisionMovLev"  value="${idDetalleSupervision}">
		<input id="idInfraccionOblDetSupLev" type="hidden" value="${detalleSupervision.infraccion.idInfraccion}"/>
    	<input id="idOblDsrDetIdDetalleSupervisionLev" type="hidden" value="${detalleSupervision.idDetalleSupervision}"/>
    	<input id="idOblDsrDetIdDetalleLevantamiento" type="hidden" value="${detalleLevantamiento.idDetalleLevantamiento}"/>
    	<input id="idExpedienteLevDet" type="hidden" value="${idExpediente}">
    	<input id="flagDescDetLev" type="hidden" value="<c:if test="${detalleLevantamiento.descripcion != null}">1</c:if>">
		<div class="title-colum">			
			<div class="solar-gap" <c:if test="${detalleLevantamiento.descripcion == null}">style="display:none;"</c:if>>
				<a href="#" class="btn-back nav_ajx"><i></i></a> 	
			</div>					
			<div class="title-cuerpo">
				<c:if test="${modoSupervision == 'registro'}">
					INGRESAR LEVANTAMIENTO DE INFRACCIONES
				</c:if>
				<c:if test="${modoSupervision == 'consulta'}">
					CONSULTAR LEVANTAMIENTO DE INFRACCIONES
				</c:if>  
			</div>
			<div class="numero-expediente">
				EXPEDIENTE: <span class="nroExpediente">${nroExpediente}</span>
			</div>
		</div>
		<div class="colum-cuerpo inbox">
			<div class="box-wrap">
				<div class="detail-content">
					<div class="title-detail">Infracciones</div>
					<hr class="line-detail">
					<div class="subtitle-detail">Descripci&oacute;n de Infracci&oacute;n:</div>	
					<div style="text-align: justify;">${detalleSupervision.infraccion.descripcionInfraccion}</div>
					<c:if test="${!empty escenarioIncumplimientoList}"> 
						<div class="escenario">
							<div class="subtitle-detail">Escenario de Incumplimiento:</div>	
							<div class="table-responsive">
								<table class="table table-bordered text-center border-bottom-none"> 
								    <thead>
								        <tr>
								        	<th style="display:none;">idEsceIncumplimiento</th>
								        	<th style="display:none;">flagIncumplidoEnDetSup</th>
								            <th>Escenario</th> 
								            <th width="100">Comentario</th>
								        </tr> 
								    </thead> 
								    <tbody>						    
								    	<c:forEach items="${escenarioIncumplimientoList}" var="supList" varStatus="status">
								    		<c:if test="${supList.flagIncumplidoEnDetSup == '1'}">								    		
										        <tr>
										        	<td style="display:none;" class="text-left blue-label">${supList.idEsceIncumplimiento}</td> 
										        	<td style="display:none;" class="text-left blue-label">${supList.flagIncumplidoEnDetSup}</td> 
										            <td class="text-justify blue-label">${supList.idEsceIncuMaestro.descripcion}</td> 
										            <td class="text-center">
										            	<div class="btn-zoom" onclick="modDetLevan.comentarioIncumplimientoLev(${supList.idEsceIncumplimiento},${detalleSupervision.idDetalleSupervision},${supList.idInfraccion});" href="#" data-toggle="modal" data-target="#comentario-levantamiento"><i></i></a>		
											        </td>    							            	
										        </tr>
										    </c:if>	    
								        </c:forEach>
								        <c:if test="${empty escenarioIncumplimientoList}">
								        	<tr>
									            <td class="text-center" colspan="4">No se encontraron resultados</td>
									        </tr>
								        </c:if>		        
								    </tbody> 
								</table>
							</div>
						</div>
					</c:if>	
					<c:if test="${empty escenarioIncumplimientoList}"> 
						<div class="sin-escenario">
							<div class="subtitle-detail">
								Comentario infracci&oacute;n:
								<a class="btn-zoom lance-jr btnSinEscenario" href="#" data-toggle="modal" data-target="#comentario-levantamiento"><i></i></a>
							</div>
						</div>
					</c:if>		
					<div class="subtitle-detail">Medios Probatorios:</div>	
					<div class="table-responsive">
						<table class="table table-bordered text-center border-bottom-none"> 
						    <thead>
						        <tr>
						        	<th style="display:none;">idDocumentoAdjunto</th>
						        	<th>Descripci&oacute;n</th>
						        	<th>Archivo</th> 
						            <th width="100">Descargar</th>
						        </tr> 
						    </thead> 
						    <tbody>		    
						    	<c:forEach items="${docAdjuntoDetSupList}" var="supList" varStatus="status">						    									    		
							        <tr>
							        	<td style="display:none;" class="text-left blue-label">${supList.idDocumentoAdjunto}</td> 
							        	<td class="text-justify blue-label">${supList.descripcionDocumento}</td> 
							            <td class="text-justify blue-label">${supList.nombreArchivo}</td> 
							            <td class="text-center">
							            	<a class="btn-zoom btn-descarga" href="/inps/pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=${supList.idDocumentoAdjunto}" target="_blank"><i></i></a>								            	
							            </td> 								            	
							        </tr>   
						        </c:forEach>
						        <c:if test="${empty docAdjuntoDetSupList}">
						        	<tr>
							            <td class="text-center" colspan="5">No se encontraron resultados</td>
							        </tr>
						        </c:if>		        
						    </tbody> 
						</table>						
					</div>
				</div>	
				<div class="detail-content">
					<div class="title-detail">Informaci&oacute;n de levantamiento</div>
					<hr class="line-detail">	
					<div class="subtitle-detail">Levantamiento:</div>
					<div>
						<textarea id="descripDetLevantamiento"maxlength="4000" placeholder="Descripci&oacute;n del levantamiento para infracci&oacute;n" class="textarea-detail" <c:if test="${modoSupervision != 'registro'}">readonly</c:if> >${detalleLevantamiento.descripcion}</textarea>
					</div>
					<c:if test="${modoSupervision == 'registro'}">				
						<div class="row">
							<div class="col-md-6 row-col-pdg"></div>
							<div class="col-md-6 row-col-pdg text-right">
								<button type="button" id="btnCerrarLevantamiento" class="btn btn-default" data-dismiss="modal">Cancelar</button>
								<button type="button" id="btnGuardarLevantamiento" class="btn-primary btn">Guardar</button>
							</div>	
						</div>	
					</c:if>
					<div class="row">
						<div class="col-md-6 row-col-pdg">
							<div class="subtitle-detail">Medios Probatorios:</div>
						</div>
						<c:if test="${modoSupervision == 'registro'}">	
							<div class="col-md-6 row-col-pdg text-right">
                           		<button id="btnAgregarMPLev" type="button" class="btn btn-default" data-toggle="modal" data-target="#agregar-medio" <c:if test="${detalleLevantamiento.descripcion == null}">disabled="disabled"</c:if> >+ Agregar Archivo</button>
							</div>
						</c:if>				
					</div>					
					<div class="table-responsive">
						<table id="gridMPDetLev" class="table table-bordered text-center border-bottom-none"> 
						    <thead>
						        <tr>
						        	<th style="display:none;">idDocumentoAdjunto</th>
						        	<th>Descripci&oacute;n</th>
						        	<th>Archivo</th> 
						            <th width="100">Descargar</th>
						        </tr> 
						    </thead> 
						    <tbody>		    
						    	<c:forEach items="${docAdjuntoDetLevList}" var="supList" varStatus="status">						    									    		
							        <tr>
							        	<td style="display:none;" class="text-left blue-label">${supList.idDocumentoAdjunto}</td> 
							        	<td class="text-justify blue-label">${supList.descripcionDocumento}</td> 
							            <td class="text-justify blue-label">${supList.nombreArchivo}</td> 
							            <td class="text-center">
							            	<a class="btn-zoom btn-descarga" href="/inps/pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=${supList.idDocumentoAdjunto}" target="_blank"><i></i></a>								            	
							            </td> 								            	
							        </tr>   
						        </c:forEach>
						        <c:if test="${empty docAdjuntoDetLevList}">
						        	<tr>
							            <td class="text-center" colspan="5">No se encontraron resultados</td>
							        </tr>
						        </c:if>		        
						    </tbody> 
						</table>					
					</div>
				</div>
			</div>		
		</div>
	</div>
	<div class="modal fade" id="comentario-levantamiento" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="title-detail" id="myModalLabel">Comentario</h4>
				</div>
				<div class="modal-body">
					<div class="comentario-levantamiento-escenario">
						<div class="subtitle-detail">Escenario de Incumplimiento:</div>
						<div><textarea class="textarea-detail" id="descEscIncLev"></textarea></div>
					</div>	
					<div class="comentario-levantamiento-sin-escenario">
						<div class="subtitle-detail">Infracci&oacute;n:</div>
						<div><textarea class="textarea-detail" id="descSinEscInfLev"></textarea></div>
					</div>	
					<div class="subtitle-detail">Comentario:</div>
					<div class="table-responsive">
						<table id="gridComentIncuLev" class="table table-bordered text-center border-bottom-none"> 
						    <thead>
						        <tr>						        
						        	<th style="display:none;">flagComentDetSupEnEsceIncdo</th>
						        	<th style="display:none;">idComentarioDetSupervision</th>
						        	<th style="display:none;">idComentarioIncumplimiento</th>						        	
						        	<th>Comentario</th>
						        </tr> 
						    </thead> 
						    <tbody></tbody> 
						</table>
					</div>
				</div>	
				<div class="modal-footer text-center">                    
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				</div>
			</div>
		</div>
	</div>		
    <div id="agregar-medio" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
	     <div class="modal-dialog">
	         <div class="modal-content">
	             <div class="modal-header">
	                     <button class="close" type="button" data-dismiss="modal">
	                     <span aria-hidden="true">×</span>
	                     <span class="sr-only">Close</span>
	                     </button>
	                     <h4 id="myModalLabel" class="title-detail">Agregar Medio Probatorio</h4>
	             </div>
	             <div class="modal-body">
	                 <form class="detail-content" id="formMedioAprobatorioDsr" action="/inps/pages/archivo/subirMedioProbatorioDetalleSuperDsr" method="post" enctype="multipart/form-data" encoding="multipart/form-data" > <!--onsubmit="setTimeout(function () { window.location.reload(); }, 0)" -->
	                     <div class="form-group">
	                            <label for="txtDescripcionMPDsr">Descripción (*) :</label>
	                            <input id="txtDescripcionMPDsr" name="descripcionDocumento" class="form-control" placeholder="Descripción">
	                     </div>
	                     <div class="form-group">
	                            <label for="exampleInputPassword1">Archivo (*) :</label>
	                           	<div class="form-inline">
	                               	<label for="exampleInputFile">
	                                   	<input type="hidden" id="idDetalleSupervisionSuperL" name="idDetalleLevantamiento" value="${detalleLevantamiento.idDetalleLevantamiento}">
	                                     	<input id="fileArchivoSuperDsr" type="file" name="archivos[0]" >
	                           		</label>
	                    		</div>
	                     </div>
	                 </form>
	                    <div class="detail-content">
	                           <p>
	                           (*) Campos Obligatorios:
	                           <br>
	                           Los tipos de archivos permitidos son: PDF, JPEG, AVI y el tamaño máximo para cada uno debe ser 2MB
	                           </p>
	                   </div>
	                   <div class="modal-footer text-center">
	                           <button class="btn btn-default" type="button" data-dismiss="modal">Cancelar</button>
	                           <button id="btnAgregarMedioProbatorioLevDsr" class="btn-primary btn" type="button" >Guardar</button>
	                   </div>
	             </div>
	         </div>
	     </div>
    </div>
    <script type="text/javascript">	
      $(function(){			
              columResize();        
              columResizeInbox();
              heightMenuOpen();			
              $(".inbox").mCustomScrollbar({
                      theme:"dark-3",
                      scrollbarPosition:"outside"
              });	
              $(".scroll-tab").mCustomScrollbar({
                      theme:"dark-3",
                      scrollbarPosition:"outside"
              });	
              $('input, textarea').placeholder();              
      });         
    </script>
</body>
