<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src='<c:url value="/javascript/app/common/supervision/descripcionHallazgo.js" />'
	charset="utf-8"></script>
	
<style type="text/css">
    	.ui-jqgrid{
    		text-transform: none !important;
    	}
</style>
</head>
<body>
	<input type="hidden" id="idDetalleSupervisionDH" value="${ds.idDetalleSupervision}">
	<input type="hidden" id="idObligacionDH" value="${ds.obligacion.idObligacion}">
	<input type="hidden" id="idTipificacionDH" value="${ds.tipificacion.idTipificacion}">
	<input type="hidden" id="idCriterioDH" value="${ds.criterio.idCriterio}">
	<input type="hidden" id="tipoCumple" value="${tipoCumple}">
	<input type="hidden" id="flagResultado" value="${ds.flagResultado}"> 
	<input type="hidden" id="flagRegistrado" value="${registado}"> <!-- mdiosesf -->	
	<input type="hidden" id="tipoDH" value="${tipoDH}">
	<input type="hidden" id="divProbatorio" value="${divProbatorio}">
	<input type="hidden" id="registado" value="${registado}">
	<div class="form" id="formDescHallazgo">
		<fieldset id="fldstHallazgo" style="width: 810px;" >
			<legend>Hallazgo</legend>
			<div id="divMensajeValidaFrmDH" class="errorMensaje" style="display: none"></div>
			<div class="filaForm tac" id="divTipificacionGrilla">
				<div>
					<div class="fila" >
						<div id="gridContenedorTipificaciones" class="content-grilla"></div>
						<div id="divContextMenuContenedorTipificaciones"></div>
					</div>
				</div>
			</div>			
			<div id="divComentarioHallazgo" style="display: none;" class="dialog">
				<div class="filaForm">
					<textarea class="ipt-medium-small-medium-small-medium" rows="20" id="txtDescripcion" style="text-transform: none !important;" maxlength="4000" ></textarea>
					<input type="hidden" id="idTipificacionCriterio" />
					<input type="hidden" id="tieneCriterio" />	
				</div>			
				<div class="botones">
		         	<input type="button" title="Aceptar" id="btnAceptarDescripcion" class="btn-azul btn-small" value="Aceptar" />
		        </div>
			</div>			
			<div id="divDescripcionHallazgo" style="display: none;">
				<div class="filaForm">
		            <div class="lbl-small"><label id="lblComentarioHallazgo">Comentario del hallazgo:</label></div>
		        	<div class="vam">
			       		<textarea class="ipt-medium-small-medium-small-medium" rows="7" id="txtDescripcionHallazgo" maxlength="4000"
			       		<c:if test="${tipoCumple=='0'}"> validate="[O]" </c:if>
			       		<c:if test="${tipoDH=='consulta'}">disabled</c:if>
			       		 ></textarea>
			       	</div>
		        </div>
		    </div>			
       		<div class="botones">
       		 	<input type="button" title="Guardar" id="btnGuardarDescripcionHallazgo" class="btn-azul btn-small" value="Guardar" <c:if test="${tipoDH=='consulta'}">style="display: none;"</c:if>>
   			</div>
		</fieldset>
   	</div>
   	<!--<div id="divMediosProbatorio" style="<c:if test="${registado=='0'}">display:none;</c:if><c:if test="${registado=='1'}">display:inline;</c:if>">-->
   	<div id="divMediosProbatorio" style="<c:if test="${tipoDH=='consulta' and divProbatorio=='false'}">display:none;</c:if><c:if test="${tipoDH=='consulta' and divProbatorio=='true'}">display:inline;</c:if>
   	<c:if test="${tipoDH=='registro' and registado=='1'}">display:inline;</c:if><c:if test="${tipoDH=='registro' and registado=='0'}">display:none;</c:if>">
        <fieldset id="fldstMedios" style="width: 810px;">
		<legend>Medios Probatorios</legend>
        <div id="divAdjuntoDH">
	        <form id="formMedioAprobatorio" action="/inps/pages/archivo/subirPghDocumentoAdjuntoDetalleSuper" method="post" enctype="multipart/form-data" encoding="multipart/form-data" >
	      	<div class="filaForm tac">
	             <div class="lbl-small tal"><label for="txtDescripcionMP">Descripci&oacute;n(*):</label></div>
	             <div>
	                 <input type="text" id="txtDescripcionMP" validate="[O]" style="text-transform: none !important;" name="descripcionDocumento" maxlength="200" class="ipt-medium-small" value="" />
	             </div>
	        </div>
		        <div class="filaForm tac">
		             <div class="lbl-small tal"><label>Archivo(*):</label></div>
		             <div class="container-file vam">
		                 <input id="fileArchivoSuper" name="archivos[0]" placeholder="" value="" type="file" class="fileUpload"/>
						 <input type="hidden" id="idDetalleSupervisionSuper" name="detalleSupervision.idDetalleSupervision" value="${ds.idDetalleSupervision}">
						 <input type="hidden" id="flagResultadoSuperMP"  name="detalleSupervision.flagResultado" value="${tipoCumple}">
		                 <input id="file_nameSuper" type="text" validate="[O]" name="file_nameSuper" disabled="disabled" style="width:133px" maxlength="200"> 
		                 <a href="#" class="search_file button" style="width:100px" >Examinar</a>
		             </div>
		        </div>
	        </form>
	        <div class="filaForm tac">
	          	<div class="lbl-medium">
	         		<input type="button" id="btnAgregarMedioProbatorio" title="Agregar Archivo" class="btn-azul btn-small" value="Agregar Archivo"
	         		 style="<c:if test="${tipoDH=='consulta' and divProbatorio=='false'}">display:none;</c:if><c:if test="${tipoDH=='registro'}">display:inline;</c:if>">
	         	</div>
	        </div>
	    </div>
       	<div class="filaForm tac">
       		<div>
               <div class="fila">
	            	<div id="gridContenedorArchivosMP" class="content-grilla"></div>
	            	<div id="divContextArchivosMP"></div>
	            	
               </div>
            </div>
        </div>
        <div class="txt-obligatorio">Los tipos de archivos permitidos son: <c:out value="${archivosPermitidos}"/> y el tamaño máximo para cada uno debe ser <c:out value="${tamanioAdjuntoMB}"/> MB</div>
        </fieldset>
    </div>
	<div class="txt-obligatorio">(*) Campos obligatorios</div>
    <div class="botones">
        <input type="button" title="Cerrar" id="btnCerrarDescripcionHallazgo" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
    </div>
</body>
</html>