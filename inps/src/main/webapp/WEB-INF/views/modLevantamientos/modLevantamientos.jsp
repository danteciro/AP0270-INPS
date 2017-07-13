<!-- 
/**
* Resumen		
* Objeto			: modLevantamientos.jsp
* Descripci&oacute;n		: Diseño modLevantamientos
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
	<title>.:: PVO - INFRACCIONES ::.</title>
	<link rel="stylesheet" href="/inps/stylesheets/contentLev/css/style.css">
	<link rel="stylesheet" href="/inps/stylesheets/contentLev/bootstrap/css/bootstrap.css">
	<script type="text/javascript" src="/inps/javascript/third-party/modernizr.custom.js"></script>
	<script type="text/javascript" src="/inps/javascript/third-party/respond.min.js"></script>
	<script type="text/javascript" src="/inps/javascript/third-party/primeui/jquery-1.11.1.js"></script>
	<script type="text/javascript" src="/inps/javascript/third-party/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="/inps/javascript/third-party/jquery.mCustomScrollbar.js"></script>
	<script type="text/javascript" src="/inps/javascript/third-party/placeholder.js"></script>			
	<script type="text/javascript" src="/inps/javascript/app/levantamiento/modLevantamientos.js"></script>	
    <script type="text/javascript" src="/inps/javascript/third-party/jqueryForm/jquery.form.js"></script>
</head>
<body class="bar-white">
	<input type="hidden" id="urlSeccion" value="">
	<input type="hidden" id="urlDetSeccion" value="">
	<input type="hidden" id="codOsinergminSeccion" value="${codigo_osinergmin}">
	<input type="hidden" id="p_pagina" value="${p_pagina}">
	<input type="hidden" id="p_usuario" value="${p_usuario}">
	<input type="hidden" id="codigoEstadoLevEvaluar" value="${codigoEstadoLevEvaluar}">
	<div class="loading" style="display:none;">
		<span class="image"></span>
	</div>
	<div class="pull-main-content">
		<header class="header-app">
			<div class="logotipo-app">
				<a href="#" class="logo"></a>
			</div>
			<div class="right-section">
				<div class="pull-left user-name-dropdown btn-group">
					<a href="#" id="dropUser" class="user-name" data-toggle="dropdown">
						<span class="radio-user">
							<span class="name-us">${usuario}</span>
						</span>
					</a>
					<div class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropUser">
			        	<ul class="item-drop">
			        		<li><a href="#" data-toggle="modal" data-target="#cerrarSesion">Cerrar Sesi&oacute;n</a></li>
			        	</ul>
			        </div>
				</div>				
				<div class="sesion">
					<span>${fecha}</span>
				</div>
			</div>
		</header>
		<div class="content-colum-wrap">
			<div class="content-colum">
				<div class="colum-bandeja colum01">
					<div class="title-colum">
						<div class="title-buzon pull-left">         
							<span>Supervisiones con Infracci&oacute;n</span>
						</div>
					</div>
					<div class="scroll-tab inbox-folder carpeta-correo">
						<ul class="listado-infraccion">
							<c:forEach items="${supervisionList}" var="supList" varStatus="status">
			            		<c:if test="${status.index == 0}"><li class="active-infraccion li${supList.idSupervision}"></c:if>
			            		<c:if test="${status.index != 0}"><li class="li${supList.idSupervision}"></c:if>			            		
				            		<a href="modLevantamientos/bandejaSupervisionInfraccion" class="item-infraccion nav_ajx">
										<span class="top table-c">
											<input type="hidden" class="idSupervision" value="${supList.idSupervision}">
											<input type="hidden" class="idExpediente" value="${supList.ordenServicioDTO.expediente.idExpediente}">
											<span class="cell left">Expediente:</span>
											<span class="cell numeroExpediente">${supList.ordenServicioDTO.expediente.numeroExpediente}</span>
										</span>
										<span class="middle table-c">
											<span class="label-01 left">C&oacute;d. Osinergmin:</span>
											<span class="label-02 codOsinergmin">${supList.ordenServicioDTO.expediente.unidadSupervisada.codigoOsinergmin}</span>
										</span>	
										<span class="middle table-c">
											<span class="label-01 left">Reg.Hidrocarburos:</span>
											<span class="label-02"><c:if test="${supList.ordenServicioDTO.expediente.unidadSupervisada.nroRegistroHidrocarburo != null}">${supList.ordenServicioDTO.expediente.unidadSupervisada.nroRegistroHidrocarburo}</c:if><c:if test="${supList.ordenServicioDTO.expediente.unidadSupervisada.nroRegistroHidrocarburo == null}">--</c:if></span>
										</span>
										<span class="cell">
											<span class="estado">${supList.ordenServicioDTO.expediente.estadoLevantamiento.descripcion}</span>
										</span>											
									</a>
									<c:if test="${supList.ordenServicioDTO.expediente.estadoLevantamiento.codigo != codigoEstadoLevEvaluar}">         		 
										<span class="accion">
											<a href="modLevantamientos/bandejaIngresoLevantamientos" class="btn-levantamiento nav_ajx">Levantamiento</a>
										</span>
									</c:if>	
								</li> 
			            	</c:forEach>																				
						</ul>
					</div>
				</div>				
			</div>
			<div id="seleccion" class="view-wrap"></div>
		</div>		
		<footer class="footer">
			<span class="copyright">
				Todos los derechos reservados - Osinergmin copyright 2016
			</span>
			<a href="#" class="by">
				Powered by
			</a>
		</footer>
	</div>
	<div class="modal fade" id="cerrarSesion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
		    	<div class="modal-header">
		        	<button type="button" class="close" data-dismiss="modal">
		        		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
		        	</button>
		        	<h4 class="modal-title" id="myModalLabel"><i></i>Aviso</h4>
		      	</div>
		      	<div class="modal-body">
		        	<div class="aviso">
			        	<span class="icono-aviso"></span>
			        	<span class="msj-aviso">Usted acaba de cerrar sesi&oacute;n</span>
		        	</div>
		      	</div>
		      	<div class="modal-footer text-center">      			      	
		      		<button id="btnCerrarSession" type="button" class="btn btn-default" data-dismiss="modal">Confirmar</button>
		      	</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="confirmacion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">Close</span></button>
					<h4 class="tituloModal">Confirmaci&oacute;n</h4>
				</div>
				<div class="modal-body text-center">
					<div class="mensajeModal text-left"></div>
					<div class="modal-footer text-center">                   
						<button type="button" class="btn btn-default btnAceptarConfirmer" data-dismiss="modal">Aceptar</button>	
						<button type="button" class="btn btn-default btnCancelarConfirmer" data-dismiss="modal" style="display:none;">Cancelar</button>				
					</div>
				</div>
			</div>
		</div>
	</div>		
	<script type="text/javascript" src="/inps/javascript/app/levantamiento/common.js"></script>	
</body>						
</html>
