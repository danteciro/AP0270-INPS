<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/informeSupervision/adjuntarInforme.js" />' charset="utf-8"></script>
   </head>
	<body>
		<form id="formSubirDocuOSInforme" action="/inps/pages/informeSupervision/adjuntarInforme" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
        	<div class="form">
        	 		<div id="divMensajeValidaFormSubirDocuOSInforme" class="errorMensaje" style="display: none"></div>
               		<input type="hidden" id="nroRuc" name="ruc" value="${ruc}">
                	<input type="hidden" id="nroExpediente" name="numeroExpediente" value="${numeroExpediente}"  >
                	<input type="hidden" id="idSupervisionRechCargaInforme" name="idSupervisionRechCargaInforme" value="${idSupervisionRechCargaInforme}" >
                	<input type="hidden" id="hdnNombreEmpresaInf" name="nombreEmpresaInf" value="${nombreEmpresaInf}"/>
					<input type="hidden" id="hdnAnioInf" name="anioInf" value="${anioInf}"/>
                	
                	
               		<div class="filaForm">
					 	<div class="lbl-small vam" ><label>Informe(*):</label></div>
	                    <div class="container-file vam">
	                    		<input type="file" class="fileUpload" value= "" placeholder="" name="archivo" id="fileArchivoInforme">
                       			<input type="text" validate="[O]" style="width:250px" disabled="disabled" id="file_nameInforme"> 
                        		<a style="width:100px" class="search_file button" href="#">Examinar</a>
                    	</div>
	                </div>
					<div class="filaForm">
					 	<div class="lbl-small vam" ><label>Tiene Observaciones :</label></div>
	                    <div class="vam">
			        	    <input id="radioObservacion" type="checkbox"  value="${flag}" name="flag">
			            	<label class="checkbox" for="radioObservacion"></label>
			            </div>
	                </div>
					<div id="divMotivoObs" class="filaForm" style="display: none;">
						<div class="lbl-small vam" ><label>Observación :</label></div>
	                   	<textArea id="txtMotivoObs" name="observacion" class="vam" value="${observacion}"  maxlength="4000" style="width:360px" ></textArea>
	                </div>
			 </div>
		</form>
		<div class="botones">
            <input type="button" id="btnGuardarDocumento" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cancelar">
        </div>
        <div class="txt-obligatorio">(*) Campos obligatorios</div>
	</body>
</html>