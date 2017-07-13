<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src='<c:url value="/javascript/app/common/supervision/evaluacionDescargo.js" />'
	charset="utf-8"></script>
	
	<style type="text/css">
    	.ui-jqgrid{
    		text-transform: none !important;
    	}
    </style>
	
</head>
<body>
	<input type="hidden" id="idDetalleSupervisionED" value="${dsED.idDetalleSupervision}">
	<input type="hidden" id="idObligacionED" value="${dsED.obligacion.idObligacion}">
	<input type="hidden" id="idTipificacionED" value="${dsED.tipificacion.idTipificacion}">
	<input type="hidden" id="idCriterioED" value="${dsED.criterio.idCriterio}">
	<input type="hidden" id="idDetalleSupervisionAntED" value="${dsED.idDetalleSupervisionAnt}">
	<input type="hidden" id="tipoSancion" value="${tipoSancion}">
	<input type="hidden" id="flagResultadoED" value="${dsED.flagResultado}">
	<input type="hidden" id="tipoED" value="${tipoED}">
	<input type="hidden" id="divProbatorio" value="${divProbatorio}">
	<input type="hidden" id="registado" value="${registado}">
	<div class="form" id="formEvalDescargo">
		<fieldset id="fldstDescargo" style="width: 810px;" >
			<legend>Descargo</legend>
			<div id="divMensajeValidaFrmED" class="errorMensaje" style="display: none"></div>
			<div class="filaForm tac" id="divTipificacionGrillaED">
				<div>
					<div class="fila" >
						<div id="gridContenedorTipificacionesED" class="content-grilla"></div>
						<div id="divContextMenuContenedorTipificacionesED"></div>
					</div>
				</div>
			</div>			
			<div id="divComentarioHallazgoED" style="display: none;" class="dialog">
				<div class="filaForm">
					<textarea class="ipt-medium-small-medium-small-medium" rows="4" id="txtDescripcionED" maxlength="4000"></textarea>
					<input type="hidden" id="idTipificacionCriterioED" />
					<input type="hidden" id="tieneCriterio" />	
				</div>			
				<div class="botones">
		         	<input type="button" title="Aceptar" id="btnAceptarDescripcionED" class="btn-azul btn-small" value="Aceptar" />
		        </div>
			</div>			
			<div id="divDescripcionHallazgoED" style="display: none;">
				<div class="filaForm">
		            <div class="lbl-small "><label id="lblComentarioHallazgo">Descargo(*):</label></div>
		        	<div class="vam">
			       		<textarea class="ipt-medium-small-medium-small-medium" rows="7" id="txtDescripcionHallazgoED" maxlength="4000"
			       		<c:if test="${tipoCumple=='0'}"> validate="[O]" </c:if>
			       		<c:if test="${tipoED=='consulta'}">disabled</c:if>></textarea>
			       	</div>
		        </div>
		    </div>
	       	<div class="botones">
				<input type="button" title="Guardar" id="btnGuardarEvalDescargo" class="btn-azul btn-small" value="Guardar" <c:if test="${tipoED=='consulta'}">style="display: none;"</c:if>>
	   		</div>
   		</fieldset>
   	</div>
   	<!-- <div id="divMediosProbatorioED" style="<c:if test="${registado=='0'}">display:none;</c:if><c:if test="${registado=='1'}">display:inline;</c:if>">  -->
   	<div id="divMediosProbatorioED" style="<c:if test="${tipoED=='consulta' and divProbatorio=='false'}">display:none;</c:if><c:if test="${tipoED=='consulta' and divProbatorio=='true'}">display:inline;</c:if>
   	<c:if test="${tipoDH=='registro' and registado=='1'}">display:inline;</c:if><c:if test="${tipoDH=='registro' and registado=='0'}">display:none;</c:if>">
    <fieldset id="fldstMediosDescargo" style="width: 810px;">
	<legend>Medios Probatorios</legend>
      <div id="divAdjuntoED">
       <form id="formMedioAprobatorioED" action="/inps/pages/archivo/subirPghDocumentoAdjuntoDetalleSuper" method="post" enctype="multipart/form-data" encoding="multipart/form-data" >
	    	<div class="filaForm tac">
	           <div class="lbl-small tal"><label for="txtDescripcionMPED">Descripci&oacute;n(*):</label></div>
	           <div>
	               <input type="text" id="txtDescripcionMPED" validate="[O]" name="descripcionDocumento" maxlength="200" class="ipt-medium-small" value="" />
	           </div>
	      	</div>
	        <div class="filaForm tac">
	            <div class="lbl-small tal"><label>Archivo(*):</label></div>
	            <div class="container-file vam">
	                <input id="fileArchivoSuperED" name="archivos[0]" placeholder="" value="" type="file" class="fileUpload" />
				 	<input type="hidden" id="idDetalleSupervisionSuperED" name="detalleSupervision.idDetalleSupervision" value="${dsED.idDetalleSupervision}">
				 	<input type="hidden" id="flagResultadoSuperMPED"  name="detalleSupervision.flagResultado" value="${tipoSancion}">
	                <input id="file_nameSuperED" validate="[O]" type="text" name="file_nameSuper" disabled="disabled" style="width:133px" maxlength="200"> 
	                <a href="#" class="search_file button" style="width:100px" >Examinar</a>
	            </div>
	        </div>
      </form>
      <div class="filaForm tac">
        	<div class="lbl-medium">
       		<input type="button" id="btnAgregarMedioProbatorioED" title="Agregar Archivo"   class="btn-azul btn-small" value="Agregar Archivo" 
       		style="<c:if test="${tipoED=='consulta' and divProbatorio=='false'}">display:none;</c:if><c:if test="${tipoDH=='registro'}">display:inline;</c:if>">
       	</div>
      </div>
  	</div>
	<div class="filaForm tac">
    		<div>
            <div class="fila">
          	<div id="gridContenedorArchivosMPED" class="content-grilla"></div>
          	<div id="divContextArchivosMPED"></div>
          	</div>
         </div>
    </div>
    <div class="txt-obligatorio">Los tipos de archivos permitidos son: <c:out value="${archivosPermitidos}"/> y el tamaño máximo para cada uno debe ser <c:out value="${tamanioAdjuntoMB}"/> MB</div>
    </fieldset>
	</div>
	<div class="botones">
        <input type="button" title="Cerrar" id="btnCerrarEvalDescargo" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
    </div>
    <div class="txt-obligatorio">(*) Campos obligatorios</div>
</body>
</html>