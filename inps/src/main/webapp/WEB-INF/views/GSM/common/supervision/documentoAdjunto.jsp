<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/supervision/documentoAdjunto.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
        	<input type="hidden" id="idSupervisionDA" value="${s.idSupervision}">
        	<input type="hidden" id="modoDASuper" value="${modoDA}">
        	<div id="divMensajeFrmDocumentoAdjuntoSuper" class="errorMensaje" style="display: none"></div>
        	<div id="divRegDASuper" <c:if test="${modoDA=='consulta'}">style="display: none;"</c:if> >
        	<form id="formDocumentoAdjuntoSuper" action="/inps/pages/archivo/subirPghDocumentoAdjunto" method="post" enctype="multipart/form-data" encoding="multipart/form-data" >
		      	<div class="filaForm tac">
		             <div class="lbl-small tal"><label for="txtDescripcionDASuper">Descripci&oacute;n(*):</label></div>
		             <div>
		                 <input type="text" id="txtDescripcionDASuper" validate="[O]" name="descripcionDocumento" maxlength="200" class="ipt-medium-small" value="" />
		             </div>
		        </div>
		        <div class="filaForm tac">
		             <div class="lbl-small tal"><label>Archivo(*):</label></div>
		             <div class="container-file vam">
		                 <input id="fileDASuper" name="archivos[0]" placeholder="" value="" type="file" class="fileUpload" />
						 <input type="hidden" name="supervision.idSupervision" value="${s.idSupervision}">
		                 <input id="file_nameDASuper" type="text" name="file_nameDASuper" disabled="disabled" style="width:133px" maxlength="200" validate="[O]"> 
		                 <a href="#" class="search_file button" style="width:100px" >Examinar</a>
		             </div>
		        </div>
	        </form>
	        
	        <div class="filaForm tac">
	          	<div class="lbl-medium">
	         		<input type="button" id="btnAgregarDASuper" title="Agregar Archivo" class="btn-azul btn-small" value="Agregar Archivo">
	         	</div>
	        </div>
	        </div>
	       	<div class="filaForm tac">
	       		<div>
	               <div class="fila">
		            	<div id="gridContenedorDASuper" class="content-grilla"></div>
		            	<div id="divContextMenuDASuper"></div>
		            	
	               </div>
	            </div>
	        </div>
        
        </div>
        <div class="botones">
	        <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
	    </div>
	    <div class="txt-obligatorio">(*) Campos obligatorios</div>
	    <div class="txt-obligatorio">Los tipos de archivos permitidos son: <c:out value="${archivosPermitidos}"/> y el tamaño máximo para cada uno debe ser <c:out value="${tamanioAdjuntoMB}"/> MB</div>
	</body>
</html>