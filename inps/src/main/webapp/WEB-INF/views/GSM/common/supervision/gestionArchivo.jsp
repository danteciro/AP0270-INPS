<%-- 
    Document   : gestionArchivo
    Created on : 04/04/2016, 03:49:15 PM
    Author     : htorress
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/supervision/gestionArchivo.js" />' charset="utf-8"></script>
    </head>
    <body>
 		<div id="divCargarVersionarArchivo" style="display: none;" class="dialog">
	    	<form id="formCargarDocuOS" action="/inps/pages/archivo/subirArchivo" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
		        <div id="divMensajeValidaFormSubirDocuOS" class="errorMensaje" style="display: none"></div>
		        <div class="form">
			        <input type="hidden" id="tipoAccion" name="tipo" validate="[O]" value="${tipo}">
			        <input type="hidden" name="idArchivo" validate="[O]" value="${idArchivo}">
			        <input type="hidden" name="idDocumento" validate="[O]" value="${idDocumento}">
			        <input type="hidden" name="tipoDocumento" value="${tipoDocumento}">
			        <input type="hidden" name="nombreArchivo" validate="[O]" value="${nombreArchivo}">
			        <c:if test="${tipo == 'cargar'}">
			        	<div class="filaForm">
			                <div class="lbl-medium"><label class="fwb">Tipo Documento:</label></div>
			                <div class="ipt-medium-small">
			                    <span class="fwb" id="txtTipoDocumento">${tipoDocumento}</span>
			                </div>
		                </div>
		        	</c:if>
		        
			        <c:if test="${tipo == 'versionar'}">
			            <div class="filaForm">
			                <div class="lbl-medium"><label class="fwb">Nombre Archivo:</label></div>
			                <div class="ipt-medium-small">
			                    <span class="fwb" id="txtNombreArchivo">${nombreArchivo}</span>
			                </div>
			            </div>
			        </c:if>
			        
			        <div class="filaForm">
		               <div class="lbl-medium"><label>Archivo(*):</label></div>
		               <div class="container-file vam">
		                   <input id="fileArchivoCargar" name="archivoCargar" placeholder="" value="" type="file" class="fileUpload" />
		                   <input id="file_name_cargar" type="text" disabled="disabled" style="width:133px" validate="[O]"> 
		                   <a href="#" class="search_file button" style="width:100px" >Examinar</a>
		               </div>
		
		           </div> 
			   </div>
			</form>
		</div>

		<div id="divAnularArchivo" style="display: none;" class="dialog">
           	<form id="formAnularArchOS" action="/inps/pages/archivo/anularArchivo" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
	            <div class="form">
	            	<div id="divMensajeValidaFormAnularArchOS" class="errorMensaje" style="display: none"></div>
	            	<input type="hidden" name="tipo" validate="[O]" value="${tipo}">
			        <input type="hidden" name="idArchivo" validate="[O]" value="${idArchivo}">
			        <input type="hidden" name="idDocumento" validate="[O]" value="${idDocumento}">
			        <input type="hidden" name="tipoDocumento" value="${tipoDocumento}">
			        <input type="hidden" name="nombreArchivo" validate="[O]" value="${nombreArchivo}">
			        <input type="hidden" name="numeroExpediente" validate="[O]" value="${numeroExpediente}">
		            <div class="filaForm">
	                   <div class="vat"><label for="txtMotivoReasignacionRE">Motivo(*):</label></div>
		                
		                <textArea id="txtMotivo"  validate="[O]" name="motivo" class="ipt-medium-small" maxlength="999"></textArea>
		            </div>
        		</div>
	        </form>
		</div>       

		<div class="botones">
			<c:if test="${tipo != 'anular'}">
				<input type="button" id="btnGuardarGestionDocumentos" class="btn-azul btn-small" value="Guardar">
			</c:if>
			<c:if test="${tipo == 'anular'}">
				<input type="button" id="btnAnularArchivo" class="btn-azul btn-small" value="Anular Archivo">
			</c:if>
			<input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
		</div>
		
        <div class="txt-obligatorio">(*) Campos obligatorios</div>

    </body>
</html>