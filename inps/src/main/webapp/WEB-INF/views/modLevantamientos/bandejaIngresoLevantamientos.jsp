<!-- 
/**
* Resumen		
* Objeto			: bandejaIngresoLevantamientos.jsp
* Descripción		: Diseño bandejaIngresoLevantamientos
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
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
	<script type="text/javascript" src="/inps/javascript/app/levantamiento/bandejaIngresoLevantamiento.js"></script>
</head>
<body class="bar-white">
	<input type="hidden" id="idSupervisionBanIngLev" value="${idSupervision}">
	<input type="hidden" id="idExpedienteBanIngLev" value="${idExpediente}">	
	<div class="colum-bandeja colum03">
		<div class="title-colum">   
			<div class="title-cuerpo">
				INGRESAR LEVANTAMIENTO DE INFRACCIONES
			</div>
			<div class="numero-expediente">
				EXPEDIENTE: <span class="nroExpediente">${nroExpediente}</span>
			</div>
		</div>
		<div class="colum-cuerpo inbox">
			<div class="box-wrap">
				<div class="table-responsive">					
					<table id="gridObliInfraccionModLev" class="table table-bordered text-center border-bottom-none"> 
					    <thead>
					        <tr>
					        	<th style="display:none;">idDetalleSupervision</th>
					        	<th style="display:none;">flagDescLevantamiento</th>
					            <th>Descripción de infracci&oacute;n</th> 
					            <th width="100">Levantamiento</th>
					        </tr> 
					    </thead> 
					    <tbody>
					    	<c:forEach items="${detalleSupervisionList}" var="supList" varStatus="status">
						        <tr>
						        	<td style="display:none;" class="text-left blue-label">${supList.idDetalleSupervision}</td> 
						        	<td style="display:none;" class="text-left blue-label flagDescLevantamiento">${supList.flagDescLevantamiento}</td> 
						            <td class="text-justify blue-label">${supList.infraccion.descripcionInfraccion}</td>
						            <c:if test="${supList.flagDescLevantamiento == '0'}">						             
						            	<td class="text-center"><a href="modLevantamientos/levantamientoDetalle?${supList.idDetalleSupervision}?registro" class="btn-zoom btn-ingreso nav_ajx"><i></i></a></td>
						        	</c:if>
						        	<c:if test="${supList.flagDescLevantamiento == '1'}">						             
						            	<td class="text-center"><a href="modLevantamientos/levantamientoDetalle?${supList.idDetalleSupervision}?registro" class="btn-zoom btn-levantamiento nav_ajx"><i></i></a></td>
						        	</c:if>
						        </tr>
					        </c:forEach>
					        <c:if test="${empty detalleSupervisionList}">
					        	<tr>
						            <td class="text-center" colspan="3">No se encontraron resultados</td>
						        </tr>
					        </c:if>		        
					    </tbody> 
					</table>					
		        </div>	
				<div class="pull-left pdt-20">
					<a href="#" class="btn-2" id="idEnviarInfoModLev">Enviar Informaci&oacute;n</a>
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